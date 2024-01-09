-- 
-- Type:   component
-- Entity: PrintPreview
-- File:   print_preview.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES 
('es_ES', 1, 'LBL_AVAILABLE', 'Disponibles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SELECT_PRINT_FIELDS', 'Campos a imprimir', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SELECT_PRINT_FIELDS_DESCRIPTION', 'Seleccione, de entre los disponibles, los campos a imprimir de la tabla.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SELECT_EXPORT_FIELDS', 'Campos a exportar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SELECT_EXPORT_FIELDS_DESCRIPTION', 'Seleccione, de entre los disponibles, los campos a exportar de la tabla.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_EXPORT', 'Exportar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_PRINT_DATE', 'Fecha impresión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_AVAILABLE', 'Available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SELECT_PRINT_FIELDS', 'Fields to print', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SELECT_PRINT_FIELDS_DESCRIPTION', 'Select, from the available ones, the fields to print from the table.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SELECT_EXPORT_FIELDS', 'Fields to export', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SELECT_EXPORT_FIELDS_DESCRIPTION', 'Select, from the available ones, the fields to export from the table.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_EXPORT', 'Export', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_PRINT_DATE', 'Print date', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--sk_SK
('sk_SK', 1, 'LBL_AVAILABLE', 'Dostupné', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_SELECT_PRINT_FIELDS','Polia na tlač',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_SELECT_PRINT_FIELDS_DESCRIPTION','Z dostupných polí vyberte z tabuľky polia na tlač.',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_SELECT_EXPORT_FIELDS','Polia na tlač',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_SELECT_EXPORT_FIELDS_DESCRIPTION','Z dostupných polí vyberte z tabuľky polia na tlač.',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_EXPORT','Export',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_PRINT_DATE', 'Tlač dátumu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--es_CL
('es_CL', 1, 'LBL_AVAILABLE', 'Disponibles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SELECT_PRINT_FIELDS', 'Campos a imprimir', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SELECT_PRINT_FIELDS_DESCRIPTION', 'Seleccione, de entre los disponibles, los campos a imprimir de la tabla.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SELECT_EXPORT_FIELDS', 'Campos a exportar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SELECT_EXPORT_FIELDS_DESCRIPTION', 'Seleccione, de entre los disponibles, los campos a exportar de la tabla.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_EXPORT', 'Exportar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_PRINT_DATE', 'Fecha impresión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
