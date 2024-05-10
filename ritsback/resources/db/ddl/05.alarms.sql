--DROP TABLE IF EXISTS conf.alarms;
--DROP TABLE IF EXISTS conf.alarm_target_elements;
--DROP TABLE IF EXISTS conf.alarm_config_measures;
--DROP TABLE IF EXISTS conf.alarm_configs;
--DROP TABLE IF EXISTS master.alarm_threshold_operations;
--DROP TABLE IF EXISTS master.alarm_types;
--DROP TABLE IF EXISTS master.alarm_levels;

---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.alarm_levels
-- Descripción: Niveles de alarmas definidos
-- Scope: master
--
	CREATE TABLE master.alarm_levels (
		alarm_level_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_alarm_levels PRIMARY KEY (alarm_level_id)
	);
	
	ALTER TABLE master.alarm_levels SET TABLESPACE tbl_master;

-- 
-- Table: master.alarm_types
-- Descripción: Tipos de alarmas definidos
-- Scope: master
--
	CREATE TABLE master.alarm_types (
		alarm_type_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_alarm_types PRIMARY KEY (alarm_type_id)
	);
	
	ALTER TABLE master.alarm_types SET TABLESPACE tbl_master;
	

---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------

-- 
-- Table: conf.alarm_configs
-- Descripción: Inventario de alarmas en el sistema
-- Scope: conf
--
	CREATE TABLE conf.alarm_configs (
		alarm_config_id int8 NOT NULL,
		alarm_type_id int8 NOT NULL,
		alarm_level_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_subtype_id int8 NULL,
		element_id int8 NULL,
		element_type_state_id int8 NULL,
		mine bool NOT NULL,
		treatment bool NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		on_create_execute varchar NULL,
		min_time_alarmed int8 NULL,
		threshold_time int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_alarm_alarm_configs PRIMARY KEY (alarm_config_id)
	);
	
	CREATE INDEX idx_alarm_config_alarm_type_id ON conf.alarm_configs USING btree (alarm_type_id);
	CREATE INDEX idx_alarm_config_alarm_level_id ON conf.alarm_configs USING btree (alarm_level_id);
	CREATE INDEX idx_alarm_config_element_type ON conf.alarm_configs USING btree (element_type_id);
	CREATE INDEX idx_alarm_config_element_subtype ON conf.alarm_configs USING btree (element_type_id, element_subtype_id);
	CREATE INDEX idx_alarm_config_elements ON conf.alarm_configs USING btree (element_type_id, element_id);
	CREATE INDEX idx_alarm_config_element_state ON conf.alarm_configs USING btree (element_type_id, element_type_state_id);
	
	ALTER TABLE conf.alarm_configs ADD CONSTRAINT fk_alarm_configs_alarm_types FOREIGN KEY (alarm_type_id) REFERENCES master.alarm_types(alarm_type_id);
	ALTER TABLE conf.alarm_configs ADD CONSTRAINT fk_alarm_configs_alarm_levels FOREIGN KEY (alarm_level_id) REFERENCES master.alarm_levels(alarm_level_id);
	ALTER TABLE conf.alarm_configs ADD CONSTRAINT fk_alarm_configs_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);
	ALTER TABLE conf.alarm_configs ADD CONSTRAINT fk_alarm_configs_element_subtypes FOREIGN KEY (element_type_id, element_subtype_id) REFERENCES master.element_subtypes(element_type_id, element_subtype_id);
	ALTER TABLE conf.alarm_configs ADD CONSTRAINT fk_alarm_configs_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.alarm_configs ADD CONSTRAINT fk_alarm_configs_element_state FOREIGN KEY (element_type_id, element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	
	ALTER TABLE conf.alarm_configs SET TABLESPACE tbl_conf;	

-- 
-- Table: conf.alarm_config_measures
-- Descripción: Información entendida de inventario de alarmas de medidas
-- Scope: conf
--
	CREATE TABLE conf.alarm_config_measures (
		alarm_config_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
  		param_type_id int8 NOT NULL,
  		element_type_param_id int8 NOT NULL,
  		alarm_threshold_operation_id int8 NOT NULL,
  		threshold_val1 varchar NOT NULL,
		threshold_val2 varchar NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_alarm_config_measures PRIMARY KEY (alarm_config_id, element_type_id, param_type_id, element_type_param_id)
	);

	CREATE INDEX idx_alarm_config_measure_alarm_configs ON conf.alarm_config_measures USING btree (alarm_config_id);
	CREATE INDEX idx_alarm_config_measure_element_type_params ON conf.alarm_config_measures USING btree (element_type_id, param_type_id, element_type_param_id);
	
	ALTER TABLE conf.alarm_config_measures ADD CONSTRAINT fk_alarm_measures_alarm_configs FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	ALTER TABLE conf.alarm_config_measures ADD CONSTRAINT fk_alarm_measures_element_type_params FOREIGN KEY (element_type_id, param_type_id, element_type_param_id) REFERENCES master.element_type_params(element_type_id, param_type_id, element_type_param_id);
	ALTER TABLE conf.alarm_config_measures ADD CONSTRAINT fk_alarm_measures_alarm_thresholds FOREIGN KEY (alarm_threshold_operation_id) REFERENCES master.threshold_operations(threshold_operation_id);
	
	ALTER TABLE conf.alarm_config_measures SET TABLESPACE tbl_conf;

-- 
-- Table: conf.alarm_target_elements
-- Descripción: Tabla de equipos que se alarman a partir de una alarma
-- Scope: conf
--
	CREATE TABLE conf.alarm_target_elements (
		alarm_config_id int8 NOT NULL,
		source_element_type_id int8 NOT NULL,
  		source_element_id int8 NOT NULL,
  		target_element_type_id int8 NOT NULL,
  		target_element_id int8 NOT NULL,
  		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_alarm_target_elements PRIMARY KEY (alarm_config_id, source_element_type_id, source_element_id, target_element_type_id, target_element_id)
	);
	
	CREATE INDEX idx_alarm_target_element_alarm_configs ON conf.alarm_target_elements USING btree (alarm_config_id);
	CREATE INDEX idx_alarm_target_element_source_elements ON conf.alarm_target_elements USING btree (source_element_type_id, source_element_id);
	CREATE INDEX idx_alarm_target_element_target_elements ON conf.alarm_target_elements USING btree (target_element_type_id, target_element_id);
	
	ALTER TABLE conf.alarm_target_elements ADD CONSTRAINT fk_alarm_target_elements_element_configs FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	ALTER TABLE conf.alarm_target_elements ADD CONSTRAINT fk_alarm_target_elements_source_elements FOREIGN KEY (source_element_type_id, source_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.alarm_target_elements ADD CONSTRAINT fk_alarm_target_elements_target_elements FOREIGN KEY (target_element_type_id, target_element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE conf.alarm_target_elements SET TABLESPACE tbl_conf;

-- 
-- Table: conf.alarms_filtered
-- Descripción: Tabla de alarmas filtradas
-- Scope: conf
--
	CREATE TABLE conf.alarms_filtered (
		alarm_config_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		filtered_until timestamptz NOT NULL,
		alarm_disabled bool NULL,
		user_id int8 NULL,
		comment varchar NULL,
  		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_alarms_filtered PRIMARY KEY (alarm_config_id, element_type_id, element_id)
	);
	
	CREATE INDEX idx_alarms_filtered_alarm_configs ON conf.alarms_filtered USING btree (alarm_config_id);
	CREATE INDEX idx_alarms_filtered_elements ON conf.alarms_filtered USING btree (element_type_id, element_id);
	CREATE INDEX idx_alarms_filtered_users ON conf.alarms_filtered USING btree (user_id);
	
	ALTER TABLE conf.alarms_filtered ADD CONSTRAINT fk_alarms_filtered_alarm_configs FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	ALTER TABLE conf.alarms_filtered ADD CONSTRAINT fk_alarms_filtered_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.alarms_filtered ADD CONSTRAINT fk_alarms_filtered_users FOREIGN KEY (user_id) REFERENCES conf.users(user_id);

	ALTER TABLE conf.alarms_filtered SET TABLESPACE tbl_conf;

	