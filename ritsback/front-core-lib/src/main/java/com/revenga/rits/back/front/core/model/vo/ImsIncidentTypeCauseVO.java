package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentTypeCauseVO extends BasicElementVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8310872588297605124L;
	
	private String incidentTypeId;
	private String incidentTypeCauseId;

}
