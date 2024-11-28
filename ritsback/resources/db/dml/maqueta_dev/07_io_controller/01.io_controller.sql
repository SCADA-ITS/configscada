DELETE FROM conf.element_io_controller_modules;
DELETE FROM conf.io_controller_module_values;
DELETE FROM conf.io_controller_modules;

-- 
-- Table: conf.io_controller_modules
--
INSERT INTO conf.io_controller_modules
(module_type_id, module_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'MANGO', 'MANGO', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'CITILOG', 'CITILOG', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'RTMS', 'RTMS', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, ' METEO_SOCKET_UNE', 'METEO_SOCKET_UNE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




-- 
-- Table: conf.io_controller_module_values
--
INSERT INTO conf.io_controller_module_values
(module_type_id, module_id, module_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
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
-- Mango 3
(1, 1, 13, 'http://192.168.88.162:8080/rest/v1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 14, 'http://192.168.88.162:8080/rest/v2/login', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Mango 5
--(1, 1, 13, 'http://192.168.243.222:8080/rest/v3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 1, 14, 'http://192.168.243.222:8080/rest/v3/login', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 15, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 16, 'frontend', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Mango 3
(1, 1, 17, '/point-values/{xids}/latest-multiple-points-single-array?limit=1&unitConversion=false&useCache=true&useRendered=false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Mango 5
--(1, 1, 17, '/point-values/single-array/latest/{xids}?fields=VALUE&limit=1&useCache=CACHE_ONLY', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 18, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Mango 3
(1, 1, 19, '/point-values?unitConversion=false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Mango 5
--(1, 1, 19, '/point-values/{xid}?unitConversion=false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 20, 'PUT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 23, '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 26, '300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 27, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 28, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 29, '/users/current', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 30, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Mango 5
--(1, 1, 31, '/data-points', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(1, 1, 32, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 1, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 2, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 3, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 4, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 5, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 6, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 7, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 8, '0/30 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(2, 1, 9, '192.168.88.114', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 9, '192.168.81.1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 10, '44000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 1, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 3, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(4, 1, 1, 'ElementTypeParam:1:1:4001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 'ElementTypeParam:1:1:4002', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 3, 'ElementTypeParam:1:1:4003', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 4, 'ElementTypeParam:1:1:4004', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 5, 'ElementTypeParam:1:1:4005', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 6, '0x10 0x20 0x30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 7, '0/30 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 8, '1048576', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 1024 * 124  1 mb
(4, 1, 9, '180000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 10, 'CRC_CCITT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 11, '50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 12, '500', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.element_io_controller_modules
--
INSERT INTO conf.element_io_controller_modules
(element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(1, 2, 4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 4, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);






