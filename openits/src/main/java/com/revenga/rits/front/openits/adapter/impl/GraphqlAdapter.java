package com.revenga.rits.front.openits.adapter.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GraphqlAdapter {

private final static String GRAPHQL_URL = "graphql.url";
	
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
	
	private List<Map<String, Object>> response2VO(String response, String method) throws IOException {
		
		List<Map<String, Object>> list = null;
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root;
			root = mapper.readTree(response);
			JsonNode data = root.path("data").path(method);
			
			if (data.isArray()) {
			
				list = mapper.readValue(data.toString(), mapper.getTypeFactory().constructCollectionType(List.class, Map.class));
			}
			else {
				
				list = new ArrayList<>();
				
				list.add(mapper.readValue(data.toString(), new TypeReference<HashMap<String, Object>>() {}));
			}
			
			
			
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw e;
		}
		
		return list;
	}
}
