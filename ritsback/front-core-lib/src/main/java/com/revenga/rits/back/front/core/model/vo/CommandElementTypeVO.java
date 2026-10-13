package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class CommandElementTypeVO extends BasicElementVO {
	/**
	* 
	*/
	private static final long serialVersionUID = -1247364292345275391L;

	@EqualsAndHashCode.Exclude
	protected List<CommandElementTypeParamVO> commandElementTypeParams;
	
	@EqualsAndHashCode.Exclude
	protected List<CommandElementPreconditionVO> commandElementPreconditions;
	
	private Boolean pmQueueCompatibility;
}
