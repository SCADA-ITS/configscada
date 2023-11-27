-- 
-- Type:   modules
-- Entity: Help viewer module
-- File:   help_viewer_module.sql
-- Langs:  es_ES, en_GB, es_CL
--

-- 
-- Table: master.i18n_labels
--

-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES', 1, 'LBL_HELP_CONTENTS', 'Contenido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MENU_HELP_CONTENTS', 'Contenido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- en_GB
('en_GB', 1, 'LBL_HELP_CONTENTS', 'Contents', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MENU_HELP_CONTENTS', 'Contenido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- es_CL
('es_CL', 1, 'LBL_HELP_CONTENTS', 'Contenido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MENU_HELP_CONTENTS', 'Contenido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
