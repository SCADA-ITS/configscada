package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Defines type of element
 * 
 * @author aborrajo
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeys(fields = {"moduleTypeId", "id"})
public class IOControllerModule extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7851309733561310318L;

	private Long moduleTypeId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"moduleTypeId=id"})
	private IOControllerModuleType moduleType;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"moduleTypeId=moduleTypeId", "id=moduleId"})
	private List<IOControllerModuleValue> moduleValues;
		
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"moduleTypeId=moduleTypeId", "id=moduleId"})
	private List<ElementIOControllerModule> moduleElements;
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}
}
