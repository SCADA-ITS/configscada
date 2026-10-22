package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines params of type of task
 * 
 * @author FJ. Olivares
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"taskTypeId", "taskTypeParamId", "incidentTypeTaskId"})
public class ImsIncidentTypeTaskValue extends BasicElement {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220160664758357863L;

	private Long taskTypeId;
	
	private Long taskTypeParamId;
	
	private Long incidentTypeTaskId;
	
	private String value;
	
}
