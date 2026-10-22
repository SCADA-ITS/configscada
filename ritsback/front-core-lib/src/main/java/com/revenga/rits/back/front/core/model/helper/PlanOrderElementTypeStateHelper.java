package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderElementTypeState;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementTypeStateVO;

public class PlanOrderElementTypeStateHelper {
	
	private PlanOrderElementTypeStateHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PlanOrderElementTypeStateVO get(final PlanOrderElementTypeState planOrderElementTypeState) {

		PlanOrderElementTypeStateVO planOrderElementTypeStateVO = null;

		if (planOrderElementTypeState != null) {

			planOrderElementTypeStateVO = new PlanOrderElementTypeStateVO();

			assign(planOrderElementTypeState, planOrderElementTypeStateVO);
		}

		return planOrderElementTypeStateVO;
	}

	public static PlanOrderElementTypeState get(final PlanOrderElementTypeStateVO planOrderElementTypeStateVO) {

		PlanOrderElementTypeState planOrderElementTypeState = null;

		if (planOrderElementTypeStateVO != null) {

			planOrderElementTypeState = new PlanOrderElementTypeState();
			
			if (planOrderElementTypeStateVO.getElementId() != null) {

				Key key = new Key(planOrderElementTypeStateVO.getElementId());

				if (key.getClazz() == Element.class) {

					planOrderElementTypeState.setElementTypeId(Long.valueOf(key.getId(1)));
					planOrderElementTypeState.setElementId(Long.valueOf(key.getId(2)));
				}
			}
			
			if (planOrderElementTypeStateVO.getElementTypeStateId() != null) {

				Key key = new Key(planOrderElementTypeStateVO.getElementTypeStateId());

				if (key.getClazz() == ElementTypeState.class) {

					planOrderElementTypeState.setElementTypeId(Long.valueOf(key.getId(1)));
					planOrderElementTypeState.setElementTypeStateId(Long.valueOf(key.getId(2)));
				}
			}
			

			if (planOrderElementTypeStateVO.getId() != null) {

				Key key = new Key(planOrderElementTypeStateVO.getId());

				if (key.getClazz() == PlanOrderElementTypeState.class) {

					planOrderElementTypeState.setPlanId(Long.valueOf(key.getId(1)));
					planOrderElementTypeState.setPlanOrderId(Long.valueOf(key.getId(2)));
					planOrderElementTypeState.setElementTypeId(Long.valueOf(key.getId(3)));
					planOrderElementTypeState.setElementTypeStateId(Long.valueOf(key.getId(4)));
					planOrderElementTypeState.setElementId(Long.valueOf(key.getId(5)));
				}
			}
	
		}

		return planOrderElementTypeState;
	}
	
	public static void assign(final PlanOrderElementTypeState planOrderElementTypeState, PlanOrderElementTypeStateVO planOrderElementTypeStateVO) {

		if (ObjectUtils.allNotNull(planOrderElementTypeState, planOrderElementTypeStateVO)) {

			BasicElementHelper.fillBasicProperties(planOrderElementTypeStateVO, planOrderElementTypeState);

		}

	}

}
