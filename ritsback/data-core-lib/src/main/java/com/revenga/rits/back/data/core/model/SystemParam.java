package com.revenga.rits.back.data.core.model;

import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = {"id"})
public class SystemParam extends BasicElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -802619031564919866L;
	
	// region data types constants

	public static final int SYSTEM_PARAM_DEFAULT_SESSION_EXPIRE_TIME_MINUTES = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "system.param.default_session_expire_time_minutes"));
	public static final int SYSTEM_PARAM_USER_LOGIN_RETRIES = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "system.param.user_login_retries"));
	public static final int SYSTEM_PARAM_DEFAULT_PASSWORD_EXPIRE_DAYS = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "system.param.default_password_expire_days"));
	public static final int SYSTEM_PARAM_DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "system.param.default_account_locked_time_minutes"));
	public static final int SYSTEM_PARAM_WAITING_TIME_UNTIL_AUTO_CONFIRM_INCIDENTS = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "system.param.waiting_time_until_auto_contirm_incidents"));
	public static final int SYSTEM_PARAM_SYSTEM_PARAM_CRON_EXPRESION_DELETE_INCIDETS = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "system.param.cron_expression_delete_incidents"));
	public static final int SYSTEM_PARAM_SMTP_CONFIG = Integer.parseInt(ConfigProperty.getInstance()
			.getProperty(BasicElement.FILE_CONSTANTS, "system.param.smtp_config"));
	// endregion data types constants
}
