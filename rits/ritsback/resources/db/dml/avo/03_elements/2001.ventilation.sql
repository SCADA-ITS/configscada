-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2001, NULL, 1, NULL, NULL,  NULL, 'Zona 1 - Tramo 2', 'CANTON1_Z1_T2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 2, NULL, NULL,  NULL, 'Zona 1 - Tramo 3', 'CANTON2_Z1_T3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 3, NULL, NULL,  NULL, 'Zona 1 - Tramo 4', 'CANTON3_Z1_T4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 4, NULL, NULL,  NULL, 'Zona 1 - Tramo 5', 'CANTON3_Z1_T5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 5, NULL, NULL,  NULL, 'Zona 2 - Tramo 1', 'CANTON12_Z2_T1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 6, NULL, NULL,  NULL, 'Zona 2 - Tramo 2', 'CANTON11_Z2_T2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 7, NULL, NULL,  NULL, 'Zona 2 - Tramo 3', 'CANTON10_Z2_T3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, NULL, 8, NULL, NULL,  NULL, 'Zona 2 - Tramo 4', 'CANTON9_Z2_T4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
