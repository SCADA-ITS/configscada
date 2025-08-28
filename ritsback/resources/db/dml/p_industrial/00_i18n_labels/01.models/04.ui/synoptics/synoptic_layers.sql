-- 
-- Type:   model
-- Entity: ui.synoptic_layers
-- File:   synoptic_layers.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
    ('es_ES', 1, 'LBL_SYNOPTIC_LAYER_CAMERAS', 'Cámaras', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_ES', 1, 'LBL_SYNOPTIC_LAYER_ETDS', 'ETDs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_ES', 1, 'LBL_SYNOPTIC_LAYER_PMVS', 'PMVs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    
--es_CL
    ('es_CL', 1, 'LBL_SYNOPTIC_LAYER_CAMERAS', 'Cámaras', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_CL', 1, 'LBL_SYNOPTIC_LAYER_ETDS', 'ETDs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_CL', 1, 'LBL_SYNOPTIC_LAYER_PMVS', 'PMVs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
