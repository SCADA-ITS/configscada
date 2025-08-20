--DROP TABLE IF EXISTS conf.modbus_element_element_type_params
--DROP TABLE IF EXISTS conf.modbus_element_element_type_states
--DROP TABLE IF EXISTS conf.element_values;
--DROP TABLE IF EXISTS conf.element_hierarchies;
--DROP TABLE IF EXISTS conf.elements;

--DROP TABLE IF EXISTS conf.road_stretchs;
--DROP TABLE IF EXISTS conf.spath_stretchs;
--DROP TABLE IF EXISTS conf.stretch_locations;
--DROP TABLE IF EXISTS conf.stretchs;
--DROP TABLE IF EXISTS conf.roads;
--DROP TABLE IF EXISTS conf.spaths;
--DROP TABLE IF EXISTS conf.locations;

--DROP TABLE IF EXISTS master.element_subtype_active_params;
--DROP TABLE IF EXISTS master.element_type_params;
--DROP TABLE IF EXISTS master.element_type_param_unit;
--DROP TABLE IF EXISTS master.threshold_operations;
--DROP TABLE IF EXISTS master.param_types;
--DROP TABLE IF EXISTS master.data_types;
--DROP TABLE IF EXISTS master.element_type_states;
--DROP TABLE IF EXISTS master.element_subtypes;
--DROP TABLE IF EXISTS master.element_types;
--DROP TABLE IF EXISTS master.road_impacts;
--DROP TABLE IF EXISTS master.road_types;
--DROP TABLE IF EXISTS master.stretch_types;
--DROP TABLE IF EXISTS master.location_types;
--DROP TABLE IF EXISTS master.log_type_params;
--DROP TABLE IF EXISTS master.log_types;
--DROP TABLE IF EXISTS master.element_hierarchies_allowed;



---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.threshold_operations
-- Descripción: Operaciones sobre umbrales definidas en el sistema
-- Scope: master
--
	CREATE TABLE master.threshold_operations (
		threshold_operation_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_threshold_operations PRIMARY KEY (threshold_operation_id)
	);
	
	ALTER TABLE master.threshold_operations SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.element_type_param_unit
-- Descripción: Librería de union parámetros para un determinado element_typees del sistema
-- Scope: master
--

	CREATE TABLE master.element_type_param_unit (
		element_type_param_unit_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		editable bool NULL DEFAULT false,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_type_param_units PRIMARY KEY (element_type_param_unit_id)
	);
	
	ALTER TABLE master.element_type_param_unit SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.location_types
-- Descripción: Tipo de localizaciones
-- Scope: master
--
	CREATE TABLE master.location_types (
		location_type_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_location_types PRIMARY KEY (location_type_id)
	);

	ALTER TABLE master.location_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.stretch_types
-- Descripción: Tipo de tramos
-- Scope: master
--
	CREATE TABLE master.stretch_types (
		stretch_type_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_stretch_types PRIMARY KEY (stretch_type_id)
	);

	ALTER TABLE master.stretch_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.road_types
-- Descripción: Tipo de carreteras
-- Scope: master
--
	CREATE TABLE master.road_types (
		road_type_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_road_types PRIMARY KEY (road_type_id)
	);

	ALTER TABLE master.road_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.road_impacts
-- Descripción: Afecciones de la carretera disponibles
-- Scope: master
--
	CREATE TABLE master.road_impacts (
		road_impact_id int8 NOT NULL,
	    alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_road_impacts PRIMARY KEY (road_impact_id)
	);
	
	ALTER TABLE master.road_impacts SET TABLESPACE tbs_controltrafico_master;
-- 
-- Table: master.element_groups
-- Descripción: Librería de element_groups genéricos en el sistema
-- Scope: master
--
	CREATE TABLE master.element_groups (
		element_group_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_groups  PRIMARY KEY (element_group_id)
	);
	
	ALTER TABLE master.element_groups SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.element_types
