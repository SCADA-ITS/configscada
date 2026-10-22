package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.IndexKey;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Inventory of alarms in the system
 * 
 * @author aborrajo
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeys(fields = {"id"})
public class AlarmConfig extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1908603455392811606L;
	
	Long alarmTypeId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"alarmTypeId=id"})
	protected AlarmType alarmType;

	Long alarmLevelId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"alarmLevelId=id"})
	protected AlarmLevel alarmLevel;

	Long elementTypeId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"elementTypeId=id"})
	protected ElementType elementType;
	
	Long elementSubtypeId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"elementSubtypeId=id", "elementTypeId=elementTypeId"})
	protected ElementSubtype elementSubtype;
	
	@IndexKey
	Long elementId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"elementId=id", "elementTypeId=elementTypeId"})
	protected Element element;
	
	Long elementTypeStateId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"elementTypeStateId=stateId", "elementTypeId=elementTypeId"})
	protected ElementTypeState elementTypeState;
	
	Integer activeCount;
	
	Boolean mine;
	Boolean treatment;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=alarmConfigId", "elementTypeId=elementTypeId"})
	private List<AlarmConfigMeasure> alarmConfigMeasures;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=alarmConfigId"})
	private List<AlarmTargetElement> alarmTargetElements;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"elementTypeId=elementTypeId", "elementTypeStateId=stateId"})
	private List<ElementStateAlarm> alarmConfigStates;
	
	String onCreateExecute;
	
	private Long minTimeAlarmed;
	private Long thresholdTime;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
