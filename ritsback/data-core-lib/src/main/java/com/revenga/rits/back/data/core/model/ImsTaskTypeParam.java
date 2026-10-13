package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines params of type of task
 * 
 * @author FJ. Olivares
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"taskTypeId", "id"})
public class ImsTaskTypeParam extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 206438695244977394L;

	private Long taskTypeId;
	
	private Long paramTypeId;
	
	private Long dataTypeId;
	private String threshold1;
	private String threshold2;
	private Long taskTypeParamUnitId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"taskTypeParamUnitId=id"})
	private ElementTypeParamUnit taskTypeParamUnit;
	
}
