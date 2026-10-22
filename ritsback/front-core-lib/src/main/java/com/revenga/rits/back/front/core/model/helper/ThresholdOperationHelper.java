package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;

public class ThresholdOperationHelper {

	private ThresholdOperationHelper() {

		super();
	}

	public static ThresholdOperationVO get(ThresholdOperation alarmThresholdOperation) {

		ThresholdOperationVO alarmThresholdOperationVO = null;

		if (alarmThresholdOperation != null) {

			alarmThresholdOperationVO = new ThresholdOperationVO();

			assign(alarmThresholdOperation, alarmThresholdOperationVO);
		}

		return alarmThresholdOperationVO;
	}

	public static ThresholdOperation get(ThresholdOperationVO alarmThresholdOperationVO) {

		ThresholdOperation alarmThresholdOperation = null;

		if (alarmThresholdOperationVO != null) {

			alarmThresholdOperation = new ThresholdOperation();

			if (alarmThresholdOperationVO.getId() != null) {

				Key key = new Key(alarmThresholdOperationVO.getId());

				if (key.getClazz() == ThresholdOperation.class) {

					alarmThresholdOperation.setId(Long.valueOf(key.getId(1)));
				}
			}

			alarmThresholdOperation.setAlias(alarmThresholdOperationVO.getAlias());
			alarmThresholdOperation.setDescription((alarmThresholdOperationVO.getDescription()));

		}

		return alarmThresholdOperation;
	}

	public static void assign(final ThresholdOperation alarmThresholdOperation,
			ThresholdOperationVO alarmThresholdOperationVO) {

		if (alarmThresholdOperation != null && alarmThresholdOperationVO != null) {

			BasicElementHelper.fillBasicProperties(alarmThresholdOperationVO, alarmThresholdOperation);

		}
	}

}
