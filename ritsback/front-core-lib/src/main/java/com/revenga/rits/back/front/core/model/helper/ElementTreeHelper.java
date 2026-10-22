package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.ElementTree;
import com.revenga.rits.back.front.core.model.vo.ElementTreeVO;

public class ElementTreeHelper {

	private ElementTreeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final ElementTree elementTree, ElementTreeVO elementTreeVO) {

		if (ObjectUtils.allNotNull(elementTree, elementTreeVO)) {

			BasicElementHelper.fillBasicProperties(elementTreeVO, elementTree);
			
			// TODO set
		}
	}
}
