DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'historical_data') THEN
  
    INSERT INTO historical_data.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES

    -- 
    -- historical_data.sg_metadata_tables
    --
    --es_ES
        ('es_ES','LBL_BACKOFFICE_SG_METADATA_TABLES_HISTORICAL_DATA', 'Datos históricos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_BACKOFFICE_SG_METADATA_TABLES_HISTORICAL_DATA_DESCRIPTION', 'Datos históricos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

    --en_GB
        ('en_GB','LBL_BACKOFFICE_SG_METADATA_TABLES_HISTORICAL_DATA', 'Datos históricos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('en_GB','LBL_BACKOFFICE_SG_METADATA_TABLES_HISTORICAL_DATA_DESCRIPTION', 'Datos históricos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

    --es_CL
        ('es_CL','LBL_BACKOFFICE_SG_METADATA_TABLES_HISTORICAL_DATA', 'Datos históricos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_BACKOFFICE_SG_METADATA_TABLES_HISTORICAL_DATA_DESCRIPTION', 'Datos históricos', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
       
	END IF;
END $$;