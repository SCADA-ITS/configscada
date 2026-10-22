package com.revenga.rits.back.front.core.model.vo;


import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class PmQueueVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2955907486682589441L;
	
	@EqualsAndHashCode.Exclude
	private List<PmQueuePriorityVO> priorities;
	
	@EqualsAndHashCode.Exclude
	private List<PmCommandElementVO> commandElements;
}
