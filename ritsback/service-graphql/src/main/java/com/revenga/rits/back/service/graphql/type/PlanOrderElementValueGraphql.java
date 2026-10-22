package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.PlanOrderElementValue;
import com.revenga.rits.back.front.core.model.helper.PlanOrderElementValueHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementValueVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PlanOrderElementValueGraphql extends PlanOrderElementValueVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;

	PlanOrderElementValue planOrderElementValue;

	public PlanOrderElementValueGraphql(final PlanOrderElementValue planOrderElementValue) {

		PlanOrderElementValueHelper.assign(planOrderElementValue, this);
		this.planOrderElementValue = planOrderElementValue;

	}

	@Override
	public ElementTypeParamVO getElementTypeParam() {

		if (super.getElementTypeParam() == null) {

			if (planOrderElementValue != null) {

				ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParamRepository().get(
						planOrderElementValue.getElementTypeId(), planOrderElementValue.getParamTypeId(),
						planOrderElementValue.getElementTypeParamId(), false);

				if (elementTypeParam != null) {

					super.setElementTypeParam(new ElementTypeParamGraphql(elementTypeParam));
				}

			} else {

				log.error("planOrderElementValue has null pointer");
			}

		}

		return super.getElementTypeParam();
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (planOrderElementValue != null) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(planOrderElementValue.getElementTypeId(), planOrderElementValue.getElementId(), false);

				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}

			} else {

				log.error("planOrderElementValue has null pointer");
			}

		}

		return super.getElement();
	}

}
