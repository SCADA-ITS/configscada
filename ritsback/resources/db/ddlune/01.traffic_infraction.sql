SET client_min_messages TO WARNING;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT schema_name 
        FROM information_schema.schemata 
        WHERE schema_name = 'rt'
    ) THEN
        CREATE SCHEMA rt;
    ELSE
        IF EXISTS (
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = 'rt'
              AND table_name = 'une_traffic_infractions'
        ) THEN
            DROP TABLE rt.une_traffic_infractions;
        END IF;
        
        IF EXISTS (
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = 'rt'
              AND table_name = 'une_mobile_radar_traffic_infractions'
        ) THEN
            DROP TABLE rt.une_mobile_radar_traffic_infractions;
        END IF;
    END IF;
END $$;


-- 
-- Scope: rt
--
-- 
-- Table: rt.une_traffic_infractions
-- Descripción: 
-- Scope: rt
--

	CREATE TABLE rt.une_traffic_infractions (
	
	 	id BIGSERIAL,
	    ordinal_id INT2 NOT NULL, 
	    element_type_id INT8 NOT NULL,
	    element_id int8 NOT NULL,
	    installation_id VARCHAR(17) NOT NULL, 		-- 01 Identificación de la instalación
	    detection_point VARCHAR(17) NOT NULL, 		-- 02 Identificación del punto de detección
	    infraction_date DATE NOT NULL, 			 	-- 03 Fecha de la infracción (formato estándar SQL para fechas)
	    infraction_time TIME NOT NULL, 			 	-- 04 Hora de la infracción
	    cinemometer_model VARCHAR(20) NOT NULL,  	-- 05 Modelo de cinemómetro
	    cinemometer_serial VARCHAR(20) NOT NULL,	-- 06 Número de serie del cinemómetro
	    speed_precise NUMERIC(6,2) NOT NULL, 		-- 07 Velocidad medida con decimales (6 caracteres, 2 decimales)
	    vehicle_plate VARCHAR(15),	 				-- 08 Matrícula
	    lateral_distance NUMERIC(5,2), 				-- 09 Distancia lateral (5 caracteres, 2 decimales)
	    frontal_distance INT2, 						-- 10 Distancia con el vehículo frontal (3 dígitos: 0 a 999)
	    direction VARCHAR(1), 						-- 11 Sentido: '+' o '-'
	    vehicle_category VARCHAR(1) NOT NULL, 		-- 12 Categoría del vehículo: 'C' o 'L'
	    infraction_type VARCHAR(2), 				-- 13 Tipo de infracción
	    speed_threshold_light INT2, 				-- 14 Umbral de disparo velocidad vehículos ligeros (3 dígitos)
	    speed_threshold_heavy INT2, 				-- 15 Umbral de disparo velocidad vehículos pesados (3 dígitos)
	    distance_threshold_light INT2, 				-- 16 Umbral de disparo distancia vehículos ligeros (3 dígitos)
	    distance_threshold_heavy INT2, 				-- 17 Umbral de disparo distancia vehículos pesados (3 dígitos)
	    speed_limit INT2, 							-- 18 Velocidad límite legal de la vía (3 dígitos)
	    image_count INT2, 							-- 19 Número de imágenes en el fichero (1 dígito)
	    	
	    created_at timestamptz NOT NULL,
	    
	    CONSTRAINT pk_une_traffic_infractions PRIMARY KEY (ordinal_id, element_type_id,  element_id, created_at)
	);

	CREATE INDEX idx_une_traffic_infractions_element_type_element_id ON rt.une_traffic_infractions (element_type_id, element_id);
	ALTER TABLE rt.une_traffic_infractions SET TABLESPACE tbl_rt;
	
	
-- Table: hist.une_mobile_radar_traffic_infractions
-- Descripción: 
-- Scope: hist
--

	CREATE TABLE rt.une_mobile_radar_traffic_infractions (
	
	    une_traffic_infraction_id INT8 NOT NULL, 
	  												-- Mobile radar 
	    radar_type VARCHAR(1) NOT NULL, 			-- 50 Tipo de radar: 'M' o 'F'
	    headquarters_id INT2 NOT NULL, 				-- 51 ID Jefatura (2 dígitos)
	    location_type VARCHAR(1) NOT NULL, 			-- 52 Tipo de localización: 'U' o 'I'
	    limitation_type VARCHAR(1) NOT NULL, 		-- 53 Tipo de limitación: 'G' o 'E'
	    street VARCHAR(40), 						-- 54 Calle
	    direction_address VARCHAR(40), 				-- 55 Dirección
	    town VARCHAR(40), 							-- 56 Población
	    road VARCHAR(6), 							-- 57 Carretera
	    kilometer_point NUMERIC(7,3), 				-- 58 Punto kilométricoelement_type_id
	    road_direction VARCHAR(1), 					-- 59 Sentido de la vía: 'C' o 'D'
	    
	    created_at timestamptz NOT NULL,
	    CONSTRAINT pk_une_traffic_infraction_mobile_radar  PRIMARY KEY (une_traffic_infraction_id, created_at)
	);

	ALTER TABLE rt.une_mobile_radar_traffic_infractions SET TABLESPACE tbl_rt;

