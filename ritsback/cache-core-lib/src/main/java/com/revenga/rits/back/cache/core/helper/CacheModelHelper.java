package com.revenga.rits.back.cache.core.helper;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Idx;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.CalculatedField;
import com.revenga.rits.back.data.core.annotation.IndexKey;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CacheModelHelper {
	
	private CacheModelHelper() {
	    
		throw new IllegalStateException(this.getClass().getSimpleName());
	}
	
	/**
	 * 
	 * Convert from Object to Hash<String, String>
	 * 
	 * @param object to convert
	 * @return Map<String, String>
	 */
	public static Map<String, String> getHash(Object object) {

		Class<?> clazz = object.getClass();
		Class<?> superClazz = clazz.getSuperclass();
		Map<String, String> hashMap = new HashMap<>();
		
		try {
			
			for (Field field : clazz.getDeclaredFields()) {
							
				field.setAccessible(true);
				
				if (Modifier.isFinal(field.getModifiers()) || 
						field.getType() == List.class ||
						field.getType() == Set.class) {

					continue;
				}
								
				Object value = field.get(object);
				
				if (!field.isAnnotationPresent(AssociatedKeys.class)) {
				
					hashMap.put(field.getName(), value != null ? String.valueOf(value) : null);
				}
			}
			
			if (superClazz != null) {
				
				for (Field field : superClazz.getDeclaredFields()) {
					
					field.setAccessible(true);
					
					if (Modifier.isFinal(field.getModifiers()) ||
							field.getType() == List.class ||
							field.getType() == Set.class) {
						
						continue;
					}
										
					Object value = field.get(object);
								
					if (!field.isAnnotationPresent(AssociatedKeys.class)) {
						
						hashMap.put(field.getName(), value != null ? String.valueOf(value) : null);
					}
				}
			}
			
			hashMap.put("modifiedBy", getModifiedBy(object)); 
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return hashMap;
	}
	
	public static String getModifiedBy(Object object) {

		Class<?> clazz = object.getClass();
		String modifiedBy = null;
		
		if (ClassesUtil.getField(clazz, "modifiedBy") != null) {
			
			String programCommand = System.getProperty("sun.java.command");
			
			modifiedBy = ManagementFactory.getRuntimeMXBean().getName() + " (" + programCommand + ")"; 
		}
		
		return modifiedBy;
	}
	
	/**
	 * 
	 * Convert from Hash<String, String> to Object
	 * 
	 * @param clazz type of target object 
	 * @param hash source
	 * @return target object
	 */
	public static Object getObject(Class<?> clazz, Map<String, String> hash) {
		
		Object newObject = null;
		
		try {
			
			if (clazz != null) {
				
				newObject = clazz.getDeclaredConstructor().newInstance();
				
				for (Map.Entry<String, String> entry: hash.entrySet()) {
					
					String fieldName = entry.getKey();
					String fieldValue = entry.getValue();
					
					if (!StringUtils.isEmpty(fieldName)) {
						
						ClassesUtil.setField(newObject, fieldName, fieldValue);	
					}
				}
			}

		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return newObject;
	}
	
	public static Object getObject(Class<?> clazz, String json) {
		
		ObjectMapper mapper = new ObjectMapper();
		Object object = null;
		
		try {
			
			object = mapper.readValue(json, clazz);
			
		} catch (JsonProcessingException | IllegalArgumentException| SecurityException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return object;
	}
	
	public static Collection<? extends BasicElement> getObjects(Class<?> clazz, List<String> jsons) {
		
		ObjectMapper mapper = new ObjectMapper();
		List<BasicElement> objects = null;
		
		try {
			
			objects = mapper.readValue(jsons.toString(), mapper.getTypeFactory().constructCollectionType(List.class, clazz));
			
//			objects = new ArrayList<>();
//			
//			for (String json : jsons) {
//				
//				objects.add((BasicElement) clazz.getConstructor().newInstance());
//			}
			
		} catch (JsonProcessingException | IllegalArgumentException | SecurityException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return objects;
	}
	
	/**
	 * 
	 * Get array of annotated indexes in object
	 * 
	 * @param object
	 * @return Array of Idx
	 */
	public static Idx[] getIndexes(Object object) {
		
		Class<?> clazz = object.getClass();
		List<Idx> idxs = new ArrayList<>();
		
		try {
			
			for (Field field : clazz.getDeclaredFields()) {
							
				field.setAccessible(true);
				
				if (Modifier.isFinal(field.getModifiers())) {
					
					continue;
				}
				
				Object value = field.get(object);
				
				if (value != null && field.isAnnotationPresent(IndexKey.class)) {
				
					Idx idx = new Idx(clazz, field, String.valueOf(value));
					
					idxs.add(idx);
				}				
			}

		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return idxs.stream().toArray(Idx[]::new);
	}
	
	/**
	 * 
	 * Fill object associated fields with values
	 * 
	 * @param object 
	 * @return result object
	 */
	public static Object fillObject(Object object) {
		
		try {
			
			if (object != null) {
				
				DataCache dataCache = new DataCache(RedisConnection.getInstance());
				
				Class<?> clazz = object.getClass();
				
				for (Field field : clazz.getDeclaredFields()) {
					
					if (Modifier.isFinal(field.getModifiers())) {
						
						continue;
					}
															
					if (field.isAnnotationPresent(AssociatedKeys.class)) {
						
						field.setAccessible(true);
						
						Key keyTargetPattern = getKeyPattern(field, object);
						
						if (keyTargetPattern != null) {
						
							if (field.getGenericType().toString().contains(List.class.getName())) {
								
								BasicElement[] basicElements = dataCache.gets(keyTargetPattern);
								
								if (basicElements.length > 0) {
								
									List<Object> basicElementsList = new ArrayList<>();
									
									for (BasicElement basicElement : basicElements) {
										
										Object relatedObj = fillObject(basicElement);
										
										basicElementsList.add(relatedObj);
									}
									
									field.set(object, basicElementsList);
								}
							}
							else {
								
								if (!keyTargetPattern.hasFilterPatterns()) {
								
									BasicElement[] basicElements = dataCache.gets(keyTargetPattern);
									
									if (basicElements.length == 1) {
									
										Object relatedObj = fillObject(basicElements[0]);
										
										field.set(object, relatedObj);
									}
								}
							}
						}
					}
				}				
			}

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return object;
	}
	
//	public static String getJson(Object object) {
//		
//		ObjectMapper mapper = new ObjectMapper();
//		String json = null;
//		
//		try {
//			
//			mapper.setSerializationInclusion(Include.NON_NULL);
//			
//			json = mapper.writeValueAsString(object);
//			
//		} catch (JsonProcessingException e) {
//			
//			log.error(e.getMessage());
//			log.debug(ExceptionUtils.getStackTrace(e));
//		}
//		
//		return json;
//	}

	
	/**
	 * 
	 * Convert from Object to String
	 * 
	 * @param be to convert
	 * @return String
	 */
	public static String getJson(Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		if (obj != null) {
			
			if (obj.getClass().isArray()) {
			
				return getJsonArray(obj);
			}
			else {
				
				if (obj instanceof BasicElement) {
				
					return mapper.valueToTree(getJsonHash(obj)).toString();
				}
				else if (obj instanceof Command){
					
					try {

						return mapper.writeValueAsString(obj);

					} catch (JsonProcessingException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * Convert from Object to JSON string
	 * 
	 * @param be to convert
	 * @return String
	 */
	private static String getJsonArray(Object objects) {

		ObjectMapper mapper = new ObjectMapper();
		
		mapper.setSerializationInclusion(Include.NON_NULL);
		List<Map<String, Object>> listBasicElement = new ArrayList<>();
		
		if (!ArrayUtils.isEmpty((Object []) objects)) {
			
			
			for (Object obj : (Object []) objects) {
				
				Map<String, Object> hashMap = getJsonHash(obj);
				
				listBasicElement.add(hashMap);
			}
		}
		
		return mapper.valueToTree(listBasicElement).toString();
	}
	
	/**
	 * 
	 * Convert from Object to JsonNode
	 * 
	 * @param obj to convert
	 * @return Map<String, Object>
	 */
	private static Map<String, Object> getJsonHash(Object obj) {

		Class<?> clazz = obj.getClass();
		Class<?> superClazz = clazz.getSuperclass();
		Map<String, Object> hashMap = new HashMap<>();
		
		try {
			
			for (Field field : clazz.getDeclaredFields()) {
							
				field.setAccessible(true);
				
				if (Modifier.isFinal(field.getModifiers()) || 
						field.getType() == List.class ||
						field.getType() == Set.class) {

					continue;
				}
								
				Object value = field.get(obj);
				
				if (!field.isAnnotationPresent(AssociatedKeys.class) && value != null) {
				
					hashMap.put(field.getName(), value);
				}
			}
			
			if (superClazz != null) {
				
				for (Field field : superClazz.getDeclaredFields()) {
					
					field.setAccessible(true);
					
					if (Modifier.isFinal(field.getModifiers()) ||
							field.getType() == List.class ||
							field.getType() == Set.class) {
						
						continue;
					}
										
					Object value = field.get(obj);
								
					if (!field.isAnnotationPresent(AssociatedKeys.class) && value != null) {
						
						hashMap.put(field.getName(), value);
					}
				}
			}
			
			if (hashMap.size() > 0) {
			
				hashMap.put("modifiedBy", getModifiedBy(obj));
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return hashMap;
	}
	
	public static byte[] getBin(BasicElement basicElement) {
		
		byte[] bin = null;
		
		try {
			
			Object cloneBasicElement = cloneBasicElementForPut(basicElement);
			
			bin = SerializationUtils.serialize((BasicElement) cloneBasicElement);
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.error(basicElement);
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return bin;
	}
	
	public static Object cloneBasicElementForPut(BasicElement basicElement) {
		
		Class<?> clazz = basicElement.getClass();
		Object cloneBasicElement = null;
		
		try {
			
			cloneBasicElement = basicElement.clone();
			
			if (cloneBasicElement != null) {
			
				for (Field field : clazz.getDeclaredFields()) {
					
					field.setAccessible(true);
					
					if (Modifier.isFinal(field.getModifiers())) {

						continue;
					}
					
					Object value = field.get(cloneBasicElement);
										
					if (value != null && !field.getType().isPrimitive() && field.getType() != Integer.class
							&& field.getType() != Long.class && field.getType() != Float.class
							&& field.getType() != Double.class && field.getType() != Boolean.class
							&& field.getType() != String.class) {

						field.set(cloneBasicElement, null);
					}				
				}
			}
			
		} catch (IllegalArgumentException | IllegalAccessException | CloneNotSupportedException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return cloneBasicElement;
	}
	
	public static Object updateBasicElement(BasicElement beSource, BasicElement beTarget) {
		
		Class<?> clazz = beSource.getClass();

		if (beSource.getClass().equals(beTarget.getClass())) {
			
			for (Field field : clazz.getDeclaredFields()) {
				
				updateField(field, beSource, beTarget);
			}
			
			Class<?> superClazz = clazz.getSuperclass();

			if (superClazz != null) {
				
				for (Field field : superClazz.getDeclaredFields()) {
					
					updateField(field, beSource, beTarget);	
				}
			}
		}

		return beTarget;
	}
	
	private static void updateField(Field field, BasicElement beSource, BasicElement beTarget) {
		
		try {
			
			field.setAccessible(true);
			
			if (!Modifier.isFinal(field.getModifiers()) && !field.isAnnotationPresent(CalculatedField.class)
					&& (field.getType().isPrimitive() || field.getType() == Integer.class
							|| field.getType() == Long.class || field.getType() == Float.class
							|| field.getType() == Double.class || field.getType() == Boolean.class
							|| field.getType() == String.class)) {

				field.set(beTarget, field.get(beSource));
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public static byte[] getBin(Command command) {
		
		byte[] bin = null;
		
		try {
			
			if (command != null) {
				
				bin = SerializationUtils.serialize(command);
			}
			
		} catch (IllegalArgumentException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return bin;
	}
	
	private static Key getKeyPattern(Field field, Object object) {
		
		AssociatedKeys associatedKeys;
		Key keyTargetPattern = null;
		Class<?> targetClass = null;
		Class<?> sourceClass = null;
		
		try {
			
			if (field != null && object != null) {
				
				associatedKeys = field.getAnnotation(AssociatedKeys.class);
			
				if (field.isAnnotationPresent(AssociatedKeys.class) && associatedKeys != null) {
						
					if (field.getType() == List.class) {
						
						Type type = null;
						
						ParameterizedType pt = (ParameterizedType) field.getGenericType() ;
						
						if (pt.getActualTypeArguments().length != 1) {
							
							throw new NullPointerException("Incorrect number of types for list");
						}
						
						type = pt.getActualTypeArguments()[0];
						
						if (type instanceof Class) {
							
							targetClass = (Class<?>) type;
						}
					}
					else {
						
						targetClass = field.getType();
					}
					
					sourceClass = field.getDeclaringClass();
					
					if (targetClass == null) {
						
						throw new NullPointerException("targetClass is null");
					}
					
					Object targetPattern = targetClass.getDeclaredConstructor().newInstance();
					keyTargetPattern = new Key((BasicElement) targetPattern);
				
					for (String fieldAssociation : associatedKeys.references()) {
						
						String[] fields = fieldAssociation.trim().split("=");
						
						if (fields != null && fields.length == 2) {
							
							Field targetField = ClassesUtil.getField(targetClass, fields[1].trim());
							Field sourceField = ClassesUtil.getField(sourceClass, fields[0].trim());
							
							if (sourceField == null || targetField == null) {
								
								keyTargetPattern = null;
								throw new NullPointerException("sourceField or targetField is null");
							}
							
							targetField.setAccessible(true);
							sourceField.setAccessible(true);
							
							keyTargetPattern.setId(targetField.getName(), String.valueOf(sourceField.get(object)));
						}
					}
				}
			}
			
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
		
		return keyTargetPattern;
	}
		
	public static Object fillObject(Object object, Map<Key, BasicElement> mapReference) {
		
		try {
			
			if (object != null && mapReference != null) {
				
				Class<?> clazz = object.getClass();
				
				for (Field field : clazz.getDeclaredFields()) {
					
					if (Modifier.isFinal(field.getModifiers())) {
						
						continue;
					}
															
					if (field.isAnnotationPresent(AssociatedKeys.class)) {
						
						field.setAccessible(true);
						
						Key keyTargetPattern = getKeyPattern(field, object);
						
						if (keyTargetPattern != null) {
						
							List<BasicElement> basicElements = new ArrayList<>();
							
							for (Map.Entry<Key, BasicElement> entry : mapReference.entrySet()) {
								
								if (entry.getKey().match(keyTargetPattern)) {
									
									basicElements.add(entry.getValue());
								}
							}
							
							if (field.getGenericType().toString().contains(List.class.getName())) {
										
								if (!CollectionUtils.isEmpty(basicElements)) {
								
									List<Object> basicElementsList = new ArrayList<>();
									
									for (BasicElement basicElement : basicElements) {
										
										Object relatedObj = fillObject(basicElement, mapReference);
										
										basicElementsList.add(relatedObj);
									}
									
									field.set(object, basicElementsList);
								}
							}
							else {
								
								if (!keyTargetPattern.hasFilterPatterns() && basicElements.size() == 1) {
									
									Object relatedObj = fillObject(basicElements.get(0), mapReference);
									
									field.set(object, relatedObj);
								}
							}
						}
					}
				}				
			}

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return object;
	}
	
	public static Map<Key, BasicElement> getAllAssociated(Class<?> clazz) {
		
		return getAllAssociated(clazz, null);
	}
	
	private static Map<Key, BasicElement> getAllAssociated(Class<?> clazz, Map<Key, BasicElement> mapReference) {

		Map<Key, BasicElement> mapBasicElements = null;
		DataCache dataCache = new DataCache(RedisConnection.getInstance());
		
		mapBasicElements = mapReference;
		
		if (mapBasicElements == null) {
		
			mapBasicElements = new HashMap<>();
		}
		
		for (Field field : clazz.getDeclaredFields()) {
			
			Class<?> targetClass = null;
			
			if (Modifier.isFinal(field.getModifiers())) {
				
				continue;
			}
													
			if (field.isAnnotationPresent(AssociatedKeys.class)) {
				
				field.setAccessible(true);
				
				if (field.getType() == List.class) {
					
					Type type = null;
					
					ParameterizedType pt = (ParameterizedType) field.getGenericType() ;
					
					if (pt.getActualTypeArguments().length != 1) {
						
						throw new NullPointerException("Incorrect number of types for list");
					}
					
					type = pt.getActualTypeArguments()[0];
					
					if (type instanceof Class) {
						
						targetClass = (Class<?>) type;
					}
				}
				else {
					
					targetClass = field.getType();
				}
				
				if (targetClass != null) {
					
					BasicElement basicElement;
					
					try {
						
						basicElement = (BasicElement) targetClass.getDeclaredConstructor().newInstance();

						Key key = new Key(basicElement);
						
						BasicElement[] basicElements = dataCache.gets(key);
						
						if (!ArrayUtils.isEmpty(basicElements)) {
							
							for (BasicElement be : basicElements) {
						
								mapBasicElements.put(new Key(be), be);
							}
						}
						
						getAllAssociated(targetClass, mapBasicElements);
						
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}
		
		return mapBasicElements;
	}
}
