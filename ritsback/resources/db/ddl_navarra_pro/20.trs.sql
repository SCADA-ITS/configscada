SET client_min_messages TO WARNING;

---------------------------------------------------------
--
-- DROP TABLES IF EXISTS
--
---------------------------------------------------------

-- SECTION 5 --
DROP TABLE IF EXISTS conf.element_transit_types;
DROP TABLE IF EXISTS conf.driver_values;
DROP TABLE IF EXISTS conf.drivers;
DROP TABLE IF EXISTS conf.vehicle_values;
DROP TABLE IF EXISTS conf.vehicles;
-- Section 4 --
DROP TABLE IF EXISTS static.infraction_params;
DROP TABLE IF EXISTS static.infraction_param_groups;
DROP TABLE IF EXISTS static.infraction_manager_types;
DROP TABLE IF EXISTS static.infraction_managers;
DROP TABLE IF EXISTS static.infraction_type_threshold_values;
DROP TABLE IF EXISTS static.infraction_types;
-- SECTION 3 --
DROP TABLE IF EXISTS static.transit_type_state_transitions;
DROP TABLE IF EXISTS static.transit_type_params;
DROP TABLE IF EXISTS static.transit_type_param_groups;
DROP TABLE IF EXISTS static.transit_types;
DROP TABLE IF EXISTS static.transit_state_options;
DROP TABLE IF EXISTS master.transit_states;
-- SECTION 2 --
DROP TABLE IF EXISTS static.driver_params;
DROP TABLE IF EXISTS static.driver_param_groups;
DROP TABLE IF EXISTS static.driver_types;
DROP TABLE IF EXISTS static.license_types;
DROP TABLE IF EXISTS static.vehicle_params;
DROP TABLE IF EXISTS static.vehicle_param_groups;
DROP TABLE IF EXISTS static.vehicle_classes;
-- SECTION 1 --
DROP TABLE IF EXISTS master.vehicle_models;
DROP TABLE IF EXISTS master.vehicle_types;
DROP TABLE IF EXISTS master.vehicle_brands;
DROP TABLE IF EXISTS master.localities;
DROP TABLE IF EXISTS master.regions;
DROP TABLE IF EXISTS master.states;
DROP TABLE IF EXISTS master.countries;

-- BEGIN SECTION 1

---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.countries
-- Descripción: Paises
-- Scope: master
--
	CREATE TABLE master.countries (
		country_id int8 NOT NULL,
		alpha_2_code varchar(2) UNIQUE NOT NULL,
		alpha_3_code varchar(3) UNIQUE NOT NULL,
		numeric_code varchar(3) UNIQUE NOT null,
		alias varchar(100) NOT NULL,
		label_alias varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_countries PRIMARY KEY (country_id)
	);
	
	ALTER TABLE master.countries SET TABLESPACE tbs_controltrafico_master;


-- 
-- Table: master.states
-- Descripción: Estados de paises
-- Scope: master
--
	CREATE TABLE master.states (
		country_id int8 NOT NULL,	
		state_id int8 NOT NULL,
		state_code varchar(10) UNIQUE NULL,
		alias varchar(100) NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_states PRIMARY KEY (country_id, state_id)
	);
	
	CREATE INDEX idx_states_countries ON master.states USING btree (country_id);
	
	ALTER TABLE master.states ADD CONSTRAINT fk_states_countries FOREIGN KEY (country_id) REFERENCES master.countries(country_id);
	
	ALTER TABLE master.states SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.regions
-- Descripción: Regiones o provincias
-- Scope: master
--
	CREATE TABLE master.regions (
		country_id int8 NOT NULL,
		state_id int8 NOT NULL,	
		region_id int8 NOT NULL,
		region_code varchar(10) UNIQUE NULL,
		alias varchar(100) NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_regions PRIMARY KEY (country_id, state_id, region_id)
	);
	
	CREATE INDEX idx_regions_states ON master.regions USING btree (country_id, state_id);
	
	ALTER TABLE master.regions ADD CONSTRAINT fk_regions_states FOREIGN KEY (country_id, state_id) REFERENCES master.states(country_id, state_id);
	
	ALTER TABLE master.regions SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.localities
