package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class PlanModeType extends BasicElement {

	private static final long serialVersionUID = -8077978403358026661L;
	
	public static final long PLAN_MODE_TYPE_AUTO = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.mode.type.auto"));
	
	public static final long PLAN_MODE_TYPE_SEMI = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.mode.type.semi"));
	
	public static final long PLAN_MODE_TYPE_MANUAL = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.mode.type.manual"));

}


