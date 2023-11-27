-- 
-- Type:   model
-- Entity: static.ims_incident_levels.sql
-- File:   ims_incident_levels.sql.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
	('es_ES', 1, 'LBL_ROAD_IMPACT_LEFT_LANE', 'Carril izquierdo afectado', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_ROAD_IMPACT_RIGHT_LANE', 'Carril derecho afectado', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_ROAD_IMPACT_LEFT_CENTER_LANES', 'Carriles izquierdo y central afectados', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_ROAD_IMPACT_RIGHT_CENTER_LANES', 'Carriles derecho y central afectados', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	
--en_GB
	('en_GB', 1, 'LBL_ROAD_IMPACT_LEFT_LANE', 'Affected left lane', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_ROAD_IMPACT_RIGHT_LANE', 'Affected right lane', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_ROAD_IMPACT_LEFT_CENTER_LANES', 'Affected left and center lanes', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_ROAD_IMPACT_RIGHT_CENTER_LANES', 'Affected right and center lanes', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	
--sk_SK
	--('sk_SK', 1, 'LBL_ROAD_IMPACT_LEFT_LANE', '', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	--('sk_SK', 1, 'LBL_ROAD_IMPACT_RIGHT_LANE', '', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	--('sk_SK', 1, 'LBL_ROAD_IMPACT_LEFT_CENTER_LANES', '', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	--('sk_SK', 1, 'LBL_ROAD_IMPACT_RIGHT_CENTER_LANES, '', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	
--es_CL
	('es_CL', 1, 'LBL_ROAD_IMPACT_LEFT_LANE', 'Pista izquierda afectada', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_ROAD_IMPACT_RIGHT_LANE', 'Pista derecha afectada', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_ROAD_IMPACT_LEFT_CENTER_LANES', 'Pistas izquierda y central afectadas', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_ROAD_IMPACT_RIGHT_CENTER_LANES', 'Pistas derecha y central afectadas', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);