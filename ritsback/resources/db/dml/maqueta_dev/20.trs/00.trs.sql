SET client_min_messages TO WARNING;

DO $$
BEGIN
	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_logs'
    ) THEN
        DELETE FROM rt.transit_logs;
    END IF;
	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_attachments'
    ) THEN
        DELETE FROM rt.transit_attachments;
    END IF;
	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_images'
    ) THEN
        DELETE FROM rt.transit_images;
    END IF;
    
    IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_values'
    ) THEN
        DELETE FROM rt.transit_values;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_driver_values'
    ) THEN
        DELETE FROM rt.transit_driver_values;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_drivers'
    ) THEN
        DELETE FROM rt.transit_drivers;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_vehicle_values'
    ) THEN
        DELETE FROM rt.transit_vehicle_values;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_vehicles'
    ) THEN
        DELETE FROM rt.transit_vehicles;
    END IF;

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transits'
    ) THEN
        DELETE FROM rt.transits;
    END IF;
END $$;

DELETE FROM conf.element_transit_types;
DELETE FROM static.infraction_types;
DELETE FROM static.vehicle_classes;
DELETE FROM static.driver_params;
DELETE FROM static.driver_types;
DELETE FROM static.vehicle_params;
DELETE FROM static.transit_type_state_transitions;
DELETE FROM static.transit_type_params;
DELETE FROM static.transit_types;
DELETE FROM master.transit_states;
DELETE FROM master.vehicle_types;


-- master.vehicle_types
INSERT INTO master.vehicle_types (vehicle_type_id,alias,enabled,visible,created_at,updated_at) VALUES
	 (1,'Vehicle Type I',true,true,'2025-02-06 10:15:42.887+01','2025-02-06 10:15:42.887+01');
	 
