-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 9, 'Jetfan Type 1', 'Jetfan Type 1', 'LBL_ELEMENT_SUBTYPE_JETFAN_TYPE_1', 'LBL_ELEMENT_SUBTYPE_JETFAN_TYPE_1_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 9, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 9, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 9, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 9, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 9, 2, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 9, 2, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 6, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
