package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeParamVO;

public class PlanOrderTypeParamHelper {

	private PlanOrderTypeParamHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanOrderTypeParamVO get(final PlanOrderTypeParam planOrderTypeParam) {

		PlanOrderTypeParamVO planOrderTypeParamVO = null;

		if (planOrderTypeParam != null) {

			planOrderTypeParamVO = new PlanOrderTypeParamVO();

			assign(planOrderTypeParam, planOrderTypeParamVO);
		}

		return planOrderTypeParamVO;
	}

	public static PlanOrderTypeParam get(final PlanOrderTypeParamVO planOrderTypeParamVO) {

		PlanOrderTypeParam planOrderTypeParam = null;

		if (planOrderTypeParamVO != null) {

			planOrderTypeParam = new PlanOrderTypeParam();
			if (planOrderTypeParamVO.getId() != null) {

				Key key = new Key(planOrderTypeParamVO.getId());

				if (key.getClazz() == PlanOrderTypeParam.class) {

					planOrderTypeParam.setPlanOrderTypeId(Long.valueOf(key.getId(1)));
					planOrderTypeParam.setId(Long.valueOf(key.getId(2)));
				}
			}

			planOrderTypeParam.setAlias(planOrderTypeParamVO.getAlias());
			planOrderTypeParam.setDescription((planOrderTypeParamVO.getDescription()));

			if (planOrderTypeParamVO.getDataTypeId() != null) {

				Key key = new Key(planOrderTypeParamVO.getDataTypeId());

				if (key.getClazz() == DataType.class) {

					planOrderTypeParam.setDataTypeId(Long.valueOf(key.getId(1)));
				}
			}

		}

		return planOrderTypeParam;
	}

	public static void assign(final PlanOrderTypeParam planOrderTypeParam, PlanOrderTypeParamVO planOrderTypeParamVO) {

		if (ObjectUtils.allNotNull(planOrderTypeParam, planOrderTypeParamVO)) {

			BasicElementHelper.fillBasicProperties(planOrderTypeParamVO, planOrderTypeParam);

			if (planOrderTypeParam.getDataTypeId() != null) {

				DataType dataType = new DataType();
				dataType.setId(planOrderTypeParam.getDataTypeId());
				planOrderTypeParamVO.setDataTypeId(BasicElementHelper.getIdFromElement(dataType));
			}

			planOrderTypeParamVO.setThreshold1(planOrderTypeParam.getThreshold1());
			planOrderTypeParamVO.setThreshold2(planOrderTypeParam.getThreshold2());
		}

	}

}
