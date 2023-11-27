
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-FUE-02
(010145, 45, 1, 4, '2 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 1, true,  'TaskTypeVentilation/ByFireDet/StopVentilationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020145, 45, 1, 3, '3 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 2, false, 'TaskTypeTubeLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030145, 45, 1, 6, '5 - AVISO OBLIGATORIO', '', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', true, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040145, 45, 1, 1, '6 - S01 - SEÑALIZACIÓN', 'Cierre de tubo afectado', null, true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050145, 45, 1, 4, '7 - V04 - VENTILACIÓN', 'Ventilación auxiliar en modo accidental', 'Operación de la ventilación auxiliar de las galerías para crear una sobrepresión en las vías de evacuación.', true, 6, false,  'SendPressurizationOn', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060145, 45, 1, 6, '4 - TOMA DE DATOS EN CASO DE MMPP', '', '', false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070145, 45, 1, 7, 'DESHABILITAR DAI CA', 'Deshabilitar alarmas del DAI sentido Cadíz', null, false, 7, false, 'ByFireDet/DeactiveAlarmsDaiCAByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080145, 45, 1, 7, 'DESHABILITAR DAI MA', 'Deshabilitar alarmas del DAI sentido Málaga', null, false, 8, false, 'ByFireDet/DeactiveAlarmsDaiMAByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010345, 45, 3, 4, '8 - V09 - VENTILACIÓN', 'Ventilación del tunel en modo evacuación',  null, true, 1, true,  'TaskTypeVentilation/ByFireDet/EvacuationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(020345, 45, 3, 6, '1 - AVISO', '', 'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', false, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030345, 45, 3, 1, '2 - S01 - SEÑALIZACIÓN', 'Cierre de tunel no afectado', null, false, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040345, 45, 3, 2, '3 - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel 100% diurno', 'Encendido de todos los circuitos de alumbrado del túnel con objeto de mejorar las condiciones de visibilidad por existencia de humos en caso de fuego.', false, 4, false, 'TaskTypeTunnelLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050345, 45, 3, 2, '4 - I04 - ILUMINACIÓN', 'Iluminación de evacuación', 'Encendido del circuito de alumbrado de evacuación del túnel con objeto de mejorar las condiciones de visibilidad para evacuación del mismo.', false, 5, false, 'TaskTypeTunnelLighting/EvacuationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060345, 45, 3, 7, '5 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', false, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070345, 45, 3, 9, '6 - M20 - MEGAFONIA', 'Evacuación del tunel', 'Emisión de los mensajes pregrabados establecidos en el protocolo de evacuación del túnel.', false, 7, false, 'ByFireDet/SendMessagePAGroupByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080345, 45, 3, 9, '7 - R03 - RADIODIFUSIÓN', 'Evacuación del tunel', 'Emisión de los mensajes pregrabados establecidos en el protocolo de evacuación del túnel.', false, 8, false, 'ByFireDet/SendMessagePAZoneByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010445, 45, 4, 7, '10 - AVISO', '',  'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020445, 45, 4, 3, '11 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 2, false, 'TaskTypeTunnelLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030445, 45, 4, 9, '12 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false, 'ByFireDet/SwitchOffPAGroupByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040445, 45, 4, 9, '13 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', true, 4, false, 'ByFireDet/SwitchOffPAZoneByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050445, 45, 4, 4, '14 - V04 - VENTILACIÓN', 'Parar ventilación auxiliar en modo accidental',null, true, 5, false,  'SendPressurizationOff', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060445, 45, 4, 4, '15 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 6, false,  'TaskTypeVentilation/ByFireDet/StopVentilationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070445, 45, 4, 1, '16 - S18a/b - SEÑALIZACIÓN', 'Retirar señalización de cierre de tubo no afectado', 'Retirar la señalización de emergencia', true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080445, 45, 4, 7, '17 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 8, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010245, 45, 2, 7, '17 - AVISO', '',  'Equipo de primera intervención - Guardia Civil de Trafico - Jefe de Seguridad - Jefe de Explotación - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020245, 45, 2, 3, '18 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 2, false, 'TaskTypeTunnelLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030245, 45, 2, 9, '19 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false,  'ByFireDet/SwitchOffPAGroupByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040245, 45, 2, 9, '20 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', true, 4, false, 'ByFireDet/SwitchOffPAZoneByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050245, 45, 2, 4, '21 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 5, false,  'TaskTypeVentilation/ByFireDet/StopVentilationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060245, 45, 2, 1, '22 - S18a/b - SEÑALIZACIÓN', 'Retirar señalización de cierre de tubo afectado', 'Retirar la señalización de emergencia', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070245, 45, 2, 7, '23 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080245, 45, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 8, true, 'ByFireDet/ActiveAlarmsDaiByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011145, 45, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ByFireDet/ActiveAlarmsDaiByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, delay, enabled, visible, created_at, updated_at) VALUES

(090345, 45, 3, 4, '9 - V03 - VENTILACIÓN', 'Ventilación del tunel en modo extracción',  null, true, 9, false,  'TaskTypeVentilation/ByFireDet/ExtractionByFireDetTask', 600, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(3, 1, 020145, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 020145, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 040345, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 040345, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 070345, '["22"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 080345, '["25"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 020445, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 020245, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


