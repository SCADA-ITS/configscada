
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- P-AMB-01
(010108, 8, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020108, 8, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030108, 8, 1, 5, '3 - V01 - VENTILACIÓN', 'Poner la ventilación en modo servicio', 'Cambio del modo de operación de la ventilación a servicio.', true, 3, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040108, 8, 1, 5, '4 - V05 - VENTILACIÓN', 'Poner la ventilación en modo manual', 'Cambio del modo de operación de la ventilación a manual.', false, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050108, 8, 1, 6, '5 - AVISO', 'Apertura orden de trabajo', 'Apertura orden de trabajo.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060108, 8, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 6, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010208, 8, 2, 3, '6 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 1, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020208, 8, 2, 5, '7 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 2, false,  'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030208, 8, 2, 7, '8 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040208, 8, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 4, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011108, 8, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES


(3, 1, 020108, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020108, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 030108, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 040108, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 010208, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 020208, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 020208, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

