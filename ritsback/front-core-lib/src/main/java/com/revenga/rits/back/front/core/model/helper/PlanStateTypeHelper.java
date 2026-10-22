package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.front.core.model.vo.PlanStateTypeVO;

public class PlanStateTypeHelper {
	
	private PlanStateTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanStateTypeVO get(final PlanStateType planStateType) {

		PlanStateTypeVO planStateTypeVO = null;

		if (planStateType != null) {

			planStateTypeVO = new PlanStateTypeVO();

			assign(planStateType, planStateTypeVO);
		}

		return planStateTypeVO;
	}

	public static PlanStateType get(final PlanStateTypeVO planStateTypeVO) {

		PlanStateType planStateType = null;

		if (planStateTypeVO != null) {

			planStateType = new PlanStateType();
			if (planStateTypeVO.getId() != null) {

				Key key = new Key(planStateTypeVO.getId());

				if (key.getClazz() == PlanStateType.class) {

					planStateType.setId(Long.valueOf(key.getId(1)));
				}
			}

			planStateType.setAlias(planStateTypeVO.getAlias());
			planStateType.setDescription((planStateTypeVO.getDescription()));

		}

		return planStateType;
	}

	public static void assign(final PlanStateType planStateType, PlanStateTypeVO planStateTypeVO) {

		if (ObjectUtils.allNotNull(planStateType, planStateTypeVO)) {

			BasicElementHelper.fillBasicProperties(planStateTypeVO, planStateType);

		}
	}

}
