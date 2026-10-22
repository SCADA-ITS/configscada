package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.front.core.model.helper.ElementStateHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmLevelVO;
import com.revenga.rits.back.front.core.model.vo.ElementStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
public class ElementStateGraphql extends ElementStateVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6212264014564721324L;
	
	private ElementState elementState;

	public ElementStateGraphql(final ElementState elementState) {

		ElementStateHelper.assign(elementState, this);

		this.elementState = elementState;
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null && elementState != null && elementState.getElementId() != null
				&& elementState.getElementTypeId() != null) {

			Element element = EntitiesManager.getInstance().getElementRepository().get(elementState.getElementTypeId(),
					elementState.getElementId(), false);

			if (element != null) {

				super.setElement(new ElementGraphql(element));
			}
		}

		return super.getElement();
	}
	
	@Override
	public ElementTypeStateVO getPreviousElementTypeState() {

		if (super.getPreviousElementTypeState() == null && elementState != null && elementState.getPreviousStateId() != null
				&& elementState.getElementTypeId() != null) {

			ElementTypeState previousElementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository().get(elementState.getElementTypeId(),
					elementState.getPreviousStateId(), false);

			if (previousElementTypeState != null) {

				super.setPreviousElementTypeState(new ElementTypeStateGraphql(previousElementTypeState));
			}
		}

		return super.getPreviousElementTypeState();
	}
	
	@Override
	public ElementTypeStateVO getCurrentElementTypeState() {

		if (super.getCurrentElementTypeState() == null && elementState != null && elementState.getStateId() != null
				&& elementState.getElementTypeId() != null) {

			ElementTypeState currentElementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository().get(elementState.getElementTypeId(),
					elementState.getStateId(), false);

			if (currentElementTypeState != null) {

				super.setCurrentElementTypeState(new ElementTypeStateGraphql(currentElementTypeState));
			}
		}

		return super.getCurrentElementTypeState();
	}
	
	@Override
	public ElementTypeVO getElementType() {

		if (super.getElementType() == null && elementState != null && elementState.getElementTypeId() != null) {

			ElementType elementType = EntitiesManager.getInstance().getElementTypeRepository()
					.get(elementState.getElementTypeId(), false);

			if (elementType != null) {

				super.setElementType(new ElementTypeGraphql(elementType));
			}
		}

		return super.getElementType();
	}
	
	@Override
	public AlarmLevelVO getMaxAlarmLevel() {

		if (super.getMaxAlarmLevel() == null && elementState != null && elementState.getMaxAlarmLevelId() != null) {

			AlarmLevel alarmLevel = EntitiesManager.getInstance().getAlarmLevelRepository()
					.get(elementState.getMaxAlarmLevelId(), false);

			if (alarmLevel != null) {

				super.setMaxAlarmLevel(new AlarmLevelGraphql(alarmLevel));
			}
		}

		return super.getMaxAlarmLevel();
	}
	
}