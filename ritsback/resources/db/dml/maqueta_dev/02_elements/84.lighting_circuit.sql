-- 
-- Table: conf.elements.FAN
--
INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(84, NULL, 1, 18, NULL, NULL, 'LIGHTING_CIRCUIT_1', 'LIGHTING_CIRCUIT_1', NULL, 'SendStateToIllumination.groovy', NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, NULL, 2, 18, NULL, NULL, 'LIGHTING_CIRCUIT_2', 'LIGHTING_CIRCUIT_2', NULL, 'SendStateToIllumination.groovy', NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, NULL, 3, 18, NULL, NULL, 'LIGHTING_CIRCUIT_3', 'LIGHTING_CIRCUIT_3', NULL, 'SendStateToIllumination.groovy', NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.FAN
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(84, 1, 1, 1, NULL, '177+257', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, 5, 1, 1, NULL, 'T1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, 1003, 1, 1, NULL, 'LIGHTING_CIRCUIT_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(84, 1, 1, 2, NULL, '177+257', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, 5, 1, 2, NULL, 'T2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, 1003, 1, 2, NULL, 'LIGHTING_CIRCUIT_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(84, 1, 1, 3, NULL, '177+257', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, 5, 1, 3, NULL, 'T3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(84, 1003, 1, 3, NULL, 'LIGHTING_CIRCUIT_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);