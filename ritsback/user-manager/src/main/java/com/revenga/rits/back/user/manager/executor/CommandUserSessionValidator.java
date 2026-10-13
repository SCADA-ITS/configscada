package com.revenga.rits.back.user.manager.executor;

import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.data.core.model.command.SetKillUserCommand;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;
import com.revenga.rits.back.user.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandUserSessionValidator {

	GenericExecutor genericExecutor;
			
	public CommandUserSessionValidator(GenericExecutor genericExecutor) {
		
		super();
		
		this.genericExecutor = genericExecutor;	
	}
	
	public void killUser(SetKillUserCommand setKillUserCommand) {
		
		if (!CollectionUtils.isEmpty(setKillUserCommand.getUserIds())) {
		
			for (Long userId : setKillUserCommand.getUserIds()) {
				
				User user = EntitiesManager.getInstance().getUser(userId);
				
				Instant instantLocked = Instant.now().plusSeconds(setKillUserCommand.getLockedTimeS());
				
				if (user.getLockedUntil() == null || user.getLockedUntil() < instantLocked.toEpochMilli()) {
				
					user.setLockedUntil(instantLocked.toEpochMilli());
					
					if (EntitiesManager.getInstance().updateUser(user) != null) {
												
						killSession(userId);
						
						log.debug(
								(new Key(user)).getSimpleValue() + " locked until " + instantLocked.toString() + " by "
										+ (!StringUtils.isEmpty(setKillUserCommand.getUserid())
												? setKillUserCommand.getUserid()
												: "UNKNOWN"));
					}
				}
			}
		}
	}
	
	private void killSession(Long userId) {
		
		List<UserSession> userSessions = EntitiesManager.getInstance().getSessions(userId);
		
		if (!CollectionUtils.isEmpty(userSessions)) {
			
			for (UserSession userSession : userSessions) {
						
				EntitiesManager.getInstance().delSession(userSession);
			}
		}
	}
}