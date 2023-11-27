-- 
-- Table: conf.io_controller_modules
--
INSERT INTO conf.io_controller_modules
(module_type_id, module_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(3, 1, 'RTMS', 'RTMS', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.io_controller_module_values
--
INSERT INTO conf.io_controller_module_values
(module_type_id, module_id, module_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(3, 1, 1, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, '15 0/1 * 1/1 * ? *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 3, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
