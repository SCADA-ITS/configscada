package com.revenga.rits.back.cache.core.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.revenga.rits.back.data.core.util.ClassesUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Idx extends GenericKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1741290688903964444L;

	public static final String IDX_PREFIX = "IDX:";
	
	Field field;
	String fieldValue;

	public Idx(Key key, Field field, String fieldValue) {
		super();
		this.clazz = key.getClazz();
		this.field = field;
		this.fieldValue = fieldValue;
	}
	
	public Idx(Class<?> clazz, Field field, String fieldValue) {
		super();
		this.clazz = clazz;
		this.field = field;
		this.fieldValue = fieldValue;
	}

	public Idx(Class<?> clazz, String fieldName, String fieldValue) {
		super();
		this.clazz = clazz;
		this.field = ClassesUtil.getField(clazz, fieldName);
		this.fieldValue = fieldValue;
	}
	
	@Override
	public String getValue() {
		
		String idxValue = null;
		
		idxValue = IDX_PREFIX + this.clazz.getSimpleName() + ":" + this.field.getName() + ":" + this.fieldValue;
		
		return idxValue;
	}
}
