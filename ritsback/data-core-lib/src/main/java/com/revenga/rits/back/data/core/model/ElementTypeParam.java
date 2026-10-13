package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Defines params of type of element
 * 
 * @author aborrajo
 * 
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeys(fields = { "elementTypeId", "paramTypeId", "id" })
public class ElementTypeParam extends BasicElement {
	
	private static final long serialVersionUID = 5859644687524343563L;

	private Long elementTypeId;

	private Long paramTypeId;

	private Boolean durable; 
	
	private String defaultValue;

	
	private Long dataTypeId;
	private String threshold1;
	private String threshold2;
	private Long elementTypeParamUnitId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeParamUnitId=id" })
	private ElementTypeParamUnit elementTypeParamUnit;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

}
