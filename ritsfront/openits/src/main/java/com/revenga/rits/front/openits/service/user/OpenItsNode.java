package com.revenga.rits.front.openits.service.user;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.Level;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.service.user.UserServer.SessionOperation;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class OpenItsNode {
	
	public static final Integer SECURE_TOKEN_LENGTH = 10;
	private static final String REQUEST_PROP_ACCEPT = "Accept";
	private static final String FORMAT_APPLICATION_JSON_CHARSET_UTF8 = "application/json;charset=UTF-8";
	private static final String REQUEST_PROP_CONTENT_TYPE = "Content-Type";
	
	private static final String NODE_PORT = AppInfra.getProperty("node.port");

	private String remoteAddress;
	private final String subscriptorPath = "/api/v2/users";
	private Integer subscriptorPort;
	
	public static OpenItsNode newIntance() {
		
		OpenItsNode openItsNode = null;

		if (!StringUtils.isEmpty(NODE_PORT) && NumberUtils.isCreatable(NODE_PORT)) {
			
			Integer port = Integer.valueOf(NODE_PORT);
			
			openItsNode = new OpenItsNode();
			openItsNode.setRemoteAddress(new InetSocketAddress(0).getAddress().getHostAddress());
			openItsNode.setSubscriptorPort(port);
		}
	
		return openItsNode;
	}
	
	public OpenItsNode() {
		
		super();
	}

	public OpenItsNode(String remoteAddress, Integer subscriptorPort) {
		super();
		this.remoteAddress = remoteAddress;
		this.subscriptorPort = subscriptorPort;
	}
	
	public String getName() {
		
		return this.remoteAddress + ":" + this.subscriptorPort;
	}
		
	public Boolean sendSessionOperation(OpenItsNode client, SessionOperation operation, UserSessionUI userSessionUI) {
		
		String requestUrl = "http://" + this.remoteAddress + ":" + this.subscriptorPort + this.subscriptorPath;
		Boolean sent = false;
		String body = "";
		int responseCode = -1;
		
		try {

			if (userSessionUI != null) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				body = objectMapper.writeValueAsString(userSessionUI);
			}
			
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(10000);
			conn.setRequestMethod(RequestMethod.POST.name());
			
			conn.setRequestProperty(REQUEST_PROP_ACCEPT, FORMAT_APPLICATION_JSON_CHARSET_UTF8);
			conn.setRequestProperty(REQUEST_PROP_CONTENT_TYPE, FORMAT_APPLICATION_JSON_CHARSET_UTF8);
			conn.setRequestProperty(UserServer.HEADER_OPERATION, operation.toString());
			conn.setRequestProperty(UserServer.HEADER_NODE_PORT, client.getSubscriptorPort().toString());
			
			conn.setDoOutput(true);
			try (OutputStream os = conn.getOutputStream()) {
				
			    byte[] input = body.getBytes(StandardCharsets.UTF_8);
			    os.write(input, 0, input.length);           
			}
			
			responseCode = conn.getResponseCode();
			
			conn.disconnect();
			
			sent = (responseCode == HttpURLConnection.HTTP_OK);
		} 
		catch (ConnectException e) {
			
			log.warn("Connection refused from " + requestUrl);
			log.warn("Check replica server status !!!");
		}
		catch (IOException e2) {

			log.warn(e2.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e2));
		}
		
		log.log(sent.booleanValue() ? Level.INFO : Level.ERROR, String.format("[%s >> %s] - %s %s", 
				client.getName(),
				this.getName(),
				operation.toString(), 
				userSessionUI != null ? " => " + userSessionUI.getSessionId() : "" ));
			
		return sent;
	}
}
