-- 
-- Type:   model
-- Entity: master.element_types_param
-- File:   element_types_param.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--Measures element_type_id = 4
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_PA_1','Autos y camionetas',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_PA_2','Camionetas doble rueda trasera y/o autos y camionetas con remolque',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_PA_3','Camiones de dos ejes, maquinaria agrícola o de construccion',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_PA_4','Camiones de mas de dos ejes',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_PA_5','Buses de dos ejes',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_PA_6','Buses de mas de dos ejes',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_PA_7','Motos y motonetas',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_1','<50 km/h',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_2','50-100 km/h',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
('es_CL',1,'LBL_ELEMENT_TYPE_PARAM_VEH_SPEED_3','>100 km/h',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);