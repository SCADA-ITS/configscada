package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.data.core.model.ImsIncidentParamGroup;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentParamVO;

public class ImsIncidentParamHelper {

	private ImsIncidentParamHelper() {
		super();
	}

	public static ImsIncidentParamVO get(final ImsIncidentParam imsIncidentParam) {

		ImsIncidentParamVO imsIncidentParamVO = null;

		if (imsIncidentParam != null) {

			imsIncidentParamVO = new ImsIncidentParamVO();

			assign(imsIncidentParam, imsIncidentParamVO);
		}

		return imsIncidentParamVO;
	}

	public static ImsIncidentParam get(final ImsIncidentParamVO imsIncidentParamVO) {

		ImsIncidentParam imsIncidentParam = null;

		if (imsIncidentParamVO != null) {

			imsIncidentParam = new ImsIncidentParam();

			if (imsIncidentParamVO.getId() != null) {

				Key key = new Key(imsIncidentParamVO.getId());

				if (key.getClazz() == ImsIncidentParam.class) {

					imsIncidentParam.setId(Long.valueOf(key.getId(1)));
					
				}
			}

			if (imsIncidentParamVO.getIncidentParamGroupId() != null) {

				Key key = new Key(imsIncidentParamVO.getIncidentParamGroupId());

				if (key.getClazz() == ImsIncidentParamGroup.class) {

					imsIncidentParam.setIncidentParamGroupId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentParamVO.getDataTypeId() != null) {

				Key key = new Key(imsIncidentParamVO.getDataTypeId());

				if (key.getClazz() == DataType.class) {

					imsIncidentParam.setDataTypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			imsIncidentParam.setAlias(imsIncidentParamVO.getAlias());
			imsIncidentParam.setDescription((imsIncidentParamVO.getDescription()));
			imsIncidentParam.setThreshold1(imsIncidentParamVO.getThreshold1());
			imsIncidentParam.setThreshold2(imsIncidentParamVO.getThreshold2());
			imsIncidentParam.setLabelAlias(imsIncidentParamVO.getLabelAlias());
			imsIncidentParam.setLabelDescription(imsIncidentParamVO.getLabelDescription());
		}
		
		return imsIncidentParam;
	}

	public static void assign(final ImsIncidentParam imsIncidentParam, ImsIncidentParamVO imsIncidentParamVO) {

		if (imsIncidentParam != null && imsIncidentParamVO != null) {

			BasicElementHelper.fillBasicProperties(imsIncidentParamVO, imsIncidentParam);

			if (ObjectUtils.allNotNull(imsIncidentParam.getIncidentParamUnitId())
					&& ObjectUtils.allNotNull(imsIncidentParam.getIncidentParamUnit().getLabelAlias())) {

				imsIncidentParamVO.setLabelUnit(imsIncidentParam.getIncidentParamUnit().getLabelAlias());
			}

			if (imsIncidentParam.getIncidentParamGroupId() != null) {

				ImsIncidentParamGroup incidentParamGroup = new ImsIncidentParamGroup();
				incidentParamGroup.setId(imsIncidentParam.getIncidentParamGroupId());
				imsIncidentParamVO.setIncidentParamGroupId(BasicElementHelper.getIdFromElement(incidentParamGroup));	
			}

			if (imsIncidentParam.getDataTypeId() != null) {

				DataType dataType = new DataType();
				dataType.setId(imsIncidentParam.getDataTypeId());
				imsIncidentParamVO.setDataTypeId(BasicElementHelper.getIdFromElement(dataType));	
			}

			imsIncidentParamVO.setAlias(imsIncidentParam.getAlias());
			imsIncidentParamVO.setThreshold1(imsIncidentParam.getThreshold1());
			imsIncidentParamVO.setThreshold2(imsIncidentParam.getThreshold2());
			imsIncidentParamVO.setDescription(imsIncidentParam.getDescription());
			imsIncidentParamVO.setLabelAlias(imsIncidentParam.getLabelAlias());
			imsIncidentParamVO.setLabelDescription(imsIncidentParam.getLabelDescription());
		}

		ModelVOHelper.translate(imsIncidentParamVO);
	}
}
