      -- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>

--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010959, 95, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraDAIZone', false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
--Afección = Pista izquierda
(01010951, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista central
(01020952, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha
(01030953, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista izquierda + central
(01040954, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha + central
(01050955, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Todas las pistas
(01060956, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01160959, 95, 1, 1, 'MEGAFONIA', 'Activacion de megafonía con obstáculo bloqueando toda la calzada', false, 2, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01260959, 95, 1, 1, 'BARRERA', 'Bajada de barreraen zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal todas la pistas
(01070956, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01170959, 95, 1, 1, 'MEGAFONIA', 'Activación de megafonía con obstáculo bloqueando el ramal', false, 2, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01270959, 95, 1, 1, 'BARRERA', 'Bajada de barrera en zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas izquierda
(01080951, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas derecha
(01090953, 95, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010959, 95, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020959, 95, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030959, 95, 2, 6, 'COORDINACIÓN', 'Aviso y coordinación con CENCO, bomberos y SAMU', false, 3, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040959, 95, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 4, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050959, 95, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 5, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010950, 95, 3, 1, 'RESTITUCIÓN SEÑALIZACIÓN', 'Restitución de señalizacióna a plan normalizado', true, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020959, 95, 3, 1, 'RESTITUCIÓN MEGAFONÍA', 'Desactivación de megafonía', true, 2, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030959, 95, 3, 1, 'RESTITUCIÓN MEGAFONÍA', 'Desactivación de megafonía', true, 2, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040959, 95, 3, 1, 'RESTITUCIÓN BARRERA', 'Subida de barrera', true, 3, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050959, 95, 3, 1, 'RESTITUCIÓN BARRERA', 'Subida de barrera', true, 3, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03060959, 95, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 4, false, null, false,  null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
