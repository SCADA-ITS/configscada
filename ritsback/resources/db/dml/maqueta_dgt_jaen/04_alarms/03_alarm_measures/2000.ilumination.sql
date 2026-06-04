INSERT INTO conf.alarm_configs
(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(0301002000, 3, 4, 2000, NULL, 3, true, false, 'ALARM CAMBIO SOLEADO', 'Cambio de régimen a Soleado', 'LBL_ALARM_CONFIGS_ALARM_CAMBIO_SOLEADO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0302002000, 3, 4, 2000, NULL, 3, true, false, 'ALARM CAMBIO CREPUSCULAR', 'Cambio de régimen a Crepuscular', 'LBL_ALARM_CONFIGS_ALARM_CAMBIO_CREPUSCULAR', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0303002000, 3, 4, 2000, NULL, 3, true, false, 'ALARM CAMBIO NUBLADO', 'Cambio de régimen a Nublado', 'LBL_ALARM_CONFIGS_ALARM_CAMBIO_NUBLADO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0304002000, 3, 4, 2000, NULL, 3, true, false, 'ALARM CAMBIO NOCTURNO', 'Cambio de régimen a Nocturno', 'LBL_ALARM_CONFIGS_ALARM_CAMBIO_NOCTURNO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0305002000, 3, 4, 2000, NULL, 3, true, false, 'ALARM CAMBIO EXTERIOR/NOCTURNO', 'Cambio de régimen a Exterior/Nocturno', 'LBL_ALARM_CONFIGS_ALARM_CAMBIO_EXTERIOR_NOCTURNO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.alarm_config_measures
(alarm_config_id, element_type_id, param_type_id, element_type_param_id, alarm_threshold_operation_id, threshold_val1, threshold_val2, created_at, updated_at) VALUES
(0301002000, 2000, 2, 4, 2, '2', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0302002000, 2000, 2, 4, 2, '3', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0303002000, 2000, 2, 4, 2, '4', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0304002000, 2000, 2, 4, 2, '5', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0305002000, 2000, 2, 4, 2, '6', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