-- Descripción: Librería de element_typees genéricos en el sistema
-- Scope: master
--
	CREATE TABLE master.element_types (
		element_type_id int8 NOT NULL,
		element_group_id int8 NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		initial_element_type_state_id int8 NOT NULL,
		connected_element_type_state_id int8 NULL,
		disconnected_element_type_state_id int8 NULL,
		maintenance_element_type_state_id int8 NULL,
		is_logic bool NULL,
		check_if_connected bool NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_types PRIMARY KEY (element_type_id)
	);
	
	ALTER TABLE master.element_types ADD CONSTRAINT fk_element_types_element_groups FOREIGN KEY (element_group_id) REFERENCES master.element_groups(element_group_id);

	ALTER TABLE master.element_types SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.element_type_states
-- Descripción: Tipos de estados que puede tener un determinado element_typee del sistema
-- Scope: master
--
	CREATE TABLE master.element_type_states (
		element_type_id int8 NOT NULL,
		element_type_state_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_type_states PRIMARY KEY (element_type_id, element_type_state_id)
	);
	
	CREATE INDEX idx_element_type_states_element_type_state_id ON master.element_type_states USING btree (element_type_state_id);

	ALTER TABLE master.element_type_states ADD CONSTRAINT fk_element_type_states_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);
	
	ALTER TABLE master.element_type_states SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.element_hierarchies_allowed
-- Descripción: Protección de jerarquía entre elementos
-- Scope: master
--
	CREATE TABLE master.element_hierarchies_allowed (
		parent_element_type_id int8 NOT NULL,
		child_element_type_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT null,
		CONSTRAINT pk_element_hierarchies_allowed PRIMARY KEY (parent_element_type_id, child_element_type_id)
	);
	
	ALTER TABLE master.element_hierarchies_allowed SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.data_types
-- Descripción: Tipos de datos primitivos
-- Scope: master
--
	CREATE TABLE master.data_types (
		data_type_id int8 NOT NULL,
		alias varchar NULL,
		description varchar null,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_data_types PRIMARY KEY (data_type_id)
	);
	
	ALTER TABLE master.data_types SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.param_types
-- Descripción: Tipos de parámetros del sistema
-- Scope: master
--
	CREATE TABLE master.param_types (
		param_type_id int NOT NULL,
		alias varchar null,
		description varchar null,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_param_types PRIMARY KEY (param_type_id)
	);
	
	ALTER TABLE master.param_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.element_type_params
-- Descripción: Librería de parámetros para un determinado element_typees del sistema
-- Scope: master
--
	CREATE TABLE master.element_type_params (
		element_type_id int8 NOT NULL,
		param_type_id int8 NOT NULL,	
		element_type_param_id int8 NOT NULL,
		default_value varchar NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
	    data_type_id int8 NOT NULL,
		threshold_1 varchar NULL,
		threshold_2 varchar NULL,
		element_type_param_unit_id int8 NULL,
		durable bool NULL,
		scaling_factor float8 NULL,
		offset_value float8 NULL,
		enabled bool NULL,
		visible bool NULL,
		editable bool NULL DEFAULT false,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_type_params PRIMARY KEY (element_type_param_id, element_type_id, param_type_id)
	);

	CREATE INDEX idx_element_type_params_data_type_id ON master.element_type_params USING btree (data_type_id);

	ALTER TABLE master.element_type_params ADD CONSTRAINT fk_element_type_params_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);
	ALTER TABLE master.element_type_params ADD CONSTRAINT fk_element_type_params_param_types FOREIGN KEY (param_type_id) REFERENCES master.param_types(param_type_id);
	ALTER TABLE master.element_type_params ADD CONSTRAINT fk_element_type_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE master.element_type_params ADD CONSTRAINT fk_element_type_params_param_unit FOREIGN KEY (element_type_param_unit_id) REFERENCES master.element_type_param_unit(element_type_param_unit_id);

	
	ALTER TABLE master.element_type_params SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.element_value_states
