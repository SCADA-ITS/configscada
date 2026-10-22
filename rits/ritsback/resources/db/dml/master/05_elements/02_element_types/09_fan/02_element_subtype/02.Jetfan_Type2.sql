-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(2, 9, 'Jetfan Type 2', 'Jetfan Type 2', 'LBL_ELEMENT_SUBTYPE_JETFAN_TYPE_2', 'LBL_ELEMENT_SUBTYPE_JETFAN_TYPE_2_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(2, 9, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 2, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 1, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 6, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
