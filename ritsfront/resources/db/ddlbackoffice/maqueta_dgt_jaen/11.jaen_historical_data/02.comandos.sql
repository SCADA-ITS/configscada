-- PMV
CREATE OR REPLACE VIEW historical_data.vw_pmv_completa (
    id,
    audit_log_id,
    time_stamp,
    user_name,
    translation,
    element,
    comment,
    zona_grafico_1,
    zona_grafico_1_label,
    mensaje,
    alternancia_zona_grafico_1,
    alternancia_zona_grafico_1_label,
    zona_grafico_2,
    zona_grafico_2_label,
    mensaje_alternancia,
    alternancia_zona_grafico_2,
    alternancia_zona_grafico_2_label
) AS
WITH comando_normalizado AS (
    SELECT
        ac.audit_log_id,
        ac.time_stamp,
        ac.user_name,
        ac.command_values,
        CASE
            WHEN jsonb_typeof(ac.command_values::jsonb) = 'string' THEN
                (ac.command_values::jsonb)::text::jsonb
            WHEN jsonb_typeof(ac.command_values::jsonb) = 'array'
                 AND jsonb_typeof((ac.command_values::jsonb)->0->'value') = 'string'
            THEN ((ac.command_values::jsonb)->0->>'value')::jsonb
            WHEN jsonb_typeof(ac.command_values::jsonb) = 'array' THEN
                (ac.command_values::jsonb)
            ELSE NULL
        END AS comando_json
    FROM hist.audit_commands ac
),
icon_labels AS (
    SELECT value, MAX(label_value) AS label_value
    FROM ui.grid_field_icons
    GROUP BY value
)
SELECT
    ac.audit_log_id::bigint AS id,
    ac.audit_log_id::bigint AS audit_log_id,
    ac.time_stamp::timestamp without time zone AS time_stamp,

    -- user_name igual que antes
    COALESCE(
        CASE
            WHEN ac.comment ~* '^Plan:\d+$' THEN (
                SELECT ap.user_name
                FROM hist.audit_plans ap
                INNER JOIN conf.plans p ON p.alias = ap.plan
                WHERE p.plan_id = CAST(regexp_replace(ac.comment, '\D', '', 'g') AS INTEGER)
                  AND ap.log_subtype_id = 3
                LIMIT 1
            )
            ELSE ac.user_name
        END,
        'Usuario'
    )::varchar AS user_name,

    il.translation::varchar AS translation,
    ac.element::varchar AS element,

    -- comment corregido para Plan y Incidencia
    COALESCE(
        CASE
            WHEN ac.comment ~* '^Plan:\d+$' THEN
                'Plan: ' || pl.alias
            WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN
                'Incidencia: ' || COALESCE(it.description, '')
            ELSE COALESCE(ac.comment, 'Usuario')
        END,
        'Usuario'
    )::varchar AS comment,

    -- zona_grafico_1
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value','0'), ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '1'
    ), '')::varchar AS zona_grafico_1,

    -- label zona_grafico_1
    COALESCE((
        SELECT mil.translation
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'value'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '1'
        LIMIT 1
    ), '')::varchar AS zona_grafico_1_label,

    -- mensaje zona 2
    COALESCE((
        SELECT string_agg(t->>'value', ' / ' ORDER BY (t->>'id')::int)
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'texts','[]'::jsonb)) t
        WHERE zone->>'zone' = '2'
    ), '')::varchar AS mensaje,

    -- alternancia zona 1
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance','0'), ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '1'
    ), '')::varchar AS alternancia_zona_grafico_1,

    -- label alternancia zona 1
    COALESCE((
        SELECT mil.translation
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'alternance'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '1'
        LIMIT 1
    ), '')::varchar AS alternancia_zona_grafico_1_label,

    -- zona_grafico_2
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value','0'), ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '3'
    ), '')::varchar AS zona_grafico_2,

    -- label zona_grafico_2
    COALESCE((
        SELECT mil.translation
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'value'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '3'
        LIMIT 1
    ), '')::varchar AS zona_grafico_2_label,

    -- mensaje alternancia
    COALESCE((
        SELECT string_agg(NULLIF(t->>'alternance',''), E' / ' ORDER BY (t->>'id')::int)
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'texts','[]'::jsonb)) t
        WHERE zone->>'zone' = '2' AND NULLIF(t->>'alternance','') IS NOT NULL
    ), '')::varchar AS mensaje_alternancia,

    -- alternancia zona 2
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance','0'), ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '3'
    ), '')::varchar AS alternancia_zona_grafico_2,

    -- label alternancia zona 2
    COALESCE((
        SELECT mil.translation
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'alternance'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '3'
        LIMIT 1
    ), '')::varchar AS alternancia_zona_grafico_2_label

