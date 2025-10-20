-- 
-- Type:   model
-- Entity: ui.equip_viewer_grp_measures
-- File:   equip_viewer_grp_measures.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
    
    --es_ES
	 ('es_ES', 1, 'LBL_ELEMENT_GENERAL', 'General', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_ELEMENT_SPEED_CLASSIFICATION', 'Clasificación velocidad', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_ES', 1, 'LBL_ELEMENT_TYPE_CLASSIFICATION', 'Clasificación tipo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    --en_GB
	 ('en_GB', 1, 'LBL_ELEMENT_GENERAL', 'General', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
	 ('en_GB', 1, 'LBL_ELEMENT_SPEED_CLASSIFICATION', 'Speed classification', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 	 ('en_GB', 1, 'LBL_ELEMENT_TYPE_CLASSIFICATION', 'Type classification', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 
    --es_CL
	 ('es_CL', 1, 'LBL_ELEMENT_GENERAL', 'General', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 	 ('es_CL', 1, 'LBL_ELEMENT_SPEED_CLASSIFICATION', 'Clasificación velocidad', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	 ('es_CL', 1, 'LBL_ELEMENT_TYPE_CLASSIFICATION', 'Clasificación tipo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    