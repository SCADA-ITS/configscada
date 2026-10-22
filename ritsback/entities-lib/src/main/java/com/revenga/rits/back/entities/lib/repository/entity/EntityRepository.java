package com.revenga.rits.back.entities.lib.repository.entity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.helper.CacheModelHelper;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.data.core.dao.impl.ObjectDaoImpl;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ParamHist;
import com.revenga.rits.back.data.core.model.command.ChangeEntityRepositoryCommand;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class EntityRepository {

	public static final Long DEFAULT_CACHE_RETENTION_TIME = 60000L;
	public static final Long MAX_CACHE_RETENTION_TIME = 1000000000000L;

	private Class<?> clazz;
	private boolean cacheable;
	private Long bufferRententionTime;
	private Connection connection;
	private List<EntityRepository> repositories;
	private boolean publishChanges;
	private boolean subscribeChanges;
	EntityOnChange entityOnChange;
	private boolean requestAll;

	private EntityBinarySubscriptorHnd entityBinarySubscriptorHnd;
	private EntitySubscriptorHnd entitySubscriptorHnd;

	private DataCache dataCache;
	private CommandCache cmdCache;

	private Map<String, Pair<BasicElement, Long>> entitiesMap = Collections
			.synchronizedMap(new HashMap<String, Pair<BasicElement, Long>>());
	private List<CustomMap> customMaps = Collections.synchronizedList(new ArrayList<CustomMap>());

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Map<String, Pair<Set<Key>, Long>> dbKeysMap = Collections.synchronizedMap(new HashMap<>());

	private ObjectDaoImpl objectDao;
	
	public ObjectDaoImpl getObjectDao() {

		if (objectDao == null) {

			objectDao = new ObjectDaoImpl(clazz);
		}

		return objectDao;
	}

	public enum PersistOperation {
		PUT, DELETE
	}

	protected EntityRepository(Class<?> clazz) {

		super();

		initialize(clazz, false, 0L, null, null, false, false, null);
	}

	protected EntityRepository(Class<?> clazz, EntityRepositoryOptions options) {

		super();

		Long bufferRetentionTime = 0L;

		if (options.isBuffered()) {

			if (options.getBufferRetentionTime() != null) {

				bufferRetentionTime = options.getBufferRetentionTime();
			} else {

				bufferRetentionTime = DEFAULT_CACHE_RETENTION_TIME;
			}
		}

		initialize(clazz, options.isBuffered(), bufferRetentionTime, null, options.getRepositories(),
				options.isPublishChanges(), options.isSubscribeChanges(), options.getEntityOnChange());
	}

	/**
	 * @deprecated (Replace for EntityRepository(Class<?> clazz,
	 *             EntityRepositoryOptions options) constructor)
	 */
	@Deprecated(forRemoval = true)
	protected EntityRepository(Class<?> clazz, boolean buffered) {
		super();

		initialize(clazz, buffered, DEFAULT_CACHE_RETENTION_TIME, null, null, false, false, null);
	}

	/**
	 * @deprecated (Replace for EntityRepository(Class<?> clazz,
	 *             EntityRepositoryOptions options) constructor)
	 */
	@Deprecated(forRemoval = true)
	protected EntityRepository(Class<?> clazz, boolean buffered, Long bufferRetentionTime) {
		super();

		initialize(clazz, buffered, bufferRetentionTime, null, null, false, false, null);
	}

//	protected EntityRepository(Class<?> clazz, boolean buffered, Long bufferRetentionTime, Connection connection) {
//		super();
//
//		initialize(clazz, buffered, bufferRetentionTime, connection, null);
//	}

	/**
	 * @deprecated (Replace for EntityRepository(Class<?> clazz,
	 *             EntityRepositoryOptions options) constructor)
	 */
	@Deprecated(forRemoval = true)
	protected EntityRepository(Class<?> clazz, List<EntityRepository> repositories) {

		super();

		initialize(clazz, false, 0L, null, repositories, false, false, null);
	}

	/**
	 * @deprecated (Replace for EntityRepository(Class<?> clazz,
	 *             EntityRepositoryOptions options) constructor)
	 */
	@Deprecated(forRemoval = true)
	protected EntityRepository(Class<?> clazz, boolean buffered, List<EntityRepository> repositories) {
		super();

		initialize(clazz, buffered, DEFAULT_CACHE_RETENTION_TIME, null, repositories, false, false, null);
	}

	/**
	 * @deprecated (Replace for EntityRepository(Class<?> clazz,
	 *             EntityRepositoryOptions options) constructor)
	 */
	@Deprecated(forRemoval = true)
	protected EntityRepository(Class<?> clazz, boolean buffered, Long bufferRetentionTime,
			List<EntityRepository> repositories) {
		super();

		initialize(clazz, buffered, bufferRetentionTime, null, repositories, false, false, null);
	}

//	protected EntityRepository(Class<?> clazz, boolean buffered, Long bufferRetentionTime, Connection connection,
//			List<EntityRepository> repositories) {
//		super();
//
//		initialize(clazz, buffered, bufferRetentionTime, connection, repositories);
//	}

	private Set<Key> getDbKeys(String keyPattern) {

		Pair<Set<Key>, Long> pair = null;
		Set<Key> dbKeys = null;
		boolean isUpdated = false;

		if (cacheable && bufferRententionTime > 0L) {

			pair = dbKeysMap.get(keyPattern);

			if (pair != null) {

				isUpdated = (Duration.between(Instant.ofEpochMilli(pair.getValue().longValue()), Instant.now())
						.toMillis() < bufferRententionTime);

				if (isUpdated) {

					dbKeys = pair.getLeft();
				}
			} else {

				dbKeys = createDbKeysFromPattern(keyPattern);

				isUpdated = (dbKeys != null);
			}
		}

		if (!isUpdated) {

			dbKeys = getKeys(new Key(keyPattern));

			if (cacheable && bufferRententionTime > 0L) {

				dbKeysMap.put(keyPattern, new MutablePair<>(dbKeys, Instant.now().toEpochMilli()));
			}
		}

		return dbKeys;
	}

	/**
	 * 
	 * Creates a new, more specific pattern from an existing one (that match) that
	 * is compatible with the new one.
	 * 
	 * @param keyPatternStr: Pattern to find in dbKeysMap
	 * @return Set of Keys that contains keyPatternStr pattern
	 */
	private Set<Key> createDbKeysFromPattern(String keyPatternStr) {

//		Instant instant = Instant.now();
		Key keyPattern = new Key(keyPatternStr);
		Set<Key> newDbKeys = null;
		long lastUpdate = 0L;
		List<String> dbKeysMapFiltered = null;

		if (!MapUtils.isEmpty(dbKeysMap)) {

			synchronized (dbKeysMap) {

				if (cacheable && (MAX_CACHE_RETENTION_TIME.equals(bufferRententionTime) && requestAll)) {
					
					Key allKeysForCurrentClazz = new Key(clazz, null);
					
					if (dbKeysMap.containsKey(allKeysForCurrentClazz.getSimpleValue())) {
						
						dbKeysMapFiltered = Arrays.asList(allKeysForCurrentClazz.getSimpleValue());				
					}
				}
				else {
					
					dbKeysMapFiltered = dbKeysMap.entrySet().stream()
							.filter(e -> e.getKey() != null && keyPattern.match(new Key(e.getKey())))
							.map(Map.Entry::getKey).collect(Collectors.toList());
				}
			}

			if (!CollectionUtils.isEmpty(dbKeysMapFiltered)) {

				// Sort matches from less to more wildcards in key string
				// ex: Plan:1:* is less than Plan:*:*
				Comparator<String> compareByWildcards = (String o1, String o2) -> Long
						.valueOf(o1.chars().filter(ch -> ch == '*').count())
						.compareTo(Long.valueOf(o2.chars().filter(ch -> ch == '*').count()));

				Collections.sort(dbKeysMapFiltered, compareByWildcards);

				String keyPatternDbKey = dbKeysMapFiltered.get(0);

				if (!StringUtils.isEmpty(keyPatternDbKey)) {

					Pair<Set<Key>, Long> valueDbKey = dbKeysMap.get(keyPatternDbKey);

					if (valueDbKey != null) {

						newDbKeys = new HashSet<>();

						Set<Key> keys = valueDbKey.getLeft();
						lastUpdate = valueDbKey.getRight().longValue();

						if (!CollectionUtils.isEmpty(keys)) {

							synchronized (dbKeysMap) {

								Iterator<Key> itDbKeys = keys.iterator();

								while (itDbKeys.hasNext()) {

									Key key = itDbKeys.next();

									if (key.match(keyPattern)) {

										boolean isUpdated = (Duration
												.between(Instant.ofEpochMilli(lastUpdate), Instant.now())
												.toMillis() < bufferRententionTime);

										if (isUpdated) {

											newDbKeys.add(key);
										}
									}
								}
							}
						}
					}
				}
			}

			if (newDbKeys != null) {

				dbKeysMap.put(keyPatternStr, new MutablePair<>(newDbKeys, lastUpdate));
			}
		}

//		log.debug("createDbKeysFromPattern for " + keyPatternStr + ": "
//				+ Duration.between(instant, Instant.now()).toMillis() + " ms.");

		return newDbKeys;
	}

	private void initialize(Class<?> clazz, boolean buffered, Long bufferRetentionTime, Connection connection,
			List<EntityRepository> repositories, boolean publishChanges, boolean subscribeChanges,
			EntityOnChange entityOnChange) {

		this.clazz = clazz;
		this.cacheable = buffered;
		this.bufferRententionTime = bufferRetentionTime;

		this.dataCache = new DataCache(RedisConnection.getInstance());
		this.cmdCache = new CommandCache(RedisConnection.getInstance());
		this.repositories = repositories;
		this.publishChanges = publishChanges;
		this.subscribeChanges = subscribeChanges;
		this.entityOnChange = entityOnChange;
	}

	public void startSubscription() {

		if (this.isSubscribeChanges()) {

			if ((new CommandCache(RedisConnection.getInstance()).isCommandCacheBin())) {

				this.entityBinarySubscriptorHnd = new EntityBinarySubscriptorHnd(this,
						Arrays.asList(this.getClazz().getSimpleName().getBytes()).toArray(byte[][]::new));

				CacheManager.getInstance().subscribe(this.entityBinarySubscriptorHnd);
			} else {

				this.entitySubscriptorHnd = new EntitySubscriptorHnd(this, this.getClazz().getSimpleName());

				CacheManager.getInstance().subscribe(this.entitySubscriptorHnd);
			}
		}
	}

	protected Connection getConnection() {

		if (this.connection == null) {

			try {

				this.connection = DataSourceConnection.getInstance().getConnection();

			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return this.connection;
	}

	protected EntityRepository getRepository(Class<?> clazz) {

		EntityRepository entityRepository = null;

		if (!CollectionUtils.isEmpty(getRepositories())) {

			entityRepository = getRepositories().stream().filter(repository -> repository.getClazz().equals(clazz))
					.findFirst().orElse(null);
		}

		return entityRepository;
	}

	private CustomMap getCustomMap(String... fieldNames) {

		CustomMap customMap = null;

		if (!ArrayUtils.isEmpty(fieldNames)) {

			synchronized (customMaps) {

				Iterator<CustomMap> itCustomMap = customMaps.iterator(); // Must be in synchronized block

				while (itCustomMap.hasNext() && customMap == null) {

					CustomMap customMapAux = itCustomMap.next();

					if (customMapAux.getName().equals(clazz.getSimpleName() + ":" + Arrays.toString(fieldNames))) {

						customMap = customMapAux;
					}
				}
			}

			if (customMap == null) {

				customMap = new CustomMap(bufferRententionTime, this.clazz, fieldNames);

				customMaps.add(customMap);
			}
		}

		return customMap;
	}

	private List<BasicElement> getFiltered(String[] fieldNames, String[] fieldValues, Boolean fillObject) {

		List<BasicElement> basicElementsList = null;
		List<BasicElement> basicElements = null;

		if (!ArrayUtils.isEmpty(fieldNames) && !ArrayUtils.isEmpty(fieldValues)
				&& fieldNames.length == fieldValues.length) {

			if (fieldNames.length == 1) {

				log.debug("Trying to load with index (" + fieldNames[0] + ") customCache " + clazz.getSimpleName()
						+ Arrays.toString(fieldNames) + " = " + Arrays.toString(fieldValues));
				basicElements = getsFromIndex(fieldNames[0], String.valueOf(fieldValues[0]), fillObject);

			} else if (fieldNames.length > 1) {

				basicElementsList = getsFromCache(fillObject);

				if (!CollectionUtils.isEmpty(basicElementsList)) {

					for (BasicElement basicElementFromList : basicElementsList) {

						boolean allFieldsAreEqual = true;

						for (int fieldIdx = 0; allFieldsAreEqual && fieldIdx < fieldNames.length; fieldIdx++) {

							try {

								String fieldName = fieldNames[fieldIdx];
								String fieldValue = fieldValues[fieldIdx];

								Field field = ClassesUtil.getField(clazz, fieldName);

								if (field != null) {

									field.setAccessible(true);

									Object fieldObjectValue = field.get(basicElementFromList);

									if (fieldObjectValue != null) {

										if (fieldObjectValue instanceof String) {

											allFieldsAreEqual &= fieldValue.equals(fieldObjectValue);

										} else if (fieldObjectValue instanceof Long) {

											allFieldsAreEqual &= fieldValue
													.equals(((Long) fieldObjectValue).toString());
										}
									} else {

										allFieldsAreEqual = false;
									}
								}

							} catch (IllegalArgumentException | IllegalAccessException e) {

								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));

							}
						}

						if (allFieldsAreEqual) {

							if (basicElements == null) {

								basicElements = new ArrayList<>();
							}

							basicElements.add(basicElementFromList);
						}
					}
				}
			}
		}

		return basicElements;
	}

	public List<BasicElement> getsFromCache(String[] fieldNames, String[] fieldValues, Boolean fillObject) {

		Map<String, BasicElement> basicElementsMap = null;
		List<BasicElement> basicElements = null;

		if (!ArrayUtils.isEmpty(fieldNames) && !ArrayUtils.isEmpty(fieldValues)
				&& fieldNames.length == fieldValues.length) {

			if (cacheable && bufferRententionTime > 0L) {

				CustomMap customMap = getCustomMap(fieldNames);

				if (customMap != null) {

					basicElementsMap = customMap.get(fieldValues);

					if (MapUtils.isEmpty(basicElementsMap)) {

						log.debug("Loading customCache for " + clazz.getSimpleName() + Arrays.toString(fieldNames)
								+ " = " + Arrays.toString(fieldValues));

						List<BasicElement> bes = getFiltered(fieldNames, fieldValues, fillObject);

						if (!CollectionUtils.isEmpty(bes)) {

							for (BasicElement be : bes) {

								customMap.put(fieldValues, be);

							}

							basicElementsMap = customMap.get(fieldValues);

							log.debug("Loaded " + basicElementsMap.size() + " items in customCache for "
									+ clazz.getSimpleName() + Arrays.toString(fieldNames) + " = "
									+ Arrays.toString(fieldValues));
						}
					}

					if (!MapUtils.isEmpty(basicElementsMap)) {

						basicElements = basicElementsMap.values().stream().collect(Collectors.toList());
					}
				}
			} else {

				basicElements = getFiltered(fieldNames, fieldValues, fillObject);
			}
		}

		return basicElements;
	}

	public BasicElement getFromCache(String[] ids, boolean fillObject) {

		Pair<BasicElement, Long> entityMap;
		BasicElement basicElement = null;

		if (cacheable && bufferRententionTime > 0L) {

			Key key = new Key(this.getClazz(), ids);

			entityMap = entitiesMap.get(key.getSimpleValue());

			if (entityMap != null && Duration.between(Instant.ofEpochMilli(entityMap.getRight()), Instant.now())
					.toMillis() < bufferRententionTime) {

				basicElement = entityMap.getLeft();
			}
		}

		if (basicElement == null && (!cacheable || bufferRententionTime < MAX_CACHE_RETENTION_TIME
				|| (MAX_CACHE_RETENTION_TIME.equals(bufferRententionTime) && !requestAll))) {

			Key keyPattern = new Key(clazz, null);

			for (int idIdx = 1; idIdx <= ids.length; idIdx++) {

				if (ids[idIdx - 1] != null) {

					keyPattern.setId(idIdx, String.valueOf(ids[idIdx - 1]));
				}
			}

			basicElement = this.dataCache.get(keyPattern);

			if (basicElement != null) {

				if (cacheable && bufferRententionTime > 0L) {

					buffer(basicElement);
				}
			}
		}

		if (basicElement != null && fillObject) {

			try {

				BasicElement beCloned = (BasicElement) basicElement.clone();

				basicElement = fillObject(beCloned);

			} catch (CloneNotSupportedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return basicElement;
	}

	private List<BasicElement> getsFromIndex(String fieldName, String fieldValue, boolean fillObject) {

		List<BasicElement> basicElements = null;

		Key keyPattern = new Key(clazz, null);

		if (keyPattern.hasFilterPatterns()) {

			BasicElement[] basicElementArray = this.dataCache.gets(clazz, fieldName, fieldValue);

			if (!ArrayUtils.isEmpty(basicElementArray)) {

				basicElements = Arrays.asList(basicElementArray);

				if (!CollectionUtils.isEmpty(basicElements)) {

					if (cacheable && bufferRententionTime > 0L) {

						buffer(basicElements);
					}

					if (fillObject) {

						List<BasicElement> besCloned = basicElements.stream().map(e -> {

							try {

								return (BasicElement) e.clone();

							} catch (CloneNotSupportedException e1) {

								log.error(e1.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e1));
							}

							return null;

						}).collect(Collectors.toList());

						if (!CollectionUtils.isEmpty(besCloned)) {

							basicElements = fillObject(besCloned);
						}
					}
				}
			}
		}

		return basicElements;
	}

	public List<BasicElement> getsFromCache(boolean fillObject) {

		List<BasicElement> basicElements = null;

		Key keyPattern = new Key(clazz, null);

		basicElements = getsFromPattern(keyPattern, fillObject);

		requestAll = true;

		return basicElements;
	}

	public List<BasicElement> getsFromCache(String[] ids, boolean fillObject) {

		List<BasicElement> basicElements = null;

		Key keyPattern = new Key(clazz, null);

		for (int idIdx = 1; idIdx <= ids.length; idIdx++) {

			if (ids[idIdx - 1] != null) {

				keyPattern.setId(idIdx, ids[idIdx - 1]);
			}
		}

		basicElements = getsFromPattern(keyPattern, fillObject);

		return basicElements;
	}

	public Set<Key> getKeys(Key keyPattern) {

		// Instant instant = Instant.now();
		Set<Key> keys = Collections.emptySet();

		if (keyPattern.hasFilterPatterns()) {

			keys = this.dataCache.keys(keyPattern.getValue());
		}

		// log.debug("getKeys for " + keyPattern.getSimpleValue() + ": " +
		// Duration.between(instant, Instant.now()).toMillis() + " ms.");

		return keys;
	}

	private List<BasicElement> getsFromPattern(Key keyPattern, boolean fillObject) {

		List<BasicElement> basicElements = new ArrayList<>();
		BasicElement[] basicElementsToBuffer = null;
		List<Key> keysToRequest = new ArrayList<>();

		boolean hasFilterPatterns = keyPattern.hasFilterPatterns();

		if (hasFilterPatterns) {

			Set<Key> dbKeys = getDbKeys(keyPattern.getSimpleValue());

			if (!CollectionUtils.isEmpty(dbKeys)) {

				if (cacheable && bufferRententionTime > 0L) {

					synchronized (dbKeysMap) {

						Iterator<Key> itDbKeys = dbKeys.iterator();

						while (itDbKeys.hasNext()) {

							Key dbKey = itDbKeys.next();

							Pair<BasicElement, Long> item = entitiesMap.get(dbKey.getSimpleValue());

							BasicElement be = null;

							if (item != null && item.getLeft() != null && item.getRight() != null) {

								boolean isUpdated = (Duration
										.between(Instant.ofEpochMilli(item.getValue().longValue()), Instant.now())
										.toMillis() < bufferRententionTime);

								if (isUpdated) {

									be = item.getLeft();

									basicElements.add(be);

								} else {

									keysToRequest.add(dbKey);

								}

							} else {

								keysToRequest.add(dbKey);
							}

						}
					}

					if (!CollectionUtils.isEmpty(keysToRequest)) {

						basicElementsToBuffer = this.dataCache.gets(keysToRequest.toArray(Key[]::new));

						if (!ArrayUtils.isEmpty(basicElementsToBuffer)) {

							basicElements.addAll(Arrays.stream(basicElementsToBuffer).collect(Collectors.toList()));
						}

					}
				} else {

					BasicElement[] basicElementArray = this.dataCache.gets(keyPattern);

					if (!ArrayUtils.isEmpty(basicElementArray)) {

						basicElements = Arrays.asList(basicElementArray);
					}
				}
			}

			if (!CollectionUtils.isEmpty(basicElements)) {

				if (!ArrayUtils.isEmpty(basicElementsToBuffer)) {

					buffer(Arrays.stream(basicElementsToBuffer).collect(Collectors.toList()));
				}

				if (fillObject && !CollectionUtils.isEmpty(basicElements)) {

					List<BasicElement> besCloned = basicElements.stream().map(e -> {

						try {

							return (BasicElement) e.clone();

						} catch (CloneNotSupportedException e1) {

							log.error(e1.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e1));
						}

						return null;

					}).collect(Collectors.toList());

					if (!CollectionUtils.isEmpty(besCloned)) {

						basicElements = fillObject(besCloned);
					}
				}
			}
		}

		return basicElements;
	}

	public List<BasicElement> putsFromCache(List<BasicElement> basicElements) {

		if (!ArrayUtils.isEmpty(this.dataCache.puts(basicElements.toArray(BasicElement[]::new)))) {
			
			ChangeEntityRepositoryCommand changeEntityRepositoryCommand = null;

			if (this.publishChanges) {

				changeEntityRepositoryCommand = getChangeEntityRepositoryCommand(basicElements, PersistOperation.PUT);
			}
			
			if (cacheable && bufferRententionTime > 0L) {

				buffer(basicElements);
			}
			
			if (changeEntityRepositoryCommand != null) {

				publish(this.getClazz().getSimpleName(), changeEntityRepositoryCommand);
			}

			return basicElements;
		}

		return Collections.emptyList();
	}

	public BasicElement putFromCache(BasicElement basicElement) {

		if (basicElement != null) {

			if (this.dataCache.put(basicElement) != null) {
				
				ChangeEntityRepositoryCommand changeEntityRepositoryCommand = null;
				
				if (this.publishChanges) {

					 changeEntityRepositoryCommand = getChangeEntityRepositoryCommand(basicElement, PersistOperation.PUT);
				}
				
				if (cacheable && bufferRententionTime > 0L) {
				
					buffer(basicElement);
				}

				if (changeEntityRepositoryCommand != null) {

					publish(this.getClazz().getSimpleName(), changeEntityRepositoryCommand);
				}
			}

			return basicElement;
		}

		return null;
	}

	public BasicElement delFromCache(BasicElement basicElement) {

		Key keyPattern = new Key(basicElement);
		BasicElement basicElementRes = null;

		if (!keyPattern.hasFilterPatterns() && this.dataCache.del(new Key(basicElement)) != null) {

			ChangeEntityRepositoryCommand changeEntityRepositoryCommand = null;
			
			if (this.publishChanges) {

				 changeEntityRepositoryCommand = getChangeEntityRepositoryCommand(basicElement, PersistOperation.DELETE);
			}
			
			if (cacheable && bufferRententionTime > 0L) {

				unbuffer(basicElement);
			}
			
			if (changeEntityRepositoryCommand != null) {

				publish(this.getClazz().getSimpleName(), changeEntityRepositoryCommand);
			}

			basicElementRes = basicElement;
		}

		return basicElementRes;
	}

	public List<BasicElement> delFromCache(List<BasicElement> basicElements) {

		List<BasicElement> basicElementsRes = null;

		if (!CollectionUtils.isEmpty(basicElements)) {

			basicElementsRes = new ArrayList<>();

			for (BasicElement basicElement : basicElements) {

				basicElementsRes.add(delFromCache(basicElement));
			}

			if (!CollectionUtils.isEmpty(basicElementsRes)) {
				
				ChangeEntityRepositoryCommand changeEntityRepositoryCommand = null;
				
				if (this.publishChanges) {
									
					changeEntityRepositoryCommand = getChangeEntityRepositoryCommand(basicElementsRes, PersistOperation.DELETE);
				}
				
				if (cacheable && bufferRententionTime > 0L) {
				
					unbuffer(basicElementsRes);
				}

				if (changeEntityRepositoryCommand != null) {

					publish(this.getClazz().getSimpleName(), changeEntityRepositoryCommand);
				}
			}
		}

		return basicElementsRes;
	}

	public void delAllFromCache() {

		dataCache.del(new Key(this.getClazz(), null));
		entitiesMap.clear();
		dbKeysMap.clear();
	}

	private BasicElement fillObject(BasicElement basicElement) {

		BasicElement be = null;

		Instant timePoint = Instant.now();

		be = (BasicElement) CacheModelHelper.fillObject(basicElement);

		log.debug("fillObject (1) = " + Duration.between(timePoint, Instant.now()).toMillis() + " ms.");

		return be;
	}

	private List<BasicElement> fillObject(List<BasicElement> basicElements) {

		List<BasicElement> basicElementsFilled = null;

		if (!CollectionUtils.isEmpty(basicElements)) {

			Instant timePoint = Instant.now();

			basicElementsFilled = new ArrayList<>();

			for (BasicElement basicElement : basicElements) {

				basicElementsFilled.add((BasicElement) CacheModelHelper.fillObject(basicElement));
			}

			log.debug("fillObject (" + basicElements.size() + ") = "
					+ Duration.between(timePoint, Instant.now()).toMillis() + " ms.");
		}

		return basicElementsFilled;
	}

	private void buffer(List<BasicElement> basicElements) {

		if (!CollectionUtils.isEmpty(basicElements)) {

			for (BasicElement basicElement : basicElements) {

				if (basicElement.getClass() == this.clazz) {

					buffer(basicElement);
				}
			}
		}
	}

	private void buffer(BasicElement basicElement) {

		if (basicElement != null && basicElement.getClass() == this.clazz) {

			Key keyResult = new Key(basicElement);

			entitiesMap.put(keyResult.getSimpleValue(), new MutablePair<>(basicElement, Instant.now().toEpochMilli()));

			synchronized (dbKeysMap) {

				Iterator<Map.Entry<String, Pair<Set<Key>, Long>>> itDbKeysMap = dbKeysMap.entrySet().iterator();

				while (itDbKeysMap.hasNext()) {

					Map.Entry<String, Pair<Set<Key>, Long>> entry = itDbKeysMap.next();

					// for (Map.Entry<String, Pair<Set<Key>, Long>> entry : dbKeysMap.entrySet()) {

					String keyPattern = entry.getKey();
					Pair<Set<Key>, Long> pair = entry.getValue();

					if (keyResult.match(new Key(keyPattern))) {

						Set<Key> keys = pair.getLeft();

						if (keys == null) {

							// keys = Collections.synchronizedSet(new HashSet<>());
							keys = new HashSet<>();

							entry.setValue(new MutablePair<>(keys, Instant.now().toEpochMilli()));

							keys.add(keyResult);
						} else if (!keys.contains(keyResult)) {

							keys.add(keyResult);
							pair.setValue(Instant.now().toEpochMilli());
						}
					}
				}
			}
		}
	}

	private void unbuffer(List<BasicElement> basicElements) {

		if (!CollectionUtils.isEmpty(basicElements)) {

			for (BasicElement basicElement : basicElements) {

				if (basicElement.getClass() == this.clazz) {

					unbuffer(basicElement);
				}
			}
		}
	}

	private void unbuffer(BasicElement basicElement) {

		if (basicElement != null && basicElement.getClass() == this.clazz) {

			Key keyResult = new Key(basicElement);

			entitiesMap.remove(keyResult.getSimpleValue());

			synchronized (dbKeysMap) {

				Iterator<Map.Entry<String, Pair<Set<Key>, Long>>> itDbKeysMap = dbKeysMap.entrySet().iterator();

				while (itDbKeysMap.hasNext()) {

					Map.Entry<String, Pair<Set<Key>, Long>> entry = itDbKeysMap.next();

					// for (Map.Entry<String, Pair<Set<Key>, Long>> entry : dbKeysMap.entrySet()) {

					String keyPattern = entry.getKey();
					Pair<Set<Key>, Long> pair = entry.getValue();

					if (keyResult.match(new Key(keyPattern))) {

						Set<Key> keys = pair.getLeft();

						if (!CollectionUtils.isEmpty(keys) && keys.contains(keyResult)) {

							keys.remove(keyResult);
							pair.setValue(Instant.now().toEpochMilli());

							if (keys.isEmpty()) {

								entry.setValue(new MutablePair<>(null, Instant.now().toEpochMilli()));
							}
						}
					}
				}
			}
		}
	}

	private ChangeEntityRepositoryCommand getChangeEntityRepositoryCommand(BasicElement basicElement,
			PersistOperation persistOpetation) {

		ChangeEntityRepositoryCommand changeEntityRepositoryCommand = null;

		if (basicElement != null && basicElement.getClass() == this.clazz && persistOpetation != null) {

			changeEntityRepositoryCommand = new ChangeEntityRepositoryCommand();

			if (persistOpetation == PersistOperation.PUT) {

				Key keyResult = new Key(basicElement);

				Pair<BasicElement, Long> pair = entitiesMap.get(keyResult.getSimpleValue());

				if (pair != null) {

					changeEntityRepositoryCommand.add(basicElement, Command.ACTION_UPDATE);
				} else {

					changeEntityRepositoryCommand.add(basicElement, Command.ACTION_CREATE);
				}
			} else if (persistOpetation == PersistOperation.DELETE) {

				changeEntityRepositoryCommand.add(basicElement, Command.ACTION_DELETE);
			}
		}

		return changeEntityRepositoryCommand;
	}

	private ChangeEntityRepositoryCommand getChangeEntityRepositoryCommand(List<BasicElement> basicElements,
			PersistOperation persistOperation) {

		ChangeEntityRepositoryCommand changeEntityRepositoryCommand = null;

		if (!CollectionUtils.isEmpty(basicElements) && persistOperation != null) {

			changeEntityRepositoryCommand = new ChangeEntityRepositoryCommand();

			for (BasicElement basicElement : basicElements) {

				ChangeEntityRepositoryCommand changeEntityRepositoryCommandItem = getChangeEntityRepositoryCommand(
						basicElement, persistOperation);

				changeEntityRepositoryCommand.add(changeEntityRepositoryCommandItem);
			}
		}

		return changeEntityRepositoryCommand;
	}

	public Command publish(Command command) {

		if (command != null && this.cmdCache.publish(command) != null) {

			return command;
		}

		return null;
	}

	public Command publish(String channel, Command command) {

		if (command != null && this.cmdCache.publish(channel, command) != null) {

			return command;
		}

		return null;
	}

	@Deprecated
	public void persist(List<Pair<PersistOperation, BasicElement>> persistOperations) {

		PersistOperation lastOperation = null;
		List<BasicElement> basicElements = new ArrayList<>();

		for (Pair<PersistOperation, BasicElement> operation : persistOperations) {

			if (lastOperation != null && !lastOperation.equals(operation.getLeft())
					&& !CollectionUtils.isEmpty(basicElements)) {

				switch (lastOperation) {

				case PUT:
					putsFromCache(basicElements);
					break;

				case DELETE:
					delFromCache(basicElements);
					break;

				default:
					break;
				}

				basicElements.clear();
			}
			basicElements.add(operation.getRight());
			lastOperation = operation.getLeft();
		}

		if (!CollectionUtils.isEmpty(basicElements) && lastOperation != null) {

			switch (lastOperation) {

			case PUT:
				putsFromCache(basicElements);
				break;

			case DELETE:
				delFromCache(basicElements);
				break;

			default:
				break;
			}
		}

	}

	public void persistOperations(List<Pair<PersistOperation, BasicElement>> persistOperations) {

		Map<Pair<PersistOperation, Class<?>>, List<BasicElement>> map = new HashedMap<>();

		for (Pair<PersistOperation, BasicElement> operation : persistOperations) {

			BasicElement basicElement = operation.getRight();

			Pair<PersistOperation, Class<?>> pair = new MutablePair<>(operation.getLeft(), basicElement.getClass());

			List<BasicElement> basicElements = map.computeIfAbsent(pair, v -> new ArrayList<>());
			basicElements.add(basicElement);
		}
		
		if (!map.isEmpty()) {
			
			makeOperations(map, PersistOperation.DELETE);
			makeOperations(map, PersistOperation.PUT);
		}
	}
	
	private void makeOperations(Map<Pair<PersistOperation, Class<?>>, List<BasicElement>> map, PersistOperation persistOperation) {
		
		if (!map.isEmpty()) {
			
			List<Pair<PersistOperation, Class<?>>> operationsToDelete = new ArrayList<>();

			map.forEach((pair, basicElements) -> {

				if (pair.getLeft() == persistOperation) {
					
					if (pair.getValue().equals(this.getClazz())) {

						if (pair.getLeft() == PersistOperation.DELETE) {
							
							delFromCache(basicElements);
						}
						else if (pair.getLeft() == PersistOperation.PUT) {
							
							putsFromCache(basicElements);
						}
						
					} else if (!CollectionUtils.isEmpty(repositories)) {
						
						EntityRepository entityRepository = getRepository(pair.getValue());

						if (entityRepository != null) {

							if (pair.getLeft() == PersistOperation.DELETE) {
								
								entityRepository.delFromCache(basicElements);
							}
							else if (pair.getLeft() == PersistOperation.PUT) {
								
								entityRepository.putsFromCache(basicElements);
							}
						}
					}
					
					operationsToDelete.add(pair);
				}
				
			});
			
			if (!CollectionUtils.isEmpty(operationsToDelete)) {
				
				operationsToDelete.forEach(map::remove);
			}
		}
	}
	
	public void onReceiveFromChannel(Class<? extends Command> clazz, String message) {

		Object objFromChannel;

		try {

			objFromChannel = CacheManager.getInstance().getMessage(clazz, message);

			if (objFromChannel != null) {

				if (objFromChannel instanceof Command) {

					// TODO
				} else {

					throw new Exception("Unknow object " + objFromChannel.getClass().getSimpleName()
							+ " from channel = " + clazz.getSimpleName());
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	public void onReceiveFromChannel(byte[] channel, byte[] message) {

		Object objFromChannel;
		String channelStr = null;

		try {

			objFromChannel = CacheManager.getInstance().getMessage(message);
			channelStr = new String(channel);

			if (objFromChannel != null) {

				if (objFromChannel instanceof ChangeEntityRepositoryCommand) {

					ChangeEntityRepositoryCommand changeEntityRepositoryCommand = (ChangeEntityRepositoryCommand) objFromChannel;

					if (!changeEntityRepositoryCommand.getGeneratedBy()
							.equals(Thread.currentThread().getName() + "_" + Thread.currentThread().getId())) {

						List<Pair<BasicElement, Integer>> basicElementActions = changeEntityRepositoryCommand
								.getBasicElementActions();

						if (!CollectionUtils.isEmpty(basicElementActions)) {

							for (Pair<BasicElement, Integer> basicElementAction : basicElementActions) {

								BasicElement basicElement = basicElementAction.getLeft();
								Integer action = basicElementAction.getRight();

								changeEntity(basicElement, action);
							}
						}
					}
				} else {

					throw new Exception("Unknow object " + objFromChannel.getClass().getSimpleName()
							+ " from channel = " + channelStr);
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void changeEntity(BasicElement basicElement, Integer action) {

		if (action == Command.ACTION_CREATE) {

			buffer(basicElement);
			
		} else if (action == Command.ACTION_UPDATE) {

			updateEntity(basicElement);
			
		} else if (action == Command.ACTION_DELETE) {

			unbuffer(basicElement);
		}

		if (entityOnChange != null) {

			try {

				entityOnChange.setBasicElement(basicElement);
				entityOnChange.setAction(action);

				entityOnChange.call();

			} catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	private void updateEntity(BasicElement newBasicElement) {

		if (newBasicElement != null && newBasicElement.getClass() == this.clazz) {

			Key keyResult = new Key(newBasicElement);

			Pair<BasicElement, Long> pair = entitiesMap.get(keyResult.getSimpleValue());

			if (pair != null) {

				BasicElement currentBasicElement = pair.getLeft();
				BasicElement newBasicElementAux = (BasicElement) CacheModelHelper
						.cloneBasicElementForPut(currentBasicElement);

				CacheModelHelper.updateBasicElement(newBasicElement, newBasicElementAux);

				buffer(newBasicElementAux);
			}
		}
	}

	public <T> List<T> getAll_(Boolean fillObject) {
		
		Class<T> classOfBasicElement = (Class<T>) this.clazz;
		
		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(classOfBasicElement::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public <T> T put_(T entity) {
		
		return (T) putFromCache((BasicElement) entity);
	}

	public <T> List<T> put_(List<T> entities) {

		return (List<T>) putsFromCache(entities.stream().map(BasicElement.class::cast).collect(Collectors.toList()));
	}
	
	public <T> T del_(T entity) {

		return (T) delFromCache((BasicElement) entity);

	}

	public <T> List<T> del_(List<T> entities) {
		
		return (List<T>) delFromCache(entities.stream().map(BasicElement.class::cast).collect(Collectors.toList()));
	}
	
}
