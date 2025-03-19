-- 
-- Table: conf.roles
--

INSERT INTO conf.roles
(role_id, alias, description, preset, enabled, visible, created_at, updated_at) VALUES
(1, 'Administrador', 'Administrador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Operador', 'Operador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Coordinador', 'Coordinador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Visualizador', 'Visualizador del sistema', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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

--Operador
(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
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

--Coordinador
(3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Visualizador
(4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--
INSERT INTO conf.users
(user_id, role_id, name_, password_, password_expired_at, session_expire_time_s, locked_until, preset, allow_multiple_sessions, full_name, email, locale_code,  enabled, visible, created_at, updated_at) VALUES
(1, 1, 'administrador', 'g6dmWgvubUf+S4s1hlEOpw==', NULL, NULL, NULL, true, true, 'Internal admin', 'administrador@rits.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'operador', '+h6zFJYmXAgFDSBlq6LFSw==', NULL, NULL, NULL, true, true, 'Internal operator', 'operador@rits.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 'visualizador', 'uKtqDqv/9iQdwi9kc+eKPg==', NULL, NULL, NULL, true, true, 'Internal visualizator', 'visualizator@rits.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 1, 'admin', 'Ren+avYOP7drGR8ltBGb4A==', NULL, NULL, NULL, true, true, 'System admin', 'admin@rits.com', 'es_ES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.user_permissions
--

