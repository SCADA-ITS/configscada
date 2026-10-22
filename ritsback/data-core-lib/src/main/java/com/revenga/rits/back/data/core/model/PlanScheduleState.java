package com.revenga.rits.back.data.core.model;



import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"planScheduleId"})
public class PlanScheduleState extends BasicElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9210147360926451932L;
	
	private Long planScheduleId;
	private Long planScheduleStateTypeId;
	private Long userId;
	
	public PlanScheduleState() {
		super();
	}

}
