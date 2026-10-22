package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.SmThreshold;
import com.revenga.rits.back.front.core.model.vo.SmThresholdVO;

public class SmThresholdHelper {

	private SmThresholdHelper() {
		super();
	}

	public static SmThresholdVO get(final SmThreshold smThreshold) {

		SmThresholdVO smThresholdVO = null;

		if (smThreshold != null) {

			smThresholdVO = new SmThresholdVO();

			assign(smThreshold, smThresholdVO);
		}

		return smThresholdVO;
	}

	public static SmThreshold get(final SmThresholdVO smThresholdVO) {

		SmThreshold smThreshold = null;

		if (smThresholdVO != null) {
			
			smThreshold = new SmThreshold();

			if (smThresholdVO.getId() != null) {

				Key key = new Key(smThresholdVO.getId());

				if (key.getClazz() == SmThreshold.class) {

					smThreshold.setId(Long.valueOf(key.getId(1)));
				}
			}

			if (smThresholdVO.getDataTypeId() != null) {

				Key key = new Key(smThresholdVO.getDataTypeId());

				if (key.getClazz() == DataType.class) {

					smThreshold.setDataTypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			smThreshold.setAlias(smThresholdVO.getAlias());
			smThreshold.setDescription((smThresholdVO.getDescription()));
			smThreshold.setLabelAlias(smThresholdVO.getLabelAlias());
			smThreshold.setLabelDescription(smThresholdVO.getLabelDescription());
			smThreshold.setValue(smThresholdVO.getValue());
		}
		
		return smThreshold;
	}

	public static void assign(final SmThreshold smThreshold, SmThresholdVO smThresholdVO) {

		if (smThreshold != null && smThresholdVO != null) {

			BasicElementHelper.fillBasicProperties(smThresholdVO, smThreshold);

		if (smThreshold.getDataTypeId() != null) {
			
				DataType dataType = new DataType();
				dataType.setId(smThreshold.getDataTypeId());
				smThresholdVO.setDataTypeId(BasicElementHelper.getIdFromElement(dataType));
			}
		
		smThresholdVO.setValue(smThreshold.getValue());
		
		}

		ModelVOHelper.translate(smThresholdVO);
	}
}