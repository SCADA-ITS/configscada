DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
  
INSERT INTO gip.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES

--es_ES
	('es_ES','LBL_PATENT', 'patente', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES','LBL_COLOR', 'color', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
	('en_GB','LBL_PATENT', 'paten', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('en_GB','LBL_COLOR', 'color', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	
--es_CL
	('es_CL','LBL_PATENT', 'patente', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_CL','LBL_COLOR', 'color', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
	
	END IF;
END $$;