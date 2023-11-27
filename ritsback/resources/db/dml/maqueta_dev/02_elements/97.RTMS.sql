-- 
-- Table: conf.elements.RTMS
--
INSERT INTO conf.elements 
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(97,  NULL, 1, NULL,  NULL,  NULL,  'RTMS-TEST-001',  'RTMS-TEST-001',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(97,  NULL, 2, NULL,  NULL,  NULL,  'RTMS-TEST-002',  'RTMS-TEST-002',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(97,  NULL, 3, NULL,  NULL,  NULL,  'RTMS-TEST-003',  'RTMS-TEST-003',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP),
(97,  NULL, 4, NULL,  NULL,  NULL,  'RTMS-TEST-004',  'RTMS-TEST-004',  NULL,  NULL,  NULL,  NULL, true, true, false, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.RTMS
--
INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(97, 1, 1, 1, NULL, 'http://10.191.10.4/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 1, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 1, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(97, 1, 1, 2, NULL, 'http://10.191.10.4/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 2, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 2, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(97, 1, 1, 3, NULL, 'http://10.191.10.4/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 3, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 3, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(97, 1, 1, 4, NULL, 'http://10.191.10.4/api/v1/', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 2, 1, 4, NULL, 'admin', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(97, 3, 1, 4, NULL, 'R4D7Revenga', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
