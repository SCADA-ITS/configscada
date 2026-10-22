package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AlarmFilteredVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -910470683474494839L;

	private String alarmConfigId;
	private Long filteredUntil;
	private String elementId;
	private Boolean enable;
	private Boolean alarmDisabled;
	
	@EqualsAndHashCode.Exclude
	private AlarmConfigVO alarmConfig;
	
	@EqualsAndHashCode.Exclude
	private ElementVO element;
	
	@EqualsAndHashCode.Exclude
	private AlarmVO alarm;
	
	/**
	 * User Fields
	 */
	private String userId;
	@EqualsAndHashCode.Exclude
	protected UserVO user;
	
	private String comment;
}
