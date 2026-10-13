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
(6, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, '0 0 1 ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--Para el número máximo de alarmas sin reconocer
update conf.system_param_values set value = 750 where system_param_id = 14;
