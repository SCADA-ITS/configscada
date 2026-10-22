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
(1, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--
INSERT INTO conf.users
(user_id, role_id, name_, password_, password_expired_at, session_expire_time_s, locked_until, preset, allow_multiple_sessions, full_name, email, locale_code, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'admin', 'hcebhlEAlBBNaZcdLdmvuQ==', NULL, NULL, NULL, True, True, 'System admin', 'admin@rits.com', 'es_ES', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'oper', 'orxsJ0MVliLpHct5TvKgLQ==', NULL, 300, NULL, True, False, 'System operator', 'oper@rits.com', 'es_ES', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 'administrator@revenga.com', '9P9fv9BLf0BT+JXg3JpaDQ==', NULL, 86400, NULL, False, True, 'Administrator', 'administrator@revenga.com', 'en_GB', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 2, 'MINDOP', 'imNhuVmHHv3EZC9wXi+RFA==', NULL, 86400, NULL, False, True, 'PA', 'mindop@mindop.sk', 'sk_SK', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 2, 'INDEPEN', 'nMkcb/wRzflYVr0o1xLQ5A==', NULL, 86400, NULL, False, True, 'PA', 'indepen@mindop.sk', 'sk_SK', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 1, 'rchvila@obchvatnula.com', 'vCWOPa5fO+xmt4wewVdx3Q==', NULL, 86400, NULL, False, True, 'PA', 'rchvila@obchvatnula.com', 'sk_SK', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 1, 'jgallo', 'R+NnBPPsVHsFo85TZtAsDg==', NULL, NULL, NULL, False, True, '', '', 'sk_SK', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'operator', 'dU4VZb2v3lEsBUYECmBZ/A==', NULL, 86400, NULL, False, True, 'Operator', 'd4r7@d4r7-obchvatnula.com', 'sk_SK', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'admin@revenga.com', 'duf21Lj77UUI555TYdODmw==', NULL, 86400, NULL, False, True, 'admin', 'admin@revenga.com', 'es_ES', True, True, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.user_permissions
--

