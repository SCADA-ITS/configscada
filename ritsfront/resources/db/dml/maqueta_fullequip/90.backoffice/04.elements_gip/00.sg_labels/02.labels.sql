DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN	
	INSERT INTO elements_gip.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES
    ('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS', 'Fotos evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC', 'Fotos asociadas a la evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    
    ('es_CL', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS', 'Fotos evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_CL', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC', 'Fotos asociadas a la evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    
    ('en_GB', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS', 'Evidence photos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('en_GB', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC', 'Photos associated with the evidence', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
  END IF;
END $$;
