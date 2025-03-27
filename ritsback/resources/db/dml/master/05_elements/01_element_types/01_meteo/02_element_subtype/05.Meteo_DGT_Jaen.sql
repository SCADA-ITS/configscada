
-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(5, 1, 'Meteo socket une', 'Meteo socket une', 'LBL_ELEMENT_SUBTYPE_METEO_SOCKET_UNE', 'LBL_ELEMENT_SUBTYPE_METEO_SOCKET_UNE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(5, 1, 1, 4001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 1, 4002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 1, 4003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 1, 4004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 1, 4005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 26, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 27, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 28, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 29, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 2, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
