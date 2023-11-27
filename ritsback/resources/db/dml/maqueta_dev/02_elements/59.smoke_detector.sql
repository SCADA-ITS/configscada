-- 
-- Table: conf.elements.SMOKE_DETECTOR
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(59, NULL, 1, 142, NULL,  NULL, 'RV-CT-FIRE-HUM-1', 'RV-CT-FIRE-HUM-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.SMOKE_DETECTOR
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(59,1003,1,1,'RV-CT-FIRE-HUM-1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);