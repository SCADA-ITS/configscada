-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, delay, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010101, 101, null, 1, 'PARAR VENTILACION', 'Parar ventilación',null, true, 1, true, 'TaskTypeVentilation/ByFireDet/StopVentilationByFireDetTask', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL - AL CAMBIAR DE AFECCIÓN
(00040101, 101, 1, 1, 'ENVIAR LIGERO', 'Indicar vehículo ligero',null, true, 1, true, 'TaskTypeVentilation/ByFireDet/EvacuationByFireDetTaskLigero', 10, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00050101, 101, 1, 1, 'ENVIAR CAMIÓN', 'Indicar vehículo camión',null, true, 1, true, 'TaskTypeVentilation/ByFireDet/EvacuationByFireDetTaskCamion', 20, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00060101, 101, 1, 1, 'ENVIAR MERCANCIAS PELIGROSAS', 'Indicar vehículo mercancias peligrosas',null, true, 1, true, 'TaskTypeVentilation/ByFireDet/EvacuationByFireDetTaskMPeligrosas', 30, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = INICIAL
(01010101, 101, 1, 3, 'EXTRACCION',null,  'Ejecutar plan automatico de modo extraccion', false, 2, false, 'TaskTypeVentilation/ByFireDet/ExtractionByFireDetTask',null, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020101, 101, 1, 1, 'PRESURIZACIÓN', 'Presurización', 'Encendido de presurización', true, 3, true, 'SendSignallingToStretch', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030101, 101, 1, 3, 'ILUMINACIÓN', 'Reforzar iluminación en túnel 100% soleado', 'Encendido de todos los circuitos de alumbrado del túnel con objeto de mejorar las condiciones de visibilidad por existencia de humos en caso de fuego.', true, 4, true, 'TaskTypeTubeLighting/SetIlluminationTask', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01040101, 101, 1, 1, 'SEÑALIZACIÓN', 'Incendio', 'Señalización de cierre de túnel', true, 5, true, 'SendSignallingToStretch', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01120101, 101, 1, 1, 'ILUMINACIÓN DE EMERGENCIA', 'Encender iluminacion de emergencia al 100%', 'Encender iluminacion de emergencia al 100%.', true, 6, true, 'SendSignallingToStretch', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01060101, 101, 1, 6, 'COMPROBACION DE SISTEMA DE VENTILACION', 'Comprobar sistema de ventilación', 'Comprobación del sistema de ventilación para determinar su buen funcionamiento', true, 7, false, null, null,null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01080101, 101, 1, 6, 'COMPROBACION ESTADO TRAFICO', 'BAJADA DE BARRERAS', 'Comprobación del estado del tráfico y proceder a bajar barreras si las condiciones lo permiten', true, 8, false, null, null,null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01090101, 101, 1, 6, 'AVISO', 'Aviso a emergencias (112)', 'Aviso a emergencias (112)', true, 9, false, null, null,null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01100101, 101, 1, 6, 'AVISO', 'Aviso a vigilantes y equipo de retén', 'Aviso a vigilantes y equipo de retén para poner en marcha el protocolo de cierre de túnel, desviar tráfico y colaborar con cuerpos de emergencia', true, 10, false, null, null,null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01110101, 101, 1, 6, 'AVISO', 'Aviso a jefe de emergencias vialidad', 'Aviso al jefe de emergencias vialidad para mantener comunicaciones', false, 11, false, null, null,null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCIÓN
(03040101, 101, 3, 1, 'VENTILACION', 'Restaurar ventilación','Volver al estado anterior la ventilación', true, 1, true, 'TaskTypeVentilation/ByFireDet/StartVentilationByFireDetTask', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020101, 101, 3, 1, 'PRESURIZACIÓN', 'Presurización', 'Apagado de presurización', true, 3, true, 'SendSignallingToStretch', null,null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030101, 101, 3, 3, 'ILUMINACIÓN', 'Iluminación modo automático', 'Iluminación modo automático', true, 4, true, 'TaskTypeTubeLighting/SetIlluminationTask', null ,null,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050101, 101, 3, 1, 'ILUMINACIÓN DE EMERGENCIA', 'Apagar iluminacion de emergencia al 100%', 'Apagar iluminacion de emergencia al 100%.', true, 5, true, 'SendSignallingToStretch', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = AL CANCELAR LA INCIDENCIA
(101020101, 101, 101, 1, 'PRESURIZACIÓN', 'Presurización', 'Apagado de presurización', true, 3, true, 'SendSignallingToStretch', null,null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101040101, 101, 101, 1, 'VENTILACION', 'Arrancar la ventilación','Volver al estado anterior la ventilación', true, 1, true, 'TaskTypeVentilation/ByFireDet/StartVentilationByFireDetTask', null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



