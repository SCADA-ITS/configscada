--Función de pgpsql utilizada para la herramienta de informes JasperReports Server
--DROP FUNCTION obtener_resultados_ws();


CREATE OR REPLACE FUNCTION obtener_resultados_ws()
RETURNS TABLE (
	f_element_id int8, 
    surface_temperature float8,
    sub_surface_temperature float8,
    freeze_point float8,
    air_pressure float8,
    water_depth int4,
    concentration_nacl int4,
    concentration_mgcl2 int4,
    concentration_cacl int4,
    road_condition int4,
    relative_humidity int4,
    precipitation_intensity float8,
    snow_fall_intensity float8,
    average_wind_speed float8,
    average_wind_direction int4,
    gust_wind_speed float8,
    gust_wind_direction int4,
    air_temperature float8,
    dew_point_temperature float8,
    precipitation_type int4,
    visibility float8,
    black_ice_signal int4,
    prediction_alarm_code int4,
    precipitation_quantity float8,
    ice_percent float8,
    friction float8,
	created_at timestamptz
) AS $$
DECLARE
    column_exists boolean;
    query text;
	medidas text[] := array ['f_element_id','surface_temperature','sub_surface_temperature','freeze_point','air_pressure','water_depth','concentration_nacl',
							 'concentration_mgcl2','Concentration_cacl','road_condition','relative_humidity','precipitation_intensity','snow_fall_intensity',
							 'average_wind_speed','average_wind_direction','gust_wind_speed','gust_wind_direction','air_temperature','dew_point_temperature',
							 'precipitation_type','visibility','black_ice_signal','prediction_alarm_code','precipitation_quantity','ice_percent','friction',
							 'created_at'];
							 
 	tipo_dato text[] := array ['int4','float8','float8','float8','float8','int4','int4','int4','int4','int4','int4','float8','float8','float8',
							   'int4','float8','int4','float8','float8','int4','float8','int4','int4','float8','float8','float8','timestamptz'];
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
				AND table_name = 'ws'
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

	query := query || ' FROM hist.ws ORDER BY created_at ASC';

	RAISE NOTICE '%', query;
	
    -- Ejecutar la consulta dinámica y retornar los resultados como una tabla
    RETURN QUERY EXECUTE query;
END
$$ LANGUAGE plpgsql;