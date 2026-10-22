package com.revenga.rits.back.data.loader.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.data.core.dao.impl.MapperDML;
import com.revenga.rits.back.data.core.dao.impl.ObjectDaoImpl;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ModelHelper;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CollectionManagerService {

	private Map<Class<?>, ObjectDaoImpl> collections = Collections.synchronizedMap(new HashMap<>());

	private Integer rowsLoaded = 0;

	public void load() {

		loadData();

		if (!collections.isEmpty()) {

			log.info("Sorting collections...");
			sortCollections(ModelHelper.OrderType.ORDER_ID);
			log.info("Collections sorted");

			log.info("Caching collections...");
			cacheCollections();
			log.info("Collections cached");
		}
	}

	private boolean sortCollections(ModelHelper.OrderType orderType) {

		Calendar calIniLoad = Calendar.getInstance();
		Integer sortedItems = 0;
		
		synchronized (collections) {
		
			for (Map.Entry<Class<?>, ObjectDaoImpl> entry : collections.entrySet()) {

				ObjectDaoImpl daoImpl = entry.getValue();

				if (daoImpl != null) {

					daoImpl.sort(orderType);
					
					sortedItems += daoImpl.getList().size();
				}
			}
		}
		
		log.info("Total " + sortedItems + " items sorted in " + (new DecimalFormat("###.###")).format(
				(double) (Calendar.getInstance().getTimeInMillis() - calIniLoad.getTimeInMillis()) / 1000)
				+ " secs.");

		return false;
	}

	private boolean cacheCollections() {

		Calendar calIniLoad = Calendar.getInstance();
		List<Integer> cachedItems = Collections.synchronizedList(new ArrayList<>());
				
		if (collections.size() > 0) {

			RedisConnection redisConnection = RedisConnection.getInstance();
			
			DataCache dataCache = new DataCache(redisConnection);
			
			dataCache.delAll();
			
			ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			
			List<Future<?>> futures = new ArrayList<>();
			
			synchronized (collections) {
				
				for (Map.Entry<Class<?>, ObjectDaoImpl> entry : collections.entrySet()) {
					
					Runnable task = () -> {
						
						ObjectDaoImpl daoImpl = entry.getValue();

						if (daoImpl != null) {
							
							Instant instantInit = Instant.now();
							
							Key[] keys = dataCache.puts(daoImpl.getList().stream().toArray(BasicElement[]::new));
							
							log.info("Caching " + keys.length + " " + entry.getKey().getSimpleName() + " items in "
									+ Duration.between(instantInit, Instant.now()).toMillis() + "ms.");
							
							cachedItems.add(keys.length);
						}	
					};
					
					Future<?> f = executor.submit(task);
					
					futures.add(f);
				}				
			}
			
			// A) Await all runnables to be done (blocking)
			for (Future<?> future : futures) {
				
				try {
					
					future.get(); // get will block until the future is done
					
				} catch (InterruptedException e) {
					
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
					
				} catch (ExecutionException e) {
					
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
			
			redisConnection.getProcessMonitor().getProcessInfo().setReady(true);

			log.info("Total " + cachedItems.stream().mapToInt(Integer::valueOf).sum() + " items cached in " + (new DecimalFormat("###.###")).format(
					(double) (Calendar.getInstance().getTimeInMillis() - calIniLoad.getTimeInMillis()) / 1000)
					+ " secs.");
		}

		return false;
	}
	
	public ObjectDaoImpl getCollection(Class<?> modelClass) {

		return collections.get(modelClass);
	}

	public List<Object> getCollectionList(Class<?> modelClass) {

		return getCollection(modelClass).getList();
	}

	private Class<?>[] getModels() {

		XMLInputFactory f = null;
		XMLStreamReader sr = null;
		XmlMapper mapper = null;
		List<Class<?>> clazzList = new ArrayList<Class<?>>();
		Class<?>[] clazzsArray = null;

		try {
			
			List<InputStream> isList = ResourcesUtil.getResources("/dml");
						
			for (InputStream dmlFile : isList) {

				f = XMLInputFactory.newInstance();
				sr = f.createXMLStreamReader(dmlFile);
				mapper = new XmlMapper();

				while (sr.hasNext()) {

					int eventType = sr.next();

					MapperDML mapperDML;

					switch (eventType) {
						case XMLStreamConstants.START_ELEMENT:

							mapperDML = mapper.readValue(sr, MapperDML.class);

							if (mapperDML.getEnabled().equals("true")) {

								Class<?> clazzModel = ClassesUtil.getModelClassBySimpleClassName(mapperDML.getModel());
								
								if (clazzModel != null) {
									
									clazzList.add(clazzModel);
								}
							}

							break;

						case XMLStreamConstants.ATTRIBUTE:
							break;
					}
				}
			}
			
		} catch (XMLStreamException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		if (!CollectionUtils.isEmpty(clazzList)) {

			clazzsArray = new Class[clazzList.size()];
			clazzsArray = clazzList.toArray(clazzsArray);
		}

		return clazzsArray;
	}

	private void loadData() {

		Connection connection = null;
		
		try {

			Class<?>[] clazzes = getModels();

			Calendar calIniLoad = Calendar.getInstance();

			connection = DataSourceConnection.getInstance().getConnection();

			if (connection != null && !connection.isClosed()) {

				ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				
				List<Future<?>> futures = new ArrayList<>();
				
				final Connection conn = connection;
				
				for (Class<?> clazz : clazzes) {

					Runnable task = () -> {
						
						collections.put(clazz, loadData(conn, clazz));	
					};
					
					Future<?> f = executor.submit(task);
					
					futures.add(f);
				}
				
				// A) Await all runnables to be done (blocking)
				for (Future<?> future : futures) {
					
					try {
						
						future.get(); // get will block until the future is done
						
					} catch (InterruptedException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
						Thread.currentThread().interrupt();
						
					} catch (ExecutionException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}

				log.info("Total " + rowsLoaded + " items loaded in " + (new DecimalFormat("###.###")).format(
						(double) (Calendar.getInstance().getTimeInMillis() - calIniLoad.getTimeInMillis()) / 1000)
						+ " secs.");
			}

			log.info("Initialized Collection manager success");

		} catch (SQLException e) {
			log.error("Initialized Collection manager sql error: " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IOException e) {
			log.error("Initialized Collection manager error: " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} finally {
			
			if (connection != null) {
				
				try {
					
					connection.close();
					
				} catch (SQLException e) {
					
					log.error("Collection manager sql error: " + e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
	}

	private ObjectDaoImpl loadData(Connection connection, Class<?> modelClass) {

		List<Object> listData = null;
		ObjectDaoImpl objectDao = null;

		objectDao = new ObjectDaoImpl(modelClass);

		if (objectDao != null) {

			log.debug("Loading " + modelClass.getSimpleName() + " ...");
			
			Instant instantInit = Instant.now();

			if (objectDao.load(connection)) {

				listData = objectDao.getList();

				log.info("Loaded " + listData.size() + " " + modelClass.getSimpleName() + " items in "
						+ Duration.between(instantInit, Instant.now()).toMillis() + "ms.");

				rowsLoaded += listData.size();

			} else {

				log.error("Error loading data for " + modelClass.getSimpleName());
			}
		}

		return objectDao;
	}

	public Object getEntity(Class<?> clazz, Long id) {

		List<Object> list = null;

		list = getCollectionList(clazz);

		if (list == null)
			return null;

		Field fieldId = null;

		fieldId = ClassesUtil.getField(clazz, "id");

		try {

			if (fieldId != null) {

				fieldId.setAccessible(true);

				for (Object object : list) {

					if (((Long) fieldId.get(object)).equals(id)) {

						return object;
					}
				}
			}
		} catch (SecurityException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	public Object getEntity(Object entity) {

		Class<?> clazz = entity.getClass();

		List<Object> list = null;

		list = getCollectionList(clazz);

		if (list == null)
			return null;

		if (list.contains(entity)) {

			return entity;
		}

		return null;
	}

	public Object addEntity(Object entity) {

		Class<?> clazz = entity.getClass();

		List<Object> list = null;

		list = getCollectionList(clazz);

		if (list != null && list.add(entity)) {
			return entity;
		}
		return null;
	}

	public Object replaceEntity(Object entity) {

		Class<?> clazz = entity.getClass();

		List<Object> list = null;

		list = getCollectionList(clazz);
		Integer size = list.size();
		for (int index = 0; index < size; index++) {
			if (((BasicElement) list.get(index)).getId().equals(((BasicElement) entity).getId())) {
				if (list.remove(index) != null) {
					if (list.add(entity))
						return entity;
				}
			}
		}

		return null;
	}

	public Object removeEntity(Object entity) {

		Class<?> clazz = entity.getClass();

		List<Object> list = null;

		list = getCollectionList(clazz);
		Integer size = list.size();
		for (int index = 0; index < size; index++) {
			if (((BasicElement) list.get(index)).getId().equals(((BasicElement) entity).getId())) {
				if (list.remove(index) != null) {
					return entity;
				}
			}
		}
		return null;
	}

	public Object removeEntity(Class<?> clazz, Integer id) {

		List<Object> list = null;

		list = getCollectionList(clazz);

		if (list == null)
			return list;

		Field fieldId = null;

		try {

			fieldId = clazz.getDeclaredField("id");

		} catch (Exception e) {

			// No trace to produce. Usual situation...
		}

		try {

			if (fieldId == null) {

				fieldId = clazz.getSuperclass().getDeclaredField("id");
			}

			if (fieldId != null) {

				fieldId.setAccessible(true);

				for (Object object : list) {

					if (((Integer) fieldId.get(object)).equals(id)) {

						list.remove(object);

						return object;
					}
				}
			}
		} catch (NoSuchFieldException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (SecurityException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	public List<Class<?>> getCollectionNames() {

		List<Class<?>> listCollections = new ArrayList<>();

		for (Map.Entry<Class<?>, ObjectDaoImpl> entry : collections.entrySet()) {

			listCollections.add(entry.getKey());
		}

		return listCollections;
	}

	public Object insertItem(Object newObject, Connection connection) {

		Object insertedObject = null;

		if (connection != null) {

			Class<?> modelClass = newObject.getClass();

			ObjectDaoImpl objectDao = collections.get(modelClass);

			if (objectDao != null) {

				List<Object> collection = objectDao.getList();

				if (collection != null) {

					insertedObject = objectDao.insert(connection, newObject);
				}
			}
		}

		return insertedObject;
	}

	public Object updateItem(Object newObject, Connection connection) {

		Object updatedObject = null;

		if (connection != null) {

			Class<?> modelClass = newObject.getClass();

			ObjectDaoImpl objectDao = collections.get(modelClass);

			if (objectDao != null) {

				List<Object> collection = objectDao.getList();

				if (collection != null && !collection.isEmpty()) {

					Object oldObject = ModelHelper.getObject(collection, ModelHelper.OrderType.ORDER_ID.getFieldName(),
							((BasicElement) newObject).getId());

					if (oldObject != null) {

						updatedObject = objectDao.update(connection, newObject);
					}
				}
			}
		}

		return updatedObject;
	}

	public Object removeItem(Class<?> modelClass, Long id, Connection connection) {

		Object deletedObject = null;

		if (connection != null) {

			ObjectDaoImpl objectDao = collections.get(modelClass);

			if (objectDao != null) {

				List<Object> collection = objectDao.getList();

				if (collection != null && !collection.isEmpty()) {

					Object oldObject = ModelHelper.getObject(collection, ModelHelper.OrderType.ORDER_ID.getFieldName(),
							id);

					if (oldObject != null) {

						deletedObject = objectDao.delete(connection, oldObject);
					}
				}
			}
		}

		return deletedObject;
	}

	public Object removeItem(Class<?> modelClass, Object oldObject, Connection connection) {
		Object deletedObject = null;

		if (connection != null) {

			ObjectDaoImpl objectDao = collections.get(modelClass);

			if (objectDao != null) {

				if (oldObject != null) {

					deletedObject = objectDao.delete(connection, oldObject);
				}
			}
		}

		return deletedObject;
	}
}
