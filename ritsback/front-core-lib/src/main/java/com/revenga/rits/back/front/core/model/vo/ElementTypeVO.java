package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementTypeVO extends BasicElementVO {

	private static final long serialVersionUID = 3336929767701556043L;

	@EqualsAndHashCode.Exclude
	private List<ElementTypeParamVO> elementTypeParams;
	
	private String initialStateId;
	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO initialState;

	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO maintenanceState;
	
	private Boolean isLogic;
	
	@EqualsAndHashCode.Exclude
	private List<CommandElementTypeVO> commandElementTypes;
		
	@EqualsAndHashCode.Exclude
	private ElementGroupVO elementGroup;
	
	@EqualsAndHashCode.Exclude
	private List<ElementTypeVO> parents;
	
	@EqualsAndHashCode.Exclude
	private List<ElementTypeStateVO> elementTypeStates;
	
	@EqualsAndHashCode.Exclude
	private List<ElementSubtypeVO> elementSubtypes;
	
	@EqualsAndHashCode.Exclude
	private List<PmQueuePriorityVO> priorities;
}
