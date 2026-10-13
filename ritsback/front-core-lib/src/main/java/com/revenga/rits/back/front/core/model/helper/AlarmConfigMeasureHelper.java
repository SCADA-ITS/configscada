package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigMeasureVO;

public class AlarmConfigMeasureHelper {

	private AlarmConfigMeasureHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}

	public static AlarmConfigMeasureVO get(final AlarmConfigMeasure alarmConfigMeasure) {

		AlarmConfigMeasureVO alarmConfigMeasureVO = null;

		if (alarmConfigMeasure != null) {

			alarmConfigMeasureVO = new AlarmConfigMeasureVO();

			assign(alarmConfigMeasure, alarmConfigMeasureVO);
		}

		return alarmConfigMeasureVO;
	}

	public static AlarmConfigMeasure get(final AlarmConfigMeasureVO alarmConfigMeasureVO) {

		AlarmConfigMeasure alarmConfigMeasure = null;

		if (alarmConfigMeasureVO != null) {

			alarmConfigMeasure = new AlarmConfigMeasure();
			

			if (alarmConfigMeasureVO.getElementTypeParamId() != null) {

				Key key = new Key(alarmConfigMeasureVO.getElementTypeParamId());

				if (key.getClazz() == ElementTypeParam.class) {

					alarmConfigMeasure.setElementTypeId(Long.valueOf(key.getId(1)));
					alarmConfigMeasure.setParamTypeId(Long.valueOf(key.getId(2)));
					alarmConfigMeasure.setElementTypeParamId(Long.valueOf(key.getId(3)));
					
					if (ObjectUtils.allNotNull(alarmConfigMeasure.getElementTypeParamId(),
							alarmConfigMeasure.getElementTypeId(), alarmConfigMeasure.getParamTypeId())) {

						ElementTypeParam elementTypeParam = new ElementTypeParam();
						elementTypeParam.setId(alarmConfigMeasure.getElementTypeParamId());
						elementTypeParam.setElementTypeId(alarmConfigMeasure.getElementTypeId());
						elementTypeParam.setParamTypeId(alarmConfigMeasure.getParamTypeId());
						
						alarmConfigMeasureVO.setElementTypeParam(ElementTypeParamHelper.get(elementTypeParam));
					}
				}
			}

			if (alarmConfigMeasureVO.getElementTypeParam() != null) {

				ElementTypeParam elementTypeParam = ElementTypeParamHelper
						.get(alarmConfigMeasureVO.getElementTypeParam());

				if (elementTypeParam != null) {

					if (alarmConfigMeasureVO.getElementTypeParamId() == null) {

						alarmConfigMeasure.setElementTypeParamId(elementTypeParam.getId());
						alarmConfigMeasure.setParamTypeId(elementTypeParam.getParamTypeId());
						alarmConfigMeasure.setElementTypeParamId(elementTypeParam.getElementTypeId());
					}
					alarmConfigMeasure.setElementTypeParam(elementTypeParam);
				}
			}

			if (alarmConfigMeasureVO.getId() != null) {

				Key key = new Key(alarmConfigMeasureVO.getId());

				if (key.getClazz() == AlarmConfigMeasure.class) {

					alarmConfigMeasure.setAlarmConfigId(Long.valueOf(key.getId(1)));
					alarmConfigMeasure.setElementTypeId(Long.valueOf(key.getId(2)));
					alarmConfigMeasure.setParamTypeId(Long.valueOf(key.getId(3)));
					alarmConfigMeasure.setElementTypeParamId(Long.valueOf(key.getId(4)));
				}
			}

			if (alarmConfigMeasureVO.getAlarmMeasureOperationId() != null) {

				Key key = new Key(alarmConfigMeasureVO.getAlarmMeasureOperationId());

				if (key.getClazz() == ThresholdOperation.class) {

					alarmConfigMeasure.setAlarmMeasureOperationId(Long.valueOf(key.getId(1)));
					
					ThresholdOperation thresholdOperation = new ThresholdOperation();
					
					thresholdOperation.setId(alarmConfigMeasure.getAlarmMeasureOperationId());
				}
			}

			alarmConfigMeasure.setThresholdVal1(alarmConfigMeasureVO.getThresholdVal1());
			alarmConfigMeasure.setThresholdVal2(alarmConfigMeasureVO.getThresholdVal2());
		}

		return alarmConfigMeasure;
	}

	public static void assign(final AlarmConfigMeasure alarmConfigMeasure, AlarmConfigMeasureVO alarmConfigMeasureVO) {

		if (ObjectUtils.allNotNull(alarmConfigMeasure, alarmConfigMeasureVO)) {

			BasicElementHelper.fillBasicProperties(alarmConfigMeasureVO, alarmConfigMeasure);

			alarmConfigMeasureVO.setThresholdVal1(alarmConfigMeasure.getThresholdVal1());
			alarmConfigMeasureVO.setThresholdVal2(alarmConfigMeasure.getThresholdVal2());
		}

	}
}
