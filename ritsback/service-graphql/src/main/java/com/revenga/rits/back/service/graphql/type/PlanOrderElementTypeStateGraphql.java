package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.PlanOrderElementTypeState;
import com.revenga.rits.back.front.core.model.helper.PlanOrderElementTypeStateHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderElementTypeStateVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PlanOrderElementTypeStateGraphql extends PlanOrderElementTypeStateVO{/**
	 * 
	 */
	private static final long serialVersionUID = 3765790220936607466L;
	
	private PlanOrderElementTypeState planOrderElementTypeState;

	public PlanOrderElementTypeStateGraphql(final PlanOrderElementTypeState planOrderElementTypeState) {

		PlanOrderElementTypeStateHelper.assign(planOrderElementTypeState, this);
		this.planOrderElementTypeState = planOrderElementTypeState;
	}
	
	
	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (planOrderElementTypeState != null) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(planOrderElementTypeState.getElementTypeId(), planOrderElementTypeState.getElementId(), false);

				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}

			} else {

				log.error("planOrderElementTypeState has null pointer");
			}

		}

		return super.getElement();
	}
	
	@Override
	public ElementTypeStateVO getElementTypeState() {

		if (super.getElementTypeState() == null) {

			if (planOrderElementTypeState != null) {

				ElementTypeState elementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository()
						.get(planOrderElementTypeState.getElementTypeId(), planOrderElementTypeState.getElementTypeStateId(), false);

				if (elementTypeState != null) {

					super.setElementTypeState(new ElementTypeStateGraphql(elementTypeState));
				}

			} else {

				log.error("planOrderElementTypeState has null pointer");
			}

		}

		return super.getElementTypeState();
	}
	

}
