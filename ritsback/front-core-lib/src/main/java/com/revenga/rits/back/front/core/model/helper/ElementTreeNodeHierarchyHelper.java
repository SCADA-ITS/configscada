package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.ElementTreeNodeHierarchy;
import com.revenga.rits.back.front.core.model.vo.ElementTreeNodeHierarchyVO;

public class ElementTreeNodeHierarchyHelper {

	private ElementTreeNodeHierarchyHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final ElementTreeNodeHierarchy elementTreeNodeHierarchy, ElementTreeNodeHierarchyVO elementTreeNodeHierarchyVO) {

		if (ObjectUtils.allNotNull(elementTreeNodeHierarchy, elementTreeNodeHierarchyVO)) {

			BasicElementHelper.fillBasicProperties(elementTreeNodeHierarchyVO, elementTreeNodeHierarchy);
			
			// TODO set

		}
	}
}
