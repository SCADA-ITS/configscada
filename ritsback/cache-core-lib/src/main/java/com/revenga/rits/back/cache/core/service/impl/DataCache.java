package com.revenga.rits.back.cache.core.service.impl;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.helper.CacheModelHelper;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Idx;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.CacheService;
import com.revenga.rits.back.data.core.annotation.IndexKey;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

@Log4j2
public class DataCache implements CacheService <BasicElement, Key> {

	private RedisConnection redisConnection;
	private String modifiedBy;
	private static final String REDIS_PROPERTIES = "redis.properties";
		
	public static final int DATACACHE_TYPE_HSET = 1;
	public static final int DATACACHE_TYPE_JSON = 2;
	public static final int DATACACHE_TYPE_BIN = 3;
	
	public static final int DEFAULT_SCAN_COUNT = 1000;
	
	@Getter
	@Setter
	private int dataCacheType = DATACACHE_TYPE_HSET;
	private int scanCount = DEFAULT_SCAN_COUNT;
	
	public DataCache(RedisConnection redisConnection) {

		super();
		this.redisConnection = redisConnection;
		this.modifiedBy = ManagementFactory.getRuntimeMXBean().getName() + " (" + System.getProperty("sun.java.command") + ")";
	
		this.dataCacheType = Integer
				.parseInt(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.datacache_type")); 
		
		this.scanCount = Integer
				.parseInt(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.scan_count"));
		
	}
	
	private Idx[] put(Idx[] idxs, Key key) {
		
		List<Idx> idxList = new ArrayList<>();
		
		if (idxs != null && idxs.length > 0) {
			
			try (Jedis jedis = redisConnection.getConnectionPool(key.getClazz()).getResource()) {
				
				for (Idx idx : idxs) {
				
					if (dataCacheType == DATACACHE_TYPE_BIN) {
						
						if (jedis.sadd(idx.getValue().getBytes(), key.getValueForPut().getBytes()) != 0) {
							
							idxList.add(idx);		
						}
					}
					else {
					
						if (jedis.sadd(idx.getValue(), key.getValueForPut()) != 0) {
							
							idxList.add(idx);		
						}
					}
				}
			}
		}
		
		return idxList.stream().toArray(Idx[]::new);
	}
	
	private Idx[] delIdxs(BasicElement basicElement) {

		List<Idx> idxList = new ArrayList<>();
		
		Idx[] idxs = CacheModelHelper.getIndexes(basicElement);
		
		if (!ArrayUtils.isEmpty(idxs)) {
			
			for (Idx idx : idxs) {
				
				try (Jedis jedis = redisConnection.getConnectionPool(basicElement.getClass()).getResource()) {

					if (dataCacheType == DATACACHE_TYPE_BIN) {
					
						if (jedis.srem(idx.getValue().getBytes(), (new Key(basicElement).getValueForPut()).getBytes()) != 0) {
							
							idxList.add(idx);
						}
					}
					else {
						
						if (jedis.srem(idx.getValue(), (new Key(basicElement).getValueForPut())) != 0) {
							
							idxList.add(idx);
						}
					}
				}
			}
		}
		
		return idxList.stream().toArray(Idx[]::new);
	}
	
	@Override
	public Key put(BasicElement basicElement) {
		
		Key key = null;
		String keyValue = null;
		
		try (Jedis jedis = redisConnection.getConnectionPool(basicElement.getClass()).getResource()) {

			basicElement.setUpdatedAt(Instant.now().toEpochMilli());
			
			key = new Key(basicElement);
			
			if (!key.hasFilterPatterns()) {
			
				keyValue = key.getValueForPut();
				
				if (dataCacheType == DATACACHE_TYPE_HSET) {

					Map<String, String> hash = CacheModelHelper.getHash(basicElement);
					
					if (!MapUtils.isEmpty(hash)) {
						
						if (jedis.exists(keyValue).booleanValue()) {
						
							List<String> valuesInHashToDel = hash.entrySet().stream()
									  .filter(e -> e.getValue() == null)
									  .map(Map.Entry::getKey)
									  .collect(Collectors.toList());
							
							if (!CollectionUtils.isEmpty(valuesInHashToDel)) {
							
								jedis.hdel(keyValue, valuesInHashToDel.toArray(String[]::new));
							}
						}
						
						put(CacheModelHelper.getIndexes(basicElement), key);
						
						Map<String, String> valuesInHashToPut = hash.entrySet().stream().filter(e -> e.getValue() != null)
								.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
						
						jedis.hset(keyValue, valuesInHashToPut);
					}
				}
				else if (dataCacheType == DATACACHE_TYPE_JSON) {
					
					String json = CacheModelHelper.getJson(basicElement);
					
					if (!StringUtils.isEmpty(json)) {
					
						put(CacheModelHelper.getIndexes(basicElement), key);
						
						jedis.set(keyValue, json);
					}
				}
				else if (dataCacheType == DATACACHE_TYPE_BIN) {

					byte[] k = keyValue.getBytes();
					byte[] v = CacheModelHelper.getBin(basicElement);
					
					put(CacheModelHelper.getIndexes(basicElement), key);
					
					jedis.set(k, v);
				}
			}
			else {
			
				log.error(key.getValue() + " contains filter patterns. Do not insert value with filter patterns");
			}

        } catch (Exception e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			key = null;
        }
		
		return key;
	}
	
	@Override
	public Key[] puts(BasicElement[] basicElements) {
		
		List<Key> resultKeys = new ArrayList<>();
		
		resultKeys.addAll(Arrays.stream(putsUnClassified(basicElements)).collect(Collectors.toList()));
		
		return resultKeys.stream().toArray(Key[]::new);
	}
	
	private Key[] putsUnClassified(BasicElement[] basicElements) {
		
		List<Key> keys = new ArrayList<>();
		Map<Class<?>, List<BasicElement>> mapClassified = new HashMap<>();
		
		if (ArrayUtils.isEmpty(basicElements)) {
			
			return keys.stream().toArray(Key[]::new);
		}
		
		for (BasicElement basicElement : basicElements) {
			
			List<BasicElement> bes = mapClassified.computeIfAbsent(basicElement.getClass(), k -> new ArrayList<>());
			
			bes.add(basicElement);
		}
		
		for (Map.Entry<Class<?>, List<BasicElement>> entry : mapClassified.entrySet()) {
			
			List<BasicElement> bes = entry.getValue();
			
			Key[] keysPartial = putsClassified(bes.stream().toArray(BasicElement[]::new));
			
			keys.addAll(Arrays.asList(keysPartial));
		}
		
		return keys.stream().toArray(Key[]::new);
	}
	
	private Key[] putsClassified(BasicElement[] basicElements) {
		
		List<Key> keys = new ArrayList<>();
		List<String> keyValues = new ArrayList<>();
		List<byte[]> keyValuesBin = new ArrayList<>();
		List<String> jsonList = null;
		
		if (ArrayUtils.isEmpty(basicElements)) {
			
			return keys.stream().toArray(Key[]::new);
		}
		
		
		try (Jedis jedis = redisConnection.getConnectionPool(basicElements[0].getClass()).getResource()) {
			
			if (dataCacheType == DATACACHE_TYPE_JSON) {
				
				String strArrayJson = CacheModelHelper.getJson(basicElements);
				
				JsonNode jsonNode = new ObjectMapper().readTree(strArrayJson);
				jsonList = StreamSupport.stream(jsonNode.spliterator(), false).map(JsonNode::toString)
						.collect(Collectors.toList());
			}
			
			int idxJson = 0;
			
			for (BasicElement basicElement : basicElements) {
				
				basicElement.setUpdatedAt(Instant.now().toEpochMilli());
				
				Key key = new Key(basicElement);
				
				if (!key.hasFilterPatterns()) {
				
					String keyValue = key.getValueForPut();
					
					if (dataCacheType == DATACACHE_TYPE_HSET) {
					
						Map<String, String> hash = CacheModelHelper.getHash(basicElement);
						
						if (!MapUtils.isEmpty(hash)) {
							
							if (jedis.exists(keyValue).booleanValue()) {
							
								List<String> valuesInHashToDel = hash.entrySet().stream()
										  .filter(e -> e.getValue() == null)
										  .map(Map.Entry::getKey)
										  .collect(Collectors.toList());
								
								if (!CollectionUtils.isEmpty(valuesInHashToDel)) {
								
									jedis.hdel(keyValue, valuesInHashToDel.toArray(String[]::new));
								}

							}
							
							put(CacheModelHelper.getIndexes(basicElement), key);
							
							Map<String, String> valuesInHashToPut = hash.entrySet().stream().filter(e -> e.getValue() != null)
									.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
							
							jedis.hset(keyValue, valuesInHashToPut);
													
							keys.add(key);
						}
					}
					else if (dataCacheType == DATACACHE_TYPE_JSON && !CollectionUtils.isEmpty(jsonList)) {
						
//							String json = CacheModelHelper.getJson(basicElement);
						String json = jsonList.get(idxJson++);
						
						if (!StringUtils.isEmpty(json)) {
							
							put(CacheModelHelper.getIndexes(basicElement), key);
							
							keyValues.add(keyValue);
							keyValues.add(json);
													
							keys.add(key);
						}
					}
					else if (dataCacheType == DATACACHE_TYPE_BIN) {
									
						put(CacheModelHelper.getIndexes(basicElement), key);
						
						keyValuesBin.add(keyValue.getBytes());
						keyValuesBin.add(CacheModelHelper.getBin(basicElement));
													
						keys.add(key);
					}
				}
				else {
					
					log.error(key.getValue() + " contains filter patterns. Do not insert value with filter patterns");
				}
			}
			
			if (dataCacheType == DATACACHE_TYPE_JSON && !CollectionUtils.isEmpty(keyValues) && keyValues.size() % 2 == 0) {
				
				jedis.mset(keyValues.toArray(String[]::new));
			}
			
			if (dataCacheType == DATACACHE_TYPE_BIN && !CollectionUtils.isEmpty(keyValuesBin) && keyValuesBin.size() % 2 == 0) {
				
				jedis.mset(keyValuesBin.toArray(byte[][]::new));
			}

		} catch (Exception e){
            
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
		return keys.stream().toArray(Key[]::new);
		
	}
	
	
	@Override
	public BasicElement get(Key key) {

		BasicElement basicElement = null;
		
		if (!key.getValue().contains("*")) {
		
			try (Jedis jedis = redisConnection.getConnectionPool(key.getClazz()).getResource()) {
				
				if (dataCacheType == DATACACHE_TYPE_HSET) {
				
					Map<String, String> hash = jedis.hgetAll(key.getValue());
					
					if (!MapUtils.isEmpty(hash)) {
						
						basicElement = (BasicElement) CacheModelHelper.getObject(key.getClazz(), hash);
					}
				}
				else if (dataCacheType == DATACACHE_TYPE_JSON) {
					
					String json = jedis.get(key.getValue());
					
					if (!StringUtils.isEmpty(json)) {
						
						basicElement = (BasicElement) CacheModelHelper.getObject(key.getClazz(), json);
					}
				}
				else if (dataCacheType == DATACACHE_TYPE_BIN) {
					
					byte[] v = jedis.get(key.getValue().getBytes());
					
					if (v != null) {
						
						basicElement = SerializationUtils.deserialize(v);
					}
				}

	        } catch (Exception e){
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
	        }
		}
		else {
			
			BasicElement[]  basicElements = gets(key);
			
			if (!ArrayUtils.isEmpty(basicElements)) {
				
				basicElement = basicElements[0];
			}
		}
		
		return basicElement;
	}
	
	@Override
	public BasicElement[] gets(Key key) {
		
		BasicElement[] basicElements = null;
		
		if (dataCacheType == DATACACHE_TYPE_HSET) {
			
			basicElements = getsHSet(key);
		}
		else if (dataCacheType == DATACACHE_TYPE_JSON) {
			
			basicElements = getsJson(key);
		}
		else if (dataCacheType == DATACACHE_TYPE_BIN) {
			
			basicElements = getsBin(key);
		}	
		
		return basicElements;
	}
	
	public BasicElement[] gets(Key[] keys) {
		
		BasicElement[] basicElements = null;
		
		if (dataCacheType == DATACACHE_TYPE_HSET) {
			
			basicElements = getArrayHSet(keys);
		}
		else if (dataCacheType == DATACACHE_TYPE_JSON) {
			
			basicElements = getArrayJson(keys);
		}
		else if (dataCacheType == DATACACHE_TYPE_BIN) {
			
			basicElements = getArrayBin(keys);
		}	
		
		return basicElements;
	}
	
	private BasicElement[] getArrayHSet(Key[] keys) {
		
		List<BasicElement> basicElements = new ArrayList<>();
		BasicElement basicElement = null;
		
		if (!ArrayUtils.isEmpty(keys)) {
			
			for (Key key : keys) {
				
				if (!key.hasFilterPatterns()) {
					
					basicElement = get(key);
					
					if (basicElement != null) {
						
						basicElements.add(basicElement);
					}
				}
			}
		}
		
		return basicElements.toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getArrayJson(Key[] keys) {
		
		List<BasicElement> basicElements = new ArrayList<>();
		
		if (!ArrayUtils.isEmpty(keys)) {
			
			try (Jedis jedis = redisConnection.getConnectionPool(keys[0].getClazz()).getResource()) {
		    	
				Class<?> classRef = keys[0].getClazz();
				
		    	List<String> keyValues = Arrays.stream(keys)
		    			.filter(e -> e.getClazz() == classRef && !e.hasFilterPatterns())
		    			.map(e ->  e.getValue())
		    			.collect(Collectors.toList());
		    	
		    	if (!CollectionUtils.isEmpty(keyValues)) {
	    	    	
		    		List<String> jsons = jedis.mget(keyValues.toArray(String[]::new));
		    				    	
			    	if (!CollectionUtils.isEmpty(jsons)) {
			    		
			    		basicElements = CacheModelHelper.getObjects(classRef, jsons).stream().collect(Collectors.toList());
			    	}
		    	}
				
			} catch (Exception e){
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
	        }
		}
		
		return basicElements.toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getArrayBin(Key[] keys) {
		
		List<BasicElement> basicElements = new ArrayList<>();
		
		if (!ArrayUtils.isEmpty(keys)) {
			
			try (Jedis jedis = redisConnection.getConnectionPool(keys[0].getClazz()).getResource()) {
		    	
		    	List<byte[]> keyValues = Arrays.stream(keys)
		    			.filter(e -> !e.hasFilterPatterns())
		    			.map(e ->  e.getValue().getBytes())
		    			.collect(Collectors.toList());
		    	
		    	if (!CollectionUtils.isEmpty(keyValues)) {
		    		
		    		List<byte[]> valuesBin = jedis.mget(keyValues.toArray(byte[][]::new));
		    		    				    	
			    	if (!CollectionUtils.isEmpty(valuesBin)) {
			    		
			    		basicElements = valuesBin.stream()
			    				.filter(e -> e != null)
			    				.map(e -> (BasicElement) SerializationUtils.deserialize(e))
			    				.collect(Collectors.toList());
			    	}
		    	}
				
			} catch (Exception e){
	            
	        	log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
	        }
		}
		
		return basicElements.toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getsHSet(Key key) {
		
		Key[] keys = find(key);
	    List<BasicElement> basicElements = new ArrayList<>();
	    
	    if (!ArrayUtils.isEmpty(keys)) {
	    
	    	for (Key keyItem : keys) {
				
		    	BasicElement basicElement = get(keyItem);
	        	
	        	if (basicElement != null) {
	        		
	        		basicElements.add(basicElement);
	        	}
			}
	    }
		
	    return basicElements.stream().toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getsJson(Key key) {
		
		Key[] keys = find(key);
	    List<BasicElement> basicElements = new ArrayList<>();
	    
	    
	    try (Jedis jedis = redisConnection.getConnectionPool(key.getClazz()).getResource()) {
	    	
	    	List<String> keyValues = new ArrayList<>();
	    	
	    	if (!ArrayUtils.isEmpty(keys)) {
	    	
	    		for (Key keyItem : keys) {
					
	    			keyValues.add(keyItem.getValue());
				}
	    	}
	    	
	    	if (!CollectionUtils.isEmpty(keyValues)) {
	    	
	    		List<String> jsons = jedis.mget(keyValues.toArray(String[]::new));
	    				    	
		    	if (!CollectionUtils.isEmpty(jsons)) {
		    		
		    		basicElements.addAll(CacheModelHelper.getObjects(key.getClazz(), jsons));
		    	}
	    	}
			
		} catch (Exception e){
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
	    return basicElements.stream().toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getsBin(Key key) {
		
		Key[] keys = find(key);
	    List<BasicElement> basicElements = null;
	    
	    
	    try (Jedis jedis = redisConnection.getConnectionPool(key.getClazz()).getResource()) {
	    	
	    	List<byte[]> keyValues = Arrays.stream(keys)
	    			.map(e ->  e.getValue().getBytes())
	    			.collect(Collectors.toList());
	    	
	    	if (!CollectionUtils.isEmpty(keyValues)) {
	    		
	    		List<byte[]> valuesBin = jedis.mget(keyValues.toArray(byte[][]::new));
	    		    				    	
		    	if (!CollectionUtils.isEmpty(valuesBin)) {
		    		
		    		basicElements = valuesBin.stream()
		    				.filter(e -> e != null)
		    				.map(e -> (BasicElement) SerializationUtils.deserialize(e))
		    				.collect(Collectors.toList());
		    	}
	    	}
			
		} catch (Exception e){
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
	    if(basicElements == null)
	    	basicElements = new ArrayList<>();
	    
	    return basicElements.stream().toArray(BasicElement[]::new);
	}
	
	@Override
	public BasicElement[] gets(Class<?> clazz, String fieldName, String fieldValue) {
		
		BasicElement[] basicElements = null;
		
		Idx idx = new Idx(clazz, fieldName, fieldValue);
		Set<String> sets = null;
		Set<byte[]> setBytes = null;
		
		try (Jedis jedis = redisConnection.getConnectionPool(clazz).getResource()) {
				
			if (dataCacheType == DATACACHE_TYPE_BIN) {
				
				setBytes = jedis.smembers(idx.getValue().getBytes());
			}
			else {
				
				sets = jedis.smembers(idx.getValue());
			}
			
			if (!CollectionUtils.isEmpty(sets)) {
				
				if (dataCacheType == DATACACHE_TYPE_HSET) {
				
					basicElements = getsByIndexesHSet(sets, clazz, jedis);
				}
				else if (dataCacheType == DATACACHE_TYPE_JSON) {
					
					basicElements = getsByIndexesJson(sets, clazz, jedis);
				}
			}
			else if (!CollectionUtils.isEmpty(setBytes)) {
				
				basicElements = getsByIndexesBin(setBytes, jedis);
			}
			else {
				
				if (dataCacheType == DATACACHE_TYPE_HSET) {
				
					basicElements = getsByFullScanHSet(clazz, fieldName, fieldValue, jedis);
				}
				else if (dataCacheType == DATACACHE_TYPE_JSON) {
					
					basicElements = getsByFullScanJson(clazz, fieldName, fieldValue, jedis);
				}
				else if (dataCacheType == DATACACHE_TYPE_BIN) {
					
					basicElements = getsByFullScanBin(clazz, fieldName, fieldValue, jedis);
				}
			}
			
		} catch (Exception e){
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
				
		return basicElements;
	}
	
	private BasicElement[] getsByIndexesHSet(@NonNull Set<String> sets, Class<?> clazz, Jedis jedis) {
		
		List<BasicElement> basicElements = new ArrayList<>();
		
		for (String keyRef : sets) {
			
			Map<String, String> hash = jedis.hgetAll(keyRef);
			
			if (!MapUtils.isEmpty(hash)) {
				
				BasicElement basicElement = (BasicElement) CacheModelHelper.getObject(clazz, hash);
				
				basicElements.add(basicElement);
			}
		}
		
		return basicElements.stream().toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getsByIndexesJson(@NonNull Set<String> sets, Class<?> clazz, Jedis jedis) {
			
		List<BasicElement> basicElements = new ArrayList<>();
		
		List<String> basicElementJsons = jedis.mget(sets.toArray(String[]::new));
		
		if (!CollectionUtils.isEmpty(basicElementJsons)) {
			
			basicElements = CacheModelHelper.getObjects(clazz, basicElementJsons).stream().collect(Collectors.toList());
		}
		
		return basicElements.stream().toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getsByIndexesBin(@NonNull Set<byte[]> sets, Jedis jedis) {
		
		List<BasicElement> basicElements = new ArrayList<>();
		
		List<byte[]> basicElementBins = jedis.mget(sets.toArray(byte[][]::new));
		
		if (!CollectionUtils.isEmpty(basicElementBins)) {
			
			basicElements = Arrays.stream(basicElementBins.toArray(byte[][]::new))
								  .map(e -> (BasicElement) SerializationUtils.deserialize(e))
								  .collect(Collectors.toList());
		}
		
		return basicElements.stream().toArray(BasicElement[]::new);
	}

	private BasicElement[] getsByFullScanHSet(@NonNull Class<?> clazz, @NonNull String fieldName, @NonNull String fieldValue, Jedis jedis) {
		
		List<BasicElement> basicElements = new ArrayList<>();

		Field fieldSearch = ClassesUtil.getField(clazz, fieldName);
		
		if (fieldSearch != null && !fieldSearch.isAnnotationPresent(IndexKey.class)) {
			
			log.warn("Full scan in keys for this input => " + 
					"[Class = " + clazz.getSimpleName() + "]" + 
					"[FieldName = " + fieldName + "]" + 
					"[FieldValue = " + fieldValue + "] Consider using index for field");	
		}
		
		Key keyPattern = new Key(clazz, null);
		
		Key[] keys = find(keyPattern);
		
		if (keys != null) {
			
			for (Key key : keys) {
				
				if (jedis.hget(key.getValue(), fieldName).equals(fieldValue)) {
					
					Map<String, String> hash = jedis.hgetAll(key.getValue());
					
					if (!MapUtils.isEmpty(hash)) {
						
						BasicElement basicElement = (BasicElement) CacheModelHelper.getObject(clazz, hash);
						
						basicElements.add(basicElement);
					}
				}
			}
		}
		
		return basicElements.stream().toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getsByFullScanJson(@NonNull Class<?> clazz, @NonNull String fieldName, @NonNull String fieldValue, Jedis jedis) {
		
		List<BasicElement> basicElements = new ArrayList<>();

		try {
			
			Field fieldSearch = ClassesUtil.getField(clazz, fieldName);
			
			if (fieldSearch != null && !fieldSearch.isAnnotationPresent(IndexKey.class)) {
				
				log.warn("Full scan in keys for this input => " + 
						"[Class = " + clazz.getSimpleName() + "]" + 
						"[FieldName = " + fieldName + "]" + 
						"[FieldValue = " + fieldValue + "] Consider using index for field");	
			}
			
			Key keyPattern = new Key(clazz, null);
			
			Key[] keys = find(keyPattern);
			
			if (keys != null) {
				
				List<String> keysStr = Arrays.stream(keys)
						.filter(e -> e.getClazz() == clazz)
						.map(e -> e.getValue())
						.collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(keysStr)) {
				
					List<String> valuesJson = jedis.mget(keysStr.toArray(String[]::new));
					
					List<BasicElement> allBasicElements = CacheModelHelper.getObjects(clazz, valuesJson).stream().collect(Collectors.toList());
					
					if (!CollectionUtils.isEmpty(allBasicElements)) {
						
						Field field = ClassesUtil.getField(clazz, fieldName);
						
						field.setAccessible(true);
						
						List<BasicElement> basicElementsFound = allBasicElements.stream().filter(be -> {
							
							try {
								
								return field.get(be).equals(fieldValue);
								
							} catch (IllegalArgumentException | IllegalAccessException e) {
								
								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}
							
							return false;
							
						}).collect(Collectors.toList());
						
						if (!CollectionUtils.isEmpty(basicElementsFound)) {
							
							basicElements = basicElementsFound;
						}
					}
				}
			}
			
		} catch (IllegalArgumentException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
		
		return basicElements.stream().toArray(BasicElement[]::new);
	}
	
	private BasicElement[] getsByFullScanBin(@NonNull Class<?> clazz, @NonNull String fieldName, @NonNull String fieldValue, Jedis jedis) {
		
		List<BasicElement> basicElements = new ArrayList<>();

		try {
			Field fieldSearch = ClassesUtil.getField(clazz, fieldName);
			
			if (fieldSearch != null && !fieldSearch.isAnnotationPresent(IndexKey.class)) {
				
				log.warn("Full scan in keys for this input => " + 
						"[Class = " + clazz.getSimpleName() + "]" + 
						"[FieldName = " + fieldName + "]" + 
						"[FieldValue = " + fieldValue + "] Consider using index for field");	
			}
			
			Key keyPattern = new Key(clazz, null);
			
			Key[] keys = find(keyPattern);
			
			if (keys != null) {
				
				List<byte[]> keysBin = Arrays.stream(keys)
						.filter(e -> e.getClazz() == clazz)
						.map(e -> e.getValue().getBytes())
						.collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(keysBin)) {
				
					List<byte[]> valuesBin = jedis.mget(keysBin.toArray(byte[][]::new));
					
					List<BasicElement> allBasicElements = valuesBin.stream().map(e -> (BasicElement) SerializationUtils.deserialize(e)).collect(Collectors.toList());
					
					if (!CollectionUtils.isEmpty(allBasicElements)) {
						
						Field field = ClassesUtil.getField(clazz, fieldName);
						
						field.setAccessible(true);
						
						List<BasicElement> basicElementsFound = allBasicElements.stream().filter(be -> {
							
							try {
								
								return field.get(be).equals(fieldValue);
								
							} catch (IllegalArgumentException | IllegalAccessException e) {
								
								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}
							
							return false;
							
						}).collect(Collectors.toList());
						
						if (!CollectionUtils.isEmpty(basicElementsFound)) {
							
							basicElements = basicElementsFound;
						}
					}
				}
			}
			
		} catch (IllegalArgumentException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
		
		return basicElements.stream().toArray(BasicElement[]::new);
	}


	@Override
	public Key[] del(Key key) {
		
		List<Key> keyList = new ArrayList<>();
		
		try (Jedis jedis = redisConnection.getConnectionPool(key.getClazz()).getResource()) {
		
			Key[] keys = find(key);
			
			if (!ArrayUtils.isEmpty(keys)) {
				
				List<String> keysStr = Arrays.stream(keys)
						.map(k -> k.getValue())
						.collect(Collectors.toList());
			
				if (!CollectionUtils.isEmpty(keysStr)) {
				
					for (Key keyToList : keys) {
						
						delIdxs(get(keyToList));
					}
					
					if (dataCacheType == DATACACHE_TYPE_BIN) {
						
						List<byte[]> keysBin = keysStr.stream()
								.map(e -> e.getBytes())
								.collect(Collectors.toList());
						
						jedis.del(keysBin.stream().toArray(byte[][]::new));
					}
					else {
				
						jedis.del(keysStr.stream().toArray(String[]::new));
					}
				}
			}
			
		} catch (Exception e){
            
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
		return keyList.stream().toArray(Key[]::new);
	}
	
	@Override
	public void delAll() {
		
		try (Jedis jedis = redisConnection.getConnectionPool().getResource()) {
			
			jedis.flushAll();
			
		} catch (Exception e){
            
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
	}

	@Override
	public Key[] find(Key key) {
		
		List<Key> keys = new ArrayList<>();
		
		if (dataCacheType == DATACACHE_TYPE_BIN) {
			
			return findBin(key);
		}
		
		ScanParams scanParams = new ScanParams().count(this.scanCount);
		
		scanParams.match(key.getValue());
		
	    String cursor = redis.clients.jedis.ScanParams.SCAN_POINTER_START;
	    boolean cycleIsFinished = false;
	    
	    try (Jedis jedis = redisConnection.getConnectionPool(key.getClazz()).getResource()) {
	    	
	    	while (!cycleIsFinished) {
	    		
		        ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
		        
		        List<String> result = scanResult.getResult();

		        for (String keyRes : result) {
					
		        	Key keyItem = new Key(keyRes);
		        		        		
	        		keys.add(keyItem);
				}

		        cursor = scanResult.getCursor();
		        
		        if (cursor.equals("0")) {
		            cycleIsFinished = true;
		        }
	    	}
	    	
	    } catch (Exception e){
            
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
	    
//		log.debug("Datacache::find() " + Duration.between(instant, Instant.now()).toMillis() + " ms. Iterations = "
//				+ iterations);
		
	    return keys.stream().toArray(Key[]::new);
	}
	
	private Key[] findBin(Key key) {
		
		List<Key> keys = new ArrayList<>();
		
		ScanParams scanParams = new ScanParams().count(this.scanCount);
		
		scanParams.match(key.getValue().getBytes());
		
	    byte[] cursor = redis.clients.jedis.ScanParams.SCAN_POINTER_START_BINARY;
	    
	    try (Jedis jedis = redisConnection.getConnectionPool(key.getClazz()).getResource()) {
	    	
	    	ScanResult<byte[]> scanResult;
	    	
	    	do {
	    		
	    		scanResult = jedis.scan(cursor, scanParams);
	            
	            List<byte[]> keysBin = scanResult.getResult();

	            for (byte[] keyBin : keysBin) {
	            	
	            	Key keyItem = new Key(new String(keyBin));
	        		
	        		keys.add(keyItem);
	            }
	            
	            cursor = scanResult.getCursorAsBytes();
	            
			} while (!Arrays.equals(cursor, redis.clients.jedis.ScanParams.SCAN_POINTER_START_BINARY));
	    	
	    } catch (Exception e){
            
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
	    
//		log.debug("Datacache::find() " + Duration.between(instant, Instant.now()).toMillis() + " ms. Iterations = "
//				+ iterations);
		
	    return keys.stream().toArray(Key[]::new);
	}
	
	public BasicElement getFromChannel(byte[] channel) {
		
		return getFromChannel(new String(channel));
	}

	@Override
	public BasicElement getFromChannel(String channel) {
		
		Key key = null;
		
		if (!StringUtils.isEmpty(channel)) {
			
			String strType = channel.substring(channel.indexOf(':') + 1);
			
			strType = strType.substring(0, strType.indexOf(':') + 1);
			
			if (strType.equals(Key.KEY_PREFIX)) {
			
				String strKey = channel.substring(channel.indexOf(':') + 1);
				key = new Key(strKey);
				
				BasicElement[] basicElements = gets(key);
					
				if (!ArrayUtils.isEmpty(basicElements) &&				
					// Prevents you from receiving the same thing you publish
					CacheModelHelper.getModifiedBy(basicElements[0]).equals(modifiedBy)) {
					
					return basicElements[0];
				}
			}
		}
		
		return null;
	}
	
	public BasicElement getDeleted(byte[] channel) {
		
		return getDeleted(new String(channel));
	}
	
	@Override
	public BasicElement getDeleted(String channel) {

		BasicElement basicElement = null;
		Key key = null;
			
		try {

			if (!StringUtils.isEmpty(channel)) {

				String strKey = channel.substring(channel.indexOf(':') + 1);
				key = new Key(strKey);

				basicElement = (BasicElement) key.getClazz().getConstructor().newInstance();

				if (basicElement != null) {

					basicElement.setPks(key.getIds());
				}
			}

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
			
		return basicElement;
	}
	
	public Set<Key> keys(String pattern) {
		
		return Arrays.stream(find(new Key(pattern))).collect(Collectors.toSet());
	}
}
