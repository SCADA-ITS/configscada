-- 
-- Table: conf.io_controller_modules
--
INSERT INTO conf.io_controller_modules
(module_type_id, module_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'MANGO', 'MANGO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.io_controller_module_values
--
INSERT INTO conf.io_controller_module_values
(module_type_id, module_id, module_type_param_id, value, enabled, visible, created_at, updated_at)VALUES
(1, 1, 1, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 3, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 4, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 5, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 6, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 7, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 8, 'user-agent', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 9, 'session-uid', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 10, '/api/v2/generic', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 11, '3000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 12, '20', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 13, 'http://192.168.88.163:8080/rest/v3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 14, 'http://192.168.88.163:8080/rest/v3/login', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 15, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 16, 'navarra3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 17, '/point-values/single-array/latest/{xids}?fields=VALUE&limit=1&useCache=CACHE_ONLY', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 18, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 19, '/point-values/{xid}?unitConversion=false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 20, 'PUT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 23, '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 26, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 27, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 28, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 29, '/users/current', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 30, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 31, '/data-points', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 32, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
