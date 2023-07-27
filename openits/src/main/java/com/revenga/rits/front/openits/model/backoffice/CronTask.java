package com.revenga.rits.front.openits.model.backoffice;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CronTask extends GroovyBasic {

	private static final long serialVersionUID = 7798179630719575747L;
	
	private static final String PACKAGE_NAME = "com.revenga.rits.front.openits.backoffice.groovy.task";
	
	private String cronExpression;
	private Map<String, Object> params;
	
	public CronTask() {
		
		super();
		
		this.packageName = PACKAGE_NAME; 
	}
}
