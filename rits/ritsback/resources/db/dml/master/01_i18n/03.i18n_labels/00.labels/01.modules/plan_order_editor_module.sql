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
	
	('es_ES', 1, 'LBL_CURRENT_VALUE', 'Valor actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_REGIME', 'Regimen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	
	('en_GB', 1, 'LBL_CURRENT_VALUE', 'Current value', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_REGIME', 'Regime', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

	
-- sk_SK
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	
	('sk_SK', 1, 'LBL_REGIME', 'Režim', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	
-- es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	('es_CL', 1, 'LBL_CURRENT_VALUE', 'Valor actual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_REGIME', 'Regimen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
