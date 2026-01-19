-- 
-- Table: conf.element_hierarchies
--
INSERT INTO conf.element_hierarchies
(parent_element_type_id, parent_element_id, child_element_type_id, child_element_id, child_inheritable_state_id, enabled, visible, created_at, updated_at) VALUES
(11, 1,  2, 1,  NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 4,  2, 3,  NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
