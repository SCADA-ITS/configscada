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
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.util.ConfigProperty;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.model.DataSourceValue;
import com.revenga.rits.front.openitsdao.service.DataSourceService;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class VODataSourceAdapter {

	protected static final String FILE_DATASOURCES_CONSTANTS = "datasources.properties";
	
	private static Map<String, String> props = ConfigProperty.getInstance().getProperties(FILE_DATASOURCES_CONSTANTS);
	
	public static final int DATA_SOURCE_GET_ALARMS = getPropertyInt("get_alarms");
	public static final int DATA_SOURCE_GET_ALARMS_ARRAY = getPropertyInt("get_alarms_array");
	public static final int DATA_SOURCE_GET_ALARMS_BY_ALARM_TYPE = getPropertyInt("get_alarms_by_alarm_type");
	public static final int DATA_SOURCE_GET_ALARMS_BY_SOURCE = getPropertyInt("get_alarms_by_source");
	public static final int DATA_SOURCE_GET_ALARMS_BY_SOURCE_AND_ALARM_TYPE = getPropertyInt("get_alarms_by_source_and_alarm_type");
	public static final int DATA_SOURCE_GET_ALARM = getPropertyInt("get_alarm");
	public static final int DATA_SOURCE_GET_ALARM_TYPES = getPropertyInt("get_alarm_types");
	public static final int DATA_SOURCE_GET_ALARM_TYPE = getPropertyInt("get_alarm_type");
	public static final int DATA_SOURCE_GET_ALARM_LEVELS = getPropertyInt("get_alarm_levels");
	public static final int DATA_SOURCE_GET_ALARM_LEVEL = getPropertyInt("get_alarm_level");
	public static final int DATA_SOURCE_GET_ALARM_CONFIGS = getPropertyInt("get_alarm_configs");
	public static final int DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_TYPE = getPropertyInt("get_alarm_configs_by_alarm_type");
	public static final int DATA_SOURCE_GET_ALARM_CONFIGS_BY_ALARM_LEVEL = getPropertyInt("get_alarm_configs_by_alarm_level");
	public static final int DATA_SOURCE_GET_ALARM_CONFIG = getPropertyInt("get_alarm_config");
	public static final int DATA_SOURCE_GET_THRESOLD_OPERATIONS = getPropertyInt("get_threshold_operations");
	public static final int DATA_SOURCE_GET_THRESOLD_OPERATION = getPropertyInt("get_threshold_operation");
	public static final int DATA_SOURCE_GET_ALARM_MUTEDS = getPropertyInt("get_alarm_muteds");
	public static final int DATA_SOURCE_GET_ALARM_MUTED = getPropertyInt("get_alarm_muted");
	public static final int DATA_SOURCE_GET_ALARM_MUTEDS_BY_ALARM_CONFIG = getPropertyInt("get_alarm_muteds_by_alarm_config");
	public static final int DATA_SOURCE_GET_ALARM_MUTEDS_BY_ELEMENT = getPropertyInt("get_alarm_muteds_by_element");
	public static final int DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE = getPropertyInt("get_elements_by_element_type");
	public static final int DATA_SOURCE_GET_ELEMENTS = getPropertyInt("get_elements");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY = getPropertyInt("get_elements_array");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_SYNOPTIC = getPropertyInt("get_elements_array_states_synoptic");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_SYNOPTIC = getPropertyInt("get_elements_array_states_measures_synoptic");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_ALARMS_SYNOPTIC = getPropertyInt("get_elements_array_states_alarms_synoptic");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_SYNOPTIC = getPropertyInt("get_elements_array_states_measures_alarms_synoptic");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MAP = getPropertyInt("get_elements_array_states_map");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_MAP = getPropertyInt("get_elements_array_states_measures_map");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_ALARMS_MAP = getPropertyInt("get_elements_array_states_alarms_map");
	public static final int DATA_SOURCE_GET_ELEMENTS_ARRAY_STATES_MEASURES_ALARMS_MAP = getPropertyInt("get_elements_array_states_measures_alarms_map");
	public static final int DATA_SOURCE_GET_ELEMENT = getPropertyInt("get_element");
	public static final int DATA_SOURCE_GET_ELEMENT_TYPES = getPropertyInt("get_element_types");
	public static final int DATA_SOURCE_GET_ELEMENT_TYPE = getPropertyInt("get_element_type");
	public static final int DATA_SOURCE_GET_ELEMENT_VALUES_STATES = getPropertyInt("get_element_values_states");
	public static final int DATA_SOURCE_GET_GENERIC_DATAS = getPropertyInt("get_generic_datas");
	public static final int DATA_SOURCE_GET_GENERIC_DATA = getPropertyInt("get_generic_data");
	public static final int DATA_SOURCE_GET_LABEL_TRANSLATED = getPropertyInt("get_label_translated");
	public static final int DATA_SOURCE_GET_LABEL_TRANSLATED_USING_FIELDS = getPropertyInt("get_label_translated_using_fields");
	public static final int DATA_SOURCE_GET_LOCALES = getPropertyInt("get_locales");
	public static final int DATA_SOURCE_GET_LOCALE = getPropertyInt("get_locale");
	public static final int DATA_SOURCE_GET_LOCATIONS = getPropertyInt("get_locations");
	public static final int DATA_SOURCE_GET_LOCATION_BY_STRETCH = getPropertyInt("get_location_by_stretch");
	public static final int DATA_SOURCE_GET_LOCATION = getPropertyInt("get_location");
	public static final int DATA_SOURCE_GET_ROADS = getPropertyInt("get_roads");
	public static final int DATA_SOURCE_GET_ROAD = getPropertyInt("get_road");
	public static final int DATA_SOURCE_GET_STRETCHS = getPropertyInt("get_stretchs");
	public static final int DATA_SOURCE_GET_STRETCH = getPropertyInt("get_stretch");
	public static final int DATA_SOURCE_GET_STRETCH_BY_ROAD = getPropertyInt("get_stretch_by_road");
	public static final int DATA_SOURCE_GET_PLANS = getPropertyInt("get_plans");
	public static final int DATA_SOURCE_GET_PLANS_SUMMARY = getPropertyInt("get_plans_summary");
	public static final int DATA_SOURCE_GET_PLAN = getPropertyInt("get_plan");
	public static final int DATA_SOURCE_GET_PLAN_TYPES = getPropertyInt("get_plan_types");
	public static final int DATA_SOURCE_GET_PLAN_TYPE = getPropertyInt("get_plan_type");
	public static final int DATA_SOURCE_GET_PLAN_SUBTYPES = getPropertyInt("get_plan_subtypes");
	public static final int DATA_SOURCE_GET_PLAN_SUBTYPE = getPropertyInt("get_plan_subtype");
	public static final int DATA_SOURCE_GET_PLAN_ORDER_TYPES = getPropertyInt("get_plan_order_types");
	public static final int DATA_SOURCE_GET_PLAN_ORDER_TYPE = getPropertyInt("get_plan_order_type");
	public static final int DATA_SOURCE_GET_PLAN_STATE_TYPES = getPropertyInt("get_plan_state_types");
	public static final int DATA_SOURCE_GET_PLAN_ORDER_STATE_TYPES = getPropertyInt("get_plan_order_state_types");
	public static final int DATA_SOURCE_GET_PLAN_SCHEDULE_STATE_TYPES = getPropertyInt("get_plan_schedule_state_types");
	public static final int DATA_SOURCE_GET_PLAN_SCHEDULES = getPropertyInt("get_plan_schedules");
	public static final int DATA_SOURCE_GET_PLAN_SCHEDULE = getPropertyInt("get_plan_schedule");
	public static final int DATA_SOURCE_GET_PLAN_SCHEDULE_BY_PLAN = getPropertyInt("get_plan_schedule_by_plan");
	public static final int DATA_SOURCE_GET_PLAN_ORDERS = getPropertyInt("get_plan_orders");
	public static final int DATA_SOURCE_GET_PLAN_ORDER = getPropertyInt("get_plan_order");
	public static final int DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE = getPropertyInt("get_translations_by_locale");
	public static final int DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE = getPropertyInt("get_translations_by_locale_and_module");
	public static final int DATA_SOURCE_GET_USERS = getPropertyInt("get_users");
	public static final int DATA_SOURCE_GET_USERS_WITH_ROLE = getPropertyInt("get_users_with_role");
	public static final int DATA_SOURCE_GET_USER = getPropertyInt("get_user");
	public static final int DATA_SOURCE_GET_ROLES = getPropertyInt("get_roles");
	public static final int DATA_SOURCE_GET_ROLE = getPropertyInt("get_role");
	public static final int DATA_SOURCE_GET_SYSTEM_PERMISSIONS = getPropertyInt("get_system_permissions");
	public static final int DATA_SOURCE_GET_SYSTEM_PERMISSION = getPropertyInt("get_system_permission");
	public static final int DATA_SOURCE_GET_INCIDENT_GROUPS = getPropertyInt("get_incident_groups");
	public static final int DATA_SOURCE_GET_INCIDENT_GROUP = getPropertyInt("get_incident_group");
	public static final int DATA_SOURCE_GET_INCIDENT_REPORTS = getPropertyInt("get_incident_reports");
	public static final int DATA_SOURCE_GET_INCIDENT_REPORT = getPropertyInt("get_incident_report");
	public static final int DATA_SOURCE_GET_INCIDENT_TYPES = getPropertyInt("get_incident_types");
	public static final int DATA_SOURCE_GET_INCIDENT_TYPE = getPropertyInt("get_incident_type");
	public static final int DATA_SOURCE_GET_INCIDENT_TYPE_STATES = getPropertyInt("get_incident_type_states");
	public static final int DATA_SOURCE_GET_NEXT_STATE = getPropertyInt("get_next_state");
	public static final int DATA_SOURCE_GET_ROAD_IMPACTS = getPropertyInt("get_road_impacts");
	public static final int DATA_SOURCE_GET_ROAD_IMPACT = getPropertyInt("get_road_impact");
	public static final int DATA_SOURCE_GET_INIT_ROAD_IMPACTS = getPropertyInt("get_init_road_impacts");
	public static final int DATA_SOURCE_GET_ROAD_IMPACTS_BY_INCIDENT = getPropertyInt("get_road_impacts_by_incident");
	public static final int DATA_SOURCE_GET_INCIDENTS_SOURCE = getPropertyInt("get_incidents_source");
	public static final int DATA_SOURCE_GET_INCIDENT_SOURCE = getPropertyInt("get_incident_source");
	public static final int DATA_SOURCE_GET_EXT_INCIDENTS = getPropertyInt("get_ext_incidents");
	public static final int DATA_SOURCE_GET_EXT_INCIDENT = getPropertyInt("get_ext_incident");
	public static final int DATA_SOURCE_GET_EXT_INCIDENT_TYPES = getPropertyInt("get_ext_incident_types");
	public static final int DATA_SOURCE_GET_EXT_INCIDENT_TYPE = getPropertyInt("get_ext_incident_type");
	public static final int DATA_SOURCE_GET_INCIDENT_DOCUMENTS = getPropertyInt("get_incident_documents");
	public static final int DATA_SOURCE_GET_INCIDENT_DOCUMENT = getPropertyInt("get_incident_document");
	public static final int DATA_SOURCE_GET_INCIDENT_DOCUMENT_TYPES = getPropertyInt("get_incident_document_types");
	public static final int DATA_SOURCE_GET_INCIDENT_DOCUMENT_TYPE = getPropertyInt("get_incident_document_type");
	public static final int DATA_SOURCE_GET_LOG_TYPES = getPropertyInt("get_log_types");
	public static final int DATA_SOURCE_GET_LOG_TYPE = getPropertyInt("get_log_type");
	public static final int DATA_SOURCE_GET_INCIDENT_STATES = getPropertyInt("get_incident_states");
	public static final int DATA_SOURCE_GET_INCIDENT_STATE = getPropertyInt("get_incident_state");
	public static final int DATA_SOURCE_GET_INCIDENT_LEVELS = getPropertyInt("get_incident_levels");
	public static final int DATA_SOURCE_GET_INCIDENT_LEVEL = getPropertyInt("get_incident_level");
	public static final int DATA_SOURCE_GET_TASK_TYPES = getPropertyInt("get_task_types");
	public static final int DATA_SOURCE_GET_TASK_TYPE = getPropertyInt("get_task_type");
	public static final int DATA_SOURCE_GET_INCIDENT_TASK_PLANS = getPropertyInt("get_incident_task_plans");
	public static final int DATA_SOURCE_GET_TASK_PLANS_BY_STRETCH_AND_INCIDENT_TYPE_AND_INCIDENT_TYPE_TASK = getPropertyInt("get_task_plans_by_stretch_and_incident_type_and_incident_type_task");
	public static final int DATA_SOURCE_GET_INCIDENT_TYPE_TASKS = getPropertyInt("get_incident_type_tasks");
	public static final int DATA_SOURCE_GET_INCIDENT_TYPE_TASK_BY_INCIDENT_TYPE_AND_INCIDENT_STATE = getPropertyInt("get_incident_type_task_by_incident_type_and_incident_state");
	public static final int DATA_SOURCE_GET_INCIDENT_INCIDENT_REPORT_SUMMARY = getPropertyInt("get_incident_report_summary");
	public static final int DATA_SOURCE_ADD_LOG_INCIDENT_INCIDENT_REPORT = getPropertyInt("add_log_incident_report");
	public static final int DATA_SOURCE_DELETE_LOG_INCIDENT_INCIDENT_REPORT = getPropertyInt("delete_log_incident_report");
	public static final int DATA_SOURCE_GET_INCIDENT_TYPE_STRETCHS = getPropertyInt("get_incident_type_stretchs");
	public static final int DATA_SOURCE_CREATE_INCIDENT_REPORT = getPropertyInt("create_incident_report");
	
	public static final int DATA_SOURCE_CREATE_USER = getPropertyInt("create_user");
	public static final int DATA_SOURCE_UPDATE_USER = getPropertyInt("update_user");
	public static final int DATA_SOURCE_DELETE_USER = getPropertyInt("delete_user");
	public static final int DATA_SOURCE_CREATE_ROLE = getPropertyInt("create_role");
	public static final int DATA_SOURCE_UPDATE_ROLE = getPropertyInt("update_role");
	public static final int DATA_SOURCE_DELETE_ROLE = getPropertyInt("delete_role");
	public static final int DATA_SOURCE_CREATE_ALARM_CONFIG = getPropertyInt("create_alarm_config");
	public static final int DATA_SOURCE_UPDATE_ALARM_CONFIG = getPropertyInt("update_alarm_config");
	public static final int DATA_SOURCE_DELETE_ALARM_CONFIG = getPropertyInt("delete_alarm_config");
	public static final int DATA_SOURCE_CREATE_ELEMENT = getPropertyInt("create_element");
	public static final int DATA_SOURCE_UPDATE_ELEMENT = getPropertyInt("update_element");
	public static final int DATA_SOURCE_CLONE_ELEMENT = getPropertyInt("clone_element");
	public static final int DATA_SOURCE_DELETE_ELEMENT = getPropertyInt("delete_element");
	public static final int DATA_SOURCE_CREATE_PLAN = getPropertyInt("create_plan");
	public static final int DATA_SOURCE_UPDATE_PLAN = getPropertyInt("update_plan");
	public static final int DATA_SOURCE_DELETE_PLAN = getPropertyInt("delete_plan");
	public static final int DATA_SOURCE_CREATE_PLAN_ORDER = getPropertyInt("create_plan_order");
	public static final int DATA_SOURCE_UPDATE_PLAN_ORDER = getPropertyInt("update_plan_order");
	public static final int DATA_SOURCE_DELETE_PLAN_ORDER = getPropertyInt("delete_plan_order");
	public static final int DATA_SOURCE_CREATE_PLAN_TYPE = getPropertyInt("create_plan_type");
	public static final int DATA_SOURCE_UPDATE_PLAN_TYPE = getPropertyInt("update_plan_type");
	public static final int DATA_SOURCE_DELETE_PLAN_TYPE = getPropertyInt("delete_plan_type");
	public static final int DATA_SOURCE_CREATE_PLAN_SUBTYPE = getPropertyInt("create_plan_subtype");
	public static final int DATA_SOURCE_UPDATE_PLAN_SUBTYPE = getPropertyInt("update_plan_subtype");
	public static final int DATA_SOURCE_DELETE_PLAN_SUBTYPE = getPropertyInt("delete_plan_subtype");
	public static final int DATA_SOURCE_SEND_AUDIT_LOGS = getPropertyInt("send_audit_logs");
	public static final int DATA_SOURCE_SEND_RECOGNIZE_AUDIT_LOGS = getPropertyInt("send_recognize_audit_logs");
	public static final int DATA_SOURCE_SEND_PLAN_SCHEDULE_ACTIONS = getPropertyInt("send_plan_schedule_actions");
	public static final int DATA_SOURCE_SEND_PLAY_PLAN = getPropertyInt("send_play_plan");
	public static final int DATA_SOURCE_SEND_ALARM_MUTED_ACTIONS = getPropertyInt("send_alarm_muted_actions");
	public static final int DATA_SOURCE_SEND_ALARMS= getPropertyInt("send_alarms");
	public static final int DATA_SOURCE_SEND_RECOGNIZE_ALARM = getPropertyInt("send_recognize_alarm");
	public static final int DATA_SOURCE_SEND_DEACTIVATION_ALARMS = getPropertyInt("send_deactivation_alarms");
	public static final int DATA_SOURCE_SEND_KILL_USER = getPropertyInt("send_kill_user");
	public static final int DATA_SOURCE_SEND_COMMAND_ELEMENT = getPropertyInt("send_command_element");

	public static final int DATA_SOURCE_TYPE_GRAPHQL_QUERY = getPropertyInt("type.graphql_query");
	public static final int DATA_SOURCE_TYPE_GRAPHQL_MUTATION = getPropertyInt("type.graphql_mutation");
	public static final int DATA_SOURCE_GRAPHQL_QUERY_URL = getPropertyInt("graphql_query.url");
	public static final int DATA_SOURCE_GRAPHQL_QUERY_METHOD = getPropertyInt("graphql_query.method");
	public static final int DATA_SOURCE_GRAPHQL_QUERY_PARAMS = getPropertyInt("graphql_query.params");
	public static final int DATA_SOURCE_GRAPHQL_QUERY_CONTENT = getPropertyInt("graphql_query.content");
	public static final int DATA_SOURCE_GRAPHQL_MUTATION_URL = getPropertyInt("graphql_mutation.url");
	public static final int DATA_SOURCE_GRAPHQL_MUTATION_METHOD = getPropertyInt("graphql_mutation.method");
	public static final int DATA_SOURCE_GRAPHQL_MUTATION_PARAMS = getPropertyInt("graphql_mutation.params");
	public static final int DATA_SOURCE_GRAPHQL_MUTATION_PARAM_TYPES = getPropertyInt("graphql_mutation.param_types");
	public static final int DATA_SOURCE_GRAPHQL_MUTATION_CONTENT = getPropertyInt("graphql_mutation.content");
	
	public static final int DATA_SOURCE_SEND_CONFIRM_INCIDENT_REPORT = getPropertyInt("send_confirm_incident_report");
	public static final int DATA_SOURCE_SEND_FORWARD_INCIDENT_REPORT = getPropertyInt("send_forward_incident_report");
	public static final int DATA_SOURCE_SEND_CANCEL_INCIDENT_REPORT = getPropertyInt("send_cancel_incident_report");
	public static final int DATA_SOURCE_SEND_ASSING_USER_INCIDENT_REPORT = getPropertyInt("send_assign_user_incident_report");
	public static final int DATA_SOURCE_SEND_FINALIZE_TASK_INCIDENT_REPORT = getPropertyInt("send_finalize_task_incident_report");
	public static final int DATA_SOURCE_SEND_EXECUTE_TASK_PLAN_INCIDENT_REPORT = getPropertyInt("send_execute_task_plan_incident_report");

	public static final int DATA_SOURCE_SEND_ASSING_CAUSE_INCIDENT_REPORT = getPropertyInt("send_assign_cause_incident_report");



	public static final int DATA_SOURCE_GET_SYSTEM_PARAMS = getPropertyInt("get_system_params");
	public static final int DATA_SOURCE_GET_HIERARCHIES = getPropertyInt("get_hierarchies");
	
	private static int getPropertyInt(final String prop) {
		
		if (!MapUtils.isEmpty(props)) {
			
			String value = props.get(prop);
			
			if (!StringUtils.isEmpty(value)) {
				
				return Integer.parseInt(value);
			}
		}
		
		return -1;
	}
	
	@Autowired
	@Getter
	DataSourceService dataSourceService;

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@Autowired
	GraphqlTypeAdapter graphqlTypeAdapter;
	
	@Autowired
	CacheManager cacheManager;

	@PostConstruct
	private void initialize() {


	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getList(Integer dataSourceId, Object[] params) {

		List<Map<String, Object>> list = null;
		DataSource dataSource = null;

		if (dataSourceId != null) {

			if (params == null) {
				
				list = (List<Map<String, Object>>) cacheManager.getEntitiesCached(dataSourceId);
			}
			
			if (list == null) {
				
				dataSource = dataSourceService.getById(dataSourceId);
				
				if (dataSource != null && dataSource.getDataSourceType().getId().equals(DATA_SOURCE_TYPE_GRAPHQL_QUERY)) {
				
					list = getListFromGraphql(dataSource, params);
				}
			}
		}

		return list;
	}

	public Map<String, Object> get(Integer dataSourceId, Object[] params) {

		Map<String, Object> output = null;
		DataSource dataSource = dataSourceService.getById(dataSourceId);

		if (dataSource != null && dataSource.getDataSourceType().getId().equals(DATA_SOURCE_TYPE_GRAPHQL_QUERY)) {

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

		if (dataSource != null && dataSource.getDataSourceType().getId().equals(DATA_SOURCE_TYPE_GRAPHQL_MUTATION)) {

			List<Map<String, Object>> list = putGraphql(dataSource, input);

			if (!CollectionUtils.isEmpty(list)) {

				output = list.get(0);
			}
		}

		return output;
	}
	
	public List<Map<String, Object>> putList(Integer dataSourceId, Map<String, Object> input) {

		DataSource dataSource = dataSourceService.getById(dataSourceId);
		List<Map<String, Object>> list = null;

		if (dataSource != null && dataSource.getDataSourceType().getId().equals(DATA_SOURCE_TYPE_GRAPHQL_MUTATION)) {

			list = putGraphql(dataSource, input);	
		}

		return list;		
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
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_MUTATION_URL)).findFirst()
					.orElse(null);
			DataSourceValue dataSourceValMethod = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_MUTATION_METHOD))
					.findFirst().orElse(null);
			DataSourceValue dataSourceValParams = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_MUTATION_PARAMS))
					.findFirst().orElse(null);
			DataSourceValue dataSourceValParamTypes = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_MUTATION_PARAM_TYPES))
					.findFirst().orElse(null);
			DataSourceValue dataSourceValContent = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_MUTATION_CONTENT))
					.findFirst().orElse(null);

			if (dataSourceValMutationUrl != null)
				mutationUrl = dataSourceValMutationUrl.getValue();
			if (dataSourceValMethod != null)
				method = dataSourceValMethod.getValue();
			if (dataSourceValParams != null)
				params = dataSourceValParams.getValue();
			if (dataSourceValParamTypes != null)
				paramTypes = dataSourceValParamTypes.getValue();
			if (dataSourceValContent != null)
				content = dataSourceValContent.getValue();

			if (!StringUtils.isEmpty(method) && !StringUtils.isEmpty(params) && !StringUtils.isEmpty(paramTypes)) {

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
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_QUERY_URL)).findFirst()
					.orElse(null);
			DataSourceValue dataSourceValMethod = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_QUERY_METHOD)).findFirst()
					.orElse(null);
			DataSourceValue dataSourceValParams = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_QUERY_PARAMS)).findFirst()
					.orElse(null);
			DataSourceValue dataSourceValContent = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(DATA_SOURCE_GRAPHQL_QUERY_CONTENT)).findFirst()
					.orElse(null);

			if (dataSourceValQueryUrl != null)
				queryUrl = dataSourceValQueryUrl.getValue();
			if (dataSourceValMethod != null)
				method = dataSourceValMethod.getValue();
			if (dataSourceValParams != null)
				params = dataSourceValParams.getValue();
			if (dataSourceValContent != null)
				content = dataSourceValContent.getValue();

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

				} else if (objValue instanceof List<?>) {

					List<?> strList = (List<?>) objValue;

					if (!CollectionUtils.isEmpty(strList)) {

						String strParam;

						if (((List<?>) objValue).get(0) instanceof String) {

							strParam = String.join(",", (List<String>) strList);
							strParam = String.format("\"%s\"", strParam.replace(",", "\",\""));
						} else {

							List<String> otherList = strList.stream().map(Object::toString)
									.collect(Collectors.toList());
							strParam = String.join(",", otherList);
						}

						strParam = String.format("[%s]", strParam);

						paramTypeGraphQL = String.format("%s:%s", paramTypeArray[paramTypePos], strParam);
					}
				} else {

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

		if (dataSource.getId().equals(DATA_SOURCE_UPDATE_USER)) {

			inputForMutation = graphqlTypeAdapter.getUserForMutation(input);
		}

		return inputForMutation;
	}
}
