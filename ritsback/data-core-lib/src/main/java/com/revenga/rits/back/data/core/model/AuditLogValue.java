package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

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
@PrimaryKeys(fields = {"auditLogId", "logTypeId", "logParamId"})
@DbConfig(db = "2")
public class AuditLogValue extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4477494663485717422L;
	
	private Long logParamId;
	private Long logTypeId;
	private Long auditLogId;
	
	private String value;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"logTypeId=logTypeId", "logParamId=id"})
	private LogTypeParam logTypeParam;

}
