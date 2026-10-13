package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "planOrderTypeId", "id" })
public class PlanOrderTypeParam extends BasicElement {

	public static final int PLAN_ORDER_TYPE_WAIT_WAITING_TIME = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.wait.waiting_time"));
	
	public static final int PLAN_ORDER_TYPE_EVENT_MESSAGE = Integer.parseInt(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.event.message"));

	public static final int PLAN_ORDER_TYPE_LOOP_NUMBER_OF_TIME = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.loop.number_of_times"));
	
	public static final int PLAN_ORDER_TYPE_AUTOMATISM_GROOVY_PATH = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.automatism.groovy_path"));
	
	public static final int PLAN_ORDER_TYPE_AUTOMATISM_AUTOMATISM_PARAM = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "plan.order.type.autoamtism.automatism_params"));

	/**
	 * 
	 */
	private static final long serialVersionUID = 5739838322221708672L;
	/**
	 * 
	 */

	private Long planOrderTypeId;
	private Long dataTypeId;
	private String threshold1;
	private String threshold2;
	private Long planOrderTypeParamUnitId;
}
