package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AlarmTargetElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.front.core.model.helper.AlarmTargetElementHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmTargetElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class AlarmTargetElementGraphql extends AlarmTargetElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5240297424197540752L;

	/**
	 * 
	 */

	private AlarmTargetElement alarmTargetElement;

	public AlarmTargetElementGraphql(final AlarmTargetElement alarmTargetElement) {

		AlarmTargetElementHelper.assign(alarmTargetElement, this);

		this.alarmTargetElement = alarmTargetElement;
	}

	@Override
	public ElementVO getSourceElement() {

		if (super.getSourceElement() == null) {

			if (alarmTargetElement != null) {

				Element element = EntitiesManager.getInstance().getElementRepository().get(
						alarmTargetElement.getSourceElementTypeId(), alarmTargetElement.getSourceElementId(), false);

				if (element != null) {

					super.setSourceElement(new ElementGraphql(element));
				}

			} else {

				log.error("alarmTargetElement has null pointer");
			}

		}

		return super.getSourceElement();
	}

	@Override
	public ElementVO getTargetElement() {

		if (super.getTargetElement() == null) {

			if (alarmTargetElement != null) {

				Element element = EntitiesManager.getInstance().getElementRepository().get(
						alarmTargetElement.getTargetElementTypeId(), alarmTargetElement.getTargetElementId(), false);

				if (element != null) {

					super.setTargetElement(new ElementGraphql(element));
				}

			} else {

				log.error("alarmTargetElement has null pointer");
			}

		}

		return super.getTargetElement();
	}
}
