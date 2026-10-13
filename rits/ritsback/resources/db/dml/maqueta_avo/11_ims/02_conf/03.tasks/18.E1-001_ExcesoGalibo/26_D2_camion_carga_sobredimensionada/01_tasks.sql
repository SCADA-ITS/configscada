-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010269, 26, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 2, true, 'LockCameraByGalibo', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010269, 26, 1, 6, 'COMPROBAR SEÑALIZACION EXCESO DE GÁLIBO', 'Comprobar que se ha señalizado el panel exterior con la señalización de exceso de gálibo', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020269, 26, 1, 1, 'BAJAR BARRERA', 'Comprobar que se cumplen las condiciones de seguridad necesarias para la bajada de la barrera', false, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02010269, 26, 2, 6, 'SEGUIMIENTO VEHÍCULO POR CCTV', 'Seguimiento del vehículo a través del CCTV', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020269, 26, 2, 6, 'ESCALAMIENTO JEFE EMERGENCIA INTERNO', 'Escalamiento del incidente al jefe de emergencias interno', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030269, 26, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040269, 26, 2, 6, 'COMPROBAR FALSA ALARMA', 'Verificación que no se trata de un vehículo lento o falsa alarma de DAI o ETD', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050269, 26, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060269, 26, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI si se requiere', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02070269, 26, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02080269, 26, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 8, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03010269, 26, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020269, 26, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030269, 26, 3, 1, 'RESTITUIR SEÑALIZACION', 'Confirmar la operación de subir barrera y comprobar que en el panel exterior se ha restituido la señalizacióna al estado anterior', true, 3, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040269, 26, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
