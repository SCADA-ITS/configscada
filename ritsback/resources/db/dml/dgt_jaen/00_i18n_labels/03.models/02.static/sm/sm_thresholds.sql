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
	 ('es_ES',1,'LBL_THRESHOLD_K_0', 'Nivel 0 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_1', 'Nivel 1 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_2', 'Nivel 2 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_CO_3', 'Nivel histéresis 0 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_CO_4', 'Nivel histéresis 1 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_CO_5', 'Nivel histéresis 2 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_3', 'Nivel histéresis 0 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_4', 'Nivel histéresis 1 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_K_5', 'Nivel histéresis 2 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_SERVICE', 'Ventilación sanitaria',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_SERVICE_DESC', 'Umbrales para la ventilación sanitaria',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_CONFORT', 'Ventilación modo confort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_VENTILATION_CONFORT_DESC','Umbrales para la ventilación en modo confort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_DESP_NORTE', 'Iluminacion Despeñaperros sentido Madrid',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_DESP_NORTE_DESC', 'Iluminacion Despeñaperros sentido Madrid',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CA_NORTE', 'Iluminacion La Cantera sentido Madrid',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CA_NORTE_DESC', 'Iluminacion La Cantera  sentido Madrid',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CZ_NORTE', 'Iluminacion El Corzo sentido Madrid',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CZ_NORTE_DESC', 'Iluminacion El Corzo sentido Madrid',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_DESP_SUR', 'Iluminacion Despeñaperros sentido Cordoba',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_DESP_SUR_DESC', 'Iluminacion Despeñaperros sentido Cordoba',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CA_SUR', 'Iluminacion La Cantera  sentido Cordoba',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CA_SUR_DESC', 'Iluminacion La Cantera  sentido Cordoba',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CZ_SUR', 'Iluminacion El Corzo sentido Cordoba',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_ILUM_CZ_SUR_DESC', 'Iluminacion El Corzo sentido Cordoba',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_LUM_1000', 'Nivel 2 iluminación (cd/m2)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_LUM_200', 'Nivel 0 iluminación (cd/m2)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_LUM_2500', 'Nivel 3 iluminación (cd/m2)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('es_ES',1,'LBL_THRESHOLD_LUM_300', 'Nivel 1 iluminación (cd/m2)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	 ('en_GB',1,'LBL_VENTILATION_SERVICE', 'Ventilation service',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_VENTILATION_SERVICE_DESC', 'Ventilaction service',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_VENTILATION_CONFORT', 'Ventilation comfort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_VENTILATION_CONFORT_DESC','Ventilation comfort',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_0', 'Threshold 0 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_1', 'Threshold 1 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_2', 'Threshold 2 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_K_0', 'Threshold 0 opacity (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_K_1', 'Threshold 1 opacity (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_K_2', 'Threshold 2 opacity (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_3', 'Hysteresis threshold 0 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_4', 'Hysteresis threshold 1 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_CO_5', 'Hysteresis threshold 2 Co (ppm)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_K_3', 'Hysteresis threshold 0 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_K_4', 'Hysteresis threshold 1 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 ('en_GB',1,'LBL_THRESHOLD_K_5', 'Hysteresis threshold 2 Opacidad (km-1)',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