-- Descripción: Estados del valor del parámetro
-- Scope: master
--
	CREATE TABLE master.element_value_states (
		element_value_state_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_value_states PRIMARY KEY (element_value_state_id)
	);
	
	ALTER TABLE master.element_value_states SET TABLESPACE tbs_controltrafico_master;

	
	
-- 
-- Table: master.element_subtype
-- Descripción: Librería de element_subtype genéricos en el sistema
-- Scope: master
--
	CREATE TABLE master.element_subtypes  (
		element_subtype_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_subtypes PRIMARY KEY (element_subtype_id, element_type_id)
	);
	
	ALTER TABLE master.element_subtypes ADD CONSTRAINT fk_element_subtypes_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);

	ALTER TABLE master.element_subtypes SET TABLESPACE tbs_controltrafico_master;
	
	
-- 
-- Table: master.element_subtype_active_param
-- Descripción: Librería de parámetros para un determinado element_subtype_id del sistema
-- Scope: master
--
	CREATE TABLE master.element_subtype_active_params (
		element_subtype_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		param_type_id int8 NOT NULL,	
		element_type_param_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_subtype_active_params PRIMARY KEY (element_subtype_id, element_type_id, element_type_param_id,  param_type_id)
	);
	
	ALTER TABLE master.element_subtype_active_params ADD CONSTRAINT fk_element_subtype_active_params_element_subtypes FOREIGN KEY (element_subtype_id, element_type_id) REFERENCES master.element_subtypes(element_subtype_id, element_type_id);
	ALTER TABLE master.element_subtype_active_params ADD CONSTRAINT fk_element_subtype_active_params_element_type_params FOREIGN KEY (element_type_param_id, element_type_id, param_type_id) REFERENCES master.element_type_params(element_type_param_id, element_type_id, param_type_id);

	
---------------------------------------------------------
--
-- TABLES FOR STATIC SCHEMA
--
---------------------------------------------------------	
-- 
-- Table: static.management_areas 
-- Descripción: 
-- Scope: static
--
	CREATE TABLE static.management_areas (
		management_area_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_management_areas PRIMARY KEY (management_area_id)
	);

	ALTER TABLE static.management_areas SET TABLESPACE tbs_controltrafico_static;	
	
	
---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------

-- 
-- Table: conf.locations
-- Descripción: Localizaciones
-- Scope: conf
--
	CREATE TABLE conf.locations (
		location_id int8 NOT NULL,
		location_type_id int8 NOT NULL,
		alias varchar NOT NULL,
		lane int4 NULL,
		description varchar NULL,
		lat float8 NULL,
		lon float8 NULL,
		coordinates varchar NULL,
		auto_route boolean NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_locations PRIMARY KEY (location_id)
	);
	
	CREATE INDEX idx_locations_location_types ON conf.locations USING btree (location_type_id);
	
	ALTER TABLE conf.locations ADD CONSTRAINT fk_locations_location_types FOREIGN KEY (location_type_id) REFERENCES master.location_types(location_type_id);
	
	ALTER TABLE conf.locations SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.spaths
-- Descripción: Tramos relacionados
-- Scope: conf
--
	CREATE TABLE conf.spaths (
		spath_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_spaths PRIMARY KEY (spath_id)
	);
	
	ALTER TABLE conf.spaths SET TABLESPACE tbs_controltrafico_conf;

	
-- 
-- Table: conf.roads
-- Descripción: Carreteras
-- Scope: conf
--
	CREATE TABLE conf.roads (
		road_id int8 NOT NULL,
		road_type_id int8 NULL,
		alias varchar NULL,
		description varchar NULL,
		management_area_id int8 NULL,
		CONSTRAINT pk_roads PRIMARY KEY (road_id)
	);
	
	CREATE INDEX idx_roads_road_types ON conf.roads USING btree (road_type_id);
	
	ALTER TABLE conf.roads ADD CONSTRAINT fk_roads_road_types FOREIGN KEY (road_type_id) REFERENCES master.road_types(road_type_id);
	ALTER TABLE conf.roads ADD CONSTRAINT fk_roads_management_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);

	
	ALTER TABLE conf.roads SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.stretchs
