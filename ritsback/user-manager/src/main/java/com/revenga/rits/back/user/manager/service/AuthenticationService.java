package com.revenga.rits.back.user.manager.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.front.core.model.helper.UserSessionHelper;
import com.revenga.rits.back.front.core.model.vo.UserSessionVO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuthenticationService {
	
	public static final String HEADER_SESSION_ID = "session-id";
	private static final String HEADER_AUTHOTIZATION = "authorization";
	
	private Map<String, Long> sessionLoginRetries;
	
	@PostConstruct
	private void initialize() {
		
		this.sessionLoginRetries = Collections.synchronizedMap(new HashMap<>());
	}
	
	public boolean auth(Map<String, String> headers, String httpSessionId) {
		
		String sessionId = null;
		String originalUrl = null;
		MultiValueMap<String, String> queryParams = null;
		boolean bKeepAlive = false;
		
		if (!MapUtils.isEmpty(headers)) {
			
			originalUrl = headers.get("x-original-uri");
			
			if (!StringUtils.isEmpty(originalUrl)) {
		
				queryParams = UriComponentsBuilder.fromUriString(originalUrl).build().getQueryParams();
				
				if (!MapUtils.isEmpty(queryParams) && queryParams.containsKey(AuthenticationService.HEADER_SESSION_ID)) {
					
					sessionId = queryParams.get(AuthenticationService.HEADER_SESSION_ID).get(0);
				}
			}
		}
		
		if (StringUtils.isEmpty(sessionId)) {
			
			sessionId = headers.get(HEADER_SESSION_ID);
		}
		
		if (StringUtils.isEmpty(sessionId) && !StringUtils.isEmpty(httpSessionId)) {
			
			sessionId = httpSessionId;
		}
		
		if (!StringUtils.isEmpty(sessionId)) {
			
			bKeepAlive = (keepAlive(sessionId) != null);
		}
		
		log.debug("/auth: " + sessionId + " => " + bKeepAlive);

		return bKeepAlive;		
	}
		
	public UserSessionVO login(Map<String, String> headers, String httpSessionId) {
		
		String user = "";
		String password = "";
		UserSessionVO userSessionVO = null;
		
		
		if (headers.containsKey(HEADER_AUTHOTIZATION)) {
		
			String authHeader = headers.get(HEADER_AUTHOTIZATION);

			String base64Credentials = authHeader.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credential = new String(credDecoded, StandardCharsets.UTF_8);
			
			String[] credentials = credential.split(":");
			
			if (!ArrayUtils.isEmpty(credentials) && credentials.length == 2) {
				
				user = credentials[0];
				password = credentials[1];
			}
			
			userSessionVO = login(user, password, httpSessionId);
		}
		
		log.debug("/login: " + userSessionVO);
		
		return userSessionVO;
	}

	private UserSessionVO login(String userName, String password, String httpSessionId) {
		
		User user = EntitiesManager.getInstance().getUser(userName);
		UserSession userSession = new UserSession();
		
		if (user != null) {
			
			userSession.setUser(user);
			userSession.setUserId(user.getId());
					
			// Locked user
			if (user.getLockedUntil() != null && user.getLockedUntil() > Instant.now().toEpochMilli()) {
				
				userSession.setAccess(UserSession.DENY_LOCKED);
			}
			// Wrong password
			else if (!user.getPassword().equals(password)) {
					
					if (allowLoginRetry(user, httpSessionId)) {
						
						userSession.setAccess(UserSession.WRONG_USER_PASSWORD);
					}
					else {
						
						userSession.setAccess(UserSession.DENY_LOCKED);
					}
				}
			// Pasword expired
			else if (user.getPasswordExpiredAt() != null && user.getPasswordExpiredAt() < Instant.now().toEpochMilli()) {
				
				userSession.setAccess(UserSession.DENY_PASSWORD_EXPIRED);
			}
			// User without permission for multiple sessions
			else if ((user.getAllowMultipleSessions() == null || !user.getAllowMultipleSessions().booleanValue()) &&
					 !CollectionUtils.isEmpty(EntitiesManager.getInstance().getSessions(user.getId()))) {
				
				userSession.setAccess(UserSession.DENY_MULTI_SESSION_NOT_ALLOWED);	
			}
			// Activate user session
			else {
				
				userSession = activate(userSession, httpSessionId);
			}
		}
		else {
			
			userSession.setAccess(UserSession.WRONG_USER_PASSWORD);
		}
		
		return UserSessionHelper.get(userSession);
	}
	
	private UserSession activate(UserSession userSession, String httpSessionId) {
	
		if (userSession != null) {
						
			userSession.setSessionId(httpSessionId);
			userSession.setAccess(UserSession.ALLOWED);
			userSession.setLoginSince(Instant.now().toEpochMilli());
			
			if (userSession.getUser().getSessionExpireTimeS() != null) {
			
				userSession.setValidUntil(Instant.now().toEpochMilli() + (userSession.getUser().getSessionExpireTimeS()*1000));
			}
			
//			if (EntitiesManager.getInstance().putSession(userSession) != null) {
//			
//				return EntitiesManager.getInstance().expireSession(userSession);
//			}
			
			return EntitiesManager.getInstance().putSession(userSession);
		}
		
		return null;
	}
	
	public UserSessionVO keepAlive(Map<String, String> headers, String httpSessionId) {
		
		String sessionId = null;
		UserSessionVO userSessionVO = null;
		
		if (headers.containsKey(HEADER_SESSION_ID)) {
		
			sessionId = headers.get(HEADER_SESSION_ID);
		}
		else {
			
			sessionId = httpSessionId;
		}

		if (!StringUtils.isEmpty(sessionId)) {
		
			userSessionVO = keepAlive(sessionId);
		}
		
		log.debug("/keepAlive: " + sessionId + " => " + userSessionVO);
		
		return userSessionVO;
	}

	private UserSessionVO keepAlive(String sessionId) {
		
		UserSession userSession = EntitiesManager.getInstance().getSession(sessionId);
		
		if (userSession != null && userSession.getUser() != null && userSession.getAccess().equals(UserSession.ALLOWED)) {
			
			if (userSession.getUser().getSessionExpireTimeS() != null) {
			
				userSession.setValidUntil(Instant.now().toEpochMilli() + (userSession.getUser().getSessionExpireTimeS()*1000));
				
//				if (EntitiesManager.getInstance().putSession(userSession) != null) {
//					
//					return UserSessionHelper.get(EntitiesManager.getInstance().expireSession(userSession));
//				}
				
				return UserSessionHelper.get(EntitiesManager.getInstance().putSession(userSession));
			}
			else {
				
				return UserSessionHelper.get(userSession);
			}
		}
		
		return null;
	}
	
	public UserSessionVO logout(Map<String, String> headers, String httpSessionId) {
		
		String sessionId = null;
		UserSessionVO userSessionVO = null;
		
		if (headers.containsKey(HEADER_SESSION_ID)) {
		
			sessionId = headers.get(HEADER_SESSION_ID);
		}
		else {
			
			sessionId = httpSessionId;
		}

		if (!StringUtils.isEmpty(sessionId)) {
		
			userSessionVO = logout(sessionId);
		}
		
		log.debug("/logout: " + userSessionVO);
		
		return userSessionVO;
	}
	
	private UserSessionVO logout(String sessionId) {
		
		UserSession userSession = EntitiesManager.getInstance().getSession(sessionId);
		
		if (userSession != null && EntitiesManager.getInstance().delSession(userSession) != null) {
				
			userSession.setValidUntil(Instant.now().toEpochMilli());
			userSession.setAccess(UserSession.LOGOUT);
			
			return UserSessionHelper.get(userSession);
		}
		
		return null;
	}
	
	private boolean allowLoginRetry(User user, String httpSessionId) {
	
		boolean allowed = true;
		SystemParamValue systemParamValue;
		Long maxRetries;
		
		try {
			
			if (ObjectUtils.allNotNull(user, httpSessionId)) {
			
				Long retries = sessionLoginRetries.get(httpSessionId);
				
				systemParamValue = EntitiesManager.getInstance().getSystemParamValue(Long.valueOf(SystemParam.SYSTEM_PARAM_USER_LOGIN_RETRIES));
				maxRetries = Long.valueOf(systemParamValue.getValue());
				
				if (maxRetries > 0) {
				
					if (retries == null) {
						
						sessionLoginRetries.put(httpSessionId, 1L);
					}
					else {
						
						if (retries + 1 < maxRetries) {
							
							sessionLoginRetries.put(httpSessionId, retries + 1);
						}
						else {
							
							long dateLocked = Date.from(Instant.parse("2099-12-31T23:59:59.999Z")).getTime();
							
							if (user.getLockedUntil() == null || user.getLockedUntil() < dateLocked) {
							
								user.setLockedUntil(dateLocked);
								
								EntitiesManager.getInstance().updateUser(user);
								
								sessionLoginRetries.remove(httpSessionId);
							}
							
							allowed = false;
						}
					}
				}
			}
		}
		catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			allowed = false;
		}
		
		return allowed;
	}
}
