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
('es_ES', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_3', 'Reinicio del gestor de equipos virtuales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_LIST_EQUIPMENT_GRID', 'Selección', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_SUMMARY', 'Resumen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_RESTART', 'Reinicio', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_STEP_1', 'Seleccione un tubo y los luminancímetros a asignar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_STEP_2', 'Revise la asignación final de luminancímetros por tubo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_PARENT', 'Tubo(s) padre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_RESTART_CHECK_DESCRIPTION', 'Para que los cambios surtan efecto en el sistema es necesario reiniciar el servicio Virtual Equipment Manager.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_RESTART_CHECK', 'Reiniciar Virtual Equipment Manager al finalizar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_CONFIRM_SAVE_AND_RESTART', '¿Desea guardar los cambios realizados y reiniciar el Virtual Equipment Manager?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_CONFIRM_SAVE_NO_RESTART', '¿Desea guardar los cambios realizados sin reiniciar el Virtual Equipment Manager?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_TUBES', 'Tubos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LIGHTING_CONFIG_LUMS', 'Luminancímetros', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- en_GB
('en_GB', 1, 'LBL_LIGHTING_CONFIG_HEADER', 'Lighting configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_1', 'Equipment selection by tube', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_2', 'Assignment summary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_HEADER_STEP_3', 'Virtual equipment manager restart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_LIST_EQUIPMENT_GRID', 'Selection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_SUMMARY', 'Summary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_RESTART', 'Restart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_STEP_1', 'Select a tube and the lighting equipment to assign', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_STEP_2', 'Review the final equipment assignment per tube', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_PARENT', 'Parent tube(s)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_RESTART_CHECK_DESCRIPTION', 'To apply the changes in the system, the Virtual Equipment Manager service must be restarted.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_RESTART_CHECK', 'Restart Virtual Equipment Manager on finish', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_CONFIRM_SAVE_AND_RESTART', 'Do you want to save the changes and restart the Virtual Equipment Manager?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_CONFIRM_SAVE_NO_RESTART', 'Do you want to save the changes without restarting the Virtual Equipment Manager?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_TUBES', 'Tubes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LIGHTING_CONFIG_LUMS', 'Luminance meters', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
