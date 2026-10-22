package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "commandElementTypeId", "elementTypeId", "id" })
public class CommandElementTypeParam extends BasicElement {

	/* TUBE */
	public static final long SEND_FANS_STOP_PARAM_IDS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_fans_stop.param.ids"));

	public static final long SEND_FANS_START_PARAM_IDS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_fans_start.param.ids"));

	public static final long SEND_FANS_START_REVERSE_PARAM_IDS = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_fans_start_reverse.param.ids"));
	
	public static final long SEND_FANS_RESET_TOTAL_HOURS_PARAM_IDS = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_fans_reset_total_hours.param.ids"));

	public static final long SEND_FANS_RESET_PARTIAL_HOURS_PARAM_IDS = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_fans_reset_partial_hours.param.ids"));
	
	public static final long SEND_FANS_RESET_ALARMS_PARAM_IDS = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_fans_reset_alarms.param.ids"));

	public static final long SEND_VENTILATION_MODE_PARAM_MODE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_ventilation_mode.param.mode"));

	public static final long SEND_VENTILATION_REGIME_PARAM_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_ventilation_regime.param.regime"));

	
	public static final long SEND_ILLUMINATION_MODE_PARAM_MODE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_illumination_mode.param.mode"));

	public static final long SEND_ILLUMINATION_REGIME_PARAM_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_illumination_regime.param.regime"));

	public static final long SEND_CIRCUITS_OFF_PARAM_IDS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_circuits_off.param.ids"));
	
	public static final long SEND_CIRCUITS_ON_PARAM_IDS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_circuits_on.param.ids"));
	
	public static final long SEND_DALI_ILLUMINATION_REGIME_PARAM_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_dali_illumination_regime.param.regime"));
	
	/* VENTILATION */
	public static final long SEND_VENTILATION_FIRE_PARAM_STRETCH_ID = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "command.send_ventilation_fire.param.stretch_id"));
	
	private static final long serialVersionUID = 5859644687524343563L;

	private Long commandElementTypeId;
	private Long elementTypeId;

	private Long dataTypeId;
	private String threshold1;
	private String threshold2;
	private Long commandElementTypeParamUnitId;
	
	private Long elementTypeParamId;
	private Long paramTypeId;
}
