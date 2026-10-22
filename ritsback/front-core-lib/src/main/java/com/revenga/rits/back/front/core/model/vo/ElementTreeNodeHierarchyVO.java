package com.revenga.rits.back.front.core.model.vo;


import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementTreeNodeHierarchyVO extends BasicElementVO {
	
	private static final long serialVersionUID = -463657021480272522L;

	@EqualsAndHashCode.Exclude
	private ElementTreeNodeVO parent;
	
	@EqualsAndHashCode.Exclude
	private ElementTreeNodeVO child;
}
