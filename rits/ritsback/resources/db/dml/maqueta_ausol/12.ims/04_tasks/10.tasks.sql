
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- P-TRA-01
(010110, 10, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020110, 10, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030110, 10, 1, 6, '3 - AVISO', 'Equipo de primera intervención', 'Equipo de primera intervención.', false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040110, 10, 1, 1, '4 - S07b - SEÑALIZACIÓN', 'Precaución en túnel', null, true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050110, 10, 1, 6, '5 - AVISO', 'Apertura orden de trabajo', 'Apertura orden de trabajo.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060110, 10, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 6, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010210, 10, 2, 6, '6 - AVISO', 'Equipo de primera intervención', 'Equipo de primera intervención.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020210, 10, 2, 1, '7 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030210, 10, 2, 3, '8 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 3, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040210, 10, 2, 7, '9 - C05 -CCTV ', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050210, 10, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 5, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011110, 10, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020110, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020110, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 030210, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);