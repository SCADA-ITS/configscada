package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AlarmTargetElementVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7387350638060752600L;

	@EqualsAndHashCode.Exclude
	protected ElementVO sourceElement;

	@EqualsAndHashCode.Exclude
	protected ElementVO targetElement;
	
	private String sourceElementId;
	private String targetElementId;
	private String alarmConfigId;

}
