package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentSequenceStateVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 9123593578043214283L;
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentStateVO state;
	
	@EqualsAndHashCode.Exclude
	private List <ImsIncidentReportTaskStateVO> taskStates;

}
