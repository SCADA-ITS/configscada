DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'dgt_gip') THEN
	
	INSERT INTO dgt_gip.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES
	('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS', 'Fotos evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_EVIDENCE_PHOTOS_DESC', 'Fotos evidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

	('es_ES', 'LBL_conductor', 'Conductor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_itv', 'Validez ITV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_seguro', 'Validez seguro', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_marca_vehiculo', 'Marca vehículo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_modelo_vehiculo', 'Modelo vehículo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_categoria_vehiculo', 'Categoria vehículo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_nacionalidad_vehiculo', 'Nacionalidad vehículo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_matricula', 'Matrícula', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_valoracion', 'Confirmación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_fecha', 'Fecha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_CONFIRMAR_DATOS', 'Confirmar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_DGT_DATOS', 'Tránsitos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

	
	  END IF;
END $$;
