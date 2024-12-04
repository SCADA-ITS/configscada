DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') AND
  	  EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'hist' AND tablename = 'ext_entities')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
    CREATE OR REPLACE VIEW reporting_bo.c4_ext_entities_with_values AS
    SELECT 
        e.uid as id,
        pv.param_11 AS categoria,
        e.alias AS tipo,
        e.description AS titulo,
        pv.param_6 AS afeccion,
        pv.param_2 AS carretera,
        pv.param_10 AS localizacion,
    	(TO_TIMESTAMP(pv.param_9, ''YYYY-MM-DD HH24:MI:SS.MS'') AT TIME ZONE ''Europe/Madrid'')::timestamptz AS fecha,
    	(TO_TIMESTAMP(pv.param_7, ''YYYY-MM-DD HH24:MI:SS.MS'') AT TIME ZONE ''Europe/Madrid'')::timestamptz AS fecha_actualizacion,
        CASE 
            WHEN e.status = ''CREATED'' THEN ''ACTIVA''
            WHEN e.status = ''DELETED'' THEN ''FINALIZADA''
            WHEN e.status = ''UPDATED'' THEN ''ACTIVA''
            ELSE e.status::varchar
        END AS estado
    FROM 
        (SELECT DISTINCT ON (ext_entity_id) *
         FROM hist.ext_entities
         WHERE ext_entity_type_id IN (1, 2, 3)
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
                ext_entity_type_param_id IN (2, 6, 7, 8, 9, 10, 11)
            ORDER BY 
                ext_entity_id, 
                created_at desc,
                ext_entity_type_param_id$$
        ) AS pv (
            ext_entity_id int8,
            param_2 varchar,
            param_6 varchar,
            param_7 varchar,
            param_8 varchar,
            param_9 varchar,
            param_10 varchar,
            param_11 varchar
        )
    ON 
        e.ext_entity_id = pv.ext_entity_id
    ';
  END IF;
END $do$;
