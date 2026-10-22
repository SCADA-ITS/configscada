-- 
-- Type:   modules
-- Entity: Plan module
-- File:   plan_module.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--


-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	
	('es_ES', 1, 'LBL_SEND_PLAN_COMMENT', 'Introduzca un comentario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SEND_PLAN_COMMENT_DIALOG', 'Enviar plan', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	
	('en_GB', 1, 'LBL_SEND_PLAN_COMMENT', 'Enter comments', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SEND_PLAN_COMMENT_DIALOG', ' Send plan', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

	
-- sk_SK
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	
	('sk_SK', 1, 'LBL_SEND_PLAN_COMMENT', 'Zadajte komentár', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SEND_PLAN_COMMENT_DIALOG', ' Predložiť plán', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	
-- es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	('es_CL', 1, 'LBL_SEND_PLAN_COMMENT', 'Introduzca un comentario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SEND_PLAN_COMMENT_DIALOG', 'Enviar plan', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
