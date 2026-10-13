package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * Defines level of incidents
 * 
 * @author aborrajo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class ImsIncidentLevel extends BasicElement {
	
	private static final long serialVersionUID = -3166439535016524163L;
	
	private Integer priority;
	
	private Long pmQueuePriorityId;
	private Long pmQueueId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"pmQueuePriorityId=id", "pmQueueId=queueId"})
	private PmQueuePriority pmQueuePriority;

}
