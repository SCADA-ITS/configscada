package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class I18nLabelVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 703840603071572916L;
	
	private String label;
	private String translation;
}
