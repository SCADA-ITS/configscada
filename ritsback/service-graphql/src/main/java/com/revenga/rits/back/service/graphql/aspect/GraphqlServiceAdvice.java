package com.revenga.rits.back.service.graphql.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class GraphqlServiceAdvice {

	@Before("execution(public * *(..)) && @annotation(com.revenga.rits.back.service.graphql.annotation.EntitiesContext)")
	public void before(JoinPoint joinPoint) {

//		EntitiesManager.getInstance().createEntitiesContext();
	}
	
//	@Around("execution(public * *(..)) && @annotation(com.revenga.rits.back.service.graphql.annotation.EntitiesContext)")
//	public Object around(ProceedingJoinPoint call) {
//
//		Object object = null;
//		
//		try {
//			
//			Instant instant = Instant.now();
//			
//			object = call.proceed();
//			
//			log.debug("[ThreadId = " + Thread.currentThread().getId() + "] => " + call.getSignature().getName() + " ("
//					+ Duration.between(instant, Instant.now()).toMillis() + " ms.)");
//		} catch (Throwable e) {
//			
//			log.error(e.getMessage());
//			log.debug(ExceptionUtils.getStackTrace(e));
//		}
//		
//		return object;
//	}
}