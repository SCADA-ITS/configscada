      -- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>

--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010979, 97, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraDAIZone', false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
--Afección = Pista izquierda
(01010971, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista central
(01020972, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha
(01030973, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista izquierda + central
(01040974, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha + central
(01050975, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Todas las pistas
(01060976, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01160979, 97, 1, 1, 'MEGAFONIA', 'Activacion de megafonía con obstáculo bloqueando toda la calzada', false, 2, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01260979, 97, 1, 1, 'BARRERA', 'Bajada de barreraen zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal todas la pistas
(01070976, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01170979, 97, 1, 1, 'MEGAFONIA', 'Activación de megafonía con obstáculo bloqueando el ramal', false, 2, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01270979, 97, 1, 1, 'BARRERA', 'Bajada de barrera en zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas izquierda
(01080971, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas derecha
(01090973, 97, 1, 1, 'OBSTÁCULO PISTA', 'Plan de señalización con obstáculo en pista', true, 1, false, 'SendSignallingToSpath', true, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010979, 97, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020979, 97, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030979, 97, 2, 6, 'COORDINACIÓN', 'Aviso y coordinación con CENCO, bomberos y SAMU', false, 3, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040979, 97, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 4, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050979, 97, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 5, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010970, 97, 3, 1, 'RESTITUCIÓN SEÑALIZACIÓN', 'Restitución de señalizacióna a plan normalizado', true, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020979, 97, 3, 1, 'RESTITUCIÓN MEGAFONÍA', 'Desactivación de megafonía', true, 2, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030979, 97, 3, 1, 'RESTITUCIÓN MEGAFONÍA', 'Desactivación de megafonía', true, 2, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040979, 97, 3, 1, 'RESTITUCIÓN BARRERA', 'Subida de barrera', true, 3, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050979, 97, 3, 1, 'RESTITUCIÓN BARRERA', 'Subida de barrera', true, 3, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03060979, 97, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 4, false, null, false,  null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
