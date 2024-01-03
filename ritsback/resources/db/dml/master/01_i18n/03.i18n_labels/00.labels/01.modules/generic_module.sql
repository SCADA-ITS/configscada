-- 
-- Type:   modules
-- Entity: genericModule
-- File:   generic_module.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES',1,'LBL_LOADING_DATA','Cargando datos',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES',1,'LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION_OR_IDS','Fallo al abrir el módulo, sin módulo o ID',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
('en_GB',1,'LBL_LOADING_DATA','Loading data',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('en_GB',1,'LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION_OR_IDS','Failure to open the module, no module or ID',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--sk_SK
('sk_SK',1,'LBL_LOADING_DATA','Načítanie údajov',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK',1,'LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION_OR_IDS','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	
--es_CL
('es_CL',1,'LBL_LOADING_DATA','Cargando datos',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION_OR_IDS','Fallo al abrir el módulo, sin módulo o ID',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
