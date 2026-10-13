package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines group of incident
 * 
 * @author aborrajo
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ImsExtIncidentType extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
	
	private Long incidentTypeId;
	private Boolean confirmationIsNeeded;
	private Long  extIncidentGroupId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"incidentTypeId=id"})
	private ImsIncidentType incidentType;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"extIncidentGroupId=id"})
	private ImsExtIncidentGroup extIncidentGroup;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=extIncidentTypeId"})
	private List<ImsExtIncidentTypeParam> extIncidentTypeParams;
}
