-- 
-- Table: conf.roles
--

INSERT INTO conf.roles
(role_id, alias, description, preset, enabled, visible, created_at, updated_at) VALUES
(1000, 'System', 'System', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--

INSERT INTO conf.users
(user_id, role_id, name_, password_, password_expired_at, session_expire_time_s, locked_until, preset, allow_multiple_sessions, full_name, email, locale_code, enabled, visible, created_at, updated_at) VALUES
(1000, 1000, 'system', 'LLjK9seqPVh5w+stntPrPg==', NULL, 86400, NULL, false, true, 'system', 'system@revenga.com', 'en_GB', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1000, 'root', 'jbx7UfBNOs4npveLA8HSgA==', NULL, NULL, NULL, true, true, 'root', 'system@rits.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.user_permissions
(user_id, system_permission_id, enabled, visible, created_at, updated_at) VALUES
(2000, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.management_area_users (management_area_id, user_id, enabled, visible, created_at, updated_at) VALUES

(2000, 1000, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2000, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
