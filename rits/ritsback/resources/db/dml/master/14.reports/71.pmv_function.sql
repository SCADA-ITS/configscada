--Función de pgpsql utilizada para la herramienta de informes JasperReports Server
--DROP FUNCTION obtener_resultados_pmv();

CREATE OR REPLACE FUNCTION obtener_resultados_pmv()
RETURNS TABLE (
	f_element_id int8, 
    brightness int4,
    temperature int4,
    data_json varchar,
    actual_brightness_state int4,
	created_at timestamptz
) AS $$
DECLARE
    column_exists boolean;
    query text;
	medidas text[] := array ['f_element_id','brightness','temperature','data_json','actual_brightness_state','created_at'];
							 
 	tipo_dato text[] := array ['int8','int4','int4','varchar','int4','timestamptz'];
	contador integer;
	array_length INTEGER;
BEGIN
    query := 'SELECT ';
	array_length := array_length(medidas, 1); -- Obtener la longitud del array
	FOR contador IN 1..array_length LOOP -- Bucle FOR basado en la longitud del array
        -- Acciones a realizar en cada iteración		
		-- Verificar si la columna existe en la tabla
		SELECT EXISTS (
			SELECT 1
			FROM information_schema.columns
			WHERE table_schema = 'hist'
				AND table_name = 'pmv'
				AND column_name = medidas[contador]
		) INTO column_exists;
		
		IF contador =1 THEN
			IF column_exists THEN
				query := query || medidas[contador];
			ELSE
				query := query || 'NULL::' || tipo_dato[contador] || ' AS ' || medidas[contador];
			END IF;
		ELSE
			IF column_exists THEN
				query := query || ', ' || medidas[contador];
			ELSE
				query := query || ', NULL::' || tipo_dato[contador] || ' AS ' || medidas[contador];
			END IF;
		END IF;
    END LOOP;

	query := query || ' FROM hist.pmv';

	RAISE NOTICE '%', query;
	
    -- Ejecutar la consulta dinámica y retornar los resultados como una tabla
    RETURN QUERY EXECUTE query;
END
$$ LANGUAGE plpgsql;