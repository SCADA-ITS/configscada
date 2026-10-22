package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentParamVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2315316855100043761L;
	
	private String incidentParamGroupId;
	
	@EqualsAndHashCode.Exclude
	private ImsIncidentParamGroupVO incidentParamGroup;
	
	private String dataTypeId;
	@EqualsAndHashCode.Exclude
	private DataTypeVO dataType;
	
	private String labelUnit;
	private String threshold1;
	private String threshold2;
	
	
	private List<ImsIncidentEnumParamVO> incidentEnumsParam;
	
}
