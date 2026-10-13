package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "queueId", "priorityId", "elementTypeId", "elementId"})
public class PmDisabledQueuePriority extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3133997270915196731L;

	private Long queueId;
	private Long priorityId;
	private Long elementTypeId;
	private Long elementId;
}
