-- 
-- Type:   component
-- Entity: Treeview
-- File:   treeview.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--


INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES', 1, 'LBL_COLLAPSE_ALL', 'Contraer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_EXPAND_ALL', 'Expandir', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_COLLAPSE_ALL', 'Collapse all', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_EXPAND_ALL', 'Expand all', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--sk_SK
('sk_SK', 1, 'LBL_COLLAPSE_ALL', 'Schovať všetky', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_EXPAND_ALL', 'Zobraziť všetky', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	
--es_CL
('es_CL', 1, 'LBL_COLLAPSE_ALL', 'Contraer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_EXPAND_ALL', 'Expandir', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);