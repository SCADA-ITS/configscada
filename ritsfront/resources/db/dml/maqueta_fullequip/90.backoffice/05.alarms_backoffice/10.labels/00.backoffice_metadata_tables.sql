DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'alarms_backoffice') THEN
  
    INSERT INTO alarms_backoffice.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES

    -- 
    -- alarms_backoffice.sg_metadata_tables
    --
    --es_ES
        ('es_ES','LBL_BACKOFFICE_SG_METADATA_TABLES_ELEMENT_TYPES', 'Tipos de equipo', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_BACKOFFICE_SG_METADATA_TABLES_ALARMS', 'Alarmas', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_BACKOFFICE_SG_METADATA_TABLES_NORMALIZE_PROCEDURES', 'Procedimientos para normalizar', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_BACKOFFICE_SG_METADATA_TABLES_RESPOSIBILITIES', 'Responsables', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_IMAGE_GROUP_ALARMS_IMAGES', 'Imágenes del mantenimiento de la alarma', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_IMAGE_GROUP_ALARMS_IMAGES_DESC', 'Si dispone de imágenes que puedan ayudar a realizar un correcto mantenimiento de la alarma, por favor, adjúntelas aquí', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_ATTACHMENT_GROUP_ALARMS', 'Adjuntos de la alarma', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_ES','LBL_ATTACHMENT_GROUP_ALARMS_DESC', 'Si dispone de adjuntos que puedan ayudar a realizar un correcto mantenimiento de la alarma, por favor, adjúntelas aquí', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

    --en_GB
        ('en_GB','LBL_BACKOFFICE_SG_METADATA_TABLES_ELEMENT_TYPES', 'Element types', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('en_GB','LBL_BACKOFFICE_SG_METADATA_TABLES_ALARMS', 'Alarms', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('en_GB','LBL_BACKOFFICE_SG_METADATA_TABLES_NORMALIZE_PROCEDURES', 'Procedimientos para normalizar', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),     
        ('en_GB','LBL_BACKOFFICE_SG_METADATA_TABLES_RESPOSIBILITIES', 'Responsibles', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('en_GB','LBL_IMAGE_GROUP_ALARMS_IMAGES', 'Maintenance alarms images', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('en_GB','LBL_IMAGE_GROUP_ALARMS_IMAGES_DESC', 'If you have images that can help carry out correct maintenance of the alarm, please attach them here', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('en_GB','LBL_ATTACHMENT_GROUP_ALARMS', 'Maintenance alarms attachments', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('en_GB','LBL_ATTACHMENT_GROUP_ALARMS_DESC', 'If you have attachments that can help carry out correct maintenance of the alarm, please attach them here', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

    --es_CL
        ('es_CL','LBL_BACKOFFICE_SG_METADATA_TABLES_ELEMENT_TYPES', 'Tipos de equipo', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_BACKOFFICE_SG_METADATA_TABLES_ALARMS', 'Alarmas', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_BACKOFFICE_SG_METADATA_TABLES_NORMALIZE_PROCEDURES', 'Procedimientos para normalizar', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_BACKOFFICE_SG_METADATA_TABLES_RESPOSIBILITIES', 'Responsables', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_IMAGE_GROUP_ALARMS_IMAGES', 'Imágenes del mantenimiento de la alarma', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_IMAGE_GROUP_ALARMS_IMAGES_DESC', 'Si dispone de imágenes que puedan ayudar a realizar un correcto mantenimiento de la alarma, por favor, adjúntelas aquí', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_ATTACHMENT_GROUP_ALARMS', 'Adjuntos de la alarma', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
        ('es_CL','LBL_ATTACHMENT_GROUP_ALARMS_DESC', 'Si dispone de adjuntos que puedan ayudar a realizar un correcto mantenimiento de la alarma, por favor, adjúntelas aquí', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
       
	END IF;
END $$;