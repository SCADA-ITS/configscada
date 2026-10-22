package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity

public class CommandElementPreconditionVO extends BasicElementVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1753897503727428154L;
	/**
	 * 
	 */	
	@EqualsAndHashCode.Exclude
	private ElementTypeParamVO targetElementTypeParam; 
	
	@EqualsAndHashCode.Exclude
	private ElementVO targetElement; 
	
	@EqualsAndHashCode.Exclude									
	private ThresholdOperationVO thresholdOperation;      
	private String thresholdVal1;                         
	private String thresholdVal2;                         
	
	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO elementTypeState;
	@EqualsAndHashCode.Exclude
	private AlarmConfigVO activeAlarmConfig;
	
	private Boolean negative;
	
	private String toStr;
	
	@EqualsAndHashCode.Exclude
	private List<CommandElementValuePreconditionVO> commandElementValuePreconditions;
}
