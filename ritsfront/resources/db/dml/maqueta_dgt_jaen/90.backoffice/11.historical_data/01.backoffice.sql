DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'historical_data') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(11, 'historical_data_backoffice', NULL, NULL, 'jdbc:postgresql://192.168.88.202:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'historical_data',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"showIdForm": false,
    	"showIdTable": false,
    	"showIdSearch": false,
    	"gridFilters": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": true,
	    "refAddButton": false
	}',
	true);
--PMV
CREATE OR REPLACE VIEW historical_data.vw_pmv_completa AS
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
)

SELECT
	ac.audit_log_id,
	ac.time_stamp,
	COALESCE(                                                                                            
		CASE
			WHEN ac.comment ~* '^Plan:\d+$' THEN (
				SELECT ap.user_name
				FROM hist.audit_plans ap
				INNER JOIN conf.plans p ON p.alias = ap.plan
				WHERE p.plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
				AND ap.log_subtype_id = 3
				LIMIT 1
			)
			ELSE ac.user_name
		END,
		'Usuario') AS user_name,
	il.translation,
	ac.element, 
	COALESCE(
		CASE
			WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
			WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
			ELSE ac.comment
		END,
		'Usuario') AS comment,

	-- Zona gráfica 1
	COALESCE((
		SELECT
			TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '1'
	), ' ') AS zona_grafico_1,

	-- Mensaje
	COALESCE((
		SELECT string_agg(t->>'value', ' / ' ORDER BY (t->>'id')::int)
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'texts') t
		WHERE zone->>'zone' = '2'
	), ' ') AS mensaje,

	-- Alternancia zona gráfica 1
	COALESCE((
		SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '1'
	), ' ') AS alternancia_zona_grafico_1,

	-- Zona gráfica 2
	COALESCE((
		SELECT
			TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '3'
	), ' ') AS zona_grafico_2,

	-- Alternancia del mensaje
	COALESCE((
		SELECT string_agg(NULLIF(t->>'alternance', ''), E' / ' ORDER BY (t->>'id')::int)
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'texts') t
		WHERE zone->>'zone' = '2' AND NULLIF(t->>'alternance', '') IS NOT NULL
	), ' ') AS mensaje_alternancia,

	-- Alternancia zona gráfica 2
	COALESCE((
		SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '3'
	), ' ') AS alternancia_zona_grafico_2

FROM hist.audit_commands ac
LEFT JOIN comando_normalizado cn ON ac.audit_log_id = cn.audit_log_id
LEFT JOIN LATERAL (
	SELECT alias
	FROM conf.plans
	WHERE ac.comment ~* '^Plan:\d+$'
		AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
	LIMIT 1
) AS pl ON TRUE
LEFT JOIN LATERAL (
	SELECT description
	FROM hist.ims_incident_reports hir
	JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
	WHERE ac.comment ~* '^ImsIncidentReport:\d+$'
		AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '') AS INTEGER)
	LIMIT 1
) AS it ON TRUE
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
            FROM regexp_matches(ac.command_values, 'Element:84:(\d+)', 'g') AS m(id)
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
                WHEN ac.command_values ~* 'Element:84:\d+' THEN ''
                WHEN ac.command_values ~* '^\[.*\]$' THEN
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
CREATE VIEW historical_data.barrier AS
SELECT 
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

--ANALOGICOS
--co
CREATE VIEW historical_data.co AS
SELECT 
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    c.concentration
FROM 
    hist.co c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 19
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--opac
CREATE VIEW historical_data.opac AS
SELECT 
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    c.concentration
FROM 
    hist.opac c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 21
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

    --LUM
CREATE VIEW historical_data.lum AS
SELECT 
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    c.luminosity_real
FROM 
    hist.lum c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 14
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--ws
CREATE VIEW historical_data.ws AS
SELECT 
    e.alias AS equipo,  
    c.timestamp_at,  
    c.date,  
    c.period,  
    c.air_pressure,
    c.air_temperature,
    c.dew_point_temperature,
    c.relative_humidity,
    c.visibility,
    c.wind_direction,
    c.wind_speed,
    c.wind_type,
    c.precipitation_intensity,  
    c.precipitation_quantity   