-- Descripción: Localidades
-- Scope: master
--
	CREATE TABLE master.localities (
		country_id int8 NOT NULL,
		state_id int8 NOT NULL,	
		region_id int8 NOT NULL,
		locality_id int8 NOT NULL,
		locality_code varchar(10) UNIQUE NULL,
		alias varchar(100) NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_localities PRIMARY KEY (country_id, state_id, region_id, locality_id)
	);
	
	CREATE INDEX idx_localities_regions ON master.localities USING btree (country_id, state_id, region_id);
	
	ALTER TABLE master.localities ADD CONSTRAINT fk_localities_regions FOREIGN KEY (country_id, state_id, region_id) REFERENCES master.regions(country_id, state_id, region_id);
	
	ALTER TABLE master.localities SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.vehicle_brands
-- Descripción: Marcas de vehículos
-- Scope: master
--
	CREATE TABLE master.vehicle_brands (
		vehicle_brand_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicle_brands PRIMARY KEY (vehicle_brand_id)
	);
	
	ALTER TABLE master.vehicle_brands SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.vehicle_types
-- Descripción: Tipos de vehículos
-- Scope: master
--
	CREATE TABLE master.vehicle_types (
		vehicle_type_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicle_types PRIMARY KEY (vehicle_type_id)
	);
	
	ALTER TABLE master.vehicle_types SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.vehicle_models
-- Descripción: Modelos de vehículos
-- Scope: master
--
	CREATE TABLE master.vehicle_models (
		vehicle_brand_id int8 NOT NULL,
		vehicle_model_id int8 NOT NULL,
		vehicle_type_id int8 NULL,
		alias varchar(100) NOT NULL,
		year_production int4 null,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicle_models PRIMARY KEY (vehicle_brand_id, vehicle_model_id)
	);
	
	CREATE INDEX idx_vehicle_models_vehicle_brands ON master.vehicle_models USING btree (vehicle_brand_id);
	CREATE INDEX idx_vehicle_models_vehicle_types ON master.vehicle_models USING btree (vehicle_type_id);
		
	ALTER TABLE master.vehicle_models ADD CONSTRAINT fk_vehicle_models_vehicle_brands FOREIGN KEY (vehicle_brand_id) REFERENCES master.vehicle_brands(vehicle_brand_id);
	ALTER TABLE master.vehicle_models ADD CONSTRAINT fk_vehicle_models_vehicle_types FOREIGN KEY (vehicle_type_id) REFERENCES master.vehicle_types(vehicle_type_id);
	
	ALTER TABLE master.vehicle_models SET TABLESPACE tbs_controltrafico_master;
	
-- END SECTION 1

-- BEGIN SECTION 2

---------------------------------------------------------
--
-- TABLES FOR STATIC SCHEMA
--
---------------------------------------------------------

-- 
-- Table: static.vehicle_classes
-- Descripción: Clases de vehículos
-- Scope: static
--
	CREATE TABLE static.vehicle_classes (
		vehicle_class_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		label_alias varchar(100) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicle_classes PRIMARY KEY (vehicle_class_id)
	);
	
	ALTER TABLE static.vehicle_classes SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.vehicle_param_groups
-- Descripción: Grupos de parámetros de vehículos
-- Scope: static
--
	CREATE TABLE static.vehicle_param_groups (
		vehicle_param_group_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NOT NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicle_param_groups PRIMARY KEY (vehicle_param_group_id)
	);
	
	ALTER TABLE static.vehicle_param_groups SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.vehicle_params
-- Descripción: Parámetros de vehículos
-- Scope: static
--
	CREATE TABLE static.vehicle_params (
		vehicle_type_id int8 NOT NULL,	
		vehicle_param_id int8 NOT NULL,
		data_type_id int8 NOT NULL,
		vehicle_param_group_id int8 NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NOT NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicle_params PRIMARY KEY (vehicle_type_id, vehicle_param_id)
	);
	
	CREATE INDEX idx_vehicle_params_vehicle_types ON static.vehicle_params USING btree (vehicle_type_id);
	CREATE INDEX idx_vehicle_params_data_types ON static.vehicle_params USING btree (data_type_id);
	CREATE INDEX idx_vehicle_params_vehicle_param_groups ON static.vehicle_params USING btree (vehicle_param_group_id);
		
	ALTER TABLE static.vehicle_params ADD CONSTRAINT fk_vehicle_params_vehicle_types FOREIGN KEY (vehicle_type_id) REFERENCES master.vehicle_types(vehicle_type_id);
	ALTER TABLE static.vehicle_params ADD CONSTRAINT fk_vehicle_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE static.vehicle_params ADD CONSTRAINT fk_vehicle_params_vehicle_param_groups FOREIGN KEY (vehicle_param_group_id) REFERENCES static.vehicle_param_groups(vehicle_param_group_id);
	
	ALTER TABLE static.vehicle_params SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.license_types
