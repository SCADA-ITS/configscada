package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanSchedulesVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8011675084205377299L;

	public enum PlanScheduleAction {

		STOP, START, PAUSE, PLAY, RESET
	}

	private String cronExpression;
	private PlanScheduleAction action;
	
	private String planId;
	
	
	@EqualsAndHashCode.Exclude
	protected PlanVO plan;
	
	@EqualsAndHashCode.Exclude
	protected PlanScheduleStateTypeVO planScheduleStateType;

}