FROM 
    hist.ws c
LEFT JOIN conf.elements e 
    ON e.element_id = c.f_element_id
    AND e.element_type_id = 1  
WHERE 
    c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--ANE
CREATE VIEW historical_data.ane AS
SELECT 
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    c.wind_speed
FROM 
    hist.ane c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 18
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--VANE
CREATE VIEW historical_data.vane AS
SELECT 
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    c.wind_speed,
    c.wind_direction
FROM 
    hist.vane c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 66
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--HISTORICOS
--BARRERAS
CREATE VIEW historical_data.barreras AS
SELECT 
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
    ev.element_type_id = 8  -- 
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 month'  -- Intervalo de 1 mes
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--SEMÁFOROS
CREATE VIEW historical_data.semaforos AS
SELECT 
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
CREATE VIEW historical_data.pres AS
SELECT 
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
WITH comando_normalizado AS (
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
)

SELECT
    ev.value::varchar AS equipo,
    cn.timestamp_at,

    -- Zona gráfica 1
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value', '0')::varchar, ' '))
        FROM jsonb_array_elements(cn.comando_json) zone
        CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
        WHERE zone->>'zone' = '1'
    ), ' ')::varchar AS zona_grafico_1,

    -- Mensaje
    COALESCE((
        SELECT string_agg(t->>'value'::varchar, ' / ' ORDER BY (t->>'id')::int)
        FROM jsonb_array_elements(cn.comando_json) zone
        CROSS JOIN LATERAL jsonb_array_elements(zone->'texts') t
        WHERE zone->>'zone' = '2'
    ), ' ')::varchar AS mensaje,

    -- Zona gráfica 2
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value', '0')::varchar, ' '))
        FROM jsonb_array_elements(cn.comando_json) zone
        CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
        WHERE zone->>'zone' = '3'
    ), ' ')::varchar AS zona_grafico_2,

    -- Alternancia zona gráfica 1
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance', '0')::varchar, ' '))
        FROM jsonb_array_elements(cn.comando_json) zone
        CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
        WHERE zone->>'zone' = '1'
    ), ' ')::varchar AS alternancia_zona_grafico_1,

    -- Alternancia del mensaje
    COALESCE((
        SELECT string_agg(NULLIF(t->>'alternance', '')::varchar, E' / ' ORDER BY (t->>'id')::int)
        FROM jsonb_array_elements(cn.comando_json) zone
        CROSS JOIN LATERAL jsonb_array_elements(zone->'texts') t
        WHERE zone->>'zone' = '2' AND NULLIF(t->>'alternance', '') IS NOT NULL
    ), ' ')::varchar AS mensaje_alternancia,

    -- Alternancia zona gráfica 2
    COALESCE((
        SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance', '0')::varchar, ' '))
        FROM jsonb_array_elements(cn.comando_json) zone
        CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
        WHERE zone->>'zone' = '3'
    ), ' ')::varchar AS alternancia_zona_grafico_2

FROM comando_normalizado cn
INNER JOIN conf.element_values ev 
    ON ev.element_id = cn.f_element_id
WHERE 
    ev.element_type_id = 71  -- PMV
    AND ev.element_type_param_id = 1003
