package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderCondition;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.helper.PlanOrderConditionHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderConditionVO;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PlanOrderConditionGraphql extends PlanOrderConditionVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 4048138265096051108L;

	private PlanOrderCondition planOrderCondition;

	public PlanOrderConditionGraphql(final PlanOrderCondition planOrderCondition) {

		PlanOrderConditionHelper.assign(planOrderCondition, this);
		this.planOrderCondition = planOrderCondition;
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (planOrderCondition != null) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(planOrderCondition.getElementTypeId(), planOrderCondition.getElementId(), false);

				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}

			} else {

				log.error("planOrderCondition has null pointer");
			}

		}

		return super.getElement();
	}

	@Override
	public ElementTypeStateVO getElementTypeState() {

		if (super.getElementTypeState() == null && planOrderCondition != null
				&& planOrderCondition.getElementTypeStateId() != null) {

			ElementTypeState elementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository()
					.get(planOrderCondition.getElementTypeId(), planOrderCondition.getElementTypeStateId(), false);

			if (elementTypeState != null) {

				super.setElementTypeState(new ElementTypeStateGraphql(elementTypeState));
			}

		}

		return super.getElementTypeState();

	}

	@Override
	public ElementTypeParamVO getElementTypeParam() {

		if (super.getElementTypeParam() == null && planOrderCondition != null
				&& planOrderCondition.getParamTypeId() != null && planOrderCondition.getElementTypeParamId() != null) {

			ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParamRepository().get(
					planOrderCondition.getElementTypeId(), planOrderCondition.getParamTypeId(),
					planOrderCondition.getElementTypeParamId(), false);

			if (elementTypeParam != null) {

				super.setElementTypeParam(new ElementTypeParamGraphql(elementTypeParam));
			}

		}

		return super.getElementTypeParam();
	}

	@Override
	public ThresholdOperationVO getThresholdOperation() {

		if (super.getThresholdOperation() == null && planOrderCondition != null
				&& planOrderCondition.getThresholdOperationId() != null) {

			ThresholdOperation thresholdOperation = EntitiesManager.getInstance().getThresholdOperationRepository()
					.get(planOrderCondition.getThresholdOperationId(), false);

			if (thresholdOperation != null) {

				super.setThresholdOperation(new ThresholdOperationGraphql(thresholdOperation));
			}

		}

		return super.getThresholdOperation();
	}

}
