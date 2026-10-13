package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class SpathStretchVO extends BasicElementVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3462557930171957215L;

	private Integer position;
	
	@EqualsAndHashCode.Exclude
	private SpathVO spath;
	
	@EqualsAndHashCode.Exclude
	private StretchVO stretch;
	
	@EqualsAndHashCode.Exclude
	private StretchVO relStretch;
	
}
