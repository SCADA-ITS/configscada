-- 
-- Table: conf.elements.PA_zone
--
INSERT INTO conf.elements 
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(96,  NULL, 1, 11,  NULL,  NULL,  'RV-GRP-1CA',  'RV-GRP-1CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(96,  NULL, 2, 12,  NULL,  NULL,  'RV-GRP-2CA',  'RV-GRP-2CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),

(96,  NULL, 3, 11,  NULL,  NULL,  'RV-GRP-3CA',  'RV-GRP-3CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(96,  NULL, 4, 12,  NULL,  NULL,  'RV-GRP-4CA',  'RV-GRP-4CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.PA_zone
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(96, 5, 1, 1, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(96, 1003, 1, 1, NULL, 'RV-GRP-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(96, 5, 1, 2, NULL, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(96, 1003, 1, 2, NULL, 'RV-GRP-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(96, 5, 1, 3, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(96, 1003, 1, 3, NULL, 'RV-GRP-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(96, 5, 1, 4, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(96, 1003, 1, 4, NULL, 'RV-GRP-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

