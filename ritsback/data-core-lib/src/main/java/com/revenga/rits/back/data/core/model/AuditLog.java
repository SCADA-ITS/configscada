package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.annotation.TimeStampField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * @author revenga
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id", "logTypeId" })
@DbConfig(db = "2")
public class AuditLog extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7516461123828477288L;

	private Long logTypeId;
	private Long logSubtypeId;
	private Long userId;

	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "userId=id" })
	private User user;
	
	@TimeStampField	
	private Long time;
	
	private Long levelId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = { "logTypeId=logTypeId", "id=auditLogId" })
	private List<AuditLogValue> auditLogValues;

}
