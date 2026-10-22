-- 
-- Type:   model
-- Entity: master.element_subtypes
-- File:   element_subtypes.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
--es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_CL', 1, 'LBL_ELEMENT_SUBTYPE_NETWORK_ANALYZER_MODBUS', 'Modbus TCP', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ELEMENT_SUBTYPE_CAMERA_DOMO_MODBUS', 'Domo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ELEMENT_SUBTYPE_CT_TYPE_1', 'AVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES', 1, 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_AF_DESC', 'Panel aspa-flecha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_GR_TXT_GR_DGT_DESC', 'Panel gráfico-texto-gráfico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

