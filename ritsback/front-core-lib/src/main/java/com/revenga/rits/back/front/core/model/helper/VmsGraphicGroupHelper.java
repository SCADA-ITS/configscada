package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.data.core.model.VmsGraphicGroup;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicGroupVO;

public class VmsGraphicGroupHelper {

	private VmsGraphicGroupHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final VmsGraphicGroup graphicGroup, VmsGraphicGroupVO graphicGroupVO) {

		
		if (graphicGroup != null && graphicGroupVO != null) {

			BasicElementHelper.fillBasicProperties(graphicGroupVO, graphicGroup);
			
			ModelVOHelper.translate(graphicGroupVO);

		}
	}
}