ORDER BY cn.timestamp_at DESC;


  	-- historical_data.sg_metadata_tables
  	--
	INSERT INTO historical_data.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(1, 'pmv', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV_DESCRIPTION', NULL, true, true,
		'SELECT * FROM historical_data.vw_pmv_completa', 200, NULL),

	(2, 'sem', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM_DESCRIPTION', NULL, true, true, 
	'SELECT * FROM historical_data.view_barriers_commands', NULL, NULL),


	(3, 'pressurization', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.presurization', NULL, NULL),

	(4, 'lightning_circuit', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.lightning_circuit', NULL, NULL),

	(5, 'barrier', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.barrier', NULL, NULL),

	(6, 'ventilation', 'LBL_BACKOFFICE_SG_METADATA_TABLES_VENTILATION', 'LBL_BACKOFFICE_SG_METADATA_TABLES_VENTILATION', 'LBL_BACKOFFICE_SG_METADATA_TABLES_VENTILATION_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.ventilation', NULL, NULL),

	(7, 'co', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CO', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CO', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CO_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.co', NULL, NULL),

	(8, 'opac', 'LBL_BACKOFFICE_SG_METADATA_TABLES_OPAC', 'LBL_BACKOFFICE_SG_METADATA_TABLES_OPAC', 'LBL_BACKOFFICE_SG_METADATA_TABLES_OPAC_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.opac', NULL, NULL),

    (9, 'lum', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LUM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LUM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LUM_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.lum', NULL, NULL),

    (10, 'ws', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.ws', NULL, NULL),

    (11, 'ane', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.ane', NULL, NULL),

    (12, 'vane', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.vane', NULL, NULL),

    (13, 'barreras', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.barreras', NULL, NULL),

    (14, 'pres', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.pres', NULL, NULL),


    (15, 'paneles', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.pmv', NULL, NULL);



	-- 
  	-- historical_data.sg_metadata_columns
  	--
	INSERT INTO historical_data.sg_metadata_columns (sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	--sem
	(2, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(2, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(2, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(2, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(2, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(2, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--pressurization
	(3, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(3, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(3, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(3, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(3, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(3, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--lightning_circuit
	(4, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(4, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(4, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(4, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(4, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(4, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
	(4, 'regimen', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_REGIMEN', NULL, false, '{"editable": false}'),

	--barrier
	(5, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(5, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(5, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(5, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(5, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(5, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

		--Ventilation
	(6, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(6, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(6, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(6, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(6, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(6, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
	(6, 'regimen', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_REGIMEN', NULL, false, '{"editable": false}'),

    --CO
	(7, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(7, 'concentration', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_CONCENTRATION', NULL, false, '{"editable": false}'),
	(7, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
    --opac
	(8, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(8, 'concentration', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_CONCENTRATION', NULL, false, '{"editable": false}'),
	(8, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
    --LUM
	(9, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(9, 'luminosity_real', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_LUMINOSITY_REAL', NULL, false, '{"editable": false}'),
	(9, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
    --WS
	(10, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(10, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
	(10, 'air_pressure', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_AIR_PRESSURE', NULL, false, '{"editable": false}'),
	(10, 'air_temperature', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_AIR_TEMP', NULL, false, '{"editable": false}'),
	(10, 'dew_point_temperature', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DEW_POINT_TEMP', NULL, false, '{"editable": false}'),
	(10, 'period', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PERIOD', NULL, false, '{"editable": false}'),
	(10, 'relative_humidity', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_RELATIVE_HUMIDITY', NULL, false, '{"editable": false}'),
	(10, 'visibility', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_VISIBILITY', NULL, false, '{"editable": false}'),
	(10, 'wind_direction', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_DIRECTION', NULL, false, '{"editable": false}'),
	(10, 'wind_speed', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_SPEED', NULL, false, '{"editable": false}'),
	(10, 'wind_type', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_TYPE', NULL, false, '{"editable": false}'),
	(10, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE_WS', NULL, false, '{"editable": false}'),
	(10, 'precipitation_type', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PRECIPITATION_TYPE', NULL, false, '{"editable": false}'),
	(10, 'precipitation_quantity', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PRECIPITATION_QUANTITY', NULL, false, '{"editable": false}'),
	(10, 'precipitation_intensity', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PRECIPITATION_INTENSITY', NULL, false, '{"editable": false}'),

    (11, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(11, 'wind_speed', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_SPEED', NULL, false, '{"editable": false}'),
	(11, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

    (12, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(12, 'wind_speed', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_SPEED', NULL, false, '{"editable": false}'),
    (12, 'wind_direction', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_DIRECTION', NULL, false, '{"editable": false}'),
	(12, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}');



  END IF;
  
END $$;
