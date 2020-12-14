package com.revenga.rits.front.openits.bo;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.util.ConversionUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SessionBO extends GenericBO {
	
	private static final String HEADER_AUTHOTIZATION = "authorization";
	private static final String USER_FIELD_ID = "id";
	private static final String USER_FIELD_USER = "name";
	private static final String USER_FIELD_PASSWORD = "password";
	private static final String USER_FIELD_LOCKED_UNTIL = "lockedUntil";
	private static final String USER_FIELD_PASSWORD_EXPIRED_AT = "passwordExpiredAt";
	private static final String USER_FIELD_ALLOW_MULTIPLE_SESSIONS = "allowMultipleSessions";
	private static final String USER_FIELD_SESSION_EXPIRE_TIME_S = "sessionExpireTimeS";
	
	private static final String AUTH_RETRIES = "auth.retries";
		
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
//	@Autowired
//	UserBO userBO;
	
	@Autowired
	Environment env;
	
	Integer retries;
	
	private Map<String, Integer> sessionLoginRetries;
	private Map<String, UserSessionUI> sessions;
	
	@PostConstruct
	private void initialize() {
		
		this.sessionLoginRetries = Collections.synchronizedMap(new HashMap<>());
		this.sessions = Collections.synchronizedMap(new HashMap<>());
		this.retries = Integer.parseInt(env.getProperty(AUTH_RETRIES));
	}

	public UserSessionUI login(Map<String, String> headers, HttpSession httpSession) {
		
		String user = "";
		String password = "";
		UserSessionUI userSession = null;
		
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
			
			userSession = login(user, password, httpSession);
		}
		
		log.debug("/login: " + userSession);
		
		return userSession;
	}
	
	public UserSessionUI login(String userName, String password, HttpSession httpSession) {
		
		UserSessionUI userSession = null;
		
		//List<Map<String, Object>> users = userBO.getUsers();
		
		List<Map<String, Object>> users = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetUsers(), null);
		
		if (!CollectionUtils.isEmpty(users)) {
			
			for (Map<String, Object> user : users) {

				String usr = user.get(USER_FIELD_USER).toString();
				String pwd = user.get(USER_FIELD_PASSWORD).toString();
				Long lockedUntil = user.get(USER_FIELD_LOCKED_UNTIL) != null
						? Long.parseLong(user.get(USER_FIELD_LOCKED_UNTIL).toString())
						: null;
				Long passwordExpiredAt = user.get(USER_FIELD_PASSWORD_EXPIRED_AT) != null
						? Long.parseLong(user.get(USER_FIELD_PASSWORD_EXPIRED_AT).toString())
						: null;
				Boolean allowMultipleSessions = user.get(USER_FIELD_ALLOW_MULTIPLE_SESSIONS) != null
						? Boolean.parseBoolean(user.get(USER_FIELD_ALLOW_MULTIPLE_SESSIONS).toString())
						: null;
				Long sessionExpireTimeS = user.get(USER_FIELD_SESSION_EXPIRE_TIME_S) != null
						? Long.parseLong(user.get(USER_FIELD_SESSION_EXPIRE_TIME_S).toString())
						: null;
				
				if (userName.equalsIgnoreCase(usr)) {
					
					userSession = new UserSessionUI();
					
					userSession.setUserName(usr);
					userSession.setSessionId(httpSession.getId());
					userSession.setUserId(user.get(USER_FIELD_ID).toString());
					
					boolean userNoMultipleSessions = (allowMultipleSessions == null
							|| !allowMultipleSessions.booleanValue())
							&& (!MapUtils.isEmpty(this.sessions) && this.sessions.entrySet().stream()
									.filter(e -> e.getValue().getUserId().equals(user.get(USER_FIELD_ID))).findFirst()
									.orElse(null) != null)
							&& !isActive(httpSession.getId());
					
					if (lockedUntil != null && lockedUntil > Instant.now().toEpochMilli()) {
						
						userSession.setAccess(UserSessionUI.DENY_LOCKED);
					}
					// Wrong password
					else if (!password.equals(pwd)) {
							
						if (allowLoginRetry(user, httpSession.getId())) {
							
							userSession.setAccess(UserSessionUI.WRONG_USER_PASSWORD);
						}
						else {
							
							userSession.setAccess(UserSessionUI.DENY_LOCKED);
						}
					}
					// Password expired
					else if (passwordExpiredAt != null && passwordExpiredAt < Instant.now().toEpochMilli()) {
						
						userSession.setAccess(UserSessionUI.DENY_PASSWORD_EXPIRED);
					}
					// User without permission for multiple sessions
					else if (userNoMultipleSessions) {
						
						userSession.setAccess(UserSessionUI.DENY_MULTI_SESSION_NOT_ALLOWED);
					}
					else {
						
						userSession = activate(userSession, httpSession.getId(), sessionExpireTimeS);
					}
					
					break;
				}
			}
			
		}
		
		if (userSession == null) {
			
			userSession = new UserSessionUI();
			
			userSession.setSessionId(httpSession.getId());
			userSession.setAccess(UserSessionUI.UNAUTHORIZED);
		}
		
		return userSession;
	}
	
	public UserSessionUI keepAlive(Map<String, String> headers, HttpSession httpSession) {
		
		UserSessionUI userSession = null;
		
		userSession = keepAlive(httpSession.getId());
		
		log.debug("/keepAlive: " + httpSession.getId() + " => " + userSession);
		
		return userSession;
	}
	
	public UserSessionUI logout(HttpSession httpSession) {
		
		UserSessionUI userSession = this.sessions.get(httpSession.getId());
		
		if (userSession != null) {
						
			this.sessions.remove(httpSession.getId());
		}
		else {
			
			userSession = new UserSessionUI();
		}
		
		userSession.setSessionId(httpSession.getId());
		userSession.setAccess(UserSessionUI.LOGOUT);
		
		return userSession;
	}
	
	private boolean isActive(String sessionId) {
		
		return (this.sessions.entrySet().stream().filter(e -> e.getValue().getSessionId().equals(sessionId)
				&& e.getValue().getAccess().equals(UserSessionUI.AUTHORIZED)).findFirst().orElse(null) != null);
	}

	private UserSessionUI keepAlive(String sessionId) {
		
		UserSessionUI userSession = this.sessions.get(sessionId);
		
		if (userSession != null && userSession.getAccess().equals(UserSessionUI.AUTHORIZED)) {
			
			if (userSession.getSessionExpireTimeS() != null) {
			
				userSession.setValidUntil(Instant.now().toEpochMilli() + (userSession.getSessionExpireTimeS()*1000));
				
			}
		}
		else {
			userSession = new UserSessionUI();
			
			userSession.setSessionId(sessionId);
			userSession.setAccess(UserSessionUI.UNAUTHORIZED);
		}
		
		return userSession;
	}
	
	private boolean allowLoginRetry(Map<String, Object> user, String httpSessionId) {
		
		boolean allowed = true;
		
		try {
			
			if (ObjectUtils.allNotNull(user, httpSessionId)) {
			
				Long lockedUntil = user.get(USER_FIELD_LOCKED_UNTIL) != null
						? Long.parseLong(user.get(USER_FIELD_LOCKED_UNTIL).toString())
						: null;
				Integer sessionRetries = sessionLoginRetries.get(httpSessionId);
				
				if (this.retries > 0) {
				
					if (sessionRetries == null) {
						
						sessionLoginRetries.put(httpSessionId, 1);
					}
					else {
						
						if (sessionRetries < this.retries) {
							
							sessionLoginRetries.put(httpSessionId, sessionRetries + 1);
						}
						else {
							
							long dateLocked = Date.from(Instant.parse("2099-12-31T23:59:59.999Z")).getTime();
							
							if (lockedUntil == null || lockedUntil < dateLocked) {
							
								user.put(USER_FIELD_LOCKED_UNTIL, dateLocked);
								
								Map<String, Object> input = new HashMap<>();
								input.put(GraphqlTypeAdapter.USER_DATA, user);
								
								voDataSourceAdapter.put(voDataSourceAdapter.getDataSourceUpdateUser(), input);
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
	
	private UserSessionUI activate(UserSessionUI userSession, String httpSessionId, Long sessionExpireTimeS) {
		
		if (userSession != null) {
						
			userSession.setSessionId(httpSessionId);
			userSession.setAccess(UserSessionUI.AUTHORIZED);
			userSession.setLoginSince(Instant.now().toEpochMilli());
			
			if (sessionExpireTimeS != null) {
			
				userSession.setValidUntil(Instant.now().toEpochMilli() + (sessionExpireTimeS*1000));
			}
			
			sessions.put(httpSessionId, userSession);
		}
		
		return userSession;
	}

	@Override
	public List<Map<String, Object>> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
