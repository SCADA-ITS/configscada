DELETE FROM conf.element_io_controller_modules;
DELETE FROM conf.io_controller_module_values;
DELETE FROM conf.io_controller_modules;

-- 
-- Table: conf.io_controller_modules
--
INSERT INTO conf.io_controller_modules
(module_type_id, module_id, alias, description, enabled, visible, created_at, updated_at)VALUES
(1, 1, 'MANGO', 'MANGO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'SOCKET_UNE', 'SOCKET_UNE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


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
(1, 1, 12, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 13, 'http://172.21.3.204:8080/rest/v1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 14, 'http://172.21.3.204:8080/rest/v2/login', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 15, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 16, 'DES_4942', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 17, '/point-values/{xids}/latest-multiple-points-single-array?limit=1&unitConversion=false&useCache=true&useRendered=false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 18, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 19, '/point-values?unitConversion=false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 20, 'PUT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 23, '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 26, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 27, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 28, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 29, '/users/current', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 30, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 31, '/data-points', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 32, 'GET', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4, 1, 1, 'ElementTypeParam:1:1:4001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 'ElementTypeParam:1:1:4002', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 3, 'ElementTypeParam:1:1:4003', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 4, 'ElementTypeParam:1:1:4004', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 5, 'ElementTypeParam:1:1:4005', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 6, '0x10 0x02 0x03 0x05 0x06', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 7, '0/30 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 8, '1048576', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 1024 * 124  1 mb
(4, 1, 9, '180000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 10, 'CRC_CCITT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 11, '50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 12, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

