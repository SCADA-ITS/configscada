package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Defines values for element
 * 
 * @author aborrajo
 * 
 */
@Getter
@Setter
@ToString
@PrimaryKeys(fields = { "elementTypeId", "elementId", "elementTypeParamId", "paramTypeId" })
public class ElementValue extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6599713631247785087L;
	private Long elementTypeId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeId=elementTypeId", "paramTypeId=paramTypeId", "elementTypeParamId=id" })
	private ElementTypeParam elementTypeParam;
	private Long elementTypeParamId;

	private Long paramTypeId;

	private Long elementId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Element element;
	
	private String value;

	private Long elementValueStateId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<AlarmConfig> alarmConfigMeasureConfig;

	public ElementValue() {
		super();
	}
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
