package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import com.revenga.rits.back.data.core.model.Stretch;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentTypeTaskVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 7792635253597434766L;
	
	private String incidentTypeId;
	private String incidentStateId;
	private String stretchId;
	private String taskTypeId;
	private String operationDescription;
	private Boolean mandatory;
	private Integer position;
	private Boolean auto;
	private String onChange;
	private Integer delay;
	private Boolean rerun;
	private String roadImpactId;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentTypeVO incidentType;
	
	@EqualsAndHashCode.Exclude
	protected StretchVO stretch;
	
	@EqualsAndHashCode.Exclude
	protected ImsTaskTypeVO taskType;
	
	@EqualsAndHashCode.Exclude
	protected ImsIncidentStateVO incidentState;
	
	@EqualsAndHashCode.Exclude
	private List <ImsIncidentTypeTaskValueVO> incidentTypeTaskValues;
	
	@EqualsAndHashCode.Exclude
	private RoadImpactVO roadImpact;

}
