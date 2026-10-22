package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.front.core.model.helper.ElementValueHelper;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ElementValueGraphql extends ElementValueVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8668695689688125120L;

	private ElementValue elementValue;

	public ElementValueGraphql(final ElementValue elementValue) {

		ElementValueHelper.assign(elementValue, this);
		this.elementValue = elementValue;
	}

	@Override
	public ElementTypeParamVO getElementTypeParam() {

		if (super.getElementTypeParam() == null) {

			if (elementValue != null) {

				ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParamRepository().get(
						elementValue.getElementTypeId(), elementValue.getParamTypeId(),
						elementValue.getElementTypeParamId(), false);

				if (elementTypeParam != null) {

					super.setElementTypeParam(new ElementTypeParamGraphql(elementTypeParam));
				}

			} else {

				log.error("elementValue has null pointer");
			}

		}

		return super.getElementTypeParam();
	}
	
	@Override
	public ElementValueStateVO getElementValueState() {

		if (super.getElementValueState() == null || !(super.getElementValueState() instanceof ElementValueStateGraphql)) {

			if (elementValue != null) {

				ElementValueState elementValueState = EntitiesManager.getInstance().getElementValueStateRepository().get(
						elementValue.getElementValueStateId(), false);

				if (elementValueState != null) {

					super.setElementValueState(new ElementValueStateGraphql(elementValueState));
				}

			} else {

				log.error("elementValue has null pointer");
			}

		}

		return super.getElementValueState();
	}
}
