-- 
-- Type:   model
-- Entity: ui.equip_viewer_tabs
-- File:   equip_viewer_tabs.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
    
    --es_ES
	 ('es_ES', 1, 'LBL_LIBRARY', 'Libreria Señalizacion', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    
    --en_GB
 	 ('en_GB', 1, 'LBL_LIBRARY', 'Signalling Library', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    --sk_SK
	 ('sk_SK',1,'LBL_LIBRARY','Knižnica signálov',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
 
    --es_CL
	 ('es_CL', 1, 'LBL_LIBRARY', 'Librería Señalización', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    