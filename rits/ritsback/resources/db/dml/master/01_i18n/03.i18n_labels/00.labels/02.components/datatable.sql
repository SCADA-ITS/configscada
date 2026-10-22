-- 
-- Type:   component
-- Entity: Datatable
-- File:   datatable.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES 
('es_ES', 1, 'LBL_NO_ITEMS_TO_SELECT', 'No hay elementos para seleccionar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_TABLE_EXPORT','Exportar documento',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_TABLE_EXPORT_XLS_DESCRIPTION','Exportar documento en formato XLS',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_TABLE_EXPORT_PDF_DESCRIPTION','Exportar documento en formato PDF',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SELECT_EXPORT_FORMAT','Selección de formato a exportar',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_NO_ITEMS_TO_SELECT', 'No items to select', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_TABLE_EXPORT','Export document',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_TABLE_EXPORT_XLS_DESCRIPTION','Export document in XLS format',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_TABLE_EXPORT_PDF_DESCRIPTION','Export document in PDF format',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SELECT_EXPORT_FORMAT','Select format to export',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--sk_SK
('sk_SK', 1, 'LBL_TABLE_EXPORT','Exportovať dokument',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL
('es_CL', 1, 'LBL_NO_ITEMS_TO_SELECT', 'No hay elementos para seleccionar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_TABLE_EXPORT','Exportar documento',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_TABLE_EXPORT_XLS_DESCRIPTION','Exportar documento en formato XLS',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_TABLE_EXPORT_PDF_DESCRIPTION','Exportar documento en formato PDF',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SELECT_EXPORT_FORMAT','Selección de formato a exportar',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);