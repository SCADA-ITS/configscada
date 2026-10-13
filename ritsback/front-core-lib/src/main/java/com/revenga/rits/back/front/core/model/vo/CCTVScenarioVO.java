package com.revenga.rits.back.front.core.model.vo;


import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class CCTVScenarioVO extends BasicElementVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2789948732159033691L;

	/**
	 * nRows Propertie
	 */
	private int nRows;
	
	/**
	 * nColumns Propertie
	 */
	private int nColumns;
	
	
	@EqualsAndHashCode.Exclude
	private List<ElementVO> elements;
}
