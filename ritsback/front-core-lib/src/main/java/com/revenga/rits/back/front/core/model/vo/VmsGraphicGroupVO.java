package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class VmsGraphicGroupVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2873796844412568753L;
	
	
	@EqualsAndHashCode.Exclude
	private List<VmsGraphicVO> graphics;
}
