package com.revenga.rits.back.cache.core.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * Class to generate key values to store in cache
 * 
 * @author alvaro
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Log4j2
public class Key extends GenericKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7563667103503181563L;
	
	public static final String KEY_PREFIX = "DAT:";
	Map<Integer, String> ids = new HashMap<Integer, String>();
	
	/**
	 * 
	 * Generate new Key instance for element object
	 * 
	 * @param element
	 */
	public Key(BasicElement element) {
		
		super();
		this.clazz = element.getClass();
		this.ids = initializeIds();
		this.ids = getIds(element);
	}
	
	/**
	 * 
	 * Generate new Key from string
	 * 
	 * @param element
	 */
	public Key(String value) {
		
		super();
		
		set(value);
	}
	
	/**
	 * 
	 * Generate new Key for type of Element and map of ids
	 * 
	 * @param clazz
	 * @param ids
	 * 
	 * <blockquote>example:
     * <pre>{@code
     *     
     *  // Create new key from type and values
     *  new Key(Element.class, new HashMap<String, String>() {{
     * 				put(1, String.valueOf("3"));
     * 				put(2, String.valueOf("1"));
	 * 		}});
     * </pre>
     * </blockquote>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Key(Class<?> clazz, Object ids) {
		super();
		this.clazz = clazz;
		this.ids = initializeIds();
		
		if (ids != null && ids.getClass().isArray()) {
			
			if (!ArrayUtils.isEmpty((String[]) ids)) {
				
				int pos = 1;
				
				for (String id : (String[]) ids) {
					
					setId(pos++, id);
				}
			}
		}
		else {
			
			if (ids != null) {
			
				setIds((Map<String, String>) ids);
			}
		}
	}
	
	/**
	 * 
	 * Set value for id in position
	 * 
	 * @param pos
	 * @param value
	 */
	public void setId(Integer pos, String value) {
		
		if (!StringUtils.isEmpty(value)) {
		
			ids.put(pos, value);
		}	
	}
	
	/**
	 * 
	 * Set value for string name of pk
	 * 
	 * @param fieldPkName
	 * @param value
	 */
	public void setId(String fieldPkName, String value) {
		
		PrimaryKeys pks = this.clazz.getAnnotation(PrimaryKeys.class);
		
		if (pks != null) {
		
			for (int idPos = 1; idPos <= pks.fields().length; idPos++) {
				
				Field field = ClassesUtil.getField(this.clazz, pks.fields()[idPos - 1]);
				
				if (field != null) {
				
					if (field.getName().equals(fieldPkName)) {
						
						field.setAccessible(true);

						ids.put(idPos, value);
						
						return;
					}
				}				
			}
		}		
	}
	
	/**
	 * 
	 * Get value for id position
	 * 
	 * @param pos
	 * @return Value of id in position
	 */
	public String getId(Integer pos) {
		
		return ids.get(pos);
	}
	
	public String[] getArrayOfIds() {
		
		return ids.values().toArray(new String[0]);
	}
	
	/**
	 * 
	 * Get map of ids for BasicElement
	 * 
	 * @param basicElement
	 * @return Map with ids
	 */
	private Map<Integer, String> getIds(BasicElement basicElement) {
		
		Map<Integer, String> ids = new HashMap<Integer, String>();
		PrimaryKeys pks = null;
		
		try {
			
			if (this.clazz == null) {
				
				throw new NullPointerException("this.clazz is null");
			}
			
			pks = this.clazz.getAnnotation(PrimaryKeys.class);
			
			if (pks != null) {
			
				for (int idPos = 1; idPos <= pks.fields().length; idPos++) {
					
					Field field = ClassesUtil.getField(this.clazz, pks.fields()[idPos - 1]);
					
					field.setAccessible(true);
					
					ids.put(idPos, "*");
					
					if (field.get(basicElement) != null) {
					
						String id = field.get(basicElement).toString();
						
						if (!StringUtils.isEmpty(id)) {
						
							ids.put(idPos, id);
						}
					}					
				}
			}	
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
		
		return ids;
	}
	
	/**
	 * 
	 * Set map of ids for BasicElement
	 * 
	 * @param Map with id values
	 * 
	 */
	private void setIds(Map<String, String> idsValues) {
		
		try {
			
			if (this.clazz == null) {
				
				throw new NullPointerException("this.clazz is null");
			}
			
			if (!MapUtils.isEmpty(idsValues)) {
			
				for (Map.Entry<String, String> entry : idsValues.entrySet()) {
					
					setId(entry.getKey(), entry.getValue());
				}
			}
				
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
	}
	
	/**
	 * 
	 * Initializr map of ids for BasicElement
	 * 
	 */
	private Map<Integer, String> initializeIds() {
		
		PrimaryKeys pks = null;
		Map<Integer, String> idsMap = new HashMap<Integer, String>();
		
		try {
			
			if (this.clazz == null) {
				
				throw new NullPointerException("this.clazz is null");
			}
			
			pks = this.clazz.getAnnotation(PrimaryKeys.class);
			
			if (pks != null) {
			
				for (int idPos = 1; idPos <= pks.fields().length; idPos++) {
					
					Field field = ClassesUtil.getField(this.clazz, pks.fields()[idPos - 1]);
					
					field.setAccessible(true);
					
					idsMap.put(idPos, "*");
				}
			}	
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
		
		return idsMap;
	}
	
	/**
	 * 
	 * Set class and ids from value witjout prefix
	 * 
	 * @param String with key and ids
	 * 
	 */
	public void set(String value) {

		String valueAux = value;
		
		if (!StringUtils.isEmpty(valueAux)) {
			
			if (valueAux.contains("@")) {
				
				valueAux = valueAux.substring(0, valueAux.indexOf('@'));
			}
		
			String[] splitItems = valueAux.replaceFirst(KEY_PREFIX, "").split(":");
			
			if (splitItems.length > 0) {
				
				try {
					
					Class<?> clazz = ClassesUtil.getModelClassBySimpleClassName(splitItems[0]);
					
					this.clazz = clazz;
					
					if (splitItems.length > 1) {
											
						PrimaryKeys pks = this.clazz.getAnnotation(PrimaryKeys.class);
						
						if (pks != null) {
						
							for (int idPos = 1; idPos <= pks.fields().length && (splitItems.length > idPos); idPos++) {
								
								Field field = ClassesUtil.getField(this.clazz, pks.fields()[idPos - 1]);
								
								field.setAccessible(true);
								
								setId(field.getName(), splitItems[idPos]);
							}
						}
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 
	 * Get String key value. This is the key to use in cache
	 * 
	 * @return String key value
	 */
	@Override
	public String getValue() {
		
		String keyValue = null;
		
		keyValue = KEY_PREFIX + getSimpleValue();
				
		return keyValue;
	}
	
	public String getValueForPut() {
		
		String keyValue = null;
		
		keyValue = KEY_PREFIX + getSimpleValue();
		
		return keyValue;
	}
	
	/**
	 * 
	 * Get Short representation string key of value.
	 * 
	 * @return String key value
	 */
	public String getSimpleValue() {
		
		List<String> keyValues = new ArrayList<String>();
		String keyValue = null;
		
		if (this.clazz != null) {
			
			keyValues.add(this.clazz.getSimpleName());
			
			if (!MapUtils.isEmpty(ids)) {
				
				for (Entry<Integer, String> entry: ids.entrySet()) {
				
					keyValues.add(entry.getValue());
				}
			}
			
			keyValue = String.join(":", keyValues);			
		}
		
		return keyValue;
	}
	
	/**
	 * 
	 * Checks if key contains patterns
	 * 
	 * @return True if key contains patterns. Otherwise returns False 
	 */
	public boolean hasFilterPatterns() {
		
		return this.getSimpleValue().contains("*");
	}
	
	public boolean allFilterPatterns() {
		
		return this.ids.values().stream().allMatch(e -> e.equals("*"));
	}

	public boolean match(Key keyTargetPattern) {
		
		boolean res = true;
		
		if (this.getClazz() == keyTargetPattern.getClazz()) {
			
			if (!MapUtils.isEmpty(ids) && !MapUtils.isEmpty(keyTargetPattern.getIds())) {
			
				int idOffset = 0;
				
				for (Map.Entry<Integer, String> entry: ids.entrySet()) {
							
					if (keyTargetPattern.getIds().size() > idOffset++) {
					
						String value = keyTargetPattern.getIds().get(entry.getKey());
						
						res &= (value.equals(entry.getValue()) || value.equals("*"));
						
						if (!res) {
							
							break;
						}
					}
				}
			}
			else {
				
				res = false;
			}
		}
		else {
			
			res = false;
		}
		
		return res;
	}
}
