package com.revenga.rits.back.data.core.model;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"sessionId"})
public class UserSession extends BasicElement {
	
	public static final String ALLOWED = "ALLOWED";
	public static final String DENY_PASSWORD_EXPIRED = "DENY_PASSWORD_EXPIRED";
	public static final String DENY_LOCKED = "DENY_LOCKED";
	public static final String DENY_MULTI_SESSION_NOT_ALLOWED = "DENY_MULTI_SESSION_NOT_ALLOWED";
	public static final String WRONG_USER_PASSWORD = "WRONG_USER_PASSWORD";
	public static final String DENY_OTHERS = "DENY_OTHERS";
	public static final String LOGOUT = "LOGOUT";
	
	public static final Integer SECURE_TOKEN_LENGTH = 20;
	

	private String sessionId;
	
	//private static final AtomicLong idSequence = new AtomicLong(Instant.now().toEpochMilli());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6330429433531818447L;
		
	private Long userId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"userId=id"})
	private User user;
	
	private String access;
	
	private Long loginSince;
	private Long validUntil;
	
	public String generateSessionId() {
		
		byte[] byteArray;
		
		SecureRandom secureRandom = new SecureRandom();
		
		byteArray = new byte[SECURE_TOKEN_LENGTH];
		secureRandom.nextBytes(byteArray);
		
		return new BigInteger(1, byteArray).toString(16);
	}
}
