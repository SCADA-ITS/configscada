DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
	
	INSERT INTO nogales_gip.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES
	--es_ES
	('es_ES', 'LBL_INCIDENT', 'Incidente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_SECTION_EVENT', 'Evento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_SECTION_IDENTIFICATION', 'Identificación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_SECTION_IDENTIFICATION', 'Identificación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

	--es_CL
	('es_CL', 'LBL_ACTIONS', 'Acciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

	
	  END IF;
END $$;