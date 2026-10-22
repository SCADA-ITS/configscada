package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentEnumParamVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3469697015542691388L;

	private String value;
	private String incidentParamId;
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentParamVO incidentParam;
	
}
