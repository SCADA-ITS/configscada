package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"elementTypeId", "stateId"})
public class ElementTypeState extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2449767741216252915L;

	public static final long STATE_UNKNOWN = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.state.unknown"));
	
	public static final long STATE_UP = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.state.up"));
	
	public static final long STATE_DOWN = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.state.down"));
	
	public static final long STATE_ALARMS = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.state.alarms"));
	
	public static final long STATE_STOPPED = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.state.stopped"));
	
	public static final long STATE_EVACUATION = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.state.evacuation"));
	
	public static final long STATE_EXTRACTION = Integer.valueOf(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "element.state.extraction"));
	
	private Long elementTypeId;
	private Long stateId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<ElementTypeState> transitions;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<ElementStateAlarm> elementStateAlarms;
}
