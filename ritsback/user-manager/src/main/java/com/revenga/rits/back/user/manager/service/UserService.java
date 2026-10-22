package com.revenga.rits.back.user.manager.service;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.helper.UserHelper;
import com.revenga.rits.back.front.core.model.vo.UserVO;

@Service
public class UserService {

	public UserVO get(String id) {
				
		Key key = new Key(id);
		
		User user = EntitiesManager.getInstance().getUser(Long.valueOf(key.getId(1)));
		
		if (user != null) {
		
			return UserHelper.get(user);
		}
		
		return null;
	}
	
	public UserVO create(UserVO user) {
		
		User newUser = UserHelper.get(user);
		User userCreated = null;
	
		userCreated = EntitiesManager.getInstance().createUser(newUser);
		
		if (userCreated != null) {
		
			return UserHelper.get(userCreated);
		}
		
		return null;
	}

	public UserVO update(UserVO user) {
		
		User updUser = UserHelper.get(user);
		User userUpdated = null;
	
		userUpdated = EntitiesManager.getInstance().updateUser(updUser);
		
		if (userUpdated != null) {
		
			return UserHelper.get(userUpdated);
		}
		
		return null;
	}

	public UserVO delete(UserVO user) {
		
		User delUser = UserHelper.get(user);
		User userDeleted = null;
		
		userDeleted = EntitiesManager.getInstance().updateUser(delUser);
		
		if (userDeleted != null) {
		
			return UserHelper.get(userDeleted);
		}
		
		return null;
	}
	
}
