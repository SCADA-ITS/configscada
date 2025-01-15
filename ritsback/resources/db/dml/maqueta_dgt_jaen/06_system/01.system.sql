DELETE FROM conf.system_param_values;

-- 
-- Table: conf.system_param_values
--

UPDATE master.system_permissions SET enabled = false WHERE system_permission_id IN (20);

INSERT INTO conf.system_param_values
(system_param_id, value, enabled, visible, created_at, updated_at) VALUES
(1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '90', true, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, '0 0 * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Para el número máximo de alarmas sin reconocer --> n_elements = 1068, se deja a n_elements * 2 = 2136
(14, '2136', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
