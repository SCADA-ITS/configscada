-- 
-- Type:   modules
-- Entity: Alarm module
-- File:   alarm_module.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
	('es_ES',1,'LBL_DISAGREE','Inválida',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES',1,'LBL_CHECK_TO_DISAGREE_THIS_ALARM','Verificar para desactivar esta alarma',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
	('en_GB',1,'LBL_DISAGREE','Disagree',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('en_GB',1,'LBL_CHECK_TO_DISAGREE_THIS_ALARM','Check to disagree this alarm',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	
--sk_SK
	('sk_SK',1,'LBL_DISAGREE','Nesúhlasiť',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('sk_SK',1,'LBL_CHECK_TO_DISAGREE_THIS_ALARM','Zaškrtnite, ak nesúhlasíte s týmto alarmom',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL
	('es_CL',1,'LBL_CHECK_TO_DISAGREE_THIS_ALARM','Verificar para desactivar esta alarma',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
   	('es_CL',1,'LBL_DISAGREE','Inválida',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

