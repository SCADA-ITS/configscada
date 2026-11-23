-- 
-- Type:   modules
-- Entity: Lighting config module
-- File:   lighting_config_module.sql
-- Langs:  es_ES, en_GB
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
-- es_ES
('es_ES', 1, 'LBL_LIGHTING_CONFIG_HEADER', 'Configuración de iluminación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_1', 'Selección de luminancímetros por tubo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_2', 'Resumen de asignaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_LIST_EQUIPMENT_GRID', 'Selección', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_SUMMARY', 'Resumen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_STEP_1', 'Seleccione un tubo y el luminancímetro a asignar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_STEP_2', 'Revise la asignación final de luminancímetros por tubo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_PARENT', 'Tubo(s) padre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_CONFIRM_SAVE', '¿Desea guardar los cambios realizados?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_TUBES', 'Tubos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_LUMS', 'Luminancímetros', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- en_GB
('en_GB', 1, 'LBL_LIGHTING_CONFIG_HEADER', 'Lighting configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_1', 'Luminance meter selection by tube', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_2', 'Assignment summary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_LIST_EQUIPMENT_GRID', 'Selection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_SUMMARY', 'Summary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_STEP_1', 'Select a tube and the luminance meter to assign', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_STEP_2', 'Review the final luminance meter assignment per tube', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_PARENT', 'Parent tube(s)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_CONFIRM_SAVE', 'Do you want to save the changes?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_TUBES', 'Tubes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_LUMS', 'Luminance meters', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
