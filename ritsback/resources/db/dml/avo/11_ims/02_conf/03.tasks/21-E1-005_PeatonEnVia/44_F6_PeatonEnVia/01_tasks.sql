-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00020449, 44, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraDAIZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      
--ETAPA = INICIAL
(01010449, 44, 1, 1, 'SEÑALIZACION PEATÓN EN TUNEL', 'Plan de señalización y megafonía por peatón en túnel', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020449, 44, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010449, 44, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020449, 44, 2, 6, 'ESCALAMIENTO JEFE INTERNO', 'Escalamiento con Jefe de emergencia interno de ser necesario', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030449, 44, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040449, 44, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050449, 44, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060449, 44, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02070449, 44, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03010449, 44, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020449, 44, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030449, 44, 3, 1, 'SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 3, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040449, 44, 3, 1, 'RESTITUCIÓN MEGAFONÍA', 'Desactivación de megafonía', true, 4, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050449, 44, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
