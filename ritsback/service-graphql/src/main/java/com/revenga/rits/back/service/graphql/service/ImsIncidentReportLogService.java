package com.revenga.rits.back.service.graphql.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.command.SetAddLogIncidentReportCommand;
import com.revenga.rits.back.data.core.model.command.SetDeleteLogIncidentReportCommand;
import com.revenga.rits.back.front.core.model.helper.command.SetAddLogIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetDeleteLogIncidentReportCommandHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogVO;

@Service
public class ImsIncidentReportLogService implements QueryInterface<ImsIncidentReportLogVO> {

	@Override
	public List<ImsIncidentReportLogVO> getAll() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public Optional<ImsIncidentReportLogVO> get(String idItem) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ImsIncidentReportLogVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	public boolean addLogIncidentReport(List<ImsIncidentReportLogVO> incidentReportLogs) {

		boolean resp = true;
		SetAddLogIncidentReportCommand command = SetAddLogIncidentReportCommandHelper.get(incidentReportLogs);

		resp = command != null && EntitiesManager.getInstance().sendCommand(command) != null;

		if (resp && !CollectionUtils.isEmpty(command.getIncidentReportLogs())) {

			for (ImsIncidentReportLog incidentReportLog : command.getIncidentReportLogs()) {

				resp &= EntitiesManager.getInstance().blockIncidentReports(incidentReportLog.getIncidentReportId());
			}
		}

		return resp;
	}

	public boolean deleteLogIncidentReport(String incidentReportLogId) {

		SetDeleteLogIncidentReportCommand command = SetDeleteLogIncidentReportCommandHelper.get(incidentReportLogId);

		return command != null && EntitiesManager.getInstance().sendCommand(command) != null;
	}
}
