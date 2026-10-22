-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(2, 61, 'FIRE_DET_CT', 'FIRE_DET_CT', 'LBL_ELEMENT_SUBTYPE_FIBROLASER_CT_AVO', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_commands
--
INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(2, 1, 61, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);