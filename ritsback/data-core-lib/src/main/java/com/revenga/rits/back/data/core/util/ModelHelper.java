package com.revenga.rits.back.data.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ModelHelper {
	
	public enum OrderType {
		
		ORDER_ID("id"),
		ORDER_PERIPHERAL("peripheral"),
		ORDER_ALIAS("alias"),
		ORDER_DESCRIPTION("description");

		private String fieldName;

		OrderType(String orderTypeFieldName) {

			this.fieldName = orderTypeFieldName;

		}

		public String getFieldName() {

			return fieldName;
		}

		private static final Map<String, OrderType> lookup = new HashMap<>();

		static {

			for (OrderType orderType : OrderType.values()) {

				lookup.put(orderType.getFieldName(), orderType);
			}
		}

		public static OrderType get(String fieldName) {

			return lookup.get(fieldName);
		}
	}
	
	public static List<Object> toList(Collection<Class<?>> collection) {
		
		return new ArrayList<>(collection); 
	}
	
	public static List<Object> getSortedList(Map<Integer, Class<?>> map, OrderType orderType) {
	
		List<Object> list = toList(map.values());
		
		return getSortedList(list, orderType);
	}
	
	public static List<Object> getSortedList(List<?> list, OrderType orderType) {
			
		return getSortedList(list, orderType.getFieldName());
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> getSortedList(List<?> list, String fieldNameOrder) {
		
		Collections.sort(list, new Comparator<Object>() {
		    @Override
		    public int compare(Object a1, Object a2) {
		        
		    	int resultCompare = 1;
		    	Field fieldA1 = ClassesUtil.getField(a1.getClass(), fieldNameOrder);
		    	Field fieldA2 = ClassesUtil.getField(a2.getClass(), fieldNameOrder);
		    	
		    	try {
		    		Object a1PropValue = null;
		    		Object a2PropValue = null;
		    		
		    		fieldA1.setAccessible(true);
		    		fieldA2.setAccessible(true);
		    		
		    		a1PropValue = fieldA1.get(a1);
		    		a2PropValue = fieldA2.get(a2);
					
					if (a1PropValue != null && a2PropValue != null) {
						
						if (a1PropValue instanceof Integer && a2PropValue instanceof Integer) {
						
							resultCompare = ((Integer) a1PropValue).compareTo((Integer) a2PropValue);
						}
						else if (a1PropValue instanceof String && a2PropValue instanceof String) {
							
							resultCompare = ((String) a1PropValue).compareTo((String) a2PropValue);
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				} 
		    	
		    	return resultCompare;
		    }
		});
		
		return (List<Object>) list;
	}
	
	private static Comparator<Object> getComparator(OrderType orderType) {
		
		return (new Comparator<Object>() 
        { 
			@Override
		    public int compare(Object a1, Object a2) {
		        
		    	int resultCompare = 1;
		    	Field fieldA1 = ClassesUtil.getField(a1.getClass(), orderType.getFieldName());
		    	Field fieldA2 = ClassesUtil.getField(a2.getClass(), orderType.getFieldName());
		    	
		    	try {
		    		Object a1PropValue = null;
		    		Object a2PropValue = null;
		    		
		    		fieldA1.setAccessible(true);
		    		fieldA2.setAccessible(true);
		    		
		    		a1PropValue = fieldA1.get(a1);
		    		a2PropValue = fieldA2.get(a2);
					
					if (a1PropValue != null && a2PropValue != null) {
						
						if (a1PropValue instanceof Integer && a2PropValue instanceof Integer) {
						
							resultCompare = ((Integer) a1PropValue).compareTo((Integer) a2PropValue);
						}
						else if (a1PropValue instanceof Long && a2PropValue instanceof Long) {
						
							resultCompare = ((Long) a1PropValue).compareTo((Long) a2PropValue);
						}
						else if (a1PropValue instanceof String && a2PropValue instanceof String) {
							
							resultCompare = ((String) a1PropValue).compareTo((String) a2PropValue);
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
		    	
		    	return resultCompare;
		    }
        }); 

	}
	
	public static Object getObjectSortedList(List<?> list, OrderType orderType, Object fieldValue) {
		
		Object element = null;
		
		if (!list.isEmpty() && orderType != null && fieldValue != null) {
		
			Class<?> clazz = list.get(0).getClass();
			
			Object elem = null;
			
			try {
				
				// Set field value
				Field field = ClassesUtil.getField(clazz, orderType.getFieldName());
				
				if (field != null) {
				
					elem = clazz.getConstructor().newInstance();
					
					field.setAccessible(true);
					field.set(elem, fieldValue);
					
					int index = Collections.binarySearch(list, elem, getComparator(orderType));
					
					if (index > -1) {
						
						element = list.get(index);
					}
				}

			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}  
		}
		
		return element;
		
	}
	
	public static Long getMaxId(List<?> list) {
		Long maxId = 0L;
		if (!list.isEmpty()) {
			Class<?> clazz = list.get(0).getClass();	
			try {
				Field field = ClassesUtil.getField(clazz, "id");
				if (field != null) {
					
					field.setAccessible(true);
								
					for (Object obj : list)	
					    if (field.getType() == Long.class) {
							Long fieldVal = (Long) field.get(obj); 
							if(maxId < fieldVal)
								maxId = fieldVal;
					    }
					return maxId;
				}
			} catch (IllegalAccessException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			} 
		}
		return null;
	}
	
	public static Object getObject(List<?> list, String fieldName, Object fieldValue) {
		
		if (!list.isEmpty() && !fieldName.isEmpty() && fieldValue != null) {
		
			Class<?> clazz = list.get(0).getClass();
						
			try {
				Field field = ClassesUtil.getField(clazz, fieldName);
				
				if (field != null) {

					field.setAccessible(true);
									
					for (Object obj : list) {
						
						if (field.getType() == Integer.class) {
							
							Integer fieldVal = (Integer) field.get(obj); 
							
							if (fieldVal.equals(fieldValue)) {

								return obj;
							}
						}
						else if (field.getType() == Long.class) {
							
							Long fieldVal = (Long) field.get(obj); 
							
							if (fieldVal.equals(fieldValue)) {

								return obj;
							}
						}
						else {
							
							String fieldVal = (String) field.get(obj);
							
							if (fieldVal.equals(fieldValue)) {

								return obj;
							}
						}
					}
				}

			} catch (IllegalAccessException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			} 
		}
		
		return null;
	}
	
	public static List<Object> getObjectsSortedList(List<?> list, String fieldName, Object fieldValue) {
		
		List<Object> elements = new ArrayList<>();
		
		if (!list.isEmpty() && !fieldName.isEmpty() && fieldValue != null) {
			
			Class<?> clazz = list.get(0).getClass();
					
			try {
				
				Field field = ClassesUtil.getField(clazz, fieldName);
			
				if (field != null) {
				
					for (Object obj : list) {
				
						field.setAccessible(true);
						
						if (field.getType() == Integer.class) {
							
							Integer fieldVal = (Integer) field.get(obj); 
							
							if (fieldVal.equals(fieldValue)) {

								elements.add(obj);
							}
							else {
								
								if (!elements.isEmpty()) {
									
									break;
								}
							}
						}
						else if (field.getType() == Long.class) {
							
							Long fieldVal = (Long) field.get(obj); 
							
							if (fieldVal.equals(fieldValue)) {

								elements.add(obj);
							}
							else {
								
								if (!elements.isEmpty()) {
									
									break;
								}
							}
						}
						else {
							
							String fieldVal = (String) field.get(obj);
							
							if (fieldVal.equals(fieldValue)) {

								elements.add(obj);
							}
							else {
								
								if (!elements.isEmpty()) {
									
									break;
								}
							}
						}
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			} 		
		}
		
		return elements;
	}
	
	public static List<Object> getObjects(List<?> list, String fieldName, Object fieldValue) {
		
		List<Object> elements = new ArrayList<>();
		
		if (!list.isEmpty() && !fieldName.isEmpty() && fieldValue != null) {
			
			Class<?> clazz = list.get(0).getClass();
					
			try {
				
				Field field = ClassesUtil.getField(clazz, fieldName);
			
				if (field != null) {
				
					for (Object obj : list) {
				
						field.setAccessible(true);
						
						if (field.getType() == Integer.class) {
							
							Integer fieldVal = (Integer) field.get(obj); 
							
							if (fieldVal.equals(fieldValue)) {

								elements.add(obj);
							}
						}
						else if (field.getType() == Long.class) {
							
							Long fieldVal = (Long) field.get(obj); 
							
							if (fieldVal.equals(fieldValue)) {

								elements.add(obj);
							}
						}
						else {
							
							String fieldVal = (String) field.get(obj);
							
							if (fieldVal.equals(fieldValue)) {

								elements.add(obj);
							}
						}
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			} 
		}
		
		return elements;
	}
}
