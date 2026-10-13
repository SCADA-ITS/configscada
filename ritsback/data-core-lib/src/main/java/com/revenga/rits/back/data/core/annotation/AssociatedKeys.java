package com.revenga.rits.back.data.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Annotation to reference fields
 * 
 * @author alvaro
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //on field level
public @interface AssociatedKeys {

	/**
	 * 
	 * Format: [{SourceField1=TargetField1}, {SourceField2=TargetField2}, ...]
	 * Example: 
	 * <pre>
	 * {@code
	 * Class Reference {
	 *   int elementTypeId;
	 * 	 int paramTypeId;
	 * 	 int id;
	 * }
	 *          
	 * Class Principal {
	 *   int id;
	 *   int typeId;
	 *   int paramId;
	 *              
	 *   @AssociatedKeys(references={ "typeId=elementTypeId", "paramId=paramTypeId" })
	 *   Reference reference;
	 * }
	 * 
	 * @return
	 */
	public String[] references();
}
