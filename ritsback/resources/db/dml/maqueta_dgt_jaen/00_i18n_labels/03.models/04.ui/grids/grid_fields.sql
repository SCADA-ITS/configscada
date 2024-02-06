-- 
-- Type:   model
-- Entity: ui.grid_fields
-- File:   grid_fields.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
    ('es_ES', 1, 'LBL_GRID_FIELD_TRAFFIC_LIGHT_GROUP', 'Grupo semafórico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_ES', 1, 'LBL_GRID_FIELD_FAN_MANUAL', 'Manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_ES', 1, 'LBL_GRID_FIELD_FAN_AUTOMATIC', 'Automático', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    --Medidor CO
    ('es_ES',1,'LBL_CO_CONCENTRATION','Concentración Co ',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--Opacímetro
    ('es_ES',1,'LBL_OPAC_CONCENTRATION','Concentración Opac ',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),


--en_GB
    ('en_GB', 1, 'LBL_GRID_FIELD_TRAFFIC_LIGHT_GROUP', 'Semaphoric group', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('en_GB', 1, 'LBL_GRID_FIELD_FAN_MANUAL', 'Manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('en_GB', 1, 'LBL_GRID_FIELD_FAN_AUTOMATIC', 'Automatic', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    --Medidor CO
    ('en_GB',1,'LBL_CO_CONCENTRATION','Co Concentration',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

    --Opacímetro
    ('en_GB',1,'LBL_OPAC_CONCENTRATION','Opac Concentration',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),


--es_CL
    ('es_CL', 1, 'LBL_GRID_FIELD_TRAFFIC_LIGHT_GROUP', 'Grupo semafórico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_CL', 1, 'LBL_GRID_FIELD_FAN_MANUAL', 'Manual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_CL', 1, 'LBL_GRID_FIELD_FAN_AUTOMATIC', 'Automático', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    --Medidor CO
    ('es_CL',1,'LBL_CO_CONCENTRATION','Concentración Co ',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

    --Opacímetro
    ('es_CL',1,'LBL_OPAC_CONCENTRATION','Concentración Opac ',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

