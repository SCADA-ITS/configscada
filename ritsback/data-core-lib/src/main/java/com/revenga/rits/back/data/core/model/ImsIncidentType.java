package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ImsIncidentType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
	
	private Long iniIncidentStateId;
	private Long incidentGroupId;
	private Long incidentLevelId;
	private Long defaultUserId;
	private Integer autoConfirmIncident;
	private Integer validForMin;
	private Boolean cancelIncident;

	
	@EqualsAndHashCode.Exclude
	@AssociatedKeys(references = {"id=incidentTypeId"})
	private List<ImsIncidentTypeCause> incidentTypeCauses;
	
}