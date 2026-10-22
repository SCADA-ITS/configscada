package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class PlanStateType extends BasicElement {/**
	 * 
	 */
	private static final long serialVersionUID = -2756399434401707366L;

	public static final long PLAN_STATE_TYPE_UNKNOWN = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.state.type.unknown"));
	
	public static final long PLAN_STATE_TYPE_STOPPED = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.state.type.stopped"));
	
	public static final long PLAN_STATE_TYPE_RUNNING = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.state.type.running"));
	
	public static final long PLAN_STATE_TYPE_SUCCESS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.state.type.success"));
	
	public static final long PLAN_STATE_TYPE_FAILED = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.state.type.failed"));
	
	public static final long PLAN_STATE_TYPE_PAUSE = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.state.type.pause"));
	
	public static final long PLAN_STATE_TYPE_CANCEL= Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.state.type.cancel"));
}
