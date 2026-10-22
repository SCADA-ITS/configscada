package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.*;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class EntitiesManager {

	private static final String FILE_PROPERTIES = "application.properties";

	private static final Long CACHE_RETENTION_TIME_CONF = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_conf"));
	private static final Long CACHE_RETENTION_TIME_MASTER = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_master"));
	private static final Long CACHE_RETENTION_TIME_STATIC = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_PROPERTIES, "entities.manager.retention_time_static"));

	private UserRepository userRepository;
	private UserSessionRepository userSessionRepository;
	private RoleRepository roleRepository;
	private SystemPermissionRepository systemPermissionRepository;

	private CCTVScenarioHierarchyRepository cctvScenarioHierarchyRepository;
	private CCTVScenarioNodeRepository cctvScenarioNodeRepository;
	private CCTVScenarioRepository cctvScenarioRepository;
	private CCTVScenarioElementRepository cctvScenarioElementRepository;

	private SystemParamValueRepository systemParamValueRepository;
	private SystemParamRepository systemParamRepository;

	private UserPermissionRepository userPermissionRepository;
	private RolePermissionRepository rolePermissionRepository;
	private RolesUsersRepository rolesUsersRepository;
	private PlanHierarchyRepository planHierarchyRepository;
	private PlanNodeRepository planNodeRepository;
	private PlanOrderTypeRepository planOrderTypeRepository;
	private PlanOrderTypeParamRepository planOrderTypeParamRepository;
	private CommandElementTypeRepository commandElementTypeRepository;
	private CommandElementTypeParamRepository commandElementTypeParamRepository;
	private PlanOrderRepository planOrderRepository;
	private PlanOrderValueRepository planOrderValueRepository;
	private CommandElementRepository commandElementRepository;
	private CommandElementValueRepository commandElementValueRepository;
	private CommandElementPreconditionRepository commandElementPreconditionRepository;
	private CommandElementValuePreconditionRepository commandElementValuePreconditionRepository;
	private ElementHierarchiesRepository elementHierarchiesRepository;
	private ElementHierarchiesAllowedRepository elementHierarchiesAllowedRepository;
	private ElementValueRepository elementValueRepository;
	private ElementRepository elementRepository;
	private ElementTypeRepository elementTypeRepository;
	private ElementTypeParamRepository elementTypeParamRepository;
	private ElementTypeParamUnitRepository elementTypeParamUnitRepository;
	private ElementStateRepository elementStateRepository;
	private ElementStateAlarmRepository elementStateAlarmRepository;
	private ElementTypeStateRepository elementTypeStateRepository;
	private ElementValueStateRepository elementValueStateRepository;
	private ElementGroupRepository elementGroupRepository;
	private ElementSubtypeRepository elementSubtypeRepository;
	private ElementSubtypeActiveParamRepository elementSubtypeActiveParamRepository;
	private ElementSubtypeActiveCommandRepository elementSubtypeActiveCommandRepository;
	private ElementTreeRepository elementTreeRepository;
	private ElementTreeNodeRepository elementTreeNodeRepository;
	private ElementTreeNodeHierarchyRepository elementTreeNodeHierarchyRepository;
	private PlanOrderCommandElementRepository planOrderCommandElementRepository;
	private PlanOrderElementValueRepository planOrderElementValueRepository;
	private PlanRepository planRepository;
	private PlanStateRepository planStateRepository;
	private PlanStateTypeRepository planStateTypeRepository;
	private PlanOrderStateRepository planOrderStateRepository;
	private PlanOrderStateTypeRepository planOrderStateTypeRepository;
	private PlanOrderElementTypeStateRepository planOrderElementTypeStateRepository;
	private PlanOrderConditionRepository planOrderConditionRepository;
	private GenericDataRepository genericDataRepository;
	private AlarmConfigRepository alarmConfigRepository;
	private AlarmTypeRepository alarmTypeRepository;
	private AlarmLevelRepository alarmLevelRepository;
	private AlarmConfigMeasureRepository alarmConfigMeasureRepository;
	private AlarmTargetElementRepository alarmTargetElementRepository;
	private AlarmRepository alarmRepository;
	private AlarmFilteredRepository alarmFilteredRepository;
	private ThresholdOperationRepository thresholdOperationRepository;

	private VmsGraphicGroupRepository vmsGraphicGroupRepository;
	private VmsGraphicGroupElementRepository vmsGraphicGroupElementRepository;
	private VmsGraphicGraphicGroupValueRepository vmsGraphicGraphicGroupRepository;

	private VmsGraphicCategoryRepository vmsGraphicCategoryRepository;
	private VmsGraphicRepository vmsGraphicRepository;

	private RoadRepository roadRepository;
	private RoadTypeRepository roadTypeRepository;
	private StretchRepository stretchRepository;
	private StretchStretchRepository stretchStretchRepository;
	private StretchStretchStretchRepository stretchStretchStretchRepository;
	private StretchTypeRepository stretchTypeRepository;
	private LocationRepository locationRepository;
	private LocationTypeRepository locationTypeRepository;
	private StretchLocationRepository stretchLocationRepository;
	private SpathRepository spathRepository;
	private SpathStretchRepository spathStretchRepository;
	private ImsExtIncidentRepository imsExtIncidentRepository;
	private ImsExtIncidentGroupRepository imsExtIncidentGroupRepository;
	private ImsExtIncidentTypeParamRepository imsExtIncidentTypeParamRepository;
	private ImsExtIncidentTypeRepository imsExtIncidentTypeRepository;
	private ImsExtIncidentValueRepository imsExtIncidentValueRepository;
	private LogTypeRepository logTypeRepository;
	private LogSubtypeRepository logSubtypeRepository;
	private LogTypeParamRepository logTypeParamRepository;
	private AuditLogRepository auditLogRepository;
	private AuditLogValueRepository auditLogValueRepository;
	private I18nLocaleRepository i18nLocaleRepository;
	private I18nLabelRepository i18nLabelRepository;
	private PlanSchedulesRepository planSchedulesRepository;
	private PlanScheduleStateRepository planScheduleStateRepository;
	private PlanScheduleStateTypeRepository planScheduleStateTypeRepository;
	private DataTypeRepository dataTypeRepository;
	private ParamTypeRepository paramTypeRepository;
	private LevelOfServiceRepository levelOfServiceRepository;
	private ImsIncidentStateRepository imsIncidentStateRepository;
	private ImsIncidentLevelRepository imsIncidentLevelRepository;
	private ImsIncidentAlarmConditionAlarmRepository imsIncidentAlarmConditionAlarmRepository;
	private ImsIncidentAlarmConditionAlarmTypeRepository imsIncidentAlarmConditionAlarmTypeRepository;
	private ImsIncidentAlarmConditionRepository imsIncidentAlarmConditionRepository;
	private ImsIncidentReportLogRepository imsIncidentReportLogRepository;
	private ImsIncidentReportLogValueRepository imsIncidentReportLogValueRepository;
	private ImsIncidentReportRepository imsIncidentReportRepository;
	private ImsIncidentReportTaskStateRepository imsIncidentReportTaskStateRepository;
	private ImsIncidentReportTaskValueRepository imsIncidentReportTaskValueRepository;
	private ImsIncidentReportTaskElementRepository imsIncidentReportTaskElementRepository;
	private ImsIncidentReportTaskPlanStateRepository imsIncidentReportTaskPlanStateRepository;
	private ImsIncidentReportAlarmRepository imsIncidentReportAlarmRepository;
	private ImsIncidentTaskPlanRepository imsIncidentTaskPlanRepository;
	private ImsIncidentTypeStateTransitionRepository imsIncidentTypeStateTransitionRepository;
	private ImsIncidentTypeRepository imsIncidentTypeRepository;
	private ImsIncidentTypeStretchRepository imsIncidentTypeStretchRepository;
	private ImsIncidentTypeTaskRepository imsIncidentTypeTaskRepository;
	private ImsIncidentTypeRoadImpactRepository imsIncidentTypeRoadImpactRepository;
	private RoadImpactRepository roadImpactRepository;
	private ImsTaskTypeRepository imsTaskTypeRepository;
	private ImsIncidentParamGroupRepository imsIncidentParamGroupRepository;
	private ImsIncidentParamRepository imsIncidentParamRepository;
	private ImsIncidentReportParamValueRepository imsIncidentParamValueRepository;
	private ImsIncidentEnumParamRepository imsIncidentEnumParamRepository;

	private ImsIncidentGroupRepository imsIncidentGroupRepository;
	private ImsIncidentTypeCauseRepository imsIncidentTypeCauseRepository;

	private ImsTaskTypeParamRepository imsTaskTypeParamRepository;

	private ImsIncidentTypeTaskValueRepository imsIncidentTypeTaskValueRepository;

	private ProcessInfoRepository processInfoRepository;

	private PmQueueRepository pmQueueRepository;
	private PmElementTypeQueueRepository pmElementTypeQueueRepository;
	private PmQueuePriorityRepository pmQueuePriorityRepository;
	private PmDisabledQueuePriorityRepository pmDisabledQueuePriorityRepository;
	private PmCommandElementRepository pmCommandElementRepository;
	private PmCommandElementValueRepository pmCommandElementValueRepository;

	private SmThresholdRepository smThresholdRepository;
	private SmStateMachineRepository smStateMachineRepository;
	private SmTransitionConditionRepository smTransitionConditionRepository;
	private SmConditionRepository smConditionRepository;
	private SmTimerRepository smTimerRepository;

	private ManagementAreaRepository managementAreaRepository;
	private ManagementAreaUserRepository managementAreaUserRepository;
	private ManagementAreaElementRepository managementAreaElementRepository;
	
	private IOControllerModuleRepository ioControllerModuleRepository;
	private ElementIOControllerModuleRepository elementIOControllerModuleRepository;

	private static EntitiesManager instance;

	private List<EntityRepository> repositories;

	public static EntitiesManager getInstance() {

		if (instance == null) {

			instance = new EntitiesManager();
		}

		return instance;
	}

	private EntitiesManager() {

		newInstanceRepository();
		EntitiesManagerHelper.cacheRepository(this);
	}

	private void newInstanceRepository() {

		repositories = new ArrayList<>();

		// CCTVScenario
		this.cctvScenarioRepository = new CCTVScenarioRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.cctvScenarioElementRepository = new CCTVScenarioElementRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.cctvScenarioNodeRepository = new CCTVScenarioNodeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.cctvScenarioHierarchyRepository = new CCTVScenarioHierarchyRepository(true, CACHE_RETENTION_TIME_MASTER);

		// SYSTEMPARAM
		this.systemParamRepository = new SystemParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.systemParamValueRepository = new SystemParamValueRepository(true, CACHE_RETENTION_TIME_MASTER);

		// USER MANAGER
		this.systemPermissionRepository = new SystemPermissionRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.userRepository = new UserRepository(true, CACHE_RETENTION_TIME_CONF, repositories);
		repositories.add(this.userRepository);

		this.userSessionRepository = new UserSessionRepository(false);
		repositories.add(this.userSessionRepository);

		this.roleRepository = new RoleRepository(true, CACHE_RETENTION_TIME_CONF, repositories);
		repositories.add(this.roleRepository);

		this.userPermissionRepository = new UserPermissionRepository(true, CACHE_RETENTION_TIME_CONF);
		repositories.add(this.userPermissionRepository);

		this.rolePermissionRepository = new RolePermissionRepository(true, CACHE_RETENTION_TIME_CONF);
		repositories.add(this.rolePermissionRepository);

		this.rolesUsersRepository = new RolesUsersRepository(true, CACHE_RETENTION_TIME_CONF);
		repositories.add(this.rolesUsersRepository);

		// PLAN MANAGER
		this.planNodeRepository = new PlanNodeRepository(true, CACHE_RETENTION_TIME_CONF, repositories);
		this.planHierarchyRepository = new PlanHierarchyRepository(true, CACHE_RETENTION_TIME_CONF);
		repositories.add(this.planHierarchyRepository);

		this.planOrderTypeParamRepository = new PlanOrderTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.planOrderTypeRepository = new PlanOrderTypeRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.planOrderRepository = new PlanOrderRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.planOrderRepository);

		this.planOrderElementTypeStateRepository = new PlanOrderElementTypeStateRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.planOrderElementTypeStateRepository);

		this.planOrderConditionRepository = new PlanOrderConditionRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.planOrderConditionRepository);

		this.planOrderValueRepository = new PlanOrderValueRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.planOrderValueRepository);

		this.planOrderCommandElementRepository = new PlanOrderCommandElementRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.planOrderCommandElementRepository);

		this.planOrderElementValueRepository = new PlanOrderElementValueRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.planOrderElementValueRepository);

		this.planRepository = new PlanRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.planRepository);

		this.planStateTypeRepository = new PlanStateTypeRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.planStateRepository = new PlanStateRepository(false);
		repositories.add(this.planStateRepository);

		this.planScheduleStateRepository = new PlanScheduleStateRepository(false);
		repositories.add(this.planScheduleStateRepository);

		this.planScheduleStateTypeRepository = new PlanScheduleStateTypeRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.planOrderStateRepository = new PlanOrderStateRepository(false);
		repositories.add(this.planOrderStateRepository);

		this.planOrderStateTypeRepository = new PlanOrderStateTypeRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.planSchedulesRepository = new PlanSchedulesRepository(false, repositories);

		this.commandElementRepository = new CommandElementRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.commandElementRepository);

		this.commandElementValueRepository = new CommandElementValueRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.commandElementValueRepository);

		// EQUIPMENT MANAGER
		this.commandElementTypeRepository = new CommandElementTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.commandElementTypeParamRepository = new CommandElementTypeParamRepository(true,
				CACHE_RETENTION_TIME_MASTER);

		this.elementTypeRepository = new ElementTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.elementStateRepository = new ElementStateRepository(
				new EntityRepositoryOptions(false, null, repositories, false, false, null));
		repositories.add(this.elementStateRepository);

		this.elementTypeStateRepository = new ElementTypeStateRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.elementGroupRepository = new ElementGroupRepository(true, CACHE_RETENTION_TIME_CONF);
		this.elementValueStateRepository = new ElementValueStateRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.elementSubtypeRepository = new ElementSubtypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.elementSubtypeActiveParamRepository = new ElementSubtypeActiveParamRepository(true,
				CACHE_RETENTION_TIME_MASTER);

		this.elementSubtypeActiveCommandRepository = new ElementSubtypeActiveCommandRepository(true,
				CACHE_RETENTION_TIME_MASTER);

		this.elementIOControllerModuleRepository = new ElementIOControllerModuleRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, null, true, false, null));
		
		repositories.add(elementIOControllerModuleRepository);

		this.elementRepository = new ElementRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.elementRepository);

		this.elementValueRepository = new ElementValueRepository(
				new EntityRepositoryOptions(false, null, repositories, true, false, null));
		repositories.add(this.elementValueRepository);

		this.elementTreeRepository = new ElementTreeRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, null, false, false, null));

		this.elementTreeNodeRepository = new ElementTreeNodeRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, null, false, false, null));

		this.elementTreeNodeHierarchyRepository = new ElementTreeNodeHierarchyRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, null, false, false, null));

		this.elementTypeParamRepository = new ElementTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		repositories.add(this.elementTypeParamRepository);

		this.elementTypeParamUnitRepository = new ElementTypeParamUnitRepository(true, CACHE_RETENTION_TIME_MASTER);
		repositories.add(this.elementTypeParamUnitRepository);

		this.elementHierarchiesRepository = new ElementHierarchiesRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.elementHierarchiesRepository);

		this.elementHierarchiesAllowedRepository = new ElementHierarchiesAllowedRepository(true,
				CACHE_RETENTION_TIME_MASTER);

		this.genericDataRepository = new GenericDataRepository(false);

		this.vmsGraphicGroupRepository = new VmsGraphicGroupRepository(true, CACHE_RETENTION_TIME_CONF);

		this.vmsGraphicGroupElementRepository = new VmsGraphicGroupElementRepository(true, CACHE_RETENTION_TIME_CONF);
		this.vmsGraphicGraphicGroupRepository = new VmsGraphicGraphicGroupValueRepository(true,
				CACHE_RETENTION_TIME_CONF);

		this.vmsGraphicCategoryRepository = new VmsGraphicCategoryRepository(true, CACHE_RETENTION_TIME_CONF);
		this.vmsGraphicRepository = new VmsGraphicRepository(true, CACHE_RETENTION_TIME_CONF);

		this.roadRepository = new RoadRepository(true, CACHE_RETENTION_TIME_CONF);
		this.roadTypeRepository = new RoadTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.stretchRepository = new StretchRepository(true, CACHE_RETENTION_TIME_CONF);
		this.stretchStretchRepository = new StretchStretchRepository(true, CACHE_RETENTION_TIME_CONF);
		this.stretchStretchStretchRepository = new StretchStretchStretchRepository(true, CACHE_RETENTION_TIME_CONF);
		this.stretchTypeRepository = new StretchTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		
		this.locationTypeRepository = new LocationTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.stretchLocationRepository = new StretchLocationRepository(true, CACHE_RETENTION_TIME_CONF);
		repositories.add(stretchLocationRepository);
		
		this.locationRepository  = new LocationRepository(
				new EntityRepositoryOptions(false, null, repositories, true, false, null));
		repositories.add(this.locationRepository);
		
		this.spathRepository = new SpathRepository(true, CACHE_RETENTION_TIME_CONF);
		this.spathStretchRepository = new SpathStretchRepository(true, CACHE_RETENTION_TIME_CONF);

		this.thresholdOperationRepository = new ThresholdOperationRepository(true, CACHE_RETENTION_TIME_MASTER);
		repositories.add(this.thresholdOperationRepository);

		this.levelOfServiceRepository = new LevelOfServiceRepository(true);

		this.dataTypeRepository = new DataTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.paramTypeRepository = new ParamTypeRepository(true, CACHE_RETENTION_TIME_MASTER);

		// Alarm manager
		this.alarmTypeRepository = new AlarmTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.alarmLevelRepository = new AlarmLevelRepository(true, CACHE_RETENTION_TIME_MASTER);

		this.alarmRepository = new AlarmRepository(
				new EntityRepositoryOptions(false, null, repositories, true, false, null));
		repositories.add(this.alarmRepository);

		this.alarmFilteredRepository = new AlarmFilteredRepository(
				new EntityRepositoryOptions(false, null, repositories, true, false, null));

		// this.alarmConfigRepository = new AlarmConfigRepository(true,
		// CACHE_RETENTION_TIME_CONF, repositories);

		this.alarmConfigRepository = new AlarmConfigRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.alarmConfigRepository);

		// this.alarmConfigMeasureRepository = new AlarmConfigMeasureRepository(true,
		// CACHE_RETENTION_TIME_CONF);

		this.alarmConfigMeasureRepository = new AlarmConfigMeasureRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.alarmConfigMeasureRepository);

		this.elementStateAlarmRepository = new ElementStateAlarmRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, repositories, true, false, null));
		repositories.add(this.elementStateAlarmRepository);

		this.alarmTargetElementRepository = new AlarmTargetElementRepository(true, CACHE_RETENTION_TIME_CONF);
		repositories.add(this.alarmTargetElementRepository);

		// Logger
		this.logTypeRepository = new LogTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.logSubtypeRepository = new LogSubtypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.logTypeParamRepository = new LogTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.auditLogRepository = new AuditLogRepository(false);
		this.auditLogValueRepository = new AuditLogValueRepository(false);

		// IMS MANAGER
		this.imsIncidentReportRepository = new ImsIncidentReportRepository(false);
		this.imsIncidentReportTaskStateRepository = new ImsIncidentReportTaskStateRepository(false);
		this.imsIncidentReportTaskValueRepository = new ImsIncidentReportTaskValueRepository(false);
		this.imsIncidentReportTaskElementRepository = new ImsIncidentReportTaskElementRepository(false);
		this.imsIncidentReportTaskPlanStateRepository = new ImsIncidentReportTaskPlanStateRepository(false);

		this.imsIncidentReportAlarmRepository = new ImsIncidentReportAlarmRepository(false);
		this.imsIncidentReportLogRepository = new ImsIncidentReportLogRepository(false, repositories);
		this.imsIncidentReportLogValueRepository = new ImsIncidentReportLogValueRepository(false);
		repositories.add(this.imsIncidentReportLogValueRepository);
		this.imsIncidentStateRepository = new ImsIncidentStateRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.imsIncidentLevelRepository = new ImsIncidentLevelRepository(true, CACHE_RETENTION_TIME_STATIC);

		this.imsTaskTypeRepository = new ImsTaskTypeRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.imsTaskTypeParamRepository = new ImsTaskTypeParamRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.imsIncidentAlarmConditionAlarmTypeRepository = new ImsIncidentAlarmConditionAlarmTypeRepository(true,
				CACHE_RETENTION_TIME_MASTER);

		this.imsIncidentTaskPlanRepository = new ImsIncidentTaskPlanRepository(true, CACHE_RETENTION_TIME_CONF);
		this.imsIncidentTypeStateTransitionRepository = new ImsIncidentTypeStateTransitionRepository(true,
				CACHE_RETENTION_TIME_CONF);
		this.imsIncidentTypeRepository = new ImsIncidentTypeRepository(true, CACHE_RETENTION_TIME_CONF, repositories);
		this.imsIncidentTypeStretchRepository = new ImsIncidentTypeStretchRepository(true, CACHE_RETENTION_TIME_CONF,
				repositories);
		this.imsIncidentTypeTaskRepository = new ImsIncidentTypeTaskRepository(true, CACHE_RETENTION_TIME_CONF,
				repositories);
		this.imsIncidentTypeTaskValueRepository = new ImsIncidentTypeTaskValueRepository(true,
				CACHE_RETENTION_TIME_CONF);
		this.imsIncidentTypeRoadImpactRepository = new ImsIncidentTypeRoadImpactRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, null, false, false, null));
		this.roadImpactRepository = new RoadImpactRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_MASTER, null, false, false, null));
		repositories.add(this.imsIncidentTypeTaskValueRepository);
		repositories.add(this.imsIncidentTypeStretchRepository);
		repositories.add(this.imsIncidentTypeTaskRepository);
		repositories.add(this.imsIncidentTypeStateTransitionRepository);
		repositories.add(this.imsIncidentTypeTaskValueRepository);
		repositories.add(this.imsIncidentTypeRoadImpactRepository);

		this.imsIncidentGroupRepository = new ImsIncidentGroupRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.imsIncidentTypeCauseRepository = new ImsIncidentTypeCauseRepository(true, CACHE_RETENTION_TIME_MASTER);
		repositories.add(this.imsIncidentTypeCauseRepository);

		this.imsIncidentAlarmConditionRepository = new ImsIncidentAlarmConditionRepository(true,
				CACHE_RETENTION_TIME_CONF, repositories);
		this.imsIncidentAlarmConditionAlarmRepository = new ImsIncidentAlarmConditionAlarmRepository(true,
				CACHE_RETENTION_TIME_CONF);
		repositories.add(this.imsIncidentAlarmConditionRepository);
		repositories.add(this.imsIncidentAlarmConditionAlarmRepository);

		this.imsExtIncidentRepository = new ImsExtIncidentRepository(false, repositories);
		repositories.add(this.imsExtIncidentRepository);

		this.imsExtIncidentGroupRepository = new ImsExtIncidentGroupRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.imsExtIncidentTypeParamRepository = new ImsExtIncidentTypeParamRepository(true);
		this.imsExtIncidentTypeRepository = new ImsExtIncidentTypeRepository(true);
		this.imsExtIncidentValueRepository = new ImsExtIncidentValueRepository(false);
		repositories.add(this.imsExtIncidentValueRepository);

		this.imsIncidentParamGroupRepository = new ImsIncidentParamGroupRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.imsIncidentParamRepository = new ImsIncidentParamRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.imsIncidentParamValueRepository = new ImsIncidentReportParamValueRepository(false);
		this.imsIncidentEnumParamRepository = new ImsIncidentEnumParamRepository(true, CACHE_RETENTION_TIME_STATIC);

		// i18n
		this.i18nLocaleRepository = new I18nLocaleRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.i18nLabelRepository = new I18nLabelRepository(true);

		// CommandElementPreconditions
		this.commandElementPreconditionRepository = new CommandElementPreconditionRepository(true,
				CACHE_RETENTION_TIME_MASTER, repositories);
		this.commandElementValuePreconditionRepository = new CommandElementValuePreconditionRepository(true,
				CACHE_RETENTION_TIME_MASTER);
		repositories.add(this.commandElementValuePreconditionRepository);

		// ProcessInfo
		this.processInfoRepository = new ProcessInfoRepository(false);

		// Pm
		this.pmQueueRepository = new PmQueueRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.pmElementTypeQueueRepository = new PmElementTypeQueueRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.pmQueuePriorityRepository = new PmQueuePriorityRepository(true, CACHE_RETENTION_TIME_MASTER);
		this.pmDisabledQueuePriorityRepository = new PmDisabledQueuePriorityRepository(false);
		this.pmCommandElementRepository = new PmCommandElementRepository(false);
		this.pmCommandElementValueRepository = new PmCommandElementValueRepository(false);

		// SM
		this.smThresholdRepository = new SmThresholdRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.smStateMachineRepository = new SmStateMachineRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.smTimerRepository = new SmTimerRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.smTransitionConditionRepository = new SmTransitionConditionRepository(true, CACHE_RETENTION_TIME_STATIC);
		this.smConditionRepository = new SmConditionRepository(true, CACHE_RETENTION_TIME_STATIC);

		// ManagementArea
		this.managementAreaRepository = new ManagementAreaRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_STATIC, null, false, false, null));

		this.managementAreaUserRepository = new ManagementAreaUserRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, null, false, false, null));

		this.managementAreaElementRepository = new ManagementAreaElementRepository(
				new EntityRepositoryOptions(true, CACHE_RETENTION_TIME_CONF, null, false, false, null));

		repositories.add(this.managementAreaUserRepository);
		repositories.add(this.managementAreaElementRepository);
		
		//IOController
		ioControllerModuleRepository = new IOControllerModuleRepository(true, CACHE_RETENTION_TIME_CONF);
	}

	public Command sendCommand(Command command) {

		log.info("SendCommand " + command);
		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

	public static Long getManagementAreaId(DataFetchingEnvironment env) {
		Long managementAreaId = null;

		if (env != null) {
			GraphQLContext context = env.getContext();

			if (context != null) {

				Optional<HttpServletRequest> httpServletRequest = context.getHttpServletRequest();

				if (httpServletRequest.isPresent()) {

					String id = httpServletRequest.get().getHeader("management-area");

					if (StringUtils.isNotEmpty(id)) {

						try {
							Key key = new Key(id);

							if (key.getClazz() == ManagementArea.class) {

								managementAreaId = Long.valueOf(key.getId(1));
							}

						} catch (Exception e) {

							log.error("The id does not correspond to the required object: ManagementArea");
							log.debug(ExceptionUtils.getStackTrace(e));
						}
					}
				}

			}
		}

		return managementAreaId;

	}
}
