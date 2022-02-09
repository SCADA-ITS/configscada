package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class UserSessionUI implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8887753141990261203L;
	
	public static final String AUTHORIZED = "AUTHORIZED";
	public static final String DENY_PASSWORD_EXPIRED = "DENY_PASSWORD_EXPIRED";
	public static final String DENY_LOCKED = "DENY_LOCKED";
	public static final String DENY_MULTI_SESSION_NOT_ALLOWED = "DENY_MULTI_SESSION_NOT_ALLOWED";
	public static final String WRONG_USER_PASSWORD = "WRONG_USER_PASSWORD";
	public static final String DENY_OTHERS = "DENY_OTHERS";
	public static final String LOGOUT = "LOGOUT";
	public static final String UNAUTHORIZED = "UNAUTHORIZED";
	
	public static final String USER_FIELD_ID = "id";
	public static final String USER_FIELD_USER = "name";
	public static final String USER_FIELD_PASSWORD = "password";
	public static final String USER_FIELD_LOCKED_UNTIL = "lockedUntil";
	public static final String USER_FIELD_PASSWORD_EXPIRED_AT = "passwordExpiredAt";
	public static final String USER_FIELD_ALLOW_MULTIPLE_SESSIONS = "allowMultipleSessions";
	public static final String USER_FIELD_SESSION_EXPIRE_TIME_S = "sessionExpireTimeS";
	
	public static final Integer SECURE_TOKEN_LENGTH = 20;
	
	private Map<String, Object> user;
	private String sessionId;
	private Long loginSince;
	private String access;
	private Long validUntil;
}