-- Descripción: Tipos de permisos de circulación
-- Scope: static
--
	CREATE TABLE static.license_types (
		license_type_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_license_types PRIMARY KEY (license_type_id)
	);
	
	ALTER TABLE static.license_types SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.driver_types
-- Descripción: Tipos de conductores
-- Scope: static
--
	CREATE TABLE static.driver_types (
		driver_type_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_driver_types PRIMARY KEY (driver_type_id)
	);
	
	ALTER TABLE static.driver_types SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.driver_param_groups
-- Descripción: Grupos de parámetros de conductores
-- Scope: static
--
	CREATE TABLE static.driver_param_groups (
		driver_param_group_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_driver_param_groups PRIMARY KEY (driver_param_group_id)
	);
	
	ALTER TABLE static.driver_param_groups SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.vehicle_params
-- Descripción: Parámetros de vehículos
-- Scope: static
--
	CREATE TABLE static.driver_params (
		driver_type_id int8 NOT NULL,	
		driver_param_id int8 NOT NULL,
		data_type_id int8 NOT NULL,
		driver_param_group_id int8 NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_driver_params PRIMARY KEY (driver_type_id, driver_param_id)
	);
	
	CREATE INDEX idx_driver_params_driver_types ON static.driver_params USING btree (driver_type_id);
	CREATE INDEX idx_driver_params_data_types ON static.driver_params USING btree (data_type_id);
	CREATE INDEX idx_driver_params_driver_param_groups ON static.driver_params USING btree (driver_param_group_id);
		
	ALTER TABLE static.driver_params ADD CONSTRAINT fk_driver_params_driver_types FOREIGN KEY (driver_type_id) REFERENCES static.driver_types(driver_type_id);
	ALTER TABLE static.driver_params ADD CONSTRAINT fk_driver_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE static.driver_params ADD CONSTRAINT fk_driver_params_driver_param_groups FOREIGN KEY (driver_param_group_id) REFERENCES static.driver_param_groups(driver_param_group_id);
	
	ALTER TABLE static.driver_params SET TABLESPACE tbs_controltrafico_static;
	
-- END SECTION 2

-- BEGIN SECTION 3
	
-- 
-- Table: master.transit_states
-- Descripción: Estados de transitos
-- Scope: static
--
	CREATE TABLE master.transit_states (
		transit_state_id int8 NOT NULL,
		transit_state_code varchar(2) UNIQUE NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		user_transit_enable bool NULL, 
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_states PRIMARY KEY (transit_state_id)
	);
	
	ALTER TABLE master.transit_states SET TABLESPACE tbs_controltrafico_static;
	
-- 
-- Table: static.transit_state_options
-- Descripción: Opciones en estados de tipos de transitos
-- Scope: static
--
	CREATE TABLE static.transit_state_options (
		transit_state_id int8 NOT NULL,
		transit_state_option_id int8 NOT NULL,
		transit_state_option_code varchar(2) UNIQUE NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		user_transit_enable bool NULL, 
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_state_options PRIMARY KEY (transit_state_id, transit_state_option_id)
	);
	
	CREATE INDEX idx_transit_state_options_transit_states ON static.transit_state_options USING btree (transit_state_id);
		
	ALTER TABLE static.transit_state_options ADD CONSTRAINT fk_transit_state_options_transit_states FOREIGN KEY (transit_state_id) REFERENCES master.transit_states (transit_state_id);
	
	ALTER TABLE static.transit_state_options SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.transit_types
-- Descripción: Tipos de transitos
-- Scope: static
--
	CREATE TABLE static.transit_types (
		transit_type_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		archive_after_minutes int NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_types PRIMARY KEY (transit_type_id)
	);
	
	ALTER TABLE static.transit_types SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: conf.transit_param_groups
-- Descripción: Grupos de parámetros de transitos
-- Scope: static
--
	CREATE TABLE static.transit_type_param_groups (
		transit_type_param_group_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_type_param_groups PRIMARY KEY (transit_type_param_group_id)
	);
	
	ALTER TABLE static.transit_type_param_groups SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.transit_type_params
