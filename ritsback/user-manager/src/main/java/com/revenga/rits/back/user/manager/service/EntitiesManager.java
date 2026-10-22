package com.revenga.rits.back.user.manager.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.entities.lib.repository.UserRepository;
import com.revenga.rits.back.entities.lib.repository.UserSessionRepository;

public class EntitiesManager {

	UserRepository userRepository;
	UserSessionRepository userSessionRepository;
	SystemParamValueRepository systemParamValueRepository;

	private static EntitiesManager instance; 
	
	public static EntitiesManager getInstance() {
		
		if (instance == null) {
			
			instance = new EntitiesManager();
		}
		
		return instance;
	}
	
	private EntitiesManager() {
		
		this.userRepository = new UserRepository(false);
		this.userSessionRepository = new UserSessionRepository(false);
		this.systemParamValueRepository = new SystemParamValueRepository(true);
	}
	
	public List<User> getUsers() {
		
		return userRepository.getAll(false);
	}
	
	public User getUser(Long userId) {
		
		return userRepository.get(userId, true);
	}
	
	public User getUser(String userName) {
		
		List<User> users = getUsers();
		User user = null;
		
		if (!CollectionUtils.isEmpty(users)) {
			
			user = users.stream().filter(e -> e.getName().equals(userName)).findFirst().orElse(null);
		}
		
		return user;	
	}
	
	public User createUser(User user) {
		
		return userRepository.create(user);
	}
	
	public User updateUser(User user) {
		
		return userRepository.update(user);
	}
	
	public User deleteUser(User user) {
		
		return userRepository.delete(user);
	}
		
	public UserSession getSession(String sessionId) {
		
		return userSessionRepository.get(sessionId, true);
	}
	
	public List<UserSession> getSessions(Long userId) {
		
		return userSessionRepository.getSessionsByUser(userId, false);
	}
	
	public UserSession putSession(UserSession userSession) {
		
		return userSessionRepository.put(userSession);
	}
	
//	public UserSession expireSession(UserSession userSession) {
//				
//		if (userSession.getValidUntil() != null) {
//			
//			Duration duration = Duration.between(Instant.now(), Instant.ofEpochMilli(userSession.getValidUntil()));
//			
//			return userSessionRepository.expire(userSession, (int) duration.getSeconds());
//		}
//		
//		return userSession;
//	}
	
	public UserSession delSession(UserSession userSession) {
		
		return userSessionRepository.del(userSession);
	}
	
	public List<UserSession> delSessions() {
		
		List<UserSession> userSessions = userSessionRepository.getAll(false);
		
		if (!CollectionUtils.isEmpty(userSessions)) {
			
			userSessions.forEach(e -> userSessionRepository.del(e));
		}
		
		return userSessions;
	}
	
	public SystemParamValue getSystemParamValue(Long id) {
		
		return systemParamValueRepository.get(id, false);
	}
}
