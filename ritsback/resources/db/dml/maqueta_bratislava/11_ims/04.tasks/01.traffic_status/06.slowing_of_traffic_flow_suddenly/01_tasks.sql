-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = EN MARCHA
(01010106, 0106, 1, 1, 'SIGNALLING', 'Send signage', true, 1, true, 'SendSignallingToStretch', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);