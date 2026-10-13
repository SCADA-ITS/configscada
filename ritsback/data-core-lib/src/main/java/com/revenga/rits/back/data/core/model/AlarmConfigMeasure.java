package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Extended measurement alarm inventory information
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"alarmConfigId", "elementTypeId", "paramTypeId", "elementTypeParamId"})
public class AlarmConfigMeasure extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4911257158494381327L;
	
	Long alarmConfigId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	AlarmConfig alarmConfig;
		
	Long elementTypeId;
	Long paramTypeId;
	Long elementTypeParamId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"elementTypeId=elementTypeId", "paramTypeId=paramTypeId", "elementTypeParamId=id"})
	ElementTypeParam elementTypeParam;
		
	Long alarmMeasureOperationId;
	
	String thresholdVal1;
	String thresholdVal2;
}
