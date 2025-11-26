DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
	CREATE OR REPLACE VIEW backoffice.annual_summary AS
	WITH resumen_base AS (
	    SELECT
	        extract(year from i.date) as anio,
	        count(distinct i.id)::int as total_accidentes,
	        count(distinct case when i.event_id = 1 then i.id end)::int as atropello,
	        count(distinct case when i.event_id = 2 then i.id end)::int as caida,
	        count(distinct case when i.event_id = 3 then i.id end)::int as volcadura,
		    count(distinct case when i.collision_id in (1,2,3,4,5) then i.id end)::int  AS colision,
		    count(distinct case when i.object_crash_id in (1,2,3) 
	                         or i.vehicle_crash_id in (1,2,3,4,5,6,7,8,9) then i.id end)::int  AS choque,
	        count(distinct case when i.event_id = 10 then i.id end)::int as otros,
	        count(distinct p.incident_id)::int as con_lesionados,
	        count(distinct case when p.participant_injury_id = 2 then p.id end)::int as leves,
	        count(distinct case when p.participant_injury_id = 3 then p.id end)::int as menos_graves,
	        count(distinct case when p.participant_injury_id = 4 then p.id end)::int as graves,
	        count(distinct case when p.participant_injury_id = 5 then p.id end)::int as muertos,
	        count(distinct case when p.participant_injury_id in (2,3,4) then p.id end)::int as lesionados
	    FROM backoffice.incidents i
	    LEFT JOIN backoffice.participants p
	        ON i.id = p.incident_id
	    GROUP BY extract(year from i.date)
	),
	resumen AS (
	    SELECT
	        abs(hashtext(anio::text)::int8) AS id,
	        anio::varchar,
	        total_accidentes,
	        con_lesionados,
	        muertos,
	        graves,
	        menos_graves,
	        leves,
	        lesionados,
	        atropello,
	        caida,
	        volcadura,
			colision,
			choque,
			otros
	    FROM resumen_base
	)
	
	SELECT 
		id,
	    anio,
	    total_accidentes,
	    con_lesionados,
	    muertos,
	    graves,
	    menos_graves,
	    leves,
	    lesionados,
	    atropello,
	    caida,
	    volcadura,
		colision,
		choque,
		otros,
	    1 as orden
	FROM resumen
	
	UNION ALL
	
	SELECT
	    999999 AS id,
	    ''TOTAL''::varchar as anio,
	    SUM(total_accidentes)::int,
	    SUM(con_lesionados)::int,
	    SUM(muertos)::int,
	    SUM(graves)::int,
	    SUM(menos_graves)::int,
	    SUM(leves)::int,
	    SUM(lesionados)::int,
	    SUM(atropello)::int,
	    SUM(caida)::int,
	    SUM(volcadura)::int,
	    SUM(colision)::int,
	    SUM(choque)::int,
	    SUM(otros)::int,
	    2 as orden
	FROM resumen
	
	ORDER BY orden, anio';
  END IF;
END $do$;