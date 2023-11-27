-- 
-- Type:   model
-- Entity: ui.map_layers
-- File:   map_layers.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
--es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('es_ES', 1, 'LBL_LAYER_SOS', 'SOS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_LAYER_PMV', 'PANEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_LAYER_CCTV', 'CCTV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('es_CL', 1, 'LBL_LAYER_SOS', 'SOS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_LAYER_PMV', 'PANEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_LAYER_CCTV', 'CCTV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('en_GB', 1, 'LBL_LAYER_SOS', 'SOS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_LAYER_PMV', 'PANEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('en_GB', 1, 'LBL_LAYER_CCTV', 'CCTV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
