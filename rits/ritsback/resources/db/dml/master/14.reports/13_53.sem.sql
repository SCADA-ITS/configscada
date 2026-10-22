--Función de pgpsql utilizada para la herramienta de informes JasperReports Server
--drop FUNCTION obtener_resultados_sem();

CREATE OR REPLACE FUNCTION obtener_resultados_sem()
RETURNS TABLE (
	label_alias varchar,
	alias varchar, 
    light int4,
	created_at timestamptz
) AS $$
DECLARE
    table_exists boolean;
	sem_exist boolean;
	sem_2_groups_exist boolean;
    query text;
	tablas text[] := array ['sem', 'sem_2_grupos'];
							 
	contador integer;
	array_length INTEGER;
BEGIN
    query := '';
	array_length := array_length(tablas, 1); -- Obtener la longitud del array
	FOR contador IN 1..array_length LOOP -- Bucle FOR basado en la longitud del array
        -- Acciones a realizar en cada iteración		
		-- Verificar si la columna existe en la tabla
		SELECT EXISTS (
			SELECT 1
			FROM information_schema.columns
			WHERE table_schema = 'hist'
				AND table_name = tablas[contador]
		) INTO table_exists;
		
		if table_exists and contador = 1 then
			sem_exist = true;
		elsif table_exists and contador = 2 then
			sem_2_groups_exist = true;
		elsif table_exists <> true and contador = 1 then
			sem_exist = false;
		else
			sem_2_groups_exist = false;
		end if;
				
    END LOOP;

	if sem_exist = true and sem_2_groups_exist = true then
		query := 'select et.label_alias, e.alias, s.light, s.created_at from hist.sem s
inner join conf.elements e on e.element_id = s.f_element_id 
inner join master.element_types et on et.element_type_id = e.element_type_id 
where e.element_type_id = 13
UNION 
select et.label_alias, e.alias, sg.light, sg.created_at from hist.sem_2_grupos sg
inner join conf.elements e on e.element_id = sg.f_element_id 
inner join master.element_types et on et.element_type_id = e.element_type_id 
where e.element_type_id = 53
order by created_at;';
	elsif sem_exist = true and sem_2_groups_exist = false then
		query := 'select f_element_id, light, created_at from hist.sem;';
	elsif sem_exist = false and sem_2_groups_exist = true then
		query := 'select f_element_id, light, created_at from hist.sem_2_grupos;';
	end if;
	RAISE NOTICE '%', query;
	
    -- Ejecutar la consulta dinámica y retornar los resultados como una tabla
    RETURN QUERY EXECUTE query;
END
$$ LANGUAGE plpgsql;

