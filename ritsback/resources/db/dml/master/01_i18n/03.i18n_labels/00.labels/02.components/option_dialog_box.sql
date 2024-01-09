-- 
-- Type:   component
-- Entity: Messagebox
-- File:   messagebox.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES', 1, 'LBL_SAME_MODE_SELECTED', 'Mismo modo seleccionado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SAME_MODE_SELECTED_DESCRIPTION', 'El modo seleccionado es el actual. No es posible cambiarlo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_SAME_MODE_SELECTED', 'Same selected mode', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SAME_MODE_SELECTED_DESCRIPTION', 'The selected mode is the current one. It is not possible to change it', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
 	
--sk_SK
('sk_SK', 1, 'LBL_SAME_MODE_SELECTED','Vybratý rovnaký mód',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_SAME_MODE_SELECTED_DESCRIPTION','Vybrali ste aktuálny režim. Nie je možné zmeniť ho.',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--es_CL
('es_CL', 1, 'LBL_SAME_MODE_SELECTED', 'Mismo modo seleccionado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SAME_MODE_SELECTED_DESCRIPTION', 'El modo seleccionado es el actual. No es posible cambiarlo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);