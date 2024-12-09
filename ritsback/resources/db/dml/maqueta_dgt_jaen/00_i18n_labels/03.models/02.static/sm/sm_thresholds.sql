-- 
-- Type:   model
-- Entity: static.sm_thresholds.sql
-- File:   sm_thresholds.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

--ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('es_ES',1,'LBL_THRESHOLD_CO_0', 'Nivel 0 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_CO_1', 'Nivel 1 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_CO_2', 'Nivel 2 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_0', 'Nivel 0 Opaciadad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_1', 'Nivel 1 Opaciadad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_2', 'Nivel 2 Opaciadad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_SERVICE', 'Ventilación sanitaria',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_SERVICE_DESC', 'Umbrales para la ventilación sanitaria',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_CONFORT', 'Ventilación modo confort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_CONFORT_DESC','Umbrales para la ventilación en modo confort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('en_GB',1,'LBL_VENTILATION_SERVICE', 'Ventilation service',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_VENTILATION_SERVICE_DESC', 'Ventilaction service',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_VENTILATION_CONFORT', 'Ventilation comfort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_VENTILATION_CONFORT_DESC','Ventilation comfort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_0', 'Threshold 0 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_1', 'Threshold 1 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_2', 'Threshold 2 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
