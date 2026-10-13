package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines Stretch
 * 
 * @author agomez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class Stretch extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
	
	private Double pkIni;
	private Double pkEnd;
	private Integer lanes;
	private Long stretchTypeId;
	private Long roadId;
	
}
