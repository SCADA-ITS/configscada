package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class RoleVO extends BasicElementVO {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 5122677781451888858L;

	private Boolean preset;
	
	@EqualsAndHashCode.Exclude
	protected List<SystemPermissionVO> rolePermissions;
	
	@EqualsAndHashCode.Exclude
	protected List<String> rolePermissionIds;
}
