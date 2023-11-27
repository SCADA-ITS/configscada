-- 
-- Table: conf.elements.PA_system
--
INSERT INTO conf.elements 
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(5,  NULL, 1, 1,  NULL,  NULL,  'RV-DAC-1',  'RV-DAC-1',  NULL,  NULL,  'PAsystemParamsAdapter.groovy',  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(5,  NULL, 2, 1,  NULL,  NULL,  'RV-DAC-2',  'RV-DAC-2',  NULL,  NULL,  'PAsystemParamsAdapter.groovy',  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.PA_system
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(5, 5, 1, 1, NULL, '192.168.11.13', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 1, NULL, 'RV-DAC-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 5, 1, 2, NULL, '192.168.11.13', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(5, 1003, 1, 2, NULL, 'RV-DAC-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);