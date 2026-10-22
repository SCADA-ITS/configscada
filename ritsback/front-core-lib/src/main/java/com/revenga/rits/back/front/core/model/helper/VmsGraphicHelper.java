package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.data.core.model.VmsGraphic;
import com.revenga.rits.back.data.core.model.VmsGraphicCategory;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicVO;


public class VmsGraphicHelper {

	/**
	 * 
	 */
	private VmsGraphicHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	/**
	 * Method that return a VmsGraphicVO object
	 * @param graphic: Source objet to wrap a new VmsGraphicVO 
	 * @return: VmsGraphicVO object warpped from a VmsGraphic object
	 */
	public static VmsGraphicVO get(final VmsGraphic graphic) {

		VmsGraphicVO graphicVO = null;

		if (graphic != null) {

			graphicVO = new VmsGraphicVO();

			assign(graphic, graphicVO);
		}

		return graphicVO;
	}

	/**
	 * 
	 * @param graphic
	 * @param graphicVO
	 */
	public static void assign(final VmsGraphic graphic, VmsGraphicVO graphicVO) {

		if (graphic != null && graphicVO != null) {

			BasicElementHelper.fillBasicProperties(graphicVO, graphic);
			
			graphicVO.setGraphicType(graphic.getGraphicTypeId());
			
			if (graphic.getGraphicCategoryId() != null) {
				
				VmsGraphicCategory graphicCategory = new VmsGraphicCategory();
				graphicCategory.setId(graphic.getGraphicCategoryId());
				graphicVO.setGraphicCategoryId(BasicElementHelper.getIdFromElement(graphicCategory));
			}
			
		
			ModelVOHelper.translate(graphicVO);

		}
	}
}
