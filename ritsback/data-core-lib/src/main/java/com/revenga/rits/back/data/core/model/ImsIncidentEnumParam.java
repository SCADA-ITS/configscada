package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id", "incidentParamId" })
public class ImsIncidentEnumParam extends BasicElement {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 462471317772611318L;

	private Long incidentParamId;
	private String value;
	
}
