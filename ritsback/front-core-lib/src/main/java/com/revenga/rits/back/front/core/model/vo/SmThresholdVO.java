package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class SmThresholdVO extends BasicElementVO {/**
	 * 
	 */
	private static final long serialVersionUID = 4308177897508127745L;

	String value;
	String dataTypeId;
	
	@EqualsAndHashCode.Exclude
	DataTypeVO dataType;
	
}
