package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class CommandElementValueVO extends BasicElementVO {
	/**
	* 
	*/
	private static final long serialVersionUID = -1247364292345275391L;
	/**
	* 
	*/

	private String commandElementTypeParamId;
	private String value;
	
	@EqualsAndHashCode.Exclude
	private CommandElementTypeParamVO commandElementTypeParam;
}
