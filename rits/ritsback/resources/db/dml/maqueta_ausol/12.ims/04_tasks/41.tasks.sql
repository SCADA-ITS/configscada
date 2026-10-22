
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-AVE-02
(010141, 41, 1, 7, '1 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020141, 41, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030141, 41, 1, 6, '3 - AVISO OBLIGATORIO', '', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040141, 41, 1, 1, '4 - S01 - SEÑALIZACIÓN', 'Cierre de tubo afectado', null, true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050141, 41, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 5, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010341, 41, 3, 6, '1 - AVISO', '', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020341, 41, 3, 1, '2 - S01 - SEÑALIZACIÓN', 'Cierre de tubo no afectado', null, false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030341, 41, 3, 2, '3 - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel 100% diurno', 'Encendido de todos los circuitos de alumbrado del túnel con objeto de mejorar las condiciones de visibilidad por existencia de humos en caso de fuego.', false, 3, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040341, 41, 3, 2, '4 - I04 - ILUMINACIÓN', 'Iluminación de evacuación', 'Encendido del circuito de alumbrado de evacuación del túnel con objeto de mejorar las condiciones de visibilidad para evacuación del mismo.', false, 4, false, 'TaskTypeTunnelLighting/EvacuationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050341, 41, 3, 7, '5 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060341, 41, 3, 9, '6 - M20 - MEGAFONIA', 'Evacuación del tunel', 'Emisión de los mensajes pregrabados establecidos en el protocolo de evacuación del túnel.', false, 6, false, 'Evacuation/SendMessagePAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070341, 41, 3, 9, '7 - R03 - RADIODIFUSIÓN', 'Evacuación del tunel', 'Emisión de los mensajes pregrabados establecidos en el protocolo de evacuación del túnel.', false, 7, false, 'Evacuation/SendMessagePAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010441, 41, 4, 7, '5 - AVISO', '', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020441, 41, 4, 3, '6 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030441, 41, 4, 9, '7 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040441, 41, 4, 9, '8 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050441, 41, 4, 1, '9 - S18a/b - SEÑALIZACIÓN', 'Retirar señalización de cierre de tubo no afectado', 'Retirar la señalización de emergencia', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010241, 41, 2, 3, '10 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 1, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020241, 41, 2, 9, '11 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 2, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030241, 41, 2, 9, '12 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 3, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040241, 41, 2, 1, '13 - S18a/b - SEÑALIZACIÓN', 'Retirar señalización de cierre de tubo afectado', 'Retirar la señalización de emergencia', true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050241, 41, 2, 7, '14 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060241, 41, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 6, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011141, 41, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(3, 1, 020141, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020141, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030341, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 030341, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 020441, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020441, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 010241, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 010241, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 060341, '["22"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 070341, '["25"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
