package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.front.core.model.vo.PlanOrderStateTypeVO;

public class PlanOrderStateTypeHelper {

	private PlanOrderStateTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanOrderStateTypeVO get(final PlanOrderStateType planOrderStateType) {

		PlanOrderStateTypeVO planOrderStateTypeVO = null;

		if (planOrderStateType != null) {

			planOrderStateTypeVO = new PlanOrderStateTypeVO();

			assign(planOrderStateType, planOrderStateTypeVO);
		}

		return planOrderStateTypeVO;
	}

	public static PlanOrderStateType get(final PlanOrderStateTypeVO planOrderStateTypeVO) {

		PlanOrderStateType planOrderStateType = null;

		if (planOrderStateTypeVO != null) {

			planOrderStateType = new PlanOrderStateType();
			if (planOrderStateTypeVO.getId() != null) {

				Key key = new Key(planOrderStateTypeVO.getId());

				if (key.getClazz() == PlanOrderStateType.class) {

					planOrderStateType.setId(Long.valueOf(key.getId(1)));
				}
			}

			planOrderStateType.setAlias(planOrderStateTypeVO.getAlias());
			planOrderStateType.setDescription((planOrderStateTypeVO.getDescription()));

		}

		return planOrderStateType;
	}

	public static void assign(final PlanOrderStateType planOrderStateType, PlanOrderStateTypeVO planOrderStateTypeVO) {

		if (ObjectUtils.allNotNull(planOrderStateType, planOrderStateTypeVO)) {

			BasicElementHelper.fillBasicProperties(planOrderStateTypeVO, planOrderStateType);

		}
	}

}
