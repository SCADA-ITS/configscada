
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- E2-ACC-03
(010136, 36, 1, 7, '1 - C01 - Visuaizar camara enclavada', '', '', true, 1, true, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020136, 36, 1, 4, '2 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 2, true, 'TaskTypeVentilation/ByFireDet/StopVentilationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030136, 36, 1, 3, '3 - I01 - ILUMINACIÓN', 'Reforzar iluminación en túnel nocturna', 'Encendido de todos los circuitos de permanente del alumbrado del túnel con regulación al 100% con objeto de mejorar las condiciones de visibilidad en situaciones nocturnas.', false, 3, false, 'TaskTypeTubeLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040136, 36, 1, 6, '5 - AVISO OBLIGATORIO', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', null, true, 5, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050136, 36, 1, 1, '6 - S01 - SEÑALIZACIÓN', 'Cierre de tubo.', null, true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060136, 36, 1, 7, '4 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070136, 36, 1, 1, '7 - V04 - VENTILACIÓN', 'Ventilación auxiliar en modo accidental', null, true, 8, false,  'SendPressurizationOn', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080136, 36, 1, 6, '4 - TOMA DE DATOS', 'En caso de accidente con implicación de mercancías peligrosas, recopilar la información definida en la ficha de la incidencia', null, false, 4, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090136, 36, 1, 7, 'DESHABILITAR DAI CA', 'Deshabilitar alarmas del DAI sentido Cadíz', null, false, 9, false, 'ByFireDet/DeactiveAlarmsDaiCAByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100136, 36, 1, 7, 'DESHABILITAR DAI MA', 'Deshabilitar alarmas del DAI sentido Málaga', null, false, 10, false, 'ByFireDet/DeactiveAlarmsDaiMAByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(110136, 36, 1, 1, '8 - P01 - COMPUERTA', 'Cierre de compuertas del depósito DLT', null, true, 9, false, 'CloseToxicGate', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(080336, 36, 3, 4, '9 - V09 - VENTILACIÓN', 'Ventilación del tunel en modo evacuación',  null, true, 1, true,  'TaskTypeVentilation/ByFireDet/EvacuationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010336, 36, 3, 6, '1 - AVISO', '','Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020336, 36, 3, 1, '2 - S01 - SEÑALIZACIÓN', 'cierre de tubo no afectado', null, true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030336, 36, 3, 2, '3 - I02 - ILUMINACIÓN', 'Reforzar iluminación en túnel', null, true, 4, false, 'TaskTypeTunnelLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040336, 36, 3, 2, '4 - I04 - ILUMINACIÓN', 'Iluminación de evacuación ambos sentidos', null, true, 5, false, 'TaskTypeTunnelLighting/EvacuationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050336, 36, 3, 7, '5 - C02 - CCTV', 'Barrido del túnel', 'Secuencia predeterminada de visualización de cámaras de cctv que permite realizar un barrido de todo el túnel.', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060336, 36, 3, 9, '6 - M20 - MEGAFONIA', 'Evacuación del tunel ambos sentidos', null, true, 7, false, 'ByFireDet/SendMessagePAGroupByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070336, 36, 3, 9, '7 - R03 - RADIODIFUSIÓN', 'Evacuación del tunel ambos sentidos', null, true, 8, false, 'ByFireDet/SendMessagePAZoneByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(030436, 36, 4, 6, '11 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 3, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040436, 36, 4, 2, '12 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 4, false, 'TaskTypeTunnelLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050436, 36, 4, 9, '13 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 5, false, 'ByFireDet/SwitchOffPAGroupByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060436, 36, 4, 9, '14 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', true, 6, false, 'ByFireDet/SwitchOffPAZoneByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070436, 36, 4, 4, '15 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 7, false, 'TaskTypeVentilation/ByFireDet/StopVentilationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080436, 36, 4, 1, '17 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 9, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090436, 36, 4, 7, '18 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 10, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100436, 36, 4, 1, '16 - V04 - VENTILACIÓN', 'Parar ventilación auxiliar en modo accidental', null, true, 8, false,  'SendPressurizationOff', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(010236, 36, 2, 6, '19 - AVISO', '', 'Equipo de primera intervención - Jefe de Seguridad - Jefe de Explotación - Guardia Civil de Trafico - CECEM-112', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020236, 36, 2, 2, '20 - I03 - ILUMINACIÓN', 'Poner la iluminación en modo automático', 'Restitución de los niveles normales de iluminación configurados automáticamente según el nivel de luminosidad detectada por el sistema de detección de niveles lumínicos', true, 2, false, 'TaskTypeTunnelLighting/SetIlluminationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030236, 36, 2, 9, '21 - M17 - MEGAFONIA', 'Interrumpir mensajes de emergencia', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de megafonía', true, 3, false, 'ByFireDet/SwitchOffPAGroupByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040236, 36, 2, 9, '22 - R02 - RADIODIFUSIÓN', 'Detener información mediante radiodifusión', 'Interrupción, desde el centro de control, de la emisión de mensajes de advertencia a los usuarios a través de los sistemas de radiodifusión', true, 4, false, 'ByFireDet/SwitchOffPAZoneByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(050236, 36, 2, 4, '23 - V02 - VENTILACIÓN', 'Parar ventilación', 'Operación de la ventilación en situación de posible fuego en túnel, o fuego en la fase inicial para no ayudar a la propagación de humos.', true, 5, false, 'TaskTypeVentilation/ByFireDet/StopVentilationByFireDetTask', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(060236, 36, 2, 1, '24 - S18 - SEÑALIZACIÓN', 'Retirar señalización de emergencia', 'Retirar la señalización de emergencia', true, 6, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(070236, 36, 2, 7, '25 - C05 - CCTV', 'Salvar grabación de video', 'Operación de salvaguarda de las grabaciones digitales de vídeo correspondientes a un intervalo de tiempo en el que haya ocurrido algo que requiera ser registrado para su posterior informe o análisis.', true, 7, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(080236, 36, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 8, true, 'ByFireDet/ActiveAlarmsDaiByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(090236, 36, 2, 1, '26 - P02 - COMPUERTA', 'Apertura de compuertas del depósito DLT', null, true, 9, false, 'OpenToxicGate', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011136, 36, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ByFireDet/ActiveAlarmsDaiByFireDet', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, delay, enabled, visible, created_at, updated_at) VALUES

(090336, 36, 3, 4, '10 - V03 - VENTILACIÓN', 'Ventilación del tunel en modo extraccion', null, true, 9, false, 'TaskTypeVentilation/ByFireDet/ExtractionByFireDetTask', 600, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.ims_incident_type_task_values
(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)
VALUES
(5, 1, 020136, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 020136, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3, 1, 030136, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 030136, '9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 030336, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 030336, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 040436, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 040436, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 070436, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 070436, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, 020236, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 020236, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(5, 1, 050236, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 050236, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(9, 1, 060336, '["22"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 070336, '["25"]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

