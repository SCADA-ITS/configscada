package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentTypeTaskValueVO extends BasicElementVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7792635253597434766L;
	
	private String taskTypeParamId;
	private String value;
	
	
	@EqualsAndHashCode.Exclude
	protected ImsTaskTypeParamVO taskTypeParam;
}
