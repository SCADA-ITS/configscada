-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(16, 71, 'VMS_TXT_1LINE', 'VMS_TXT_1LINE', 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_TXT_1LINE', 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_TXT_1LINE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(16, 71, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 71, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 71, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(16, 2, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 3, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
