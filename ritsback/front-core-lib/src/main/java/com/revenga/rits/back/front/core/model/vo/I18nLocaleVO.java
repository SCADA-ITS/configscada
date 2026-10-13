package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class I18nLocaleVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3549061583327765184L;
	
	private String code;
	private String name;
	
	private List<I18nLabelVO> labels;
}
