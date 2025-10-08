DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_reporting_bo') AND
  	  EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'hist' AND tablename = 'ext_entities')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
    CREATE OR REPLACE VIEW reporting_bo.waze_traffic_alert_ext_entities_with_values AS
    SELECT 
        e.uid as id,
		e.last_update as fecha_actualizacion,
		CASE
			WHEN e.ext_entity_subtype_id in (''7001'', ''7002'') THEN ''Accidente''
			WHEN e.ext_entity_subtype_id in (''7003'', ''7004'', ''7005'', ''7006'') THEN ''Atasco''
			WHEN e.ext_entity_subtype_id in (''7003'', ''7004'', ''7005'', ''7006'',
											 ''7007'', ''7008'', ''7009'', ''7010'',
											 ''7011'', ''7012'', ''7013'', ''7014'',
											 ''7015'', ''7016'', ''7017'', ''7018'',
											 ''7019'', ''7020'', ''7021'', ''7022'',
											 ''7023'', ''7024'', ''7025'', ''7026'',
											 ''7027'', ''7028'', ''7029'', ''7030'', ''7031'') THEN ''Riesgo en el tráfico''
			WHEN e.ext_entity_subtype_id = ''7032'' THEN ''Varios''
			WHEN e.ext_entity_subtype_id = ''7033'' THEN ''Construcción''
			WHEN e.ext_entity_subtype_id in (''7034'', ''7035'', ''7036'') THEN ''Carretera cortada''
			ELSE ''Sin clasificación''
		END AS categoria, 
		CASE
			WHEN e.ext_entity_subtype_id = ''7001'' THEN ''Accidente leve''
			WHEN e.ext_entity_subtype_id = ''7002'' THEN ''Accidente grave''
			WHEN e.ext_entity_subtype_id = ''7003'' THEN ''Tráfico moderado''
			WHEN e.ext_entity_subtype_id = ''7004'' THEN ''Tráfico intenso''
			WHEN e.ext_entity_subtype_id = ''7005'' THEN ''Tráfico detenido''
			WHEN e.ext_entity_subtype_id = ''7006'' THEN ''Tráfico ligero''
			WHEN e.ext_entity_subtype_id = ''7007'' THEN ''Peligro en la carretera''
			WHEN e.ext_entity_subtype_id = ''7008'' THEN ''Peligro en el arcén''
			WHEN e.ext_entity_subtype_id = ''7009'' THEN ''Peligro por condiciones meteorológicas''
			WHEN e.ext_entity_subtype_id = ''7010'' THEN ''Objeto en la carretera''
			WHEN e.ext_entity_subtype_id = ''7011'' THEN ''Bache en la carretera''
			WHEN e.ext_entity_subtype_id = ''7012'' THEN ''Animal muerto en la carretera''
			WHEN e.ext_entity_subtype_id = ''7013'' THEN ''Vehículo detenido en el arcén''
			WHEN e.ext_entity_subtype_id = ''7014'' THEN ''Animales en el arcén''
			WHEN e.ext_entity_subtype_id = ''7015'' THEN ''Señal de tráfico faltante en el arcén''
			WHEN e.ext_entity_subtype_id = ''7016'' THEN ''Niebla''
			WHEN e.ext_entity_subtype_id = ''7017'' THEN ''Granizo''
			WHEN e.ext_entity_subtype_id = ''7018'' THEN ''Lluvia intensa''
			WHEN e.ext_entity_subtype_id = ''7019'' THEN ''Nieve intensa''
			WHEN e.ext_entity_subtype_id = ''7020'' THEN ''Inundación''
			WHEN e.ext_entity_subtype_id = ''7021'' THEN ''Monzón''
			WHEN e.ext_entity_subtype_id = ''7022'' THEN ''Tornado''
			WHEN e.ext_entity_subtype_id = ''7023'' THEN ''Ola de calor''
			WHEN e.ext_entity_subtype_id = ''7024'' THEN ''Huracán''
			WHEN e.ext_entity_subtype_id = ''7025'' THEN ''Lluvia helada''
			WHEN e.ext_entity_subtype_id = ''7026'' THEN ''Carril cerrado''
			WHEN e.ext_entity_subtype_id = ''7027'' THEN ''Mancha de aceite en la carretera''
			WHEN e.ext_entity_subtype_id = ''7028'' THEN ''Hielo en la carretera''
			WHEN e.ext_entity_subtype_id = ''7029'' THEN ''Obras en la carretera''
			WHEN e.ext_entity_subtype_id = ''7030'' THEN ''Vehículo detenido en la carretera''
			WHEN e.ext_entity_subtype_id = ''7031'' THEN ''Semáforo averiado''
			WHEN e.ext_entity_subtype_id = ''7032'' THEN ''Varios''
			WHEN e.ext_entity_subtype_id = ''7033'' THEN ''Construcción''
			WHEN e.ext_entity_subtype_id = ''7034'' THEN ''Carretera cerrada por peligro''
			WHEN e.ext_entity_subtype_id = ''7035'' THEN ''Carretera cerrada por obras''
			WHEN e.ext_entity_subtype_id = ''7036'' THEN ''Carretera cerrada por evento''
		END AS tipo, 
		e.alias AS carretera,
		e.coordinates::json->0->>1 AS latitud,
        e.coordinates::json->0->>0 AS longitud,
        TO_TIMESTAMP(pv.param_1::BIGINT / 1000) AS fecha_publicacion,
        pv.param_2 AS direccion,
        COALESCE(pv.param_3, ''-'') AS calle,
        COALESCE(pv.param_4, ''-'') AS localidad,
        COALESCE(pv.param_5, ''-'') AS pais,
        COALESCE(pv.param_6, ''-'') AS valoracion,
        COALESCE(pv.param_7, ''-'') AS fiabilidad,
        COALESCE(pv.param_8, ''-'') AS confianza,
        COALESCE(pv.param_9, ''-'') AS usuario_municipio,
        CASE 
            WHEN e.status = ''CREATED'' THEN ''ACTIVA''
            WHEN e.status = ''DELETED'' THEN ''FINALIZADA''
            WHEN e.status = ''UPDATED'' THEN ''ACTIVA''
            ELSE e.status::varchar
        END AS estado,
	    CASE 
	        WHEN e.status = ''DELETED'' THEN e.last_update
	        ELSE NULL
	    END AS fecha_finalizacion
    FROM 
        (SELECT DISTINCT ON (ext_entity_id) *
         FROM hist.ext_entities
         WHERE ext_entity_type_id IN (7)
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
            param_8 varchar,
            param_9 varchar
        )
    ON 
        e.ext_entity_id = pv.ext_entity_id
    ';
  END IF;
END $do$;
