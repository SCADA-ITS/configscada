package com.revenga.rits.back.historical.manager.executor.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Deprecated
@Data
@EqualsAndHashCode(callSuper = false)
public class InstantExecutorModel {

	private String targetTable;
	private String tableSpace;
	
}
