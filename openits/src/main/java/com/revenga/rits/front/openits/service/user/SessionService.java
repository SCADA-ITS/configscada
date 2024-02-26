package com.revenga.rits.front.openits.service.user;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

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
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlTypeAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.bo.AuditLogBO;
import com.revenga.rits.front.openits.bo.SystemParamBO;
import com.revenga.rits.front.openits.bo.UserBO;
import com.revenga.rits.front.openits.model.EntityChangedUI;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.revenga.rits.front.openits.service.user.UserServer.SessionOperation;
import com.revenga.rits.front.openits.util.LDAPManager;
import com.revenga.rits.front.openits.util.LDAPManager.LDAPUser;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SessionService {
	
	private static final String CRON_EXPRESSION = "0 0/1 * 1/1 * ? *";
	
	protected Scheduler scheduler;
	protected boolean enabled;
	protected String cronExpression;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static final Boolean USERMGR_ENABLED = Boolean.valueOf(AppInfra.getProperty("usermgr.enabled"));
	private static final String AUTH_ENCRYPTED_PASSWORD = "auth.encryptedPassword";
	private static final Boolean LDAP_ENABLED = Boolean.valueOf(AppInfra.getProperty("ldap.enabled"));
	
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
	
	private Map<Pair<String, String>, Integer> sessionLoginRetries;
	private Map<String, UserSessionUI> sessions;
	
	private UserServer userServer;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	Environment env;

	@Autowired
	SystemParamBO systemParamBO;
	
	@Autowired
	AuditLogBO auditLogBO;
	
	@Autowired
	UserBO userBO;
	
	@Autowired
	UserManager userManager;
	
	@SuppressWarnings("unused")
	private class AuditLogLevel {
		
		public static final String TRACE = "TRACE";
		public static final String DEBUG = "DEBUG";
		public static final String INFO = "INFO";
		public static final String WARN = "WARN";
		public static final String ERROR = "ERROR";
		public static final String CRITICAL = "CRITICAL";
	}
	
	@PostConstruct
	private void initialize() {
		
		this.sessionLoginRetries = Collections.synchronizedMap(new HashMap<>());
		this.sessions = Collections.synchronizedMap(new HashMap<>());
		this.userServer = new UserServer(this);
		
		if (!StringUtils.isEmpty(CRON_EXPRESSION)) {
			
			initQuartzScheduler();
			
			launchScheduled();
			
			// All of the jobs have been added to the scheduler, but none of the jobs
		    // will run until the scheduler has been started
			try {
				if (!this.scheduler.isStarted()) {
					
					this.scheduler.start();
				}
			} catch (SchedulerException e1) {
				log.error(e1.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e1));
			}
		}
	}
	
	private void initQuartzScheduler() {
		
		try {
			
			SchedulerFactory sf = new StdSchedulerFactory();
			this.scheduler = sf.getScheduler();
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void launchScheduled() {
		
		try {
			
			// First we must get a reference to a scheduler

			this.scheduler.getContext().put(this.getClass().getSimpleName(), this);
			createJob(ScheduledUsersJob.class, CRON_EXPRESSION);
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	protected void createJob(Class<? extends Job> jobClass, String cronExpression) {
		
		try {
			
			// jobs can be scheduled before sched.start() has been called
			// job 1 will run every cron value
			
			JobDetail job = newJob(jobClass).withIdentity("job" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName()).build();
			
			if (!StringUtils.isEmpty(cronExpression)) {
				
				CronTrigger trigger = newTrigger().withIdentity("trigger" + jobClass.getSimpleName(), "group" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();
				
				Date dt = scheduler.scheduleJob(job, trigger);
				
				log.debug(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());
			}
			else {
				
				log.debug(job.getKey() + " is not scheduled");
			}
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public List<UserSessionUI> getSessions() {
		
		return this.sessions.values().stream().collect(Collectors.toList());
	}
	
	public UserSessionUI getSession(String sessionUID) {
		
		return this.sessions.get(sessionUID);
	}
	
	public UserSessionUI putSession(UserSessionUI userSessionUI) {
		
		return this.sessions.put(userSessionUI.getSessionId(), userSessionUI);
	}
	
	public UserSessionUI removeSession(String sessionUID) {
		
		return this.sessions.remove(sessionUID);
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
			
			LDAPUser ldapUser = null;
			
			// Request LDAP service if it's enabled in config.properties
			if (LDAP_ENABLED.booleanValue()) {
				
				ldapUser = LDAPManager.login(userName, password);
			}
			
			// Request user to db 
			Map<String, Object> user = userBO.getByName(userName);
			
			// Create user in db if it's ldap user and not exists
			if (user == null && ldapUser != null) {
				
				Map<String, Object> newLdapUser = createLdapUser(ldapUser, password); 
				
				if (newLdapUser != null) {
					
					user = userBO.getByName(userName);
				}
			}
			
			if (user != null) {
				
				// Update user if ldap password is not equals to user db
				if (ldapUser != null && !StringUtils.equals((String) user.get(UserSessionUI.USER_FIELD_PASSWORD), password)) {
						
					updateLdapUser(ldapUser, (String) user.get(UserSessionUI.USER_FIELD_ID), password);
				}
								
				if (isLockedUser(user)) {
					
					userSession.setAccess(UserSessionUI.DENY_LOCKED);
				}
				
				else {
					
					if (password.equals(user.get(UserSessionUI.USER_FIELD_PASSWORD)) || ldapUser != null) {
						
						user = vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_USER, user);
						
						if (user != null) {
							
							user.put(UserSessionUI.USER_FIELD_PASSWORD, null);
							
							userSession.setUser(user);

							setAccess(user, userSession);

							if (userSession.getAccess().equals(UserSessionUI.AUTHORIZED)) {

								activate(userSession);
								
								sendAuditLog(userSession, AUDIT_LOG_SUBTYPE_LOGIN, AuditLogLevel.INFO, null);
							}
						}
						
					} else {
						
						userSession.setAccess(allowLoginRetry(user, httpSession.getId())
								? UserSessionUI.WRONG_USER_PASSWORD
								: UserSessionUI.DENY_LOCKED);
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
			
			this.userServer.syncSession(SessionOperation.REMOVE_SESSION, userSession);
			
		} else {

			userSession = new UserSessionUI();

			userSession.setSessionId(httpSession.getId());
		}

		userSession.setAccess(UserSessionUI.LOGOUT);

		return userSession;
	}
	
	public UserSessionUI setManagementArea(String managementAreaId, HttpSession httpSession) {

		UserSessionUI userSession = this.sessions.get(httpSession.getId());

		if (userSession != null) {

			userSession.setManagementAreaId(!StringUtils.equals(managementAreaId, "-1") ? managementAreaId : null);
			
			log.info("Session id " + httpSession.getId() + " by user "
					+ userSession.getUser().get(UserSessionUI.USER_FIELD_USER) + " established management area to "
					+ (!StringUtils.isEmpty(managementAreaId) ? managementAreaId : "undefined"));			
			
			this.userServer.syncSession(SessionOperation.UPDATE_SESSION, userSession);
		} 

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
			
			this.sessions.values().forEach(userSession -> this.userServer.syncSession(SessionOperation.UPDATE_SESSION, userSession));
		}
	}

	public void updateUser(Map<String, Object> user) {

		List<UserSessionUI> userSessions = getUserSessions(user);

		if (!CollectionUtils.isEmpty(userSessions)) {

			for (UserSessionUI us : userSessions) {
				
				UserSessionUI userSession = this.sessions.get(us.getSessionId());
				
				userSession.setEditing(true);

				Map<String, Object> currentUser = userSession.getUser();

				for (Map.Entry<String, Object> currentUserfield : currentUser.entrySet()) {

					String key = currentUserfield.getKey();
					
					if (key.equals(UserSessionUI.USER_FIELD_PASSWORD)) {
						
						continue;
					}

					Object newValue = user.get(key);

					currentUser.put(key, newValue);
				}
				
				setAccess(currentUser, userSession);
				
				userSession.setEditing(false);
			}
			
			for (UserSessionUI us : userSessions) {
				
				UserSessionUI userSession = this.sessions.get(us.getSessionId());
				
				if (userSession != null) {
					
					this.userServer.syncSession(SessionOperation.UPDATE_SESSION, userSession);
				}
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
			
			for (UserSessionUI us : userSessions) {
				
				UserSessionUI userSession = this.sessions.get(us.getSessionId());
				
				if (userSession != null) {
					
					this.userServer.syncSession(SessionOperation.REMOVE_SESSION, userSession);
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
			else if (userNoMultipleSessions && !userSession.getEditing().booleanValue()) {

				userSession.setAccess(UserSessionUI.DENY_MULTI_SESSION_NOT_ALLOWED);
			}
			else {
				
				if (userSession.getManagementAreaId() != null) {
					
					Object managementAreasObject = user.get(UserSessionUI.USER_FIELD_MANAGEMENT_AREAS);
					
					if (managementAreasObject instanceof List) {
						
						List<Map<String, Object>> managementAreas = (List<Map<String, Object>>) managementAreasObject;
						
						if (managementAreas.stream().filter(e -> e.get(UserSessionUI.USER_FIELD_ID).equals(userSession.getManagementAreaId())).findFirst().orElse(null) == null) {
							
							userSession.setAccess(UserSessionUI.UNAUTHORIZED);
						}
					}
				}
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
					
					this.userServer.syncSession(SessionOperation.UPDATE_SESSION, userSession);
				}
			}
			else {
				
				// Remove first time UserSession is not authorized.
				this.sessions.remove(userSession.getSessionId());
				
				this.userServer.syncSession(SessionOperation.REMOVE_SESSION, userSession);
				
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
	
	private boolean isLockedUser(Map<String, Object> user) {
		
		boolean locked = false;
		
		Long lockedUntil = user.get(UserSessionUI.USER_FIELD_LOCKED_UNTIL) != null
				? Long.parseLong(user.get(UserSessionUI.USER_FIELD_LOCKED_UNTIL).toString())
				: null;
		
		locked = (lockedUntil != null && lockedUntil > Instant.now().toEpochMilli());
			
		return locked;
	}
	
	private Map<String, Object> getUserToPersistFromLDAPUser(LDAPUser ldapUser, String password) {
	
		Map<String, Object> newLdapUser = new HashMap<>();
		
		try {
			
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_NAME, ldapUser.getLogin());
			
			if (Boolean.parseBoolean(env.getProperty(AUTH_ENCRYPTED_PASSWORD))) {
				
				newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_PASSWORD, AES.encrypt(password));
			}
			else {
				
				newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_PASSWORD, password);
			}
			
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_PRESET, false);
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_ALLOW_MULTIPLE_SESSIONS, false);
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_EMAIL, StringUtils.defaultIfEmpty(ldapUser.getEmail(), ""));
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_FULLNAME, StringUtils.defaultIfEmpty(String.format("%s %s", ldapUser.getFirstName(), ldapUser.getLastName()), ""));
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_LDAP, true);
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_ROLE_ID, ldapUser.getRoleId());
			newLdapUser.put(UserSessionUI.USER_INPUT_FIELD_LOCALE_ID, ldapUser.getLocaleId());
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return newLdapUser;
	}
	
	private Map<String, Object> createLdapUser(LDAPUser ldapUser, String password) {
		
		Map<String, Object> createdUser = null;
		Map<String, Object> newLdapUser = getUserToPersistFromLDAPUser(ldapUser, password);
		Map<String, Object> input = new HashMap<>();
		
		if (!MapUtils.isEmpty(newLdapUser)) {
			
			input.put("user", newLdapUser);
			
			if (USERMGR_ENABLED.booleanValue()) {
				
				createdUser = userManager.createUser(input);
			}
			else {
				
				createdUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_CREATE_USER, input); 
			}
		}
			
		return createdUser;
	}
	
	private Map<String, Object> updateLdapUser(LDAPUser ldapUser, String id, String password) {
		
		Map<String, Object> updatedUser = null;
		Map<String, Object> updLdapUser = getUserToPersistFromLDAPUser(ldapUser, password);
		Map<String, Object> input = new HashMap<>();
		
		if (!MapUtils.isEmpty(updLdapUser)) {
			
			updLdapUser.put(UserSessionUI.USER_INPUT_FIELD_ID, id);
			
			input.put("user", updLdapUser);
			
			if (USERMGR_ENABLED.booleanValue()) {
				
				updatedUser = userManager.updateUser(input);
			}
			else {
				
				updatedUser = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_UPDATE_USER, input); 
			}
		}
			
		return updatedUser;
	}
	
//	private boolean authLdap(Map<String, Object> user, String password) {
//		
//		boolean ldapAllowed = true;
//		
//		Map<String, Object> userOptions = getUserOptions(user); 
//		
//		if (!MapUtils.isEmpty(userOptions)) {
//			
//			ldapAllowed = false;
//			
//			String ldapUserConnectionString = (String) userOptions.get(UserSessionUI.USER_OPTIONS_LDAP_USER_CONNECTION_STRING);
//			
//			LDAPDirectoryType ldapDirectoryType = List.of(LDAPDirectoryType.values()).stream()
//					.filter(e -> StringUtils.containsIgnoreCase(LDAP_DIRECTORY_TYPE, e.getType())).findFirst()
//					.orElse(null);
//			
//			if (ldapDirectoryType != null) {
//				
//				try (LDAPConnection ldapConnection = LDAPManagerOld.getConnection(ldapUserConnectionString, password, LDAP_URL, ldapDirectoryType)) {
//					
//					LDAPUserOld ldapUser = ldapConnection.getUser();
//					
//					if (ldapUser != null) {
//						
//						// Overwrite user password to next step verification
//						user.put(UserSessionUI.USER_FIELD_PASSWORD, password);
//						user.put(UserSessionUI.USER_FIELD_FULL_NAME, ldapUser.getCommonName());
//						
//						ldapAllowed = true;
//					}
//				
//				} catch (Exception e) {
//					
//					
//					log.error(e.getMessage());
//					log.debug(ExceptionUtils.getStackTrace(e));
//				}
//			}
//			else {
//				
//				log.error("Unsupported type of LDAP Server: Soported types are: " + LDAPDirectoryType.values().toString());			
//			}
//		}
//		
//		return ldapAllowed;
//	}
	
	
//	private Map<String, Object> getUserOptions(Map<String, Object> user) {
//		
//		Map<String, Object> userOptions = null;
//		
//		try {
//			
//			Object options = user.get(UserSessionUI.USER_OPTIONS); 
//			
//			if (options != null) {
//				
//				userOptions = ConversionUtil.stringToMap((String) options);
//			}
//			
//		} catch (JsonProcessingException e) {
//			
//			log.error(e.getMessage());
//			log.debug(ExceptionUtils.getStackTrace(e));
//		}
//		
//		return userOptions;
//	}

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
								
								userBO.updateUser(input);

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
			
			if (this.userServer.getOpenItsNode() != null) {
				
				userSession.setNodeName(this.userServer.getOpenItsNode().getName());
			}

			sessions.put(userSession.getSessionId(), userSession);

			this.userServer.syncSession(SessionOperation.CREATE_SESSION, userSession);
			
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