-- Descripción: Parámetros de tránsitos
-- Scope: static
--
	CREATE TABLE static.transit_type_params (
		transit_type_id int8 NOT NULL,	
		transit_type_param_id int8 NOT NULL,
		data_type_id int8 NOT NULL,
		transit_type_param_group_id int8 NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_type_params PRIMARY KEY (transit_type_id, transit_type_param_id)
	);
	
	CREATE INDEX idx_transit_type_params_transit_types ON static.transit_type_params USING btree (transit_type_id);
	CREATE INDEX idx_transit_type_params_data_types ON static.transit_type_params USING btree (data_type_id);
	CREATE INDEX idx_transit_type_params_transit_param_groups ON static.transit_type_params USING btree (transit_type_param_group_id);
		
	ALTER TABLE static.transit_type_params ADD CONSTRAINT fk_transit_type_params_transit_types FOREIGN KEY (transit_type_id) REFERENCES static.transit_types(transit_type_id);
	ALTER TABLE static.transit_type_params ADD CONSTRAINT fk_transit_type_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE static.transit_type_params ADD CONSTRAINT fk_transit_type_params_transit_type_param_groups FOREIGN KEY (transit_type_param_group_id) REFERENCES static.transit_type_param_groups(transit_type_param_group_id);
	
	ALTER TABLE static.transit_type_params SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.transit_type_state_transitions
-- Descripción: Compatibilidad de transiciones de estados de tipos de transitos
-- Scope: static
--
	CREATE TABLE static.transit_type_state_transitions (
		transit_type_id int8 NOT NULL,
		parent_transit_state_id int8 NULL,
		child_transit_state_id int8 NOT NULL,
		auto bool NULL,
		groovy_file varchar(100) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_transit_type_state_transitions PRIMARY KEY (transit_type_id, parent_transit_state_id, child_transit_state_id)
	);
	
	CREATE INDEX idx_transit_state_transitions_transit_types ON static.transit_state_transitions USING btree (transit_type_id);
	CREATE INDEX idx_transit_state_transitions_transit_states_1 ON static.transit_state_transitions USING btree (parent_transit_state_id);
	CREATE INDEX idx_transit_state_transitions_transit_states_2 ON static.transit_state_transitions USING btree (child_transit_state_id);
	
	ALTER TABLE static.transit_state_transitions ADD CONSTRAINT fk_transit_state_transitions_transit_types FOREIGN KEY (transit_type_id) REFERENCES static.transit_types(transit_type_id);
	ALTER TABLE static.transit_state_transitions ADD CONSTRAINT fk_transit_state_transitions_transit_states_1 FOREIGN KEY (parent_transit_state_id) REFERENCES master.transit_states(transit_state_id);
	ALTER TABLE static.transit_state_transitions ADD CONSTRAINT fk_transit_state_transitions_transit_states_2 FOREIGN KEY (child_transit_state_id) REFERENCES master.transit_states(transit_state_id);
	
	ALTER TABLE static.transit_type_state_transitions SET TABLESPACE tbs_controltrafico_static;

-- END SECTION 3

-- BEGIN SECTION 4


	ALTER TABLE static.transit_type_state_transitions SET TABLESPACE tbs_controltrafico_static;
	
-- 
-- Table: static.infraction_types
-- Descripción: Tipos de infracción
-- Scope: static
--
	CREATE TABLE static.infraction_types (
		infraction_type_id int8 NOT NULL,
		infraction_code varchar(10) UNIQUE NULL,
		infraction_original_code varchar(100) NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_infraction_types PRIMARY KEY (infraction_type_id)
	);
	

	ALTER TABLE static.infraction_types SET TABLESPACE tbs_controltrafico_static;
	
	
