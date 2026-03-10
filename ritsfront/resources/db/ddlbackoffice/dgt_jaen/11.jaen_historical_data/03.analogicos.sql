
--ANALOGICOS
--co
CREATE OR REPLACE VIEW historical_data.co AS
WITH co_data AS (
    SELECT 
        row_number() OVER (ORDER BY c.timestamp_at DESC)::bigint AS id,  -- Campo requerido por SCADA
        ev.value::varchar AS equipo,
        c.timestamp_at, 
        c.concentration::varchar AS concentration
    FROM 
        hist.co c
    INNER JOIN conf.element_values ev 
        ON ev.element_id = c.f_element_id
    WHERE 
        ev.element_type_id = 19
        AND ev.element_type_param_id = 1003
        AND c.timestamp_at >= NOW() - INTERVAL '1 year'
        AND c.timestamp_at <= NOW()
)
SELECT *
FROM co_data
ORDER BY timestamp_at DESC;



--opac
CREATE OR REPLACE VIEW historical_data.opac AS
WITH opac_data AS (
    SELECT
        -- Campo requerido por el SCADA: id único
        row_number() OVER (ORDER BY c.timestamp_at DESC)::bigint AS id,

        -- Campos originales
        ev.value::varchar AS equipo,
        c.timestamp_at, 
        c.concentration::varchar AS concentration
    FROM 
        hist.opac c
    INNER JOIN conf.element_values ev 
        ON ev.element_id = c.f_element_id
    WHERE 
        ev.element_type_id = 21
        AND ev.element_type_param_id = 1003
        AND c.timestamp_at >= NOW() - INTERVAL '1 year'
        AND c.timestamp_at <= NOW()
)
SELECT *
FROM opac_data
ORDER BY timestamp_at DESC;


    --LUM
CREATE OR REPLACE VIEW historical_data.lum AS
WITH lum_data AS (
    SELECT
        -- Campo requerido por el SCADA: id único incremental
        row_number() OVER (ORDER BY c.timestamp_at DESC)::bigint AS id,

        -- Campos originales
        ev.value::varchar AS equipo,
        c.timestamp_at, 
        c.luminosity_real::varchar AS luminosity_real
    FROM 
        hist.lum c
    INNER JOIN conf.element_values ev 
        ON ev.element_id = c.f_element_id
    WHERE 
        ev.element_type_id = 14
        AND ev.element_type_param_id = 1003
        AND c.timestamp_at >= NOW() - INTERVAL '1 year'
        AND c.timestamp_at <= NOW()
)
SELECT *
FROM lum_data
ORDER BY timestamp_at DESC;


--ws
CREATE OR REPLACE VIEW historical_data.ws AS
WITH ws_data AS (
    SELECT
        -- Campo requerido por el SCADA: identificador único incremental
        row_number() OVER (ORDER BY c.timestamp_at DESC)::bigint AS id,

        -- Campos originales
        e.alias AS equipo,  
        c.timestamp_at,  
        c.date::varchar AS date,  
        c.period::varchar AS period,  
        c.air_pressure::varchar AS air_pressure,
        c.air_temperature::varchar AS air_temperature,
        c.dew_point_temperature::varchar AS dew_point_temperature,
        c.relative_humidity::varchar AS relative_humidity,
        c.visibility::varchar AS visibility,
        c.wind_direction::varchar AS wind_direction,
        c.wind_speed::varchar AS wind_speed,
        c.wind_type::varchar AS wind_type,
        c.precipitation_intensity::varchar AS precipitation_intensity,  
        c.precipitation_quantity::varchar AS precipitation_quantity
    FROM 
        hist.ws c
    LEFT JOIN conf.elements e 
        ON e.element_id = c.f_element_id
        AND e.element_type_id = 1  
    WHERE 
        c.timestamp_at >= NOW() - INTERVAL '1 year'
        AND c.timestamp_at <= NOW()
)
SELECT *
FROM ws_data
ORDER BY timestamp_at DESC;

--ANE
CREATE OR REPLACE VIEW historical_data.ane AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY c.timestamp_at DESC) AS id,
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    c.wind_speed::varchar AS wind_speed
FROM 
    hist.ane c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 18
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 year'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

--VANE
CREATE OR REPLACE VIEW historical_data.vane AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY c.timestamp_at DESC) AS id,
    ev.value::varchar AS equipo,
    c.timestamp_at, 
    c.wind_speed::varchar AS wind_speed,
    c.wind_direction::varchar AS wind_direction
FROM 
    hist.vane c
INNER JOIN conf.element_values ev 
    ON ev.element_id = c.f_element_id
WHERE 
    ev.element_type_id = 66
    AND ev.element_type_param_id = 1003
    AND c.timestamp_at >= NOW() - INTERVAL '1 year'
    AND c.timestamp_at <= NOW()
ORDER BY 
    c.timestamp_at DESC;

