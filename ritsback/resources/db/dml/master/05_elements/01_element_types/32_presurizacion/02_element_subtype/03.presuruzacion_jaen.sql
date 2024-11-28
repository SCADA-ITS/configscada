-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(3, 32, 'PRESURIZACION JAEN', 'PRESURIZACION JAEN', 'LBL_ELEMENT_SUBTYPE_PRESURIZACION_JAEN', 'LBL_ELEMENT_SUBTYPE_PRESURIZACION_JAEN_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(3, 32, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 32, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 32, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(3, 1, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);