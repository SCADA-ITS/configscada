INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(3, 87, 'Toxic Tank', 'Toxic Tank','LBL_ELEMENT_SUBTYPE_TOXIC_TANK', 'LBL_ELEMENT_SUBTYPE_TOXIC_TANK_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(3, 87, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);