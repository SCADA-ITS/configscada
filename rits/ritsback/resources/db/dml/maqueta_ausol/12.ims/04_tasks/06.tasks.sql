
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- I-TRA-04
(010106, 6, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020106, 6, 1, 3, '2a - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel 100% diurno', 'Encendido de todos los circuitos de alumbrado del túnel con objeto de mejorar las condiciones de visibilidad por existencia de humos en caso de fuego.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030106, 6, 1, 3, '2b - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 3, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040106, 6, 1, 5, '3 - V08 - VENTILACIÓN', 'Poner la ventilación en modo automático mantenimiento', 'Cambio del modo de operación de la ventilación de manual a automático mantenimiento.', true, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050106, 6, 1, 1, '4 - S23 - SEÑALIZACIÓN', 'Corte de carril en túnel por obras', null, true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060106, 6, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 6, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010206, 6, 2, 1, '5 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020206, 6, 2, 5, '6 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 2, false,  'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030206, 6, 2, 3, '7 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 3, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040206, 6, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 8, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011106, 6, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020106, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020106, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 030106, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 030106, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 040106, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 020206, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 020206, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 030206, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

