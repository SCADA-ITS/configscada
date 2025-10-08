DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents') THEN
  
	INSERT INTO incidents.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES
	('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS', 'Fotos evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC', 'Fotos asociadas a la evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS', 'Documentos evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_DOCUMENTS_DESC', 'Documentos asociados a la evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_SKETCH_ACCIDENT', 'Croquis accidente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_SKETCH_ACCIDENT_DESC', 'Croquis del accidente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
		
  END IF;
END $$;
