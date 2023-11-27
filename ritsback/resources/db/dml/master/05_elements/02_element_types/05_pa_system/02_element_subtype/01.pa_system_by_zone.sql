-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 5, 'PA SYSTEM BY ZONE', 'PA SYSTEM BY ZONE', 'LBL_ELEMENT_SUBTYPE_PA_SYSTEM_BY_ZONE', 'LBL_ELEMENT_SUBTYPE_PA_SYSTEM_BY_ZONE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 5, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 2, 1001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, 2, 1002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);