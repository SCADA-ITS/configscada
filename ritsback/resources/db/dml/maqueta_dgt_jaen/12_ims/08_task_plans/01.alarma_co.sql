-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>
--Despeñaperros direccion NORTE--
--planes de señalizacion de precaucion
(0202201100100002, 0102201, 201, 1001, 6, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0202201200100001, 0102201, 201, 2001, 23, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0202201300100002, 0102201, 201, 3001, 15, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--planes de vuelta a la normalidad del tunel
(0203201100100002, 0203201, 201, 1001, 60, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203201200100002, 0203201, 201, 2001, 64, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0203201300100002, 0203201, 201, 3001, 62, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Despeñaperros direccion SUR--
--planes de señalizacion de precaucion
(0205201100200002, 0102201, 201, 1002, 9, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0205201200200002, 0102201, 201, 2002, 25, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0205201300200002, 0102201, 201, 3002, 17, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--planes de vuelta a la normalidad del tunel
(0206201100200002, 0203201, 201, 1002, 61, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0206201200200002, 0203201, 201, 2002, 65, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0206201300200002, 0203201, 201, 3002, 63, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

