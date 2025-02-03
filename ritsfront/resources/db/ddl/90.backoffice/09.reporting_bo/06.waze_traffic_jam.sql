DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') AND
  	  EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'hist' AND tablename = 'ext_entities')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
    CREATE OR REPLACE VIEW reporting_bo.waze_traffic_jam_ext_entities_with_values AS
    SELECT 
        e.uid as id,
        case
        	when e.ext_entity_subtype_id = 8001 then ''(0) Libre''
        	when e.ext_entity_subtype_id = 8002 then ''(1) Velocidad moderada''
        	when e.ext_entity_subtype_id = 8003 then ''(2) Muy lenta''
        	when e.ext_entity_subtype_id = 8004 then ''(3) Paradas intermitentes''
        	when e.ext_entity_subtype_id = 8005 then ''(4) Paradas prolongadas''
        	when e.ext_entity_subtype_id = 8006 then ''(5) Detenida''
        	ELSE e.status::varchar
        end as circulacion,
        e.alias AS calle,
        pv.param_5 AS ciudad,
        pv.param_6 AS pais,
        TO_TIMESTAMP(pv.param_1::BIGINT / 1000) AS fecha_publicacion,
        pv.param_2 AS velocidad_km_h,
        pv.param_3 AS distancia_m,
        pv.param_4 AS retraso_s,
        pv.param_7 AS comienzo,
        pv.param_8 AS fin,
        CASE 
            WHEN e.status = ''CREATED'' THEN ''ACTIVA''
            WHEN e.status = ''DELETED'' THEN ''FINALIZADA''
            WHEN e.status = ''UPDATED'' THEN ''ACTIVA''
            ELSE e.status::varchar
        END AS estado
    FROM 
        (SELECT DISTINCT ON (ext_entity_id) *
         FROM hist.ext_entities
         WHERE ext_entity_type_id IN (8)
         ORDER BY ext_entity_id, created_at DESC) AS e
    LEFT JOIN 
        crosstab(
            $$SELECT 
                ext_entity_id, 
                ext_entity_type_param_id, 
                value
            FROM 
                hist.ext_entity_values
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
            param_5 varchar,
            param_6 varchar,
            param_7 varchar,
            param_8 varchar
        )
    ON 
        e.ext_entity_id = pv.ext_entity_id
    ';
  END IF;
END $do$;
