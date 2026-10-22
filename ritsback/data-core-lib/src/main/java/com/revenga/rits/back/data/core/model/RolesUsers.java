package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * @author Elias Testa
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"userId", "roleId"})
public class RolesUsers extends BasicElement {/**
	 * 
	 */
	private static final long serialVersionUID = 895870319752474010L;
	
	private Long userId;
	private Long roleId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"roleId=id"})
	private Role role;

}
