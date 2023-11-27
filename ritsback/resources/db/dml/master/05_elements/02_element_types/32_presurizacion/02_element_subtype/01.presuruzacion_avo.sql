-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 32, 'PRESURIZACION AVO', 'PRESURIZACION AVO', 'LBL_ELEMENT_SUBTYPE_PRESURIZACION_AVO', 'LBL_ELEMENT_SUBTYPE_PRESURIZACION_AVO_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 32, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 32, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 32, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 32, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 32, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);