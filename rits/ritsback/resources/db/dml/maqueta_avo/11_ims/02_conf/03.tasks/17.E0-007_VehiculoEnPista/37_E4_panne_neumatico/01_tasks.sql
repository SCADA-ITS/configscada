-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>

--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010379, 37, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraDAIZone', false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
--Afección = Pista izquierda
(01010371, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01110371, 37, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
            
--Afección = Pista central
(01020372, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01120372, 37, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha
(01030373, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01130373, 37, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista izquierda + central
(01040374, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01140374, 37, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha + central
(01050375, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01150375, 37, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Todas las pistas
(01060376, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01160376, 37, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01260376, 37, 1, 1, 'BARRERA', 'Bajada de barrera en zona en zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal todas la pistas
(01070376, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01270376, 37, 1, 1, 'BARRERA', 'Bajada de barrera en zona en zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas izquierda
(01080371, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas derecha
(01090373, 37, 1, 1, 'VEHÍCULO PISTA', 'Plan de señalización con VEHÍCULO en pista', true, 1, false, 'SendSignallingToSpath', true, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010379, 37, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020379, 37, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030379, 37, 2, 6, 'COORDINACIÓN', 'Aviso y coordinación con CENCO, bomberos y SAMU', false, 3, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040379, 37, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 4, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050379, 37, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 5, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010370, 37, 3, 1, 'SIN VEHÍCULO', 'Restitución de señalizacióna a plan normalizado', true, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020379, 37, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 2, false, null, false,  null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
