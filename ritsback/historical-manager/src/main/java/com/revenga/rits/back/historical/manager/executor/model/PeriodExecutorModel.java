package com.revenga.rits.back.historical.manager.executor.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Deprecated
@Data
@EqualsAndHashCode(callSuper = false)
public class PeriodExecutorModel {

	private String targetTable;
	private String tableSpace;
	private Long integrationPeriod;
	private Long elementTypeId;
	private String fieldTargetTimestamp;
	private String fieldTargetElementId;
	
}
