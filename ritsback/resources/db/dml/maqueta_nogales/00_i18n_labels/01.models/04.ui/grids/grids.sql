-- 
-- Type:   model
-- Entity: ui.grids
-- File:   map_layers.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
--es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('es_ES', 1, 'LBL_GRID_SOS', 'SOS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_GRID_CAMERA', 'CÁMARA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 	 ('es_ES', 1, 'LBL_ETD', 'ETD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_DATE', 'Fecha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	
	 ('es_ES', 1, 'LBL_GRID_PANEL', 'PANEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('es_CL', 1, 'LBL_GRID_SOS', 'SOS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_GRID_CAMERA', 'CÁMARA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_ETD', 'ETD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_DATE', 'Fecha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_N_VEHICLES', 'Número de vehículos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	 
	 ('es_CL', 1, 'LBL_SPEED', 'Velocidad', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	
	 ('es_CL', 1, 'LBL_LENGTH', 'Longitud', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	 
	 ('es_CL', 1, 'LBL_OCCUPANCY', 'Ocupación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_VEH_SPEED_4', '< 100 km/h', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_VOL_MIC', 'Volumen micro', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),		 
	 ('es_CL', 1, 'LBL_GRID_PANEL', 'PANEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('en_GB', 1, 'LBL_GRID_SOS', 'SOS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_GRID_CAMERA', 'CÁMERA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_ETD', 'ETD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_GRID_PANEL', 'PANEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


