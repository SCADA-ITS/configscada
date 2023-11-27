INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 55, 'CT Type 1', 'CT Type 1', 'LBL_ELEMENT_SUBTYPE_CT_TYPE_1', 'LBL_ELEMENT_SUBTYPE_CT_TYPE_1_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 55, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
