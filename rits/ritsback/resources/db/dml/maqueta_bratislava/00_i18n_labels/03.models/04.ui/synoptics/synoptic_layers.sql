-- 
-- Type:   model
-- Entity: ui.synoptic_layers
-- File:   synoptic_layers.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

--es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES', 1, 'LBL_CAM_DAI', 'Cámara Dai', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_PRESENCE_DETECTION', 'Detector de presencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_RTMS', 'RTMS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--sk_SK
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('sk_SK', 1, 'LBL_CAM_DAI', 'Dai kamera', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_PRESENCE_DETECTION', 'Pohybove cidlo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_GATE', 'Dvere', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ARMARIOS_ELEC', 'Elektro', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_NRL', 'NRL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_RTMS', 'RTMS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('en_GB', 1, 'LBL_CAM_DAI', 'Dai camera', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_PRESENCE_DETECTION', 'Presence detection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_GATE', 'Gate', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_RTMS', 'RTMS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
