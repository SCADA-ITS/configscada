package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportLogHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogValueVO;
import com.revenga.rits.back.front.core.model.vo.LogSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ImsIncidentReportLogGraphql extends ImsIncidentReportLogVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 8331238977473701541L;

	private ImsIncidentReportLog imsIncidentReportLog;
	private List<ImsIncidentReportLogValue> imsIncidentReportLogValues;

	public ImsIncidentReportLogGraphql(final ImsIncidentReportLog imsIncidentReportLog,
			final List<ImsIncidentReportLogValue> imsIncidentReportLogValues) {

		ImsIncidentReportLogHelper.assign(imsIncidentReportLog, this);
		this.imsIncidentReportLog = imsIncidentReportLog;
		this.imsIncidentReportLogValues = imsIncidentReportLogValues;

	}

	@Override
	public UserVO getUser() {

		if (super.getUser() == null && imsIncidentReportLog != null && imsIncidentReportLog.getUserId() != null) {

			User user = EntitiesManager.getInstance().getUserRepository().get(imsIncidentReportLog.getUserId(), false);

			if (user != null) {

				super.setUser(new UserGraphql(user));
			}
		}

		return super.getUser();
	}

	@Override
	public List<ImsIncidentReportLogValueVO> getIncidentReportLogValues() {

		if (super.getIncidentReportLogValues() == null) {

			if (imsIncidentReportLog != null) {

				if (!CollectionUtils.isEmpty(imsIncidentReportLogValues)) {

					List<ImsIncidentReportLogValueVO> imsIncidentReportLogValuesVO = new ArrayList<>();

					for (ImsIncidentReportLogValue imsIncidentReportLogValue : imsIncidentReportLogValues) {

						if (imsIncidentReportLogValue.getIncidentReportLogId().equals(imsIncidentReportLog.getId()))
							
							imsIncidentReportLogValuesVO
									.add(new ImsIncidentReportLogValueGraphql(imsIncidentReportLogValue));
					}
					super.setIncidentReportLogValues(imsIncidentReportLogValuesVO);
				}

			} else {

				log.error("imsIncidentReportLog has null pointer");
			}
		}

		return super.getIncidentReportLogValues();
	}

	@Override
	public LogSubtypeVO getLogSubtype() {

		if (super.getLogSubtype() == null) {

			if (imsIncidentReportLog != null && imsIncidentReportLog.getLogSubtypeId() != null) {

				LogSubtype logSubtype = EntitiesManager.getInstance().getLogSubtypeRepository()
						.get(imsIncidentReportLog.getLogSubtypeId(), false);

				if (logSubtype != null) {

					super.setLogSubtype(new LogSubtypeGraphql(logSubtype));
				}
			}
		}
		return super.getLogSubtype();
	}
}
