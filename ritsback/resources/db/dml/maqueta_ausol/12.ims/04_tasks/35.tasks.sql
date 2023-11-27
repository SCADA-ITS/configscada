-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-ACC-02
(010135, 35, 1, 5, '1 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 1, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020135, 35, 1, 3, '2 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030135, 35, 1, 7, '3 - C01 - CCTV', 'Visualización de cámara enclavada', 'El enclavamiento automático de una o varias cámaras y visualización de sus imágenes sobre zonas del vídeo-wall y monitores predefinidos con objeto de realizar un seguimiento de determinados sucesos.', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040135, 35, 1, 6, '4 - AVISO OBLIGATORIO', '','Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050135, 35, 1, 1, '5 - S01 - SEÑALIZACIÓN', 'Cierre de tubo.', null, true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060135, 35, 1, 5, '6 - V03 - VENTILACIÓN', 'Ventilación del tunel en modo extracción',  null, true, 6, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070135, 35, 1, 1, '7 - V04 - VENTILACIÓN', 'Ventilación auxiliar en modo accidental', null, true, 7, false,  'SendPressurizationOn', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080135, 35, 1, 7, '8 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090135, 35, 1, 7, 'DESHABILITAR DAI', 'Deshabilitar alarmas del DAI', null, false, 9, false, 'DeactiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010335, 35, 3, 6, '1 - AVISO', '','Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020335, 35, 3, 1, '2 - S01 - SEÑALIZACIÓN', 'Cierre de tunel ambos sentidos', null, true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030335, 35, 3, 2, '3 - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel', null, true, 3, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040335, 35, 3, 2, '4 - I04 - ILUMINACIÓN', 'Iluminación de evacuación ambos sentidos', null, true, 4, false, 'TaskTypeTunnelLighting/EvacuationTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050335, 35, 3, 7, '5 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060335, 35, 3, 9, '6 - M20 - MEGAFONIA', 'Evacuación del tunel ambos sentidos', null, true, 6, false, 'Evacuation/SendMessagePAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070335, 35, 3, 9, '7 - R03 - RADIODIFUSIÓN', 'Evacuación del tunel ambos sentidos', null, true, 7, false, 'Evacuation/SendMessagePAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010435, 35, 4, 6, '10 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020435, 35, 4, 2, '11 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030435, 35, 4, 9, '12 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040435, 35, 4, 9, '13 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050435, 35, 4, 5, '14 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 5, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060435, 35, 4, 1, '16 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070435, 35, 4, 7, '17 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080435, 35, 4, 1, '15 - V04 - VENTILACIÓN', 'Parar ventilación auxiliar en modo accidental', null, true, 6, false,  'SendPressurizationOff', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010235, 35, 2, 6, '18 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020235, 35, 2, 2, '19 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 2, false, 'TaskTypeTunnelLighting/SetIlluminationEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030235, 35, 2, 9, '20 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false, 'Evacuation/SwitchOffPAGroupEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040235, 35, 2, 9, '21 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', false, 4, false, 'Evacuation/SwitchOffPAZoneEvacuation', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050235, 35, 2, 5, '22 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 5, false, 'TaskTypeVentilation/SetVentilationTubeTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060235, 35, 2, 1, '23 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070235, 35, 2, 7, '24 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080235, 35, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 7, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011135, 35, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(5, 1, 010135, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 010135, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 060135, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 060135, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 020135, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020135, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030335, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 030335, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 050435, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 050435, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020435, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020435, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 050235, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 050235, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020235, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020235, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 060335, '["22"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 070335, '["25"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
