DO $$ 
BEGIN
  -- Verifica si el TABLESPACE existe
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_reporting_bo') THEN
  
INSERT INTO reporting_bo.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES

	-- Type_detection
	--es_ES
	('es_ES','LBL_EXT_ENTITY_C4', 'Incidencias', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES','LBL_EXT_ENTITY_112', 'Incidencias', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES','LBL_EXT_ENTITY_TYPE_112_RESOURCES', 'Recursos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES','LBL_EXT_ENTITY_TYPE_112_VEHICLES', 'Vehículos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES','LBL_EXT_ENTITY_WAZE_TRAFFIC_ALERT', 'Incidencias', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES','LBL_EXT_ENTITY_WAZE_TRAFFIC_JAM', 'Retenciones', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);	

	END IF;
END $$;
