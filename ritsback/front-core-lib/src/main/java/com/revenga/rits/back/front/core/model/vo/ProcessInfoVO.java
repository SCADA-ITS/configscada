package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity

public class ProcessInfoVO extends BasicElementVO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3867037296950294327L;
	
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
	protected Long createdAt;
	protected Long updatedAt;
	private String gitCommit;
	private String gitAuthor;
	private String gitDate;
	private String gitMessage;

}
