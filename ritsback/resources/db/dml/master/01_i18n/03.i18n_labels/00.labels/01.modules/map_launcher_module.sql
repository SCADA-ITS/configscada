-- 
-- Type:   modules
-- Entity: External url module
-- File:   external_url_module.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	
('es_ES', 1, 'LBL_ZONES', 'Zonas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SHOW_ZONES', 'Zonas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_CREATE_INCIDENT', 'Crear incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MAP_NOT_AVAILABLE_FOR', 'Mapa no disponible para', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('en_GB', 1, 'LBL_ZONES', 'Zones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SHOW_ZONES', 'Show zones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_CREATE_INCIDENT', 'Create incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MAP_NOT_AVAILABLE_FOR', 'Map not available for', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('es_CL', 1, 'LBL_ZONES', 'Zonas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SHOW_ZONES', 'Zonas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_CREATE_INCIDENT', 'Crear incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MAP_NOT_AVAILABLE_FOR', 'Mapa no disponible para', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);