-- Descripción: Tramos
-- Scope: conf
--
	CREATE TABLE conf.stretchs (
		stretch_id int8 NOT NULL,
		stretch_type_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		pk_ini float8 NOT NULL,
		pk_end float8 NOT NULL,
		lanes int4 NOT NULL,
		road_id int8 NOT NULL,
		direction int4 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_stretchs PRIMARY KEY (stretch_id)
	);
	
	CREATE INDEX idx_stretchs_stretch_types ON conf.stretchs USING btree (stretch_type_id);
	CREATE INDEX idx_stretchs_roads ON conf.stretchs USING btree (road_id);
	
	ALTER TABLE conf.stretchs ADD CONSTRAINT fk_stretchs_stretch_types FOREIGN KEY (stretch_type_id) REFERENCES master.stretch_types(stretch_type_id);
	ALTER TABLE conf.stretchs ADD CONSTRAINT fk_stretchs_roads FOREIGN KEY (road_id) REFERENCES conf.roads(road_id);
	
	ALTER TABLE conf.stretchs SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.stretchs_stretchs
-- Descripción: Super Tramos
-- Scope: conf
--
	CREATE TABLE conf.stretch_stretchs (
		stretch_stretch_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_stretch_stretchs PRIMARY KEY (stretch_stretch_id)
	);
	
	ALTER TABLE conf.stretch_stretchs SET TABLESPACE tbs_controltrafico_conf;
	
	-- 
-- Table: conf.stretchs
-- Descripción: Tramos
-- Scope: conf
--
	CREATE TABLE conf.stretch_stretchs_stretchs (
		stretch_stretch_id int8 NOT NULL,
		stretch_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_stretch_stretchs_stretchs PRIMARY KEY (stretch_stretch_id, stretch_id)
	);
	
	ALTER TABLE conf.stretch_stretchs_stretchs ADD CONSTRAINT fk_stretch_stretchs_stretchs_stretch_stretchs FOREIGN KEY (stretch_stretch_id) REFERENCES conf.stretch_stretchs(stretch_stretch_id);
	ALTER TABLE conf.stretch_stretchs_stretchs ADD CONSTRAINT fk_stretch_stretchs_stretchs_stretchs FOREIGN KEY (stretch_id) REFERENCES conf.stretchs(stretch_id);

	
	ALTER TABLE conf.stretch_stretchs_stretchs SET TABLESPACE tbs_controltrafico_conf;
-- 
-- Table: conf.stretch_locations
-- Descripción: Localizaciones asociadas a tramos
-- Scope: conf
--
	CREATE TABLE conf.stretch_locations (
		stretch_id int8 NOT NULL,
		location_id int8 NOT NULL,
		position int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_stretch_locations PRIMARY KEY (stretch_id, location_id)
	);
	
	CREATE INDEX idx_stretch_locations_stretchs ON conf.stretch_locations USING btree (stretch_id);
	CREATE INDEX idx_stretch_locations_locations ON conf.stretch_locations USING btree (location_id);
	
	ALTER TABLE conf.stretch_locations ADD CONSTRAINT fk_stretch_locations_stretchs FOREIGN KEY (stretch_id) REFERENCES conf.stretchs(stretch_id);
	ALTER TABLE conf.stretch_locations ADD CONSTRAINT fk_stretch_locations_locations FOREIGN KEY (location_id) REFERENCES conf.locations(location_id);
	
	ALTER TABLE conf.stretch_locations SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.spath_stretchs
