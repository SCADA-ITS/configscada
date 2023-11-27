-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes
(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
(3, 1, 'Meteo Modbus_P1', 'Meteo Modbus_P1', 'LBL_ELEMENT_SUBTYPE_METEO_MODBUS_P1', 'LBL_ELEMENT_SUBTYPE_METEO_MODBUS_P1_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
-- Modbus_P1
(3, 1, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 11, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
