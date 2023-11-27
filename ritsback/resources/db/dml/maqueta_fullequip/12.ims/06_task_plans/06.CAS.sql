-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>

 -- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121104201, 030121, 201, 1042, 499, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121104202, 030121, 201, 1042, 500, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121104203, 030121, 201, 1042, 501, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121104204, 030121, 201, 1042, 502, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221104201, 020221, 201, 1042, 7, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221104202, 020221, 201, 1042, 865, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221104203, 020221, 201, 1042, 866, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);







