package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.VmsGraphic;
import com.revenga.rits.back.data.core.model.VmsGraphicCategory;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.front.core.model.helper.VmsGraphicHelper;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicCategoryVO;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class VmsGraphicGraphql extends VmsGraphicVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 207190913062615043L;

	/**
	 * Graphic object propertie
	 */
	private VmsGraphic graphic;

	/**
	 * GraphicGroup object propertie
	 */

	private VmsGraphicGraphicGroupValue graphicGraphicGroup;


	/**
	 * Cosntructor with graphic and graphicGroup parameters
	 * 
	 * @param graphic
	 * @param graphicGroup
	 */
	public VmsGraphicGraphql(final VmsGraphic graphic, final VmsGraphicGraphicGroupValue graphicGroup) {

		VmsGraphicHelper.assign(graphic, this);

		this.graphic = graphic;
		this.graphicGraphicGroup = graphicGroup;
	}

	@Override
	public VmsGraphicCategoryVO getGraphicCategory() {

		if (super.getGraphicCategory() == null && graphic != null && graphic.getGraphicCategoryId() != null) {

			VmsGraphicCategory graphicCategory = EntitiesManager.getInstance().getVmsGraphicCategoryRepository()
					.get(graphic.getGraphicCategoryId(), false);

			if (graphicCategory != null) {

				super.setGraphicCategory(new VmsGraphicCategoryGraphql(graphicCategory));
			}
		}

		return super.getGraphicCategory();
	}
}
