package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "commandElementTypeId", "elementTypeId", "commandElementTypeParamId", "commandElementId" })
public class CommandElementValue extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5859644687524343563L;

	private Long commandElementTypeId;
	private Long elementTypeId;
	private Long commandElementTypeParamId;
	private Long commandElementId;
	
	private String  value;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"commandElementTypeId=commandElementTypeId", "elementTypeId=elementTypeId", "commandElementTypeParamId=id"})
	private CommandElementTypeParam commandElementTypeParam;

}
