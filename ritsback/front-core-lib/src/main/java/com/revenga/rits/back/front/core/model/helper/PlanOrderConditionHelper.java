package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.vo.PlanOrderConditionVO;

public class PlanOrderConditionHelper {

	private PlanOrderConditionHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanOrderConditionVO get(final PlanOrderCondition planOrderCondition) {

		PlanOrderConditionVO planOrderConditionVO = null;

		if (planOrderCondition != null) {

			planOrderConditionVO = new PlanOrderConditionVO();

			assign(planOrderCondition, planOrderConditionVO);
		}

		return planOrderConditionVO;
	}

	public static PlanOrderCondition get(final PlanOrderConditionVO planOrderConditionVO) {

		PlanOrderCondition planOrderCondition = null;

		if (planOrderConditionVO != null) {

			planOrderCondition = new PlanOrderCondition();

			if (planOrderConditionVO.getThresholdOperationId() != null) {

				Key key = new Key(planOrderConditionVO.getThresholdOperationId());

				if (key.getClazz() == ThresholdOperation.class) {

					planOrderCondition.setThresholdOperationId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (planOrderConditionVO.getElementTypeStateId() != null) {

				Key key = new Key(planOrderConditionVO.getElementTypeStateId());

				if (key.getClazz() == ElementTypeState.class) {

					planOrderCondition.setElementTypeStateId(Long.valueOf(key.getId(2)));
				}
			}

			if (planOrderConditionVO.getElementId() != null) {

				Key key = new Key(planOrderConditionVO.getElementId());

				if (key.getClazz() == Element.class) {

					planOrderCondition.setElementTypeId(Long.valueOf(key.getId(1)));
					planOrderCondition.setElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (planOrderConditionVO.getElementTypeParamId() != null) {

				Key key = new Key(planOrderConditionVO.getElementTypeParamId());

				if (key.getClazz() == ElementTypeParam.class) {

					planOrderCondition.setElementTypeId(Long.valueOf(key.getId(1)));
					planOrderCondition.setParamTypeId(Long.valueOf(key.getId(2)));
					planOrderCondition.setElementTypeParamId(Long.valueOf(key.getId(3)));
				}
			}

			if (planOrderConditionVO.getId() != null) {

				Key key = new Key(planOrderConditionVO.getId());

				if (key.getClazz() == PlanOrderCondition.class) {

					planOrderCondition.setId(Long.valueOf(key.getId(1)));
					planOrderCondition.setPlanId(Long.valueOf(key.getId(2)));
					planOrderCondition.setPlanOrderId(Long.valueOf(key.getId(3)));
				}
			}

			planOrderCondition.setThresholdVal1(planOrderConditionVO.getThresholdVal1());
			planOrderCondition.setThresholdVal2(planOrderConditionVO.getThresholdVal2());
			planOrderCondition.setPlanOrderConditionOrder(planOrderConditionVO.getPlanOrderConditionOrder());
			planOrderCondition.setNexusAnd(planOrderConditionVO.getNexusAnd());
		}

		return planOrderCondition;
	}

	public static void assign(final PlanOrderCondition planOrderCondition, PlanOrderConditionVO planOrderConditionVO) {

		if (ObjectUtils.allNotNull(planOrderCondition, planOrderConditionVO)) {

			BasicElementHelper.fillBasicProperties(planOrderConditionVO, planOrderCondition);

			planOrderConditionVO.setThresholdVal1(planOrderCondition.getThresholdVal1());
			planOrderConditionVO.setThresholdVal2(planOrderCondition.getThresholdVal2());
			planOrderConditionVO.setPlanOrderConditionOrder(planOrderCondition.getPlanOrderConditionOrder());
			planOrderConditionVO.setNexusAnd(planOrderCondition.getNexusAnd());

		}

	}

}
