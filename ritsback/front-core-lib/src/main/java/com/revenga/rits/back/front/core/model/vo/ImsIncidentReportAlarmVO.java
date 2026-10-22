package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsIncidentReportAlarmVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -910470683474494839L;

	private AlarmConfigVO alarmConfig;
	private ElementVO element;
	private Long activation;
}