-- 
-- Table: static.infraction_type_threshold_values
-- Descripción: Configuración de umbrales de velocidad o distancia de tipos de infracción
-- Scope: static
--
	CREATE TABLE static.infraction_type_threshold_values (
		infraction_type_id int8 NOT NULL,
		infraction_type_threshold_value_id int8 NOT NULL,
		vehicle_class_id int8 NULL,
		speed_threshold_operation_id int8 NULL,
		speed_val1 float8 NULL,
		speed_val2 float8 NULL,
		distance_threshold_operation_id int8 NULL,
		distance_val1 float8 NULL,
		distance_val2 float8 NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_infraction_type_threshold_values PRIMARY KEY (infraction_type_id, infraction_type_threshold_value_id)
	);
	
	CREATE INDEX idx_infraction_type_threshold_values_infraction_types ON static.infraction_type_threshold_values USING btree (infraction_type_id);
	CREATE INDEX idx_infraction_type_threshold_values_vehicle_classes ON static.infraction_type_threshold_values USING btree (vehicle_class_id);
	CREATE INDEX idx_infraction_type_threshold_values_threshold_operations_1 ON static.infraction_type_threshold_values USING btree (speed_threshold_operation_id);
	CREATE INDEX idx_infraction_type_threshold_values_threshold_operations_2 ON static.infraction_type_threshold_values USING btree (distance_threshold_operation_id);
	
	ALTER TABLE static.infraction_type_threshold_values ADD CONSTRAINT fk_infraction_type_threshold_values_infraction_types FOREIGN KEY (infraction_type_id) REFERENCES static.infraction_types(infraction_type_id);
	ALTER TABLE static.infraction_type_threshold_values ADD CONSTRAINT fk_infraction_type_threshold_values_vehicle_classes FOREIGN KEY (vehicle_class_id) REFERENCES static.vehicle_classes(vehicle_class_id);
	ALTER TABLE static.infraction_type_threshold_values ADD CONSTRAINT fk_infraction_type_threshold_values_threshold_operations_1 FOREIGN KEY (speed_threshold_operation_id) REFERENCES master.threshold_operations(threshold_operation_id);
	ALTER TABLE static.infraction_type_threshold_values ADD CONSTRAINT fk_infraction_type_threshold_values_threshold_operations_2 FOREIGN KEY (distance_threshold_operation_id) REFERENCES master.threshold_operations(threshold_operation_id);

	ALTER TABLE static.infraction_type_threshold_values SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.infraction_managers
-- Descripción: Gestores de infracción
-- Scope: static
--
	CREATE TABLE static.infraction_managers (
		infraction_manager_id int8 NOT NULL,	
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_infraction_managers PRIMARY KEY (infraction_manager_id)
	);
	
	ALTER TABLE static.infraction_managers SET TABLESPACE tbs_controltrafico_static;
-- 
-- Table: static.infraction_manager_types
-- Descripción: Tipos de infracciones asociadas a un gestor de infracciones
-- Scope: static
--
	CREATE TABLE static.infraction_manager_types (
		infraction_manager_id int8 NOT NULL,
		infraction_type_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_infraction_manager_types PRIMARY KEY (infraction_manager_id, infraction_type_id)
	);
	
	CREATE INDEX idx_infraction_manager_types_infraction_managers ON static.infraction_manager_types USING btree (infraction_manager_id);
	CREATE INDEX idx_infraction_manager_types_infraction_types ON static.infraction_manager_types USING btree (infraction_type_id);
		
	ALTER TABLE static.infraction_manager_types ADD CONSTRAINT fk_infraction_manager_types_infraction_managers FOREIGN KEY (infraction_manager_id) REFERENCES static.infraction_managers(infraction_manager_id);
	ALTER TABLE static.infraction_manager_types ADD CONSTRAINT fk_infraction_manager_types_infraction_types FOREIGN KEY (infraction_type_id) REFERENCES static.infraction_types(infraction_type_id);

	ALTER TABLE static.infraction_manager_types SET TABLESPACE tbs_controltrafico_static;
	
-- 
-- Table: static.infraction_param_groups
-- Descripción: Grupos de parámetros de infracciones
-- Scope: static
--
	CREATE TABLE static.infraction_param_groups (
		infraction_param_group_id int8 NOT NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_infraction_param_groups PRIMARY KEY (infraction_param_group_id)
	);
	
	ALTER TABLE static.infraction_param_groups SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.infraction_params
