DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') AND
  	  EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'hist' AND tablename = 'ext_entities')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
    CREATE OR REPLACE VIEW reporting_bo.e112_vehicles_ext_entities AS
    SELECT 
        abs(hashtext(e.uid)::int8) AS id,
        e.uid,
		e.last_update as fecha_actualizacion,
   		abs(hashtext(split_part(e.uid, ''##'', 1))::int8) AS id_incidente,
    	split_part(e.uid, ''##'', 2)::varchar(20)  AS matricula,
        e.alias AS marca,
        e.description AS modelo,
        pv.param_1 as color,
        CASE 
            WHEN e.status = ''CREATED'' THEN ''ACTIVA''
            WHEN e.status = ''DELETED'' THEN ''FINALIZADA''
            WHEN e.status = ''UPDATED'' THEN ''ACTIVA''
            ELSE e.status::varchar
        END AS estado
    FROM 
        (SELECT DISTINCT ON (ext_entity_id) *
         FROM hist.ext_entities
         WHERE ext_entity_type_id IN (6)
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
                ext_entity_type_param_id IN (1)
            ORDER BY 
                ext_entity_id, 
                created_at desc,
                ext_entity_type_param_id$$
        ) AS pv (
            ext_entity_id int8,
            param_1 varchar
        )
    ON 
        e.ext_entity_id = pv.ext_entity_id
    ';
  END IF;
END $do$;
