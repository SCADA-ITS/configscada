-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, operation_description, mandatory, position, auto, on_change, road_impact_id, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
(0101201, 201, null, 7, 'DETERMINAR CAUSAS', 'Revisar causas del incidente', 'Revisar si las causas del incidente con la visualizacion de las cámaras', true, 1, true, null, null,true,true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0102201, 201, 1, 1, 'SEÑALIZACIÓN', 'Alarma de co', 'Señalización de precaución en el túnel', true, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0202201, 201, 1, 6, 'COMPROBACION DE ALARMA', 'Revisar alarma de CO', 'Comprobación de la autenticidad de la alarma', true, 2, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0302201, 201, 1, 6, 'AVISO', 'Aviso a jefe de emergencias', 'Aviso al jefe de emergencias para que revise el equipo en caso de falsa alarma', false, 3, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0402201, 201, 1, 6, 'COMPROBACION DE SISTEMA DE VENTILACION', 'Comprobar sistema de ventilación', 'Comprobación del sistema de ventilación para determinar su buen funcionamiento', true, 4, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0502201, 201, 1, 6, 'COMPROBACION DE NIVELES', 'Revisar niveles de CO', 'Revision de niveles de concentración de co, para determinar si descienden', false, 5, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(0203201, 201, 3, 1, 'SEÑALIZACIÓN', 'Túnel con normalidad', 'Señalización de normalidad en el túnel', true, 1, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



