-- 
-- Type:   modules
-- Entity: User service
-- File:   user_service.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES,
('es_ES', 1, 'LBL_SESSION_WITHOUT_COMM', 'Sesión cerrada por pérdida de comunicación.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_AUTHORIZED', 'Usuario autorizado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_DENY_PASSWORD_EXPIRED', 'Contraseña caducada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_DENY_LOCKED', 'Usuario bloqueado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_DENY_MULTI_SESSION_NOT_ALLOWED', 'Multi-sesión no permitida', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_DENY_OTHERS', 'Acceso denegado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_UNAUTHORIZED', 'Usuario no autorizado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SESSION_CLOSED_BY_EXCEED_IDLE_TIME', 'Sesión cerrada por tiempo de inactividad excedido.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_WRONG_USER_PASSWORD', 'Contraseña incorrecta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_SESSION_WITHOUT_COMM', 'Session without communication.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_AUTHORIZED', 'Authorized', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_DENY_PASSWORD_EXPIRED', 'Password expired', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_DENY_LOCKED', 'User is locked', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_DENY_MULTI_SESSION_NOT_ALLOWED', 'Multi-session not allowed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_DENY_OTHERS', 'Deny others', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_UNAUTHORIZED', 'Unauthorized', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SESSION_CLOSED_BY_EXCEED_IDLE_TIME', 'Session closed by exceed idle time.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_WRONG_USER_PASSWORD', 'Incorrect password', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--sk_SK
('sk_SK', 1, 'LBL_SESSION_CLOSED_BY_EXCEED_IDLE_TIME', 'končené z dôvodu nečinnosti', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_UNAUTHORIZED', 'neautorizovaný', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_SESSION_WITHOUT_COMM', 'bez komunikácie', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--es_CL
('es_CL', 1, 'LBL_SESSION_WITHOUT_COMM', 'Sesión cerrada por pérdida de comunicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_AUTHORIZED', 'Usuario autorizado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_DENY_PASSWORD_EXPIRED', 'Contraseña caducada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_DENY_LOCKED', 'Usuario bloqueado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_DENY_MULTI_SESSION_NOT_ALLOWED', 'Multi-sesión no permitida', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_DENY_OTHERS', 'Acceso denegado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_UNAUTHORIZED', 'Usuario no autorizado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SESSION_CLOSED_BY_EXCEED_IDLE_TIME', 'Sesión cerrada por tiempo de inactividad excedido.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_WRONG_USER_PASSWORD', 'Contraseña incorrecta', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
