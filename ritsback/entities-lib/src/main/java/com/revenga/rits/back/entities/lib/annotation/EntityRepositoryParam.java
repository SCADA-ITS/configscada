package com.revenga.rits.back.entities.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;

/**
 * 
 * Annotation to reference EntityRepository
 * 
 * @author alvaro
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //on field level
public @interface EntityRepositoryParam {

	public boolean buffered() default true;
	public boolean publishChanges() default false;
	public boolean subscribeChanges() default false;
	public Class<? extends EntityOnChange> entityOnChange() default EntityOnChange.class;
}
