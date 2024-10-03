-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(03010100, 3, 3, 100,3, true, false, 'ALARM THRESHOLD MEASURE 1', 'Measure1 greater than', 'Peligro de reacción nuclear', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020100, 3, 3, 100,3, true, false, 'ALARM THRESHOLD MEASURE 2', 'Measure2 greater than', 'Peligro de reacción nuclear', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030100, 3, 3, 100,3, true, false, 'ALARM THRESHOLD MEASURE 3', 'Measure3 greater than', 'Peligro de reacción nuclear', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(03010100, 100, 2, 1, 5, '20', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020100, 100, 2, 2, 5, '20', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030100, 100, 2, 3, 5, '20', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);