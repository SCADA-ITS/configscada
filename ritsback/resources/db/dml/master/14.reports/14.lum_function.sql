--Función de pgpsql utilizada para la herramienta de informes JasperReports Server
--drop FUNCTION obtener_resultados_lum();

CREATE OR REPLACE FUNCTION obtener_resultados_lum()
RETURNS TABLE (
	f_element_id int8, 
    luminosity float8,
    luminosity_real float8,
    multiplier float8,
	created_at timestamptz
) AS $$
DECLARE
    column_exists boolean;
    query text;
	medidas text[] := array ['f_element_id','luminosity','luminosity_real','multiplier','created_at'];
							 
 	tipo_dato text[] := array ['int8','float8','float8','float8','timestamptz'];
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
				AND table_name = 'lum'
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

	query := query || ' FROM hist.lum';

	RAISE NOTICE '%', query;
	
    -- Ejecutar la consulta dinámica y retornar los resultados como una tabla
    RETURN QUERY EXECUTE query;
END
$$ LANGUAGE plpgsql;
