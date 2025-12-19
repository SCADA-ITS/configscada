DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
	CREATE OR REPLACE VIEW backoffice.m_november AS
	SELECT
        abs(hashtext((extract(year from i.date)::text))::int8) AS id,
	    ROW_NUMBER() OVER (
			PARTITION BY EXTRACT(YEAR FROM i."date"), EXTRACT(MONTH FROM i."date")
	        ORDER BY i."date"
	    ) AS num_registro,
        to_char(i."date", ''YYYY'') as anio,
        CASE 
          WHEN to_char(i."date", ''MM'') = ''01'' THEN ''Enero''
          WHEN to_char(i."date", ''MM'') = ''02'' THEN ''Febrero''
          WHEN to_char(i."date", ''MM'') = ''03'' THEN ''Marzo''
          WHEN to_char(i."date", ''MM'') = ''04'' THEN ''Abril''
          WHEN to_char(i."date", ''MM'') = ''05'' THEN ''Mayo''
          WHEN to_char(i."date", ''MM'') = ''06'' THEN ''Junio''
          WHEN to_char(i."date", ''MM'') = ''07'' THEN ''Julio''
          WHEN to_char(i."date", ''MM'') = ''08'' THEN ''Agosto''
          WHEN to_char(i."date", ''MM'') = ''09'' THEN ''Septiembre''
          WHEN to_char(i."date", ''MM'') = ''10'' THEN ''Octubre''
          WHEN to_char(i."date", ''MM'') = ''11'' THEN ''Noviembre''
          WHEN to_char(i."date", ''MM'') = ''12'' THEN ''Diciembre''
          ELSE ''-''
        END AS mes,
        to_char(i."date", ''DD'') AS dia,
        to_char(i."date", ''HH24:MI'') as hora,
        i.km,
        et.alias as tipo_accidente,
        count(distinct case when p.participant_injury_id = 1 then p.id end)::varchar AS ilesos,
        count(distinct case when p.participant_injury_id = 5 then p.id end)::varchar as muertos,
        count(distinct case when p.participant_injury_id = 4 then p.id end)::varchar as graves,
        count(distinct case when p.participant_injury_id = 3 then p.id end)::varchar as menos_graves,
        count(distinct case when p.participant_injury_id = 2 then p.id end)::varchar as leves,
        count(distinct case when p.participant_injury_id in (2,3,4) then p.id end)::varchar as lesionados,
        c.alias AS causa,
        rl.alias AS ubicacion_relativa,
        (SELECT string_agg(sst.alias::text, ''-'' ORDER BY sst.alias)
           FROM backoffice.support_services ss
           LEFT JOIN backoffice.support_service_subtypes sst 
             ON ss.subtype_id = sst.id
          WHERE ss.type_id = 2 and ss.incident_id = i.id) as internas,
        (SELECT string_agg(sst.alias::text, ''-'' ORDER BY sst.alias)
           FROM backoffice.support_services ss
           LEFT JOIN backoffice.support_service_subtypes sst 
             ON ss.subtype_id = sst.id
          WHERE ss.type_id = 1 and ss.incident_id = i.id) as externas
      FROM backoffice.incidents i
      LEFT JOIN backoffice.event_type et 
        ON i.event_id = et.id
      LEFT JOIN backoffice.participants p 
        ON i.id = p.incident_id
      LEFT JOIN backoffice.causes c 
        ON i.cause_id = c.id 
      LEFT JOIN backoffice.relative_location rl 
        ON i.relative_location_id = rl.id
	  WHERE
		extract(month from i.date) = 11
      GROUP BY i."date", i.km, et.alias, c.alias, rl.alias, i.id
      ORDER BY i."date"';
  END IF;
END $do$;