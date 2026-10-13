package com.revenga.rits.back.front.core.model.helper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogValueVO;

public class ImsIncidentReportLogHelper {
	
	private ImsIncidentReportLogHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentReportLogVO get(final ImsIncidentReportLog imsIncidentReportLog) {

		ImsIncidentReportLogVO imsIncidentReportLogVO = null;

		if (imsIncidentReportLog != null) {

			imsIncidentReportLogVO = new ImsIncidentReportLogVO();

			assign(imsIncidentReportLog, imsIncidentReportLogVO);
		}

		return imsIncidentReportLogVO;
	}

	public static ImsIncidentReportLog get(final ImsIncidentReportLogVO imsIncidentReportLogVO) {

		ImsIncidentReportLog imsIncidentReportLog = null;

		if (imsIncidentReportLogVO != null) {

			imsIncidentReportLog = new ImsIncidentReportLog();
			if (imsIncidentReportLogVO.getId() != null) {

				Key key = new Key(imsIncidentReportLogVO.getId());

				if (key.getClazz() == ImsIncidentReportLog.class) {

					imsIncidentReportLog.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentReportLog.setLevel(LogLevelHelper.get(imsIncidentReportLogVO.getLevel()).intValue());
	
			imsIncidentReportLog.setTime(Instant.now().toEpochMilli());
			
			if (imsIncidentReportLogVO.getUserId() != null) {

				Key key = new Key(imsIncidentReportLogVO.getUserId());

				if (key.getClazz() == User.class) {

					imsIncidentReportLog.setUserId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentReportLogVO.getLogSubtypeId() != null) {

				Key key = new Key(imsIncidentReportLogVO.getLogSubtypeId());

				if (key.getClazz() == LogSubtype.class) {

					imsIncidentReportLog.setLogSubtypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentReportLogVO.getIncidentReportId() != null) {

				Key key = new Key(imsIncidentReportLogVO.getIncidentReportId());

				if (key.getClazz() == ImsIncidentReport.class) {

					imsIncidentReportLog.setIncidentReportId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentReportLogVO.getIncidentReportLogValues() != null) {

				List<ImsIncidentReportLogValue> imsIncidentReportLogValues = new ArrayList<>();

				for (ImsIncidentReportLogValueVO imsIncidentReportLogValueVO : imsIncidentReportLogVO.getIncidentReportLogValues()) {

					ImsIncidentReportLogValue imsIncidentReportLogValue = ImsIncidentReportLogValueHelper.get(imsIncidentReportLogValueVO);

					imsIncidentReportLogValues.add(imsIncidentReportLogValue);
				}

				imsIncidentReportLog.setIncidentReportLogValues(imsIncidentReportLogValues);
			}

		}

		return imsIncidentReportLog;
	}

	public static void assign(final ImsIncidentReportLog imsIncidentReportLog,
			ImsIncidentReportLogVO imsIncidentReportLogVO) {

		if (ObjectUtils.allNotNull(imsIncidentReportLog, imsIncidentReportLogVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentReportLogVO, imsIncidentReportLog);

			imsIncidentReportLogVO.setLevel(LogLevelHelper.get(imsIncidentReportLog.getLevel().longValue()));
			imsIncidentReportLogVO.setTime(imsIncidentReportLog.getTime());
			
			if (imsIncidentReportLog.getIncidentReportLogValues() != null) {

				List<ImsIncidentReportLogValueVO> imsIncidentReportLogValuesVO = new ArrayList<>();

				for (ImsIncidentReportLogValue imsIncidentReportLogValue : imsIncidentReportLog.getIncidentReportLogValues()) {

					if (imsIncidentReportLog.getIncidentReportId() != null) {

						imsIncidentReportLogValuesVO.add(ImsIncidentReportLogValueHelper.get(imsIncidentReportLogValue));
					}
				}

				imsIncidentReportLogVO.setIncidentReportLogValues(imsIncidentReportLogValuesVO);
			}

		}
	}

}
