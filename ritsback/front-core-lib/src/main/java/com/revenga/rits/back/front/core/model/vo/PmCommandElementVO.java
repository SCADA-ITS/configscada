package com.revenga.rits.back.front.core.model.vo;


import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PmCommandElementVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2955907486682589441L;

	private CommandElementTypeVO commandElementType;
	private List<CommandElementValueVO> commandElementValues;
	private PmQueuePriorityVO priority;
	private Long startTime;
	private Long endTime;
	private Boolean enable;
	
	private String commandElementTypeId;
	private String elementId;
	private String priorityId;
	private String source;
	private Boolean conflict;
}
