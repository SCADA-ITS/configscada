DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') THEN
--	INSERT INTO reporting_bo.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES

  END IF;
END $$;
