
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-ILI-02
(010138, 38, 1, 7, '1 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020138, 38, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030138, 38, 1, 6, '3 - AVISO', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112 - TEDAX', null, true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040138, 38, 1, 1, '4 - S07b - SEÑALIZACIÓN', 'Precaución en túnel', null, true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050138, 38, 1, 6, '5 - AVISO', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112 - TEDAX', null, false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060138, 38, 1, 6, '6 - INSTRUCCIONES', 'TEDAX', null, false, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070138, 38, 1, 1, '7 - S01 - SEÑALIZACIÓN', 'Cierre de túnel.', null, false, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080138, 38, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 8, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010338, 38, 3, 6, '1 - AVISO', '','Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020338, 38, 3, 1, '2 - S01 - SEÑALIZACIÓN', 'Cierre de tunel ambos sentidos', null, false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030338, 38, 3, 2, '3 - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel', null, false, 3, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040338, 38, 3, 2, '4 - I04 - ILUMINACIÓN', 'Iluminación de evacuación ambos sentidos', null, false, 4, false, 'TaskTypeTunnelLighting/EvacuationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050338, 38, 3, 7, '5 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060338, 38, 3, 9, '6 - M20 - MEGAFONIA', 'Evacuación del tunel ambos sentidos', null, false, 6, false, 'Evacuation/SendMessagePAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070338, 38, 3, 9, '7 - R03 - RADIODIFUSIÓN', 'Evacuación del tunel ambos sentidos', null, false, 7, false, 'Evacuation/SendMessagePAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080338, 38, 3, 7, '9 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', false, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(020438, 38, 4, 6, '10 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030438, 38, 4, 2, '11 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040438, 38, 4, 9, '12 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050438, 38, 4, 9, '13 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060438, 38, 4, 1, '14 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070438, 38, 4, 7, '15 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010238, 38, 2, 6, '16 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020238, 38, 2, 2, '17 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030238, 38, 2, 9, '18 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040238, 38, 2, 9, '19 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060238, 38, 2, 1, '20 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070238, 38, 2, 1, '21 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080238, 38, 2, 7, '22 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090238, 38, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 9, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011138, 38, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(3, 1, 020138, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020138, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030338, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 030338, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030438, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 030438, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020238, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020238, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 060338, '["22"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 070338, '["25"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
