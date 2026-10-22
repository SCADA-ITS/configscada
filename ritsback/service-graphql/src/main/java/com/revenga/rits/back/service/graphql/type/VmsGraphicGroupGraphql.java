package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.VmsGraphic;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.model.VmsGraphicGroup;
import com.revenga.rits.back.front.core.model.helper.VmsGraphicGroupHelper;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicGroupVO;
import com.revenga.rits.back.front.core.model.vo.VmsGraphicVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class VmsGraphicGroupGraphql extends VmsGraphicGroupVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7429221335966982392L;

	/**
	 * VmsGraphicGroup object. Code injection
	 */
	private VmsGraphicGroup graphicGroup;

	/**
	 * Default contructor of the class
	 * 
	 * @param graphicGroup: VmsGraphicGroup parameter injected
	 */
	public VmsGraphicGroupGraphql(final VmsGraphicGroup graphicGroup) {

		VmsGraphicGroupHelper.assign(graphicGroup, this);

		this.graphicGroup = graphicGroup;
	}

	/**
	 * Method that return the Graphic Object of the Graphic group that we are
	 * processing.
	 */
	@Override
	public List<VmsGraphicVO> getGraphics() {

		if (super.getGraphics() == null && graphicGroup != null) {

			List<VmsGraphicVO> graphicsVO = new ArrayList<>();

			List<VmsGraphic> beVmsGraphics = EntitiesManager.getInstance().getVmsGraphicRepository().getAll(false);

			List<VmsGraphicGraphicGroupValue> graphicGraphicGroups = EntitiesManager.getInstance()
					.getVmsGraphicGraphicGroupRepository().getByGroup(graphicGroup.getId(), false);

			if (!CollectionUtils.isEmpty(graphicGraphicGroups)) {

				for (VmsGraphicGraphicGroupValue graphicGraphicGroup : graphicGraphicGroups) {

					VmsGraphic graphic = beVmsGraphics.stream()
							.filter(e -> e.getGraphicTypeId().equals(graphicGraphicGroup.getGraphicTypeId())
									&& e.getId().equals(graphicGraphicGroup.getGraphicId()))
							.findFirst().orElse(null);

					if (graphic != null) {

						graphicsVO.add(new VmsGraphicGraphql(graphic, graphicGraphicGroup));
					}
				}
			}

			super.setGraphics(graphicsVO);
		}

		return super.getGraphics();
	}
}
