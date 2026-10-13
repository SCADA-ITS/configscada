-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>

--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010159, 015, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraDAIZone', false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00020159, 015, null, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00030159, 015, null, 1, 'BARRERA', 'Bajada de barrera en zona en zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
--Afección = Pista izquierda
(01010151, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista central
(01020152, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha
(01030153, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista izquierda + central
(01040154, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha + central
(01050155, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Todas las pistas
(01060156, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal todas la pistas
(01070156, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas izquierda
(01080151, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas derecha
(01090153, 015, 1, 1, 'FALLO ESTRUCTURAL', 'Plan de señalización de fallo estructural', true, 1, false, 'SendSignallingToSpath', true, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010159, 015, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020159, 015, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030159, 015, 2, 6, 'COORDINACIÓN', 'Aviso y coordinación con CENCO, bomberos y SAMU', false, 3, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040159, 015, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 4, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050159, 015, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 5, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010150, 015, 3, 1, 'RESTITUCION SEÑALIZACIÓN', 'Restitución de señalizacióna a plan normalizado', true, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020159, 015, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, false,  null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
