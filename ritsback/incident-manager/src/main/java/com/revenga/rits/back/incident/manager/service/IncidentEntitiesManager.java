package com.revenga.rits.back.incident.manager.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskElement;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.data.core.model.StretchStretchStretch;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.ResponseCommand;
import com.revenga.rits.back.data.core.model.command.ResponseToIncidentManagerCommand;
import com.revenga.rits.back.data.core.model.command.SetAddCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetBlockIncidentReportsPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetDeleteCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetGenericData;
import com.revenga.rits.back.data.core.model.command.SetIncidentReportsPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetPlanActionsCommand;
import com.revenga.rits.back.data.core.model.command.SetPlanActionsCommand.Action;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.helper.command.PmCommandElementHelper;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.GenericDataRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentAlarmConditionRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentGroupRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentLevelRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportAlarmRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportLogRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportLogValueRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportParamValueRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportTaskElementRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportTaskPlanStateRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportTaskStateRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentReportTaskValueRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentStateRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentTaskPlanRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentTypeRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentTypeStateTransitionRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentTypeStretchRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentTypeTaskRepository;
import com.revenga.rits.back.entities.lib.repository.ImsIncidentTypeTaskValueRepository;
import com.revenga.rits.back.entities.lib.repository.ImsTaskTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.LocationRepository;
import com.revenga.rits.back.entities.lib.repository.PlanRepository;
import com.revenga.rits.back.entities.lib.repository.RoadRepository;
import com.revenga.rits.back.entities.lib.repository.SpathStretchRepository;
import com.revenga.rits.back.entities.lib.repository.StretchRepository;
import com.revenga.rits.back.entities.lib.repository.StretchStretchStretchRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.incident.manager.task.model.SignallingCommandToSpath;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.manager.lib.service.ResponseManager;

