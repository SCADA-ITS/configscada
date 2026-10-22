
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-TRA-01
(010133, 33, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020133, 33, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030133, 33, 1, 6, '3 - AVISO OBLIGATORIO', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', null, true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040133, 33, 1, 1, '4 - S01 - SEÑALIZACIÓN', 'Cierre de túnel.', null, true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050133, 33, 1, 9, '5 - M16 - MEGAFONIA', 'Túnel bloqueado.', 'Emisión de mensajes pregrabados en el caso de bloqueo total de la calzada.', true, 5, false, 'SendMessagePAGroup', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060133, 33, 1, 9, '6 - R01 - RADIODIFUSIÓN', 'Retencion en el interior de los tuneles', 'Emisión de mensajes de radiodifusión para situaciones de retenciones de tráfico en el interior del túnel.', true, 7, false, 'SendMessagePAZone', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070133, 33, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 7, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010233, 33, 2, 7, '7 - AVISO', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', null, false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020233, 33, 2, 3, '8 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030233, 33, 2, 9, '9 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false, 'SwitchOffPAGroup', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040233, 33, 2, 9, '10 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', true, 4, false, 'SwitchOffPAZone', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050233, 33, 2, 1, '11 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060233, 33, 2, 7, '12 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070233, 33, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 7, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011133, 33, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020133, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020133, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 050133, '["18"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 060133, '["23"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 020233, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
