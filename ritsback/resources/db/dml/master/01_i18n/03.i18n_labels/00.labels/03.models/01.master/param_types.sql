-- 
-- Type:   model
-- Entity: master.param_types
-- File:   param_types.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
    ('es_ES',1,'LBL_PARAM_TYPE_CONFIG','Config',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('es_ES',1,'LBL_PARAM_TYPE_MEASURE','Medida',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
    ('en_GB',1,'LBL_PARAM_TYPE_CONFIG','Config',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('en_GB',1,'LBL_PARAM_TYPE_MEASURE','Measure',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--sk_SK
    ('sk_SK',1,'LBL_PARAM_TYPE_CONFIG','Konfig',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('sk_SK',1,'LBL_PARAM_TYPE_MEASURE','Meranie',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL
    ('es_CL',1,'LBL_PARAM_TYPE_CONFIG','Config',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('es_CL',1,'LBL_PARAM_TYPE_MEASURE','Medida',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
