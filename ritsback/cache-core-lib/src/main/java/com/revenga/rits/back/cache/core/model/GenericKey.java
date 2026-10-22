package com.revenga.rits.back.cache.core.model;

import lombok.Getter;

@Getter
public abstract class GenericKey {

	protected Class<?> clazz;
	protected String value;
	
	public abstract String getValue();
}
