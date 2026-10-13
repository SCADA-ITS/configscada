package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.PlanOrderElementValue;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementValueVO;

public class PlanOrderElementValueHelper {

	private PlanOrderElementValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanOrderElementValueVO get(final PlanOrderElementValue planOrderElementValue) {

		PlanOrderElementValueVO planOrderElementValueVO = null;

		if (planOrderElementValue != null) {

			planOrderElementValueVO = new PlanOrderElementValueVO();

			assign(planOrderElementValue, planOrderElementValueVO);
		}

		return planOrderElementValueVO;
	}

	public static PlanOrderElementValue get(final PlanOrderElementValueVO planOrderElementValueVO) {

		PlanOrderElementValue planOrderElementValue = null;

		if (planOrderElementValueVO != null) {

			planOrderElementValue = new PlanOrderElementValue();
			
			if (planOrderElementValueVO.getElementId() != null) {

				Key key = new Key(planOrderElementValueVO.getElementId());

				if (key.getClazz() == Element.class) {

					planOrderElementValue.setElementTypeId(Long.valueOf(key.getId(1)));
					planOrderElementValue.setElementId(Long.valueOf(key.getId(2)));
				}
			}
			
			if (planOrderElementValueVO.getElementTypeParamId() != null) {

				Key key = new Key(planOrderElementValueVO.getElementTypeParamId());

				if (key.getClazz() == ElementTypeParam.class) {

					planOrderElementValue.setElementTypeId(Long.valueOf(key.getId(1)));
					planOrderElementValue.setParamTypeId(Long.valueOf(key.getId(2)));
					planOrderElementValue.setElementTypeParamId(Long.valueOf(key.getId(3)));
				}
			}
			

			if (planOrderElementValueVO.getId() != null) {

				Key key = new Key(planOrderElementValueVO.getId());

				if (key.getClazz() == PlanOrderElementValue.class) {

					planOrderElementValue.setPlanId(Long.valueOf(key.getId(1)));
					planOrderElementValue.setPlanOrderId(Long.valueOf(key.getId(2)));
					planOrderElementValue.setElementTypeId(Long.valueOf(key.getId(3)));
					planOrderElementValue.setParamTypeId(Long.valueOf(key.getId(4)));
					planOrderElementValue.setElementTypeParamId(Long.valueOf(key.getId(5)));
					planOrderElementValue.setElementId(Long.valueOf(key.getId(6)));
				}
			}

			planOrderElementValue.setValue(planOrderElementValueVO.getValue());		
		}

		return planOrderElementValue;
	}
	
	public static void assign(final PlanOrderElementValue planOrderElementValue, PlanOrderElementValueVO planOrderElementValueVO) {

		if (ObjectUtils.allNotNull(planOrderElementValue, planOrderElementValueVO)) {

			BasicElementHelper.fillBasicProperties(planOrderElementValueVO, planOrderElementValue);

			planOrderElementValueVO.setValue(planOrderElementValue.getValue());

		}

	}
}
