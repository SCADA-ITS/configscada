-- 
-- Type:   model
-- Entity: master.stretch_types
-- File:   stretch_types.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
    ('es_ES',1,'LBL_STRETCH_TYPE_ELEMENT','Tramo de elemento',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('es_ES',1,'LBL_STRETCH_TYPE_INCIDENT_REPORT','Tramo de incidencia',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
    ('en_GB',1,'LBL_STRETCH_TYPE_ELEMENT','Element stretch',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('en_GB',1,'LBL_STRETCH_TYPE_INCIDENT_REPORT','Incident stretch',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--sk_SK

--es_CL
    ('es_CL',1,'LBL_STRETCH_TYPE_ELEMENT','Tramo de elemento',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('es_CL',1,'LBL_STRETCH_TYPE_INCIDENT_REPORT','Tramo de incidencia',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);