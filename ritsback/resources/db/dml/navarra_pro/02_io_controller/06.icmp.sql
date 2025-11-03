-- 
-- Table: conf.io_controller_modules
--
INSERT INTO conf.io_controller_modules
(module_type_id, module_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(6, 1, 'ICMP', 'ICMP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.io_controller_module_values
--
INSERT INTO conf.io_controller_module_values
(module_type_id, module_id, module_type_param_id, value, enabled, visible, created_at, updated_at)VALUES
(6, 1, 1, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 2, '0 0/10 * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
