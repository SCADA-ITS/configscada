package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class GenericDataVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4696610720160017306L;

	private String value;
}
