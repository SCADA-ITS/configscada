package com.revenga.rits.front.openits.adapter.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	
	public static final String DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE = "data.source.get_elements_by_element_type";
	public static final String DATA_SOURCE_GET_ELEMENTS = "data.source.get_elements";
	public static final String DATA_SOURCE_GET_ELEMENT = "data.source.get_element";
	public static final String DATA_SOURCE_GET_ELEMENT_TYPES = "data.source.get_element_types";
	public static final String DATA_SOURCE_GET_ELEMENT_TYPE = "data.source.get_element_type";
	public static final String DATA_SOURCE_GET_GENERIC_DATAS = "data.source.get_generic_datas";
	public static final String DATA_SOURCE_GET_GENERIC_DATA = "data.source.get_generic_data";
	public static final String DATA_SOURCE_GET_USERS = "data.source.get_users";
	public static final String DATA_SOURCE_GET_USER = "data.source.get_user";
	public static final String DATA_SOURCE_GET_LOCALES = "data.source.get_locales";
	public static final String DATA_SOURCE_GET_LOCALE = "data.source.get_locale";
	public static final String DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE = "data.source.get_translations_by_locale";
	public static final String DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE = "data.source.get_translations_by_locale_and_module";
	public static final String DATA_SOURCE_GET_LABEL_TRANSLATED = "data.source.get_label_translated";
	public static final String DATA_SOURCE_GET_LABEL_TRANSLATED_USING_FIELDS = "data.source.get_label_translated_using_fields";
	public static final String DATA_SOURCE_UPDATE_USER = "data.source.update_user";
	
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
	private Integer dataSourceGetElementsByElementType;
	@Getter
	private Integer dataSourceGetElements;
	@Getter
	private Integer dataSourceGetElement;
	@Getter
	private Integer dataSourceGetElementTypes;
	@Getter
	private Integer dataSourceGetElementType;
	@Getter
	private Integer dataSourceGetGenericDatas;
	@Getter
	private Integer dataSourceGetGenericData;
	@Getter
	private Integer dataSourceGetUsers;
	@Getter
	private Integer dataSourceGetUser;
	@Getter
	private Integer dataSourceGetLocales;
	@Getter
	private Integer dataSourceGetLocale;
	@Getter
	private Integer dataSourceGetTranslationsByLocale;
	@Getter
	private Integer dataSourceGetTranslationsByLocaleAndModule;
	@Getter
	private Integer dataSourceGetLabelTranslated;
	@Getter
	private Integer dataSourceGetLabelTranslatedUsingFields;
	@Getter
	private Integer dataSourceUpdateUser;
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
	
		this.dataSourceGetElementsByElementType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE));
		this.dataSourceGetElements = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS));
		this.dataSourceGetElement = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT));
		this.dataSourceGetElementTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT_TYPES));
		this.dataSourceGetElementType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT_TYPE));
		this.dataSourceGetGenericDatas = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_GENERIC_DATAS));
		this.dataSourceGetGenericData = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_GENERIC_DATA));
		this.dataSourceGetUsers = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_USERS));
		this.dataSourceGetUser = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_USER));
		this.dataSourceGetLocales = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOCALES));
		this.dataSourceGetLocale = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LOCALE));
		this.dataSourceGetTranslationsByLocale = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE));
		this.dataSourceGetTranslationsByLocaleAndModule = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE));
		this.dataSourceGetLabelTranslated = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LABEL_TRANSLATED));
		this.dataSourceGetLabelTranslatedUsingFields = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_LABEL_TRANSLATED_USING_FIELDS));
		this.dataSourceUpdateUser = Integer.parseInt(env.getProperty(DATA_SOURCE_UPDATE_USER));
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
	
	public Map<String, Object> put(Integer dataSourceId, String input) {
		
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
	
	private List<Map<String, Object>> putGraphql(DataSource dataSource, String input) {
		
		List<Map<String, Object>> list = null;
		String method = null;
		String params = null;
		String paramTypes = null;
		String content = null;
		String paramsGraphQL = null;
		String paramTypesGraphQL = null;
		String variablesGraphQL = null;
		
		try {
			
		
			DataSourceValue dataSourceValMethod = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationMethod)).findFirst().orElse(null);
			DataSourceValue dataSourceValParams = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationParams)).findFirst().orElse(null);
			DataSourceValue dataSourceValParamTypes = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationParamTypes)).findFirst().orElse(null);
			DataSourceValue dataSourceValContent = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMutationContent)).findFirst().orElse(null);
			
			if (dataSourceValMethod != null) method = dataSourceValMethod.getValue();
			if (dataSourceValParams != null) params = dataSourceValParams.getValue();
			if (dataSourceValParamTypes != null) paramTypes = dataSourceValParamTypes.getValue();
			if (dataSourceValContent != null) content = dataSourceValContent.getValue();
			
			if (!StringUtils.isEmpty(method) && !StringUtils.isEmpty(params) && !StringUtils.isEmpty(paramTypes)
					&& !StringUtils.isEmpty(content)) {
			
				paramsGraphQL = getParamsGraphqQLMutation(params);
				paramTypesGraphQL = getParamTypesGraphqQLMutation(paramTypes);
				
				String inputForMutation = getInputForMutation(dataSource, input);
				
				if (!StringUtils.isEmpty(inputForMutation)) {
					
					variablesGraphQL = getVariablesGraphqQL(inputForMutation);
				}
				
				if (!StringUtils.isAllEmpty(paramsGraphQL, paramTypesGraphQL, variablesGraphQL)) {
				
					list = graphqlAdapter.mutation(method, content, paramsGraphQL, paramTypesGraphQL, variablesGraphQL);
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
		String method = null;
		String params = null;
		String content = null;
		String paramsGraphQL = null;
		
		try {
			
			DataSourceValue dataSourceValMethod = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlQueryMethod)).findFirst().orElse(null);
			DataSourceValue dataSourceValParams = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlQueryParams)).findFirst().orElse(null);
			DataSourceValue dataSourceValContent = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlQueryContent)).findFirst().orElse(null);
			
			if (dataSourceValMethod != null) method = dataSourceValMethod.getValue();
			if (dataSourceValParams != null) params = dataSourceValParams.getValue();
			if (dataSourceValContent != null) content = dataSourceValContent.getValue();
			
			if (!StringUtils.isEmpty(params) && !ArrayUtils.isEmpty(objParamValues)) {
				
				paramsGraphQL = getParamsGraphqQLQuery(params, objParamValues);
			}
			
			if (!StringUtils.isAllEmpty(method, content)) {
				
				list = graphqlAdapter.query(method, paramsGraphQL, content);
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
		
		String[] paramArray = params.split(",");
		String paramsGraphQL = null;
		
		if (paramArray.length > 0) {
			
			List<String> paramList = new ArrayList<>();
			
			for (int i = 0; i < paramArray.length; i++) {
				
				StringBuilder paramGraphQL = new StringBuilder();
				
				String param = paramArray[i].trim();
				String paramName = "$arg_" + (i + 1);
				
				paramGraphQL.append(param);
				paramGraphQL.append(": ");
				paramGraphQL.append(paramName);
				
				paramList.add(paramGraphQL.toString());
			}
			
			paramsGraphQL = String.join(",", paramList);
		}
		
		return paramsGraphQL;
	}
	
	private String getParamTypesGraphqQLMutation(String paramTypes) {
		
		String[] paramArray = paramTypes.split(",");
		String paramsGraphQL = null;
		
		if (paramArray.length > 0) {
			
			List<String> paramList = new ArrayList<>();
			
			for (int i = 0; i < paramArray.length; i++) {
				
				StringBuilder paramGraphQL = new StringBuilder();
				
				String paramName = "$arg_" + (i + 1);
				String paramType = paramArray[i].trim();
				
				
				paramGraphQL.append(paramName);
				paramGraphQL.append(": ");
				paramGraphQL.append(paramType);
				
				paramList.add(paramGraphQL.toString());
			}
			
			paramsGraphQL = String.join(",", paramList);
		}
		
		return paramsGraphQL;
	}
	
	private String getVariablesGraphqQL(String input) {
		
		String output = "";
		
//		ObjectMapper objectMapper = new ObjectMapper();

		try {
									
//			Map<String, Object> inputMap = objectMapper.readValue(input, new TypeReference<HashMap<String, Object>>() {});
			
			Map<String, Object> inputMap = ConversionUtil.stringToMap(input);
			JsonNode root = ConversionUtil.mapToJson("user", inputMap);
			inputMap = ConversionUtil.jsonToMap(root);
			
			if (!MapUtils.isEmpty(inputMap)) {
				
				int field = 1;
				JsonNode json =  ConversionUtil.stringToJson("{}");
				
				for (Map.Entry<String, Object> entry: inputMap.entrySet()) {
					
					String key = entry.getKey();
					
					JsonNode value = root.get(key);
					
					String fieldName = "arg_" + (field++);
					
					((ObjectNode) json).set(fieldName, value);
				}
				
				output = json.toString();
			}
			
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return output;
	}
	
	private String getInputForMutation(DataSource dataSource, String input) {
		
		String inputForMutation = input;
		
		if (dataSource.getId().equals(this.dataSourceUpdateUser)) {
			
			inputForMutation = graphqlTypeAdapter.getUserForMutation(input);
		}
		
		return inputForMutation;
	}
}
