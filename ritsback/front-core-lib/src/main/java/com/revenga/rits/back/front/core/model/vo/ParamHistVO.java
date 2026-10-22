package com.revenga.rits.back.front.core.model.vo;

import javax.persistence.Entity;

import com.revenga.rits.back.data.core.model.ParamHist.OperationEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ParamHistVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1162663382518219236L;

	private String columnName;
	private OperationEnum operation;
	private String value;

}
