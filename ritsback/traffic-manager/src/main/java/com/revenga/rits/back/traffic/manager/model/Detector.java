package com.revenga.rits.back.traffic.manager.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.traffic.manager.annotation.AElementId;
import com.revenga.rits.back.traffic.manager.annotation.AElementTypeParam;

import lombok.Data;

@Data
public class Detector {

	public static final Long ELEMENT_TYPE = Long
			.parseLong(ConfigProperty.getInstance().getProperty("application.properties", "element.type.detector"));

	public static final Integer STATE_UNKNOWN = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.detector.unknown"));

	public static final Integer STATE_UP = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.detector.up"));

	public static final Integer STATE_DOWN = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.detector.down"));

	public static final Integer STATE_ALARMS = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.detector.alarms"));

	public static Integer VEH_LENGTH = null;
	public static Integer VEH_SPEED = null;
	public static Integer VEH_PA = null;

	@AElementId
	private Long id;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.date" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Long date;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.period" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer period;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.current_direction" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Boolean currentDirection;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.n_vehicles" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer nVehicles;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.intensity" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer intensity;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.occupancy" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer occupancy;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.congestion" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Boolean congestion;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.kamikaze" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Boolean kamikaze;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.speed" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer speed;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.gap" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer gap;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.headway" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer headway;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.weight" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer weight;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.fail_data" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Boolean failData;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.flow" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer flow;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.density" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer density;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.invalid_mask" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer invalidMask;