FROM hist.audit_commands ac
LEFT JOIN comando_normalizado cn ON ac.audit_log_id = cn.audit_log_id

-- Plan
LEFT JOIN LATERAL (
    SELECT alias
    FROM conf.plans
    WHERE plan_id = CAST(regexp_replace(ac.comment, '\D', '', 'g') AS INTEGER)
    LIMIT 1
) pl ON ac.comment ~* '^Plan:\d+$'

-- Incidencia
LEFT JOIN LATERAL (
    SELECT cit.description
    FROM hist.ims_incident_reports hir
    JOIN conf.ims_incident_types cit 
      ON cit.incident_type_id = hir.incident_type_id
    WHERE hir.incident_report_id = CAST(regexp_replace(ac.comment, '\D', '', 'g') AS INTEGER)
      AND ac.comment ~* '^ImsIncidentReport:\d+$'
    LIMIT 1
) it ON TRUE

INNER JOIN master.i18n_labels il ON il.label = ac.command_type
INNER JOIN conf.elements el ON el.alias = ac.element
WHERE il.locale_code = 'es_ES'
  AND el.element_type_id = 71
  AND ac.log_subtype_id = 1
  AND ac.time_stamp >= now() - INTERVAL '1 month'
  AND ac.time_stamp <= now()
ORDER BY ac.time_stamp DESC;










--SEM
CREATE OR REPLACE VIEW historical_data.view_barriers_commands AS
SELECT
    -- Campo requerido por el SCADA
    ac.audit_log_id::bigint AS id,

    -- Campos originales
    ac.audit_log_id,
    ac.time_stamp,
    ac.user_name,
    il.translation,
    ac.element,
    COALESCE(
        CASE
            WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
            WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
            ELSE ac.comment
        END,
        'Usuario'
    ) AS comment
FROM hist.audit_commands ac
LEFT JOIN LATERAL (
    SELECT alias
    FROM conf.plans
    WHERE ac.comment ~* '^Plan:\d+$'
      AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
    LIMIT 1
) AS pl ON TRUE
LEFT JOIN LATERAL (
    SELECT cit.description
    FROM hist.ims_incident_reports hir
    JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
    WHERE ac.comment ~* '^ImsIncidentReport:\d+$'
      AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '', 'i') AS INTEGER)
    LIMIT 1
) AS it ON TRUE
INNER JOIN master.i18n_labels il ON il.label = ac.command_type
INNER JOIN conf.elements el ON el.alias = ac.element
WHERE il.locale_code = 'es_ES'
  AND el.element_type_id = 13
  AND ac.log_subtype_id = 1
  AND ac.time_stamp >= NOW() - INTERVAL '1 month'
  AND ac.time_stamp <= NOW()
ORDER BY ac.time_stamp DESC;


--PRESURIZATION
CREATE OR REPLACE VIEW historical_data.presurization AS
SELECT
    -- Campo requerido por el SCADA
    ac.audit_log_id::bigint AS id,

    -- Campos originales
    ac.audit_log_id,
    ac.time_stamp,
    ac.user_name,
    il.translation,
    ac.element,
    COALESCE(
        CASE
            WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
            WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
            ELSE ac.comment
        END,
        'Usuario'
    ) AS comment
FROM hist.audit_commands ac
LEFT JOIN LATERAL (
    SELECT alias
    FROM conf.plans
    WHERE ac.comment ~* '^Plan:\d+$'
      AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
    LIMIT 1
) AS pl ON TRUE
LEFT JOIN LATERAL (
    SELECT cit.description
    FROM hist.ims_incident_reports hir
    JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
    WHERE ac.comment ~* '^ImsIncidentReport:\d+$'
      AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '', 'i') AS INTEGER)
    LIMIT 1
) AS it ON TRUE
INNER JOIN master.i18n_labels il ON il.label = ac.command_type
INNER JOIN conf.elements el ON el.alias = ac.element
WHERE il.locale_code = 'es_ES'
  AND el.element_type_id = 32
  AND ac.log_subtype_id = 1
  AND ac.time_stamp >= NOW() - INTERVAL '1 month'
  AND ac.time_stamp <= NOW()
ORDER BY ac.time_stamp DESC;


