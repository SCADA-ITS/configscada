package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class LogTypeVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
	
	@EqualsAndHashCode.Exclude
	private List<LogSubtypeVO> logSubtypes;
	
	@EqualsAndHashCode.Exclude
	private List<LogTypeParamVO> logTypeParams;
}
