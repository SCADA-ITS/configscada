package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class UserSessionRepository extends EntityRepository implements EntityCacheable<UserSession> {

	// region Constructors
	public UserSessionRepository() {

		super(UserSession.class);
	}

	public UserSessionRepository(Boolean cacheable) {

		super(UserSession.class, cacheable);
	}

	public UserSessionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(UserSession.class, cacheable, cacheRetentionTime);
	}
	
	// endregion Constructors

	// region getters
	public UserSession get(String sessionId, Boolean fillObject) {

		return (UserSession) getFromCache(new String[] { sessionId }, fillObject);
	}
	
	public List<UserSession> getSessionsByUser(Long userId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			List<UserSession> userSessions = basicElements.stream()
					.map(be -> (UserSession) be).collect(Collectors.toList());
			
			return userSessions.stream().filter(e -> e.getUserId().equals(userId)).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

	@Override
	public List<UserSession> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (UserSession) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public UserSession put(UserSession entity) {

		return (UserSession) putFromCache(entity);
	}

	@Override
	public List<UserSession> put(List<UserSession> entities) {

		List<BasicElement> basicElements = entities.stream().map(userSession -> (BasicElement) userSession)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (UserSession) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public UserSession del(UserSession entity) {

		return (UserSession) delFromCache(entity);

	}

	@Override
	public List<UserSession> del(List<UserSession> entities) {

		List<BasicElement> basicElements = entities.stream().map(userSession -> (BasicElement) userSession)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (UserSession) be).collect(Collectors.toList());
	}
	// endregion dels

}
