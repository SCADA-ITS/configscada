package com.revenga.rits.back.service.graphql.type;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.data.core.model.StretchType;
import com.revenga.rits.back.front.core.model.helper.StretchHelper;
import com.revenga.rits.back.front.core.model.vo.RoadVO;
import com.revenga.rits.back.front.core.model.vo.StretchTypeVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class StretchGraphql extends StretchVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;
	private Stretch stretch;
	private StretchLocation stretchLocation;

	public StretchGraphql(final Stretch stretch) {

		StretchHelper.assign(stretch, this);
		this.stretch = stretch;

	}

	public StretchGraphql(final Stretch stretch, StretchLocation stretchLocation) {

		StretchHelper.assign(stretch, this);
		this.stretch = stretch;
		this.stretchLocation = stretchLocation;

	}

	@Override
	public StretchTypeVO getStretchType() {

		if (super.getStretchType() == null && stretch != null && stretch.getStretchTypeId() != null) {

			StretchType stretchType = EntitiesManager.getInstance().getStretchTypeRepository()
					.get(stretch.getStretchTypeId(), false);

			if (stretchType != null) {

				super.setStretchType(new StretchTypeGraphql(stretchType));
			}
		}

		return super.getStretchType();
	}

	@Override
	public RoadVO getRoad() {

		if (super.getRoad() == null) {

			if (ObjectUtils.allNotNull(stretch)) {

				Road road = EntitiesManager.getInstance().getRoadRepository().get(stretch.getRoadId(), false);

				if (road != null) {

					super.setRoad(new RoadGraphql(road));
				}
			}

		}

		return super.getRoad();

	}

	@Override
	public Integer getPosition() {

		return stretchLocation != null ? stretchLocation.getPosition() : null;
	}
}
