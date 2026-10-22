package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class StretchVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;

	private Double pkIni;
	private Double pkEnd;
	private Integer lanes;

	private String stretchTypeId;
	@EqualsAndHashCode.Exclude
	private StretchTypeVO stretchType;

	@EqualsAndHashCode.Exclude
	private RoadVO road;
	
	private Integer position;
}
