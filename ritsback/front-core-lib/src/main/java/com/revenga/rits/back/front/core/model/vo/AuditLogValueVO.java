package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AuditLogValueVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8816448067211557446L;
	
	private String logTypeParamId;
	
	private String value;
	
	@EqualsAndHashCode.Exclude
	private LogTypeParamVO logTypeParam;

}
