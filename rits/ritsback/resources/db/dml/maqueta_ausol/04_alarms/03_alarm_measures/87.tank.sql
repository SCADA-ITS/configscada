-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(3000087, 3, 3, 87, 3, true, false, 'ALARM LOW LEVEL WATER', 'Water less than 70%', 'LBL_WATER_LEVEL_LESS_70', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010087, 3, 3, 87, 3, true, false, 'ALARM LOW LEVEL DIESEL', 'Diesel less than 60%', 'LBL_DIESEL_LEVEL_LESS_60', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3020087, 3, 1, 87, 3, true, true, 'ALARM LOW TOXIC LEVEL', 'Toxic greater than 10% but less than 76%', 'LBL_ALARM_CONFIGS_ALARM_LOW_TOXIC_LEVEL', 'LBL_ALARM_CONFIGS_ALARM_LOW_TOXIC_LEVEL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3030087, 3, 1, 87, 3, true, true, 'ALARM HIGH TOXIC LEVEL', 'Toxic greater than 75%', 'LBL_ALARM_CONFIGS_ALARM_HIGH_TOXIC_LEVEL', 'LBL_ALARM_CONFIGS_ALARM_HIGH_TOXIC_LEVEL_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(3000087, 87, 2, 1, 7, '70', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3010087, 87, 2, 2, 7, '60', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3020087, 87, 2, 3, 1, '11', '75', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3030087, 87, 2, 3, 4, '75', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
