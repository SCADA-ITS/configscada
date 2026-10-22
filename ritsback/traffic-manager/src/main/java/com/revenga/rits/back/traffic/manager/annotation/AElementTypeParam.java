package com.revenga.rits.back.traffic.manager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //on field level
public @interface AElementTypeParam {

	public enum paramType { CONFIG, MEASURE }
	
	public String[] references();
	
	public paramType paramType();
}
