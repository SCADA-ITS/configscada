package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class PlanOrderStateType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -61674150745274428L;

	public static final long PLAN_ORDER_STATE_TYPE_UNKNOWN = Integer.valueOf(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.state.type.unknown"));

	public static final long PLAN_ORDER_STATE_TYPE_RUNNING = Integer.valueOf(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.state.type.running"));

	public static final long PLAN_ORDER_STATE_TYPE_SUCCESS = Integer.valueOf(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.state.type.success"));

	public static final long PLAN_ORDER_STATE_TYPE_FAILED = Integer.valueOf(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.state.type.failed"));
}
