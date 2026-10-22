package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanNode;
import com.revenga.rits.back.front.core.model.vo.PlanNodeVO;

public class PlanNodeHelper {

	private PlanNodeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final PlanNode planNode, PlanNodeVO planNodeVO) {

		if (ObjectUtils.allNotNull(planNode, planNodeVO)) {

			BasicElementHelper.fillBasicProperties(planNodeVO, planNode);
		}
	}

	public static PlanNode get(PlanNodeVO planNodeVO) {

		PlanNode planNode = null;

		if (planNodeVO != null) {

			planNode = new PlanNode();
			if (planNodeVO.getId() != null) {

				Key key = new Key(planNodeVO.getId());

				if (key.getClazz() == PlanNode.class) {

					planNode.setId(Long.valueOf(key.getId(1)));
				}
			}
			planNode.setAlias(planNodeVO.getAlias());
			planNode.setDescription((planNodeVO.getDescription()));

			if (planNodeVO.getPlanId() != null) {

				Key key = new Key(planNodeVO.getPlanId());

				if (key.getClazz() == Plan.class) {

					planNode.setPlanId(Long.valueOf(key.getId(1)));
				}
			}
		}

		return planNode;
	}
}
