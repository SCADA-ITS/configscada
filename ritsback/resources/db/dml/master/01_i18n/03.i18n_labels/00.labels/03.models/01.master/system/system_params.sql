-- 
-- Type:   model
-- Entity: master.system_params
-- File:   system_params.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
	 ('es_ES',1,'LBL_SYSTEM_PARAM_USER_LOGIN_RETRIES','Número máximo de intentos de inicio de sesión',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_DEFAULT_PASSWORD_EXPIRE_DAYS','Número de días de caducidad para la contraseña',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES','Tiempo de bloqueo de cuenta (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_DEFAULT_SESSION_EXPIRE_TIME_MINUTES','Tiempo de caducidad de sesión (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_TRAFFIC_PERIOD_TIME_MINUTES','Período de datos de tráfico (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_INIT_TRAFFIC_PERIOD_SECONDS','Segundo del cálculo de los datos de tráfico',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_WAITING_TIME_INCIDENTS_SECONDS','Tiempo para autoconfirmar incidencia (s)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_CRON_EXPRESION_DELETE_INCIDETS', 'Expresión CRON para el borrado de incidencias cerradas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_SMTP_CONFIG', 'Parámetros de configuración del servidor SMTP para envío de correos automáticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_DB_GIT_COMMIT', 'Id de git del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_DB_GIT_AUTHOR', 'Autor del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_DB_GIT_DATE', 'Fecha del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_DB_GIT_MESSAGE', 'Mensaje del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_MAX_UNRECOGNIZED_ALARMS_ALLOWED', 'Número máximo de alarmas permitidas sin reconocer en el sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_TRAVEL_TIME_PERIOD_TIME_MINUTES', 'Período de calculo de los tiempos de recorrido (min)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_MAX_SESSIONS', 'Número de sesiones máximas permitidas en el sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_SYSTEM_PARAM_AUDIT_LOGS', 'Número de logs de auditorías máximas permitidas en el sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


--en_GB
	 ('en_GB',1,'LBL_SYSTEM_PARAM_USER_LOGIN_RETRIES','Maximum number of login attempts',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_DEFAULT_PASSWORD_EXPIRE_DAYS','Number of days until password expiration',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES','Account lockout time (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_DEFAULT_SESSION_EXPIRE_TIME_MINUTES','Session expiration time (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_TRAFFIC_PERIOD_TIME_MINUTES','Traffic data period (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_INIT_TRAFFIC_PERIOD_SECONDS','Traffic data calculation second',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_WAITING_TIME_INCIDENTS_SECONDS','Time for auto-confirmation of incident (s)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_CRON_EXPRESION_DELETE_INCIDETS', 'CRON expression for deleting closed incidents', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_SMTP_CONFIG', 'SMTP server configuration parameters for sending automatic emails', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_DB_GIT_COMMIT', 'Git ID of the current version commit', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_DB_GIT_AUTHOR', 'Author of the current version commit', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_DB_GIT_DATE', 'Date of the current version commit', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_DB_GIT_MESSAGE', 'Message of the current version commit', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_MAX_UNRECOGNIZED_ALARMS_ALLOWED', 'Maximum number of alarms allowed without recognize in the system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_TRAVEL_TIME_PERIOD_TIME_MINUTES', 'Travel time calculation period (min)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_MAX_SESSIONS', 'Maximum number of allowed sessions in the system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_SYSTEM_PARAM_AUDIT_LOGS', 'Maximum number of audit logs allowed in the system', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	
--sk_SK

--es_CL
	 ('es_CL',1,'LBL_SYSTEM_PARAM_USER_LOGIN_RETRIES','Número máximo de intentos de inicio de sesión',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_DEFAULT_PASSWORD_EXPIRE_DAYS','Número de días de caducidad para la contraseña',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES','Tiempo de bloqueo de cuenta (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_DEFAULT_SESSION_EXPIRE_TIME_MINUTES','Tiempo de caducidad de sesión (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_TRAFFIC_PERIOD_TIME_MINUTES','Período de datos de tráfico (min)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_INIT_TRAFFIC_PERIOD_SECONDS','Segundo del cálculo de los datos de tráfico',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_WAITING_TIME_INCIDENTS_SECONDS','Tiempo para autoconfirmar incidencia (s)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_CRON_EXPRESION_DELETE_INCIDETS', 'Expresión CRON para el borrado de incidencias cerradas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_SMTP_CONFIG', 'Parámetros de configuración del servidor SMTP para envío de correos automáticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_DB_GIT_COMMIT', 'Id de git del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_DB_GIT_AUTHOR', 'Autor del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_DB_GIT_DATE', 'Fecha del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_DB_GIT_MESSAGE', 'Mensaje del commit de la versión actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_MAX_UNRECOGNIZED_ALARMS_ALLOWED', 'Número máximo de alarmas permitidas sin reconocer en el sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_TRAVEL_TIME_PERIOD_TIME_MINUTES', 'Período de calculo de los tiempos de recorrido (min)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_MAX_SESSIONS', 'Número de sesiones máximas permitidas en el sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL',1,'LBL_SYSTEM_PARAM_AUDIT_LOGS', 'Número de logs de auditorías máximas permitidas en el sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
