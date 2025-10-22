-- 
-- Table: conf.roles
--

INSERT INTO conf.roles
(role_id, alias, description, preset, enabled, visible, created_at, updated_at) VALUES
(1, 'Administrators', 'System administrators', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Operators', 'System operators', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.role_permissions
--
INSERT INTO conf.role_permissions
(role_id, system_permission_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--
INSERT INTO conf.users
(user_id, role_id, name_, password_, password_expired_at, session_expire_time_s, locked_until, preset, allow_multiple_sessions, full_name, email, locale_code, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'admin', 'hcebhlEAlBBNaZcdLdmvuQ==', NULL, NULL, NULL, true, true, 'System admin', 'admin@rits.com', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'oper', 'hcebhlEAlBBNaZcdLdmvuQ==', NULL, 300, NULL, true, true, 'System operator', 'oper@rits.com', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'aortiztr', 'uS0w/QXS6CoMzB+AlcWY4A==', NULL, 300, NULL, true, true, 'System operator', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'msanhueza', 'Nw9OCrNQsjMugZM+Ag99XQ==', NULL, 300, NULL, true, true, 'System operator', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'istruckrath', '6HgymZBA6VIHPmNJ42bI+g==', NULL, 300, NULL, true, true, 'System operator', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 'rcontreras', 'UX3jR03q6EcLfC7Eyk2u9A==', NULL, 300, NULL, true, true, 'System operator', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 2, 'amanriquez', 'sq4HoCXQ9GVfHVx62O+P9Q==', NULL, 300, NULL, true, true, 'System operator', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'gonzaloroa', 'qWnOGZ1JGW0sxeoIIPXprg==', NULL, 300, NULL, true, true, 'System operator', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 'cserrano', 'EOTGE5fynKZZFc92BooLCg==', NULL, 300, NULL, true, true, 'System admin', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1, 'pgaldames', 'f/cCPQcasBnD+FI6s/ar3g==', NULL, 300, NULL, true, true, 'System admin', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 'avillaroel', 'y39LBYK+sOEiAz0LKWHkGQ==', NULL, 300, NULL, true, true, 'System admin', '', 'es_CL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.user_permissions
--

