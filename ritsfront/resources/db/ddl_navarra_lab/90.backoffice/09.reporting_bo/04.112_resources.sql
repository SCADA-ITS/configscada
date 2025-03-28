DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_reporting_bo') AND
  	  EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'hist' AND tablename = 'ext_entities')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
    CREATE OR REPLACE VIEW reporting_bo.e112_resources_ext_entities AS
    SELECT 
        abs(hashtext(e.uid)::int8) AS id,
        e.uid,
   		abs(hashtext(split_part(e.uid, ''##'', 1))::int8) AS id_incidente,
    	split_part(e.uid, ''##'', 2)::varchar(50)  AS nombre,
		(TO_TIMESTAMP(pv.param_1, ''YYYY-MM-DD"T"HH24:MI:SS'') AT TIME ZONE ''Europe/Madrid'')::timestamptz AS fecha_salida,
        e.alias AS agencia,
        e.description AS estacion,
        pv.param_2 as estado,
		(TO_TIMESTAMP(pv.param_3, ''YYYY-MM-DD"T"HH24:MI:SS'') AT TIME ZONE ''Europe/Madrid'')::timestamptz AS fecha_ultimo_estado
    FROM 
        (SELECT DISTINCT ON (ext_entity_id) *
         FROM hist.ext_entities
         WHERE ext_entity_type_id IN (5)
         ORDER BY ext_entity_id, created_at DESC) AS e
    LEFT JOIN 
        crosstab(
            $$SELECT 
                ext_entity_id, 
                ext_entity_type_param_id, 
                value
            FROM 
                hist.ext_entity_values
            WHERE 
                ext_entity_type_param_id IN (1, 2, 3)
            ORDER BY 
                ext_entity_id, 
                created_at desc,
                ext_entity_type_param_id$$
        ) AS pv (
            ext_entity_id int8,
            param_1 varchar,
            param_2 varchar,
            param_3 varchar
        )
    ON 
        e.ext_entity_id = pv.ext_entity_id
    ';
  END IF;
END $do$;
