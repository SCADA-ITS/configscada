DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'dgt_gip') THEN
	
	INSERT INTO dgt_gip.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES
	('es_ES', 'LBL_DGT_DATOS', 'DGT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

	
	  END IF;
END $$;
