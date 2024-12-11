
-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(5, 1, 'Meteo DGT', 'Meteo DGT', 'LBL_ELEMENT_SUBTYPE_METEO_DGT', 'LBL_ELEMENT_SUBTYPE_METEO_DGT_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(5, 1, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
