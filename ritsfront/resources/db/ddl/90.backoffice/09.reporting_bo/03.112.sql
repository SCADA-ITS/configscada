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
        e.uid as id,
		CASE 
            WHEN pv.param_6 in (''11020'', ''11021'', ''11022'', ''11080'', ''11081'', ''11082'', ''11150'', ''11160'', ''11360'') THEN ''Accidentes''
            WHEN pv.param_6 in (''11510'', ''11570'', ''11640'') THEN ''Meteorológicas''
            WHEN pv.param_6 in (''12000'', ''12001'', ''12010'', ''12020'', ''12030'', ''12040'', ''12050'', ''12060'', ''12070'', 
                                ''12080'', ''12090'', ''12100'', ''12110'', ''12120'', ''12130'', ''12350'', ''12360'', ''12370'',
                                ''12410'', ''12420'', ''12430'', ''12440'', ''12510'', ''12520'') THEN ''Tráfico''
            WHEN pv.param_6 in (''18010'') THEN ''Varios''
            ELSE pv.param_6::varchar
        END AS categoria,
        e.alias AS tipo,
		pv.param_5::timestamptz AT TIME ZONE ''Europe/Madrid'' AS fecha,
        pv.param_1 as localizacion,
        pv.param_2 as estado
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
                ext_entity_type_param_id IN (1, 2, 5, 6)
            ORDER BY 
                ext_entity_id, 
                created_at desc,
                ext_entity_type_param_id$$
        ) AS pv (
            ext_entity_id int8,
            param_1 varchar,
            param_2 varchar,
            param_5 varchar,
            param_6 varchar
        )
    ON 
        e.ext_entity_id = pv.ext_entity_id
    ';
  END IF;
END $do$;
