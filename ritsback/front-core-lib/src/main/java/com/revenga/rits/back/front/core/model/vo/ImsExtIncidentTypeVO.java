package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ImsExtIncidentTypeVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482242987658695912L;
	
	private Boolean confirmationIsNeeded;
	

	@EqualsAndHashCode.Exclude
	private ImsExtIncidentGroupVO extIncidentGroup;
	
	@EqualsAndHashCode.Exclude
	private List<ImsExtIncidentTypeParamVO> extIncidentTypeParams;
}
