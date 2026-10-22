package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PlanActionVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1311260409626516993L;

	public enum PlanAction {

		PLAY, PAUSE, STOP, CANCEL
	}
	
	private String planId;
	
	private PlanAction action;
		
}