-- master.transit_states
INSERT INTO master.transit_states (transit_state_id,transit_state_code,alias,description,label_alias,label_description,user_transit_enable,enabled,visible,created_at,updated_at) VALUES
	 (1,'IN','Initial','Initial','LBL_TRANSIT_STATE_INITIAL','LBL_TRANSIT_STATE_INITIAL_DESC',false,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,'PE','Pending','Pending','LBL_TRANSIT_STATE_PENDING','LBL_TRANSIT_STATE_PENDING_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (3,'VA','Validated','Validated','LBL_TRANSIT_STATE_VALIDATED','LBL_TRANSIT_STATE_VALIDATED_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (4,'DI','Discarded','Discarded','LBL_TRANSIT_STATE_DISCARDED','LBL_TRANSIT_STATE_DISCARDED_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (5,'AR','Archived','Archived','LBL_TRANSIT_STATE_ARCHIVED','LBL_TRANSIT_STATE_ARCHIVED_DESC',true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- static.transit_types
INSERT INTO static.transit_types (transit_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,'Generic Transit','Generic Transit','LBL_TRANSIT_GENERIC','LBL_TRANSIT_GENERIC_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,'LPR','LPR','LBL_TRANSIT_LPR','LBL_TRANSIT_LPR_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (3,'ZBE','ZBE','LBL_TRANSIT_ZBE','LBL_TRANSIT_LPR_DESC',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

	 
-- static.transit_type_params
INSERT INTO static.transit_type_params (transit_type_id, transit_type_param_id, data_type_id, transit_type_param_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
	(1, 1, 2, null, 'Transit type Param 1', 'Transit type Param 1', 'LBL_TRANSIT_PARAM_1', 'LBL_TRANSIT_PARAM_DESC', true, true, '2025-01-31 11:24:43.315+01', '2025-01-31 11:24:43.315+01');

-- LPRR transit type params
INSERT INTO static.transit_type_params (transit_type_id, transit_type_param_id, data_type_id, transit_type_param_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
	(2, 1, 2, null, 'nationality', 'Nacionalidad', 'LBL_NATIONALITY', 'LBL_DESC_NATIONALITY', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(2, 2, 2, null, 'public_service_vehicle', 'Vehículo de servicio público', 'LBL_PUBLIC_SERVICE_VEHICLE', 'LBL_DESC_PUBLIC_SERVICE_VEHICLE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(2, 3, 2, null, 'hazardous_goods', 'Transporte de mercancías peligrosas', 'LBL_HAZARDOUS_GOODS', 'LBL_DESC_HAZARDOUS_GOODS', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ZBE transit type params
INSERT INTO static.transit_type_params (transit_type_id, transit_type_param_id, data_type_id, transit_type_param_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
	(3, 1, 2, null, 'nationality', 'Nacionalidad', 'LBL_NATIONALITY', 'LBL_DESC_NATIONALITY', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 2, 2, null, 'public_service_vehicle', 'Vehículo de servicio público', 'LBL_PUBLIC_SERVICE_VEHICLE', 'LBL_DESC_PUBLIC_SERVICE_VEHICLE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 3, 2, null, 'hazardous_goods', 'Transporte de mercancías peligrosas', 'LBL_HAZARDOUS_GOODS', 'LBL_DESC_HAZARDOUS_GOODS', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 4, 2, null, 'adr_code', 'Código ADR', 'LBL_ADR_CODE', 'LBL_DESC_ADR_CODE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 5, 2, null, 'imo_code', 'Código IMO', 'LBL_IMO_CODE', 'LBL_DESC_IMO_CODE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 6, 2, null, 'tag_obe', 'Identificador tag OBE', 'LBL_TAG_OBE', 'LBL_DESC_TAG_OBE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 7, 2, null, 'trailer_plate', 'Matrícula del remolque', 'LBL_TRAILER_PLATE', 'LBL_DESC_TRAILER_PLATE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 8, 1, null, 'speed_kmh', 'Velocidad en km/h', 'LBL_SPEED_KMH', 'LBL_DESC_SPEED_KMH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 9, 1, null, 'front_vehicle_distance', 'Distancia vehículo delante', 'LBL_FRONT_VEHICLE_DISTANCE', 'LBL_DESC_FRONT_VEHICLE_DISTANCE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 10, 2, null, 'vehicle_class', 'Clase del vehículo', 'LBL_VEHICLE_CLASS', 'LBL_DESC_VEHICLE_CLASS', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 11, 2, null, 'vehicle_type', 'Tipo de vehículo', 'LBL_VEHICLE_TYPE', 'LBL_DESC_VEHICLE_TYPE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 12, 1, null, 'vehicle_weight', 'Peso del vehículo (kg)', 'LBL_VEHICLE_WEIGHT', 'LBL_DESC_VEHICLE_WEIGHT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 13, 1, null, 'axle_weight_1', 'Peso del eje 1 (kg)', 'LBL_AXLE_WEIGHT_1', 'LBL_DESC_AXLE_WEIGHT_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 14, 1, null, 'axle_weight_2', 'Peso del eje 2 (kg)', 'LBL_AXLE_WEIGHT_2', 'LBL_DESC_AXLE_WEIGHT_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 15, 1, null, 'vehicle_length', 'Longitud del vehículo (mm)', 'LBL_VEHICLE_LENGTH', 'LBL_DESC_VEHICLE_LENGTH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 16, 1, null, 'vehicle_height', 'Altura del vehículo (mm)', 'LBL_VEHICLE_HEIGHT', 'LBL_DESC_VEHICLE_HEIGHT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 17, 1, null, 'vehicle_width', 'Anchura del vehículo (mm)', 'LBL_VEHICLE_WIDTH', 'LBL_DESC_VEHICLE_WIDTH', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 18, 2, null, 'vehicle_brand', 'Marca del vehículo', 'LBL_VEHICLE_BRAND', 'LBL_DESC_VEHICLE_BRAND', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 19, 2, null, 'vehicle_model', 'Modelo del vehículo', 'LBL_VEHICLE_MODEL', 'LBL_DESC_VEHICLE_MODEL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 20, 2, null, 'vehicle_color', 'Color del vehículo', 'LBL_VEHICLE_COLOR', 'LBL_DESC_VEHICLE_COLOR', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 21, 2, null, 'eco_label', 'Etiqueta medioambiental', 'LBL_ECO_LABEL', 'LBL_DESC_ECO_LABEL', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 22, 1, null, 'ocr_score', 'Porcentaje de lectura OCR (Score)', 'LBL_OCR_SCORE', 'LBL_DESC_OCR_SCORE', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	(3, 23, 2, null, 'coordinates', 'Coordenadas (ymin, ymax)', 'LBL_COORDINATES', 'LBL_DESC_COORDINATES', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- static.transit_type_state_transitions
INSERT INTO static.transit_type_state_transitions (transit_type_id,parent_transit_state_id,child_transit_state_id, auto, groovy_file, enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,2,3,false,'transitStateExample.groovy',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,2,4,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,3,5,true,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,4,5,false,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);



-- "static".vehicle_params
INSERT INTO static.vehicle_params (vehicle_type_id,vehicle_param_id,data_type_id,vehicle_param_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,NULL,'Vehicle Type I Param 1','Vehicle Type I Param 1','LBL_VEHICLE_TYPE_I_PARAM_I','LBL_VEHICLE_TYPE_I_PARAM_I_DESC',true,true,'2025-02-06 10:16:49.204+01','2025-02-06 10:16:49.204+01');


INSERT INTO static.driver_types (driver_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,'Driver Type I','Driver Type I','LBL_DRIVER_TYPE_I','LBL_DRIVER_TYPE_I_DESCRIPTION',true,true,'2025-02-06 09:08:13.125+01','2025-02-06 09:08:19.192+01');

INSERT INTO static.driver_params (driver_type_id,driver_param_id,data_type_id,driver_param_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,NULL,'Driver Type I Param 1','Driver Type I Param 1','LBL_DRIVER_TYPE_I_PARAM_1','LBL_DRIVER_TYPE_I_PARAM_1_DESC',true,true,'2025-02-06 09:09:38.587+01','2025-02-06 09:09:38.587+01');
	
INSERT INTO static.vehicle_classes (vehicle_class_id,alias,label_alias,enabled,visible,created_at,updated_at) VALUES
	 (1, 'light', 'LBL_VEHICLE_CLASS_LIGHT', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),	 
	 (2, 'heavy', 'LBL_VEHICLE_CLASS_HEAVY', true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
	 
INSERT INTO static.infraction_types (infraction_type_id,infraction_code,infraction_original_code,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,'0001','0001','Infraction type I','Infraction type I','LBL_INFRACTION_TYPE_1','LBL_INFRACTION_TYPE_1_DESC',true,true,'2025-03-12 10:37:18.887+01','2025-03-12 10:37:18.887+01');

INSERT INTO conf.element_transit_types (element_type_id, element_id, transit_type_id, infraction_manager_id, enabled, visible, created_at, updated_at) VALUES
	(99, 1, 1, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(2, 1, 2, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(2, 1, 3, null, true, true, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


DO $$
BEGIN

	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transits'
    ) THEN
    
		INSERT INTO rt.transits 
			(transit_id, transit_code, transit_type_id, dossier_code, remittance_code, installation_code, detection_point_code, date_transit, transit_state_id, transit_state_option_id, location_id, lane_number, element_type_id, element_id, certificate_valid_date, certificate_path, infraction_type_id, infraction_code, speed, distance, assigned_user_id, towards, address, locality, road, pk, direction, vehicle_type_id, vehicle_id, vehicle_brand_id, vehicle_model_id, vehicle_class_id, vehicle_country_id, vehicle_plate_number, vehicle_direction, vehicle_date_registration, vehicle_color, driver_type_id, driver_id, driver_license_type_id, driver_license_value, driver_license_issue_date, driver_license_expire_date, driver_name, driver_surname, driver_gender, driver_date_of_birth, driver_country_id, driver_state_id, driver_region_id, driver_locality_id, driver_address, enabled, visible, created_at, updated_at)				
			VALUES
				(1739893658868, 'CR. TEST, 8 C-20250216_121409_01853', 1, NULL, NULL, 'CR. TEST, 8 C', 'CR. TEST, 8 C', '2025-04-11 12:14:09.000', 1, NULL, NULL, NULL, 99, 1, NULL, NULL, NULL, NULL, 90.0, NULL, 1000, 'C', 'CR. TEST, 8', 'SANT BOI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 'B1111BB', true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, '2025-02-18 17:48:01.793', '2025-02-18 17:48:01.793'),
 				(1739893658864, 'CR. TEST, 8 C-20250217_121413_01854', 1, NULL, NULL, 'CR. TEST, 8 C', 'CR. TEST, 8 C', '2025-04-12 12:14:13.000', 1, NULL, NULL, NULL, 99, 1, NULL, NULL, NULL, NULL, 120.0, NULL, 1000, 'C', 'CR. TEST, 8', 'SANT BOI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '4231BBB', true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, '2025-02-18 17:48:01.793', '2025-02-18 17:48:01.793'),
				(1739893658872, 'CR. TEST, 8 C-20250215_121402_01852', 1, NULL, NULL, 'CR. TEST, 8 C', 'CR. TEST, 8 C', '2025-04-13 12:14:02.000', 1, NULL, NULL, NULL, 99, 1, NULL, NULL, NULL, NULL, 60.0, NULL, 1000, 'C', 'CR. TEST, 8', 'SANT BOI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '1234AAA', true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, '2025-02-18 17:48:01.793', '2025-04-07 16:05:42.582');
        
    END IF;

    
   	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_attachments'
    ) THEN
    
    	INSERT INTO rt.transit_attachments
				(transit_attachment_id, transit_id, "path", visible, created_at, updated_at) VALUES
				(1739893658867, 1739893658864, 'CR. TEST, 8 C-20250217_121413_01854_001.txt', true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
				(1739893658871, 1739893658868, 'CR. TEST, 8 C-20250216_121409_01853_001.txt', true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
				(1739893658875, 1739893658872, 'CR. TEST, 8 C-20250215_121402_01852_001.txt', true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793');
        
    END IF;

    
    	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'transit_images'
    ) THEN
    
    	INSERT INTO rt.transit_images
				(transit_image_id, transit_id, "path", read_only, visible, created_at, updated_at) VALUES
				(1739893658873, 1739893658872, 'CR. TEST, 8 C-20250215_121402_01852_001.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
				(1739893658874, 1739893658872, 'CR. TEST, 8 C-20250215_121402_01852_002.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
				(1739893658869, 1739893658868, 'CR. TEST, 8 C-20250216_121409_01853_001.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
				(1739893658870, 1739893658868, 'CR. TEST, 8 C-20250216_121409_01853_002.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
				(1739893658865, 1739893658864, 'CR. TEST, 8 C-20250217_121413_01854_001.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
				(1739893658866, 1739893658864, 'CR. TEST, 8 C-20250217_121413_01854_002.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793');
        
    END IF;

END $$;

	
--DO $$
--BEGIN
--
--	IF EXISTS (
--        SELECT table_name
-- 	        FROM information_schema.tables
-- 	        where table_schema = 'rt'
-- 	          and table_name =  'transits'
--    ) THEN
--        -- rt.transits
--		INSERT INTO rt.transits (transit_id, transit_code, transit_type_id, date_transit, plate_number, transit_state_id, transit_state_option_id, location_id, direction, lane_number, element_type_id, element_id, certificate_valid_date, certificate_path, driver_type_id, driver_id, vehicle_type_id, vehicle_id, infraction_type_id, infraction_code, speed, distance, assigned_user_id, enabled, visible, created_at, updated_at) VALUES
--		(3, 'TR0001', 1, '2025-01-31 11:09:08.849', NULL, 1, NULL, 1, NULL, NULL, 2, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, true, true, '2025-01-31 11:08:53.540', '2025-01-31 11:08:56.605');
--    END IF;
--
--	IF EXISTS (
--        SELECT table_name
-- 	        FROM information_schema.tables
-- 	        where table_schema = 'rt'
-- 	          and table_name =  'transit_drivers'
--    ) THEN
--		-- rt.transit_drivers
--		INSERT INTO rt.transit_drivers (transit_id,driver_type_id,license_type_id,license_value,license_issue_date,license_expire_date,"name",surname,gender,date_of_birth,country_id,state_id,region_id,locality_id,address,visible,created_at,updated_at) VALUES
--			 (3,NULL,NULL,NULL,NULL,NULL,'Pepito','Pérez','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2025-02-06 09:11:40.337+01','2025-02-06 09:11:40.337+01');
--    END IF;
--
--	IF EXISTS (
--        SELECT table_name
-- 	        FROM information_schema.tables
-- 	        where table_schema = 'rt'
-- 	          and table_name =  'transit_driver_values'
--    ) THEN
--		-- rt.transit_driver_values
--		INSERT INTO rt.transit_driver_values (transit_id,driver_type_id,driver_param_id,value,visible,created_at,updated_at) VALUES
--			 (3,1,1,'Valor Driver 1',true,'2025-02-06 09:12:19.984+01','2025-02-06 09:12:19.984+01');
--    END IF;
--
--	IF EXISTS (
--        SELECT table_name
-- 	        FROM information_schema.tables
-- 	        where table_schema = 'rt'
-- 	          and table_name =  'transit_vehicles'
--    ) THEN
--		-- rt.transit_vehicles
--		INSERT INTO rt.transit_vehicles (transit_id,vehicle_brand_id,vehicle_model_id,country_id,plate_number,date_registration,color,visible,created_at,updated_at) VALUES
--			 (3,NULL,NULL,NULL,'LE0000AG','2025-02-06 10:13:21.153+01','AZUL',NULL,'2025-02-06 10:13:21.153+01','2025-02-06 10:13:21.153+01');
--    END IF;
--
--	IF EXISTS (
--        SELECT table_name
-- 	        FROM information_schema.tables
-- 	        where table_schema = 'rt'
-- 	          and table_name =  'transit_vehicle_values'
--    ) THEN
--		-- rt.transit_vehicle_values
--		INSERT INTO rt.transit_vehicle_values (transit_id,vehicle_type_id,vehicle_param_id,value,visible,created_at,updated_at) VALUES
--			 (3,1,1,'Valor Vehicle 1',true,'2025-02-06 10:20:24.583+01','2025-02-06 10:20:24.583+01');
--    END IF;
--
--	IF EXISTS (
--        SELECT table_name
-- 	        FROM information_schema.tables
-- 	        where table_schema = 'rt'
-- 	          and table_name =  'transit_vehicle_values'
--    ) THEN
--		-- rt.transit_values	 
--		INSERT INTO rt.transit_values (transit_id, transit_type_id, transit_type_param_id, value, visible, created_at, updated_at) VALUES
--			(3, 1, 1, 'Valor transito 1', true, '2025-02-06 10:20:24.583+01','2025-02-06 10:20:24.583+01');
--    END IF;
--
--END $$;