import groovy.lang.GroovyObject;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class IncidentEntitiesManager {

	private static final String FILE_APP_PROPS = "application.properties";

	private static final String GROOVY_PATH = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.manager.groovy_path");

	private static final String GROOVY_INCIDENT_TYPE_PATH = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.manager.groovy_incident_type_path");

	private static final String METHOD_PROCESS = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.manager.method.process");

	private static final String METHOD_GET_ELEMENTS = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.manager.method.get_elements");

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "entities.manager.retention_time_conf"));

	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "entities.manager.retention_time_master"));

	private static final Long CACHE_RETENTION_TIME_STATIC = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "entities.manager.retention_time_static"));

	private static final String INSERT_SELECT_QUERY = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.entities.manager.insert_select_query");
	private static final String INCIDENT_REPORT_FIELD = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.entities.manager.incident_report_field");
	private static final String INCIDENT_REPORT_LOG_FIELD = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.entities.manager.incident_report_log_field");

	private static final String INCIDENT_REPORT_SOURCE_TABLE = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.entities.manager.incident_report_source_table");
	private static final String INCIDENT_REPORT_TARGET_TABLE = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.entities.manager.incident_report_target_table");
	private static final String INCIDENT_REPORT_SOURCE_FIELDS = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.entities.manager.incident_report_source_fields");
	private static final String INCIDENT_REPORT_TARGET_FIELDS = ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"incident.entities.manager.incident_report_target_fields");

	private static final String INCIDENT_REPORT_TASK_VALUE_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_value_source_table");
	private static final String INCIDENT_REPORT_TASK_VALUE_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_value_target_table");
	private static final String INCIDENT_REPORT_TASK_VALUE_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_value_source_fields");
	private static final String INCIDENT_REPORT_TASK_VALUE_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_value_target_fields");

	private static final String INCIDENT_REPORT_TASK_ELEMENT_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_element_source_table");
	private static final String INCIDENT_REPORT_TASK_ELEMENT_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_element_target_table");
	private static final String INCIDENT_REPORT_TASK_ELEMENT_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_element_source_fields");
	private static final String INCIDENT_REPORT_TASK_ELEMENT_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_element_target_fields");

	private static final String INCIDENT_REPORT_TASK_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_source_table");
	private static final String INCIDENT_REPORT_TASK_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_target_table");
	private static final String INCIDENT_REPORT_TASK_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_source_fields");
	private static final String INCIDENT_REPORT_TASK_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_target_fields");
	private static final String INCIDENT_REPORT_TASK_PLAN_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_plan_source_table");
	private static final String INCIDENT_REPORT_TASK_PLAN_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_plan_target_table");
	private static final String INCIDENT_REPORT_TASK_PLAN_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_plan_source_fields");
	private static final String INCIDENT_REPORT_TASK_PLAN_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_task_plan_target_fields");

	private static final String INCIDENT_REPORT_LOG_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_source_table");
	private static final String INCIDENT_REPORT_LOG_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_target_table");
	private static final String INCIDENT_REPORT_LOG_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_source_fields");
	private static final String INCIDENT_REPORT_LOG_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_target_fields");

	private static final String INCIDENT_REPORT_LOG_VALUES_QUERY = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_values_query");
	private static final String INCIDENT_REPORT_LOG_VALUES_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_values_source_table");
	private static final String INCIDENT_REPORT_LOG_VALUES_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_values_target_table");
	private static final String INCIDENT_REPORT_LOG_VALUES_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_values_source_fields");
	private static final String INCIDENT_REPORT_LOG_VALUES_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_log_values_target_fields");

	private static final String INCIDENT_REPORT_ALARMS_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_alarms_source_table");
	private static final String INCIDENT_REPORT_ALARMS_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_alarms_target_table");
	private static final String INCIDENT_REPORT_ALARMS_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_alarms_source_fields");
	private static final String INCIDENT_REPORT_ALARMS_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_alarms_target_fields");

	private static final String INCIDENT_REPORT_PARAM_VALUES_SOURCE_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_param_values_source_table");
	private static final String INCIDENT_REPORT_PARAM_VALUES_TARGET_TABLE = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_param_values_target_table");
	private static final String INCIDENT_REPORT_PARAM_VALUES_SOURCE_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_param_values_source_fields");
	private static final String INCIDENT_REPORT_PARAM_VALUES_TARGET_FIELDS = ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "incident.entities.manager.incident_report_param_values_target_fileds");

	private static final String JAVA_GROOVY_PACKAGE = "com.revenga.rits.back.incident.manager.groovy";

	PlanRepository planRepository;

	ImsIncidentReportRepository incidentReportRepository;
	ImsIncidentReportTaskStateRepository incidentReportTaskStateRepository;
	ImsIncidentReportTaskValueRepository incidentReportTaskValueRepository;
	ImsIncidentReportTaskElementRepository incidentReportTaskElementRepository;

	ImsIncidentReportTaskPlanStateRepository incidentReportTaskPlanStateRepository;
	ImsIncidentReportAlarmRepository incidentReportAlarmRepository;

	ImsIncidentTypeTaskRepository incidentTypeTaskRepository;
	ImsIncidentTypeTaskValueRepository incidentTypeTaskValueRepository;

	ImsIncidentReportParamValueRepository incidentParamValueRepository;

	ImsTaskTypeParamRepository imsTaskTypeParamRepository;
	ImsIncidentTaskPlanRepository incidentTaskPlanRepository;
	ImsIncidentStateRepository incidentStateRepository;
	ImsIncidentTypeStateTransitionRepository incidentTypeStateTransitionReposirory;
	ImsIncidentTypeStretchRepository incidentTypeStretchRepository;
	ImsIncidentTypeRepository incidentTypeRepository;
	ImsIncidentReportLogRepository incidentReportLogRepository;
	ImsIncidentReportLogValueRepository incidentReportLogValueRepository;
	SpathStretchRepository spathStretchRepository;
	ImsIncidentAlarmConditionRepository incidentAlarmConditionRepository;
	StretchStretchStretchRepository stretchStretchStretchRepository;

	ImsIncidentLevelRepository incidentLevelRepository;
	ImsIncidentGroupRepository incidentGroupRepository;
	StretchRepository stretchRepository;
	RoadRepository roadRepository;
	LocationRepository locationRepository;

	@Getter
	Map<Long, Semaphore> incidentReportMutex;
	@Getter
	Set<Long> incidentReportPendingToConfirm;

	// incident_type_id, stretch_id,
	Map<Pair<Long, Long>, Semaphore> incidentTypeStretchsMutex;

	// incidentReportId, incidentTypeTaskId
	Set<Pair<Long, Long>> unCompletedDelayedTasks;

	private static IncidentEntitiesManager instance;

	private List<EntityRepository> repositories;

	@Data
	public static class IncidentReporByManagementArea {

		private List<Long> incidentReportsWithOutArea;
		private Map<Long, List<Long>> incidentReportsWithArea;
	}

	public static IncidentEntitiesManager getInstance() {

		if (instance == null) {

			instance = new IncidentEntitiesManager();
		}

		return instance;
	}

	private IncidentEntitiesManager() {

		repositories = new ArrayList<>();

		this.planRepository = new PlanRepository(true);
		this.incidentParamValueRepository = new ImsIncidentReportParamValueRepository(false);
		this.incidentStateRepository = new ImsIncidentStateRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.incidentTypeTaskRepository = new ImsIncidentTypeTaskRepository(true, CACHE_RETENTION_TIME_CONF);
		this.incidentTypeTaskValueRepository = new ImsIncidentTypeTaskValueRepository(true, CACHE_RETENTION_TIME_CONF);
		this.imsTaskTypeParamRepository = new ImsTaskTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.incidentTaskPlanRepository = new ImsIncidentTaskPlanRepository(true, CACHE_RETENTION_TIME_CONF);
		this.incidentTypeStateTransitionReposirory = new ImsIncidentTypeStateTransitionRepository(true,
				CACHE_RETENTION_TIME_CONF);
		this.incidentTypeStretchRepository = new ImsIncidentTypeStretchRepository(true, CACHE_RETENTION_TIME_CONF,
				repositories);
		this.incidentTypeRepository = new ImsIncidentTypeRepository(true, CACHE_RETENTION_TIME_CONF, repositories);

		this.incidentReportRepository = new ImsIncidentReportRepository(true, CACHE_RETENTION_TIME_MASTER,
				repositories);
		this.incidentReportTaskStateRepository = new ImsIncidentReportTaskStateRepository(true,
				CACHE_RETENTION_TIME_MASTER);
		this.incidentReportTaskValueRepository = new ImsIncidentReportTaskValueRepository(true,
				CACHE_RETENTION_TIME_MASTER);
		this.incidentReportTaskElementRepository = new ImsIncidentReportTaskElementRepository(true,
				CACHE_RETENTION_TIME_MASTER);
		this.incidentReportTaskPlanStateRepository = new ImsIncidentReportTaskPlanStateRepository(true,
				CACHE_RETENTION_TIME_MASTER);
		this.incidentReportAlarmRepository = new ImsIncidentReportAlarmRepository(false);

		this.incidentReportLogRepository = new ImsIncidentReportLogRepository(false, repositories);

		this.incidentReportLogValueRepository = new ImsIncidentReportLogValueRepository(false);

		this.spathStretchRepository = new SpathStretchRepository(true, CACHE_RETENTION_TIME_CONF);

		this.incidentAlarmConditionRepository = new ImsIncidentAlarmConditionRepository(true,
				CACHE_RETENTION_TIME_CONF);

		this.incidentLevelRepository = new ImsIncidentLevelRepository(true, CACHE_RETENTION_TIME_CONF);

		this.stretchStretchStretchRepository = new StretchStretchStretchRepository(true, CACHE_RETENTION_TIME_CONF);

		this.incidentGroupRepository = new ImsIncidentGroupRepository(true, CACHE_RETENTION_TIME_CONF);
		this.stretchRepository = new StretchRepository(true, CACHE_RETENTION_TIME_CONF);
		this.roadRepository = new RoadRepository(true, CACHE_RETENTION_TIME_CONF);

		this.locationRepository = new LocationRepository(true, CACHE_RETENTION_TIME_CONF);

		repositories.add(this.incidentReportTaskStateRepository);
		repositories.add(this.incidentReportTaskElementRepository);
		repositories.add(this.incidentReportTaskValueRepository);
		repositories.add(this.incidentReportTaskPlanStateRepository);
		repositories.add(this.incidentReportAlarmRepository);
		repositories.add(this.incidentReportLogRepository);
		repositories.add(this.incidentReportLogValueRepository);
		repositories.add(this.incidentParamValueRepository);
		repositories.add(this.incidentAlarmConditionRepository);
		repositories.add(this.incidentGroupRepository);
		repositories.add(this.stretchRepository);
		repositories.add(this.locationRepository);

		incidentReportMutex = Collections.synchronizedMap(new HashedMap<>());
		incidentReportPendingToConfirm = Collections.synchronizedSet(new HashSet<>());
		unCompletedDelayedTasks = Collections.synchronizedSet(new HashSet<>());
		incidentTypeStretchsMutex = Collections.synchronizedMap(new HashedMap<>());

		EntitiesManagerHelper.cacheRepository(this);

		// Init incidentReportMutex
		List<ImsIncidentReport> incidentReports = incidentReportRepository.getAll(false);

		if (!CollectionUtils.isEmpty(incidentReports)) {

			for (ImsIncidentReport incidentReport : incidentReports) {

				if (incidentReport != null && incidentReport.getId() != null) {

					incidentReport.setLastUpdateState(Instant.now().toEpochMilli());

					List<ImsIncidentTypeTask> incidentTypeTasks = getUncompletedDelayedTasks(incidentReport.getId());

					if (!CollectionUtils.isEmpty(incidentTypeTasks)) {

						addUncompletedDelayedTasks(incidentReport.getId(), incidentTypeTasks);
					}

					incidentReportMutex.put(incidentReport.getId(), new Semaphore(1));

					if (!Long.valueOf(ImsIncidentState.IMS_STATE_CANCEL).equals(incidentReport.getIncidentStateId())
							&& (incidentReport.getConfirm() == null || !incidentReport.getConfirm().booleanValue())) {

						incidentReportPendingToConfirm.add(incidentReport.getId());
					}
				}
			}
		}

		// Init incidentTypeStretchsMutex
		List<ImsIncidentTypeStretch> incidentTypeStretchs = incidentTypeStretchRepository.getAll(false);

		if (!CollectionUtils.isEmpty(incidentTypeStretchs)) {

			for (ImsIncidentTypeStretch incidentTypeStretch : incidentTypeStretchs) {

				incidentTypeStretchsMutex.put(
						new MutablePair<>(incidentTypeStretch.getIncidentTypeId(), incidentTypeStretch.getStretchId()),
						new Semaphore(1));
			}
		}

	}

	private Long getManagementAreaBystretch(final Long stretchId) {

		Long managementAreaId = null;

		if (stretchId != null) {

			Stretch stretch = stretchRepository.get(stretchId, false);

			if (stretch != null && stretch.getRoadId() != null) {

				Road road = roadRepository.get(stretch.getRoadId(), false);

				managementAreaId = road != null ? road.getManagementAreaId() : null;
			}
		}
		return managementAreaId;
	}

	private IncidentReporByManagementArea groupIncidentsByArea(final List<ImsIncidentReport> incidentReports) {

		IncidentReporByManagementArea incidentReporByManagementArea = new IncidentReporByManagementArea();
		incidentReporByManagementArea.setIncidentReportsWithOutArea(new ArrayList<>());
		incidentReporByManagementArea.setIncidentReportsWithArea(new HashedMap<>());

		if (CollectionUtils.isNotEmpty(incidentReports)) {

			incidentReports.forEach(item -> {

				ImsIncidentReport incidentReport = item.getAffectionStretchId() != null ? item
						: incidentReportRepository.get(item.getId(), false);

				if (incidentReport != null) {

					if (incidentReport.getManagementAreaId() != null) {

						incidentReporByManagementArea.getIncidentReportsWithArea()
								.computeIfAbsent(incidentReport.getManagementAreaId(), k -> new ArrayList<>());

						incidentReporByManagementArea.getIncidentReportsWithArea()
								.get(incidentReport.getManagementAreaId()).add(item.getId());
					} else {

						incidentReporByManagementArea.getIncidentReportsWithOutArea().add(item.getId());
					}
				}
			});
		}

		return incidentReporByManagementArea;
	}

	public List<CommandElementError> executeIncidentTypeTask(final Long incidentReportId, final Long incidentTypeTaskId,
			final List<ImsIncidentTypeTaskValue> incidentTypeTaskValues) {

		List<CommandElementError> commandElementErrors = new ArrayList<>();

		if (incidentTypeTaskId != null && incidentReportId != null) {

			ImsIncidentTypeTask incidentTypeTask = incidentTypeTaskRepository.get(incidentTypeTaskId, false);

			if (incidentTypeTask != null) {

				if (incidentTypeTask.getOnChange() != null) {

					ImsIncidentReport incidentReport;
					incidentReport = incidentReportRepository.get(incidentReportId, false);

					if (incidentReport != null) {

						String groovyFile = GROOVY_PATH + incidentTypeTask.getOnChange() + ".groovy";

						GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);

						if (groovyObj != null) {

							List<?> cmds = null;

							Method[] methods = groovyObj.getClass().getMethods();

							if (Arrays.asList(methods).stream()
									.filter(method -> method.getName().equals(METHOD_PROCESS)).findFirst()
									.orElse(null) != null) {

								/*
								 * if (groovyFile.equals(GROOVY_PATH +
								 * "TaskTypeTunnelLighting/SetIlluminationTask.groovy")) {
								 * 
								 * cmds = (new SetIlluminationTask(log)).process(incidentReport,
								 * incidentTypeTask,
								 * 
								 * incidentTypeTaskValues); } else if (groovyFile.equals(GROOVY_PATH +
								 * "SendMailTask.groovy")) {
								 * 
								 * cmds = (new SendMailTask(log)).process(incidentReport, incidentTypeTask,
								 * incidentTypeTaskValues);
								 * 
								 * } else if (groovyFile.equals(GROOVY_PATH + "SendSignallingToSpath.groovy")) {
								 * 
								 * cmds = (new SendSignallingToSpath(log)).process(incidentReport,
								 * incidentTypeTask, incidentTypeTaskValues); } else if
								 * (groovyFile.equals(GROOVY_PATH + "SwitchOffPAGroup.groovy")) {
								 * 
								 * 
								 * cmds = (new SwitchOffPAGroup(log)).process(incidentReport, incidentTypeTask,
								 * incidentTypeTaskValues);
								 * 
								 * } else if (groovyFile.equals(GROOVY_PATH + "SendMessagePAGroup.groovy")) {
								 * 
								 * cmds = (new SendMessagePAGroup(log)).process(incidentReport,
								 * incidentTypeTask, incidentTypeTaskValues); }
								 */

								Instant instantInit = Instant.now();

								cmds = (List<?>) groovyObj.invokeMethod(METHOD_PROCESS,
										new Object[] { incidentReport, incidentTypeTask, incidentTypeTaskValues });

								log.info("Execute " + METHOD_PROCESS + " in "
										+ Duration.between(instantInit, Instant.now()).toMillis() + " ms, return "
										+ (cmds != null ? cmds.size() : 0) + " comands, groovyFile " + groovyFile
										+ ", task " + incidentTypeTask.getId());

							}

							if (CollectionUtils.isNotEmpty(cmds)) {

								Instant instantInit = Instant.now();

								SetAddCommandsCommand setAddCommandsCommand = new SetAddCommandsCommand();
								setAddCommandsCommand.setResponseCommand(new ResponseToIncidentManagerCommand());
								setAddCommandsCommand.setSendWithAnyError(true);

								String source = (new Key(incidentReport)).getSimpleValue();

								cmds.forEach(cmd -> {

									if (cmd instanceof SignallingCommand) {

										SignallingCommand signallingCommand = (SignallingCommand) cmd;

										PmQueuePriority pmQueuePriority = null;

										ImsIncidentType incidentType = this.incidentTypeRepository
												.get(incidentTypeTask.getIncidentTypeId(), false);

										if (incidentType != null) {

											ImsIncidentLevel incidentLevel = incidentLevelRepository
													.get(incidentType.getIncidentLevelId(), false);

											if (incidentLevel != null && incidentLevel.getPmQueueId() != null
													&& incidentLevel.getPmQueuePriorityId() != null) {

												pmQueuePriority = new PmQueuePriority();
												pmQueuePriority.setQueueId(incidentLevel.getPmQueueId());
												pmQueuePriority.setId(incidentLevel.getPmQueuePriorityId());
											}
										}

										if (pmQueuePriority == null) {

											pmQueuePriority = EntitiesManager.getInstance()
													.getIncidentPriority(signallingCommand.getElementTypeId());
										}

										PmCommandElement pmCommandElement = PmCommandElementHelper
												.get(signallingCommand, pmQueuePriority, source);

										if (pmCommandElement != null) {

											CommandElementType commandElementType = EntitiesManager.getInstance()
													.getCommandElementType(signallingCommand.getSignallingCommandId(),
															signallingCommand.getElementTypeId());

											Element element = EntitiesManager.getInstance().getElement(
													signallingCommand.getElementTypeId(),
													signallingCommand.getElementId());

											if (element != null && commandElementType != null) {

												try {
													AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS,
															LogSubtype.LOG_SUBTYPE_EXECUTE_COMMAND, User.USER_SYSTEM,
															LogLevel.INFO, commandElementType.getLabelAlias(),
															element.getAlias(),
															new ObjectMapper().writeValueAsString(
																	signallingCommand.getSignallingParams()),
															(new Key(incidentReport)).getSimpleValue());

												} catch (JsonProcessingException e) {

													log.error(e.getMessage());
													log.debug(ExceptionUtils.getStackTrace(e));
												}
											}

											setAddCommandsCommand.addCommand(pmCommandElement);
										}

									} else if (cmd instanceof SetDeleteCommandsCommand) {

										SetDeleteCommandsCommand setDeleteCommandsCommand = (SetDeleteCommandsCommand) cmd;
										setDeleteCommandsCommand
												.setResponseCommand(new ResponseToIncidentManagerCommand());
										setDeleteCommandsCommand.setSource(source);

										ResponseCommand respounse = ResponseManager.getInstance()
												.sendCommandSync(setDeleteCommandsCommand);

										if (respounse == null) {

											CommandElementError commandElementError = new CommandElementError();
											commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
											commandElementErrors.add(commandElementError);

										} else if (CollectionUtils.isNotEmpty(respounse.getBasicElements())) {

											for (BasicElement be : respounse.getBasicElements()) {

												if (be instanceof CommandElementError) {

													commandElementErrors.add((CommandElementError) be);
												}
											}
										}

									} else {

										log.info("SendCommand " + cmd);
										CacheManager.getInstance().sendCommand((Command) cmd,
												Command.DEFAULT_EXPIRE_TIME);
									}
								});

								if (CollectionUtils.isNotEmpty(setAddCommandsCommand.getCommands())) {

									ResponseCommand respounse = ResponseManager.getInstance()
											.sendCommandSync(setAddCommandsCommand);

									if (respounse == null) {

										CommandElementError commandElementError = new CommandElementError();
										commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
										commandElementErrors.add(commandElementError);

									} else if (CollectionUtils.isNotEmpty(respounse.getBasicElements())) {

										for (BasicElement be : respounse.getBasicElements()) {

											if (be instanceof CommandElementError) {

												commandElementErrors.add((CommandElementError) be);
											}
										}
									}
								}

								log.info("Send  " + cmds.size() + " commands, in "
										+ Duration.between(instantInit, Instant.now()).toMillis() + " ms, errors "
										+ commandElementErrors.size() + ", task " + incidentTypeTask.getId());
							}

						} else {

							log.error(" Not found object with " + groovyFile);
							CommandElementError commandElementError = new CommandElementError();
							commandElementError.setError(CommandElementError.GROOVY_NOT_FOUND);
							commandElementErrors.add(commandElementError);
						}

					} else {

						log.error(" Not found incidentReport" + incidentReportId);
						CommandElementError commandElementError = new CommandElementError();
						commandElementError.setError(CommandElementError.INCIDENT_REPORT_NOT_FOUND);
						commandElementErrors.add(commandElementError);
					}
				}

			} else {

				log.error(" Not found incidentTypeTask" + incidentTypeTaskId);
				CommandElementError commandElementError = new CommandElementError();
				commandElementError.setError(CommandElementError.INCIDENT_TYPE_TASK_NOT_FOUND);
				commandElementErrors.add(commandElementError);
			}
		}

		return commandElementErrors;

	}

	public void addLogError(Long incidentReportId, Long incidentTypeTaskId,
			List<CommandElementError> commandElementErrors) {

		if (CollectionUtils.isNotEmpty(commandElementErrors)) {

			ImsIncidentTypeTask incidentTypeTask = incidentTypeTaskRepository.get(incidentTypeTaskId, false);

			if (incidentTypeTask != null) {

				List<ImsIncidentReportLog> incidentReportLogs = new ArrayList<>();

				commandElementErrors.forEach(commandElementError -> {

					StringBuilder stringBuilder = new StringBuilder();

					stringBuilder.append(incidentTypeTask.getAlias());
					stringBuilder.append(", ");

					if (commandElementError.getElementId() != null && commandElementError.getElementTypeId() != null) {

						Element element = EntitiesManager.getInstance()
								.getElement(commandElementError.getElementTypeId(), commandElementError.getElementId());

						if (element != null && element.getAlias() != null) {

							stringBuilder.append(element.getAlias());
							stringBuilder.append(", ");
						}
					}

					stringBuilder.append(commandElementError.getToStr());

					addLogIncidentReport(incidentReportLogs, incidentReportId,
							LogSubtype.LOG_SUBTYPE_FINALIZED_TASK_ERROR, LogLevel.INFO, stringBuilder.toString());

				});

				incidentReportLogRepository.create(incidentReportLogs);
			}

		}
	}

	public ImsIncidentReport saveIncidentReport(final Long incidentReportId) {

		Connection connection = null;
		Statement stmt = null;

		ImsIncidentReport incidentReport = null;

		if (incidentReportId != null) {

			String saveIncidentReportQuery = String.format(INSERT_SELECT_QUERY, INCIDENT_REPORT_TARGET_TABLE,
					INCIDENT_REPORT_TARGET_FIELDS, INCIDENT_REPORT_SOURCE_FIELDS, INCIDENT_REPORT_SOURCE_TABLE,
					INCIDENT_REPORT_FIELD, incidentReportId);

			String saveIncidentReportTaskQuery = String.format(INSERT_SELECT_QUERY, INCIDENT_REPORT_TASK_TARGET_TABLE,
					INCIDENT_REPORT_TASK_TARGET_FIELDS, INCIDENT_REPORT_TASK_SOURCE_FIELDS,
					INCIDENT_REPORT_TASK_SOURCE_TABLE, INCIDENT_REPORT_FIELD, incidentReportId);

			String saveIncidentReportTaskValueQuery = String.format(INSERT_SELECT_QUERY,
					INCIDENT_REPORT_TASK_VALUE_TARGET_TABLE, INCIDENT_REPORT_TASK_VALUE_TARGET_FIELDS,
					INCIDENT_REPORT_TASK_VALUE_SOURCE_FIELDS, INCIDENT_REPORT_TASK_VALUE_SOURCE_TABLE,
					INCIDENT_REPORT_FIELD, incidentReportId);

			String saveIncidentReportTaskElementQuery = String.format(INSERT_SELECT_QUERY,
					INCIDENT_REPORT_TASK_ELEMENT_TARGET_TABLE, INCIDENT_REPORT_TASK_ELEMENT_TARGET_FIELDS,
					INCIDENT_REPORT_TASK_ELEMENT_SOURCE_FIELDS, INCIDENT_REPORT_TASK_ELEMENT_SOURCE_TABLE,
					INCIDENT_REPORT_FIELD, incidentReportId);

			String saveIncidentReportTaskPlanQuery = String.format(INSERT_SELECT_QUERY,
					INCIDENT_REPORT_TASK_PLAN_TARGET_TABLE, INCIDENT_REPORT_TASK_PLAN_TARGET_FIELDS,
					INCIDENT_REPORT_TASK_PLAN_SOURCE_FIELDS, INCIDENT_REPORT_TASK_PLAN_SOURCE_TABLE,
					INCIDENT_REPORT_FIELD, incidentReportId);

			String saveIncidentReportLogQuery = String.format(INSERT_SELECT_QUERY, INCIDENT_REPORT_LOG_TARGET_TABLE,
					INCIDENT_REPORT_LOG_TARGET_FIELDS, INCIDENT_REPORT_LOG_SOURCE_FIELDS,
					INCIDENT_REPORT_LOG_SOURCE_TABLE, INCIDENT_REPORT_FIELD, incidentReportId);

			String saveIncidentReportLogValuesQuery = String.format(INCIDENT_REPORT_LOG_VALUES_QUERY,
					INCIDENT_REPORT_LOG_VALUES_TARGET_TABLE, INCIDENT_REPORT_LOG_VALUES_TARGET_FIELDS,
					INCIDENT_REPORT_LOG_VALUES_SOURCE_FIELDS, INCIDENT_REPORT_LOG_VALUES_SOURCE_TABLE,
					INCIDENT_REPORT_LOG_FIELD, INCIDENT_REPORT_LOG_FIELD, INCIDENT_REPORT_LOG_SOURCE_TABLE,
					INCIDENT_REPORT_FIELD, incidentReportId);

			String saveIncidentReportAlarmQuery = String.format(INSERT_SELECT_QUERY,
					INCIDENT_REPORT_ALARMS_TARGET_TABLE, INCIDENT_REPORT_ALARMS_TARGET_FIELDS,
					INCIDENT_REPORT_ALARMS_SOURCE_FIELDS, INCIDENT_REPORT_ALARMS_SOURCE_TABLE, INCIDENT_REPORT_FIELD,
					incidentReportId);

			String saveIncidentReportParamValuesQuery = String.format(INSERT_SELECT_QUERY,
					INCIDENT_REPORT_PARAM_VALUES_TARGET_TABLE, INCIDENT_REPORT_PARAM_VALUES_TARGET_FIELDS,
					INCIDENT_REPORT_PARAM_VALUES_SOURCE_FIELDS, INCIDENT_REPORT_PARAM_VALUES_SOURCE_TABLE,
					INCIDENT_REPORT_FIELD, incidentReportId);

			try {
				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);

				stmt = connection.createStatement();

				stmt.executeUpdate(saveIncidentReportQuery);
				stmt.executeUpdate(saveIncidentReportTaskQuery);
				stmt.executeUpdate(saveIncidentReportTaskValueQuery);
				stmt.executeUpdate(saveIncidentReportTaskElementQuery);
				stmt.executeUpdate(saveIncidentReportTaskPlanQuery);
				stmt.executeUpdate(saveIncidentReportLogQuery);
				stmt.executeUpdate(saveIncidentReportLogValuesQuery);
				stmt.executeUpdate(saveIncidentReportAlarmQuery);
				stmt.executeUpdate(saveIncidentReportParamValuesQuery);

				connection.commit();

				incidentReport = new ImsIncidentReport();
				incidentReport.setId(incidentReportId);

				log.info("IncidentReport " + incidentReportId + " saved");

			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));

			} finally {

				try {

					if (stmt != null) {
						stmt.close();
					}

					if (connection != null) {
						connection.close();
					}

				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return incidentReport;
	}

	/**
	 * 
	 * @param incidentTypeId
	 * @param locationId
	 * @param affectionStretchId
	 * @param incidentReportAlarms
	 * @return
	 */
	public List<ImsIncidentReport> createIncidentReport(final Long incidentTypeId, final Long locationId,
			final List<Long> affectionStretchsId, final List<ImsIncidentReportAlarm> incidentReportAlarms,
			final GenericExecutor genericExecutor, final Long userId, final Long roadImpactId, boolean multiInstance) {

		List<ImsIncidentReport> incidentReports = null;

		if (incidentTypeId != null && (locationId != null || !CollectionUtils.isEmpty(affectionStretchsId))) {

			Instant instantInit = Instant.now();

			ImsIncidentType incidentType = incidentTypeRepository.get(incidentTypeId, false);

			if (incidentType != null && incidentType.getId() != null) {

				List<Long> stretchs = null;

				// Basic use case
				if (CollectionUtils.isEmpty(affectionStretchsId)) {

					stretchs = new ArrayList<>();

					List<StretchLocation> stretchLocations = EntitiesManager.getInstance()
							.getStretchLocationByLocation(locationId);

					for (StretchLocation stretchLocation : stretchLocations) {

						if (stretchLocation.getStretchId() != null) {

							stretchs.add(stretchLocation.getStretchId());
						}
					}
				} else {

					stretchs = new ArrayList<>(affectionStretchsId);
				}

				if (!CollectionUtils.isEmpty(stretchs)) {

					for (Long stretchId : stretchs) {

						ImsIncidentTypeStretch incidentTypeStretch = incidentTypeStretchRepository.get(incidentTypeId,
								stretchId, false);

						Semaphore mutex = acquire(incidentTypeStretch, genericExecutor);

						if (mutex == null) {

							log.error("Mutex not found: incidentTypeId = " + incidentTypeId + ", stretchId = "
									+ stretchId);
						}
						// Check multi istance option
						else if (multiInstance
								|| (incidentTypeStretch != null && (incidentTypeStretch.getMultiInstance() == null
										|| incidentTypeStretch.getMultiInstance().booleanValue()
										|| incidentReportRepository
												.getByIncidentTypeStretch(incidentTypeId, stretchId, false).stream()
												.filter(incidentReport -> !isFinalState(incidentReport.getId()))
												.count() == 0))) {

							// Check StretchStrechs incident reports.

							boolean areIncidentsStretchStretch = false;

							if (incidentTypeStretch.getCheckStretchStretch() != null
									&& incidentTypeStretch.getCheckStretchStretch().booleanValue()) {

								areIncidentsStretchStretch = areIncidentReportsStretchStretch(stretchId, incidentTypeId,
										null);
							}

							if (!areIncidentsStretchStretch) {

								ImsIncidentReport incidentReport = new ImsIncidentReport();

								incidentReport.setAffectionStretchId(stretchId);
								incidentReport.setLocationId(
										locationId != null ? locationId : incidentTypeStretch.getDefaultLocationId());

								incidentReport.setIncidentTypeId(incidentType.getId());
								incidentReport.setIncidentStateId(incidentType.getIniIncidentStateId());
								incidentReport.setIncidentLevelId(incidentType.getIncidentLevelId());

								if (userId != null) {

									incidentReport.setCurrentUserId(userId);
								} else {

									incidentReport.setCurrentUserId(incidentType.getDefaultUserId());
								}

								incidentReport.setValidForMin(incidentType.getValidForMin());
								incidentReport.setConfirm(false);
								incidentReport.setGeneratedAt(Instant.now().toEpochMilli());

								incidentReport.setLastUpdate(Instant.now().toEpochMilli());
								incidentReport.setLastUpdateState(Instant.now().toEpochMilli());

								incidentReport.setIncidentReportAlarms(incidentReportAlarms);
								incidentReport.setRoadImpactId(roadImpactId);
								incidentReport.setManagementAreaId(getManagementAreaBystretch(stretchId));

								ImsIncidentReport incident = incidentReportRepository.create(incidentReport);

								if (incident != null && incident.getId() != null) {

									incidentReportMutex.put(incident.getId(), new Semaphore(1));

									if (incidentReports == null) {

										incidentReports = new ArrayList<>();
									}
									incidentReports.add(incident);

									incidentReportPendingToConfirm.add(incident.getId());
								}
							}

							mutex.release();

						} else {
							mutex.release();
							log.info("No more than one incident of the type = " + incidentTypeId
									+ " is allowed in the section = " + stretchId);
						}
					}

				} else {
					log.info("Not found any stretch for location");
				}

			} else {
				log.error("IncidentType not found: " + incidentTypeId);
			}

			log.info("Created " + (incidentReports != null ? incidentReports.size() : 0) + " incident reports in "
					+ Duration.between(instantInit, Instant.now()).toMillis() + " ms");
		}

		return incidentReports;

	}

	private Semaphore acquire(final ImsIncidentTypeStretch incidentTypeStretch, final GenericExecutor genericExecutor) {

		boolean acquire = false;
		Semaphore mutex = null;

		if (incidentTypeStretch != null) {

			mutex = incidentTypeStretchsMutex.get(
					new MutablePair<>(incidentTypeStretch.getIncidentTypeId(), incidentTypeStretch.getStretchId()));

			try {
				if (mutex != null) {

					while (!genericExecutor.isStop() && !(acquire = mutex.tryAcquire(500, TimeUnit.MILLISECONDS)))
						;
				}

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return acquire ? mutex : null;
	}

	public void initIncidentTasks(Long incidentReportId) {

		ImsIncidentReport toUpdateIncidentReport = null;

		if (incidentReportId != null) {

			Instant instantInit = Instant.now();

			ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null) {

				List<ImsIncidentTypeTask> incidentTypeTasks = null;

				if (!isIncidentReportCanceled(incidentReportId) && !isIncidentReportConfirmed(incidentReportId)) {

					incidentTypeTasks = getInitIncidentTypeTasks(incidentReport.getIncidentTypeId(),
							incidentReport.getRoadImpactId(), incidentReport.getAffectionStretchId());

				} else {

					incidentTypeTasks = getIncidentTypeTasks(incidentReportId, incidentReport.getIncidentStateId(),
							incidentReport.getIncidentTypeId(), incidentReport.getRoadImpactId(),
							incidentReport.getAffectionStretchId());
				}

				if (!CollectionUtils.isEmpty(incidentTypeTasks)) {

					ImsIncidentTypeStretch incidentTypeStretch = incidentTypeStretchRepository
							.get(incidentReport.getIncidentTypeId(), incidentReport.getAffectionStretchId(), false);

					if (incidentTypeStretch != null && !CollectionUtils.isEmpty(incidentTypeTasks)) {

						List<ImsIncidentReportTaskState> incidentReportTaskStates = new ArrayList<>();
						List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates = new ArrayList<>();
						List<ImsIncidentReportTaskElement> incidentReportTaskElements = new ArrayList<>();

						try {

							toUpdateIncidentReport = (ImsIncidentReport) incidentReport.clone();

							for (ImsIncidentTypeTask incidentTypeTask : incidentTypeTasks) {

								if (incidentTypeTask.getId() != null) {

									ImsIncidentReportTaskState incidentReportTaskState = new ImsIncidentReportTaskState();
									incidentReportTaskState.setIncidentTypeTaskId(incidentTypeTask.getId());

									incidentReportTaskStates.add(incidentReportTaskState);

									// Init ImsIncidentReportTaskElement
									List<ImsIncidentReportTaskElement> taskElements = initIncidentTaskElements(
											incidentTypeTask, incidentTypeStretch);

									if (CollectionUtils.isNotEmpty(taskElements)) {

										incidentReportTaskElements.addAll(taskElements);
									}

									// Init ImsIncidentReportTaskPlanState
									List<ImsIncidentTaskPlan> incidentTaskPlans = incidentTaskPlanRepository.gets(
											incidentReport.getAffectionStretchId(), incidentReport.getIncidentTypeId(),
											incidentTypeTask.getId(), false);

									// Add TaskPlanState for Spath
									if (incidentTypeStretch.getSpathId() != null) {

										List<SpathStretch> spathStretchs = spathStretchRepository.gets(
												incidentTypeStretch.getSpathId(),
												incidentReport.getAffectionStretchId(), false);

										if (CollectionUtils.isNotEmpty(spathStretchs)) {

											for (SpathStretch spathStretch : spathStretchs) {

												if (spathStretch.getRelStretchId() != null && !spathStretch
														.getRelStretchId().equals(spathStretch.getStretchId())) {

													List<ImsIncidentTaskPlan> incidentTaskPlansStretch = incidentTaskPlanRepository
															.gets(spathStretch.getRelStretchId(),
																	incidentReport.getIncidentTypeId(),
																	incidentTypeTask.getId(), false);

													if (CollectionUtils.isNotEmpty(incidentTaskPlansStretch)) {

														if (incidentTaskPlans == null) {

															incidentTaskPlans = new ArrayList<>();
														}

														incidentTaskPlans.addAll(incidentTaskPlansStretch);
													}

												}
											}
										}
									}

									if (!CollectionUtils.isEmpty(incidentTaskPlans)) {

										for (ImsIncidentTaskPlan incidentTaskPlan : incidentTaskPlans) {

											if (incidentTaskPlan.getId() != null) {

												ImsIncidentReportTaskPlanState incidentReportTaskPlanState = new ImsIncidentReportTaskPlanState();
												incidentReportTaskPlanState
														.setIncidentTaskPlanId(incidentTaskPlan.getId());
												incidentReportTaskPlanStates.add(incidentReportTaskPlanState);
											}
										}
									}
								}
							}

							toUpdateIncidentReport.setIncidentReportTaskStates(incidentReportTaskStates);
							toUpdateIncidentReport.setIncidentReportTaskElements(
									incidentReportTaskElements.stream().distinct().collect(Collectors.toList()));
							toUpdateIncidentReport.setIncidentReportTaskPlanStates(incidentReportTaskPlanStates);

							incidentReportRepository.addIncidentReportTaskState(toUpdateIncidentReport);

							List<ImsIncidentTypeTask> tasks = getUncompletedDelayedTasks(incidentReportId);

							if (!CollectionUtils.isEmpty(tasks)) {

								addUncompletedDelayedTasks(incidentReportId, tasks);
							}

							log.info("IncidentReport: " + incidentReportId + "  init "
									+ (incidentTypeTasks != null ? incidentTypeTasks.size() : 0) + " task  in "
									+ Duration.between(instantInit, Instant.now()).toMillis() + " ms");

						} catch (CloneNotSupportedException e) {

							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}
					}

				}
			}
		}
	}

	private List<ImsIncidentReportTaskElement> initIncidentTaskElements(ImsIncidentTypeTask incidentTypeTask,
			ImsIncidentTypeStretch incidentTypeStretch) {

		List<ImsIncidentReportTaskElement> incidentReportTaskElements = new ArrayList<>();

		if (incidentTypeTask != null && incidentTypeTask.getOnChange() != null) {

			String groovyFile = GROOVY_PATH + incidentTypeTask.getOnChange() + ".groovy";

			GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);

			if (groovyObj != null) {

				Method[] methods = groovyObj.getClass().getMethods();

				if (Arrays.asList(methods).stream().filter(method -> method.getName().equals(METHOD_GET_ELEMENTS))
						.findFirst().orElse(null) != null) {

					List<?> elements = null;

					Instant instantInit = Instant.now();
					/*
					 * if (groovyFile.equals(GROOVY_PATH + "SendSignallingToSpath.groovy")) {
					 * 
					 * elements = (new SendSignallingToSpath(log)).getElements(incidentTypeTask,
					 * incidentTypeStretch);
					 * 
					 * } else {
					 */
					elements = (List<?>) groovyObj.invokeMethod(METHOD_GET_ELEMENTS,
							new Object[] { incidentTypeTask, incidentTypeStretch });
					// }

					if (CollectionUtils.isNotEmpty(elements)) {

						elements.forEach(be -> {

							if (be instanceof Element) {

								Element element = (Element) be;

								ImsIncidentReportTaskElement incidentReportTaskElement = new ImsIncidentReportTaskElement();

								incidentReportTaskElement.setIncidentTypeTaskId(incidentTypeTask.getId());
								incidentReportTaskElement.setElementId(element.getId());
								incidentReportTaskElement.setElementTypeId(element.getElementTypeId());

								incidentReportTaskElements.add(incidentReportTaskElement);
							}

						});
					}

					log.info("Execute " + METHOD_GET_ELEMENTS + " in "
							+ Duration.between(instantInit, Instant.now()).toMillis() + " ms, return "
							+ (elements != null ? elements.size() : 0) + " elements, groovyFile " + groovyFile
							+ ", task " + incidentTypeTask.getId());
				}
			}

		}

		return incidentReportTaskElements;
	}

	public ImsIncidentReport changeIncidentReport(Long incidentReportId, Long locationId, Long affectionStretchId,
			Long incindetTypeId, Long roadImpactId) {

		ImsIncidentReport incidentReport = null;

		if (incidentReportId != null && locationId != null && affectionStretchId != null && incindetTypeId != null) {

			incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null) {

				RoadImpact roadImpact = new RoadImpact();
				roadImpact.setId(roadImpactId);

				if (affectionStretchId.equals(incidentReport.getAffectionStretchId())
						&& incindetTypeId.equals(incidentReport.getIncidentTypeId())) {

					incidentReport = new ImsIncidentReport();
					incidentReport.setId(incidentReportId);
					incidentReport.setLocationId(locationId);
					incidentReport.setLastUpdate(Instant.now().toEpochMilli());
					incidentReport.setRoadImpact(roadImpact);

					incidentReport = incidentReportRepository.update(incidentReport);

				} else {

					ImsIncidentType incidentType = incidentTypeRepository.get(incindetTypeId, false);

					if (incidentType != null && incidentType.getId() != null) {

						ImsIncidentTypeStretch incidentTypeStretch = incidentTypeStretchRepository
								.get(incidentType.getId(), affectionStretchId, false);

						if (incidentTypeStretch == null) {

							log.info("Incident  type = " + incidentType.getId() + " is not allowed in the section = "
									+ affectionStretchId);

						} else if ((incidentTypeStretch.getMultiInstance() == null
								|| incidentTypeStretch.getMultiInstance().booleanValue()
								|| incidentReportRepository
										.getByIncidentTypeStretch(incidentType.getId(), affectionStretchId, false)
										.stream().filter(i -> !isFinalState(i.getId())).count() == 0)) {

							boolean areIncidentsStretchStretch = false;

							if (incidentTypeStretch.getCheckStretchStretch() != null
									&& incidentTypeStretch.getCheckStretchStretch().booleanValue()) {

								areIncidentsStretchStretch = areIncidentReportsStretchStretch(affectionStretchId,
										incindetTypeId, incidentReportId);
							}

							if (!areIncidentsStretchStretch) {

								Long currentUserId = incidentReport.getCurrentUserId();
								incidentReport = new ImsIncidentReport();
								incidentReport.setId(incidentReportId);
								incidentReport.setIncidentTypeId(incindetTypeId);
								incidentReport.setIncidentStateId(incidentType.getIniIncidentStateId());
								incidentReport.setCurrentUserId(currentUserId);
								incidentReport.setValidForMin(incidentType.getValidForMin());

								if (incidentType.getIncidentLevelId() != null) {

									incidentReport.setIncidentLevelId(incidentType.getIncidentLevelId());
								}

								incidentReport.setLocationId(locationId);
								incidentReport.setValidForMin(incidentType.getValidForMin());

								incidentReport.setAffectionStretchId(affectionStretchId);

								incidentReport.setLastUpdate(Instant.now().toEpochMilli());
								incidentReport.setRoadImpact(roadImpact);

								incidentReport = incidentReportRepository.update(incidentReport);
							}

						} else {
							log.info("No more than one incident of the type = " + incidentType.getId()
									+ " is allowed in the section = " + affectionStretchId);
						}
					}
				}
			}
		}
		return incidentReport;

	}

	public ImsIncidentReport updateCurrentUser(final Long incidentReportId, final Long currentUserId) {

		ImsIncidentReport incidentReport = new ImsIncidentReport();
		incidentReport.setId(incidentReportId);
		incidentReport.setCurrentUserId(currentUserId);
		incidentReport.setLastUpdate(Instant.now().toEpochMilli());

		return incidentReportRepository.update(incidentReport);
	}

	/**
	 * 
	 * @param incidentReportId
	 * @param currentCauseId
	 * @return
	 */
	public ImsIncidentReport updateCurrentCause(final Long incidentReportId, final Long currentCauseId) {

		ImsIncidentReport incidentReport = new ImsIncidentReport();
		incidentReport.setId(incidentReportId);
		incidentReport.setIncidentTypeCauseId(currentCauseId);
		incidentReport.setLastUpdate(Instant.now().toEpochMilli());

		return incidentReportRepository.update(incidentReport);
	}

	/**
	 * 
	 * @param incidentReportId
	 * @param currentCauseId
	 * @return
	 */
	public ImsIncidentReport updateRoadImpact(final Long incidentReportId, final Long roadImpactId) {

		ImsIncidentReport result = null;
		RoadImpact roadImpact = new RoadImpact();
		roadImpact.setId(roadImpactId);

		Long incidentStateId = getFirstStateWithPendingTask(incidentReportId, roadImpactId);

		ImsIncidentReport incidentReport = new ImsIncidentReport();
		incidentReport.setId(incidentReportId);
		incidentReport.setRoadImpact(roadImpact);
		incidentReport.setLastUpdate(Instant.now().toEpochMilli());

		if (incidentStateId != null) {

			incidentReport.setBackward(false);
			incidentReport.setIncidentStateId(incidentStateId);
			incidentReport.setLastUpdateState(Instant.now().toEpochMilli());
		}

		result = incidentReportRepository.update(incidentReport);

		if (incidentStateId != null) {

			log.info("Incident report: " + incidentReportId + ", new state: " + incidentStateId);
			addLogIncidentReport(null, incidentReportId, LogSubtype.LOG_SUBTYPE_INCIDENT_REPORT_FORWARDED,
					LogLevel.INFO);
		}

		return result;
	}

	public ImsIncidentReport confirmIncidentReport(final Long incidentReportId, Long userId) {

		ImsIncidentReport incidentReport = new ImsIncidentReport();
		incidentReport.setId(incidentReportId);
		incidentReport.setConfirm(true);
		incidentReport.setLastUpdate(Instant.now().toEpochMilli());
		incidentReport.setLastUpdateState(Instant.now().toEpochMilli());
		incidentReport.setCurrentUserId(userId);

		incidentReport = incidentReportRepository.update(incidentReport);

		if (incidentReport != null) {

			incidentReportPendingToConfirm.remove(incidentReportId);
			processOnConfirmIncidentReport(incidentReport);

		}

		return incidentReport;
	}

	private boolean processOnConfirmIncidentReport(ImsIncidentReport imsIncidentReport) {

		if (imsIncidentReport != null && imsIncidentReport.getIncidentTypeId() != null) {

			ImsIncidentType incidentType = this.incidentTypeRepository.get(imsIncidentReport.getIncidentTypeId(),
					false);

			if (incidentType != null && incidentType.getOnCreateExecute() != null) {

				try {

					return (boolean) GroovyManager.getInstance().executeGroovy(incidentType.getOnCreateExecute(),
							GROOVY_INCIDENT_TYPE_PATH, JAVA_GROOVY_PACKAGE, METHOD_PROCESS, imsIncidentReport);

				} catch (Exception e) {

					log.error("Groovy not executed");
					log.debug(e.getMessage());
				}

			}
		}

		return false;
	}

	public boolean isIncidentReportConfirmed(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		return incidentReport != null && incidentReport.getConfirm();
	}

	public boolean isIncidentReportOpen(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		return incidentReport != null && incidentReport.getConfirm()
				&& !Long.valueOf(ImsIncidentState.IMS_STATE_CANCEL).equals(incidentReport.getIncidentStateId());
	}

	public boolean isIncidentReportCanceled(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		return incidentReport != null
				&& Long.valueOf(ImsIncidentState.IMS_STATE_CANCEL).equals(incidentReport.getIncidentStateId());
	}

	/**
	 * 
	 * @param incidentReportId
	 * @param incidentTypeId
	 * @return
	 */
	public boolean checkIncidentReportType(final Long incidentReportId, final Long incidentTypeId) {

		boolean bReturn = false;

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		if (incidentReport != null && incidentReport.getIncidentTypeId() != null
				&& incidentReport.getIncidentTypeId().equals(incidentTypeId)) {

			bReturn = true;
		}

		return bReturn;
	}

	public ImsIncidentReport deleteIncidentReport(final Long incidentReportId) {

		ImsIncidentReport incidentReport = null;

		if (incidentReportId != null) {

			incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null) {

				if (incidentReportPendingToConfirm.contains(incidentReportId)) {

					incidentReportPendingToConfirm.remove(incidentReportId);
				}

				if (incidentReportMutex.containsKey(incidentReportId)) {

					incidentReportMutex.remove(incidentReportId);
				}

				incidentReport = incidentReportRepository.delete(incidentReport);
			}
		}

		return incidentReport;
	}

	public boolean isAutoForwardIncidentReport(Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		return incidentReport != null && !Boolean.TRUE.equals(incidentReport.getBackward())
				&& isIncidentReportConfirmed(incidentReportId)
				&& isAutoForwardIncidentType(incidentReport.getIncidentTypeId(), incidentReport.getIncidentStateId());
	}

	private boolean isAutoForwardIncidentType(Long incidentTypeId, Long iniIncidentStateId) {

		ImsIncidentTypeStateTransition incidentTypeStateTransition = incidentTypeStateTransitionReposirory
				.get(incidentTypeId, iniIncidentStateId, false);

		return incidentTypeStateTransition != null && incidentTypeStateTransition.getAuto() != null
				&& incidentTypeStateTransition.getAuto().booleanValue();
	}

	public ImsIncidentReport forwardIncidentReport(final Long incidentReportId) {

		if (incidentReportId != null) {

			ImsIncidentReport auxIncidentReport = incidentReportRepository.get(incidentReportId, false);

			if (auxIncidentReport != null && ObjectUtils.allNotNull(auxIncidentReport.getIncidentStateId(),
					auxIncidentReport.getIncidentTypeId())) {

				ImsIncidentTypeStateTransition incidentTypeStateTransition = incidentTypeStateTransitionReposirory
						.get(auxIncidentReport.getIncidentTypeId(), auxIncidentReport.getIncidentStateId(), false);

				if (incidentTypeStateTransition != null
						&& incidentTypeStateTransition.getEndIncidentStateId() != null) {

					ImsIncidentReport incidentReport = new ImsIncidentReport();
					incidentReport.setId(incidentReportId);
					incidentReport.setBackward(false);
					incidentReport.setIncidentStateId(incidentTypeStateTransition.getEndIncidentStateId());
					incidentReport.setLastUpdate(Instant.now().toEpochMilli());
					incidentReport.setLastUpdateState(Instant.now().toEpochMilli());

					return (incidentReportRepository.update(incidentReport));

				}
			}
		}
		return null;
	}

	public ImsIncidentReport backwardIncidentReport(final Long incidentReportId) {

		if (incidentReportId != null) {

			ImsIncidentReport auxIncidentReport = incidentReportRepository.get(incidentReportId, false);

			if (auxIncidentReport != null && ObjectUtils.allNotNull(auxIncidentReport.getIncidentStateId(),
					auxIncidentReport.getIncidentTypeId())) {

				List<ImsIncidentTypeStateTransition> incidentTypeStateTransitions = incidentTypeStateTransitionReposirory
						.getsByIncidentType(auxIncidentReport.getIncidentTypeId(), false);

				ImsIncidentTypeStateTransition incidentTypeStateTransition = null;

				if (CollectionUtils.isNotEmpty(incidentTypeStateTransitions)) {

					incidentTypeStateTransition = incidentTypeStateTransitions.stream()
							.filter(be -> auxIncidentReport.getIncidentStateId().equals(be.getEndIncidentStateId()))
							.findFirst().orElse(null);
				}

				if (incidentTypeStateTransition != null
						&& incidentTypeStateTransition.getIniIncidentStateId() != null) {

					ImsIncidentReport incidentReport = new ImsIncidentReport();
					incidentReport.setId(incidentReportId);
					incidentReport.setBackward(true);
					incidentReport.setIncidentStateId(incidentTypeStateTransition.getIniIncidentStateId());
					incidentReport.setLastUpdate(Instant.now().toEpochMilli());
					incidentReport.setLastUpdateState(Instant.now().toEpochMilli());

					return (incidentReportRepository.update(incidentReport));

				}
			}
		}
		return null;
	}

	public ImsIncidentReport cancelIncidentReport(final Long incidentReportId, Long userId) {

		if (incidentReportId != null) {

			ImsIncidentReport auxIncidentReport = incidentReportRepository.get(incidentReportId, false);

			if (auxIncidentReport != null && auxIncidentReport.getIncidentTypeId() != null) {

				ImsIncidentReport incidentReport = new ImsIncidentReport();
				incidentReport.setId(incidentReportId);
				incidentReport.setIncidentStateId(ImsIncidentState.IMS_STATE_CANCEL);
				incidentReport.setLastUpdate(Instant.now().toEpochMilli());
				incidentReport.setLastUpdateState(Instant.now().toEpochMilli());
				incidentReport.setCurrentUserId(userId);
				incidentReport = incidentReportRepository.update(incidentReport);

				if (auxIncidentReport.getConfirm() == null || !auxIncidentReport.getConfirm().booleanValue()) {

					incidentReportPendingToConfirm.remove(incidentReportId);
				}

				return incidentReport;
			}
		}
		return null;
	}

	public ImsIncidentReport finalizeTaskIncidentReport(final Long incidentReportId, final Long incidentTypeTaskId,
			final List<ImsIncidentTypeTaskValue> incidentTypeTaskValues, boolean error) {

		ImsIncidentReport incidentReport = null;

		if (ObjectUtils.allNotNull(incidentReportId, incidentTypeTaskId)) {

			ImsIncidentReportTaskState incidentReportTaskState = new ImsIncidentReportTaskState();
			incidentReportTaskState.setIncidentReportId(incidentReportId);
			incidentReportTaskState.setIncidentTypeTaskId(incidentTypeTaskId);
			incidentReportTaskState.setCompletedAt(Instant.now().toEpochMilli());
			incidentReportTaskState.setError(error);

			if (incidentReportTaskStateRepository.update(incidentReportTaskState) != null) {

				incidentReport = new ImsIncidentReport();
				incidentReport.setId(incidentReportId);
				incidentReport.setLastUpdate(Instant.now().toEpochMilli());

				incidentReportRepository.update(incidentReport);

				unCompletedDelayedTasks.remove(new MutablePair<>(incidentReportId, incidentTypeTaskId));
			}

			if (!CollectionUtils.isEmpty(incidentTypeTaskValues)) {

				for (ImsIncidentTypeTaskValue incidentTypeTaskValue : incidentTypeTaskValues) {

					ImsIncidentReportTaskValue incidentReportTaskValue = incidentReportTaskValueRepository.get(
							incidentReportId, incidentTypeTaskValue.getTaskTypeId(),
							incidentTypeTaskValue.getTaskTypeParamId(), incidentTypeTaskValue.getIncidentTypeTaskId(),
							false);

					if (incidentReportTaskValue != null) {

						incidentReportTaskValue.setIncidentReportId(incidentReportId);
						incidentReportTaskValue.setTaskTypeId(incidentTypeTaskValue.getTaskTypeId());
						incidentReportTaskValue.setTaskTypeParamId(incidentTypeTaskValue.getTaskTypeParamId());
						incidentReportTaskValue.setIncidentTypeTaskId(incidentTypeTaskValue.getIncidentTypeTaskId());
						incidentReportTaskValue.setValue(incidentTypeTaskValue.getValue());

						incidentReportTaskValueRepository.update(incidentReportTaskValue);

					} else {

						incidentReportTaskValue = new ImsIncidentReportTaskValue();

						incidentReportTaskValue.setIncidentReportId(incidentReportId);
						incidentReportTaskValue.setTaskTypeId(incidentTypeTaskValue.getTaskTypeId());
						incidentReportTaskValue.setTaskTypeParamId(incidentTypeTaskValue.getTaskTypeParamId());
						incidentReportTaskValue.setIncidentTypeTaskId(incidentTypeTaskValue.getIncidentTypeTaskId());
						incidentReportTaskValue.setValue(incidentTypeTaskValue.getValue());

						incidentReportTaskValueRepository.create(incidentReportTaskValue);
					}

				}
			}
		}
		return incidentReport;
	}

	public ImsIncidentReport executeTaskPlanIncidentReport(final Long incidentReportId, final Long incidentTaskPlanId,
			final Boolean addLog) {

		ImsIncidentReport incidentReport = null;

		if (ObjectUtils.allNotNull(incidentReportId, incidentTaskPlanId)) {

			ImsIncidentTaskPlan incidentTaskPlan = incidentTaskPlanRepository.get(incidentTaskPlanId, false);

			if (incidentTaskPlan != null && incidentTaskPlan.getPlanId() != null) {

				Plan plan = planRepository.get(incidentTaskPlan.getPlanId(), false);

				if (plan != null) {

					ImsIncidentReportTaskPlanState incidentReportTaskPlanState = new ImsIncidentReportTaskPlanState();
					incidentReportTaskPlanState.setIncidentReportId(incidentReportId);
					incidentReportTaskPlanState.setIncidentTaskPlanId(incidentTaskPlanId);
					incidentReportTaskPlanState.setCompletedAt(Instant.now().toEpochMilli());

					if (incidentReportTaskPlanStateRepository.update(incidentReportTaskPlanState) != null) {

						incidentReport = new ImsIncidentReport();
						incidentReport.setId(incidentReportId);
						incidentReport.setLastUpdate(Instant.now().toEpochMilli());

						incidentReportRepository.update(incidentReport);

						SetPlanActionsCommand command = new SetPlanActionsCommand();
						command.addPlanAction(incidentTaskPlan.getPlanId(), Action.PLAN_PLAY);
						command.setSource((new Key(incidentReport)).getSimpleValue());

						if (CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME) != null) {

							AuditHelper.Audit(LogType.LOG_TYPE_PLANS, LogSubtype.LOG_SUBTYPE_EXECUTE_PLAN,
									User.USER_SYSTEM, LogLevel.DEBUG, plan.getAlias(), "",
									(new Key(incidentReport)).getSimpleValue());

							if (Boolean.TRUE.equals(addLog)) {

								addLogIncidentReport(null, incidentReportId, LogSubtype.LOG_SUBTYPE_EXECUTED_TASK_PLAN,
										LogLevel.INFO, plan.getAlias());
							}
						}
					}

				} else {
					log.error("Plan not " + incidentTaskPlan.getPlanId() + " found");
				}
			}
		}

		return incidentReport;
	}

	public void publishIncidentReports(final Integer action, final List<ImsIncidentReport> incidentReports) {

		if (!CollectionUtils.isEmpty(incidentReports) && action != null) {

			IncidentReporByManagementArea incidentReporByManagementArea = groupIncidentsByArea(incidentReports);

			if (!CollectionUtils.isEmpty(incidentReporByManagementArea.getIncidentReportsWithOutArea())) {

				SetIncidentReportsPublishCommand command = new SetIncidentReportsPublishCommand();
				command.setAction(action);

				incidentReporByManagementArea.getIncidentReportsWithOutArea()
						.forEach(item -> command.addIncident(item));

				CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
			}

			if (!MapUtils.isEmpty(incidentReporByManagementArea.getIncidentReportsWithArea())) {

				incidentReporByManagementArea.getIncidentReportsWithArea()
						.forEach((managementAreaId, indidentReportIds) -> {

							SetIncidentReportsPublishCommand command = new SetIncidentReportsPublishCommand();
							command.setAction(action);
							command.addManagementArea(managementAreaId);

							indidentReportIds.forEach(indidentReportId -> command.addIncident(indidentReportId));

							CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
						});
			}

		}

	}

	public ImsIncidentTypeTask getIncidentTypeTask(final Long incidentTypeTaskId) {

		return incidentTypeTaskRepository.get(incidentTypeTaskId, false);
	}

	public List<ImsIncidentTypeTask> getUncompletedMandatoryTasks(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		return incidentReport != null
				? getUncompletedMandatoryTasks(incidentReport.getId(), incidentReport.getIncidentTypeId(),
						incidentReport.getIncidentStateId(), incidentReport.getRoadImpactId(),
						incidentReport.getAffectionStretchId())
				: null;
	}

	public List<ImsIncidentTypeTask> getUncompletedMandatoryIncidentTasks(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		return incidentReport != null
				? getUncompletedMandatoryTasks(incidentReport.getId(), incidentReport.getIncidentTypeId(), null,
						incidentReport.getRoadImpactId(), incidentReport.getAffectionStretchId())
				: null;
	}

	private List<ImsIncidentTypeTask> getUncompletedMandatoryTasks(final Long incidentReportId,
			final Long incidentTypeId, final Long incidentStateId, final Long roadImpactId, final Long stretchId) {

		List<ImsIncidentTypeTask> incidentTypeTasks = null;

		if (ObjectUtils.allNotNull(incidentReportId, incidentTypeId)) {

			incidentTypeTasks = incidentTypeTaskRepository.gets(incidentTypeId, incidentStateId, stretchId, false);

			incidentTypeTasks = incidentTypeTasks.stream()
					.filter(be -> be.getMandatory() != null && be.getMandatory().booleanValue()
							&& (incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false) == null
									|| incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false)
											.getCompletedAt() == null)
							&& (be.getRoadImpactId() == null || be.getRoadImpactId().equals(roadImpactId)))
					.collect(Collectors.toList());
		}
		return incidentTypeTasks;
	}

	public List<ImsIncidentTypeTask> getUncompletedAutoTasks(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		Long incidentStateId = isIncidentReportConfirmed(incidentReportId) ? incidentReport.getIncidentStateId() : null;

		return incidentReport != null
				? getUncompletedAutoTasks(incidentReport.getId(), incidentReport.getIncidentTypeId(), incidentStateId,
						incidentReport.getRoadImpactId(), incidentReport.getAffectionStretchId())
				: null;
	}

	public List<ImsIncidentTypeTask> getCancelTasks(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		return incidentReport != null
				? getUncompletedAutoTasks(incidentReport.getId(), incidentReport.getIncidentTypeId(),
						ImsIncidentState.IMS_STATE_CANCEL, incidentReport.getRoadImpactId(),
						incidentReport.getAffectionStretchId())
				: null;
	}

	private List<ImsIncidentTypeTask> getUncompletedAutoTasks(final Long incidentReportId, final Long incidentTypeId,
			final Long incidentStateId, final Long roadImpactId, final Long stretchId) {

		List<ImsIncidentTypeTask> incidentTypeTasks = null;

		if (ObjectUtils.allNotNull(incidentReportId, incidentTypeId, stretchId)) {

			incidentTypeTasks = incidentTypeTaskRepository.getByIncidentType(incidentTypeId, false);

			List<ImsIncidentTypeTask> incidentTypeTasksStretchts = null;

			incidentTypeTasksStretchts = incidentTypeTasks.stream()
					.filter(be -> (be.getIncidentStateId() == null || be.getIncidentStateId().equals(incidentStateId))
							&& be.getAuto() != null && be.getAuto().booleanValue()
							&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false) != null
							&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false)
									.getCompletedAt() == null
							&& (be.getRoadImpactId() == null || be.getRoadImpactId().equals(roadImpactId))
							&& stretchId.equals(be.getStretchId()))
					.collect(Collectors.toList());

			if (CollectionUtils.isEmpty(incidentTypeTasksStretchts)) {

				return incidentTypeTasks.stream().filter(
						be -> (be.getIncidentStateId() == null || be.getIncidentStateId().equals(incidentStateId))
								&& be.getAuto() != null && be.getAuto().booleanValue()
								&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false) != null
								&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false)
										.getCompletedAt() == null
								&& (be.getRoadImpactId() == null || be.getRoadImpactId().equals(roadImpactId))
								&& be.getStretchId() == null)
						.collect(Collectors.toList());
			} else {

				return incidentTypeTasksStretchts;
			}
		}

		return incidentTypeTasks;
	}

	private List<ImsIncidentTypeTask> getIncidentTypeTasks(final Long incidentReportId, final Long incidentStateId,
			final Long incidentTypeId, final Long roadImpactId, final Long stretchId) {

		List<ImsIncidentTypeTask> incidentTypeTasks = null;

		if (ObjectUtils.allNotNull(incidentReportId, incidentTypeId, stretchId)) {

			incidentTypeTasks = incidentTypeTaskRepository.getByIncidentType(incidentTypeId, false);

			List<ImsIncidentTypeTask> incidentTypeTasksStretchts = null;

			incidentTypeTasksStretchts = incidentTypeTasks.stream()
					.filter(be -> (be.getIncidentStateId() == null || be.getIncidentStateId().equals(incidentStateId))
							&& (be.getRoadImpactId() == null || be.getRoadImpactId().equals(roadImpactId))
							&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false) == null
							&& stretchId.equals(be.getStretchId()))
					.collect(Collectors.toList());

			if (CollectionUtils.isEmpty(incidentTypeTasksStretchts)) {

				return incidentTypeTasks.stream().filter(
						be -> (be.getIncidentStateId() == null || be.getIncidentStateId().equals(incidentStateId))
								&& (be.getRoadImpactId() == null || be.getRoadImpactId().equals(roadImpactId))
								&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false) == null
								&& be.getStretchId() == null)
						.collect(Collectors.toList());
			} else {

				return incidentTypeTasksStretchts;
			}
		}
		return incidentTypeTasks;
	}

	private List<ImsIncidentTypeTask> getInitIncidentTypeTasks(final Long incidentTypeId, final Long roadImpactId,
			final Long stretchId) {

		List<ImsIncidentTypeTask> incidentTypeTasks = null;

		if (ObjectUtils.allNotNull(incidentTypeId)) {

			incidentTypeTasks = incidentTypeTaskRepository.gets(incidentTypeId, null, stretchId, false);

			incidentTypeTasks = incidentTypeTasks.stream()
					.filter(be -> be.getAuto() != null && be.getAuto().booleanValue()
							&& (be.getRoadImpactId() == null || be.getRoadImpactId().equals(roadImpactId)))
					.collect(Collectors.toList());
		}
		return incidentTypeTasks;
	}

	private void addUncompletedDelayedTasks(final Long incidentReportId, List<ImsIncidentTypeTask> incidentTypeTasks) {

		if (incidentReportId != null && CollectionUtils.isNotEmpty(incidentTypeTasks)
				&& unCompletedDelayedTasks != null) {

			for (ImsIncidentTypeTask incidentTypeTask : incidentTypeTasks) {

				if (incidentTypeTask.getId() != null) {

					unCompletedDelayedTasks.add(new MutablePair<>(incidentReportId, incidentTypeTask.getId()));
					log.info("add uncompleted delayed task: " + incidentTypeTask.getId() + " in incident report: "
							+ incidentReportId);
				}
			}
		}
	}

	private List<ImsIncidentTypeTask> getUncompletedDelayedTasks(final Long incidentReportId) {

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		Long incidentStateId = isIncidentReportConfirmed(incidentReportId) ? incidentReport.getIncidentStateId() : null;

		return incidentReport != null
				? getUncompletedDelayedTasks(incidentReport.getId(), incidentReport.getIncidentTypeId(),
						incidentStateId, incidentReport.getRoadImpactId(), incidentReport.getAffectionStretchId())
				: null;
	}

	private List<ImsIncidentTypeTask> getUncompletedDelayedTasks(final Long incidentReportId, final Long incidentTypeId,
			final Long incidentStateId, final Long roadImpactId, final Long stretchId) {

		List<ImsIncidentTypeTask> incidentTypeTasks = null;

		if (ObjectUtils.allNotNull(incidentReportId, incidentTypeId)) {

			incidentTypeTasks = incidentTypeTaskRepository.gets(incidentTypeId, incidentStateId, stretchId, false);

			incidentTypeTasks = incidentTypeTasks.stream()
					.filter(be -> (be.getAuto() == null || !be.getAuto().booleanValue()) && be.getDelay() != null
							&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false) != null
							&& incidentReportTaskStateRepository.get(incidentReportId, be.getId(), false)
									.getCompletedAt() == null
							&& (be.getRoadImpactId() == null || be.getRoadImpactId().equals(roadImpactId)))
					.collect(Collectors.toList());
		}
		return incidentTypeTasks;
	}

	public List<ImsIncidentTaskPlan> getUncompletedTaskPlans(final Long incidentReportId,
			final Long incidentTypeTaskId) {

		List<ImsIncidentTaskPlan> incidentTaskPlans = null;

		ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

		if (incidentReport != null) {

			incidentTaskPlans = getUncompletedTaskPlans(incidentReport.getId(), incidentReport.getIncidentTypeId(),
					incidentReport.getAffectionStretchId(), incidentTypeTaskId);

			// Add TaskPlanState for Spath
			ImsIncidentTypeStretch incidentTypeStretch = incidentTypeStretchRepository
					.get(incidentReport.getIncidentTypeId(), incidentReport.getAffectionStretchId(), false);

			if (incidentTypeStretch != null && incidentTypeStretch.getSpathId() != null) {

				List<SpathStretch> spathStretchs = spathStretchRepository.gets(incidentTypeStretch.getSpathId(),
						incidentTypeStretch.getStretchId(), false);

				if (CollectionUtils.isNotEmpty(spathStretchs)) {

					for (SpathStretch spathStretch : spathStretchs) {

						if (spathStretch.getRelStretchId() != null
								&& !spathStretch.getRelStretchId().equals(spathStretch.getStretchId())) {

							List<ImsIncidentTaskPlan> incidentTaskPlansStretch = getUncompletedTaskPlans(
									incidentReport.getId(), incidentReport.getIncidentTypeId(),
									spathStretch.getRelStretchId(), incidentTypeTaskId);

							if (CollectionUtils.isNotEmpty(incidentTaskPlansStretch)) {

								if (incidentTaskPlans == null) {

									incidentTaskPlans = new ArrayList<>();
								}

								incidentTaskPlans.addAll(incidentTaskPlansStretch);
							}

						}
					}
				}
			}
		}

		return incidentTaskPlans;
	}

	private List<ImsIncidentTaskPlan> getUncompletedTaskPlans(final Long incidentReportId, final Long incidentTypeId,
			final Long stretchId, final Long incidentTypeTaskId) {

		List<ImsIncidentTaskPlan> incidentTaskPlans = null;

		if (ObjectUtils.allNotNull(incidentReportId, incidentReportId, incidentTypeId, stretchId)) {

			incidentTaskPlans = incidentTaskPlanRepository.gets(stretchId, incidentTypeId, incidentTypeTaskId, false);

			incidentTaskPlans = incidentTaskPlans.stream()
					.filter(be -> incidentReportTaskPlanStateRepository.get(incidentReportId, be.getId(), false) != null
							&& incidentReportTaskPlanStateRepository.get(incidentReportId, be.getId(), false)
									.getCompletedAt() == null)
					.collect(Collectors.toList());
		}
		return incidentTaskPlans;
	}

	public Boolean isFinalState(final Long incidentReportId) {

		Boolean finalState = false;

		if (incidentReportId != null) {

			ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null && incidentReport.getIncidentStateId() != null) {

				ImsIncidentState incidentState = incidentStateRepository.get(incidentReport.getIncidentStateId(),
						false);

				if (incidentState != null) {

					finalState = incidentState.getFinalState() != null && incidentState.getFinalState().booleanValue();
				}

			}
		}
		return finalState;
	}

	public Boolean isAffectionStretch(final Long incidentReportId, final Long stretchId) {

		Boolean resp = false;

		if (incidentReportId != null && stretchId != null) {

			ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

			resp = stretchId.equals(incidentReport.getAffectionStretchId());
		}
		return resp;
	}

	public Boolean isIncidentType(final Long incidentReportId, final Long incidentTypeId) {

		Boolean resp = false;

		if (incidentReportId != null && incidentTypeId != null) {

			ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

			resp = incidentTypeId.equals(incidentReport.getIncidentTypeId());
		}
		return resp;
	}

	public boolean isNextStateFinal(final Long incidentReportId) {

		boolean finalState = false;

		if (incidentReportId != null) {

			ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null && incidentReport.getIncidentStateId() != null) {

				ImsIncidentTypeStateTransition incidentTypeStateTransition = incidentTypeStateTransitionReposirory
						.get(incidentReport.getIncidentTypeId(), incidentReport.getIncidentStateId(), false);

				if (incidentTypeStateTransition != null) {

					ImsIncidentState incidentState = incidentStateRepository
							.get(incidentTypeStateTransition.getEndIncidentStateId(), false);

					if (incidentState != null) {

						finalState = incidentState.getFinalState() != null
								&& incidentState.getFinalState().booleanValue();
					}
				}

			}
		}
		return finalState;
	}

	public ImsIncidentState getCurrentState(final Long incidentReportId) {

		ImsIncidentState incidentState = null;

		if (incidentReportId != null) {

			ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null && incidentReport.getIncidentStateId() != null) {

				incidentState = incidentStateRepository.get(incidentReport.getIncidentStateId(), false);
			}
		}
		return incidentState;
	}

	public List<Pair<Long, Boolean>> getIncidentPendingToConfirm(Long now, Long systemParamAutoConfirm) {

		List<Pair<Long, Boolean>> incidentReportIds = new ArrayList<>();

		synchronized (incidentReportPendingToConfirm) {

			incidentReportPendingToConfirm.forEach(incidentReportId -> {

				ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

				if (incidentReport != null && incidentReport.getGeneratedAt() != null) {

					ImsIncidentType incidentType = incidentTypeRepository.get(incidentReport.getIncidentTypeId(),
							false);

					if (incidentType != null) {

						Long timeToAutoConfirm = null;

						if (incidentType.getAutoConfirmIncident() != null) {

							timeToAutoConfirm = incidentType.getAutoConfirmIncident().longValue();

						} else if (systemParamAutoConfirm != null) {

							timeToAutoConfirm = systemParamAutoConfirm;
						}

						// necessary to divide by 1000 to go from milliseconds to seconds
						if (timeToAutoConfirm != null
								&& (now - (incidentReport.getGeneratedAt().longValue() / 1000)) > timeToAutoConfirm) {

							if (incidentType.getCancelIncident() != null
									&& incidentType.getCancelIncident().booleanValue()) {

								incidentReportIds.add(new MutablePair<>(incidentReport.getId(), true));
								log.info("Add incident report " + incidentReport.getId()
										+ " to auto cancel, time to auto confirm " + timeToAutoConfirm);

							} else {

								incidentReportIds.add(new MutablePair<>(incidentReport.getId(), false));
								log.info("Add incident report " + incidentReport.getId()
										+ " to auto confirm, time to auto confirm " + timeToAutoConfirm);
							}

						}
					}
				}
			});
		}

		return incidentReportIds;
	}
	
	public List<ImsIncidentReport> getIncidentPendingToConfirm()
	{
		List<ImsIncidentReport> incidentReports = new ArrayList<>();
		

		synchronized (incidentReportPendingToConfirm) {

			incidentReportPendingToConfirm.forEach(incidentReportId -> {

				ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);
				
				if(incidentReport != null){
					
					incidentReports.add(incidentReport);
				}
			});
		}
		
		return incidentReports;
	}

	public List<Long> getExpiredIncidents(Long now) {

		List<Long> incidentReportIds = new ArrayList<>();

		synchronized (incidentReportMutex) {

			incidentReportMutex.forEach((incidentReportId, sem) -> {

				if (!isFinalState(incidentReportId).booleanValue()) {

					ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

					if (incidentReport != null && incidentReport.getGeneratedAt() != null) {

						ImsIncidentType incidentType = incidentTypeRepository.get(incidentReport.getIncidentTypeId(),
								false);

						if (incidentType != null) {

							Long validForMin = incidentType.getValidForMin() != null
									? incidentType.getValidForMin().longValue()
									: null;

							// necessary to multiplie by 600 to go from to minutes
							// necessary to divide by 1000 to go from milliseconds to seconds

							if (validForMin != null
									&& (now - incidentReport.getGeneratedAt().longValue() / 1000) > validForMin * 60) {

								incidentReportIds.add(incidentReport.getId());

								log.info("Incident report " + incidentReportId + " expired, time valid " + validForMin
										+ " min");
							}
						}
					}
				}

			});
		}

		return incidentReportIds;
	}

	public Long getNumIncidentsPendingToConfirm() {

		return incidentReportPendingToConfirm != null ? incidentReportPendingToConfirm.size() : 0L;
	}

	public List<Long> getIncidentPendingToDelete() {

		List<Long> incidentReportIds = new ArrayList<>();

		synchronized (incidentReportMutex) {

			incidentReportMutex.forEach((incidentReportId, sem) -> {

				if (isFinalState(incidentReportId).booleanValue())

					incidentReportIds.add(incidentReportId);
			});
		}

		return incidentReportIds;
	}

	public List<Pair<Long, Long>> getTasksPendingToFinalize(Long epochMilli) {

		List<Pair<Long, Long>> tasksPendingToFinalize = new ArrayList<>();
		List<Pair<Long, Long>> tasksToRemove = new ArrayList<>();

		synchronized (unCompletedDelayedTasks) {

			unCompletedDelayedTasks.forEach(unCompletedDelayedTask -> {

				Long incidentReportId = unCompletedDelayedTask.getKey();
				Long incidentTypeTaskId = unCompletedDelayedTask.getValue();

				ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);
				ImsIncidentTypeTask incidentTypeTask = incidentTypeTaskRepository.get(incidentTypeTaskId, false);

				if (incidentReport == null || incidentTypeTask == null || incidentTypeTask.getDelay() == null
						|| (incidentTypeTask.getIncidentStateId() != null
								&& !incidentTypeTask.getIncidentStateId().equals(incidentReport.getIncidentStateId()))
						|| (incidentTypeTask.getRoadImpactId() != null
								&& !incidentTypeTask.getRoadImpactId().equals(incidentReport.getRoadImpactId()))) {

					tasksToRemove.add(new MutablePair<>(incidentReportId, incidentTypeTaskId));
				}

				if (incidentReport != null && incidentReport.getLastUpdateState() != null
						&& incidentReport.getLastUpdateState() + (incidentTypeTask.getDelay() * 1000) < epochMilli) {

					tasksPendingToFinalize.add(new MutablePair<>(incidentReportId, incidentTypeTaskId));
				}

			});

			unCompletedDelayedTasks.removeAll(tasksToRemove);
		}

		return tasksPendingToFinalize;
	}

	public ImsIncidentReportLog addLogIncidentReport(List<ImsIncidentReportLog> imsIncidentReportLogs,
			final Long incidentReportId, final Long logSubtypeId, final Long level, final String... args) {

		ImsIncidentReportLog incidentReportLog = null;

		if (ObjectUtils.allNotNull(incidentReportId, level)) {

			incidentReportLog = new ImsIncidentReportLog();
			incidentReportLog.setIncidentReportId(incidentReportId);
			incidentReportLog.setLogSubtypeId(logSubtypeId);
			incidentReportLog.setUserId(User.USER_SYSTEM);
			incidentReportLog.setTime(Instant.now().toEpochMilli());
			incidentReportLog.setLevel(level.intValue());

			ImsIncidentReport incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null && incidentReport.getIncidentStateId() != null) {

				ImsIncidentState incidentState = incidentStateRepository.get(incidentReport.getIncidentStateId(),
						false);

				if (incidentState != null) {

					List<ImsIncidentReportLogValue> incidentReportLogValues = new ArrayList<>();

					ImsIncidentReportLogValue stateValue = new ImsIncidentReportLogValue();
					stateValue.setIncidentReportLogId(incidentReportId);
					stateValue.setLogTypeId(LogType.LOG_TYPE_IMS);
					stateValue.setLogTypeParamId(LogTypeParam.LOG_TYPE_PARAM_INCIDENT_REPORT_STATE);
					stateValue.setValue(incidentState.getLabelAlias());
					incidentReportLogValues.add(stateValue);

					if (ArrayUtils.isNotEmpty(args)) {

						Long logParamId = 2L;
						for (final String arg : args) {

							if (arg != null) {

								ImsIncidentReportLogValue logValue = new ImsIncidentReportLogValue();
								logValue.setIncidentReportLogId(incidentReportId);
								logValue.setLogTypeId(LogType.LOG_TYPE_IMS);
								logValue.setLogTypeParamId(logParamId);
								logValue.setValue(arg);
								incidentReportLogValues.add(logValue);
							}
							logParamId++;
						}
					}

					incidentReportLog.setIncidentReportLogValues(incidentReportLogValues);

				}
			}

		}

		if (incidentReportLog != null) {

			if (imsIncidentReportLogs != null) {

				imsIncidentReportLogs.add(incidentReportLog);

			} else {

				incidentReportLog = incidentReportLogRepository.create(incidentReportLog);
			}
		}

		return incidentReportLog;
	}

	public ImsIncidentReportLog createIncidetReportLog(ImsIncidentReportLog incidentReportLog) {

		return incidentReportLog != null && incidentReportLog.getIncidentReportId() != null
				? incidentReportLogRepository.create(incidentReportLog)
				: null;
	}

	public ImsIncidentReportLog getIncidetReportLog(Long incidentReportLogId) {

		return incidentReportLogId != null ? incidentReportLogRepository.get(incidentReportLogId, false) : null;
	}

	public ImsIncidentReportLog deleteIncidetReportLog(ImsIncidentReportLog incidentReportLog) {

		return incidentReportLog != null ? incidentReportLogRepository.delete(incidentReportLog) : null;
	}

	public List<ImsIncidentTypeTaskValue> getIncidentTypeTaskValues(final Long incidentTypeTaskId,
			final List<ImsIncidentTypeTaskValue> newIncidentTypeTaskValues) {

		List<ImsIncidentTypeTaskValue> newImsIncidentTypeTaskValues = null;

		ImsIncidentTypeTask incidentTypeTask = incidentTypeTaskRepository.get(incidentTypeTaskId, false);

		if (incidentTypeTask != null) {

			List<ImsIncidentTypeTaskValue> currentIncidentTypeTaskValues = incidentTypeTaskValueRepository
					.getsByIncidentTypeTask(incidentTypeTaskId, false);

			List<ImsTaskTypeParam> imsTaskTypeParams = imsTaskTypeParamRepository.gets(incidentTypeTask.getTaskTypeId(),
					false);

			if (!CollectionUtils.isEmpty(imsTaskTypeParams)) {

				for (ImsTaskTypeParam imsTaskTypeParam : imsTaskTypeParams) {

					String value = null;

					ImsIncidentTypeTaskValue newIncidentTypeTaskValue = null;
					ImsIncidentTypeTaskValue currentIncidentTypeTaskValue = null;

					if (Long.valueOf(ParamType.CONFIG).equals(imsTaskTypeParam.getParamTypeId())
							&& !CollectionUtils.isEmpty(newIncidentTypeTaskValues)) {

						newIncidentTypeTaskValue = newIncidentTypeTaskValues.stream()
								.filter(e -> incidentTypeTaskId != null
										&& imsTaskTypeParam.getId().equals(e.getTaskTypeParamId())
										&& imsTaskTypeParam.getTaskTypeId() != null
										&& imsTaskTypeParam.getTaskTypeId().equals(e.getTaskTypeId()))
								.findFirst().orElse(null);
					}

					if (newIncidentTypeTaskValue != null) {

						value = newIncidentTypeTaskValue.getValue();

					} else {

						if (!CollectionUtils.isEmpty(currentIncidentTypeTaskValues)) {

							currentIncidentTypeTaskValue = currentIncidentTypeTaskValues.stream()
									.filter(e -> incidentTypeTaskId != null
											&& imsTaskTypeParam.getId().equals(e.getTaskTypeParamId())
											&& imsTaskTypeParam.getTaskTypeId() != null
											&& imsTaskTypeParam.getTaskTypeId().equals(e.getTaskTypeId()))
									.findFirst().orElse(null);

							if (currentIncidentTypeTaskValue != null) {

								value = currentIncidentTypeTaskValue.getValue();
							}
						}
					}

					if (value != null) {

						if (newImsIncidentTypeTaskValues == null) {

							newImsIncidentTypeTaskValues = new ArrayList<>();
						}

						ImsIncidentTypeTaskValue newImsIncidentTypeTaskValue = new ImsIncidentTypeTaskValue();

						newImsIncidentTypeTaskValue.setIncidentTypeTaskId(incidentTypeTaskId);
						newImsIncidentTypeTaskValue.setTaskTypeId(imsTaskTypeParam.getTaskTypeId());
						newImsIncidentTypeTaskValue.setTaskTypeParamId(imsTaskTypeParam.getId());
						newImsIncidentTypeTaskValue.setValue(value);

						newImsIncidentTypeTaskValues.add(newImsIncidentTypeTaskValue);
					}
				}
			}
		}

		return newImsIncidentTypeTaskValues;
	}

	public boolean putGenericData(String value) {

		boolean res = false;

		if (value != null) {

			GenericData genericData = new GenericData();
			genericData.setId(GenericData.GENERIC_DATA_INCIDENT_PENDING_TO_CONFIRM);
			genericData.setValue(value);

			res = ((new GenericDataRepository(false).put(genericData)) != null);

			SetGenericData setGenericData = new SetGenericData();
			setGenericData.setGenericDatas(Arrays.asList(genericData));

			res &= ((CacheManager.getInstance().sendCommand(setGenericData, Command.DEFAULT_EXPIRE_TIME)) != null);
		}

		return res;
	}

	public Long getSpathIdByIncidentReport(SignallingCommandToSpath signallingCommandToSpath,
			ImsIncidentReport incidentReport) {

		Long spathId = null;

		if (signallingCommandToSpath != null) {

			if (signallingCommandToSpath.getSpathId() != null) {

				Key spathKey = new Key(signallingCommandToSpath.getSpathId());

				if (spathKey.getClazz() == Spath.class) {

					Spath spath = new Spath();

					spath.setPks(spathKey.getIds());

					spathId = spath.getId();
				}
			} else if (incidentReport != null) {

				ImsIncidentTypeStretch incidentTypeStretch = this.incidentTypeStretchRepository
						.get(incidentReport.getIncidentTypeId(), incidentReport.getAffectionStretchId(), false);

				spathId = incidentTypeStretch != null ? incidentTypeStretch.getSpathId() : null;
			}

		}

		return spathId;
	}

	public List<SpathStretch> getSpathStretchs(final Long spathId, final Long stretchId) {

		List<SpathStretch> spathStretchs = null;

		if (spathId != null && stretchId != null) {

			spathStretchs = this.spathStretchRepository.gets(spathId, stretchId, false);

		}
		return spathStretchs;
	}

	public String getIncidentTypeTaskValue(List<ImsIncidentTypeTaskValue> values, Long taskTypeParamId) {

		String returnVal = null;

		if (!CollectionUtils.isEmpty(values)) {

			for (ImsIncidentTypeTaskValue value : values) {

				if (value.getTaskTypeParamId() != null && value.getTaskTypeParamId().equals(taskTypeParamId)
						&& !StringUtils.isEmpty(value.getValue())) {

					returnVal = value.getValue();

					break;
				}
			}
		}

		return returnVal;
	}

	public String getIncidentTypeTaskValue(Long taskTypeId, Long incidentTypeTaskId, Long taskTypeParamId) {

		ImsIncidentTypeTaskValue incidentTypeTaskValue = null;

		if (incidentTypeTaskId != null && taskTypeParamId != null) {

			incidentTypeTaskValue = incidentTypeTaskValueRepository.get(taskTypeId, taskTypeParamId, incidentTypeTaskId,
					false);
		}

		return incidentTypeTaskValue != null && !StringUtils.isEmpty(incidentTypeTaskValue.getValue())
				? incidentTypeTaskValue.getValue()
				: null;
	}

	public List<ImsIncidentReportAlarm> getIncidentReportAlarms(Long incidentReportId) {

		return incidentReportAlarmRepository.getsByIncidentReport(incidentReportId, false);
	}

	public void response(Object command) {

		if (command instanceof Command && ((Command) command).getResponseCommand() instanceof ResponseCommand) {

			CacheManager.getInstance().sendCommand(((Command) command).getResponseCommand(),
					Command.DEFAULT_EXPIRE_TIME);
		}
	}

	public Long getFirstStateWithPendingTask(Long incidentReportId, Long roadImpactId) {

		Long incidentStateId = null;

		if (incidentReportId != null) {

			ImsIncidentReport incidentReport;
			incidentReport = incidentReportRepository.get(incidentReportId, false);

			if (incidentReport != null && incidentReport.getIncidentTypeId() != null) {

				ImsIncidentType incidentType = incidentTypeRepository.get(incidentReport.getIncidentTypeId(), false);

				if (incidentType != null && incidentType.getId() != null
						&& incidentType.getIniIncidentStateId() != null) {

					Long stateId = incidentType.getIniIncidentStateId();

					if (stateId != null) {

						boolean error = false;

						while (!error && incidentStateId == null
								&& !stateId.equals(incidentReport.getIncidentStateId())) {

							if (CollectionUtils
									.isNotEmpty(getUncompletedMandatoryTasks(incidentReportId, incidentType.getId(),
											stateId, roadImpactId, incidentReport.getAffectionStretchId()))) {

								incidentStateId = stateId;

							} else {

								ImsIncidentTypeStateTransition incidentTypeStateTransition = incidentTypeStateTransitionReposirory
										.get(incidentType.getId(), stateId, false);

								if (incidentTypeStateTransition != null) {

									stateId = incidentTypeStateTransition.getEndIncidentStateId();

								} else {

									error = true;
								}
							}

						}
					}

				}

			}
		}

		return incidentStateId;
	}

	public boolean areIncidentReportsStretchStretch(Long stretchId, Long incidentTypeId, Long incidentReportId) {

		List<StretchStretchStretch> stretchStretchsStretchs = stretchStretchStretchRepository.getAll(false);

		List<StretchStretchStretch> stretchStretchsStretchsFiltered = stretchStretchsStretchs.stream()
				.filter(stretchStretchsStretch -> stretchStretchsStretch.getStretchId().equals(stretchId))
				.collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(stretchStretchsStretchsFiltered)) {

			for (StretchStretchStretch stretchStretchStretch : stretchStretchsStretchsFiltered) {

				// Filtramos todos los tramos de un supertramo.

				List<StretchStretchStretch> stretchsByStrechStrech = stretchStretchsStretchs.stream()
						.filter(stretchStretchsStretch -> stretchStretchsStretch.getStretchStretchId()
								.equals(stretchStretchStretch.getStretchStretchId()))
						.collect(Collectors.toList());

				for (StretchStretchStretch stretch : stretchsByStrechStrech) {

					// Comprobamos si hay o no incidencias en el tramo del supertramo:

					List<ImsIncidentReport> incidentReportsByStretch = incidentReportRepository
							.getByIncidentTypeStretch(incidentTypeId, stretch.getStretchId(), false).stream()
							.filter(incidentReport -> !isFinalState(incidentReport.getId()))
							.collect(Collectors.toList());

					if (CollectionUtils.isNotEmpty(incidentReportsByStretch)) {

						// Hay incidencias en el tramo, y por tanto en el supertramo.

						for (ImsIncidentReport incidentReport : incidentReportsByStretch) {

							if (incidentReportId == null || !incidentReport.getId().equals(incidentReportId)) {

								log.info("No more than one incident in Stretch Stretch: "
										+ stretchStretchStretch.getStretchStretchId() + " are allowed");

								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	public List<ImsIncidentReportAlarm> getIncidentAlarmsByIncidentReport(Long incidentReportId) {

		if (incidentReportAlarmRepository != null) {

			return incidentReportAlarmRepository.getsByIncidentReport(incidentReportId, false);
		}

		return Collections.emptyList();
	}

	public ImsIncidentType getIncidentType(Long indicentTypeId) {

		if (incidentTypeRepository != null) {

			return incidentTypeRepository.get(indicentTypeId, false);
		}

		return null;
	}

	public ImsIncidentGroup getIncidentGroup(Long incidentGroupId) {

		if (incidentGroupRepository != null) {

			return incidentGroupRepository.get(incidentGroupId, false);
		}

		return null;
	}

	public ImsIncidentLevel getIncidentLevel(Long incidentLevelId) {

		if (incidentLevelRepository != null) {

			return incidentLevelRepository.get(incidentLevelId, false);
		}

		return null;
	}

	public Stretch getStretch(Long stretchId) {

		if (stretchRepository != null) {

			return stretchRepository.get(stretchId, false);
		}

		return null;
	}

	public Location getLocation(Long locationId) {

		if (locationRepository != null) {

			return locationRepository.get(locationId, false);
		}

		return null;
	}

	public void blockIncidentReport(final Long incidentReportId) {

		if (incidentReportId != null) {

			ImsIncidentReport incidentReport = new ImsIncidentReport();
			incidentReport.setId(incidentReportId);

			IncidentReporByManagementArea incidentReporByManagementArea = groupIncidentsByArea(
					Arrays.asList(incidentReport));

			if (!CollectionUtils.isEmpty(incidentReporByManagementArea.getIncidentReportsWithOutArea())) {

				SetBlockIncidentReportsPublishCommand command = new SetBlockIncidentReportsPublishCommand();
				command.setAction(Command.ACTION_BLOCK);

				incidentReporByManagementArea.getIncidentReportsWithOutArea()
						.forEach(item -> command.addIncident(item));

				CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
			}

			if (!MapUtils.isEmpty(incidentReporByManagementArea.getIncidentReportsWithArea())) {

				incidentReporByManagementArea.getIncidentReportsWithArea()
						.forEach((managementAreaId, indidentReportIds) -> {

							SetBlockIncidentReportsPublishCommand command = new SetBlockIncidentReportsPublishCommand();
							command.setAction(Command.ACTION_BLOCK);
							command.addManagementArea(managementAreaId);

							indidentReportIds.forEach(indidentReportId -> command.addIncident(indidentReportId));

							CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
						});
			}
		}
	}

	
}
