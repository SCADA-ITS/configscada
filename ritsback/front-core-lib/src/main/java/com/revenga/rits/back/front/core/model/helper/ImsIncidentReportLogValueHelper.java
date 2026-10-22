package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogValueVO;

public class ImsIncidentReportLogValueHelper {
	
	private ImsIncidentReportLogValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentReportLogValueVO get(final ImsIncidentReportLogValue imsIncidentReportLogValue) {

		ImsIncidentReportLogValueVO imsIncidentReportLogValueVO = null;

		if (imsIncidentReportLogValue != null) {

			imsIncidentReportLogValueVO = new ImsIncidentReportLogValueVO();

			assign(imsIncidentReportLogValue, imsIncidentReportLogValueVO);
		}

		return imsIncidentReportLogValueVO;
	}

	public static ImsIncidentReportLogValue get(final ImsIncidentReportLogValueVO imsIncidentReportLogValueVO) {

		ImsIncidentReportLogValue imsIncidentReportLogValue = null;

		if (imsIncidentReportLogValueVO != null) {

			imsIncidentReportLogValue = new ImsIncidentReportLogValue();
			
			if (imsIncidentReportLogValueVO.getLogTypeParamId() != null) {

				Key key = new Key(imsIncidentReportLogValueVO.getLogTypeParamId());

				if (key.getClazz() == LogTypeParam.class) {

					imsIncidentReportLogValue.setLogTypeId(Long.valueOf(key.getId(1)));
					imsIncidentReportLogValue.setLogTypeParamId(Long.valueOf(key.getId(2)));
				}
			}
			
			if (imsIncidentReportLogValueVO.getId() != null) {

				Key key = new Key(imsIncidentReportLogValueVO.getId());

				if (key.getClazz() == ImsIncidentReportLogValue.class) {

					imsIncidentReportLogValue.setIncidentReportLogId(Long.valueOf(key.getId(1)));
					imsIncidentReportLogValue.setLogTypeId(Long.valueOf(key.getId(2)));
					imsIncidentReportLogValue.setLogTypeParamId(Long.valueOf(key.getId(3)));
				}
			}

			imsIncidentReportLogValue.setValue(imsIncidentReportLogValueVO.getValue());		

			if (imsIncidentReportLogValueVO.getLogTypeParam() != null) {

				LogTypeParam logTypeParam = LogTypeParamHelper
						.get(imsIncidentReportLogValueVO.getLogTypeParam());

				if (logTypeParam != null) {

					if (imsIncidentReportLogValueVO.getLogTypeParamId() == null) {

						imsIncidentReportLogValue.setLogTypeId(logTypeParam.getLogTypeId());
						imsIncidentReportLogValue.setLogTypeParamId(logTypeParam.getId());
					}
					imsIncidentReportLogValue.setLogTypeParam(logTypeParam);
				}
			}
		}

		return imsIncidentReportLogValue;
	}
	
	public static void assign(final ImsIncidentReportLogValue imsIncidentReportLogValue, ImsIncidentReportLogValueVO imsIncidentReportLogValueVO) {

		if (ObjectUtils.allNotNull(imsIncidentReportLogValue, imsIncidentReportLogValueVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentReportLogValueVO, imsIncidentReportLogValue);

			imsIncidentReportLogValueVO.setValue(imsIncidentReportLogValue.getValue());
			
			if (imsIncidentReportLogValue.getLogTypeParamId() != null) {

				LogTypeParam logTypeParam = new LogTypeParam();
				logTypeParam.setId(imsIncidentReportLogValue.getLogTypeParamId());
				logTypeParam.setLogTypeId(Long.valueOf(LogType.LOG_TYPE_IMS));
				imsIncidentReportLogValueVO.setLogTypeParamId(BasicElementHelper.getIdFromElement(logTypeParam));
			}

			imsIncidentReportLogValueVO
					.setLogTypeParam(LogTypeParamHelper.get(imsIncidentReportLogValue.getLogTypeParam()));

			if (imsIncidentReportLogValue.getLogTypeParamId() == null && imsIncidentReportLogValue.getLogTypeParam() != null
					&& imsIncidentReportLogValue.getLogTypeParam().getId() != null) {

				imsIncidentReportLogValueVO.setLogTypeParamId(
						BasicElementHelper.getIdFromElement(imsIncidentReportLogValue.getLogTypeParam()));
			}

		}

	}

}
