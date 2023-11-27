-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>

 -- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121107501, 030121, 201, 1075, 453, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121107502, 030121, 201, 1075, 454, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121107503, 030121, 201, 1075, 455, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121107504, 030121, 201, 1075, 456, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221107501, 020221, 201, 1075, 53, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221107502, 020221, 201, 1075, 54, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