-- Descripción: Parámetros de infracciones
-- Scope: static
--
	CREATE TABLE static.infraction_params (
		infraction_type_id int8 NOT NULL,	
		infraction_param_id int8 NOT NULL,
		data_type_id int8 NOT NULL,
		infraction_param_group_id int8 NULL,
		alias varchar(100) NOT NULL,
		description varchar(200) NULL,
		label_alias varchar(50) NOT NULL,
		label_description varchar(50) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_infraction_params PRIMARY KEY (infraction_type_id, infraction_param_id)
	);
	
	CREATE INDEX idx_infraction_params_infraction_types ON static.infraction_params USING btree (infraction_type_id);
	CREATE INDEX idx_infraction_params_data_types ON static.infraction_params USING btree (data_type_id);
	CREATE INDEX idx_infraction_params_infraction_param_groups ON static.infraction_params USING btree (infraction_param_group_id);
		
	ALTER TABLE static.infraction_params ADD CONSTRAINT idx_infraction_params_infraction_types FOREIGN KEY (infraction_type_id) REFERENCES static.infraction_types(infraction_type_id);
	ALTER TABLE static.infraction_params ADD CONSTRAINT idx_infraction_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE static.infraction_params ADD CONSTRAINT idx_infraction_params_infraction_param_groups FOREIGN KEY (infraction_param_group_id) REFERENCES static.infraction_param_groups(infraction_param_group_id);
	
	ALTER TABLE static.infraction_params SET TABLESPACE tbs_controltrafico_static;

-- END SECTION 4

-- BEGIN SECTION 5

---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------
	
-- 
-- Table: conf.vehicles
-- Descripción: Vehículos
-- Scope: conf
--
	CREATE TABLE conf.vehicles (
		vehicle_type_id int8 NOT NULL,
		vehicle_id int8 NOT NULL,
		vehicle_model_id int8 NOT NULL,
		vehicle_class_id int8 NULL,
		country_id int8 NULL,
		plate_number varchar(20) NULL,
		date_registration timestamptz NULL,
		color varchar(100) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicles PRIMARY KEY (vehicle_type_id, vehicle_id)
	);
	
	CREATE INDEX idx_vehicles_vehicle_types ON conf.vehicles USING btree (vehicle_type_id);
	CREATE INDEX idx_vehicles_vehicle_classes ON conf.vehicles USING btree (vehicle_class_id);
	
	ALTER TABLE conf.vehicles ADD CONSTRAINT fk_vehicles_vehicle_types FOREIGN KEY (vehicle_type_id) REFERENCES master.vehicle_types(vehicle_type_id);
	ALTER TABLE conf.vehicles ADD CONSTRAINT fk_vehicles_vehicle_classes FOREIGN KEY (vehicle_class_id) REFERENCES static.vehicle_classes(vehicle_class_id);
	
	ALTER TABLE conf.vehicles SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.vehicle_values
-- Descripción: Valores de parámetros de vehículos
-- Scope: conf
--
	CREATE TABLE conf.vehicle_values (
		vehicle_type_id int8 NOT NULL,
		vehicle_id int8 NOT NULL,
		vehicle_param_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vehicle_values PRIMARY KEY (vehicle_type_id, vehicle_param_id, vehicle_id)
	);
	
	CREATE INDEX idx_vehicle_values_vehicle_params ON conf.vehicle_values USING btree (vehicle_type_id, vehicle_param_id);
	CREATE INDEX idx_vehicle_values_vehicles ON conf.vehicle_values USING btree (vehicle_type_id, vehicle_id);
		
	ALTER TABLE conf.vehicle_values ADD CONSTRAINT fk_vehicle_values_vehicle_params FOREIGN KEY (vehicle_type_id, vehicle_param_id) REFERENCES static.vehicle_params(vehicle_type_id, vehicle_param_id);
	ALTER TABLE conf.vehicle_values ADD CONSTRAINT fk_vehicle_values_vehicles FOREIGN KEY (vehicle_type_id, vehicle_id) REFERENCES conf.vehicles(vehicle_type_id, vehicle_id);
	
	ALTER TABLE conf.vehicle_values SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.drivers
