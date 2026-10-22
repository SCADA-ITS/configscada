package com.revenga.rits.back.io.controller.driver.mango;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.io.controller.driver.DriverException;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class MangoHttpSession {
	
	@Data
	class User {
		 
		String username;
		String password;
	}

	public static final Integer SECURE_TOKEN_LENGTH = 10;
	private static final String REQUEST_PROP_ACCEPT = "Accept";
	public static final String FORMAT_TEXT_CSV = "text/csv";
	public static final String FORMAT_APPLICATION_JSON_CHARSET_UTF8 = "application/json;charset=UTF-8";
	private static final String REQUEST_PROP_COOKIE = "Cookie";
	private static final String REQUEST_PROP_XSRF_TOKEN = "XSRF-TOKEN";
	private static final String REQUEST_PROP_X_XSRF_TOKEN = "X-XSRF-TOKEN";
	private static final String REQUEST_PROP_CONTENT_TYPE = "Content-Type";
	private static final String REQUEST_PROP_CONTENT_LENGHT = "Content-Length";
	private static final String REQUEST_PROP_SET_COOKIE = "Set-Cookie";
	
	MangoDriver mangoDriver;
	boolean auth;
	
	String strUrl;
	String strUrlLogin;
	String strUser;
	String strPassword;
	String responseHeaderXsrfToken;
	String responseKey;
	
	public MangoHttpSession(MangoDriver mangoDriver) {
		super();
		
		this.auth = false;
		this.mangoDriver = mangoDriver;
		
		init();
	}
	
	private void init() {
		
		this.strUrl = mangoDriver.getPublisherUrl();
		this.strUrlLogin = mangoDriver.getPublisherUrlLogin();
		this.strUser = mangoDriver.getPublisherUser();
		this.strPassword = mangoDriver.getPublisherPassword();
	}
	
	public String send(String requestUrl, String requestMethodStr, String body, String responseContentType) {
		
		String response = null;
		RequestMethod requestMethod = null;
		
		if (!StringUtils.isAllEmpty(requestUrl, requestMethodStr)) {
		
			requestMethod = RequestMethod.valueOf(requestMethodStr);
			
			response = send(requestUrl, requestMethod, body, responseContentType);
		}
		
		return response;
	}
		
	private String send(String requestUrl, RequestMethod requestMethod, String body, String responseContentType) {
		
		StringBuilder response = new StringBuilder();
		int responseCode = -1;
		
		try {
			
			if (!this.auth && !login()) {
				
				throw new DriverException(mangoDriver, "User not logged. Use " + strUrlLogin
						+ " to login. See: https://help.infiniteautomation.com/mango-rest-api-authentication");
			}

			URL url = new URL(this.strUrl + requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(10000);
			conn.setRequestMethod(requestMethod.name());
			
			if (responseContentType == null) {
				
				responseContentType = FORMAT_APPLICATION_JSON_CHARSET_UTF8;
			}
			
			conn.setRequestProperty(REQUEST_PROP_ACCEPT, responseContentType);
			conn.setRequestProperty(REQUEST_PROP_COOKIE, responseKey + "; " + REQUEST_PROP_XSRF_TOKEN + "=" + responseHeaderXsrfToken);
			conn.setRequestProperty(REQUEST_PROP_X_XSRF_TOKEN, responseHeaderXsrfToken);
			
			
			if (requestMethod != RequestMethod.DELETE && requestMethod != RequestMethod.GET) {
			
				conn.setRequestProperty(REQUEST_PROP_CONTENT_TYPE, responseContentType);
				conn.setDoOutput(true);
				try (OutputStream os = conn.getOutputStream()) {
					
				    byte[] input = body.getBytes(StandardCharsets.UTF_8);
				    os.write(input, 0, input.length);           
				}
			}
			
			if (requestMethod == RequestMethod.GET && requestUrl.length() > 8192) {
				
				log.warn("URI is too large > 8192");
				log.warn(requestUrl);
			}
			
			responseCode = conn.getResponseCode();
						
			if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_CREATED) {

				if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
					
					if (login()) {
						
						send(requestUrl, requestMethod, body, responseContentType);
					}
					else {
					
						throw new DriverException(mangoDriver, "Failed : Access to mango api rest");
					}
				}
				else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
					
					return StringUtils.EMPTY;
				}
				else {
					
					throw new DriverException(mangoDriver, "Failed : HTTP error code : " + responseCode);
				}
				
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String output;
					
			while ((output = br.readLine()) != null) {
				
				if (!StringUtils.isEmpty(output)) {
					
					response.append(output);
					
					if (StringUtils.equals(responseContentType, FORMAT_TEXT_CSV)) {
					
						response.append("\r\n");
					}
				}
			}
			
			conn.disconnect();
			
		} 
		catch (ConnectException e) {
			
			log.warn("Connection refused from " + this.strUrl);
			log.warn("Check mango server status !!!");
		}
		catch (DriverException e1) {
			
			log.warn(e1.getMessage());
		}
		catch (IOException e2) {

			log.warn(e2.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e2));
		}
		
		return response.toString();
	}

	private boolean login() {

		URL url;
		HttpURLConnection conn;
		ObjectMapper mapper;
		String body = null;
		User user;
		Map<String, List<String>> mapHeaderFields;
		String secureTokenStr;
		
		try {
			
			if (!StringUtils.isAllEmpty(strUrlLogin, this.strUser, this.strPassword)) {

				url = new URL(strUrlLogin);
				
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				conn.setDoOutput(true);
				
				conn.setRequestMethod(RequestMethod.POST.name().toUpperCase());
				conn.setRequestProperty(REQUEST_PROP_ACCEPT, FORMAT_APPLICATION_JSON_CHARSET_UTF8);
				conn.setRequestProperty(REQUEST_PROP_CONTENT_LENGHT, "39");
				conn.setRequestProperty(REQUEST_PROP_CONTENT_TYPE, FORMAT_APPLICATION_JSON_CHARSET_UTF8);
				secureTokenStr = getSecureToken();
				conn.setRequestProperty(REQUEST_PROP_COOKIE, "XSRF-TOKEN=" + secureTokenStr);
				conn.setRequestProperty(REQUEST_PROP_X_XSRF_TOKEN, secureTokenStr);

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

					throw new DriverException(mangoDriver, "Failed : HTTP error code : " + conn.getResponseCode());
				}
				
				mapHeaderFields = conn.getHeaderFields();

				log.debug("Printing Response Header...\n");

				for (Map.Entry<String, List<String>> entry : mapHeaderFields.entrySet()) {
					
					log.debug("Key : " + entry.getKey() 
			                           + " ,Value : " + entry.getValue());
				}
				
				responseHeaderXsrfToken = getResponseXsrfToken(mapHeaderFields);
				responseKey = getResponseKey(mapHeaderFields);
				
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				
				log.debug("Output from Server .... ");
				
				while ((output = br.readLine()) != null) {
					
					if (!StringUtils.isEmpty(output)) {
						
						log.debug(output);	
					}
				}

				conn.disconnect();
				
				this.auth = true;
			}
		}
		catch (ConnectException e) {
			
			log.warn("Connection refused from " + this.strUrl);
			log.warn("Check mango server status !!!");
		}
		catch (DriverException e1) {
			
			log.warn(e1.getMessage());
		}
		catch (IOException e2) {

			log.warn(e2.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e2));
		}
		
		return this.auth;
	}
	
	private String getSecureToken() {
		
		byte[] byteArray;
		
		SecureRandom secureRandom = new SecureRandom();
		
		byteArray = new byte[SECURE_TOKEN_LENGTH];
		secureRandom.nextBytes(byteArray);
		
		return new BigInteger(1, byteArray).toString(16);
	}
	
	private String getResponseXsrfToken(Map<String, List<String>> headers) throws DriverException {
		
		String strToken = null;
		
		if (headers.containsKey(REQUEST_PROP_SET_COOKIE)) {
			
			List<String> setCookies = headers.get(REQUEST_PROP_SET_COOKIE);
			
			if (!CollectionUtils.isEmpty(setCookies)) {
			
				String[] setCookieValues = setCookies.get(0).split(";");
				
				if (!ArrayUtils.isEmpty(setCookieValues) && setCookieValues.length > 0) {
						
					String[] xsrfToken = setCookieValues[0].split("=");
					
					if (!ArrayUtils.isEmpty(xsrfToken) && xsrfToken.length == 2) {
						
						strToken = xsrfToken[1];
					}
				}
			}
		}
		
		if (strToken == null) {
			
			throw new DriverException(mangoDriver, "Can not get header XSRF_TOKEN");
		}
		
		return strToken;
	}
	
	private String getResponseKey(Map<String, List<String>> headers) throws DriverException {
		
		String strResponseKey = null;
		
		if (headers.containsKey(REQUEST_PROP_SET_COOKIE)) {
			
			List<String> setCookies = headers.get(REQUEST_PROP_SET_COOKIE);
			
			if (!CollectionUtils.isEmpty(setCookies) && setCookies.size() > 1) {
					
				String[] setCookieValues = setCookies.get(2).split(";");
				
				if (!ArrayUtils.isEmpty(setCookieValues)) {
					
					strResponseKey = setCookieValues[0];
				}
			}
		}
		
		if (strResponseKey == null) {
			
			throw new DriverException(mangoDriver, "Can not get header Token aux");
		}
		
		return strResponseKey;
	}
}
