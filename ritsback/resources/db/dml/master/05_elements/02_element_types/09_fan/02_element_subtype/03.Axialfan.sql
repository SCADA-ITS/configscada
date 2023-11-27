-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(3, 9, 'Axialfan', 'Axialfan', 'LBL_ELEMENT_SUBTYPE_AXIALFAN', 'LBL_ELEMENT_SUBTYPE_AXIALFAN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(3, 9, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9, 2, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(3, 1, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 6, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 7, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 8, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

