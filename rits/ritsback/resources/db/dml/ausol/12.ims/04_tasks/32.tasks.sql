
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-ILI-01
(010132, 32, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020132, 32, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030132, 32, 1, 6, '3 - AVISO', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040132, 32, 1, 9, '4 - M19 - MEGAFONIA', 'Pare junto a poste SOS', 'Emisión de mensajes pregrabados, dirigidos al conductor de un vehículo en movimiento, para que estacione su vehículo en el arcén y en un lugar cercano a un poste sos donde se le explicaran las pautas a seguir.', false, 4, false, 'SendMessagePAGroup', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050132, 32, 1, 1, '5 - S07b - SEÑALIZACIÓN', 'Precaucion en túnel', null, true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060132, 32, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 6, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010232, 32, 2, 6, '6 - AVISO', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', null, false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020232, 32, 2, 9, '7 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 2, false, 'SwitchOffPAGroup', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030232, 32, 2, 3, '8 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 3, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040232, 32, 2, 1, '9 - S17 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050232, 32, 2, 7, '10 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060232, 32, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 6, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011132, 32, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020132, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020132, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 040132, '["21"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 030232, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
