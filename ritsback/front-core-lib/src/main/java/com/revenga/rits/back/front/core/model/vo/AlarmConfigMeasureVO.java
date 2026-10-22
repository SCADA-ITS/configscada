package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AlarmConfigMeasureVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8844654816928978014L;

	private String elementTypeParamId;

	@EqualsAndHashCode.Exclude
	private ElementTypeParamVO elementTypeParam;

	private String alarmMeasureOperationId;
	@EqualsAndHashCode.Exclude
	private ThresholdOperationVO alarmMeasureOperation;

	private String thresholdVal1;
	private String thresholdVal2;
}
