package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ImsIncidentTaskPlan extends BasicElement implements Comparable<ImsIncidentTaskPlan>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;

	private Long incidentTypeTaskId;
	private Long incidentTypeId;
	private Long stretchId;
	private Long planId;
	private Integer position;
	
	@Override
	public int compareTo(ImsIncidentTaskPlan incidentTaskPlan) {

		return (incidentTaskPlan == null || incidentTaskPlan.getPosition() == null) ? 1
				: (this.position == null ? -1 : this.position.compareTo(incidentTaskPlan.getPosition()));
	}

}
