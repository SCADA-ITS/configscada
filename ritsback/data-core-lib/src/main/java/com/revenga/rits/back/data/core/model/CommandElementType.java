package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id", "elementTypeId" })
public class CommandElementType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3840593541357962628L;

	private Long elementTypeId;

	private ElementType elementType;

	private String onChange;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "id=commandElementTypeId" })
	private List<CommandElementTypeParam> commandElementTypeParams;
	
	private Boolean pmQueueCompatibility;
}
