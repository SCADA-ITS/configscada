-- 
-- Table: conf.roles
--

INSERT INTO conf.roles
(role_id, alias, description, preset, enabled, visible, created_at, updated_at) VALUES
(1, 'Administrador', 'Administrador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Operador', 'Operador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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

--Visualizador
(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--
INSERT INTO conf.users
(user_id, role_id, name_, password_, password_expired_at, session_expire_time_s, locked_until, preset, allow_multiple_sessions, full_name, email, locale_code,  enabled, visible, created_at, updated_at) VALUES
--Administradores
(1, 1, 'admin', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'System admin', 'admin@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'admin_en', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'System admin en_GB', 'admin_GB@revenga.com', 'en_GB', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Operadores
(3, 2, 'oper_tun', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator traffic tunel', 'oper_tun@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'oper_tun_en', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator traffic tunel en_GB', 'oper_tun_GB@revenga.com', 'en_GB', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 2, 'oper_map', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator traffic map', 'oper_map@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 'oper_map_en', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator traffic map en_GB', 'oper_map_GB@revenga.com', 'en_GB', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(7, 2, 'oper_hosp', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator hospital', 'oper_hosp@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'oper_hosp_en', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator hospital en_GB', 'oper_hosp_GB@revenga.com', 'en_GB', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 2, 'oper_nuc', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator nuclear system', 'oper_nuc@revenga.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2, 'oper_nuc_en', 'PTp8841/XyOkxeVUCyNf+w==', NULL, NULL, NULL, true, true, 'Operator nuclear system en_GB', 'oper_nuc_GB@revenga.com', 'en_GB', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



