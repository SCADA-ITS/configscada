-- 
-- Table: conf.ims_incident_type_road_impacts
--
INSERT INTO conf.ims_incident_type_road_impacts
(incident_type_id, stretch_id, road_impact_id, enabled, visible, created_at, updated_at) values
--Todos los tramos tienen 2 afecciones: pista izquierda, pista derecha
--pista izq
(301,4001,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,4002,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

--pista dcha
(301,4001,20,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(301,4002,20,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);



