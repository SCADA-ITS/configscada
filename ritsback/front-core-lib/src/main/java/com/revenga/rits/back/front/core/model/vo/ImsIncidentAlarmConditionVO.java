package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentAlarmConditionVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 2460412391488362959L;
	
	private String incidentTypeId;
	private String stretchId;
	private Boolean auto;
	private String roadImpactId;
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentTypeVO incidentType;
	
	@EqualsAndHashCode.Exclude
	private StretchVO stretch;
	
	@EqualsAndHashCode.Exclude
	private List <ImsIncidentAlarmConditionAlarmVO> incidentAlarmConditionAlarms;
	
	@EqualsAndHashCode.Exclude
	private RoadImpactVO roadImpact;

}
