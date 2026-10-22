
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-TRA-04
(010127, 27, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020127, 27, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030127, 27, 1, 6, '3 - AVISO', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - CECEM-112', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040127, 27, 1, 1, '4 - S03 - SEÑALIZACIÓN', 'Corte de carril hasta la sección afectada', null, true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050127, 27, 1, 9, '5 - M07 - MEGAFONIA', 'Corte de carril hasta la sección afectada', 'Emisión de mensajes pregrabados en el caso de corte de carril', false, 5, false, 'SendMessagePAGroup', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060127, 27, 1, 1, '6 - S10 - SEÑALIZACIÓN', 'Vehículo lento en el tunel', null, false, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070127, 27, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 7, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010227, 27, 2, 6, '7 - AVISO', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - CECEM-112', null, false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020227, 27, 2, 3, '8 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030227, 27, 2, 1, '9 - S17 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040227, 27, 2, 9, '10 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 4, false, 'SwitchOffPAGroup', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050227, 27, 2, 7, '11 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060227, 27, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 6, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011127, 27, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020127, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020127, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 050127, '["9"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 020227, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


