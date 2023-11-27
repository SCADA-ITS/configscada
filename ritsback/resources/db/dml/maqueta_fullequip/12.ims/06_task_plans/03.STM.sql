-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>

 -- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121101801, 030121, 201, 1018, 351, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121101802, 030121, 201, 1018, 352, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121101803, 030121, 201, 1018, 353, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121101804, 030121, 201, 1018, 354, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221101801, 020221, 201, 1018, 138, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221101802, 020221, 201, 1018, 878, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
















