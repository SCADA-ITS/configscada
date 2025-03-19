-- 
-- Table: conf.ims_incident_type_tasks
--
INSERT INTO conf.ims_incident_type_tasks (incident_type_task_id, incident_type_id, incident_state_id, task_type_id, alias, description, mandatory, position, auto, on_change, rerun, enabled, visible, created_at, updated_at) VALUES
-- Para el incident_type_task_id sigo la siguiente convencion: <orden de la tarea dentro del estado><estado en el que está englobada la tarea><tipo de incidente>
--ETAPA = EN MARCHA
(01010101, 0101, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01010102, 0101, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01020101, 0102, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01020102, 0102, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01030101, 0103, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01030102, 0103, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01040101, 0104, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01040102, 0104, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01050101, 0105, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01050102, 0105, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(01060101, 0106, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(01060102, 0106, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02010101, 0201, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02010102, 0201, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02020101, 0202, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02020102, 0202, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02030101, 0203, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02030102, 0203, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02040101, 0204, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02040102, 0204, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02050101, 0205, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02050102, 0205, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02060101, 0206, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02060102, 0206, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02070101, 0207, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02070102, 0207, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(02080101, 0208, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(02080102, 0208, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(03010101, 0301, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03010102, 0301, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(03020101, 0302, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03020102, 0302, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(03030101, 0303, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(03030102, 0303, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(04010101, 0401, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(04010102, 0401, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(04020101, 0402, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(04020102, 0402, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(04030101, 0403, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(04030102, 0403, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(04040101, 0404, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(04040102, 0404, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(05010101, 0501, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(05010102, 0501, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(05020101, 0502, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(05020102, 0502, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(05030101, 0503, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(05030102, 0503, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(06010101, 0601, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(06010102, 0601, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(06020101, 0602, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(06020102, 0602, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(06030101, 0603, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(06030102, 0603, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(06040101, 0604, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(06040102, 0604, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(06050101, 0605, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(06050102, 0605, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(06060101, 0606, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(06060102, 0606, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(06070101, 0607, 1, 1, 'AVISO A PATRULLERO', 'Aviso a patrullero', false, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(06070102, 0607, 1, 1, 'RELLENAR BITACORA', 'Rellenar bitácora', true, 1, false, null, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

