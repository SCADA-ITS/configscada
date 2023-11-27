-- 
-- Table: conf.io_controller_modules
--
INSERT INTO conf.io_controller_modules
(module_type_id, module_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(2, 1, 'CITILOG', 'CITILOG', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.io_controller_module_values
--
INSERT INTO conf.io_controller_module_values
(module_type_id, module_id, module_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(2, 1, 1, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 2, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 3, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 4, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 5, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 6, '0 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 7, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 8, '0/30 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 9, '10.133.50.33', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 10, '44000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);