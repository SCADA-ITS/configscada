SET client_min_messages TO WARNING;

DELETE FROM rt.transit_driver_values;
DELETE FROM rt.transit_drivers;
DELETE FROM rt.transit_vehicle_values;
DELETE FROM rt.transit_vehicles;

DELETE FROM rt.transits;

DELETE FROM "static".driver_params;
DELETE FROM "static".driver_types;
DELETE FROM "static".vehicle_params;
DELETE FROM master.vehicle_types;


-- master.vehicle_types
INSERT INTO master.vehicle_types (vehicle_type_id,alias,enabled,visible,created_at,updated_at) VALUES
	 (1,'Vehicle Type I',true,true,'2025-02-06 10:15:42.887+01','2025-02-06 10:15:42.887+01');

-- "static".vehicle_params
INSERT INTO "static".vehicle_params (vehicle_type_id,vehicle_param_id,data_type_id,vehicle_param_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,NULL,'Vehicle Type I Param 1','Vehicle Type I Param 1','LBL_VEHICLE_TYPE_I_PARAM_I','LBL_VEHICLE_TYPE_I_PARAM_I_DESC',true,true,'2025-02-06 10:16:49.204+01','2025-02-06 10:16:49.204+01');


INSERT INTO "static".driver_types (driver_type_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,'Driver Type I','Driver Type I','LBL_DRIVER_TYPE_I','LBL_DRIVER_TYPE_I_DESCRIPTION',true,true,'2025-02-06 09:08:13.125+01','2025-02-06 09:08:19.192+01');

INSERT INTO "static".driver_params (driver_type_id,driver_param_id,data_type_id,driver_param_group_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,NULL,'Driver Type I Param 1','Driver Type I Param 1','LBL_DRIVER_TYPE_I_PARAM_1','LBL_DRIVER_TYPE_I_PARAM_1_DESC',true,true,'2025-02-06 09:09:38.587+01','2025-02-06 09:09:38.587+01');

-- rt.transits
INSERT INTO rt.transits (transit_id, transit_code, transit_type_id, date_transit, plate_number, transit_type_state_id, transit_type_state_option_id, location_id, direction, lane_number, element_type_id, element_id, certificate_valid_date, certificate_path, driver_type_id, driver_id, vehicle_type_id, vehicle_id, infraction_type_id, infraction_code, speed, distance, assigned_user_id, enabled, visible, created_at, updated_at) VALUES
(3, 'TR0001', 1, '2025-01-31 11:09:08.849', NULL, 1, NULL, 1, NULL, NULL, 2, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, true, true, '2025-01-31 11:08:53.540', '2025-01-31 11:08:56.605');

-- rt.transit_drivers
INSERT INTO rt.transit_drivers (transit_id,driver_type_id,license_type_id,license_value,license_issue_data,license_expire_date,"name",surname,gender,date_of_birth,country_id,state_id,region_id,locality_id,address,visible,created_at,updated_at) VALUES
	 (3,NULL,NULL,NULL,NULL,NULL,'Pepito','Pérez','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2025-02-06 09:11:40.337+01','2025-02-06 09:11:40.337+01');

-- rt.transit_driver_values
INSERT INTO rt.transit_driver_values (transit_id,driver_type_id,driver_param_id,value,visible,created_at,updated_at) VALUES
	 (3,1,1,'Valor Driver 1',true,'2025-02-06 09:12:19.984+01','2025-02-06 09:12:19.984+01');

-- rt.transit_vehicles
INSERT INTO rt.transit_vehicles (transit_id,vehicle_brand_id,vehicle_model_id,country_id,plate_number,date_registration,color,visible,created_at,updated_at) VALUES
	 (3,NULL,NULL,NULL,'LE0000AG','2025-02-06 10:13:21.153+01','AZUL',NULL,'2025-02-06 10:13:21.153+01','2025-02-06 10:13:21.153+01');

-- rt.transit_vehicle_values
INSERT INTO rt.transit_vehicle_values (transit_id,vehicle_type_id,vehicle_param_id,value,visible,created_at,updated_at) VALUES
	 (3,1,1,'Valor Vehicle 1',true,'2025-02-06 10:20:24.583+01','2025-02-06 10:20:24.583+01');
