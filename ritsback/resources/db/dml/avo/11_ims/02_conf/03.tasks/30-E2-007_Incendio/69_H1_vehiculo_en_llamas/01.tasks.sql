-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = AL CANCELAR
(01010699, 69, 101, 1, 'ENVIAR CANCELACION', 'Enviar cancelación a sistema de control', true, 1, true, 'SendCancel', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = AL SALTAR LA INCIDENCIA - ANTES DE CONFIRMAR - TAREAS GENERALES
(00010699, 69, null, 7, 'CCTV', 'Enclavamiento CCTV Zona afectada', true, 1, true, 'LockCamFireZone', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00020699, 69, null, 1, 'ENVIAR FLUIDO', 'Indicar tráfico fluido', false, 2, false, 'SendFluid', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00030699, 69, null, 1, 'ENVIAR CONGESTIONADO', 'Indicar tráfico congestionado', false, 3, false, 'SendCongested', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(00040699, 69, null, 1, 'FINALIZAR INCENDIO', 'Finalizar ventilación al darse por finalizado el incendio', false, 4, false, 'SendFinished', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  
 --ETAPA = INICIAL 
(01010696, 69, 1, 1, 'SEÑALIZACION', 'Actuación plan señalización de incendio', true, 1, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020699, 69, 1, 1, 'MEGAFONIA', 'Activacion de megafonía', false, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030699, 69, 1, 1, 'BARRERA', 'Bajada de barrera', false, 3, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01040699, 69, 1, 1, 'ENVIAR CONFIRMACION', 'Enviar confirmación a sistema de control', true, 4, true, 'SendConfirm', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--ETAPA = EN MARCHA   
(02010699, 69, 2, 6, 'SEGUIMIENTO', 'Seguimiento del vehiculo por CCTV', false, 1, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020699, 69, 2, 6, 'ESCALAMIENTO', 'Escalamiento al jefe de emergencia interno de ser necesario', false, 2, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030699, 69, 2, 6, 'ALERTA SVI', 'Alerta a móviles de SVI', false, 3, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040699, 69, 2, 6, 'DESPACHO SVI', 'Despacho móviles SVI', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050699, 69, 2, 6, 'COORDINACIÓN', 'Aviso y coordinación con CENCO, bomberos y SAMU', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060699, 69, 2, 6, 'REGISTRAR INCIDENTE', 'Completar registro de incidente', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02070699, 69, 2, 6, 'COMPROBAR VIDEO', 'Revisar existencia de respaldos de imagen', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--ETAPA = RESTITUCION      
(03010690, 69, 3, 1, 'RESTITUCIÓN SEÑALIZACION', 'Restitución de señalizacióna a estado normalizado', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020699, 69, 3, 1, 'RESTITUCIÓN MEGAFONÍA', 'Desactivación de megafonía', true, 2, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030699, 69, 3, 1, 'RESTITUCIÓN BARRERA', 'Subir barreras', true, 3, false, 'SendSignallingToSpath', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03040699, 69, 3, 7, 'CCTV', 'Activación de monitorización automática de cámaras', false, 4, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03050699, 69, 3, 6, 'AVISAR CIERRE INCIDENTE A SVI', 'Aviso de termino de incidente a móviles de SVI', false, 5, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03060699, 69, 3, 6, 'AVISAR CIERRE INCIDENTE A VIAL', 'Aviso de termino de incidente a móviles de Asistencia Vial', false, 6, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03070699, 69, 3, 6, 'AVISAR CIERRE INCIDENTE A CENCO', 'Aviso de termino de incidente a CENCO', false, 7, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03080699, 69, 3, 6, 'AVISAR CIERRE INCIDENTE A UOCT', 'Aviso de termino de incidente a UOCT', false, 8, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03090699, 69, 3, 6, 'AVISAR CIERRE INCIDENTE A IF', 'Aviso de termino de incidente a Inspector Fiscal', false, 9, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03100699, 69, 3, 6, 'AVISAR CIERRE INCIDENTE A GERENCIA', 'Aviso de termino de incidente a gerencia y encargado', false, 10, false, null, false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

--ETAPA = FINALIZAR INCIDENCIA  
(100010699, 69, 100, 1, 'FINALIZAR INCENDIO', 'Finalizar ventilación al darse por finalizado el incendio', true, 1, true, 'SendFinished', false, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
