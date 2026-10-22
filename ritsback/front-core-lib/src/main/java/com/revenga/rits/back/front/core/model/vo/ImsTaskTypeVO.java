package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsTaskTypeVO extends BasicElementVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2250259111386220172L;
		
	@EqualsAndHashCode.Exclude
	private List <ImsTaskTypeParamVO> taskTypeParams;
}
