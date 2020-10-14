package com.revenga.rits.front.openits.adapter.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.revenga.rits.front.openits.util.ConversionUtil;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GraphqlAdapter {

private static final String GRAPHQL_URL = "graphql.url";
	
	@Autowired
	private Environment env;
	
	public List<Map<String, Object>> query(String method, String params, String content) throws IOException, InterruptedException {
		
		HttpResponse<String> response = null;
		
		try {
			
			StringBuilder sb = new StringBuilder();
			
			if (!StringUtils.isAllEmpty(method, content)) {
			
				sb.append("{\"query\":\"query{");
				
				sb.append(method);
				if (!StringUtils.isEmpty(params)) {
				
					String paramsAux = params.replace("\"", "\\\"");
					
					if (!StringUtils.isEmpty(paramsAux)) {
						sb.append("(" + paramsAux + ")");
					}
				}

				sb.append(content);
				sb.append("}\"}");
				
				String body = sb.toString();
				//String body1 = "{\"query\":\"query  {getElementsByType(elementTypeId:\\\"ElementType:2\\\"){id alias description state}}\"}";
				
				String graphqlUrl = env.getProperty(GRAPHQL_URL); 
				
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(graphqlUrl))
						.header("content-type", "application/json")
						.method("POST", HttpRequest.BodyPublishers.ofString(body)).build();
				
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			}
			
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw e;
		} 
		
		return response != null ? response2VO(response.body(), method) : null;
	}
	
	public List<Map<String, Object>> mutation(String method, String content, String paramsGraphQL, String paramTypesGraphQL, String variablesGraphQL) throws IOException, InterruptedException {
		
		HttpResponse<String> response = null;
		
		try {
			
			StringBuilder sb = new StringBuilder();
			
			if (!StringUtils.isAllEmpty(method, content)) {
			
				sb.append("{\"query\":\"mutation  ");
				
				sb.append(String.format("%s(%s)", method, paramTypesGraphQL));
				sb.append(String.format("{ %s(%s)  ", method, paramsGraphQL));
				sb.append(String.format(" %s}\"", content));
				
				sb.append(String.format(",\"variables\":%s", variablesGraphQL));
				sb.append(String.format(",\"operationName\":\"%s\"}", method));

				String body = sb.toString();
				//String body1 = "{\"query\":\"mutation  updateUser($arg_1: UserVO!){ updateUser(user: $arg_1)   {id name password passwordExpiredAt sessionExpireTimeS passwordExpiredAt lockedUntil preset fullName language email role{id alias rolePermissions{id alias}} userPermissions{id alias}}}\",\"variables\":{\"arg_1\":{\"id\":\"User:2\",\"name\":\"oper\",\"password\":\"oper\",\"preset\":false,\"allowMultipleSessions\":true,\"isAdmin\":false,\"language\":\"bo\",\"country\":\"Nepal\",\"timezone\":\"Asia/Nepal\",\"roleId\":\"Role:1\"}},\"operationName\":\"updateUser\"}";
				
				String graphqlUrl = env.getProperty(GRAPHQL_URL); 
				
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(graphqlUrl))
						.header("content-type", "application/json")
						.method("POST", HttpRequest.BodyPublishers.ofString(body)).build();
				
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			}
			
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw e;
		} 
		
		return response != null ? response2VO(response.body(), method) : null;
	}
	
	
	private List<Map<String, Object>> response2VO(String response, String method) throws IOException {
		
		List<Map<String, Object>> list = null;
		
		try {
			
//			ObjectMapper mapper = new ObjectMapper();
			JsonNode root;
			root = ConversionUtil.stringToJson(response);
			JsonNode data = root.path("data").path(method);
			
			if (data.isArray()) {
			
				//list = mapper.readValue(data.toString(), mapper.getTypeFactory().constructCollectionType(List.class, Map.class));
				
				list = ConversionUtil.stringToListMap(data.toString());
			}
			else {
				
				list = new ArrayList<>();
				
				//list.add(mapper.readValue(data.toString(), new TypeReference<HashMap<String, Object>>() {}));
				
				list.add(ConversionUtil.stringToMap(data.toString()));
			}
			
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw e;
		}
		
		return list;
	}
}
