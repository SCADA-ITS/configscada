-- 
-- Table: conf.element_hierarchies
--
INSERT INTO conf.element_hierarchies
(parent_element_type_id, parent_element_id, child_element_type_id, child_element_id, child_inheritable_state_id, enabled, visible, created_at, updated_at) VALUES

(4, 1, 3, 1, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 3, 2, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 3, 3, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 3, 4, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 3, 5, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 3, 6, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 3, 7, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 3, 8, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

