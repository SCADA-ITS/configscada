package com.revenga.rits.back.front.core.model.vo;

import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author revenga
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementSubtypeVO extends BasicElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8813314099530628244L;
	
	@EqualsAndHashCode.Exclude
	private List<ElementTypeParamVO> elementSubtypeActiveParams;
	
	@EqualsAndHashCode.Exclude
	private List<CommandElementTypeVO> commandElementSubtypes;	

}
