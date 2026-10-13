-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00011099, 109, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 2, true, 'LockCameraByGalibo', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01011099, 109, 1, 6, 'COMPROBAR SEÑALIZACION EXCESO DE GÁLIBO', 'Comprobar que se ha señalizado el panel exterior con la señalización de exceso de gálibo', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01021099, 109, 1, 1, 'BAJAR BARRERA', 'Comprobar condiciones de seguridad necesarias para la bajada de la barrera', false, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = EN MARCHA
(02011099, 109, 2, 6, 'SEGUIMIENTO VEHÍCULO POR CCTV', 'Seguimiento del vehículo a través del CCTV', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02021099, 109, 2, 6, 'ESCALAMIENTO JEFE EMERGENCIA INTERNO', 'Escalamiento del incidente al jefe de emergencias interno', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02031099, 109, 2, 6, 'COORDINACIÓN CENCO', 'Aviso y coordinación con CENCO', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02041099, 109, 2, 6, 'COMPROBAR FALSA ALARMA', 'Verificación que no se trata de un vehículo lento o falsa alarma de DAI o ETD', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02051099, 109, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02061099, 109, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI si se requiere', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02071099, 109, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02081099, 109, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 8, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION 
(03011099, 109, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03021099, 109, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03031099, 109, 3, 1, 'SUBIR BARRERA', 'Confirmar la operación de subir barrera', false, 3, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03041099, 109, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
