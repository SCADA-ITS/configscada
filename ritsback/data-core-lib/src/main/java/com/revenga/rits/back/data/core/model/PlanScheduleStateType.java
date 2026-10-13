package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class PlanScheduleStateType extends BasicElement{/**
	 * 
	 */
	private static final long serialVersionUID = -8762843874412564468L;
	
	public static final long PLAN_SCHEDULE_STATE_TYPE_UNKNOWN = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.schedule.state.type.unknown"));
	
	public static final long PLAN_SCHEDULE_STATE_TYPE_PAUSE = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.schedule.state.type.pause"));
	
	public static final long PLAN_SCHEDULE_STATE_TYPE_RUNNING = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.schedule.state.type.running"));
	
	public static final long PLAN_SCHEDULE_STATE_TYPE_FAILED = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.schedule.state.type.failed"));

}
