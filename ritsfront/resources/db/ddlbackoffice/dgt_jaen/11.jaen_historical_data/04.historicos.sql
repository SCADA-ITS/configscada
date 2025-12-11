--HISTORICOS
--BARRERAS
CREATE OR REPLACE VIEW historical_data.barreras AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY c.timestamp_at DESC) AS id,
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    CASE 
        WHEN c.bar_state = 1 THEN 'Abierta'
        WHEN c.bar_state = 2 THEN 'Cerrada'
        ELSE 'Desconocido'
    END::varchar AS estado_barrera  
FROM 
    hist.barrier c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 8
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--SEMÁFOROS
CREATE OR REPLACE VIEW historical_data.semaforos AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY c.timestamp_at DESC) AS id,
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    CASE 
        WHEN c.light = 0 THEN 'Ámbar intermitente'
        WHEN c.light = 1 THEN 'Rojo'
        WHEN c.light = 2 THEN 'Ámbar fijo'
        WHEN c.light = 4 THEN 'Verde'
        WHEN c.light = 7 THEN 'Apagado'
        ELSE 'Desconocido'
    END::varchar AS estado_semaforo
FROM 
    hist.sem c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 13
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;
    
--PRESURIZACIÓN
CREATE OR REPLACE VIEW historical_data.pres AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY c.timestamp_at DESC) AS id,
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    CASE 
        WHEN c.fan_low_speed_state = true AND c.fan_high_speed_state = false THEN 'Lenta'
        WHEN c.fan_low_speed_state = false AND c.fan_high_speed_state = true THEN 'Rápida'
        ELSE 'Desconocido'
    END::varchar AS fan_speed,
    CASE 
        WHEN c.grid_gate_clousure_state = true THEN 'Abierta'
        WHEN c.grid_gate_clousure_state = false THEN 'Cerrada'
        ELSE 'Desconocido'
    END::varchar AS grid_gate_state
FROM 
    hist.pressurization c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 32
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--PANELES
CREATE OR REPLACE VIEW historical_data.pmv AS
WITH comando_normalizado_pmv AS (
    SELECT
        p.f_element_id,
        p.timestamp_at,
        p.data_json,
        CASE
            WHEN jsonb_typeof(p.data_json::jsonb) = 'string' THEN
                (p.data_json::jsonb)::text::jsonb
            WHEN jsonb_typeof(p.data_json::jsonb) = 'array'
                AND jsonb_typeof((p.data_json::jsonb)->0->'value') = 'string'
            THEN ((p.data_json::jsonb)->0->>'value')::jsonb
            WHEN jsonb_typeof(p.data_json::jsonb) = 'array' THEN
                (p.data_json::jsonb)
            ELSE NULL
        END AS comando_json
    FROM hist.pmv p
    WHERE p.timestamp_at >= now() - interval '1 month'
      AND p.timestamp_at <= now()
),
-- tabla auxiliar para asegurar una sola label por value
icon_labels AS (
    SELECT value, MAX(label_value) AS label_value
    FROM ui.grid_field_icons
    GROUP BY value
)
SELECT
    CAST(row_number() OVER (ORDER BY cn.timestamp_at, cn.f_element_id) AS bigint) AS id,  -- ID numérico único
    ev.value::varchar AS equipo,
    cn.timestamp_at,

    -- Zona gráfica 1
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value','0')::varchar, ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '1'
    ), '')::varchar AS zona_grafico_1,

    -- Label zona gráfica 1
    COALESCE((
        SELECT COALESCE(mil.translation, '')::varchar
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'value'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '1'
        LIMIT 1
    ), '')::varchar AS zona_grafico_1_label,

    -- Mensaje
    COALESCE((
        SELECT string_agg(t->>'value'::varchar, ' / ' ORDER BY (t->>'id')::int)
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'texts','[]'::jsonb)) t
        WHERE zone->>'zone' = '2'
    ), '')::varchar AS mensaje,

    -- Zona gráfica 2
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value','0')::varchar, ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '3'
    ), '')::varchar AS zona_grafico_2,

    -- Label zona gráfica 2
    COALESCE((
        SELECT COALESCE(mil.translation, '')::varchar
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'value'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '3'
        LIMIT 1
    ), '')::varchar AS zona_grafico_2_label,

    -- Alternancia zona gráfica 1
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance','0')::varchar, ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '1'
    ), '')::varchar AS alternancia_zona_grafico_1,

    -- Label alternancia zona gráfica 1
    COALESCE((
        SELECT COALESCE(mil.translation, '')::varchar
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'alternance'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '1'
        LIMIT 1
    ), '')::varchar AS alternancia_zona_grafico_1_label,

    -- Alternancia del mensaje (original, sin label)
    COALESCE((
        SELECT string_agg(NULLIF(t->>'alternance','')::varchar, E' / ' ORDER BY (t->>'id')::int)
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'texts','[]'::jsonb)) t
        WHERE zone->>'zone' = '2' AND NULLIF(t->>'alternance','') IS NOT NULL
    ), '')::varchar AS mensaje_alternancia,

    -- Alternancia zona gráfica 2
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance','0')::varchar, ' '))
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        WHERE zone->>'zone' = '3'
    ), '')::varchar AS alternancia_zona_grafico_2,

    -- Label alternancia zona gráfica 2
    COALESCE((
        SELECT COALESCE(mil.translation, '')::varchar
        FROM jsonb_array_elements(coalesce(cn.comando_json, '[]'::jsonb)) zone
        CROSS JOIN LATERAL jsonb_array_elements(coalesce(zone->'graphics','[]'::jsonb)) g
        JOIN icon_labels ic ON ic.value = g->>'alternance'
        JOIN master.i18n_labels mil ON mil.label = ic.label_value AND mil.locale_code = 'es_ES'
        WHERE zone->>'zone' = '3'
        LIMIT 1
    ), '')::varchar AS alternancia_zona_grafico_2_label

FROM comando_normalizado_pmv cn
INNER JOIN conf.element_values ev 
    ON ev.element_id = cn.f_element_id
WHERE ev.element_type_id = 71
  AND ev.element_type_param_id = 1003
ORDER BY cn.timestamp_at DESC;
