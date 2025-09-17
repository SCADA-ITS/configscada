-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>
--Despeñaperros direccion NORTE--
--planes de señalizacion de visibilidad reducida
(0102301100100001, 0102301, 301, 1001, 12, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0102301200100001, 0102301, 301, 2001, 28, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0102301300100001, 0102301, 301, 3001, 20, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--planes de vuelta a la normalidad del tunel
(0203301100100002, 0203301, 301, 1001, 60, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203301200100002, 0203301, 301, 2001, 64, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203301300100002, 0203301, 301, 3001, 62, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


--Despeñaperros direccion SUR--
--planes de señalizacion de visibilidad reducida
(0102301100200001, 0102301, 301, 1002, 13, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0102301200200001, 0102301, 301, 2002, 29, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0102301300200001, 0102301, 301, 3002, 21, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--planes de vuelta a la normalidad del tunel
(0203301100200002, 0203301, 301, 1002, 61, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203301200200002, 0203301, 301, 2002, 65, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203301300200002, 0203301, 301, 3002, 63, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

