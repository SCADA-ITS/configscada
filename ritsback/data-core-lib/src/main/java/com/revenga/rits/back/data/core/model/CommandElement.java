package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "commandElementTypeId", "elementTypeId", "id" })
public class CommandElement extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4168196699894936108L;

	/**
	 * 
	 */

	private Long commandElementTypeId;
	private Long elementTypeId;
	private Long elementId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "commandElementTypeId=id"})
	private CommandElementType commandElementType;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "elementTypeId=elementTypeId", "elementId=id"})
	private Element element;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "commandElementTypeId=commandElementTypeId", "elementTypeId=elementTypeId", "id=commandElementId" })
	private List<CommandElementValue> commandElementValues;
}
