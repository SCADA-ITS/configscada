package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AuditLogVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7364108937412292441L;
	
	private String logTypeId;
	private String logSubtypeId;
	private String userId;
	
	private Long time;
	private LogLevelEnum level;
	
	@EqualsAndHashCode.Exclude
	private List<AuditLogValueVO> auditLogValues;
	
	@EqualsAndHashCode.Exclude
	private LogTypeVO logType;
	
	@EqualsAndHashCode.Exclude
	private LogSubtypeVO logSubtype;
	
	@EqualsAndHashCode.Exclude
	private UserVO user;
}
