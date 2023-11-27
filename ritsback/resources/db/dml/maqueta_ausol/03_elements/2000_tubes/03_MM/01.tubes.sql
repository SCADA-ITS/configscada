-- 
-- Table: conf.elements.tube
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2000, NULL, 3, 1009, NULL,  NULL, 'MM Sentido Cádiz', 'MM Sentido Cádiz', NULL, NULL, NULL, NULL, true, true, false , 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 4, 1008, NULL,  NULL, 'MM Sentido Málaga ', 'MM Sentido Málaga', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