--ILUMINATION
CREATE OR REPLACE VIEW historical_data.lightning_circuit AS
WITH circuit_data AS (
    SELECT
        -- Campo requerido por el SCADA
        ac.audit_log_id::bigint AS id,

        -- Campos originales
        ac.audit_log_id,
        ac.time_stamp,
        ac.user_name,
        il.translation,
        CASE
            WHEN el84.alias IS NOT NULL THEN el84.alias
            ELSE el.alias
        END AS element,
        COALESCE(
            CASE
                WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
                WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
                ELSE ac.comment
            END,
            'Usuario'
        ) AS comment,
        modo.regimen::varchar AS regimen
    FROM hist.audit_commands ac

    LEFT JOIN LATERAL (
        SELECT e.alias
        FROM conf.elements e
        WHERE EXISTS (
            SELECT 1
            FROM regexp_matches(ac.command_values, 'Element:84:(\\d+)', 'g') AS m(id)
            WHERE
                e.element_type_id = 84
                AND e.element_id = CAST(m[1] AS INTEGER)
            LIMIT 1
        )
        LIMIT 1
    ) AS el84 ON TRUE

    LEFT JOIN LATERAL (
        SELECT alias
        FROM conf.plans
        WHERE ac.comment ~* '^Plan:\\d+$'
        AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
        LIMIT 1
    ) AS pl ON TRUE

    LEFT JOIN LATERAL (
        SELECT cit.description
        FROM hist.ims_incident_reports hir
        JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
        WHERE ac.comment ~* '^ImsIncidentReport:\\d+$'
        AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '', 'i') AS INTEGER)
        LIMIT 1
    ) AS it ON TRUE

    -- Lógica combinada para regimen y modo de operación
    LEFT JOIN LATERAL (
        SELECT
            CASE
                WHEN e.element_type_id = 2000 AND ac.command_type ~* 'ILLUMINATION' AND ac.command_type ~* 'MODE' THEN
                    CASE
                        WHEN ac.command_values LIKE '%0%' THEN 'Automático'
                        WHEN ac.command_values LIKE '%1%' THEN 'Manual'
                        ELSE ''
                    END
                WHEN ac.command_values ~* 'Element:84:\\d+' THEN ''
                WHEN ac.command_values ~* '^\\[.*\\]$' THEN
                    CASE
                        WHEN ac.command_values LIKE '%2%' THEN 'Soleado'
                        WHEN ac.command_values LIKE '%3%' THEN 'Nublado'
                        WHEN ac.command_values LIKE '%4%' THEN 'Crepuscular'
                        WHEN ac.command_values LIKE '%5%' THEN 'Nocturno'
                        WHEN ac.command_values LIKE '%6%' THEN 'Exterior/Nocturno'
                        ELSE ''
                    END
                WHEN ac.command_values LIKE '%2%' THEN 'Soleado'
                WHEN ac.command_values LIKE '%3%' THEN 'Nublado'
                WHEN ac.command_values LIKE '%4%' THEN 'Crepuscular'
                WHEN ac.command_values LIKE '%5%' THEN 'Nocturno'
                WHEN ac.command_values LIKE '%6%' THEN 'Exterior/Nocturno'
                ELSE ''
            END AS regimen
        FROM conf.elements e
        WHERE e.alias = ac.element
        LIMIT 1
    ) AS modo ON TRUE

    INNER JOIN master.i18n_labels il ON il.label = ac.command_type
    INNER JOIN conf.elements el ON el.alias = ac.element

    WHERE il.locale_code = 'es_ES'
      AND (
        el.element_type_id = 84
        OR (el.element_type_id = 2000 AND ac.command_type ~* '(ILLUMINATION|CIRCUIT)')
      )
      AND ac.log_subtype_id = 1 
      AND ac.time_stamp >= now() - INTERVAL '1 month'
      AND ac.time_stamp <= now()
)
SELECT *
FROM circuit_data
ORDER BY time_stamp DESC;


--BARRIER
CREATE OR REPLACE VIEW historical_data.barrier AS
SELECT 
    -- Campo requerido por el SCADA
    ac.audit_log_id::bigint AS id,

    -- Campos originales
    ac.audit_log_id, 
    ac.time_stamp, 
    ac.user_name, 
    il.translation, 
    ac.element, 
    COALESCE(
        CASE
            WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
            WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
            ELSE ac.comment
        END,
        'Usuario'
    ) AS comment
FROM 
    hist.audit_commands ac
INNER JOIN master.i18n_labels il 
    ON il.label = ac.command_type
INNER JOIN conf.elements el 
    ON el.alias = ac.element
