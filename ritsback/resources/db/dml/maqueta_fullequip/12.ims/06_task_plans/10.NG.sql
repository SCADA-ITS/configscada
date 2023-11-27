-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>

 -- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121107801, 030121, 201, 1078, 475, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121107802, 030121, 201, 1078, 476, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121107803, 030121, 201, 1078, 477, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121107804, 030121, 201, 1078, 478, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221107801, 020221, 201, 1078, 121, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);







