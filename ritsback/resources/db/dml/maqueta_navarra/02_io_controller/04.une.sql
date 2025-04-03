-- 
-- Table: conf.io_controller_modules
--
INSERT INTO conf.io_controller_modules
(module_type_id, module_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(4, 1, 'LPR_SOCKET_UNE', 'LPR_SOCKET_UNE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.io_controller_module_values
--
INSERT INTO conf.io_controller_module_values
(module_type_id, module_id, module_type_param_id, value, enabled, visible, created_at, updated_at)VALUES
(4, 1, 1, 'ElementTypeParam:2:1:4001', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 'ElementTypeParam:2:1:4002', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 3, 'ElementTypeParam:2:1:4003', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 4, 'ElementTypeParam:2:1:4004', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 5, 'ElementTypeParam:2:1:4005', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 7, '0 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 8, '1048576', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- 1024 * 1024  1 mb
(4, 1, 9, '120000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 11, '50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 12, '500', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 13, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 14, 'ElementTypeParam:2:1:4006', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 15, 'ElementTypeParam:2:1:4007', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 16, '1048576', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- 1024 * 1024  
