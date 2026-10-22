package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.data.core.model.PlanOrderValue;
import com.revenga.rits.back.front.core.model.vo.PlanOrderValueVO;

public class PlanOrderValueHelper {

	private PlanOrderValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final PlanOrderValue planOrderValue, PlanOrderValueVO planOrderValueVO) {

		if (ObjectUtils.allNotNull(planOrderValue, planOrderValueVO)) {

			BasicElementHelper.fillBasicProperties(planOrderValueVO, planOrderValue);

			planOrderValueVO.setValue(planOrderValue.getValue());
			
			if (planOrderValue.getPlanOrderTypeParam() != null) {

				PlanOrderTypeParam planOrderTypeParam = new PlanOrderTypeParam();
				planOrderTypeParam.setPlanOrderTypeId(planOrderValue.getPlanOrderTypeId());
				planOrderTypeParam.setId(planOrderValue.getPlanOrderTypeParamId());
				planOrderValueVO.setPlanOrderTypeParamId(BasicElementHelper.getIdFromElement(planOrderTypeParam));
			}

			planOrderValueVO
					.setPlanOrderTypeParam(PlanOrderTypeParamHelper.get(planOrderValue.getPlanOrderTypeParam()));

			if (planOrderValue.getPlanOrderTypeParamId() == null && planOrderValue.getPlanOrderTypeParam() != null
					&& planOrderValue.getPlanOrderTypeParam().getId() != null) {

				planOrderValueVO.setPlanOrderTypeParamId(
						BasicElementHelper.getIdFromElement(planOrderValue.getPlanOrderTypeParam()));
			}

		}

	}

	public static PlanOrderValueVO get(final PlanOrderValue planOrderValue) {

		PlanOrderValueVO planOrderValueVO = null;

		if (planOrderValue != null) {

			planOrderValueVO = new PlanOrderValueVO();

			assign(planOrderValue, planOrderValueVO);
		}

		return planOrderValueVO;
	}

	public static PlanOrderValue get(final PlanOrderValueVO planOrderValueVO) {

		PlanOrderValue planOrderValue = null;

		if (planOrderValueVO != null) {

			planOrderValue = new PlanOrderValue();
			
			if (planOrderValueVO.getPlanOrderTypeParamId() != null) {

				Key key = new Key(planOrderValueVO.getPlanOrderTypeParamId());

				if (key.getClazz() == PlanOrderTypeParam.class) {

					planOrderValue.setPlanOrderTypeId(Long.valueOf(key.getId(1)));
					planOrderValue.setPlanOrderTypeParamId(Long.valueOf(key.getId(2)));
				}
			}
			
			if (planOrderValueVO.getId() != null) {

				Key key = new Key(planOrderValueVO.getId());

				if (key.getClazz() == PlanOrderValue.class) {

					planOrderValue.setPlanId(Long.valueOf(key.getId(1)));
					planOrderValue.setPlanOrderId(Long.valueOf(key.getId(2)));
					planOrderValue.setPlanOrderTypeId(Long.valueOf(key.getId(3)));
					planOrderValue.setPlanOrderTypeParamId(Long.valueOf(key.getId(4)));
				}
			}

			planOrderValue.setValue(planOrderValueVO.getValue());		

			if (planOrderValueVO.getPlanOrderTypeParam() != null) {

				PlanOrderTypeParam planOrderTypeParam = PlanOrderTypeParamHelper
						.get(planOrderValueVO.getPlanOrderTypeParam());

				if (planOrderTypeParam != null) {

					if (planOrderValueVO.getPlanOrderTypeParamId() == null) {

						planOrderValue.setPlanOrderTypeId(planOrderTypeParam.getPlanOrderTypeId());
						planOrderValue.setPlanOrderTypeParamId(planOrderTypeParam.getId());
					}
					planOrderValue.setPlanOrderTypeParam(planOrderTypeParam);
				}
			}
		}

		return planOrderValue;
	}
}
