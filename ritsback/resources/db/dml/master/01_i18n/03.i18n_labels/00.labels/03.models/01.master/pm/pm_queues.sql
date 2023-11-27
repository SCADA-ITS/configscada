-- 
-- Type:   model
-- Entity: master.data_types
-- File:   data_types.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
	('es_ES',1,'LBL_PRIORITY_QUEUE_VMS','Señalización',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES',1,'LBL_PRIORITY_QUEUE_VMS_DESC','Colas de mensajes para elementos de señalización',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
	('en_GB',1,'LBL_PRIORITY_QUEUE_VMS','Signalling',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('en_GB',1,'LBL_PRIORITY_QUEUE_VMS_DESC','Message queues for signaling elements',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--sk_SK
	--('sk_SK',1,'LBL_PRIORITY_QUEUE_VMS','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	--('sk_SK',1,'LBL_PRIORITY_QUEUE_VMS_DESC','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL
	('es_CL',1,'LBL_PRIORITY_QUEUE_VMS','Señalización',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_CL',1,'LBL_PRIORITY_QUEUE_VMS_DESC','Colas de mensajes para elementos de señalización',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
