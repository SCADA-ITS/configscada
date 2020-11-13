package com.revenga.rits.front.openits.adapter.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.model.DataSourceValue;
import com.revenga.rits.front.openitsdao.service.DataSourceService;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class VODataSourceAdapter {
	
	@Autowired
	@Getter
	DataSourceService dataSourceService;
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired
	GraphqlTypeAdapter graphqlTypeAdapter;
	
	@Autowired
	private Environment env;
	
	public static final String DATA_SOURCE_GET_ALARMS = "data.source.get_alarms";
	public static final String DATA_SOURCE_GET_ALARMS_BY_ALARM_TYPE = "data.source.get_alarms_by_alarm_type";
	public static final String DATA_SOURCE_GET_ALARM = "data.source.get_alarm";
	public static final String DATA_SOURCE_GET_ALARM_TYPES = "data.source.get_alarm_types";
	public static final String DATA_SOURCE_GET_ALARM_TYPE = "data.source.get_alarm_type";
	public static final String DATA_SOURCE_GET_ALARM_LEVELS = "data.source.get_alarm_levels";
	public static final String DATA_SOURCE_GET_ALARM_LEVEL = "data.source.get_alarm_level";
	public static final String DATA_SOURCE_GET_ALARM_CONFIGS = "data.source.get_alarm_configs";
	public static final String DATA_SOURCE_GET_ALARM_CONFIG = "data.source.get_alarm_config";
	public static final String DATA_SOURCE_GET_ALARM_THRESOLD_OPERATIONS = "data.source.get_alarm_threshold_operations";
	public static final String DATA_SOURCE_GET_ALARM_THRESOLD_OPERATION = "data.source.get_alarm_threshold_operation";
	public static final String DATA_SOURCE_GET_ALARM_MUTEDS = "data.source.get_alarm_muteds";
	public static final String DATA_SOURCE_GET_ALARM_MUTED = "data.source.get_alarm_muted";
	public static final String DATA_SOURCE_GET_ALARM_MUTEDS_BY_ALARM_CONFIG = "data.source.get_alarm_muteds_by_alarm_config";
	public static final String DATA_SOURCE_GET_ALARM_MUTEDS_BY_ELEMENT = "data.source.get_alarm_muteds_by_element";
	public static final String DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE = "data.source.get_elements_by_element_type";
	public static final String DATA_SOURCE_GET_ELEMENTS = "data.source.get_elements";
	public static final String DATA_SOURCE_GET_ELEMENTS_ARRAY = "data.source.get_elements_array";
	public static final String DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_SYNOPTIC = "data.source.get_elements_array_states_synoptic";
	public static final String DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_SYNOPTIC = "data.source.get_elements_array_states_measures_alarms_synoptic";
	public static final String DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MAP = "data.source.get_elements_array_states_map";
	public static final String DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_MAP = "data.source.get_elements_array_states_measures_alarms_map";
	public static final String DATA_SOURCE_GET_ELEMENT = "data.source.get_element";
	public static final String DATA_SOURCE_GET_ELEMENT_TYPES = "data.source.get_element_types";
	public static final String DATA_SOURCE_GET_ELEMENT_TYPE = "data.source.get_element_type";
	public static final String DATA_SOURCE_GET_ELEMENT_VALUES_STATES = "data.source.get_element_values_states";
	public static final String DATA_SOURCE_GET_GENERIC_DATAS = "data.source.get_generic_datas";
	public static final String DATA_SOURCE_GET_GENERIC_DATA = "data.source.get_generic_data";
	public static final String DATA_SOURCE_GET_LABEL_TRANSLATED = "data.source.get_label_translated";
	public static final String DATA_SOURCE_GET_LABEL_TRANSLATED_USING_FIELDS = "data.source.get_label_translated_using_fields";
	public static final String DATA_SOURCE_GET_LOCALES = "data.source.get_locales";
	public static final String DATA_SOURCE_GET_LOCALE = "data.source.get_locale";
	public static final String DATA_SOURCE_GET_LOCATIONS = "data.source.get_locations";
	public static final String DATA_SOURCE_GET_LOCATION = "data.source.get_location";
	public static final String DATA_SOURCE_GET_ROADS = "data.source.get_roads";
	public static final String DATA_SOURCE_GET_ROAD = "data.source.get_road";
	public static final String DATA_SOURCE_GET_STRETCHS = "data.source.get_stretchs";
	public static final String DATA_SOURCE_GET_STRETCH = "data.source.get_stretch";
	public static final String DATA_SOURCE_GET_STRETCH_BY_ROAD = "data.source.get_stretch_by_road";
	public static final String DATA_SOURCE_GET_PLANS = "data.source.get_plans";
	public static final String DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE = "data.source.get_translations_by_locale";
	public static final String DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE = "data.source.get_translations_by_locale_and_module";
	public static final String DATA_SOURCE_GET_USERS = "data.source.get_users";
	public static final String DATA_SOURCE_GET_USER = "data.source.get_user";
	public static final String DATA_SOURCE_GET_ROLES = "data.source.get_roles";
	public static final String DATA_SOURCE_GET_ROLE = "data.source.get_role";
	public static final String DATA_SOURCE_GET_SYSTEM_PERMISSIONS = "data.source.get_system_permissions";
	public static final String DATA_SOURCE_GET_SYSTEM_PERMISSION = "data.source.get_system_permission";
	public static final String DATA_SOURCE_GET_INCIDENTS = "data.source.get_incidents";
	public static final String DATA_SOURCE_GET_INCIDENT = "data.source.get_incident";
	public static final String DATA_SOURCE_GET_INCIDENT_BY_TYPE = "data.source.get_incident_by_type";
	public static final String DATA_SOURCE_GET_INCIDENT_TYPES = "data.source.get_incident_types";
	public static final String DATA_SOURCE_GET_INCIDENT_TYPE = "data.source.get_incident_type";
	public static final String DATA_SOURCE_GET_INCIDENT_TYPE_STATES = "data.source.get_incident_type_states";
	public static final String DATA_SOURCE_GET_NEXT_STATE = "data.source.get_next_state";
	public static final String DATA_SOURCE_GET_ROAD_IMPACTS = "data.source.get_road_impacts";
	public static final String DATA_SOURCE_GET_ROAD_IMPACT = "data.source.get_road_impact";
	public static final String DATA_SOURCE_GET_INIT_ROAD_IMPACTS = "data.source.get_init_road_impacts";
	public static final String DATA_SOURCE_GET_ROAD_IMPACTS_BY_INCIDENT = "data.source.get_road_impacts_by_incident";
	public static final String DATA_SOURCE_GET_INCIDENTS_SOURCE = "data.source.get_incidents_source";
	public static final String DATA_SOURCE_GET_INCIDENT_SOURCE = "data.source.get_incident_source";
	public static final String DATA_SOURCE_GET_EXT_INCIDENTS = "data.source.get_ext_incidents";
	public static final String DATA_SOURCE_GET_EXT_INCIDENT = "data.source.get_ext_incident";
	public static final String DATA_SOURCE_GET_EXT_INCIDENT_TYPES = "data.source.get_ext_incident_types";
	public static final String DATA_SOURCE_GET_EXT_INCIDENT_TYPE = "data.source.get_ext_incident_type";
	public static final String DATA_SOURCE_GET_INCIDENT_DOCUMENTS = "data.source.get_incident_documents";
	public static final String DATA_SOURCE_GET_INCIDENT_DOCUMENT = "data.source.get_incident_document";
	public static final String DATA_SOURCE_GET_INCIDENT_DOCUMENT_TYPES = "data.source.get_incident_document_types";
	public static final String DATA_SOURCE_GET_INCIDENT_DOCUMENT_TYPE = "data.source.get_incident_document_type";
	public static final String DATA_SOURCE_GET_LOG_TYPES = "data.source.get_log_types";
	public static final String DATA_SOURCE_GET_LOG_TYPE = "data.source.get_log_type";
	
	
	public static final String DATA_SOURCE_CREATE_USER = "data.source.create_user";
	public static final String DATA_SOURCE_UPDATE_USER = "data.source.update_user";
	public static final String DATA_SOURCE_DELETE_USER = "data.source.delete_user";
	public static final String DATA_SOURCE_TYPE_GRAPHQL_QUERY = "data.source.type.graphql_query";
	public static final String DATA_SOURCE_TYPE_GRAPHQL_MUTATION = "data.source.type.graphql_mutation";
	public static final String DATA_SOURCE_GRAPHQL_QUERY_URL = "data.source.graphql_query.url";
	public static final String DATA_SOURCE_GRAPHQL_QUERY_METHOD = "data.source.graphql_query.method";
	public static final String DATA_SOURCE_GRAPHQL_QUERY_PARAMS = "data.source.graphql_query.params";
	public static final String DATA_SOURCE_GRAPHQL_QUERY_CONTENT = "data.source.graphql_query.content";
	public static final String DATA_SOURCE_GRAPHQL_MUTATION_URL = "data.source.graphql_mutation.url";
	public static final String DATA_SOURCE_GRAPHQL_MUTATION_METHOD = "data.source.graphql_mutation.method";
	public static final String DATA_SOURCE_GRAPHQL_MUTATION_PARAMS = "data.source.graphql_mutation.params";
	public static final String DATA_SOURCE_GRAPHQL_MUTATION_PARAM_TYPES = "data.source.graphql_mutation.param_types";
	public static final String DATA_SOURCE_GRAPHQL_MUTATION_CONTENT = "data.source.graphql_mutation.content";
	
	@Getter
	private Integer dataSourceGetAlarms;
	@Getter
	private Integer dataSourceGetAlarmsByAlarmType;
	@Getter
	private Integer dataSourceGetAlarm;
	@Getter
	private Integer dataSourceGetAlarmTypes;
	@Getter
	private Integer dataSourceGetAlarmType;
	@Getter
	private Integer dataSourceGetAlarmLevels;
	@Getter
	private Integer dataSourceGetAlarmLevel;
	@Getter
	private Integer dataSourceGetAlarmConfigs;
	@Getter
	private Integer dataSourceGetAlarmConfig;
	@Getter
	private Integer dataSourceGetAlarmThresholdOperations;
	@Getter
	private Integer dataSourceGetAlarmThresholdOperation;
	@Getter
	private Integer dataSourceGetAlarmMuteds;
	@Getter
	private Integer dataSourceGetAlarmMuted;
	@Getter
	private Integer dataSourceGetAlarmMutedsByAlarmConfig;
	@Getter
	private Integer dataSourceGetAlarmMutedsByElement;
	@Getter
	private Integer dataSourceGetElementsByElementType;
	@Getter
	private Integer dataSourceGetElements;
	@Getter
	private Integer dataSourceGetElementsArray;
	@Getter
	private Integer dataSourceGetElementsArrayStatesSynoptic;
	@Getter
	private Integer dataSourceGetElementsArrayStatesMeasuresAlarmsSynoptic;
	@Getter
	private Integer dataSourceGetElementsArrayStatesMap;
	@Getter
	private Integer dataSourceGetElementsArrayStatesMeasuresAlarmsMap;
	@Getter
	private Integer dataSourceGetElement;
	@Getter
	private Integer dataSourceGetElementTypes;
	@Getter
	private Integer dataSourceGetElementType;
	@Getter
	private Integer dataSourceGetElementValueStates;
	@Getter
	private Integer dataSourceGetGenericDatas;
	@Getter
	private Integer dataSourceGetGenericData;
	@Getter
	private Integer dataSourceGetLabelTranslated;
	@Getter
	private Integer dataSourceGetLabelTranslatedUsingFields;
	@Getter
	private Integer dataSourceGetLocales;
	@Getter
	private Integer dataSourceGetLocale;
	@Getter
	private Integer dataSourceGetLocations;
	@Getter
	private Integer dataSourceGetLocation;
	@Getter
	private Integer dataSourceGetRoads;
	@Getter
	private Integer dataSourceGetRoad;
	@Getter
	private Integer dataSourceGetStretchs;
	@Getter
	private Integer dataSourceGetStretch;
	@Getter
	private Integer dataSourceGetStretchByRoad;
	@Getter
	private Integer dataSourceGetPlans;
	@Getter
	private Integer dataSourceGetTranslationsByLocale;
	@Getter
	private Integer dataSourceGetTranslationsByLocaleAndModule;
	@Getter
	private Integer dataSourceGetUsers;
	@Getter
	private Integer dataSourceGetUser;
	@Getter
	private Integer dataSourceGetRoles;
	@Getter
	private Integer dataSourceGetRole;
	@Getter
	private Integer dataSourceGetSystemPermissions;
	@Getter
	private Integer dataSourceGetSystemPermission;
	@Getter
	private Integer dataSourceGetIncidents;
	@Getter
	private Integer dataSourceGetIncident;
	@Getter
	private Integer dataSourceGetIncidentByType;
	@Getter
	private Integer dataSourceGetIncidentTypes;
	@Getter
	private Integer dataSourceGetIncidentType;
	@Getter
	private Integer dataSourceGetIncidentTypeStates;
	@Getter
	private Integer dataSourceGetNextState;
	@Getter
	private Integer dataSourceGetRoadImpacts;
	@Getter
	private Integer dataSourceGetRoadImpact;
	@Getter
	private Integer dataSourceGetInitRoadImpacts;
	@Getter
	private Integer dataSourceGetRoadImpactsByIncident;
	@Getter
	private Integer dataSourceGetIncidentsSource;
	@Getter
	private Integer dataSourceGetIncidentSource;
	@Getter
	private Integer dataSourceGetExtIncidents;
	@Getter
	private Integer dataSourceGetExtIncident;
	@Getter
	private Integer dataSourceGetExtIncidentTypes;
	@Getter
	private Integer dataSourceGetExtIncidentType;
	@Getter
	private Integer dataSourceGetIncidentDocuments;
	@Getter
	private Integer dataSourceGetIncidentDocument;
	@Getter
	private Integer dataSourceGetIncidentDocumentTypes;
	@Getter
	private Integer dataSourceGetIncidentDocumentType;
	@Getter
	private Integer dataSourceGetLogTypes;
	@Getter
	private Integer dataSourceGetLogType;
	@Getter
	private Integer dataSourceCreateUser;
	@Getter
	private Integer dataSourceUpdateUser;
	@Getter
	private Integer dataSourceDeleteUser;
	@Getter
	private Integer dataSourceTypeGraphqlQuery;
	@Getter
	private Integer dataSourceTypeGraphqlMutation;
	@Getter
	private Integer dataSourceGraphqlQueryUrl;
	@Getter
	private Integer dataSourceGraphqlQueryMethod;
	@Getter
	private Integer dataSourceGraphqlQueryParams;
	@Getter
	private Integer dataSourceGraphqlQueryContent;
	@Getter
	private Integer dataSourceGraphqlMutationUrl;
	@Getter
	private Integer dataSourceGraphqlMutationMethod;
	@Getter
	private Integer dataSourceGraphqlMutationParams;
	@Getter
	private Integer dataSourceGraphqlMutationParamTypes;
	@Getter
	private Integer dataSourceGraphqlMutationContent;
	
	@PostConstruct
	private void initialize() {
	
		this.dataSourceGetAlarms = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARMS));
		this.dataSourceGetAlarmsByAlarmType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARMS_BY_ALARM_TYPE));
		this.dataSourceGetAlarm = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM));
		this.dataSourceGetAlarmTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_TYPES));
		this.dataSourceGetAlarmType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_TYPE));
		this.dataSourceGetAlarmLevels = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_LEVELS));
		this.dataSourceGetAlarmLevel = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_LEVEL));
		this.dataSourceGetAlarmConfigs = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_CONFIGS));
		this.dataSourceGetAlarmConfig = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_CONFIG));
		this.dataSourceGetAlarmThresholdOperations = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_THRESOLD_OPERATIONS));
		this.dataSourceGetAlarmThresholdOperation = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_THRESOLD_OPERATION));
		this.dataSourceGetAlarmMuteds = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_MUTEDS));
		this.dataSourceGetAlarmMuted = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_MUTED));
		this.dataSourceGetAlarmMutedsByAlarmConfig = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_MUTEDS_BY_ALARM_CONFIG));
		this.dataSourceGetAlarmMutedsByElement = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ALARM_MUTEDS_BY_ELEMENT));
		this.dataSourceGetElementsByElementType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE));
		this.dataSourceGetElements = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS));
		this.dataSourceGetElementsArray = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_ARRAY));
		this.dataSourceGetElementsArrayStatesSynoptic = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_SYNOPTIC));
		this.dataSourceGetElementsArrayStatesMeasuresAlarmsSynoptic = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_SYNOPTIC));
		this.dataSourceGetElementsArrayStatesMap = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MAP));
		this.dataSourceGetElementsArrayStatesMeasuresAlarmsMap = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_MAP));
		this.dataSourceGetElement = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT));
		this.dataSourceGetElementTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT_TYPES));
		this.dataSourceGetElementType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT_TYPE));
		this.dataSourceGetElementValueStates = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT_VALUES_STATES));
		this.dataSourceGetGenericDatas = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_GENERIC_DATAS));
		this.dataSourceGetGenericData = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_GENERIC_DATA));
		this.dataSourceGetLabelTranslated = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LABEL_TRANSLATED));
		this.dataSourceGetLabelTranslatedUsingFields = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LABEL_TRANSLATED_USING_FIELDS));
		this.dataSourceGetLocales = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOCALES));
		this.dataSourceGetLocale = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOCALE));
		this.dataSourceGetLocations = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOCATIONS));
		this.dataSourceGetLocation = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOCATION));
		this.dataSourceGetRoads = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ROADS));
		this.dataSourceGetRoad = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ROAD));
		this.dataSourceGetStretchs = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_STRETCHS));
		this.dataSourceGetStretch = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_STRETCH));
		this.dataSourceGetStretchByRoad = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_STRETCH_BY_ROAD));
		this.dataSourceGetPlans = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_PLANS));
		this.dataSourceGetTranslationsByLocale = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE));
		this.dataSourceGetTranslationsByLocaleAndModule = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE));
		this.dataSourceGetUsers = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_USERS));
		this.dataSourceGetUser = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_USER));
		this.dataSourceGetRoles = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ROLES));
		this.dataSourceGetRole = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ROLE));
		this.dataSourceGetSystemPermissions = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_SYSTEM_PERMISSIONS));
		this.dataSourceGetSystemPermission = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_SYSTEM_PERMISSION));
		this.dataSourceGetIncidents = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENTS));
		this.dataSourceGetIncident = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT));
		this.dataSourceGetIncidentByType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_BY_TYPE));
		this.dataSourceGetIncidentTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_TYPES));
		this.dataSourceGetIncidentType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_TYPE));
		this.dataSourceGetIncidentTypeStates = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_TYPE_STATES));
		this.dataSourceGetNextState = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_NEXT_STATE));
		this.dataSourceGetRoadImpacts = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ROAD_IMPACTS));
		this.dataSourceGetRoadImpact = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ROAD_IMPACT));
		this.dataSourceGetInitRoadImpacts = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INIT_ROAD_IMPACTS));
		this.dataSourceGetRoadImpactsByIncident = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ROAD_IMPACTS_BY_INCIDENT));
		this.dataSourceGetIncidentsSource = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENTS_SOURCE));
		this.dataSourceGetIncidentSource = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_SOURCE));
		this.dataSourceGetExtIncidents = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_EXT_INCIDENTS));
		this.dataSourceGetExtIncident = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_EXT_INCIDENT));
		this.dataSourceGetExtIncidentTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_EXT_INCIDENT_TYPES));
		this.dataSourceGetExtIncidentType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_EXT_INCIDENT_TYPE));
		this.dataSourceGetIncidentDocuments = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_DOCUMENTS));
		this.dataSourceGetIncidentDocument = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_DOCUMENT));
		this.dataSourceGetIncidentDocumentTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_DOCUMENT_TYPES));
		this.dataSourceGetIncidentDocumentType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_INCIDENT_DOCUMENT_TYPE));
		this.dataSourceGetLogTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOG_TYPES));
		this.dataSourceGetLogType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOG_TYPE));

		this.dataSourceCreateUser = Integer.parseInt(env.getProperty(DATA_SOURCE_CREATE_USER));
		this.dataSourceUpdateUser = Integer.parseInt(env.getProperty(DATA_SOURCE_UPDATE_USER));
		this.dataSourceDeleteUser = Integer.parseInt(env.getProperty(DATA_SOURCE_DELETE_USER));
		this.dataSourceTypeGraphqlQuery = Integer.parseInt(env.getProperty(DATA_SOURCE_TYPE_GRAPHQL_QUERY));
		this.dataSourceTypeGraphqlMutation = Integer.parseInt(env.getProperty(DATA_SOURCE_TYPE_GRAPHQL_MUTATION));
		this.dataSourceGraphqlQueryUrl = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_QUERY_URL));
		this.dataSourceGraphqlQueryMethod = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_QUERY_METHOD));
		this.dataSourceGraphqlQueryParams = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_QUERY_PARAMS));
		this.dataSourceGraphqlQueryContent = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_QUERY_CONTENT));
		this.dataSourceGraphqlMutationUrl = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_MUTATION_URL));
		this.dataSourceGraphqlMutationMethod = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_MUTATION_METHOD));
		this.dataSourceGraphqlMutationParams = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_MUTATION_PARAMS));
		this.dataSourceGraphqlMutationParamTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_MUTATION_PARAM_TYPES));
		this.dataSourceGraphqlMutationContent = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_MUTATION_CONTENT));
	}

	public List<Map<String, Object>> getList(Integer dataSourceId, Object[] params) {
		
		List<Map<String, Object>> list = null;
		DataSource dataSource = dataSourceService.getById(dataSourceId);
		
		if (dataSource != null && dataSource.getDataSourceType().getId().equals(this.dataSourceTypeGraphqlQuery)) {
				
			list = getListFromGraphql(dataSource, params);
		}
		
		return list;
	}
	
	public Map<String, Object> get(Integer dataSourceId, Object[] params) {
		
		Map<String, Object> output = null;
		DataSource dataSource = dataSourceService.getById(dataSourceId);
		
		if (dataSource != null && dataSource.getDataSourceType().getId().equals(this.dataSourceTypeGraphqlQuery)) {
				
			List<Map<String, Object>> list = getListFromGraphql(dataSource, params);
			
			if (!CollectionUtils.isEmpty(list)) {
				
				output = list.get(0);
			}
		}
		
		return output;
	}
	
	public Map<String, Object> put(Integer dataSourceId, Map<String, Object> input) {
		
		Map<String, Object> output = null;
		DataSource dataSource = dataSourceService.getById(dataSourceId);
		
		if (dataSource != null && dataSource.getDataSourceType().getId().equals(this.dataSourceTypeGraphqlMutation)) {
				
			List<Map<String, Object>> list = putGraphql(dataSource, input);
			
			if (!CollectionUtils.isEmpty(list)) {
				
				output = list.get(0);
			}
		}
		
		return output;
	}
	
	private List<Map<String, Object>> putGraphql(DataSource dataSource, Map<String, Object> input) {
		
		List<Map<String, Object>> list = null;
		String mutationUrl = null;
		String method = null;
		String params = null;
		String paramTypes = null;
		String content = null;
		String paramsGraphQL = null;
		String paramTypesGraphQL = null;
		String variablesGraphQL = null;
		
		try {
			
			DataSourceValue dataSourceValMutationUrl = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationUrl)).findFirst().orElse(null);
			DataSourceValue dataSourceValMethod = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationMethod)).findFirst().orElse(null);
			DataSourceValue dataSourceValParams = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationParams)).findFirst().orElse(null);
			DataSourceValue dataSourceValParamTypes = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationParamTypes)).findFirst().orElse(null);
			DataSourceValue dataSourceValContent = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationContent)).findFirst().orElse(null);
			
			if (dataSourceValMutationUrl != null) mutationUrl = dataSourceValMutationUrl.getValue();
			if (dataSourceValMethod != null) method = dataSourceValMethod.getValue();
			if (dataSourceValParams != null) params = dataSourceValParams.getValue();
			if (dataSourceValParamTypes != null) paramTypes = dataSourceValParamTypes.getValue();
			if (dataSourceValContent != null) content = dataSourceValContent.getValue();
			
			if (!StringUtils.isEmpty(method) && !StringUtils.isEmpty(params) && !StringUtils.isEmpty(paramTypes)
					&& !StringUtils.isEmpty(content)) {
				
				Map<String, Object> inputForMutation = getInputForMutation(dataSource, input);
				
				if (!MapUtils.isEmpty(inputForMutation)) {
					
					paramsGraphQL = getParamsGraphqQLMutation(params);
					paramTypesGraphQL = getParamTypesGraphqQLMutation(params, paramTypes);
					variablesGraphQL = ConversionUtil.mapToString(inputForMutation);
					
					if (!StringUtils.isEmpty(paramsGraphQL) && !StringUtils.isEmpty(paramTypesGraphQL)
							&& !StringUtils.isEmpty(variablesGraphQL)) {

						list = graphqlAdapter.mutation(mutationUrl, method, content, paramsGraphQL, paramTypesGraphQL,
								variablesGraphQL);
					}
				}
			}
			
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (InterruptedException e1) {
			log.error(e1.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e1));
			Thread.currentThread().interrupt();
		}
		
		return list;
	}
	
	private List<Map<String, Object>> getListFromGraphql(DataSource dataSource, Object[] objParamValues) {
		
		List<Map<String, Object>> list = null;
		String queryUrl = null;
		String method = null;
		String params = null;
		String content = null;
		String paramsGraphQL = null;
		
		try {
			
			DataSourceValue dataSourceValQueryUrl = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlQueryUrl)).findFirst().orElse(null);
			DataSourceValue dataSourceValMethod = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlQueryMethod)).findFirst().orElse(null);
			DataSourceValue dataSourceValParams = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlQueryParams)).findFirst().orElse(null);
			DataSourceValue dataSourceValContent = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlQueryContent)).findFirst().orElse(null);
			
			if (dataSourceValQueryUrl != null) queryUrl = dataSourceValQueryUrl.getValue();
			if (dataSourceValMethod != null) method = dataSourceValMethod.getValue();
			if (dataSourceValParams != null) params = dataSourceValParams.getValue();
			if (dataSourceValContent != null) content = dataSourceValContent.getValue();
			
			if (!StringUtils.isEmpty(params) && !ArrayUtils.isEmpty(objParamValues)) {
				
				paramsGraphQL = getParamsGraphqQLQuery(params, objParamValues);
			}
			
			if (!StringUtils.isAllEmpty(method, content)) {
				
				list = graphqlAdapter.query(queryUrl, method, paramsGraphQL, content);
			}		
			
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} catch (InterruptedException e1) {
			log.error(e1.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e1));
			Thread.currentThread().interrupt();
		}
		
		return list; 
	}
	
	@SuppressWarnings("unchecked")
	private String getParamsGraphqQLQuery(String params, Object[] objParamValues) {
		
		String paramsGraphQL = null;
		String[] paramTypeArray = params.split(",");
		
		if (objParamValues.length == paramTypeArray.length) {
			
			List<String> paramTypeList = new ArrayList<>();
		
			for (int paramTypePos = 0; paramTypePos < objParamValues.length; paramTypePos++) {
				
				Object objValue = objParamValues[paramTypePos];
				String paramTypeGraphQL = "";
				
				if (objValue instanceof String) {
					
					paramTypeGraphQL = String.format("%s:\"%s\"", paramTypeArray[paramTypePos], (String) objValue);
				}
				else if (objValue instanceof List<?>) {
					
					List<?> strList = (List<?>) objValue;
					
					if (!CollectionUtils.isEmpty(strList)) {
					
						String strParam;
						
						if (((List<?>)objValue).get(0) instanceof String) {
							
							strParam = String.join(",", (List<String>) strList);
							strParam = String.format("\"%s\"", strParam.replace(",", "\",\""));
						}
						else {
							
							List<String> otherList = strList.stream().map(Object::toString).collect(Collectors.toList());
							strParam = String.join(",", otherList);
						}
						
						strParam = String.format("[%s]", strParam);
						
						paramTypeGraphQL = String.format("%s:%s", paramTypeArray[paramTypePos], strParam);
					}
				}
				else {
					
					paramTypeGraphQL = String.format("%s:%s", paramTypeArray[paramTypePos], objValue.toString());
				}
				
				paramTypeList.add(paramTypeGraphQL);
				paramsGraphQL = String.join(",", paramTypeList);
			}
		}
		
		return paramsGraphQL;
	}
	
	private String getParamsGraphqQLMutation(String params) {
		
		String[] paramsArray = params.split(",");
		String paramsGraphQL = null;
		
		if (paramsArray.length > 0) {
			
			List<String> paramList = new ArrayList<>();
			
			for (int i = 0; i < paramsArray.length; i++) {
				
				StringBuilder paramGraphQL = new StringBuilder();
				
				String param = paramsArray[i].trim();
				String paramName = "$" + param;
				
				paramGraphQL.append(param);
				paramGraphQL.append(": ");
				paramGraphQL.append(paramName);
				
				paramList.add(paramGraphQL.toString());
			}
			
			paramsGraphQL = String.join(",", paramList);
		}
		
		return paramsGraphQL;
	}
	
	private String getParamTypesGraphqQLMutation(String params, String paramTypes) {
		
		String[] paramsArray = params.split(",");
		String[] paramTypesArray = paramTypes.split(",");
		
		String paramsGraphQL = null;
		
		if (paramsArray.length > 0 && paramsArray.length == paramTypesArray.length) {
			
			List<String> paramList = new ArrayList<>();
			
			for (int i = 0; i < paramsArray.length; i++) {
				
				StringBuilder paramGraphQL = new StringBuilder();
				
				String paramName = "$" + paramsArray[i];
				String paramType = paramTypesArray[i].trim();
				
				
				paramGraphQL.append(paramName);
				paramGraphQL.append(": ");
				paramGraphQL.append(paramType);
				
				paramList.add(paramGraphQL.toString());
			}
			
			paramsGraphQL = String.join(",", paramList);
		}
		
		return paramsGraphQL;
	}
	
	private Map<String, Object> getInputForMutation(DataSource dataSource, Map<String, Object> input) {
		
		Map<String, Object> inputForMutation = input;
		
		if (dataSource.getId().equals(this.dataSourceUpdateUser)) {
			
			inputForMutation = graphqlTypeAdapter.getUserForMutation(input);
		}
		
		return inputForMutation;
	}
}
