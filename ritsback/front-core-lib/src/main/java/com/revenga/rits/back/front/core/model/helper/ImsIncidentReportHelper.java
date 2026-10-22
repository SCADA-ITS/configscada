package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportVO;

public class ImsIncidentReportHelper {
	
	private ImsIncidentReportHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentReportVO get(final ImsIncidentReport imsIncidentReport) {

		ImsIncidentReportVO imsIncidentReportVO = null;

		if (imsIncidentReport != null) {

			imsIncidentReportVO = new ImsIncidentReportVO();

			assign(imsIncidentReport, imsIncidentReportVO);
		}

		return imsIncidentReportVO;
	}

	public static ImsIncidentReport get(final ImsIncidentReportVO imsIncidentReportVO) {

		ImsIncidentReport imsIncidentReport = null;

		if (imsIncidentReportVO != null) {

			imsIncidentReport = new ImsIncidentReport();
			if (imsIncidentReportVO.getId() != null) {

				Key key = new Key(imsIncidentReportVO.getId());

				if (key.getClazz() == ImsIncidentReport.class) {

					imsIncidentReport.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentReport.setConfirm(imsIncidentReportVO.getConfirm());
			imsIncidentReport.setValidForMin(imsIncidentReportVO.getValidForMin());
			imsIncidentReport.setGeneratedAt(imsIncidentReportVO.getGeneratedAt());
			imsIncidentReport.setLastUpdate(imsIncidentReportVO.getLastUpdate());

		}

		return imsIncidentReport;
	}

	public static void assign(final ImsIncidentReport imsIncidentReport,
			ImsIncidentReportVO imsIncidentReportVO) {

		if (ObjectUtils.allNotNull(imsIncidentReport, imsIncidentReportVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentReportVO, imsIncidentReport);

			imsIncidentReportVO.setConfirm(imsIncidentReport.getConfirm());
			imsIncidentReportVO.setValidForMin(imsIncidentReport.getValidForMin());
			imsIncidentReportVO.setGeneratedAt(imsIncidentReport.getGeneratedAt());
			imsIncidentReportVO.setLastUpdate(imsIncidentReport.getLastUpdate());
		}
	}


}
