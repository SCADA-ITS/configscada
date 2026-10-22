package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.ElementTreeNode;
import com.revenga.rits.back.front.core.model.vo.ElementTreeNodeVO;

public class ElementTreeNodeHelper {

	private ElementTreeNodeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final ElementTreeNode elementTreeNode, ElementTreeNodeVO elementTreeNodeVO) {

		if (ObjectUtils.allNotNull(elementTreeNode, elementTreeNodeVO)) {

			BasicElementHelper.fillBasicProperties(elementTreeNodeVO, elementTreeNode);
			
			// TODO set
			
			elementTreeNodeVO.setPosition(elementTreeNode.getPosition() != null ? elementTreeNode.getPosition().intValue() : null);

		}
	}
}
