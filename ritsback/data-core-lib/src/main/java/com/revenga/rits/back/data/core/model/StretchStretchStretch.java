package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Defines Stretch
 * 
 * @author mamonzons
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"stretchStretchId", "stretchId"})
public class StretchStretchStretch extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6845083331483630946L;

	private Long stretchStretchId;
	private Long stretchId;
	
	
}
