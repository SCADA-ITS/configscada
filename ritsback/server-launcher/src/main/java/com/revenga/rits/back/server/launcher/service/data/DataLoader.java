package com.revenga.rits.back.server.launcher.service.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

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
public class DataLoader {

	private Map<Class<?>, ObjectDaoImpl> collections = Collections.synchronizedMap(new HashMap<>());

	private Integer rowsLoaded = 0;
	
	public DataLoader() {
		
		super();
	}

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
		Integer cachedItems = 0;
				
		if (collections.size() > 0) {

			RedisConnection redisConnection = RedisConnection.getInstance();
			
			DataCache dataCache = new DataCache(redisConnection);
			
			dataCache.delAll();
			
			synchronized (collections) {
				
				for (Map.Entry<Class<?>, ObjectDaoImpl> entry : collections.entrySet()) {

					ObjectDaoImpl daoImpl = entry.getValue();

					if (daoImpl != null) {

						Key[] keys = dataCache.puts(daoImpl.getList().stream().toArray(BasicElement[]::new));
						
						cachedItems += keys.length;
					}
				}				
			}
		
			log.info("Total " + cachedItems + " items cached in " + (new DecimalFormat("###.###")).format(
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
		List<Class<?>> clazzList = new ArrayList<>();
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
						case XMLStreamReader.START_ELEMENT:

							mapperDML = mapper.readValue(sr, MapperDML.class);

							if (mapperDML.getEnabled().equals("true")) {

								clazzList.add(ClassesUtil.getModelClassBySimpleClassName(mapperDML.getModel()));
							}

							break;

						case XMLStreamReader.ATTRIBUTE:
							break;
					}
				}
			}
			
		} catch (XMLStreamException | IOException | ClassNotFoundException e) {
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


		log.debug("Loading " + modelClass.getSimpleName() + " ...");

		if (objectDao.load(connection)) {

			listData = objectDao.getList();

			log.debug("Loaded " + listData.size() + " " + modelClass.getSimpleName() + " items");

			rowsLoaded += listData.size();

		} else {

			log.error("Error loading data for " + modelClass.getSimpleName());
		}

		return objectDao;
	}
}
