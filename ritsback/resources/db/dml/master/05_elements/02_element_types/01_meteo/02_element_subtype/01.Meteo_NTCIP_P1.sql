-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(1, 1, 'Meteo NTCIP_P1', 'Meteo NTCIP_P1', 'LBL_ELEMENT_SUBTYPE_METEO_NTCIP_P1', 'LBL_ELEMENT_SUBTYPE_METEO_NTCIP_P1_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 1, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 2, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
