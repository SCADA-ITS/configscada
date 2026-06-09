-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, delay, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010501, 501, 1, 3, 'Desactivar alarma', 'Desactivar alarma',null, true, 1, true, 'DeactivateIlumChangeRegimeAlarms', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
