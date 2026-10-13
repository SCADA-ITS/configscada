package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.front.core.model.vo.UserSessionVO;

public class UserSessionHelper {

	private UserSessionHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static UserSessionVO get(final UserSession userSession) {

		UserSessionVO userSessionVO = new UserSessionVO();
		
		assign(userSession, userSessionVO);
		
		return userSessionVO;
	}

	public static void assign(final UserSession userSession, UserSessionVO userSessionVO ) {

		if (ObjectUtils.allNotNull(userSession, userSessionVO)) {
		
			BasicElementHelper.fillBasicProperties(userSessionVO, userSession);

		
			userSessionVO.setSessionId(userSession.getSessionId());
			userSessionVO.setAccess(userSession.getAccess());
			userSessionVO.setValidUntil(userSession.getValidUntil());
			
			if (userSession.getUserId() != null) {
				
				User user = new User();
				user.setId(userSession.getUserId());
				userSessionVO.setUserId(BasicElementHelper.getIdFromElement(user));
			}

			if (userSession.getUserId() == null && userSession.getUser() != null && userSession.getUser().getId() != null) {

				userSessionVO.setUserId(BasicElementHelper.getIdFromElement(userSession.getUser()));
			}
		}
	}
	
}
