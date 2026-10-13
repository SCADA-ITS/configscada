-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(01020659, 65, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 2, true, 'LockCameraById', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010659, 65, 1, 1, 'SEÑALIZACION LLUVIA INTENSA', 'Plan de señalización de lluvia intensa', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA  
(02010659, 65, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020659, 65, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI si se requiere', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030659, 65, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040659, 65, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03010659, 65, 3, 1, 'RESTITUCIÓN SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020659, 65, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(7, 1, 01020659, '42', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
