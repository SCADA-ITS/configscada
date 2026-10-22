DELETE FROM master.system_params;

-----
--
-- INSERTS FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.system_params
--

INSERT INTO master.system_params
(system_param_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2, 'user_login_retries', 'number of user login retries', 'LBL_SYSTEM_PARAM_USER_LOGIN_RETRIES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'default_password_expire_days', 'number of days to expire password', 'LBL_SYSTEM_PARAM_DEFAULT_PASSWORD_EXPIRE_DAYS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'default_account_locked_time_minutes', 'number of minutes locked an acount', 'LBL_SYSTEM_PARAM_DEFAULT_ACCOUNT_LOCKED_TIME_MINUTES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'default_session_expire_time_minutes', 'default number of minutes session timeout', 'LBL_SYSTEM_PARAM_DEFAULT_SESSION_EXPIRE_TIME_MINUTES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'traffic_period_time_minutes', 'traffic period time minutes [1 - 30]', 'LBL_SYSTEM_PARAM_TRAFFIC_PERIOD_TIME_MINUTES', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'init_traffic_period_seconds', 'init traffic period seconds [0 - 59]', 'LBL_SYSTEM_PARAM_INIT_TRAFFIC_PERIOD_SECONDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'waiting_time_until_auto_contirm_incidents', 'waiting time until auto confirm incidents in seconds', 'LBL_SYSTEM_PARAM_WAITING_TIME_INCIDENTS_SECONDS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'cron_expression_delete_incidents', 'cron expression for delete closed incidets report', 'LBL_SYSTEM_PARAM_CRON_EXPRESION_DELETE_INCIDETS', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'smtp_config', 'smtp config', 'LBL_SYSTEM_PARAM_SMTP_CONFIG', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);