-- Descripción: Tramos relacionados detalles
-- Scope: conf
--
	CREATE TABLE conf.spath_stretchs (
		spath_id int8 NOT NULL,
		stretch_id int8 NOT NULL,
		rel_stretch_id int8 NOT NULL,
		position int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_spath_stretchs PRIMARY KEY (spath_id, stretch_id, rel_stretch_id)
	);
	
	CREATE INDEX idx_spath_stretchs_spaths ON conf.spath_stretchs USING btree (spath_id);
	CREATE INDEX idx_spath_stretchs_stretchs ON conf.spath_stretchs USING btree (stretch_id);
	CREATE INDEX idx_spath_stretchs_rel_stretchs ON conf.spath_stretchs USING btree (stretch_id);
	
	ALTER TABLE conf.spath_stretchs ADD CONSTRAINT fk_spath_stretchs_spaths FOREIGN KEY (spath_id) REFERENCES conf.spaths(spath_id);
	ALTER TABLE conf.spath_stretchs ADD CONSTRAINT fk_spath_stretchs_stretchs FOREIGN KEY (stretch_id) REFERENCES conf.stretchs(stretch_id);
	ALTER TABLE conf.spath_stretchs ADD CONSTRAINT fk_spath_stretchs_rel_stretchs FOREIGN KEY (rel_stretch_id) REFERENCES conf.stretchs(stretch_id);
	
	ALTER TABLE conf.spath_stretchs SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.elements
