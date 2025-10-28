DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'historical_data') THEN
  
   -- INSERT INTO historical_data.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES

 
	END IF;
END $$;