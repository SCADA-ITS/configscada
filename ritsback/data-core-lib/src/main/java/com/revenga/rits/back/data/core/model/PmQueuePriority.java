package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "queueId", "id" })
public class PmQueuePriority extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3133997270915196731L;

	private Long queueId;
	private Integer priority;
	private Boolean defaultPriority;
	private Boolean planPriority;
	private Boolean incidentPriority;
	private Boolean travelTimePriority;
	private Boolean checkConflict;
}
