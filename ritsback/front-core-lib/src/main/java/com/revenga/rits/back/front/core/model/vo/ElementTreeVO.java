package com.revenga.rits.back.front.core.model.vo;


import java.util.List;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ElementTreeVO extends BasicElementVO {
	
	private static final long serialVersionUID = -2789948732159033691L;

	@EqualsAndHashCode.Exclude
	private List<ElementTreeNodeVO> elementTreeNodes;

	@EqualsAndHashCode.Exclude
	private List<ElementTreeNodeHierarchyVO> elementTreeNodeHierarchies;
}
