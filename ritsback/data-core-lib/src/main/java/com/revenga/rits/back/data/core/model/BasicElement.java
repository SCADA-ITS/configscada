package com.revenga.rits.back.data.core.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * Defines the inheritance base type for all elements of the relational database
 * model. This class defines both methods and attributes common to database
 * objects.
 * 
 * @author aborrajo
 */
@Getter
@Setter
@Log4j2
public abstract class BasicElement implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8961367361963811958L;
	
	protected static final String FILE_CONSTANTS = "constants.properties";
		
	private static final AtomicLong idSequence = new AtomicLong(Instant.now().toEpochMilli());

	protected Long id;
	protected String alias;
	protected String description;
	protected String labelAlias;
	protected String labelDescription;
	protected Boolean enabled;
	protected Boolean visible;
	protected Long createdAt;
	protected Long updatedAt;
	protected String modifiedBy;
	protected Long expireAt;
	
	@JsonIgnore
	private Set<String> primaryKeyFields = new LinkedHashSet<>();
	
	public BasicElement() {
		super();
		
		this.enabled = true;
		this.visible = true;
		this.createdAt = Instant.now().toEpochMilli();
		this.updatedAt = this.createdAt;
		this.expireAt = null;
		
		initPrimaryKeyFields();
	}
	
	/**
	 * 
	 * Generate UID for object
	 * Cannot generate UID if field id is already set
	 * 
	 * @return Unique identifier generated for object 
	 */
	public Long generateId() {
	
		if (id == null) {
		
			id = idSequence.getAndIncrement();
		}
			
		return id;
	}
	
	private void initPrimaryKeyFields() {
		
		PrimaryKeys primaryKeys = this.getClass().getAnnotation(PrimaryKeys.class);
		
		if (primaryKeys != null && !ArrayUtils.isEmpty(primaryKeys.fields())) {
						
			for (String pkName : primaryKeys.fields()) {
				
				primaryKeyFields.add(pkName);
			}
		}
	}
	
	public void initPks() {
		
		try {
			
			for (String fieldPk : primaryKeyFields) {
				
				Field field = ClassesUtil.getField(this.getClass(), fieldPk);
				
				if (field != null) {
					
					field.setAccessible(true);
					
					field.set(this, null);
				}
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
	}
	
	public void initAssociatedKeys() {
		
		try {
			
			for (Field field : this.getClass().getDeclaredFields()) {

				field.setAccessible(true);
				
				if (Modifier.isFinal(field.getModifiers())) {
					
					continue;
				}
				
				Object value = field.get(this);
				
				if (value != null && field.isAnnotationPresent(AssociatedKeys.class)) {
				
					field.set(this, null);
				}
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public boolean update(BasicElement newBasicElement) {
		
		boolean isUpdated = false;
		
		try {
			
			for (Field field : this.getClass().getDeclaredFields()) {
				
				field.setAccessible(true);
				
				if (Modifier.isFinal(field.getModifiers())) {
					
					continue;
				}
				
				Object newValue;
				
				Field fieldValue = newBasicElement.getClass().getDeclaredField(field.getName());
				
				fieldValue.setAccessible(true);
				
				newValue = fieldValue.get(newBasicElement);
				
				if (field.get(this) != newValue) {
				
					field.set(this, newValue);
					
					isUpdated |= true;
				}
			}
			
			for (Field field : this.getClass().getSuperclass().getDeclaredFields()) {
				
				field.setAccessible(true);
				
				if (Modifier.isFinal(field.getModifiers())) {
					
					continue;
				}
				
				Object newValue;
				
				Field fieldValue = newBasicElement.getClass().getSuperclass().getDeclaredField(field.getName());
				
				fieldValue.setAccessible(true);
				
				newValue = fieldValue.get(newBasicElement);
								
				if (field.get(this) != newValue) {
					
					field.set(this, newValue);
					
					isUpdated |= true;			
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return isUpdated;
	}
	
	public void setPks(Map<Integer, String> ids) {
		
		int idxPk = 0;
		
		try {
			
			if (ids == null) return;
			
			for (String fieldPk : primaryKeyFields) {
				
				Field field = ClassesUtil.getField(this.getClass(), fieldPk);
				
				if (field != null) {
					
					field.setAccessible(true);
				
					String fieldValue = ids.get(++idxPk);
					
					if (!StringUtils.isEmpty(fieldValue)) {
					
						if (field.getType() == Long.class && NumberUtils.isCreatable(fieldValue)) {
						
							field.set(this, Long.valueOf(fieldValue));
						}
						else if (field.getType() == Integer.class && NumberUtils.isCreatable(fieldValue)) {
							
							field.set(this, Integer.valueOf(fieldValue));
						}
						else if (field.getType() == String.class) {
							
							field.set(this, fieldValue);
						}
						else {
							
							throw new Exception("Unsupported type: " + field.getType());
						}
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}		
	}
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
			
		try {
			
			for (String fieldPk : primaryKeyFields) {
				
				Field field = ClassesUtil.getField(this.getClass(), fieldPk);
				
				if (field != null) {
					
					field.setAccessible(true);
				
					result = prime * result + ((field.get(this) == null) ? 0 : field.get(this).hashCode());
				}
			}
			
		} catch (IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		boolean res = false;
		
		try {

			if (this == obj)
				return true;
			
			if (obj == null)
				return false;
			
			if (this.getClass() != obj.getClass())
				return false;
			
			res = true;
			
			for (String fieldPk : primaryKeyFields) {
				
				Field field = ClassesUtil.getField(this.getClass(), fieldPk);
				
				if (field != null) {
					
					field.setAccessible(true);
				
					Field fieldObj = ClassesUtil.getField(obj.getClass(), field.getName());
					fieldObj.setAccessible(true);
					
					res &= (field.get(this).equals(fieldObj.get(obj)));
				}
			}
			
		} catch (IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}		
		
		return res;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
}
