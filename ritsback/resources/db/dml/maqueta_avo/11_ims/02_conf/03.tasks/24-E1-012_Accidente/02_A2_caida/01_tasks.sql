-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>

--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010029, 02, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCameraDAIZone', false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
--Afección = Pista izquierda
(01010021, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01110021, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista central
(01020022, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01120022, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha
(01030023, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01130023, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista izquierda + central
(01040024, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01140024, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Pista derecha + central
(01050025, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01150025, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Todas las pistas
(01060026, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01160026, 02, 1, 1, 'BARRERA', 'Bajada de barrera en zona en zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01260026, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 123, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal todas la pistas
(01070026, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01170026, 02, 1, 1, 'BARRERA', 'Bajada de barrera en zona en zonas exteriores afectadas por la incidencia', false, 3, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01270026, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas izquierda
(01080021, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01180021, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Afección = Ramal 2 pistas derecha
(01090023, 02, 1, 1, 'ACCIDENTE EN PISTA', 'Plan de señalización de accidente en pista', true, 1, false, 'SendSignallingToSpath', true, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01190023, 02, 1, 1, 'MEGAFONIA', 'Activacion de megafonía en localizaciones del incidente', false, 2, false, 'SendSignallingToSpath', true, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010029, 02, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 1, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020029, 02, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 2, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030029, 02, 2, 6, 'COORDINACIÓN', 'Aviso y coordinación con CENCO, bomberos y SAMU', false, 3, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040029, 02, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 4, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050029, 02, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 5, false, null, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION
(03010020, 02, 3, 1, 'SIN ACCIDENTE', 'Restitución de señalizacióna a plan normalizado', true, 1, false, null, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020020, 02, 3, 1, 'BARRERA', 'Subir barrera', false, 2, false, 'SendSignallingToSpath', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030020, 02, 3, 1, 'MEGAFONIA', 'Desactivar megafonía', false, 3, false, 'SendSignallingToSpath', true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040029, 02, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 4, false, null, false,  null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
