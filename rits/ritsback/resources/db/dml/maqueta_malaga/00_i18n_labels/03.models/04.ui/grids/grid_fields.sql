-- 
-- Type:   model
-- Entity: ui.grid_fields
-- File:   grid_fields.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code, module_id, "label", "translation", created_at, updated_at) VALUES
('es_ES', 1, 'LBL_GRID_LUMINOSITY', 'Iluminación (%)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_GRID_STATE', 'Porcentaje de iluminación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