-- Descripción: Equipos físicos o lógicos de proyecto
-- Scope: conf
--
	CREATE TABLE conf.elements (
		element_type_id int8 NOT NULL,	
		element_subtype_id int8 NULL,
		element_id int8 NOT NULL,
		location_id int8 NULL,
		keep_values_on_disconnected bool NULL,
		inherit_state_id int8 NULL,
		alias varchar NULL,
		description varchar NULL,
		on_change_alarm varchar NULL,
		on_change_state varchar NULL,
		on_change_measure varchar NULL,
		on_schedule varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		alert bool NULL,
		alarm_count int4 NULL,
		measure_change_all bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_elements PRIMARY KEY (element_type_id, element_id)
	);

	CREATE INDEX idx_elements_element_type_id ON conf.elements USING btree (element_type_id);
	CREATE INDEX idx_elements_locations ON conf.elements USING btree (location_id);
	CREATE INDEX idx_elements_element_type_states ON conf.elements USING btree (element_type_id, inherit_state_id);

	ALTER TABLE conf.elements ADD CONSTRAINT fk_elements_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);
	ALTER TABLE conf.elements ADD CONSTRAINT fk_elements_locations FOREIGN KEY (location_id) REFERENCES conf.locations(location_id);
	ALTER TABLE conf.elements ADD CONSTRAINT fk_elements_element_type_states FOREIGN KEY (element_type_id, inherit_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE conf.elements ADD CONSTRAINT fk_elements_element_subtypes FOREIGN KEY (element_type_id, element_subtype_id) REFERENCES master.element_subtypes(element_type_id, element_subtype_id);

	ALTER TABLE conf.elements SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.element_hierarchies
-- Descripción: Relación de jerarquía entre elementos
-- Scope: conf
--
	CREATE TABLE conf.element_hierarchies (
		parent_element_type_id int8 NOT NULL,
		parent_element_id int8 NOT NULL,
		child_element_type_id int8 NOT NULL,
		child_element_id int8 NOT null,
		child_inheritable_state_id int8 null,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT null,
		CONSTRAINT pk_element_hierarchies PRIMARY KEY (parent_element_type_id, parent_element_id, child_element_type_id, child_element_id)
	);

	CREATE INDEX idx_element_hierarchies_parent ON conf.element_hierarchies USING btree (parent_element_type_id, parent_element_id);
	CREATE INDEX idx_element_hierarchies_child ON conf.element_hierarchies USING btree (child_element_type_id, child_element_id);
	CREATE INDEX idx_element_hierarchies_element_type_states ON conf.element_hierarchies USING btree (child_element_type_id, child_inheritable_state_id);

	ALTER TABLE conf.element_hierarchies ADD CONSTRAINT fk_element_hierarchies_parent FOREIGN KEY (parent_element_type_id, parent_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.element_hierarchies ADD CONSTRAINT fk_element_hierarchies_child FOREIGN KEY (child_element_type_id, child_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.element_hierarchies ADD CONSTRAINT fk_element_hierarchies_element_type_states FOREIGN KEY (child_element_type_id, child_inheritable_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE conf.element_hierarchies ADD CONSTRAINT fk_element_hierarchies_allowed FOREIGN KEY (parent_element_type_id, child_element_type_id) REFERENCES master.element_hierarchies_allowed(parent_element_type_id, child_element_type_id);

	
	ALTER TABLE conf.element_hierarchies SET TABLESPACE tbs_controltrafico_conf;

	
-- 
-- Table: conf.element_values
-- Descripción: Parámetros de los equipos
-- Scope: conf
--
	CREATE TABLE conf.element_values (
		element_type_id int8 NOT NULL,
		element_type_param_id int8 NOT NULL,
		param_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		element_value_state_id int8 NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_values PRIMARY KEY (element_type_id, element_type_param_id, param_type_id, element_id)
	);

	CREATE INDEX idx_element_values_element_id ON conf.element_values USING btree (element_id);
	CREATE INDEX idx_element_values_element_value_states ON conf.element_values USING btree (element_value_state_id);

	ALTER TABLE conf.element_values ADD CONSTRAINT fk_element_values_element_type_params FOREIGN KEY (element_type_id, element_type_param_id, param_type_id) REFERENCES master.element_type_params(element_type_id, element_type_param_id, param_type_id);
	ALTER TABLE conf.element_values ADD CONSTRAINT fk_element_values_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.element_values ADD CONSTRAINT fk_element_values_element_value_states FOREIGN KEY (element_value_state_id) REFERENCES master.element_value_states(element_value_state_id);
	
	ALTER TABLE conf.element_values SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.modbus_element_element_type_states
-- Descripción: Direcciones modbus para estados
-- Scope: conf
--
	CREATE TABLE conf.modbus_element_element_type_states (
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		xAddress int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_modbus_element_element_type_states PRIMARY KEY (element_type_id, element_id)
	);

	CREATE INDEX idx_modbus_element_element_type_states_elements ON conf.modbus_element_element_type_states USING btree (element_type_id, element_id);

	ALTER TABLE conf.modbus_element_element_type_states ADD CONSTRAINT fk_modbus_element_element_type_states_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE conf.modbus_element_element_type_states SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.modbus_element_element_type_params
-- Descripción: Direcciones modbus para medidas
-- Scope: conf
--
	CREATE TABLE conf.modbus_element_element_type_params (
		element_type_id int8 NOT NULL,
		element_type_param_id int8 NOT NULL,
		param_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		xAddress int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_modbus_element_element_type_params PRIMARY KEY (element_type_id, element_type_param_id, param_type_id, element_id)
	);

	CREATE INDEX idx_modbus_element_element_type_params_elements ON conf.modbus_element_element_type_params USING btree (element_type_id, element_id);
	CREATE INDEX idx_modbus_element_element_type_params_element_type_params ON conf.modbus_element_element_type_params USING btree (element_type_param_id, element_type_id, param_type_id);

	ALTER TABLE conf.modbus_element_element_type_params ADD CONSTRAINT fk_modbus_element_element_type_params_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.modbus_element_element_type_params ADD CONSTRAINT fk_modbus_element_element_type_params_element_type_params FOREIGN KEY (element_type_param_id, element_type_id, param_type_id) REFERENCES master.element_type_params(element_type_param_id, element_type_id, param_type_id);
	
	ALTER TABLE conf.modbus_element_element_type_params SET TABLESPACE tbs_controltrafico_conf;
