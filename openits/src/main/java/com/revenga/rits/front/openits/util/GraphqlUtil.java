package com.revenga.rits.front.openits.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class GraphqlUtil {

	private final static String GRAPHQL_URL = "graphql.url";
	
	@Autowired
	private Environment env;
	
	public String query(String method, String params, String fields) throws IOException, InterruptedException {
		
		HttpResponse<String> response = null;
		
		try {
			
			StringBuilder sb = new StringBuilder();
			
			if (!StringUtils.isAllEmpty(method, fields)) {
			
				sb.append("{\"query\":\"query{");
				
				String paramsAux = params.replace("\"", "\\\"");
				
				if (!StringUtils.isEmpty(paramsAux)) {
					sb.append(method + "(" + paramsAux + ")");
				}
				else {
					sb.append(method + "()");
				}
				sb.append(fields);
				sb.append("}\"}");
				
				String body = sb.toString();
				//String body = "{\"query\":\"query  {getElementsByType(elementTypeId:\\\"ElementType:3\\\"){id alias description state}}\"}";
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
		
		return response != null ? response.body() : null;
	}
}
