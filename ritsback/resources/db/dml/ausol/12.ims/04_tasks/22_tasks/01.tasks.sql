
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E1-AMB-01
(010122, 22, 1, 7, '1 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020122, 22, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030122, 22, 1, 6, '3 - AVISO', 'Equipo de primera intervención', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040122, 22, 1, 5, '4 - V07 - VENTILACIÓN', 'Comprobación del funcionamiento del sistema de ventilación', 'Comprobación de si el sistema de ventilación funciona correctamente', false, 4, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050122, 22, 1, 1, '5 - S20/S19 - SEÑALIZACIÓN', 'Señalización para excesiba concentración de CO y/o NO', null, true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060122, 22, 1, 6, '6 - AVISO OBLIGATORIO', 'Jefe de seguridad - Guardia Civil de Trafico - Equipo primera intervención - CECEM-112', null, false, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070122, 22, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 7, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010322, 22, 3, 6, '1 - AVISO', '','Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020322, 22, 3, 1, '2 - S01 - SEÑALIZACIÓN', 'Cierre de tunel ambos sentidos', null, false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030322, 22, 3, 2, '3 - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel', null, false, 3, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040322, 22, 3, 2, '4 - I04 - ILUMINACIÓN', 'Iluminación de evacuación ambos sentidos', null, false, 4, false, 'TaskTypeTunnelLighting/EvacuationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050322, 22, 3, 7, '5 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060322, 22, 3, 9, '6 - M20 - MEGAFONIA', 'Evacuación del tunel ambos sentidos', null, false, 6, false, 'Evacuation/SendMessagePAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070322, 22, 3, 9, '7 - R03 - RADIODIFUSIÓN', 'Evacuación del tunel ambos sentidos', null, false, 7, false, 'Evacuation/SendMessagePAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010422, 22, 4, 6, '9 - AVISO', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - CECEM-112', null, false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020422, 22, 4, 2, '10 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030422, 22, 4, 9, '11 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040422, 22, 4, 9, '12 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050422, 22, 4, 1, '13 - S18 - SEÑALIZACIÓN', 'Retirar señalización de cierre de tunel', 'Retirar la señalización de cierre de accesos al túnel', false, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010222, 22, 2, 2, '14 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', false, 1, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020222, 22, 2, 9, '15 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', false, 2, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030222, 22, 2, 9, '16 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 3, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040222, 22, 2, 1, '17 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050222, 22, 2, 5, '18 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', false, 5, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060222, 22, 2, 1, '19 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070222, 22, 2, 7, '20 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', false, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080222, 22, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 8, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011122, 22, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES

(3, 1, 020122, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020122, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030322, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 030322, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020422, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020422, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 050222, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 050222, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 010222, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 010222, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 060322, '["22"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 070322, '["25"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
