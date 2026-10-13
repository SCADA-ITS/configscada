-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00021149, 114, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCamFireZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01011149, 114, 1, 1, 'SEÑALIZACION VEHICULO EXCESO VELOCIDAD', 'Plan de señalización de vehículo circulando a velocidad excesiva', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA  
(02011149, 114, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02021149, 114, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03011149, 114, 3, 1, 'RESTITUCIÓN SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03021149, 114, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
