package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class CommandElementVO extends BasicElementVO {
	
	/**
	* 
	*/
	private static final long serialVersionUID = -1247364292345275391L;
	/**
	* 
	*/

	private String commandElementTypeId;
	private String elementId;

	@EqualsAndHashCode.Exclude
	private List<CommandElementValueVO> commandElementValues;
	
	@EqualsAndHashCode.Exclude
	private CommandElementTypeVO commandElementType;
	
	@EqualsAndHashCode.Exclude
	private ElementVO element;
}
