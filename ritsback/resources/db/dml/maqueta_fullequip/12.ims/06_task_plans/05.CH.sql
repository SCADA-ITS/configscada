-- 
-- Table: conf.ims_incident_task_plans
--
INSERT INTO conf.ims_incident_task_plans (incident_task_plan_id, incident_type_task_id, incident_type_id, stretch_id, plan_id, position, enabled, visible, created_at, updated_at) VALUES
-- Numero el incident_task_plan_id como: <incident_type_task_id><stretch_id><position>
-- E1-MET-01
 -- 3 - S05 SEÑALIZACIÓN
(030121103101, 030121, 201, 1031, 389, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121103102, 030121, 201, 1031, 390, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121103103, 030121, 201, 1031, 391, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030121103104, 030121, 201, 1031, 392, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

 -- 6 - S17 SEÑALIZACIÓN
(020221103101, 020221, 201, 1031, 154, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020221103102, 020221, 201, 1031, 155, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);














