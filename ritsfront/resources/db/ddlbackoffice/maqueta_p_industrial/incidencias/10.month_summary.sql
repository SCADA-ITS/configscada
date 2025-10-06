DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE '
	CREATE OR REPLACE VIEW backoffice.month_summary AS
	SELECT
	    abs(hashtext((extract(year from i.date)::text))::int8) AS id,
	    extract(month from i.date)::int as orden,
	    to_char(i.date, ''YYYY'') AS anio,
	    CASE extract(month from i.date)
		        WHEN 1 THEN ''Enero'' 
		        WHEN 2 THEN ''Febrero''
		        WHEN 3 THEN ''Marzo''
		        WHEN 4 THEN ''Abril''
		        WHEN 5 THEN ''Mayo''
		        WHEN 6 THEN ''Junio''
		        WHEN 7 THEN ''Julio''
		        WHEN 8 THEN ''Agosto''
		        WHEN 9 THEN ''Septiembre''
		        WHEN 10 THEN ''Octubre''
		        WHEN 11 THEN ''Noviembre''
		        WHEN 12 THEN ''Diciembre''
	        ELSE ''-''
	    END AS mes,
	    count(distinct i.id) AS total_accidentes,
	    count(distinct p.incident_id) AS con_lesionados,
	    count(distinct case when p.participant_injury_id = 5 then p.id end) AS muertos,
	    count(distinct case when p.participant_injury_id = 4 then p.id end) AS graves,
	    count(distinct case when p.participant_injury_id = 3 then p.id end) AS menos_graves,
	    count(distinct case when p.participant_injury_id = 2 then p.id end) AS leves,
	    count(distinct case when p.participant_injury_id in (2,3,4) then p.id end) AS lesionados,
	    count(distinct case when i.event_id = 1 then i.id end) AS atropello,
	    count(distinct case when i.event_id = 2 then i.id end) AS caida,
	    count(distinct case when i.event_id = 3 then i.id end) AS volcadura,
	    count(distinct case when i.collision_id in (1,2,3,4,5) then i.id end) AS colision,
	    count(distinct case when i.object_crash_id in (1,2,3) 
	                         or i.vehicle_crash_id in (1,2,3,4,5,6,7,8,9) then i.id end) AS choque,
		count(distinct case when i.event_id = 10 then i.id end) AS otros
	FROM backoffice.incidents i
	LEFT JOIN backoffice.participants p
	    ON i.id = p.incident_id
	LEFT JOIN backoffice.object_crash oc 
	    ON i.object_crash_id = oc.id
	LEFT JOIN backoffice.vehicle_crash vc 
	    ON i.vehicle_crash_id = vc.id
	GROUP BY 
	    extract(year from i.date),
	    extract(month from i.date),
	    to_char(i.date, ''YYYY'')
	ORDER BY anio DESC, orden ASC';
  END IF;
END $do$;