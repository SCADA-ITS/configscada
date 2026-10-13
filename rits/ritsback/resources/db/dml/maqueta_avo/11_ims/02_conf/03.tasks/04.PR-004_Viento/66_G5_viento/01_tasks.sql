-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010669, 66, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraById', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010669, 66, 1, 1, 'SEÑALIZACION VIENTO LATERAL', 'Plan de señalización de viento lateral', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010669, 66, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020669, 66, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010669, 66, 3, 1, 'RESTITUCIÓN SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020669, 66, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(7, 1, 00010669, '42', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);