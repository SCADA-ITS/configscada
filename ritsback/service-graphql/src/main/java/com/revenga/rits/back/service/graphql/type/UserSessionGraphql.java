package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.front.core.model.helper.UserSessionHelper;
import com.revenga.rits.back.front.core.model.vo.UserSessionVO;

public class UserSessionGraphql extends UserSessionVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7880442964139460503L;

	public UserSessionGraphql(final UserSession userSession) {

		UserSessionHelper.assign(userSession, this);

	}
}
