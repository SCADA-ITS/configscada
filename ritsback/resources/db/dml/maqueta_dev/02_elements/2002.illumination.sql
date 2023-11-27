-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2002, NULL, 1, 1000, NULL,  NULL, 'ILLUMINATION_1', 'ILLUMINATION_1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO conf.element_hierarchies
(parent_element_type_id, parent_element_id, child_element_type_id, child_element_id, child_inheritable_state_id, enabled, visible, created_at, updated_at) VALUES
(2002, 1, 2000, 1, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2002, 1, 2000, 2, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


