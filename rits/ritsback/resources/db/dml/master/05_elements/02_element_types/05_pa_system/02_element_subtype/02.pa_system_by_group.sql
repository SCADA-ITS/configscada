-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(2, 5, 'PA SYSTEM BY GROUP', 'PA SYSTEM BY GROUP', 'LBL_ELEMENT_SUBTYPE_PA_SYSTEM_BY_GROUP', 'LBL_ELEMENT_SUBTYPE_PA_SYSTEM_BY_GROUP_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(2, 5, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 2, 1001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 2, 1002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 1, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);