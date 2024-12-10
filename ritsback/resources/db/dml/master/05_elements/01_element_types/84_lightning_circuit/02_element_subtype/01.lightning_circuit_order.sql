INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 84, 'LIGHTING_CIRCUIT', 'Lighting circuit','LBL_ELEMENT_TYPE_LIGHTING_CIRCUIT', 'LBL_ELEMENT_TYPE_LIGHTING_CIRCUIT_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 84, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_commands (element_subtype_id, command_element_type_id, element_type_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 84, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
