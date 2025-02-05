-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00020101, 101, null, 5, 'VENTILACIÓN', 'Régimen normal', 'Poner la ventilación en régimen normal para parar ventiladores', true, 2, true, 'TaskTypeVentilation/SetVentilationTubeTask', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00040101, 101, null, 1, 'ENVIAR LIGERO', 'Indicar vehículo ligero',null, false, 3, false, 'SendLigero', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00050101, 101, null, 1, 'ENVIAR CAMIÓN', 'Indicar vehículo camión',null, false, 4, false, 'SendCamión', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00060101, 101, null, 1, 'ENVIAR MERCANCIAS PELIGROSAS', 'Indicar vehículo mercancias peligrosas',null, false, 5, false, 'SendMPeligrosas', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01010101, 101, 1, 5, 'VENTILACIÓN', 'Poner la ventilación en modo automático incendio', 'Cambio del modo de operación de la ventilación modo incendio', true, 1, true, 'TaskTypeVentilation/SetVentilationTubeTask', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020101, 101, 1, 1, 'PRESURIZACIÓN', 'Presurización', 'Encendido de presurización', true, 2, true, 'SendSignallingToStretch', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030101, 101, 1, 3, 'ILUMINACIÓN', 'Reforzar iluminación en túnel 100% soleado', 'Encendido de todos los circuitos de alumbrado del túnel con objeto de mejorar las condiciones de visibilidad por existencia de humos en caso de fuego.', true, 3, true, 'TaskTypeTubeLighting/SetIlluminationTask', null ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01040101, 101, 1, 1, 'SEÑALIZACIÓN', 'Incendio', 'Señalización de cierre de túnel', true, 4, true, 'SendSignallingToStretch', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01080101, 101, 1, 6, 'COMPROBACION DE SISTEMA DE VENTILACION', 'Comprobar sistema de ventilación', 'Comprobación del sistema de ventilación para determinar su buen funcionamiento', true, 6, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01090101, 101, 1, 6, 'AVISO', 'Aviso a emergencias (112)', 'Aviso a emergencias (112)', true, 7, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01100101, 101, 1, 6, 'AVISO', 'Aviso a vigilantes y equipo de retén', 'Aviso a vigilantes y equipo de retén para poner en marcha el protocolo de cierre de túnel, desviar tráfico y colaborar con cuerpos de emergencia', true, 8, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01110101, 101, 1, 6, 'AVISO', 'Aviso a jefe de emergencias vialidad', 'Aviso al jefe de emergencias vialidad para mantener comunicaciones', false, 9, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(03010101, 101, 3, 5, 'VENTILACIÓN', 'Ventilación modo Confort', 'Ventilación modo Confort', true, 1, true, 'TaskTypeVentilation/SetVentilationTubeTask', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020101, 101, 3, 1, 'PRESURIZACIÓN', 'Presurización', 'Apagado de presurización', true, 2, true, 'SendSignallingToStretch', null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030101, 101, 3, 3, 'ILUMINACIÓN', 'Iluminación modo automático', 'Iluminación modo automático', true, 3, true, 'TaskTypeTubeLighting/SetIlluminationTask', null ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);




