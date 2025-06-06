DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') AND
  	  EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'hist' AND tablename = 'ext_entities')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
    CREATE OR REPLACE VIEW reporting_bo.e112_ext_entities_with_values AS
    SELECT 
   		abs(hashtext(e.uid)::int8) AS id,
        e.uid,
		CASE 
            WHEN e.ext_entity_subtype_id = ''4001'' THEN ''ACCIDENTES''
            WHEN e.ext_entity_subtype_id = ''4002'' THEN ''METEOROLÓGICAS''
            WHEN e.ext_entity_subtype_id = ''4003'' THEN ''TRÁFICO''
            ELSE ''VARIOS''
        END AS categoria,
        e.alias AS tipo,
		e.coordinates::json->0->>1 AS latitud,
        e.coordinates::json->0->>0 AS longitud,
		CASE 
            WHEN e.status = ''CREATED'' THEN ''ACTIVA''
            WHEN e.status = ''DELETED'' THEN ''FINALIZADA''
            WHEN e.status = ''UPDATED'' THEN ''ACTIVA''
            ELSE e.status::varchar
        END AS estado,
		pv.param_5::timestamptz AT TIME ZONE ''Europe/Madrid'' AS fecha,
        pv.param_1 as localizacion,
        pv.param_2 as estado112,
		split_part(pv.param_3, ''-'', 1)::int8 AS num_recursos,
		split_part(pv.param_4, ''-'', 1)::int8 AS num_vehiculos,
	    CASE 
	        WHEN e.status = ''DELETED'' THEN e.last_update
	        ELSE NULL
	    END AS fecha_finalizacion
    FROM 
        (SELECT DISTINCT ON (ext_entity_id) *
         FROM hist.ext_entities
         WHERE ext_entity_type_id IN (4)
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
                ext_entity_type_param_id IN (1, 2, 3, 4, 5)
            ORDER BY 
                ext_entity_id, 
                created_at desc,
                ext_entity_type_param_id$$
        ) AS pv (
            ext_entity_id int8,
            param_1 varchar,
            param_2 varchar,
            param_3 varchar,
            param_4 varchar,
            param_5 varchar
        )
    ON 
        e.ext_entity_id = pv.ext_entity_id
    ';
  END IF;
END $do$;
