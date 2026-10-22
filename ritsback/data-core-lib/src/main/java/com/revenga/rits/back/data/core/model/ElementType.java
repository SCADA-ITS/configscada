package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines type of element
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ElementType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;

	public static final long ELEMENT_TYPE_CC = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.cc"));

	public static final long ELEMENT_TYPE_TUBE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.tube"));

	public static final long ELEMENT_TYPE_VENTILATION = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.ventilation"));

	public static final long ELEMENT_TYPE_ILLUMINATION = Long.parseLong(
			ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.illumination"));

	public static final long ELEMENT_TYPE_FAN = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.fan"));
	
	public static final long ELEMENT_TYPE_DALI = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.dali"));
	
	public static final long ELEMENT_TYPE_CAMERA = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.camera"));
	
	public static final long ELEMENT_TYPE_ELECTRICAL_PANEL = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.electrical_panel"));
	
	public static final long ELEMENT_TYPE_DAI = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.dai"));
	
	public static final long ELEMENT_TYPE_CIRCUIT = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.circuit"));
	
	public static final long ELEMENT_TYPE_RTMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.rtms"));
	
	public static final long ELEMENT_TYPE_DETECTOR = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.detector"));
	
	public static final long ELEMENT_TYPE_VMS = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.vms"));
	
	public static final long ELEMENT_TYPE_TRAVEL_TIME = Long
			.parseLong(ConfigProperty.getInstance().getProperty(BasicElement.FILE_CONSTANTS, "element.type.travel_time"));
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=elementTypeId" })
	private List<ElementTypeParam> elementTypeParams;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=elementTypeId" })
	private List<ElementSubtype> elementSubtypes;

	private Long initialElementTypeStateId;
	private Long connectedElementTypeStateId;
	private Long disconnectedElementTypeStateId;
	private Long maintenanceElementTypeStateId;
	private Long elementGroupId;

	private Boolean isLogic;
	private Boolean checkIfConnected;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementGroupId=id" })
	private ElementGroup elementGroup;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=elementTypeId" })
	private List<ElementTypeState> elementTypeStates;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<AlarmConfig> alarmConfigs;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<AlarmConfig> alarmConfigsWithMeasures;
}
