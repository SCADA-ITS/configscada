-- 
-- Table: conf.elements.PA_zone
--
INSERT INTO conf.elements 
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(16,  NULL, 1, 11,  NULL,  NULL,  'RV-MEG-1CA',  'RV-MEG-1CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(16,  NULL, 2, 12,  NULL,  NULL,  'RV-MEG-2CA',  'RV-MEG-2CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(16,  NULL, 3, 13,  NULL,  NULL,  'RV-MEG-1MA',  'RV-MEG-1MA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(16,  NULL, 4, 14,  NULL,  NULL,  'RV-MEG-2MA',  'RV-MEG-2MA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),

(16,  NULL, 5, 11,  NULL,  NULL,  'RV-MEG-3CA',  'RV-MEG-3CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(16,  NULL, 6, 12,  NULL,  NULL,  'RV-MEG-4CA',  'RV-MEG-4CA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(16,  NULL, 7, 13,  NULL,  NULL,  'RV-MEG-3MA',  'RV-MEG-3MA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(16,  NULL, 8, 14,  NULL,  NULL,  'RV-MEG-4MA',  'RV-MEG-4MA',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.PA_zone
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(16, 5, 1, 1, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 1, NULL, 'RV-MEG-1CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 5, 1, 2, NULL, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 2, NULL, 'RV-MEG-2CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 5, 1, 3, NULL, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 3, NULL, 'RV-MEG-1MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 5, 1, 4, NULL, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 4, NULL, 'RV-MEG-2MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 5, 1, 5, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 5, NULL, 'RV-MEG-3CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 5, 1, 6, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 6, NULL, 'RV-MEG-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 5, 1, 7, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 7, NULL, 'RV-MEG-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(16, 5, 1, 8, NULL, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(16, 1003, 1, 8, NULL, 'RV-MEG-4MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);