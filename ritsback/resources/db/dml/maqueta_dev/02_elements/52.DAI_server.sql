-- 
-- Table: conf.elements.SERVER_DAI
--
INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(52, NULL, 1, 37, NULL, NULL, 'AVOITSDAI01', 'AVOITSDAI01', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(52, NULL, 2, 37, NULL, NULL, 'AVOITSDAI02', 'AVOITSDAI02', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(52,1004,1,1,'AVOITSDAI01',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(52,1004,1,2,'AVOITSDAI02',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

--INSERT INTO conf.alarm_configs
--(alarm_config_id, alarm_type_id, alarm_level_id, element_type_id, element_id, element_type_state_id, mine, treatment, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
--(10052, 2, 3, 52, NULL, 3, true, true, 'CITILOG_COMM_ERROR', 'CITILOG_COMM_ERROR', 'LBL_ALARM_CONFIGS_CITILOG_COMM_ERROR', '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