	@AElementTypeParam(references = {
			"element.type.param.measure.detector.length" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer length;

	@AElementTypeParam(references = { "element.type.param.measure.detector.veh_length_1",
			"element.type.param.measure.detector.veh_length_2", "element.type.param.measure.detector.veh_length_3",
			"element.type.param.measure.detector.veh_length_4", "element.type.param.measure.detector.veh_length_5",
			"element.type.param.measure.detector.veh_length_6", "element.type.param.measure.detector.veh_length_7",
			"element.type.param.measure.detector.veh_length_8", "element.type.param.measure.detector.veh_length_9",
			"element.type.param.measure.detector.veh_length_10", "element.type.param.measure.detector.veh_length_11",
			"element.type.param.measure.detector.veh_length_12", "element.type.param.measure.detector.veh_length_13",
			"element.type.param.measure.detector.veh_length_14", "element.type.param.measure.detector.veh_length_15",
			"element.type.param.measure.detector.veh_length_16" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehLength;

	@AElementTypeParam(references = { "element.type.param.measure.detector.veh_speed_1",
			"element.type.param.measure.detector.veh_speed_2", "element.type.param.measure.detector.veh_speed_3",
			"element.type.param.measure.detector.veh_speed_4", "element.type.param.measure.detector.veh_speed_5",
			"element.type.param.measure.detector.veh_speed_6", "element.type.param.measure.detector.veh_speed_7",
			"element.type.param.measure.detector.veh_speed_8", "element.type.param.measure.detector.veh_speed_9",
			"element.type.param.measure.detector.veh_speed_10", "element.type.param.measure.detector.veh_speed_11",
			"element.type.param.measure.detector.veh_speed_12", "element.type.param.measure.detector.veh_speed_13",
			"element.type.param.measure.detector.veh_speed_14", "element.type.param.measure.detector.veh_speed_15",
			"element.type.param.measure.detector.veh_speed_16" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehSpeed;

	@AElementTypeParam(references = { "element.type.param.measure.detector.veh_pa_1",
			"element.type.param.measure.detector.veh_pa_2", "element.type.param.measure.detector.veh_pa_3",
			"element.type.param.measure.detector.veh_pa_4", "element.type.param.measure.detector.veh_pa_5",
			"element.type.param.measure.detector.veh_pa_6", "element.type.param.measure.detector.veh_pa_7",
			"element.type.param.measure.detector.veh_pa_8", "element.type.param.measure.detector.veh_pa_9",
			"element.type.param.measure.detector.veh_pa_10", "element.type.param.measure.detector.veh_pa_11",
			"element.type.param.measure.detector.veh_pa_12",
			"element.type.param.measure.detector.veh_pa_13" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehPa;

	@AElementTypeParam(references = { "element.type.param.measure.detector.veh_length_1_percent",
			"element.type.param.measure.detector.veh_length_2_percent",
			"element.type.param.measure.detector.veh_length_3_percent",
			"element.type.param.measure.detector.veh_length_4_percent",
			"element.type.param.measure.detector.veh_length_5_percent",
			"element.type.param.measure.detector.veh_length_6_percent",
			"element.type.param.measure.detector.veh_length_7_percent",
			"element.type.param.measure.detector.veh_length_8_percent",
			"element.type.param.measure.detector.veh_length_9_percent",
			"element.type.param.measure.detector.veh_length_10_percent",
			"element.type.param.measure.detector.veh_length_11_percent",
			"element.type.param.measure.detector.veh_length_12_percent",
			"element.type.param.measure.detector.veh_length_13_percent",
			"element.type.param.measure.detector.veh_length_14_percent",
			"element.type.param.measure.detector.veh_length_15_percent",
			"element.type.param.measure.detector.veh_length_16_percent" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehLengthPercent;

	@AElementTypeParam(references = { "element.type.param.measure.detector.veh_speed_1_percent",
			"element.type.param.measure.detector.veh_speed_2_percent",
			"element.type.param.measure.detector.veh_speed_3_percent",
			"element.type.param.measure.detector.veh_speed_4_percent",
			"element.type.param.measure.detector.veh_speed_5_percent",
			"element.type.param.measure.detector.veh_speed_6_percent",
			"element.type.param.measure.detector.veh_speed_7_percent",
			"element.type.param.measure.detector.veh_speed_8_percent",
			"element.type.param.measure.detector.veh_speed_9_percent",
			"element.type.param.measure.detector.veh_speed_10_percent",
			"element.type.param.measure.detector.veh_speed_11_percent",
			"element.type.param.measure.detector.veh_speed_12_percent",
			"element.type.param.measure.detector.veh_speed_13_percent",
			"element.type.param.measure.detector.veh_speed_14_percent",
			"element.type.param.measure.detector.veh_speed_15_percent",
			"element.type.param.measure.detector.veh_speed_16_percent" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehSpeedPercent;

	@AElementTypeParam(references = { "element.type.param.measure.detector.veh_pa_1_percent",
			"element.type.param.measure.detector.veh_pa_2_percent",
			"element.type.param.measure.detector.veh_pa_3_percent",
			"element.type.param.measure.detector.veh_pa_4_percent",
			"element.type.param.measure.detector.veh_pa_5_percent",
			"element.type.param.measure.detector.veh_pa_6_percent",
			"element.type.param.measure.detector.veh_pa_7_percent",
			"element.type.param.measure.detector.veh_pa_8_percent",
			"element.type.param.measure.detector.veh_pa_9_percent",
			"element.type.param.measure.detector.veh_pa_10_percent",
			"element.type.param.measure.detector.veh_pa_11_percent",
			"element.type.param.measure.detector.veh_pa_12_percent",
			"element.type.param.measure.detector.veh_pa_13_percent" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehPaPercent;

	// Configuration parameters
	@AElementTypeParam(references = {
			"element.type.param.config.detector.direction" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Boolean direction;

	@AElementTypeParam(references = {
			"element.type.param.config.detector.order" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Integer order;

	@AElementTypeParam(references = {
			"element.type.param.config.detector.mask_validation" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Integer maskValidation;

	@AElementTypeParam(references = {
			"element.type.param.config.detector.delay" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Integer delay;

	public Detector() {

		if (VEH_LENGTH == null) {

			Field field = ClassesUtil.getField(this.getClass(), "vehLength");

			if (field != null && field.isAnnotationPresent(AElementTypeParam.class)) {

				AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);
				String[] references = aElementTypeParam.references();

				if (references != null) {

					VEH_LENGTH = references.length;
				}
			}
		}
		if (VEH_LENGTH != null) {

			vehLength = new ArrayList<>(Collections.nCopies(VEH_LENGTH, null));
			vehLengthPercent = new ArrayList<>(Collections.nCopies(VEH_LENGTH, null));
		}

		if (VEH_SPEED == null) {

			Field field = ClassesUtil.getField(this.getClass(), "vehSpeed");

			if (field != null && field.isAnnotationPresent(AElementTypeParam.class)) {

				AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);
				String[] references = aElementTypeParam.references();

				if (references != null) {

					VEH_SPEED = references.length;
				}
			}
		}
		if (VEH_SPEED != null) {

			vehSpeed = new ArrayList<>(Collections.nCopies(VEH_SPEED, null));
			vehSpeedPercent = new ArrayList<>(Collections.nCopies(VEH_SPEED, null));
		}

		if (VEH_PA == null) {

			Field field = ClassesUtil.getField(this.getClass(), "vehPa");

			if (field != null && field.isAnnotationPresent(AElementTypeParam.class)) {

				AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);
				String[] references = aElementTypeParam.references();

				if (references != null) {

					VEH_PA = references.length;
				}
			}
		}
		if (VEH_PA != null) {

			vehPa = new ArrayList<>(Collections.nCopies(VEH_PA, null));
			vehPaPercent = new ArrayList<>(Collections.nCopies(VEH_PA, null));
		}
	}
}
