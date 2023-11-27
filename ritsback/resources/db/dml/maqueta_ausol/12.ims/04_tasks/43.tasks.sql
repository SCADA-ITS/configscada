
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-RUI-02
(010143, 43, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020143, 43, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030143, 43, 1, 6, '3 - AVISO OBLIGATORIO', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', null, true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040143, 43, 1, 1, '4 - S01 - SEÑALIZACIÓN', 'Cierre de túnel.', null, true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050143, 43, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 5, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010343, 43, 3, 6, '1 - AVISO', '','Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020343, 43, 3, 1, '2 - S01 - SEÑALIZACIÓN', 'Cierre de tunel ambos sentidos', null, true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030343, 43, 3, 2, '3 - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel', null, true, 3, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040343, 43, 3, 2, '4 - I04 - ILUMINACIÓN', 'Iluminación de evacuación ambos sentidos', null, true, 4, false, 'TaskTypeTunnelLighting/EvacuationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050343, 43, 3, 7, '5 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060343, 43, 3, 9, '6 - M20 - MEGAFONIA', 'Evacuación del tunel ambos sentidos', null, true, 6, false, 'Evacuation/SendMessagePAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070343, 43, 3, 9, '7 - R03 - RADIODIFUSIÓN', 'Evacuación del tunel ambos sentidos', null, true, 7, false, 'Evacuation/SendMessagePAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010443, 43, 4, 6, '6 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020443, 43, 4, 2, '7 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030443, 43, 4, 9, '8 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040443, 43, 4, 9, '9 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', true, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060443, 43, 4, 1, '10 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070443, 43, 4, 7, '11 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010243, 43, 2, 6, '6 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020243, 43, 2, 2, '7 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030243, 43, 2, 9, '8 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040243, 43, 2, 9, '9 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', true, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060243, 43, 2, 1, '10 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070243, 43, 2, 7, '11 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080243, 43, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 8, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011143, 43, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020143, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020143, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030343, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 030343, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020443, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020443, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020243, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020243, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 060343, '["22"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 070343, '["25"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
