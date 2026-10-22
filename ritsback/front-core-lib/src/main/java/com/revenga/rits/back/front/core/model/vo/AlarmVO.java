package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AlarmVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -910470683474494839L;

	private String alarmConfigId;
	@EqualsAndHashCode.Exclude
	private AlarmConfigVO alarmConfig;
	private Long filteredUntil;
	private Long recognized;
	private String recognizedComments;
	private Long activation;
	private String activationComments;
	private Long deactivation;
	private String deactivationComments;
	private Boolean disagree;

	private String sourceElementId;
	@EqualsAndHashCode.Exclude
	private ElementVO sourceElement;

	private String targetElementId;
	@EqualsAndHashCode.Exclude
	private ElementVO targetElement;
	
	/**
	 * Creation User Fields
	 */
	private String creationUserId;
	@EqualsAndHashCode.Exclude
	protected UserVO creationUser;

	/**
	 * Recognized User Fields
	 */
	private String recognizedUserId;
	@EqualsAndHashCode.Exclude
	protected UserVO recognizedUser;
	
	/**
	 * Deactivation User Fields
	 */
	private String deactivationUserId;
	@EqualsAndHashCode.Exclude
	protected UserVO deactivationUser;


}
