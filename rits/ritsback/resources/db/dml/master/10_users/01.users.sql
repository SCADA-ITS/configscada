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
(1000, 1000, 'system', 'system', NULL, 86400, NULL, false, true, 'system', 'system@revenga.com', 'en_NZ', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



