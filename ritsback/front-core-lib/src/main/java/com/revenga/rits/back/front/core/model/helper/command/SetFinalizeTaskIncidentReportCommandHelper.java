package com.revenga.rits.back.front.core.model.helper.command;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.data.core.model.command.SetFinalizeTaskIncidentReportCommand;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskValueVO;

public class SetFinalizeTaskIncidentReportCommandHelper {

	private SetFinalizeTaskIncidentReportCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetFinalizeTaskIncidentReportCommand get(String incidentReportId, String incidentTypeTaskId,
			List<ImsIncidentTypeTaskValueVO> incidentTypeTaskValues) {

		SetFinalizeTaskIncidentReportCommand command = null;

		if (StringUtils.isNoneBlank(incidentReportId, incidentTypeTaskId)) {

			Key incidentReportKey = new Key(incidentReportId);
			Key incidentTypeTaskKey = new Key(incidentTypeTaskId);

			if (incidentReportKey.getClazz() == ImsIncidentReport.class
					&& incidentTypeTaskKey.getClazz() == ImsIncidentTypeTask.class) {

				command = new SetFinalizeTaskIncidentReportCommand();
				command.setIncidentReportId(Long.valueOf(incidentReportKey.getId(1)));
				command.setIncidentTypeTaskId(Long.valueOf(incidentTypeTaskKey.getId(1)));
			}

			if (!CollectionUtils.isEmpty(incidentTypeTaskValues)) {

				for (ImsIncidentTypeTaskValueVO incidentTypeTaskValue : incidentTypeTaskValues) {

					if (incidentTypeTaskValue.getTaskTypeParamId() != null
							&& incidentTypeTaskValue.getValue() != null) {

						Key taskTypeParamIdKey = new Key(incidentTypeTaskValue.getTaskTypeParamId());

						if (taskTypeParamIdKey.getClazz() == ImsTaskTypeParam.class) {

							command.addIncidentTypeTaskValue(Long.valueOf(taskTypeParamIdKey.getId(1)),
									Long.valueOf(taskTypeParamIdKey.getId(2)), incidentTypeTaskValue.getValue());
						}
					}
				}
			}
		}
		return command;
	}

}
