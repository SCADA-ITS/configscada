 -- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010339, 33, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraDAIZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010339, 33, 1, 1, 'SEÑALIZACION ALTO FLUJO VEHICULAR', 'Plan de señalización de alto flujo vehicular', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020339, 33, 1, 1, 'MEGAFONIA', 'Activacion de megafonía', false, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010339, 33, 2, 6, 'COMPROBAR FALSA ALARMA', 'Verificación que no se trata de un vehículo lento o falsa alarma de DAI o ETD', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020339, 33, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030339, 33, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI si se requiere', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040339, 33, 2, 6, 'COORDINACIÓN UOCT', 'Aviso y coordinación con UOCT', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050339, 33, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060339, 33, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010339, 33, 3, 6, 'AVISAR CIERRE INCIDENTE A UOCT', 'Aviso de termino de incidente a UOCT', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020339, 33, 3, 1, 'RESTITUCIÓN SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 2, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030339, 33, 3, 1, 'RESTITUCIÓN MEGAFONÍA', 'Desactivación de megafonía', false, 3, false, 'SendSignallingToSpath', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040339, 33, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
