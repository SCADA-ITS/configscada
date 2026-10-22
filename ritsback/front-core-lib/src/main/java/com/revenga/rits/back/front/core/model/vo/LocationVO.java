package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class LocationVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2525072578233411108L;

	private String locationTypeId;
	private Integer lane;
	
	@EqualsAndHashCode.Exclude
	private LocationTypeVO locationType;

	@EqualsAndHashCode.Exclude
	private List<StretchLocationVO> stretchLocations;
	
	@EqualsAndHashCode.Exclude
	private List<StretchVO> stretchs;

}
