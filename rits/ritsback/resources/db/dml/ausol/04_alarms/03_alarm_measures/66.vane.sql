-- 
-- Table: conf.alarm_measures.VANE
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(660001, 66, 2, 2, 1, '60', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Velocidad del viento > 60 m/s
(660002, 66, 2, 2, 1, '80', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Velocidad del viento > 80 m/s