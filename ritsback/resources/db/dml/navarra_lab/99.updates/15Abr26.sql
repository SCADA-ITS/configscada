/*BEGIN;

-- Añade aquí todos los incident_type_id afectados por este mismo patrón
WITH incident_ids(incident_type_id) AS (
    VALUES
        (403),
        (404),
        (405),
        (406),
        (407),
        (409),
        (410),
        (504),
        (601),
        (604)
),
task_ids AS (
    SELECT
        incident_type_id,
        CAST('0101'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0101,
        CAST('0201'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0201,
        CAST('0301'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0301,
        CAST('0401'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0401,
        CAST('0501'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0501,
        CAST('0801'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0801,
        CAST('0901'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0901,
        CAST('0103'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0103,
        CAST('0203'   || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_0203,
        CAST('010601' || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_010601,
        CAST('010701' || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_010701,
        CAST('020601' || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_020601,
        CAST('020701' || LPAD(incident_type_id::text, 4, '0') AS bigint) AS id_020701
    FROM incident_ids
)

-- =========================================================
-- 1) TASKS: actualizar las 3 tareas que se conservan
-- =========================================================

UPDATE conf.ims_incident_type_tasks t
SET
    incident_state_id       = 1,
    task_type_id            = 10,
    alias                   = 'ENVIAR TELEGRAM PUBLICO',
    description             = 'Notificación usuarios por Telegram',
    operation_description   = 'Enviar información a canal público de Telegram de la incidencia para informar a los ciudadanos.',
    mandatory               = false,
    position                = 1,
    auto                    = false,
    on_change               = 'SendToTelegramNotificationPublic',
    rerun                   = true,
    road_impact_id          = NULL,
    enabled                 = true,
    visible                 = true,
    updated_at              = CURRENT_TIMESTAMP
FROM task_ids x
WHERE t.incident_type_task_id = x.id_0101;

UPDATE conf.ims_incident_type_tasks t
SET
    incident_state_id       = 1,
    task_type_id            = 10,
    alias                   = 'ENVIAR TELEGRAM INTERNO',
    description             = 'Notificación interna por Telegram',
    operation_description   = 'Enviar información a canal interno de Telegram de la incidencia.',
    mandatory               = false,
    position                = 2,
    auto                    = false,
    on_change               = 'SendToTelegramNotificationPrivate',
    rerun                   = true,
    road_impact_id          = NULL,
    enabled                 = true,
    visible                 = true,
    updated_at              = CURRENT_TIMESTAMP
FROM task_ids x
WHERE t.incident_type_task_id = x.id_0201;

UPDATE conf.ims_incident_type_tasks t
SET
    incident_state_id       = 1,
    task_type_id            = 8,
    alias                   = 'ENVIAR CORREO',
    description             = 'Enviar correo de aviso',
    operation_description   = 'Enviar correo eléctronico a personal para informar de ocurencia de incidencia',
    mandatory               = false,
    position                = 3,
    auto                    = false,
    on_change               = 'SendMailTask',
    rerun                   = true,
    road_impact_id          = NULL,
    enabled                 = true,
    visible                 = true,
    updated_at              = CURRENT_TIMESTAMP
FROM task_ids x
WHERE t.incident_type_task_id = x.id_0301;

-- =========================================================
-- 2) TASKS: borrar tareas que desaparecen del diff
-- =========================================================

DELETE FROM conf.ims_incident_type_tasks t
USING task_ids x
WHERE t.incident_type_task_id IN (
    x.id_0401,
    x.id_0501,
    x.id_010601,
    x.id_010701,
    x.id_020601,
    x.id_020701,
    x.id_0801,
    x.id_0901,
    x.id_0103,
    x.id_0203
);

-- =========================================================
-- 3) TASK_VALUES: limpiar todas las filas afectadas
--    (incluye antiguas y nuevas para reinsertarlas limpias)
-- =========================================================

DELETE FROM conf.ims_incident_type_task_values tv
USING task_ids x
WHERE tv.incident_type_task_id IN (
    x.id_0101,
    x.id_0201,
    x.id_0301,
    x.id_0401,
    x.id_010601,
    x.id_010701,
    x.id_020601,
    x.id_020701,
    x.id_0801,
    x.id_0901,
    x.id_0103,
    x.id_0203
);

-- =========================================================
-- 4) TASK_VALUES: insertar los nuevos valores
-- =========================================================

INSERT INTO conf.ims_incident_type_task_values
(
    task_type_id,
    task_type_param_id,
    incident_type_task_id,
    value,
    enabled,
    visible,
    created_at,
    updated_at
)
SELECT
    10,
    1,
    x.id_0101,
    E'Inicio de evento de atletismo: Carrera\n@alias\n@tipo\n@localizacion\n@afeccion\n@fecha',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM task_ids x

UNION ALL

SELECT
    10,
    1,
    x.id_0201,
    E'@alias\n@tipo\n@localizacion\n@afeccion\n@fecha',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM task_ids x

UNION ALL

SELECT
    8,
    1,
    x.id_0301,
    'monica.labat.villalba@navarra.es,alberto.egea.corella@navarra.es',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM task_ids x

UNION ALL

SELECT
    8,
    4,
    x.id_0301,
    'Aviso de accidente',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM task_ids x

UNION ALL

SELECT
    8,
    5,
    x.id_0301,
    '¡ACCIDENTE CON HERIDOS GRAVES Y FALLECIDOS!',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM task_ids x;

COMMIT;*/
