package com.revenga.rits.front.openits.adapter.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.model.DataSourceValue;
import com.revenga.rits.front.openitsdao.service.DataSourceService;

import lombok.Getter;

@Component
public class VODataSourceAdapter {
	
	@Autowired
	DataSourceService dataSourceService;
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired
	private Environment env;
	
	public static final String DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE = "data.source.get_elements_by_element_type";
	public static final String DATA_SOURCE_GET_ELEMENT_TYPES = "data.source.get_element_types";
	public static final String DATA_SOURCE_TYPE_GRAPHQL = "data.source.type.graphql";
	public static final String DATA_SOURCE_GRAPHQL_URL = "data.source.graphql.url";
	public static final String DATA_SOURCE_GRAPHQL_METHOD = "data.source.graphql.method";
	public static final String DATA_SOURCE_GRAPHQL_PARAMS = "data.source.graphql.params";
	public static final String DATA_SOURCE_GRAPHQL_CONTENT = "data.source.graphql.content";
	
	@Getter
	private Integer dataSourceGetElementsByElementType;
	@Getter
	private Integer dataSourceGetElementTypes;
	@Getter
	private Integer dataSourceTypeGraphql;
	@Getter
	private Integer dataSourceGraphqlUrl;
	@Getter
	private Integer dataSourceGraphqlMethod;
	@Getter
	private Integer dataSourceGraphqlParams;
	@Getter
	private Integer dataSourceGraphqlContent;
	
	@PostConstruct
	private void initialize() {
	
		this.dataSourceGetElementsByElementType = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENTS_BY_ELEMENT_TYPE));
		this.dataSourceGetElementTypes = Integer.parseInt(env.getProperty(DATA_SOURCE_GET_ELEMENT_TYPES));
		this.dataSourceTypeGraphql = Integer.parseInt(env.getProperty(DATA_SOURCE_TYPE_GRAPHQL));
		this.dataSourceGraphqlUrl = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_URL));
		this.dataSourceGraphqlMethod = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_METHOD));
		this.dataSourceGraphqlParams = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_PARAMS));
		this.dataSourceGraphqlContent = Integer.parseInt(env.getProperty(DATA_SOURCE_GRAPHQL_CONTENT));
	}
	

	public List<Map<String, Object>> getList(Integer dataSourceId, Object[] params) {
		
		List<Map<String, Object>> list = null;
		DataSource dataSource = dataSourceService.getById(dataSourceId);
		
		if (dataSource != null && dataSource.getDataSourceType().getId().equals(this.dataSourceGetElementsByElementType)) {
				
			list = getListFromGraphql(dataSource, params);
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
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlMethod)).findFirst().orElse(null);
			DataSourceValue dataSourceValParams = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlParams)).findFirst().orElse(null);
			DataSourceValue dataSourceValContent = dataSource.getDataSourceValues().stream()
					.filter(e -> e.getDataSourceParam().getId().equals(this.dataSourceGraphqlContent)).findFirst().orElse(null);
			
			if (dataSourceValMethod != null) method = dataSourceValMethod.getValue();
			if (dataSourceValParams != null) params = dataSourceValParams.getValue();
			if (dataSourceValContent != null) content = dataSourceValContent.getValue();
			
			if (!StringUtils.isEmpty(params) && !ArrayUtils.isEmpty(objParamValues)) {
				
				paramsGraphQL = getParamsGraphqQL(params, objParamValues);
			}
			
			if (!StringUtils.isAllEmpty(method, content)) {
			
				list = graphqlAdapter.query(method, paramsGraphQL, content);
			}
			
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return list; 
	}
	
	private String getParamsGraphqQL(String params, Object[] objParamValues) {
		
		String paramsGraphQL = null;
		String[] paramArray = params.split(",");
		
		if (objParamValues.length == paramArray.length) {
			
			List<String> paramList = new ArrayList<>();
		
			for (int paramPos = 0; paramPos < objParamValues.length; paramPos++) {
				
				Object objValue = objParamValues[paramPos];
				StringBuilder paramGraphQL = new StringBuilder();
				
				if (objValue instanceof String) {
					
					paramGraphQL.append(paramArray[paramPos]);
					paramGraphQL.append(":");
					paramGraphQL.append("\"");
					paramGraphQL.append((String) objValue);
					paramGraphQL.append("\"");
				}
				else {
					
					paramGraphQL.append(paramArray[paramPos]);
					paramGraphQL.append(":");
					paramGraphQL.append(objValue.toString());
				}
				
				paramList.add(paramGraphQL.toString());
				paramsGraphQL = String.join(",", paramList);
			}
		}
		
		return paramsGraphQL;
	}
}
