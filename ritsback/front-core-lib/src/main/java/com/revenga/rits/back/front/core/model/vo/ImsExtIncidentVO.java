package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import com.revenga.rits.back.data.core.annotation.TimeStampField;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsExtIncidentVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;
	
	/* Input */
	private String extIncidentTypeId;
	private String locationId;

	/* Output */	
	@EqualsAndHashCode.Exclude
	private ImsExtIncidentTypeVO extIncidentType;
	
	@EqualsAndHashCode.Exclude
	private LocationVO location;
	
	/* All */
	@TimeStampField	
	private Long incidentInitDate;
	
	@EqualsAndHashCode.Exclude
	private List<ImsExtIncidentValueVO> extIncidentValues;
}

