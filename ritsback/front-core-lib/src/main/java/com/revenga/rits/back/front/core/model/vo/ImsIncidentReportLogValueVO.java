package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportLogValueVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1496332897559510875L;
	
	private String logTypeParamId;
	private String value;
	
	@EqualsAndHashCode.Exclude
	protected LogTypeParamVO logTypeParam;

}
