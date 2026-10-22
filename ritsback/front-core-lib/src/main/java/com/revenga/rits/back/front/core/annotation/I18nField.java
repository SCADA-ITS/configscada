package com.revenga.rits.back.front.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface I18nField {
	
	public String model();
	public String labelFieldName() default "labelAlias";
	public String[] relations();
}
