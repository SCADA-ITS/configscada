-- 
-- Table: conf.elements.tube
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2000, NULL, 5, 1020, NULL,  NULL, 'STMI Sentido Cádiz', 'STMI Sentido Cádiz', NULL, NULL, NULL, NULL, true, true, false , 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 6, 1019, NULL,  NULL, 'STMI Sentido Málaga ', 'STMI Sentido Málaga', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
