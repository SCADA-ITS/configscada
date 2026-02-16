-- 
-- Table: conf.ims_incident_type_road_impacts
--
INSERT INTO conf.ims_incident_type_road_impacts
(incident_type_id, stretch_id, road_impact_id, enabled, visible, created_at, updated_at) values
--Todos los tramos tienen 2 afecciones: pista izquierda, pista derecha
--pista izq
(301,1000,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,1001,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,1002,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,1003,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--pista dcha
(301,1000,20,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,1001,20,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,1002,20,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,1003,20,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);



