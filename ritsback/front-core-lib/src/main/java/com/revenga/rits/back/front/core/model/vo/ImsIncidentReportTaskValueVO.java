package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportTaskValueVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7537587211253963632L;

	private String value;
	
	@EqualsAndHashCode.Exclude
	protected ImsTaskTypeParamVO taskTypeParam;
}
