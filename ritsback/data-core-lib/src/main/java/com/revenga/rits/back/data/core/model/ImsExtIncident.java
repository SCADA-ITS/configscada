package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.annotation.TimeStampField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ImsExtIncident extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;
	
	private Long extIncidentTypeId;
	private Long locationId;

	@TimeStampField	
	private Long incidentInitDate;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"extIncidentTypeId=id"})
	private ImsExtIncidentType extIncidentType;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"locationId=id"})
	private Location location;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"extIncidentTypeId=extIncidentTypeId", "id=extIncidentId"})
	private List<ImsExtIncidentValue> extIncidentValues;
}

