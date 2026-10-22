package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportLogVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = -8941794161693648692L;

	private String incidentReportId;
	private String userId;
	private String logSubtypeId;
	private LogLevelEnum level;

	private Long time;
	
	@EqualsAndHashCode.Exclude
	protected UserVO user;
	
	@EqualsAndHashCode.Exclude
	private LogSubtypeVO logSubtype;
	
	@EqualsAndHashCode.Exclude
	private List<ImsIncidentReportLogValueVO> incidentReportLogValues;
		
}
