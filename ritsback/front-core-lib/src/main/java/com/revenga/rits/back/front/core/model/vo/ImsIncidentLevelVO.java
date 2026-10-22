package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentLevelVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -1815189715991577260L;
	
	private Integer priority;
	
	@EqualsAndHashCode.Exclude
	private PmQueuePriorityVO pmQueuePriority;

}
