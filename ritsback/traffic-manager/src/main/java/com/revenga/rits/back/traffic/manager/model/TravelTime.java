package com.revenga.rits.back.traffic.manager.model;

import java.util.List;

import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.traffic.manager.annotation.AElementId;
import com.revenga.rits.back.traffic.manager.annotation.AElementTypeParam;

import lombok.Data;

@Data
public class TravelTime {

	public static final Long ELEMENT_TYPE = Long
			.parseLong(ConfigProperty.getInstance().getProperty("application.properties", "element.type.travel_time"));

	public static final Integer STATE_UNKNOWN = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.travel_time.unknown"));

	public static final Integer STATE_VALID = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.travel_time.valid"));

	public static final Integer STATE_INVALID = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.travel_time.invalid"));

	public static final Integer STATE_ALARMS = Integer.parseInt(
			ConfigProperty.getInstance().getProperty("application.properties", "element.type.state.travel_time.alarms"));

	@AElementId
	private Long id;

	@AElementTypeParam(references = {
			"element.type.param.measure.travel_time.date" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Long date;

	@AElementTypeParam(references = {
			"element.type.param.measure.travel_time.time_min" }, paramType = AElementTypeParam.paramType.MEASURE)
	private Integer timeMin;
	
	@AElementTypeParam(references = {
			"element.type.param.config.travel_time.direction" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Boolean direction;

	@AElementTypeParam(references = {
			"element.type.param.config.travel_time.pk_ini" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Double pkIni;

	@AElementTypeParam(references = {
			"element.type.param.config.travel_time.pk_end" }, paramType = AElementTypeParam.paramType.CONFIG)
	private Double pkEnd;


	private List<Section> sections;
}
