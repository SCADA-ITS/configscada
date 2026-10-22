package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsExtIncidentValueVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5859644687524343563L;

	/* Input */
	protected String extIncidentTypeParamId;
	
	/* Output */
	@EqualsAndHashCode.Exclude
	private ImsExtIncidentTypeParamVO extIncidentTypeParam;
	
	/* All */
	private String value;
}
