
-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(4, 1, 'Meteo Modbus_P2', 'Meteo Modbus_P2', 'LBL_ELEMENT_SUBTYPE_METEO_MODBUS_P2', 'LBL_ELEMENT_SUBTYPE_METEO_MODBUS_P2_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
-- Modbus_P2
(4, 1, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 25, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 44, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 45, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 46, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 47, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 48, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 49, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 50, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 51, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 52, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 53, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 54, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 55, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 56, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 57, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 2, 58, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
