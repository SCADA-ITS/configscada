package com.revenga.rits.back.traffic.manager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.traffic.manager.annotation.AElementId;
import com.revenga.rits.back.traffic.manager.annotation.AElementTypeParam;
import java.lang.reflect.Field;

import lombok.Data;

@Data
public class Section {

	public static final Long ELEMENT_TYPE = Long
			.parseLong(ConfigProperty.getInstance().getProperty("application.properties", "element.type.section"));

	public static final Long STATE_UNKNOWN = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.section.unknown"));

	public static final Long STATE_VALID = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.section.valid"));

	public static final Long STATE_INVALID = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.section.invalid"));

	public static final Long STATE_ALARMS = Long.parseLong(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.section.alarms"));

	public static Integer CAT_VEH_LENGTH = null;
	public static Integer CAT_VEH_SPEED = null;
	public static Integer CAT_VEH_PA = null;

	@AElementId
	private Long id;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.date" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Long date;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.period" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer period;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.current_direction" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Boolean currentDirection;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.n_vehicles" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer nVehicles;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.intensity" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer intensity;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.occupancy" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer occupancy;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.congestion" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Boolean congestion;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.kamikaze" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Boolean kamikaze;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.speed" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer speed;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.gap" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer gap;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.headway" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer headway;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.los" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer los;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.los_percent" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer losPercent;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.quality" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer quality;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.flow" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer flow;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.density" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer density;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.adt_hourly" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer adtHourly;

	@AElementTypeParam(references = {
			"element.type.param.measure.section.length" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer length;

	@AElementTypeParam(references = { "element.type.param.measure.section.veh_length_1",
			"element.type.param.measure.section.veh_length_2", 
			"element.type.param.measure.section.veh_length_3",
			"element.type.param.measure.section.veh_length_4", 
			"element.type.param.measure.section.veh_length_5",
			"element.type.param.measure.section.veh_length_6", 
			"element.type.param.measure.section.veh_length_7",
			"element.type.param.measure.section.veh_length_8", 
			"element.type.param.measure.section.veh_length_9",
			"element.type.param.measure.section.veh_length_10", 
			"element.type.param.measure.section.veh_length_11",
			"element.type.param.measure.section.veh_length_12", 
			"element.type.param.measure.section.veh_length_13",
			"element.type.param.measure.section.veh_length_14", 
			"element.type.param.measure.section.veh_length_15",
			"element.type.param.measure.section.veh_length_16" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehLength;

	@AElementTypeParam(references = { "element.type.param.measure.section.veh_speed_1",
			"element.type.param.measure.section.veh_speed_2", 
			"element.type.param.measure.section.veh_speed_3",
			"element.type.param.measure.section.veh_speed_4", 
			"element.type.param.measure.section.veh_speed_5",
			"element.type.param.measure.section.veh_speed_6", 
			"element.type.param.measure.section.veh_speed_7",
			"element.type.param.measure.section.veh_speed_8", 
			"element.type.param.measure.section.veh_speed_9",
			"element.type.param.measure.section.veh_speed_10", 
			"element.type.param.measure.section.veh_speed_11",
			"element.type.param.measure.section.veh_speed_12", 
			"element.type.param.measure.section.veh_speed_13",
			"element.type.param.measure.section.veh_speed_14", 
			"element.type.param.measure.section.veh_speed_15",
			"element.type.param.measure.section.veh_speed_16" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehSpeed;

	@AElementTypeParam(references = { "element.type.param.measure.section.veh_pa_1",
			"element.type.param.measure.section.veh_pa_2", 
			"element.type.param.measure.section.veh_pa_3",
			"element.type.param.measure.section.veh_pa_4", 
			"element.type.param.measure.section.veh_pa_5",
			"element.type.param.measure.section.veh_pa_6", 
			"element.type.param.measure.section.veh_pa_7",
			"element.type.param.measure.section.veh_pa_8", 
			"element.type.param.measure.section.veh_pa_9",
			"element.type.param.measure.section.veh_pa_10", 
			"element.type.param.measure.section.veh_pa_11",
			"element.type.param.measure.section.veh_pa_12",
			"element.type.param.measure.section.veh_pa_13" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehPa;

	@AElementTypeParam(references = { "element.type.param.measure.section.veh_length_1_percent",
			"element.type.param.measure.section.veh_length_2_percent",
			"element.type.param.measure.section.veh_length_3_percent",
			"element.type.param.measure.section.veh_length_4_percent",
			"element.type.param.measure.section.veh_length_5_percent",
			"element.type.param.measure.section.veh_length_6_percent",
			"element.type.param.measure.section.veh_length_7_percent",
			"element.type.param.measure.section.veh_length_8_percent",
			"element.type.param.measure.section.veh_length_9_percent",
			"element.type.param.measure.section.veh_length_10_percent",
			"element.type.param.measure.section.veh_length_11_percent",
			"element.type.param.measure.section.veh_length_12_percent",
			"element.type.param.measure.section.veh_length_13_percent",
			"element.type.param.measure.section.veh_length_14_percent",
			"element.type.param.measure.section.veh_length_15_percent",
			"element.type.param.measure.section.veh_length_16_percent" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehLengthPercent;

	@AElementTypeParam(references = { "element.type.param.measure.section.veh_speed_1_percent",
			"element.type.param.measure.section.veh_speed_2_percent",
			"element.type.param.measure.section.veh_speed_3_percent",
			"element.type.param.measure.section.veh_speed_4_percent",
			"element.type.param.measure.section.veh_speed_5_percent",
			"element.type.param.measure.section.veh_speed_6_percent",
			"element.type.param.measure.section.veh_speed_7_percent",
			"element.type.param.measure.section.veh_speed_8_percent",
			"element.type.param.measure.section.veh_speed_9_percent",
			"element.type.param.measure.section.veh_speed_10_percent",
			"element.type.param.measure.section.veh_speed_11_percent",
			"element.type.param.measure.section.veh_speed_12_percent",
			"element.type.param.measure.section.veh_speed_13_percent",
			"element.type.param.measure.section.veh_speed_14_percent",
			"element.type.param.measure.section.veh_speed_15_percent",
			"element.type.param.measure.section.veh_speed_16_percent" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehSpeedPercent;

	@AElementTypeParam(references = { "element.type.param.measure.section.veh_pa_1_percent",
			"element.type.param.measure.section.veh_pa_2_percent",
			"element.type.param.measure.section.veh_pa_3_percent",
			"element.type.param.measure.section.veh_pa_4_percent",
			"element.type.param.measure.section.veh_pa_5_percent",
			"element.type.param.measure.section.veh_pa_6_percent",
			"element.type.param.measure.section.veh_pa_7_percent",
			"element.type.param.measure.section.veh_pa_8_percent",
			"element.type.param.measure.section.veh_pa_9_percent",
			"element.type.param.measure.section.veh_pa_10_percent",
			"element.type.param.measure.section.veh_pa_11_percent",
			"element.type.param.measure.section.veh_pa_12_percent",
			"element.type.param.measure.section.veh_pa_13_percent" }, paramType = AElementTypeParam.paramType.MEASURE)
	private List<Integer> vehPaPercent;

	@AElementTypeParam(references = {
			"element.type.param.config.section.virtual" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Boolean virtual;

	@AElementTypeParam(references = {
			"element.type.param.config.section.los_type" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Integer losType;

	@AElementTypeParam(references = {
			"element.type.param.config.section.los_soften" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Boolean losSoften;

	@AElementTypeParam(references = {
			"element.type.param.config.section.los_flatten" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Boolean losFlatten;

	@AElementTypeParam(references = {
			"element.type.param.config.section.los_threshold_speed" }, paramType = AElementTypeParam.paramType.CONFIG)
	private List<Integer> losThresholdSpeed;

	@AElementTypeParam(references = {
			"element.type.param.config.section.los_threshold_occupancy" }, paramType = AElementTypeParam.paramType.CONFIG)
	private List<Integer> losThresholdOccupancy;
	
	@AElementTypeParam(references = {
	"element.type.param.config.section.pk" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Double pk;

	@AElementTypeParam(references = { "element.type.param.config.section.cat_veh_length_1",
			"element.type.param.config.section.cat_veh_length_2", 
			"element.type.param.config.section.cat_veh_length_3",
			"element.type.param.config.section.cat_veh_length_4", 
			"element.type.param.config.section.cat_veh_length_5",
			"element.type.param.config.section.cat_veh_length_6", 
			"element.type.param.config.section.cat_veh_length_7",
			"element.type.param.config.section.cat_veh_length_8", 
			"element.type.param.config.section.cat_veh_length_9",
			"element.type.param.config.section.cat_veh_length_10",
			"element.type.param.config.section.cat_veh_length_11",
			"element.type.param.config.section.cat_veh_length_12",
			"element.type.param.config.section.cat_veh_length_13",
			"element.type.param.config.section.cat_veh_length_14",
			"element.type.param.config.section.cat_veh_length_15",
			"element.type.param.config.section.cat_veh_length_16" }, paramType = AElementTypeParam.paramType.CONFIG)
	private List<List<Integer>> catVehLength;

	@AElementTypeParam(references = { "element.type.param.config.section.cat_veh_speed_1",
			"element.type.param.config.section.cat_veh_speed_2", 
			"element.type.param.config.section.cat_veh_speed_3",
			"element.type.param.config.section.cat_veh_speed_4", 
			"element.type.param.config.section.cat_veh_speed_5",
			"element.type.param.config.section.cat_veh_speed_6", 
			"element.type.param.config.section.cat_veh_speed_7",
			"element.type.param.config.section.cat_veh_speed_8", 
			"element.type.param.config.section.cat_veh_speed_9",
			"element.type.param.config.section.cat_veh_speed_10", 
			"element.type.param.config.section.cat_veh_speed_11",
			"element.type.param.config.section.cat_veh_speed_12", 
			"element.type.param.config.section.cat_veh_speed_13",
			"element.type.param.config.section.cat_veh_speed_14", 
			"element.type.param.config.section.cat_veh_speed_15",
			"element.type.param.config.section.cat_veh_speed_16" }, paramType = AElementTypeParam.paramType.CONFIG)
	private List<List<Integer>> catVehSpeed;

	@AElementTypeParam(references = { "element.type.param.config.section.cat_veh_pa_1",
			"element.type.param.config.section.cat_veh_pa_2", 
			"element.type.param.config.section.cat_veh_pa_3",
			"element.type.param.config.section.cat_veh_pa_4", 
			"element.type.param.config.section.cat_veh_pa_5",
			"element.type.param.config.section.cat_veh_pa_6", 
			"element.type.param.config.section.cat_veh_pa_7",
			"element.type.param.config.section.cat_veh_pa_8", 
			"element.type.param.config.section.cat_veh_pa_9",
			"element.type.param.config.section.cat_veh_pa_10", 
			"element.type.param.config.section.cat_veh_pa_11",
			"element.type.param.config.section.cat_veh_pa_12", 
			"element.type.param.config.section.cat_veh_pa_13" }, paramType = AElementTypeParam.paramType.CONFIG)
	private List<List<Integer>> catVehPa;

	private List<Detector> detectors;

	private Integer nlos;

	public Section() {

		if (CAT_VEH_LENGTH == null) {

			Field field = ClassesUtil.getField(this.getClass(), "catVehLength");

			if (field != null && field.isAnnotationPresent(AElementTypeParam.class)) {

				AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);
				String[] references = aElementTypeParam.references();

				if (references != null) {

					CAT_VEH_LENGTH = references.length;
				}
			}
		}
		if (CAT_VEH_LENGTH != null) {

			catVehLength = new ArrayList<>(Collections.nCopies(CAT_VEH_LENGTH, null));
			vehLength = new ArrayList<>(Collections.nCopies(CAT_VEH_LENGTH, null));
			vehLengthPercent = new ArrayList<>(Collections.nCopies(CAT_VEH_LENGTH, null));
		}

		if (CAT_VEH_SPEED == null) {

			Field field = ClassesUtil.getField(this.getClass(), "catVehSpeed");

			if (field != null && field.isAnnotationPresent(AElementTypeParam.class)) {

				AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);
				String[] references = aElementTypeParam.references();

				if (references != null) {

					CAT_VEH_SPEED = references.length;
				}
			}
		}
		if (CAT_VEH_SPEED != null) {

			catVehSpeed = new ArrayList<>(Collections.nCopies(CAT_VEH_SPEED, null));
			vehSpeed = new ArrayList<>(Collections.nCopies(CAT_VEH_SPEED, null));
			vehSpeedPercent = new ArrayList<>(Collections.nCopies(CAT_VEH_SPEED, null));
		}
		
		if (CAT_VEH_PA == null) {

			Field field = ClassesUtil.getField(this.getClass(), "catVehPa");

			if (field != null && field.isAnnotationPresent(AElementTypeParam.class)) {

				AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);
				String[] references = aElementTypeParam.references();

				if (references != null) {

					CAT_VEH_PA = references.length;
				}
			}
		}
		if (CAT_VEH_PA != null) {

			catVehPa = new ArrayList<>(Collections.nCopies(CAT_VEH_PA, null));
			vehPa = new ArrayList<>(Collections.nCopies(CAT_VEH_PA, null));
			vehPaPercent = new ArrayList<>(Collections.nCopies(CAT_VEH_PA, null));
		}

	}
}
