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
        e.alias AS tipo,
        pv.param_3 as fecha,
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