LEFT JOIN LATERAL (
    SELECT alias
    FROM conf.plans
    WHERE ac.comment ~* '^Plan:\d+$'
      AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
    LIMIT 1
) AS pl ON TRUE
LEFT JOIN LATERAL (
    SELECT cit.description
    FROM hist.ims_incident_reports hir
    JOIN conf.ims_incident_types cit 
        ON cit.incident_type_id = hir.incident_type_id
    WHERE ac.comment ~* '^ImsIncidentReport:\d+$'
      AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '', 'i') AS INTEGER)
    LIMIT 1
) AS it ON TRUE
WHERE 
    il.locale_code = 'es_ES'
    AND el.element_type_id = 8 
    AND ac.log_subtype_id = 1 
    AND ac.time_stamp >= NOW() - INTERVAL '1 month'
    AND ac.time_stamp <= NOW()
ORDER BY 
    ac.time_stamp DESC;


--ventilation
CREATE OR REPLACE VIEW historical_data.ventilation AS
WITH ventilation_data AS (
    SELECT
        -- Campo requerido por el SCADA
        ac.audit_log_id::bigint AS id,

        -- Campos originales
        ac.audit_log_id,
        ac.time_stamp,
        ac.user_name,
        il.translation,
        -- Alias del elemento
        CASE
            WHEN el9.alias IS NOT NULL THEN el9.alias
            ELSE el.alias
        END AS element,
        COALESCE(
            CASE
                WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
                WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
                ELSE ac.comment
            END,
            'Usuario'
        ) AS comment,
        modo.regimen::varchar AS regimen
    FROM hist.audit_commands ac

    LEFT JOIN LATERAL (
        -- Alias del Element:9:<id> si existe
        SELECT e.alias
        FROM conf.elements e
        WHERE EXISTS (
            SELECT 1
            FROM regexp_matches(ac.command_values, 'Element:9:(\d+)', 'g') AS m(id)
            WHERE
                e.element_type_id = 9
                AND e.element_id = CAST(m[1] AS INTEGER)
            LIMIT 1
        )
        LIMIT 1
    ) AS el9 ON TRUE

    LEFT JOIN LATERAL (
        SELECT alias
        FROM conf.plans
        WHERE ac.comment ~* '^Plan:\d+$'
        AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
        LIMIT 1
    ) AS pl ON TRUE

    LEFT JOIN LATERAL (
        SELECT cit.description
        FROM hist.ims_incident_reports hir
        JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
        WHERE ac.comment ~* '^ImsIncidentReport:\d+$'
        AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '', 'i') AS INTEGER)
        LIMIT 1
    ) AS it ON TRUE

    -- Columna "regimen", que muestra modo o régimen según el tipo de comando
    LEFT JOIN LATERAL (
        SELECT
            CASE
                WHEN e.element_type_id = 2000 AND ac.command_type ~* 'VENTILATION' AND ac.command_type ~* 'MODE' THEN
                    CASE
                        WHEN ac.command_values LIKE '%0%' THEN 'Automático'
                        WHEN ac.command_values LIKE '%1%' THEN 'Manual'
                        WHEN ac.command_values LIKE '%2%' THEN 'Mantenimiento'
                        WHEN ac.command_values LIKE '%3%' THEN 'Incendio'
                        ELSE ''
                    END
                WHEN ac.command_values ~* 'Element:9:\d+' THEN ''
                WHEN ac.command_values ~* '^\[.*\]$' THEN
                    CASE
                        WHEN ac.command_values LIKE '%1%' THEN 'Normal'
                        WHEN ac.command_values LIKE '%2%' THEN 'Alto'
                        WHEN ac.command_values LIKE '%3%' THEN 'Alto-Alto'
                        WHEN ac.command_values LIKE '%4%' THEN 'Máximo'
                        ELSE ''
                    END
                WHEN ac.command_values LIKE '%1%' THEN 'Normal'
                WHEN ac.command_values LIKE '%2%' THEN 'Alto'
                WHEN ac.command_values LIKE '%3%' THEN 'Alto-Alto'
                WHEN ac.command_values LIKE '%4%' THEN 'Máximo'
                ELSE ''
            END AS regimen
        FROM conf.elements e
        WHERE e.alias = ac.element
        LIMIT 1
    ) AS modo ON TRUE

    INNER JOIN master.i18n_labels il ON il.label = ac.command_type
    INNER JOIN conf.elements el ON el.alias = ac.element

    WHERE il.locale_code = 'es_ES'
      AND (
        el.element_type_id = 9
        OR (el.element_type_id = 2000 AND ac.command_type ~* '(VENTILATION|FANS)')
      )
      AND ac.log_subtype_id = 1 
      AND ac.time_stamp >= now() - INTERVAL '1 month'
      AND ac.time_stamp <= now()
)
SELECT *
FROM ventilation_data
ORDER BY time_stamp DESC;

