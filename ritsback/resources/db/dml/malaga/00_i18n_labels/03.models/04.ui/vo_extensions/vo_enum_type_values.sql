-- 
-- Type:   model
-- Entity: ui.vo_enum_type_values
-- File:   vo_enum_type_values.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code, module_id, "label", "translation", created_at, updated_at) VALUES
('es_ES', 1, 'LBL_ENUM_TYPE_CERRADO', 'Apagado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ENUM_TYPE_OPEN_100', 'Fijo 100%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ENUM_TYPE_OPEN_70', 'Fijo 70%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ENUM_TYPE_OPEN_100_FLASHING', 'Flashing 100%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ENUM_TYPE_OPEN_70_FLASHING', 'Flashing 70%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
