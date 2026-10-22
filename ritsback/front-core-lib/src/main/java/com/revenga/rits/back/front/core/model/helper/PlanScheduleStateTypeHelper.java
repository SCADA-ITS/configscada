package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.front.core.model.vo.PlanScheduleStateTypeVO;

public class PlanScheduleStateTypeHelper {

	private PlanScheduleStateTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanScheduleStateTypeVO get(final PlanScheduleStateType planScheduleStateType) {

		PlanScheduleStateTypeVO planScheduleStateTypeVO = null;

		if (planScheduleStateType != null) {

			planScheduleStateTypeVO = new PlanScheduleStateTypeVO();

			assign(planScheduleStateType, planScheduleStateTypeVO);
		}

		return planScheduleStateTypeVO;
	}

	public static PlanScheduleStateType get(final PlanScheduleStateTypeVO planScheduleStateTypeVO) {

		PlanScheduleStateType planScheduleStateType = null;

		if (planScheduleStateTypeVO != null) {

			planScheduleStateType = new PlanScheduleStateType();
			if (planScheduleStateTypeVO.getId() != null) {

				Key key = new Key(planScheduleStateTypeVO.getId());

				if (key.getClazz() == PlanScheduleStateType.class) {

					planScheduleStateType.setId(Long.valueOf(key.getId(1)));
				}
			}

			planScheduleStateType.setAlias(planScheduleStateTypeVO.getAlias());
			planScheduleStateType.setDescription((planScheduleStateTypeVO.getDescription()));

		}

		return planScheduleStateType;
	}

	public static void assign(final PlanScheduleStateType planScheduleStateType,
			PlanScheduleStateTypeVO planScheduleStateTypeVO) {

		if (ObjectUtils.allNotNull(planScheduleStateType, planScheduleStateTypeVO)) {

			BasicElementHelper.fillBasicProperties(planScheduleStateTypeVO, planScheduleStateType);

		}
	}

}
