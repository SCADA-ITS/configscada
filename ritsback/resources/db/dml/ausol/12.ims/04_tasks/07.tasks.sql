
-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
-- P-MET-01
(010107, 7, 1, 2, '1 - I05 - ILUMINACIÓN', 'Verificación de la correcta iluminación en las bocas', 'Verificar el correcto funcionamiento de los niveles de iluminación en la/s boca/s de los túnel con objeto de obtener unas condiciones óptimas de adaptación de la visión desde el interior al exterior y viceversa.', false, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020107, 7, 1, 1, '2 - S04 - SEÑALIZACIÓN', 'Peligro por lluvia intensa, nieve o hielo', 'Existencia de peligro en las bocas del túnel como consecuencia de condiciones climáticas en el exterior que pueden suponer un cambio brusco en las condiciones de conducción.', true, 2, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(030107, 7, 1, 7, 'DESHABILITAR DAI CA', 'Deshabilitar alarmas del DAI sentido Cadíz', null, false, 3, false, 'DeactiveAlarmsDaiCA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(040107, 7, 1, 7, 'DESHABILITAR DAI MA', 'Deshabilitar alarmas del DAI sentido Málaga', null, false, 4, false, 'DeactiveAlarmsDaiMA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(010207, 7, 2, 1, '3 - S17 - SEÑALIZACIÓN', 'Retirar señalización de la emergencia', 'Retirar la señalización activada con el aviso de cualquier situación de emergencia.', true, 1, false, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(020207, 7, 2, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 2, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(011107, 7, 101, 7, 'HABILITAR DAI', 'Habilitar alarmas del DAI', null, true, 1, true, 'ActiveAlarmsDai', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
