package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.front.core.model.helper.AlarmConfigMeasureHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigMeasureVO;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class AlarmConfigMeasureGraphql extends AlarmConfigMeasureVO {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5081806387727870958L;

	private AlarmConfigMeasure alarmConfigMeasure;

	public AlarmConfigMeasureGraphql(final AlarmConfigMeasure alarmConfigMeasure) {

		AlarmConfigMeasureHelper.assign(alarmConfigMeasure, this);

		this.alarmConfigMeasure = alarmConfigMeasure;
	}
	
	@Override
	public ThresholdOperationVO getAlarmMeasureOperation() {

		if (super.getAlarmMeasureOperation() == null && alarmConfigMeasure != null
				&& alarmConfigMeasure.getAlarmMeasureOperationId() != null) {

			ThresholdOperation thresholdOperation = EntitiesManager.getInstance().getThresholdOperationRepository()
					.get(alarmConfigMeasure.getAlarmMeasureOperationId(), false);

			if (thresholdOperation != null) {

				super.setAlarmMeasureOperation(new ThresholdOperationGraphql(thresholdOperation));
			}
		}

		return super.getAlarmMeasureOperation();
	}

	@Override
	public ElementTypeParamVO getElementTypeParam() {

		if (super.getElementTypeParam() == null) {

			if (alarmConfigMeasure != null) {

				ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParamRepository().get(
						alarmConfigMeasure.getElementTypeId(), alarmConfigMeasure.getParamTypeId(),
						alarmConfigMeasure.getElementTypeParamId(), false);

				if (elementTypeParam != null) {

					super.setElementTypeParam(new ElementTypeParamGraphql(elementTypeParam));
				}

			} else {

				log.error("ElementTypeParam has null pointer");
			}

		}

		return super.getElementTypeParam();

	}

}
