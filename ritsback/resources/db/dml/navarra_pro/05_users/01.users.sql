-- 
-- Table: conf.roles
--

INSERT INTO conf.roles
(role_id, alias, description, preset, enabled, visible, created_at, updated_at) VALUES
(1, 'Administrador', 'Administrador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Visualizador', 'Visualizador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 'Eventos', 'Introducir incidencias programadas', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.role_permissions
--
INSERT INTO conf.role_permissions
(role_id, system_permission_id, enabled, visible, created_at, updated_at) VALUES
--Adminsitrador
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
(1, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 31, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 33, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 34, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 35, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 36, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Visualizador
(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Eventos
(1001, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--
INSERT INTO conf.users
(user_id, role_id, name_, password_, password_expired_at, session_expire_time_s, locked_until, preset, allow_multiple_sessions, full_name, email, locale_code,  enabled, visible, created_at, updated_at) VALUES
(1, 1, 'admin', 'iMTEltghfjLEY2kKHT5yyA==', NULL, NULL, NULL, true, true, 'System admin', 'admin@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'traficonavarra', 'g71Fo4TZU0Htr2bphklKtg==', NULL, NULL, NULL, true, true, 'System admin', 'admin@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'mlabatvi', 'UwoC4qpPOv8qNn/SNjrt6Q==', NULL, NULL, NULL, true, true, 'System admin', 'admin@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'jjboulandier', '+BtScbG7HCk2OH7pLQZXBw==', NULL, NULL, NULL, true, true, 'System admin', 'admin@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'alegea', '5YC8xEpktJHuKq0QO6XHPA==', NULL, NULL, NULL, true, true, 'System admin', 'admin@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 1, 'jorozpas', 'GseY8QwrOOBHFeFLdMb7Xg==', NULL, NULL, NULL, false, true, 'Jokin Oroz  Pascual', 'jorozpas@navarra.es', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


