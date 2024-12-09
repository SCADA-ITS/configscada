-- 
-- Table: conf.element_hierarchies
--
INSERT INTO conf.element_hierarchies
(parent_element_type_id, parent_element_id, child_element_type_id, child_element_id, child_inheritable_state_id, enabled, visible, created_at, updated_at) VALUES

(2001, 1, 2000, 1, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2001, 1, 2000, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--(2001, 2, 2000, 3, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
