-- 
-- Table: conf.elements.ETD
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(51, NULL, 1, 70, NULL,  NULL, 'ETD1', 'ETD1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.ETD
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(51,1,1,1,'Revenga ETD',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(51,2,1,1,'Revenga ETD',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(51,1003,1,1,'ETD1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);