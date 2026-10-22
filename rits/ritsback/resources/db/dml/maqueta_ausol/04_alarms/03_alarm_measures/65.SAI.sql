-- 
-- Table: conf.alarm_measures.VANE
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(03000065, 3, 2, 65, NULL, 3, true, false, 'ALARM SAI AUTONOMY <50%', 'Autonomy less than 50%', 'LBL_ALARM_CONFIGS_ALARM_SAI_AUTONOMY_LESS_50', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03010065, 3, 5, 65, NULL, 3, true, false, 'ALARM SAI AUTONOMY <5%', 'Autonomy less than 5%', 'LBL_ALARM_CONFIGS_ALARM_SAI_AUTONOMY_LESS_5', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(03000065, 65, 2, 7, 7, '50', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Autonomia < 50%
(03010065, 65, 2, 7, 7, '5', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Autonomia < 5%
