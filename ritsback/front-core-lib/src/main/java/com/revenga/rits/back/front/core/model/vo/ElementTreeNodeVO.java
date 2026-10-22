package com.revenga.rits.back.front.core.model.vo;


import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementTreeNodeVO extends BasicElementVO {
	
	private static final long serialVersionUID = -6863207869322505064L;
	
	@EqualsAndHashCode.Exclude
	private ElementVO element;
	
	private Integer position;
}
