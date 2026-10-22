package com.revenga.rits.back.io.controller.module.rtms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class RtmsHttpSession {
	
	@Data
	class User {
		 
		String username;
		String password;
	}

	private static final String REQUEST_PROP_ACCEPT = "Accept";
	private static final String FORMAT_APPLICATION_JSON_CHARSET_UTF8 = "application/json;charset=UTF-8";
	private static final String REQUEST_PROP_CONTENT_TYPE = "Content-Type";
	private static final String REQUEST_PROP_CONTENT_LENGHT = "Content-Length";
	private static final String BEARER_TOKEN = "bearerToken";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";
	
	private static final String FILE_APP_PROPS = "application.properties";
	private static final Long PARAM_ROUTE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "driver.rtms.connection.parameters.route"));
	private static final Long PARAM_USER = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "driver.rtms.connection.parameters.user"));
	private static final Long PARAM_PASSWORD = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "driver.rtms.connection.parameters.password"));
	private static final Long PARAM_TYPE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS, "driver.rtms.connection.parameters.param.type"));

	Element targetRtms;
	boolean auth;
	
	private String rtmsRoute;
	private String strUser;
	private String strPassword;
	private String urlLogin;
	private String securityToken;
	
	public RtmsHttpSession(Element targetRtms) {
		
		this.securityToken=null;
		this.auth = false;
		this.targetRtms = targetRtms;
		this.rtmsRoute = EntitiesManager.getInstance().getElementValue(targetRtms, PARAM_ROUTE, PARAM_TYPE).getValue();
		this.strUser = EntitiesManager.getInstance().getElementValue(targetRtms, PARAM_USER, PARAM_TYPE).getValue();
		this.strPassword = EntitiesManager.getInstance().getElementValue(targetRtms, PARAM_PASSWORD, PARAM_TYPE).getValue();
		this.urlLogin = EntitiesManager.getInstance().getElementValue(targetRtms, PARAM_ROUTE, PARAM_TYPE).getValue()+"login";
	}
		
	public String requestRtmsMeasures(String values) {
		
		StringBuilder response = new StringBuilder();
		int responseCode = -1;

		try {

			if (!this.auth) {

				login();
			}

			if (!this.auth) {
				
				throw new RtmsException("Cannot login to RTMS");
			}

			String urlFormatted =UriComponentsBuilder.fromHttpUrl(this.rtmsRoute + values).toUriString();
			URL url = new URL(urlFormatted);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(10000);
			conn.setRequestMethod(RequestMethod.GET.name());

			conn.setRequestProperty(AUTHORIZATION, BEARER + securityToken);

			conn.setDoOutput(true);

			responseCode = conn.getResponseCode();

			if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {

				if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

					if (Boolean.TRUE.equals(login())) {

						requestRtmsMeasures(values);
					} else {

						log.error("Failed to connect Rtms Api Rest");
						this.auth = false;
					}
				} else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {

					return StringUtils.EMPTY;
					
				} else {

					log.error("[RTMS request][" + (new Key(targetRtms)).getSimpleValue()
							+ "] Failed : HTTP error code : " + responseCode);
					this.auth = false;
				}

			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;

			while ((output = br.readLine()) != null) {

				if (!StringUtils.isEmpty(output)) {

					response.append(output);
				}
			}

		} catch (RtmsException e) {
			
			log.error("[RTMS request][" + (new Key(targetRtms)).getSimpleValue() + "] Error : " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
		} catch (ConnectException e) {

			log.warn("[RTMS request][" + (new Key(targetRtms)).getSimpleValue() + "] Connection refused from "
					+ this.rtmsRoute);
			log.warn("[RTMS request][" + (new Key(targetRtms)).getSimpleValue() + "] Check Rtms Status !!!");
			this.auth = false;
			
		} catch (IOException e) {

			log.warn("[RTMS request][" + (new Key(targetRtms)).getSimpleValue() + "] Error : " + e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			this.auth = false;
		}
		
		return response.toString();
	}

	private boolean login() {

		URL url;
		HttpURLConnection conn;
		ObjectMapper mapper;
		String body = null;
		User user;
		this.auth = false;
		this.securityToken = null;
		
		try {
			
			if (!StringUtils.isAllEmpty(this.urlLogin, this.strUser, this.strPassword)) {
				
				log.debug("[RTMS login][" + (new Key(targetRtms)).getSimpleValue() + "] - Request login...");

				url = new URL(this.urlLogin);
				
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				conn.setDoOutput(true);
				
				conn.setRequestMethod(RequestMethod.POST.name().toUpperCase());
				conn.setRequestProperty(REQUEST_PROP_ACCEPT, FORMAT_APPLICATION_JSON_CHARSET_UTF8);
				conn.setRequestProperty(REQUEST_PROP_CONTENT_LENGHT, "39");
				conn.setRequestProperty(REQUEST_PROP_CONTENT_TYPE, FORMAT_APPLICATION_JSON_CHARSET_UTF8);

				mapper = new ObjectMapper();
				
				mapper.setSerializationInclusion(Include.NON_NULL);
		
				
				user = new User();
				
				user.setUsername(this.strUser);
				user.setPassword(this.strPassword);
				
				body = mapper.writeValueAsString(user);

				OutputStream os = conn.getOutputStream();
				os.write(body.getBytes());
				os.flush();

				if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED &&
					conn.getResponseCode() != HttpURLConnection.HTTP_OK) {

					log.error("[RTMS login][" + (new Key(targetRtms)).getSimpleValue()
							+ "] Failed Login : HTTP error code : " + conn.getResponseCode() + " "
							+ conn.getResponseMessage());
					
				}
				
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				
				while ((output = br.readLine()) != null) {
					
					if (!StringUtils.isEmpty(output)) {
						
						log.debug("[RTMS login][" + (new Key(targetRtms)).getSimpleValue() + "] << " + output);
						securityToken = getSecurityToken(output);
					}
				}
				
				if (securityToken == null) {
					
					log.error("[RTMS login][" + (new Key(targetRtms)).getSimpleValue() + "] Can not get Security Token");
					
				} else {
					
					this.auth = true;				
				}
			}
		}
		catch (ConnectException e) {
			
			log.warn("[RTMS login][" + (new Key(targetRtms)).getSimpleValue() + "] Connection refused from " + this.urlLogin);
			log.warn("[RTMS login][" + (new Key(targetRtms)).getSimpleValue() + "] Check RTMS server status !!!");
		}
		catch (IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return this.auth;
	}
	
	private String getSecurityToken(String loginResponse){
		
		String strToken = null;
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonNode;
	    
		try {
			
			jsonNode = mapper.readTree(loginResponse);
			
			if (jsonNode != null && jsonNode.get(BEARER_TOKEN) != null) {
		    	
		    	strToken = jsonNode.get(BEARER_TOKEN).textValue();
		    }
			
		} catch (Exception e) {
			
			log.error("[RTMS login][" + (new Key(targetRtms)).getSimpleValue() + "] Error trying to get Bearer Token");
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
	    
	   return strToken;
	}
}
