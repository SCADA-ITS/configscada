package com.revenga.rits.front.openits.aspect;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revenga.rits.front.openits.bo.SessionBO;
import com.revenga.rits.front.openits.controller.AuthController;
import com.revenga.rits.front.openits.model.UserSessionUI;

import lombok.Getter;

@Aspect
@Configuration
public class SessionAspect {

	private static final String AUTH_REQUIRED = "auth.required";
	
	@Autowired
	SessionBO sessionBO;
	
	@Autowired
	private Environment env;
	
	@Getter
	private Boolean authRequired;
	

	public class SessionThrowable extends Throwable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6245436481462505522L;

	}
	
	@PostConstruct
	private void initialize() {
		
		this.authRequired = Boolean.parseBoolean(env.getProperty(AUTH_REQUIRED));
	}

	@Before("execution(* com.revenga.rits.front.openits.controller.*.*(..))")
	public void before(JoinPoint jp) throws SessionThrowable {

		if (this.authRequired.booleanValue() && !jp.getTarget().getClass().equals(AuthController.class)) {

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession httpSession = attr.getRequest().getSession(true);
			Map<String, String> headers = Collections.list(attr.getRequest().getHeaderNames()).stream()
					.collect(Collectors.toMap(h -> h, attr.getRequest()::getHeader));

			UserSessionUI userSession = sessionBO.keepAlive(headers, httpSession); 
			
			if (userSession == null || userSession.getAccess().equals(UserSessionUI.UNAUTHORIZED)) {

				throw new SessionThrowable();
			}
		}
	}

	@Around("execution(* com.revenga.rits.front.openits.controller.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) {

		Object response = null;
		UserSessionUI userSession = null;

		try {
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession httpSession = attr.getRequest().getSession(true);
			
			userSession = new UserSessionUI();
			
			userSession.setSessionId(httpSession.getId());
			userSession.setAccess(UserSessionUI.UNAUTHORIZED);
			
			response = pjp.proceed();

		} catch (Throwable t) {

			if (t.getClass().equals(SessionThrowable.class)) {
								
				return new ResponseEntity<>(userSession != null ? userSession : UserSessionUI.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
			}
		}

		return response;

	}

}
