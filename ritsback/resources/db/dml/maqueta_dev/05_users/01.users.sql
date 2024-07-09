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
(1, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--
INSERT INTO conf.users
(user_id, role_id, name_, password_, password_expired_at, session_expire_time_s, locked_until, preset, allow_multiple_sessions, full_name, email, locale_code, ldap, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'admin', 'admin', NULL, NULL, NULL, true, true, 'System admin', 'admin@rits.com', 'es_ES', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'oper', 'oper', NULL, 300, NULL, true, false, 'System operator', 'oper@rits.com', 'es_ES', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'admin@revenga.com', 'admin123', NULL, 86400, NULL, false, true, 'admin', 'admin@revenga.com', 'en_GB', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'operator@revenga.com', 'operator', NULL, 86400, NULL, false, true, 'Operator', 'operator@revenga.com', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 'administrator@revenga.com', 'adminadmin', NULL, 86400, NULL, false, true, 'Administrator', 'administrator@revenga.com', 'en_GB', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 2, 'MINDOP', 'mINDOP$$1', NULL, 86400, NULL, false, true, 'PA', 'mindop@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'MINDOP2', 'MINDoP$$2', NULL, 86400, NULL, false, true, 'PA', 'mindop2@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 'MINDOP3', 'MiNDOP$$3', NULL, 86400, NULL, false, true, 'PA', 'mindop3@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 'MINDOP4', 'MINdOP$$4', NULL, 86400, NULL, false, true, 'PA', 'mindop4@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2, 'MINDOP5', 'MINDOp$$5', NULL, 86400, NULL, false, true, 'PA', 'mindop5@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 2, 'MINDOP6', 'MInDOP$$6', NULL, 86400, NULL, false, true, 'PA', 'mindop6@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 2, 'MINDOP7', 'mINdOp$$7', NULL, 86400, NULL, false, true, 'PA', 'mindop7@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 2, 'INDEPEN', 'IndePEN$$1', NULL, 86400, NULL, false, true, 'PA', 'indepen@mindop.sk', 'sk_SK', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 2, 'larrieta', 'LaRRieta$$1', NULL, 86400, NULL, false, true, 'PA', 'larrieta@obchvatnula.com', 'en_GB', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, 'adm', 'GGB2V/Dcxx8SzNE+31nLrA==', NULL, NULL, NULL, true, true, 'adm', 'admin@revenga.com', 'es_ES', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.user_permissions
--
INSERT INTO conf.user_permissions
(user_id, system_permission_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
