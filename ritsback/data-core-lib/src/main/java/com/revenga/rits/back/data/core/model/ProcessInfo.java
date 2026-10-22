package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"main"})
public class ProcessInfo extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698480416046131475L;

	private Long pid;
	private String main;
	private String commandLine;
	private Double cpuLoad;
	private Long cpuTime;
	private Double cpuAverage;
	private Long heapSize;
	private Long heapUsed;
	private Long heapMax;
	private Integer threadsLive;
	private Integer numRestarts;
	private Boolean ready;
	private String gitCommit;
	private String gitAuthor;
	private String gitDate;
	private String gitMessage;
}
