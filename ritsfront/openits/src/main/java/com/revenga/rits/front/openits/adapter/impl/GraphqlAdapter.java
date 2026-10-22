package com.revenga.rits.front.openits.adapter.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.service.user.SessionService;
import com.revenga.rits.front.openits.util.ConversionUtil;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GraphqlAdapter {

	private static final String GRAPHQL_DEBUG = "graphql.debug";
	
	@Autowired
	private Environment env;
	
	@Autowired SessionService sessionService;
	
	boolean graphqlDebug;
	
	@PostConstruct
	private void initialize() {
		
		this.graphqlDebug = Boolean.parseBoolean(env.getProperty(GRAPHQL_DEBUG));
	}
	
	public List<Map<String, Object>> query(String graphqlUrl, String method, String params, String content) throws IOException, InterruptedException {
		
		HttpResponse<String> response = null;
		
		try {
			
			StringBuilder sb = new StringBuilder();
			
			if (!StringUtils.isEmpty(method)) {
			
				sb.append("{\"query\":\"query{");
				
				sb.append(method);
				if (!StringUtils.isEmpty(params)) {
				
					String paramsAux = params.replace("\"", "\\\"");
					
					if (!StringUtils.isEmpty(paramsAux)) {
						sb.append("(" + paramsAux + ")");
					}
				}

				if (!StringUtils.isEmpty(content)) {
				
					String strContent = content.replaceAll("[\r\n]+", " ");
					sb.append(strContent);
				}
				
				sb.append("}\"}");
				
				String body = sb.toString();
				//String body1 = "{\"query\":\"query  {getElementsByType(elementTypeId:\\\"ElementType:2\\\"){id alias description state}}\"}";
				
				//String graphqlUrl = env.getProperty(GRAPHQL_URL); 
				
				//String test = "{\"query\":\"query   {\\n  getHistoricalElementStates(params:[{columnName: \\\"element_type_id\\\", operation: EQUALS, value: \\\"9\\\"}])\\n  {   element {     id   }   currentElementTypeState {     id   }   createdAt }\\n}\"}";
				
				HttpRequest request = getRequest(graphqlUrl, body);
				
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				
				traceGraphQL(body, response);
			}
			
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw e;
		} 
		
		return response != null ? response2VO(response.body(), method) : null;
	}
	
	public List<Map<String, Object>> mutation(String graphqlUrl, String method, String content, String paramsGraphQL, String paramTypesGraphQL, String variablesGraphQL) throws IOException, InterruptedException {
		
		HttpResponse<String> response = null;
		
		try {
			
			StringBuilder sb = new StringBuilder();
			
			if (!StringUtils.isEmpty(method)) {

				sb.append("{\"query\":\"mutation  ");
				
				sb.append(String.format("%s(%s)", method, paramTypesGraphQL));
				sb.append(String.format("{ %s(%s)  ", method, paramsGraphQL));
				
				if (!StringUtils.isEmpty(content)) {
				
					String strContent = content.replaceAll("[\r\n]+", " ");
					
					sb.append(String.format(" %s", strContent));
				}
				 
				sb.append("}\"");
				sb.append(String.format(",\"variables\":%s", variablesGraphQL));
				sb.append(String.format(",\"operationName\":\"%s\"}", method));

				String body = sb.toString();
				//String body1 = "{\"query\":\"mutation  updateUser($arg_1: UserVO!){ updateUser(user: $arg_1)   {id name password passwordExpiredAt sessionExpireTimeS passwordExpiredAt lockedUntil preset fullName language email role{id alias rolePermissions{id alias}} userPermissions{id alias}}}\",\"variables\":{\"arg_1\":{\"id\":\"User:2\",\"name\":\"oper\",\"password\":\"oper\",\"preset\":false,\"allowMultipleSessions\":true,\"isAdmin\":false,\"language\":\"bo\",\"country\":\"Nepal\",\"timezone\":\"Asia/Nepal\",\"roleId\":\"Role:1\"}},\"operationName\":\"updateUser\"}";
				
				//String graphqlUrl = env.getProperty(GRAPHQL_URL); 
				
				HttpRequest request = getRequest(graphqlUrl, body);
				
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				
				traceGraphQL(body, response);
			}
			
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw e;
		} 
		
		return response != null ? response2VO(response.body(), method) : null;
	}
	
	private HttpRequest getRequest(String graphqlUrl, String body) {
		
		HttpRequest request = null;
		HttpSession httpSession = null;
		
		try {
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			httpSession = attr.getRequest().getSession(true);
			
			if (httpSession != null) {
				
				UserSessionUI userSession = sessionService.getSession(httpSession.getId());

				if (userSession != null && !StringUtils.isEmpty(userSession.getManagementAreaId())) {
					
					request = HttpRequest.newBuilder().uri(URI.create(graphqlUrl))
							.headers("content-type", "application/json",
									 "management-area", userSession.getManagementAreaId())
							.method("POST", HttpRequest.BodyPublishers.ofString(body)).build();
				}
			}
			
		} catch (IllegalStateException e) {
			
		}
		
		if (request == null) {
			
			request = HttpRequest.newBuilder().uri(URI.create(graphqlUrl))
					.header("content-type", "application/json")
					.method("POST", HttpRequest.BodyPublishers.ofString(body)).build();
		}
		
		return request;
	}
	
	
	private List<Map<String, Object>> response2VO(String response, String method) throws IOException {
		
		List<Map<String, Object>> list = null;
		
		try {
			
//			ObjectMapper mapper = new ObjectMapper();
			JsonNode root;
			root = ConversionUtil.stringToJson(response);
			JsonNode data = root.path("data").path(method);
			
			if (data.isMissingNode()) {
				
				throw new IOException(method + " => " + response);
			}
			
			if (!data.isNull()) {

				if (data.isArray()) {
					
					JsonNode firstItem = data.get(0);
					
					if (firstItem != null && firstItem.isObject()) {
						
						list = ConversionUtil.stringToListMap(data.toString());
					}
					else {
						
						list = new ArrayList<>();
						
						for (JsonNode elementNode : data) {
							
							Map<String, Object> map = new HashedMap<>();
							ObjectMapper mapper = new ObjectMapper();
							
							Object obj = mapper.convertValue(elementNode, Object.class);

							map.put(method, obj);
							
							list.add(map);
						}
					}
				}
				else if (data.isBoolean()) {
					
					Map<String, Object> mapBool = new HashedMap<>();
					
					mapBool.put(method, data.asBoolean());
					
					list = new ArrayList<>();
					
					list.add(mapBool);
				}
				else if (data.isLong()) {
					
					Map<String, Object> mapNumber = new HashedMap<>();
					
					mapNumber.put(method, data.asLong());
					
					list = new ArrayList<>();
					
					list.add(mapNumber);
				}
				else {
					
					list = new ArrayList<>();
					
					list.add(ConversionUtil.stringToMap(data.toString()));
				}	
			}
			
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw e;
		}
		
		return list;
	}
	
	private void traceGraphQL(String request, HttpResponse<String> response) {
		
		if (graphqlDebug && response != null) {

			log.debug("---------------------------------- GRAPHQL DEBUG ----------------------------------");
			log.debug("");
			log.debug("  REQUEST  >> " + request);
			log.debug("");
			log.debug("  RESPONSE << " + response);
			log.debug("  CONTENT  << " + response.body());
			log.debug("");
			log.debug("-----------------------------------------------------------------------------------");
		}
	}
}
