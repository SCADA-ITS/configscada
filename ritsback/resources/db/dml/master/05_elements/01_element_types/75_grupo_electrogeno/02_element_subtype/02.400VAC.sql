INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(2, 75, '400VAC', 'Grupo electrogeno 400VAC','LBL_ELEMENT_SUBTYPE_GE_400VAC', 'LBL_ELEMENT_SUBTYPE_GE_400VAC_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at) VALUES
(2, 75, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 31, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 33, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75, 2, 34, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);