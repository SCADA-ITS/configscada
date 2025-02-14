SET client_min_messages TO WARNING;

---------------------------------------------------------
--
-- DROP TABLES IF EXISTS
--
---------------------------------------------------------

DROP TABLE IF EXISTS rt.transit_logs;
DROP TABLE IF EXISTS rt.transit_attachments;
DROP TABLE IF EXISTS rt.transit_images;
DROP TABLE IF EXISTS rt.transit_vehicle_values;
DROP TABLE IF EXISTS rt.transit_driver_values;
DROP TABLE IF EXISTS rt.transit_values;
DROP TABLE IF EXISTS rt.transits;

-- 
-- Table: rt.transits
-- Descripción: Registro de tránsitos
-- Scope: rt
--
	CREATE TABLE rt.transits (
		-- Transit info
		transit_id int8 NOT null,
		transit_code varchar(200) NULL,
		transit_type_id int8 NOT NULL,
		dossier_code varchar(200) NULL,
		remittance_code varchar(200) NULL,
		installation_code varchar(200) NULL,
		detection_point_code varchar(200) NULL,
		date_transit timestamptz NOT NULL,
		transit_type_state_id int8 NOT NULL,
		transit_type_state_option_id int8 NULL,
		location_id int8 NOT NULL,
		lane_number int4 NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		certificate_valid_date timestamptz NULL,
		certificate_path varchar(500) NULL,
		infraction_type_id int8 NULL,
		infraction_code varchar(10) NULL,
		speed float8 NULL,
		distance float8 NULL,
		assigned_user_id int8 NULL,
		towards varchar(1000) NULL,
		address varchar(1000) NULL,
		locality varchar(100) NULL,
		road varchar(100) NULL,
		pk varchar(50) NULL,
		direction varchar(10) NULL,
		-- Vehicle info		
		vehicle_type_id int8 NULL,
		vehicle_id int8 NULL,
		vehicle_brand_id int8 NULL,
		vehicle_model_id int8 NULL,
		vehicle_class_id int8 NULL,
		vehicle_country_id int8 NULL,
		vehicle_plate_number varchar(20) NULL,
		vehicle_direction bool NULL,
		vehicle_date_registration timestamptz NULL,
		vehicle_color varchar(100) NULL,
		-- Driver info
		driver_type_id int8 NULL,
		driver_id int8 NULL,
		driver_license_type_id int8 NULL,
		driver_license_value varchar(200) NULL,
		driver_license_issue_date timestamptz NULL,
		driver_license_expire_date timestamptz NULL,
		driver_name varchar(100) NOT NULL,
		driver_surname varchar(300) NULL,
		driver_gender varchar(1) NULL,
		driver_date_of_birth timestamptz NULL,
		driver_country_id int8 NULL,
		driver_state_id int8 NULL,
		driver_region_id int8 NULL,
		driver_locality_id int8 NULL,
		driver_address varchar(1000) NULL,
		-- Others		
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transits PRIMARY KEY (transit_id)
	);

	-- Transit indexes
	CREATE INDEX idx_transits_transit_types ON rt.transits USING btree (transit_type_id);
	CREATE INDEX idx_transits_transit_type_states ON rt.transits USING btree (transit_type_state_id);
	CREATE INDEX idx_transits_transit_type_state_options ON rt.transits USING btree (transit_type_state_id, transit_type_state_option_id);
	CREATE INDEX idx_transits_transit_locations ON rt.transits USING btree (location_id);
	CREATE INDEX idx_transits_transit_elements ON rt.transits USING btree (element_type_id, element_id);
	CREATE INDEX idx_transits_transit_infraction_types ON rt.transits USING btree (infraction_type_id);
	CREATE INDEX idx_transits_transit_users ON rt.transits USING btree (assigned_user_id);
	CREATE INDEX idx_transits_element_transit_types ON rt.transits USING btree (element_type_id, element_id, transit_type_id);
	
	-- Vehicle indexes
	CREATE INDEX idx_transits_vehicle_types ON rt.transits USING btree (vehicle_type_id);
	CREATE INDEX idx_transits_vehicles ON rt.transits USING btree (vehicle_type_id, vehicle_id);
	CREATE INDEX idx_transits_vehicle_brands ON rt.transits USING btree (vehicle_brand_id);
	CREATE INDEX idx_transits_vehicle_models ON rt.transits USING btree (vehicle_brand_id, vehicle_model_id);
	CREATE INDEX idx_transits_vehicle_classes ON rt.transits USING btree (vehicle_class_id);
	CREATE INDEX idx_transits_vehicle_countries ON rt.transits USING btree (vehicle_country_id);
	
	-- Driver indexes
	CREATE INDEX idx_transits_driver_types ON rt.transits USING btree (driver_type_id);
	CREATE INDEX idx_transits_drivers ON rt.transits USING btree (driver_type_id, driver_id);
	CREATE INDEX idx_transits_driver_license_types ON rt.transits USING btree (driver_license_type_id);
	CREATE INDEX idx_transits_driver_countries ON rt.transits USING btree (driver_country_id);
	CREATE INDEX idx_transits_driver_states ON rt.transits USING btree (driver_state_id);
	CREATE INDEX idx_transits_driver_regions ON rt.transits USING btree (driver_region_id);
	CREATE INDEX idx_transits_driver_localities ON rt.transits USING btree (driver_locality_id);

	-- Transit relationships
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_transit_type_states FOREIGN KEY (transit_type_state_id) REFERENCES static.transit_type_states(transit_type_state_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_transit_type_state_options FOREIGN KEY (transit_type_state_id, transit_type_state_option_id) REFERENCES static.transit_type_state_options(transit_type_state_id, transit_type_state_option_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_transit_locations FOREIGN KEY (location_id) REFERENCES conf.locations(location_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_transit_infraction_types FOREIGN KEY (infraction_type_id) REFERENCES static.infraction_types(infraction_type_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_transit_users FOREIGN KEY (assigned_user_id) REFERENCES conf.users(user_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_element_transit_types FOREIGN KEY (element_type_id, element_id, transit_type_id) REFERENCES conf.element_transit_types(element_type_id, element_id, transit_type_id);
	
	-- Vehicle relationships
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_vehicle_types FOREIGN KEY (vehicle_type_id) REFERENCES master.vehicle_types(vehicle_type_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_vehicles FOREIGN KEY (vehicle_type_id, vehicle_id) REFERENCES conf.vehicles(vehicle_type_id, vehicle_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_vehicle_brands FOREIGN KEY (driver_license_type_id) REFERENCES master.vehicle_brands(vehicle_brand_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_vehicle_models FOREIGN KEY (vehicle_brand_id, vehicle_model_id) REFERENCES master.vehicle_models(vehicle_brand_id, vehicle_model_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_vehicle_classes FOREIGN KEY (vehicle_class_id) REFERENCES static.vehicle_classes(vehicle_class_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_vehicle_countries FOREIGN KEY (vehicle_country_id) REFERENCES master.countries(country_id);
	
	-- Driver relationships
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_driver_types FOREIGN KEY (driver_type_id) REFERENCES static.driver_types(driver_type_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_drivers FOREIGN KEY (driver_type_id, driver_id) REFERENCES conf.drivers(driver_type_id, driver_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_driver_license_types FOREIGN KEY (driver_license_type_id) REFERENCES static.license_types(license_type_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_driver_countries FOREIGN KEY (driver_country_id) REFERENCES master.countries(country_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_driver_states FOREIGN KEY (driver_country_id, driver_state_id) REFERENCES master.states(country_id, state_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_driver_regions FOREIGN KEY (driver_country_id, driver_state_id, driver_region_id) REFERENCES master.regions(country_id, state_id, region_id);
	ALTER TABLE rt.transits ADD CONSTRAINT fk_transits_driver_localities FOREIGN KEY (driver_country_id, driver_state_id, driver_region_id, driver_locality_id) REFERENCES master.localities(country_id, state_id, region_id, locality_id);
	
	ALTER TABLE rt.transits SET TABLESPACE tbl_rt;
	
-- 
-- Table: rt.transit_values
-- Descripción: Parámetros asociados al tránsito
-- Scope: rt
--
	CREATE TABLE rt.transit_values (
		transit_id int8 NOT NULL,
		transit_type_id int8 NOT NULL,
		transit_type_param_id int8 NOT NULL,
		value varchar NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_values PRIMARY KEY (transit_id, transit_type_id, transit_type_param_id)
	);
	
	CREATE INDEX idx_transit_values_transit_types ON rt.transit_values USING btree (transit_id);
	CREATE INDEX idx_transit_values_transit_type_params ON rt.transit_values USING btree (transit_type_id, transit_type_param_id);
	
	ALTER TABLE rt.transit_values ADD CONSTRAINT fk_transit_values_transit_types FOREIGN KEY (transit_id) REFERENCES rt.transits(transit_id);
	ALTER TABLE rt.transit_values ADD CONSTRAINT fk_transit_values_transit_type_params FOREIGN KEY (transit_type_id, transit_type_param_id) REFERENCES static.transit_type_params(transit_type_id, transit_type_param_id);
	
	ALTER TABLE rt.transit_values SET TABLESPACE tbl_rt;

-- 
-- Table: rt.transit_driver_values
-- Descripción: Parámetros del conductor asociado al tránsito
-- Scope: rt
--
	CREATE TABLE rt.transit_driver_values (
		transit_id int8 NOT NULL,
		driver_type_id int8 NOT NULL,
		driver_param_id int8 NOT NULL,
		value varchar NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_driver_values PRIMARY KEY (transit_id, driver_type_id, driver_param_id)
	);
	
	CREATE INDEX idx_transit_driver_values_transit_drivers ON rt.transit_driver_values USING btree (transit_id);
	CREATE INDEX idx_transit_driver_values_driver_params ON rt.transit_driver_values USING btree (driver_type_id, driver_param_id);
	
	ALTER TABLE rt.transit_driver_values ADD CONSTRAINT fk_transit_driver_values_transit_drivers FOREIGN KEY (transit_id) REFERENCES rt.transits(transit_id);
	ALTER TABLE rt.transit_driver_values ADD CONSTRAINT fk_transit_driver_values_driver_params FOREIGN KEY (driver_type_id, driver_param_id) REFERENCES static.driver_params(driver_type_id, driver_param_id);
	
	ALTER TABLE rt.transit_driver_values SET TABLESPACE tbl_rt;
	
-- 
-- Table: rt.transit_vehicle_values
-- Descripción: Parámetros del vehículo asociado al tránsito
-- Scope: rt
--
	CREATE TABLE rt.transit_vehicle_values (
		transit_id int8 NOT NULL,
		vehicle_type_id int8 NOT NULL,	
		vehicle_param_id int8 NOT NULL,
		value varchar NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_vehicle_values PRIMARY KEY (transit_id, vehicle_type_id, vehicle_param_id)
	);
	
	CREATE INDEX idx_transit_vehicle_values_transit_vehicles ON rt.transit_vehicle_values USING btree (transit_id);
	CREATE INDEX idx_transit_driver_values_vehicle_params ON rt.transit_vehicle_values USING btree (vehicle_type_id, vehicle_param_id);
	
	ALTER TABLE rt.transit_vehicle_values ADD CONSTRAINT fk_transit_vehicle_values_transit_vehicles FOREIGN KEY (transit_id) REFERENCES rt.transits(transit_id);
	ALTER TABLE rt.transit_vehicle_values ADD CONSTRAINT fk_transit_driver_values_vehicle_params FOREIGN KEY (vehicle_type_id, vehicle_param_id) REFERENCES static.vehicle_params(vehicle_type_id, vehicle_param_id);
	
	ALTER TABLE rt.transit_vehicle_values SET TABLESPACE tbl_rt;
	
-- 
-- Table: rt.transit_images
-- Descripción: Imágenes asociadas al transito
-- Scope: rt
--
	CREATE TABLE rt.transit_images (
		transit_image_id int8 NOT NULL,
		transit_id int8 NOT NULL,
		path varchar(400) NOT NULL,
		read_only bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_images PRIMARY KEY (transit_image_id)
	);
	
	CREATE INDEX idx_transit_images_transits ON rt.transit_images USING btree (transit_id);
	
	ALTER TABLE rt.transit_images ADD CONSTRAINT fk_transit_images_transits FOREIGN KEY (transit_id) REFERENCES rt.transits(transit_id);
	
	ALTER TABLE rt.transit_images SET TABLESPACE tbl_rt;
	
-- 
-- Table: rt.transit_attachments
-- Descripción: Adjuntos asociados al transito
-- Scope: rt
--
	CREATE TABLE rt.transit_attachments (
		transit_attachment_id int8 NOT NULL,
		transit_id int8 NOT NULL,
		path varchar(400) NOT NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_attachments PRIMARY KEY (transit_attachment_id)
	);
	
	CREATE INDEX idx_transit_attachments_transits ON rt.transit_attachments USING btree (transit_id);
	
	ALTER TABLE rt.transit_attachments ADD CONSTRAINT fk_transit_attachments_transits FOREIGN KEY (transit_id) REFERENCES rt.transits(transit_id);
	
	ALTER TABLE rt.transit_attachments SET TABLESPACE tbl_rt;
	
-- 
-- Table: rt.transit_logs
-- Descripción: Trazas asociadas al transito
-- Scope: rt
--
	CREATE TABLE rt.transit_logs (
		transit_log_id int8 NOT NULL,
		transit_id int8 NOT NULL,
		assigned_user_id int8 NOT NULL,
		transit_type_state_id int8 NOT NULL,
		transit_type_state_option_id int8 NULL,
		timestamp_log timestamptz NOT NULL,
		log_type varchar(100) NOT NULL,
		message varchar(1000) NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_logs PRIMARY KEY (transit_log_id)
	);
	
	CREATE INDEX idx_transit_logs_timestamp_log ON rt.transit_logs USING btree (timestamp_log);
	CREATE INDEX idx_transit_logs_users ON rt.transit_logs USING btree (assigned_user_id);
	CREATE INDEX idx_transit_logs_transit_type_states ON rt.transit_logs USING btree (transit_type_state_id);
	CREATE INDEX idx_transit_logs_transit_type_state_options ON rt.transit_logs USING btree (transit_type_state_id, transit_type_state_option_id);
	
	ALTER TABLE rt.transit_logs ADD CONSTRAINT fk_transit_logs_users FOREIGN KEY (assigned_user_id) REFERENCES conf.users(user_id);
	ALTER TABLE rt.transit_logs ADD CONSTRAINT fk_transit_logs_transit_type_states FOREIGN KEY (transit_type_state_id) REFERENCES static.transit_type_states(transit_type_state_id);
	ALTER TABLE rt.transit_logs ADD CONSTRAINT fk_transit_logs_transit_type_state_options FOREIGN KEY (transit_type_state_id, transit_type_state_option_id) REFERENCES static.transit_type_state_options(transit_type_state_id, transit_type_state_option_id);
	
	ALTER TABLE rt.transit_logs SET TABLESPACE tbl_rt;
	