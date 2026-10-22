package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommandElementErrorVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4168196699894936108L;

	/**
	 * 
	 */
	private Long error;
	private ElementVO element;
	private List<CommandElementPreconditionVO> commandElementPreconditions;
}
