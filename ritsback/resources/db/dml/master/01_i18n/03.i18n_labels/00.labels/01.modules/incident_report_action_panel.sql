-- 
-- Type:   modules
-- Entity: incidentReportActionPanel
-- File:   incident_report_action_panel.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
    ('es_ES',1,'LBL_OPERATION_PLAN','Plan de operación',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('es_ES',1,'LBL_INCIDENT_ATTACHMENTS_DESCRIPTION','Archivos adjuntos',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
    ('en_GB',1,'LBL_OPERATION_PLAN','Operation plan',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('en_GB',1,'LBL_INCIDENT_ATTACHMENTS_DESCRIPTION','Attachments',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL
    ('es_CL',1,'LBL_OPERATION_PLAN','Plan de operación',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
    ('es_CL',1,'LBL_INCIDENT_ATTACHMENTS_DESCRIPTION','Archivos adjuntos',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
