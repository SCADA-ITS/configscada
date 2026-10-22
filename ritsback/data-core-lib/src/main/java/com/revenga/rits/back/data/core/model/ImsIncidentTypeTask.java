package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
public class ImsIncidentTypeTask extends BasicElement implements Comparable<ImsIncidentTypeTask> {

	private static final long serialVersionUID = -3166439535016524163L;

	private Long taskTypeId;
	private Long incidentTypeId;
	private Long incidentStateId;
	private Long stretchId;

	private String operationDescription;

	private Boolean mandatory;
	private Integer position;
	private Boolean auto;
	private String onChange;
	private Long delay;
	private Boolean rerun;
	private Long roadImpactId;

	@Override
	public int compareTo(ImsIncidentTypeTask incidentTypeTask) {

		if (incidentTypeTask == null || incidentTypeTask.getPosition() == null) {
			
			return 1;
		}
		
		return this.position == null ? -1 : this.position.compareTo(incidentTypeTask.getPosition());
	}
}
