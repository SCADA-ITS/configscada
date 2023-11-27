-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(5, 1, 'Meteo MetService', 'Meteo MetService', 'LBL_ELEMENT_SUBTYPE_METEO_METSERVICE', 'LBL_ELEMENT_SUBTYPE_METEO_METSERVICE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
-- MetService
(5, 1, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 31, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 32, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 33, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 34, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 35, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 36, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 37, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 38, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 39, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 40, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 41, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 42, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 43, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
