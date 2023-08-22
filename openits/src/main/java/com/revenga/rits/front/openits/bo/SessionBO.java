package com.revenga.rits.front.openits.bo;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.EntityChangedUI;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SessionBO extends GenericBO {

	private static final String AUTH_ENCRYPTED_PASSWORD = "auth.encryptedPassword";
	
	private static final String HEADER_AUTHOTIZATION = "authorization";
	private static final String DEFAULT_ACCOUNT_LOCKED_ISO_DATE = "2099-12-31T23:59:59.999Z";
	private static final Integer DEFAULT_VALID_SESSION_SECS = 600;
	
	private static final String FIELD_AUDIT_LOGS = "auditLogs";
	private static final String FIELD_AUDIT_LOG_TYPE_ID = "logTypeId";
	private static final String FIELD_AUDIT_LOG_SUBTYPE_ID = "logSubtypeId";
	private static final String FIELD_AUDIT_LOG_USER_ID = "userId";
	private static final String FIELD_AUDIT_LOG_LEVEL = "level";
	private static final String FIELD_AUDIT_LOG_AUDIT_LOG_VALUES = "auditLogValues";
	private static final String FIELD_AUDIT_LOG_VALUE_LOG_TYPE_PARAM_ID = "logTypeParamId";
	private static final String FIELD_AUDIT_LOG_VALUE_VALUE = "value";
	
	private static final String AUDIT_LOG_TYPE = "LogType:4";
	private static final String AUDIT_LOG_SUBTYPE_LOGIN = "LogSubtype:38";
	private static final String AUDIT_LOG_SUBTYPE_LOGOUT = "LogSubtype:39";
	private static final String AUDIT_LOG_SUBTYPE_EXPIRED = "LogSubtype:40";
	private static final String AUDIT_LOG_USERS_PARAM_SESSION = "LogTypeParam:4:1";
	private static final String AUDIT_LOG_USERS_PARAM_COMMENT = "LogTypeParam:4:2";
	
	private static final Integer DEFAULT_MAX_SESSIONS = 0;
	private static final Integer DEFAULT_LOGIN_RETRIES = 3;
	private static final Integer DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES = 60;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@SuppressWarnings("unused")
	private class AuditLogLevel {
		
		public static final String TRACE = "TRACE";
		public static final String DEBUG = "DEBUG";
		public static final String INFO = "INFO";
		public static final String WARN = "WARN";
		public static final String ERROR = "ERROR";
		public static final String CRITICAL = "CRITICAL";
	}

	@Autowired
	Environment env;

	@Autowired
	CacheManager cacheManager;

	@Autowired
	SystemParamBO systemParamBO;
	
	@Autowired
	AuditLogBO auditLogBO;

	private Map<Pair<String, String>, Integer> sessionLoginRetries;
	private Map<String, UserSessionUI> sessions;

	@PostConstruct
	private void initialize() {

		this.sessionLoginRetries = Collections.synchronizedMap(new HashMap<>());
		this.sessions = Collections.synchronizedMap(new HashMap<>());
	}

	public UserSessionUI login(Map<String, String> headers, HttpSession httpSession) {

		String user = "";
		String password = "";
		UserSessionUI userSession = null;
		UserSessionUI userSessionReturned = null;

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
			
			log.debug("/login: " + userSession);
			
			userSessionReturned = getUserSessionWithoutChanges(userSession);
		}
		
		return userSessionReturned != null ? userSessionReturned : null;
	}

	public UserSessionUI login(String userName, String password, HttpSession httpSession) {
		
		Integer maxSessions = Integer.valueOf(String.valueOf(systemParamBO.getSystemValue(SystemParamBO.SYSTEM_PARAM_MAX_SESSIONS,
				DEFAULT_MAX_SESSIONS)));

		UserSessionUI userSession = new UserSessionUI();

		userSession.setSessionId(httpSession.getId());
		userSession.setAccess(UserSessionUI.UNAUTHORIZED);
		
		if (sessions.size() < maxSessions || maxSessions == null || maxSessions <= 0) {
			
			List<Map<String, Object>> users = voDataSourceAdapter
					.getList(VODataSourceAdapter.DATA_SOURCE_GET_USERS, null);

			if (!CollectionUtils.isEmpty(users)) {

				users = vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USERS, users);

				for (Map<String, Object> u : users) {

					String usr = u.get(UserSessionUI.USER_FIELD_USER).toString();

					if (userName.equalsIgnoreCase(usr)) {
						
						String id = u.get(UserSessionUI.USER_FIELD_ID).toString();
						String pwd = u.get(UserSessionUI.USER_FIELD_PASSWORD).toString();
						
						Map<String, Object> user = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_USER, new Object[] { id });
						
						if (user != null) {
							
							user = vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USER, user);
							
							if (user != null) {
								
								if (Boolean.parseBoolean(env.getProperty(AUTH_ENCRYPTED_PASSWORD))) {
									
									try {
										
										password = AES.encrypt(password);
										
									} catch (Exception e) {
										
										log.error(e.getMessage());
										log.debug(ExceptionUtils.getStackTrace(e));
									}
								}

								if (password.equals(pwd)) {
									
									user.put(UserSessionUI.USER_FIELD_PASSWORD, null);

									userSession.setUser(user);

									setAccess(user, userSession);

									if (userSession.getAccess().equals(UserSessionUI.AUTHORIZED)) {

										activate(userSession);
										
										sendAuditLog(userSession, AUDIT_LOG_SUBTYPE_LOGIN, AuditLogLevel.INFO, null);
									}
									
								} else {
									
									userSession.setAccess(allowLoginRetry(user, httpSession.getId())
											? UserSessionUI.WRONG_USER_PASSWORD
											: UserSessionUI.DENY_LOCKED);
								}
							}
						}
						
						break;
					}
				}
			}
		}
		else {
			
			userSession.setAccess(UserSessionUI.MAX_SESSIONS);
		}

		return getUserSessionWithoutChanges(userSession);
	}

	public UserSessionUI keepAlive(HttpSession httpSession) {

		UserSessionUI userSession = null;

		userSession = keepAlive(httpSession.getId());

		log.debug("/keepAlive: " + httpSession.getId() + " => " + userSession);
		
		UserSessionUI userSessionCloned = SerializationUtils.clone(userSession);
		
		if (!userSessionCloned.getServerAvailable().booleanValue()) {
			
			userSessionCloned.setAccess(UserSessionUI.SERVER_NOT_AVAILABLE);
		}
		
		userSession.getEntitiesChanged().clear();
		
		return userSessionCloned;
	}

	public UserSessionUI logout(HttpSession httpSession) {

		UserSessionUI userSession = this.sessions.get(httpSession.getId());

		if (userSession != null) {

			this.sessions.remove(httpSession.getId());

			log.info("Session id " + httpSession.getId() + " logout by user "
					+ userSession.getUser().get(UserSessionUI.USER_FIELD_USER) + " at " + sdf.format(new Date()));
			
			sendAuditLog(userSession, AUDIT_LOG_SUBTYPE_LOGOUT, AuditLogLevel.INFO, null);
			
		} else {

			userSession = new UserSessionUI();

			userSession.setSessionId(httpSession.getId());
		}

		userSession.setAccess(UserSessionUI.LOGOUT);

		return userSession;
	}
	
	public void maintenanceServer(Boolean available) {
		
		if (!MapUtils.isEmpty(this.sessions)) {

			synchronized (this.sessions) {
				
				this.sessions.values().forEach(userSession -> {
					
					if (!userSession.getServerAvailable().equals(available)) {
						
						userSession.setServerAvailable(available);
					}
				});
			}
		}
	}

	public void maintenanceSessions() {

		Set<String> sessionsToRemove = new HashSet<>();

		if (!MapUtils.isEmpty(this.sessions)) {

			synchronized (this.sessions) {
				
				for (Map.Entry<String, UserSessionUI> entry : this.sessions.entrySet()) {

					String httpSessionId = entry.getKey();
					UserSessionUI userSession = entry.getValue();

					if (httpSessionId != null && userSession != null && userSession.getValidUntil() != null
							&& userSession.getValidUntil() < Instant.now().toEpochMilli()) {

						sessionsToRemove.add(httpSessionId);
					}
				}
			} 

			sessionsToRemove.forEach(sessionToRemove -> {

				UserSessionUI userSession = this.sessions.get(sessionToRemove);

				if (userSession != null) {

					this.sessions.remove(sessionToRemove);

					log.info("Session id " + sessionsToRemove + " logged by user "
							+ userSession.getUser().get(UserSessionUI.USER_FIELD_USER) + " since "
							+ sdf.format(new Date(userSession.getLoginSince())) + " expired at "
							+ sdf.format(new Date(userSession.getValidUntil())) + " removed");

					removeSessionLoginRetriesBySessionId(sessionToRemove);
					
					sendAuditLog(userSession, AUDIT_LOG_SUBTYPE_EXPIRED, AuditLogLevel.INFO, null);
				}
			});
		}
	}
	
	public List<UserSessionUI> getUserSessions() {
		
		List<UserSessionUI> userSessions = null;
		
		synchronized (this.sessions) {
			
			userSessions = this.sessions.values().stream().map(this::getUserSessionWithoutChanges).collect(Collectors.toList());
		}
		
		return userSessions;
	}

	public UserSessionUI getUserSession(String sessionId) {

		return getUserSessionWithoutChanges(this.sessions.get(sessionId));
	}

	public List<UserSessionUI> getUserSessions(Map<String, Object> user) {

		List<UserSessionUI> userSessionsList = null;

		synchronized (sessions) {

			userSessionsList = this.sessions.values().stream().map(this::getUserSessionWithoutChanges).filter(
					e -> e.getUser().get(UserSessionUI.USER_FIELD_ID).equals(user.get(UserSessionUI.USER_FIELD_ID)))
					.collect(Collectors.toList());

		}

		return userSessionsList;
	}
	
	public void addEntityChanged(int entityTypeId, String id, int operation, Map<String, Object> entity) {
		
		synchronized (sessions) {
			
			EntityChangedUI entityChanged = new EntityChangedUI(entityTypeId, id, operation, Instant.now().toEpochMilli(), entity);
			
			this.sessions.values().forEach(userSession -> userSession.getEntitiesChanged().add(entityChanged));
		}
	}

	public void updateUser(Map<String, Object> user) {

		List<UserSessionUI> userSessions = getUserSessions(user);

		if (!CollectionUtils.isEmpty(userSessions)) {

			for (UserSessionUI userSession : userSessions) {

				Map<String, Object> currentUser = userSession.getUser();

				for (Map.Entry<String, Object> currentUserfield : currentUser.entrySet()) {

					String key = currentUserfield.getKey();
					
					if (key.equals(UserSessionUI.USER_FIELD_PASSWORD)) {
						
						continue;
					}

					Object currentValue = currentUserfield.getValue();
					Object newValue = user.get(key);

					if (currentValue != null && !(currentValue instanceof Map) && !currentValue.equals(newValue)) {

						currentUser.put(key, newValue);
					}
				}
				
				setAccess(currentUser, userSession);
			}
		}
	}

	public void deleteUser(Map<String, Object> user) {

		List<UserSessionUI> userSessions = getUserSessions(user);

		if (!CollectionUtils.isEmpty(userSessions)) {

			for (UserSessionUI userSession : userSessions) {

				if (!StringUtils.isEmpty(userSession.getSessionId())) {

					this.sessions.remove(userSession.getSessionId());
					
					log.info("Session id " + userSession.getSessionId() + " logged by user "
							+ userSession.getUser().get(UserSessionUI.USER_FIELD_USER) + " since "
							+ sdf.format(new Date(userSession.getLoginSince())) + " removed");
				}
			}
		}
	}

	private void setAccess(final Map<String, Object> user, UserSessionUI userSession) {

		if (!MapUtils.isEmpty(user) && userSession != null) {

			Long lockedUntil = user.get(UserSessionUI.USER_FIELD_LOCKED_UNTIL) != null
					? Long.parseLong(user.get(UserSessionUI.USER_FIELD_LOCKED_UNTIL).toString())
					: null;
			Long passwordExpiredAt = user.get(UserSessionUI.USER_FIELD_PASSWORD_EXPIRED_AT) != null
					? Long.parseLong(user.get(UserSessionUI.USER_FIELD_PASSWORD_EXPIRED_AT).toString())
					: null;
			Boolean allowMultipleSessions = user.get(UserSessionUI.USER_FIELD_ALLOW_MULTIPLE_SESSIONS) != null
					? Boolean.parseBoolean(user.get(UserSessionUI.USER_FIELD_ALLOW_MULTIPLE_SESSIONS).toString())
					: null;

			UserSessionUI userSessionLoaded = null;

			if (!MapUtils.isEmpty(this.sessions)) {
				
				synchronized (sessions) {

					userSessionLoaded = this.sessions.values().stream().filter(e -> e.getUser()
							.get(UserSessionUI.USER_FIELD_ID).equals(user.get(UserSessionUI.USER_FIELD_ID))).findFirst()
							.orElse(null);
				}
			}
			
			boolean userNoMultipleSessions = (allowMultipleSessions == null || !allowMultipleSessions.booleanValue())
					&& userSessionLoaded != null && userSessionLoaded.getAccess().equals(UserSessionUI.AUTHORIZED);

			userSession.setAccess(UserSessionUI.AUTHORIZED);

			if (lockedUntil != null && lockedUntil > Instant.now().toEpochMilli()) {

				userSession.setAccess(UserSessionUI.DENY_LOCKED);
			}
			// Password expired
			else if (passwordExpiredAt != null && passwordExpiredAt < Instant.now().toEpochMilli()) {

				userSession.setAccess(UserSessionUI.DENY_PASSWORD_EXPIRED);
			}
			// User without permission for multiple sessions
			else if (userNoMultipleSessions) {

				userSession.setAccess(UserSessionUI.DENY_MULTI_SESSION_NOT_ALLOWED);
			}
		}
	}

	

	private void removeSessionLoginRetriesBySessionId(String sessionId) {

		Map.Entry<Pair<String, String>, Integer> entrySessionLoginRetry = null;

		if (!MapUtils.isEmpty(this.sessionLoginRetries)) {

			synchronized (this.sessionLoginRetries) {

				entrySessionLoginRetry = this.sessionLoginRetries.entrySet().stream()
						.filter(e -> e.getKey().getKey().equals(sessionId)).findFirst().orElse(null);
			}

			if (entrySessionLoginRetry != null) {

				this.sessionLoginRetries.remove(entrySessionLoginRetry);
			}
		}
	}

	private UserSessionUI keepAlive(String sessionId) {

		UserSessionUI userSession = this.sessions.get(sessionId);

		if (userSession != null) {
			
			if (userSession.getAccess().equals(UserSessionUI.AUTHORIZED)) {
			
	
				Integer sessionExpireTimeSecs = userSession.getUser()
						.get(UserSessionUI.USER_FIELD_SESSION_EXPIRE_TIME_S) != null
								? (Integer) userSession.getUser().get(UserSessionUI.USER_FIELD_SESSION_EXPIRE_TIME_S)
								: DEFAULT_VALID_SESSION_SECS;
	
				if (sessionExpireTimeSecs > 0) {
	
					userSession.setValidUntil(Instant.now().toEpochMilli() + (sessionExpireTimeSecs * 1000));
				}
			}
			else {
				
				// Remove first time UserSession is not authorized.
				this.sessions.remove(userSession.getSessionId());
				log.info("Session id " + userSession.getSessionId() + " logged by user "
						+ userSession.getUser().get(UserSessionUI.USER_FIELD_USER) + " since "
						+ sdf.format(new Date(userSession.getLoginSince())) + " removed");
			}
		} 
		else {
			userSession = new UserSessionUI();

			userSession.setSessionId(sessionId);
			userSession.setAccess(UserSessionUI.UNAUTHORIZED);
		}

		return userSession;
	}

	private boolean allowLoginRetry(Map<String, Object> user, String sessionId) {

		boolean allowed = true;
		Integer userLoginRetries = Integer.valueOf(String.valueOf(systemParamBO.getSystemValue(SystemParamBO.SYSTEM_PARAM_USER_LOGIN_RETRIES,
				DEFAULT_LOGIN_RETRIES)));

		String userName = (String) user.get(UserSessionUI.USER_FIELD_USER);

		try {

			if (ObjectUtils.allNotNull(user, sessionId) && !StringUtils.isEmpty(userName)) {

				Long lockedUntil = user.get(UserSessionUI.USER_FIELD_LOCKED_UNTIL) != null
						? Long.parseLong(user.get(UserSessionUI.USER_FIELD_LOCKED_UNTIL).toString())
						: null;
				Integer sessionRetries = sessionLoginRetries.get(new ImmutablePair<>(sessionId, userName));

				if (userLoginRetries > 0) {

					if (sessionRetries == null) {

						sessionLoginRetries.put(new ImmutablePair<>(sessionId, userName), 1);
					} else {

						if (sessionRetries < userLoginRetries) {

							sessionLoginRetries.put(new ImmutablePair<>(sessionId, userName), sessionRetries + 1);
						} else {

							long dateLocked = Date.from(Instant.parse(DEFAULT_ACCOUNT_LOCKED_ISO_DATE)).getTime();

							Integer minutesToLockAccount = Integer.valueOf(String.valueOf(systemParamBO.getSystemValue(
									SystemParamBO.SYSTEM_PARAM_DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES,
									DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES)));

							dateLocked = (minutesToLockAccount != null
									? (Instant.now().toEpochMilli() + minutesToLockAccount * 60000)
									: dateLocked);

							if (lockedUntil == null || lockedUntil < dateLocked) {

								user.put(UserSessionUI.USER_FIELD_LOCKED_UNTIL, dateLocked);

								Map<String, Object> input = new HashMap<>();

								input.put(GraphqlTypeAdapter.USER_DATA, user);

								voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER, input);
								sessionLoginRetries.remove(new ImmutablePair<>(sessionId, userName));
							}

							allowed = false;
						}
					}
				}
			}
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			allowed = false;
		}

		return allowed;
	}

	private void activate(UserSessionUI userSession) {

		if (userSession != null && userSession.getUser() != null) {

			Long sessionExpireTimeS = userSession.getUser().get(UserSessionUI.USER_FIELD_SESSION_EXPIRE_TIME_S) != null
					? Long.parseLong(
							userSession.getUser().get(UserSessionUI.USER_FIELD_SESSION_EXPIRE_TIME_S).toString())
					: null;

			userSession.setLoginSince(Instant.now().toEpochMilli());

			String strTrace = "Session id " + userSession.getSessionId() + " logged by user "
					+ userSession.getUser().get(UserSessionUI.USER_FIELD_USER) + " at "
					+ sdf.format(new Date(userSession.getLoginSince()));

			if (sessionExpireTimeS != null && sessionExpireTimeS > 0L) {

				userSession.setValidUntil(Instant.now().toEpochMilli() + (sessionExpireTimeS * 1000));
			}
			else {
				
				userSession.setValidUntil(Instant.now().toEpochMilli() + (DEFAULT_VALID_SESSION_SECS * 1000));
			}
			
			strTrace += " is valid until " + sdf.format(new Date(userSession.getValidUntil()));

			sessions.put(userSession.getSessionId(), userSession);

			// Reset session login retries for http session
			sessionLoginRetries.remove(new ImmutablePair<>(userSession.getSessionId(), userSession.getUser()));

			log.info(strTrace);
		}
	}
	
	private Boolean sendAuditLog(UserSessionUI userSession, String auditLogSubtypeId, String level, String comments) {
		
		Map<String, Object> auditLog = new HashMap<>();
		Map<String, Object> auditLogs = Map.of(FIELD_AUDIT_LOGS, Arrays.asList(auditLog));
		
		auditLog.put(FIELD_AUDIT_LOG_TYPE_ID, AUDIT_LOG_TYPE);
		auditLog.put(FIELD_AUDIT_LOG_SUBTYPE_ID, auditLogSubtypeId);
		auditLog.put(FIELD_AUDIT_LOG_USER_ID, userSession.getUser().get(UserSessionUI.USER_FIELD_ID));
		auditLog.put(FIELD_AUDIT_LOG_LEVEL, level);
		
		Map<String, Object> logValueSession = Map.of(
				FIELD_AUDIT_LOG_VALUE_LOG_TYPE_PARAM_ID, AUDIT_LOG_USERS_PARAM_SESSION,
				FIELD_AUDIT_LOG_VALUE_VALUE, userSession.getSessionId());
		
		Map<String, Object> logValueComments = Map.of(
				FIELD_AUDIT_LOG_VALUE_LOG_TYPE_PARAM_ID, AUDIT_LOG_USERS_PARAM_COMMENT,
				FIELD_AUDIT_LOG_VALUE_VALUE, !StringUtils.isEmpty(comments) ? comments : "");
		
		List<Map<String, Object>> logValues = Arrays.asList(logValueSession, logValueComments);
		
		auditLog.put(FIELD_AUDIT_LOG_AUDIT_LOG_VALUES, logValues);
		
		return auditLogBO.send(auditLogs);
	}
	
	private UserSessionUI getUserSessionWithoutChanges(UserSessionUI userSession) {

		UserSessionUI userSessionCloned = SerializationUtils.clone(userSession);
		
		userSessionCloned.setEntitiesChanged(null);
		
		if (!userSession.getServerAvailable().booleanValue()) {
			
			userSessionCloned.setAccess(UserSessionUI.SERVER_NOT_AVAILABLE);
		}
		
		return userSessionCloned;
	}
}
