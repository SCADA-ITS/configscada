package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.helper.UserHelper;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.type.UserGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService implements QueryInterface<UserVO>, MutationInterface<UserVO> {

	@Override
	public List<UserVO> getAll() {

		List<UserVO> users = new ArrayList<>();

		for (User user : EntitiesManager.getInstance().getUserRepository().getAll(false)) {

			users.add(new UserGraphql(user));
		}

		return users;
	}

	@Override
	public Optional<UserVO> get(String userId) {

		Optional<UserVO> userVO = null;

		try {
			Key key = new Key(userId);

			if (key.getClazz() == User.class) {

				User user = EntitiesManager.getInstance().getUserRepository().get(Long.valueOf(key.getId(1)), false);

				if (user != null) {

					userVO = Optional.of(new UserGraphql(user));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: User");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return userVO;
	}

	@Override
	public Optional<UserVO> create(UserVO userVO) {

		Optional<UserVO> resp = null;

		if (userVO != null) {

			User user = UserHelper.get(userVO);

			User userCreated = EntitiesManager.getInstance().getUserRepository().create(user);

			if (userCreated != null) {

				resp = Optional.of(new UserGraphql(userCreated));
			}
		}

		return resp;
	}

	@Override
	public Optional<UserVO> update(UserVO userVO) {

		Optional<UserVO> resp = null;

		if (userVO != null) {

			User user = UserHelper.get(userVO);

			User userUpdated = EntitiesManager.getInstance().getUserRepository().update(user);

			if (userUpdated != null) {

				resp = Optional.of(new UserGraphql(userUpdated));
			}
		}

		return resp;

	}

	@Override
	public Optional<UserVO> delete(String userId) {

		Optional<UserVO> resp = null;

		if (userId != null) {

			UserVO userVO = new UserVO();
			userVO.setId(userId);

			User user = UserHelper.get(userVO);

			User userDeleted = EntitiesManager.getInstance().getUserRepository().delete(user);

			if (userDeleted != null) {

				resp = Optional.of(new UserGraphql(userDeleted));
			}
		}

		return resp;
	}

	@Override
	public Optional<UserVO> clone(String userVO) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean sendKillUser(List<String> userIds, Integer lockedTimeS) {

		Boolean resp = false;

		if (!CollectionUtils.isEmpty(userIds)) {

			List<Long> list = new ArrayList<>();

			for (String userId : userIds) {

				Key key = new Key(userId);

				list.add(Long.valueOf(key.getId(1)));
			}

			return EntitiesManager.getInstance().getUserRepository().sendKillUser(list, lockedTimeS);
		}

		return resp;
	}

	@Override
	public List<UserVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
