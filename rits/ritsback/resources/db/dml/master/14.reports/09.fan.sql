--Función de pgpsql utilizada para la herramienta de informes JasperReports Server
--drop FUNCTION obtener_resultados_fan();

CREATE OR REPLACE FUNCTION obtener_resultados_fan()
RETURNS TABLE (
	f_element_id int8, 
    fan_state int4,
	fan_vibration float8,
    front_bearing_temperature int4,
    back_bearing_temperature int4,
	fan_available bool,
	fan_running bool,
	local_remote bool,
	flow int4,
	electrical_consumption int4,
	turning_direction bool,
	parcial_operating_hours int4,
	total_operating_hours int4,
	power int4,
	engine_reference int4,
	engine_speed int4,
	engine_speed_rpm int4,
	axial_direct_order bool,
	axial_reverse_order bool,
	axial_local_mode bool,
	axial_remote_mode bool,
	emergency_mode bool,
	open_damper bool,
	close_damper bool,
	n_starts int4,
	created_at timestamptz
) AS $$
DECLARE
    column_exists boolean;
    query text;
	medidas text[] := array ['f_element_id','fan_state','fan_vibration','front_bearing_temperature',
							 'back_bearing_temperature','fan_available','fan_running','local_remote',
							 'flow','electrical_consumption','turning_direction','parcial_operating_hours',
							 'total_operating_hours','power','engine_reference','engine_speed','engine_speed_rpm',
							 'axial_direct_order','axial_reverse_order','axial_local_mode','axial_remote_mode',
							 'emergency_mode','open_damper','close_damper','n_starts','created_at'];
							 
 	tipo_dato text[] := array ['int8','int4','float8','int4','int4','bool','bool','bool','int4','int4','bool',
							   'int4','int4','int4','int4','int4','int4','bool','bool','bool','bool','bool','bool',
							   'bool','int4','timestamptz'];
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
				AND table_name = 'fan'
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

	query := query || ' FROM hist.fan ORDER BY created_at ASC';

	RAISE NOTICE '%', query;
	
    -- Ejecutar la consulta dinámica y retornar los resultados como una tabla
    RETURN QUERY EXECUTE query;
END
$$ LANGUAGE plpgsql;