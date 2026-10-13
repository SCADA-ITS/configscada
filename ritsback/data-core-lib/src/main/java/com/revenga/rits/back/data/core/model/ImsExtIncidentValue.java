package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines value of ext Incident
 * 
 * @author agomez
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"extIncidentTypeId", "extIncidentTypeParamId", "extIncidentId"})
public class ImsExtIncidentValue extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5859644687524343563L;

	private Long extIncidentTypeId;
	private Long extIncidentTypeParamId;
	private Long extIncidentId;
	private String value;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"extIncidentTypeId=extIncidentTypeId", "extIncidentTypeParamId=id"})
	private ImsExtIncidentTypeParam extIncidentTypeParam;
}
