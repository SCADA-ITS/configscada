package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class PlanOrderType extends BasicElement {

	private static final long serialVersionUID = -9029218909310179697L;

	public static final int PLAN_ORDER_TYPE_SIGNALLING = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.signalling"));

	public static final int PLAN_ORDER_TYPE_CONFIRMATION = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.confirmation"));

	public static final int PLAN_ORDER_TYPE_CHANGE_STATUS = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.change_status"));

	public static final int PLAN_ORDER_TYPE_CHANGE_ELEMENT_PARAM_CONFIG = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.change_param_config"));

	public static final int PLAN_ORDER_TYPE_WAIT = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.wait"));

	public static final int PLAN_ORDER_TYPE_PLAY_PLAN = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.play_plan"));

	public static final int PLAN_ORDER_TYPE_CONDITION = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.condition"));

	public static final int PLAN_ORDER_TYPE_LOOP = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.loop"));
	
	public static final int PLAN_ORDER_TYPE_EVENT = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.event"));
	
	public static final int PLAN_ORDER_TYPE_AUTOMATISM = Integer
			.parseInt(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.automatism"));

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=planOrderTypeId" })
	private List<PlanOrderTypeParam> planOrderTypeParams;
}