-- Descripción: conductores
-- Scope: conf
--
	CREATE TABLE conf.drivers (
		driver_type_id int8 NOT NULL,
		driver_id int8 NOT NULL,
		license_type_id int8 NOT NULL,
		license_value varchar(200) NULL,
		license_issue_date timestamptz NULL,
		license_expire_date timestamptz NULL,
		name varchar(100) NOT NULL,
		surname varchar(300) NULL,
		gender varchar(1) NULL,
		date_of_birth timestamptz NULL,
		country_id int8 NOT NULL,
		state_id int8 NOT NULL,
		region_id int8 NOT NULL,
		locality_id int8 NOT NULL,
		address varchar(1000) NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_drivers PRIMARY KEY (driver_type_id, driver_id)
	);
	
	CREATE INDEX idx_drivers_driver_types ON conf.drivers USING btree (driver_type_id);
	CREATE INDEX idx_drivers_license_types ON conf.drivers USING btree (license_type_id);
	CREATE INDEX idx_drivers_countries ON conf.drivers USING btree (country_id);
	CREATE INDEX idx_drivers_states ON conf.drivers USING btree (state_id);
	CREATE INDEX idx_drivers_regions ON conf.drivers USING btree (region_id);
	CREATE INDEX idx_drivers_localities ON conf.drivers USING btree (locality_id);
		
	ALTER TABLE conf.drivers ADD CONSTRAINT fk_drivers_driver_types FOREIGN KEY (driver_type_id) REFERENCES static.driver_types(driver_type_id);
	ALTER TABLE conf.drivers ADD CONSTRAINT fk_drivers_license_types FOREIGN KEY (license_type_id) REFERENCES static.license_types(license_type_id);
	ALTER TABLE conf.drivers ADD CONSTRAINT fk_drivers_countries FOREIGN KEY (country_id) REFERENCES master.countries(country_id);
	ALTER TABLE conf.drivers ADD CONSTRAINT fk_drivers_states FOREIGN KEY (country_id, state_id) REFERENCES master.states(country_id, state_id);
	ALTER TABLE conf.drivers ADD CONSTRAINT fk_drivers_regions FOREIGN KEY (country_id, state_id, region_id) REFERENCES master.regions(country_id, state_id, region_id);
	ALTER TABLE conf.drivers ADD CONSTRAINT fk_drivers_localities FOREIGN KEY (country_id, state_id, region_id, locality_id) REFERENCES master.localities(country_id, state_id, region_id, locality_id);
	
	ALTER TABLE conf.drivers SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.driver_values
-- Descripción: Valores de parámetros de conductores
-- Scope: conf
--
	CREATE TABLE conf.driver_values (
		driver_type_id int8 NOT NULL,
		driver_param_id int8 NOT NULL,
		driver_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_driver_values PRIMARY KEY (driver_type_id, driver_param_id, driver_id)
	);
	
	CREATE INDEX idx_driver_values_driver_params ON conf.driver_values USING btree (driver_type_id, driver_param_id);
	CREATE INDEX idx_driver_values_drivers ON conf.driver_values USING btree (driver_type_id, driver_id);
		
	ALTER TABLE conf.driver_values ADD CONSTRAINT fk_driver_values_driver_params FOREIGN KEY (driver_type_id, driver_param_id) REFERENCES static.driver_params(driver_type_id, driver_param_id);
	ALTER TABLE conf.driver_values ADD CONSTRAINT fk_driver_values_drivers FOREIGN KEY (driver_type_id, driver_id) REFERENCES conf.drivers(driver_type_id, driver_id);
	
	ALTER TABLE conf.driver_values SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.element_transit_types
-- Descripción: Asociación de elemento para la generación de transitos
-- Scope: conf
--
	CREATE TABLE conf.element_transit_types (
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		transit_type_id int8 NOT NULL,
		infraction_manager_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_transit_types PRIMARY KEY (element_type_id, element_id, transit_type_id)
	);
	
	CREATE INDEX idx_element_transit_types_elements ON conf.element_transit_types USING btree (element_type_id, element_id);
	CREATE INDEX idx_element_transit_types_transit_types ON conf.element_transit_types USING btree (transit_type_id);
	CREATE INDEX idx_element_transit_types_infraction_managers ON conf.element_transit_types USING btree (infraction_manager_id);
		
	ALTER TABLE conf.element_transit_types ADD CONSTRAINT idx_element_transit_types_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.element_transit_types ADD CONSTRAINT idx_element_transit_types_transit_types FOREIGN KEY (transit_type_id) REFERENCES static.transit_types(transit_type_id);
	ALTER TABLE conf.element_transit_types ADD CONSTRAINT idx_element_transit_types_infraction_managers FOREIGN KEY (infraction_manager_id) REFERENCES static.infraction_managers(infraction_manager_id);
	
	ALTER TABLE conf.element_transit_types SET TABLESPACE tbs_controltrafico_conf;
	
	
