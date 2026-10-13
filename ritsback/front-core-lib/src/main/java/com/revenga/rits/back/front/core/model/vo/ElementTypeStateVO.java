package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementTypeStateVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -2052385534514379192L;


	private String elementTypeId;
	private Long stateId;

}
