package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class AlarmLevelVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7358481434200735900L;
}
