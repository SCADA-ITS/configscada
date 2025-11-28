-- 
-- Table: conf.alarm_configs
--
INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(03010021, 3, 4, 21, NULL, 3, true, false, 'ALARM OPAC VISIBILITY', 'Visibility 20 km-1 < k < 40 km-1', 'LBL_ALARM_CONFIGS_ALARM_OPAC_VISIBILITY', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020021, 3, 4, 21, NULL, 3, true, false, 'ALARM OPAC REDUCED VISIBILITY', 'Reduced visibility 40 km-1 < k < 66 km-1', 'LBL_ALARM_CONFIGS_ALARM_OPAC_REDUCED_VISIBILITY', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030021, 3, 5, 21, NULL, 3, true, false, 'ALARM OPAC EXCESSIVE LEVEL OF OPACITY', 'Excessive level of opacity, k > 66 km-1', 'LBL_ALARM_CONFIGS_ALARM_OPAC_EXCESSIVE_OPACITY', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.alarm_measures
--
INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(03010021, 21, 2, 1, 1, '20', '40', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020021, 21, 2, 1, 1, '40', '66', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030021, 21, 2, 1, 1, '66', '65534', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 
