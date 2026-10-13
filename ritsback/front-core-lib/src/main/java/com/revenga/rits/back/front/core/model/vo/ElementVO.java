package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import com.revenga.rits.back.front.core.annotation.TranslatedField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementVO extends BasicElementVO {
	/**
	* 
	*/
	private static final long serialVersionUID = -715114996914795331L;

	protected Long state;
	/**
	 * 
	 * stateIdLabel = "ElementVO.stateLabel" + elementTypeId + stateId;
	 */
	
	@TranslatedField(references = {"elementTypeId", "state"})
	protected String stateLabel;

	@EqualsAndHashCode.Exclude
	protected LocationVO location;
	
	private String locationId;
	
	private Boolean keepValuesOnDisconnected;

	@EqualsAndHashCode.Exclude
	protected ElementTypeVO elementType;
	
	@EqualsAndHashCode.Exclude
	protected ElementSubtypeVO elementSubtype;
	
	protected String elementTypeId;

	@EqualsAndHashCode.Exclude
	private List<ElementValueVO> elementValues;
	
	@EqualsAndHashCode.Exclude
	private List<ElementVO> childs;
	
	@EqualsAndHashCode.Exclude
	private List<ElementVO> parents;
	
	@EqualsAndHashCode.Exclude
	private List<String> parentIds;

	@EqualsAndHashCode.Exclude
	private List<AlarmVO> alarms;
	
	@EqualsAndHashCode.Exclude
	private ElementTypeStateVO elementTypeState;
	
	@EqualsAndHashCode.Exclude
	private List<CommandElementTypeVO> commandElements;
	
	@EqualsAndHashCode.Exclude
	private List<VmsGraphicVO> graphics;	
	
	@EqualsAndHashCode.Exclude
	private List<VmsGraphicGroupVO> graphicGroups;	
	
	@EqualsAndHashCode.Exclude
	private PmQueueVO queue;
	
}
