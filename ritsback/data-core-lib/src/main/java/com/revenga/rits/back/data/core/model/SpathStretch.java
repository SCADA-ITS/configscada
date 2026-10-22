package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines Spath
 * 
 * @author agomez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"spathId","stretchId","relStretchId"})
public class SpathStretch extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
	
	private Long spathId;
	private Long stretchId;
	private Long relStretchId;
	private Integer position;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"spathId=id"})
	private Spath spath;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"relStretchId=id"})
	private Stretch relStretch;
}
