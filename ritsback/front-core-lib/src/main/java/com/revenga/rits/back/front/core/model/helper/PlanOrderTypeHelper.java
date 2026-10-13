package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderType;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeVO;

public class PlanOrderTypeHelper {

	private PlanOrderTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final PlanOrderType planOrderType, PlanOrderTypeVO planOrderTypeVO) {
		if (ObjectUtils.allNotNull(planOrderType, planOrderTypeVO)) {

			BasicElementHelper.fillBasicProperties(planOrderTypeVO, planOrderType);

			if (planOrderType.getPlanOrderTypeParams() != null) {

				List<PlanOrderTypeParamVO> paramTypes = new ArrayList<>();

				for (PlanOrderTypeParam param : planOrderType.getPlanOrderTypeParams()) {

					if (param.getId() != null) {

						paramTypes.add(PlanOrderTypeParamHelper.get(param));
					}
				}

				planOrderTypeVO.setPlanOrderTypeParams(paramTypes);
			}
			
			ModelVOHelper.translate(planOrderTypeVO);
		}
	}

	public static PlanOrderTypeVO get(final PlanOrderType planOrderType) {

		PlanOrderTypeVO planOrderTypeVO = null;

		if (planOrderType != null) {

			planOrderTypeVO = new PlanOrderTypeVO();

			assign(planOrderType, planOrderTypeVO);
		}

		return planOrderTypeVO;
	}

	public static PlanOrderType get(final PlanOrderTypeVO planOrderTypeVO) {

		PlanOrderType planOrderType = null;

		if (planOrderTypeVO != null) {

			planOrderType = new PlanOrderType();
			if (planOrderTypeVO.getId() != null) {

				Key key = new Key(planOrderTypeVO.getId());

				if (key.getClazz() == PlanOrder.class) {

					planOrderType.setId(Long.valueOf(key.getId(1)));
				}
			}

			planOrderType.setAlias(planOrderTypeVO.getAlias());
			planOrderType.setDescription((planOrderTypeVO.getDescription()));

			if (planOrderTypeVO.getPlanOrderTypeParams() != null) {

				List<PlanOrderTypeParam> planOrderTypeParams = new ArrayList<>();

				for (PlanOrderTypeParamVO planOrderTypeParamVO : planOrderTypeVO.getPlanOrderTypeParams()) {

					PlanOrderTypeParam planOrderTypeParam = new PlanOrderTypeParam();

					planOrderTypeParam.setId(Long.parseLong(planOrderTypeParamVO.getId()));
					planOrderTypeParams.add(planOrderTypeParam);
				}

				planOrderType.setPlanOrderTypeParams(planOrderTypeParams);
			}

		}

		return planOrderType;
	}

}
