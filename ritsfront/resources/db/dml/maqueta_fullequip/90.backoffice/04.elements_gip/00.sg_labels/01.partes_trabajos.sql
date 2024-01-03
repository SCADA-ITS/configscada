DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'elements_gip') THEN
  
INSERT INTO elements_gip.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES
--es_ES
	('es_ES','LBL_PARTES', 'Partes de trabajo', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB 

	('en_GB','LBL_PARTES', 'Orders', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL

	('es_CL','LBL_PARTES', 'Partes de trabajo', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

	END IF;
END $$;

