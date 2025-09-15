-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(4, 9, 'Jetfan Type 3', 'Jetfan Type 3', 'LBL_ELEMENT_SUBTYPE_JETFAN_TYPE_3', 'LBL_ELEMENT_SUBTYPE_JETFAN_TYPE_3_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(4, 9, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 2, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 2, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 2, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 9, 2, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(4, 1, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
