DELETE FROM conf.system_param_values;

-- 
-- Table: conf.system_param_values
--

INSERT INTO conf.system_param_values
(system_param_id, value, enabled, visible, created_at, updated_at) VALUES
(1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '90', true, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, '20', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, '0 0 * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
