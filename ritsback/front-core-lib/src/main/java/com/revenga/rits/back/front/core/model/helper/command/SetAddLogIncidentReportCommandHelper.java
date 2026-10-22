package com.revenga.rits.back.front.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.command.SetAddLogIncidentReportCommand;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportLogHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogVO;

public class SetAddLogIncidentReportCommandHelper {

	private SetAddLogIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetAddLogIncidentReportCommand get(List<ImsIncidentReportLogVO> incidentReportLogs) {

		SetAddLogIncidentReportCommand command = null;

		if (!CollectionUtils.isEmpty(incidentReportLogs)) {

			command = new SetAddLogIncidentReportCommand();

			for (ImsIncidentReportLogVO imsIncidentReportLogVO : incidentReportLogs) {

				command.addIncidentReportLog(ImsIncidentReportLogHelper.get(imsIncidentReportLogVO));
			}
		}

		return command;
	}

}
