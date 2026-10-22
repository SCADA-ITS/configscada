package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.data.core.model.VmsGraphicCategory;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicCategoryVO;

public class VmsGraphicCategoryHelper {

	private VmsGraphicCategoryHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final VmsGraphicCategory graphicCategory, VmsGraphicCategoryVO graphicCategoryVO) {

		
		if (graphicCategory != null && graphicCategoryVO != null) {

			BasicElementHelper.fillBasicProperties(graphicCategoryVO, graphicCategory);
			
			ModelVOHelper.translate(graphicCategoryVO);

		}
	}
}
