package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity

public class SmStateMachineVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -3508003737135401207L;

	@EqualsAndHashCode.Exclude
	private List<SmThresholdVO> smThresholds;
	
}
