package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.IndexKey;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.annotation.TimeStampField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Table of equipment that is alarmed by an alarm
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
@DbConfig(db = "1")
public class Alarm extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -477342983930823461L;

	@IndexKey
	Long alarmConfigId;

	@TimeStampField	
	Long recognized;
	String recognizedComments;
	@TimeStampField	
	Long activation;
	String activationComments;
	@TimeStampField	
	Long deactivation;
	String deactivationComments;
	
	Long creationUserId;
	Long recognizedUserId;	
	Long deactivationUserId;
	
	@IndexKey
	Long sourceElementTypeId;
	Long sourceElementId;

	@IndexKey
	Long targetElementTypeId;
	Long targetElementId;
	
	@TimeStampField	
	Long filteredUntil;
	
	Boolean disagree;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "alarmConfigId=id"})
	private AlarmConfig alarmConfig;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "sourceElementId=id", "sourceElementTypeId=elementTypeId"})
	private Element sourceElement;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "targetElementId=id", "targetElementTypeId=elementTypeId"})
	private Element targetElement;
	
	public Alarm() {
		super();
	}
}
