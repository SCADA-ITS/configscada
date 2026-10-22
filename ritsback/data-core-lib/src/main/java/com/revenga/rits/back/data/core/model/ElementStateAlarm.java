package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.IndexKey;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"elementTypeId", "stateId"})
public class ElementStateAlarm extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2449767741216252915L;

	
	private Long elementTypeId;
	private Long stateId;
	
	private Long alarmConfigId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"alarmConfigId=id"})
	private AlarmConfig alarmConfig;
	
}
