-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(11, 71, 'VMS_CLV', 'VMS_CLV', 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_CLV', 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_CLV_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(11, 71, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(11, 2, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 3, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 5, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 6, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 7, 71, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
