INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(2, 55, 'CT Type 2', 'CT Type 2', 'LBL_ELEMENT_SUBTYPE_CT_TYPE_2', 'LBL_ELEMENT_SUBTYPE_CT_TYPE_2_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(2, 55, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);