--DROP TABLE IF EXISTS conf.modbus_element_command_element_types;
--DROP TABLE IF EXISTS conf.command_element_values;
--DROP TABLE IF EXISTS conf.command_elements;
--
--DROP TABLE IF EXISTS conf.element_io_controller_modules;
--DROP TABLE IF EXISTS conf.io_controller_module_values;
--DROP TABLE IF EXISTS conf.io_controller_modules; 
--
--DROP TABLE IF EXISTS master.command_element_type_params;
--DROP TABLE IF EXISTS master.command_element_types;
--DROP TABLE IF EXISTS master.io_controller_module_type_element_types;
--DROP TABLE IF EXISTS master.io_controller_module_type_params;
--DROP TABLE IF EXISTS master.io_controller_module_types;


---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.io_controller_module_types
-- Descripción: Tipos de controladores de equipamiento
-- Scope: master
--
	CREATE TABLE master.io_controller_module_types (
		module_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_io_controller_module_types PRIMARY KEY (module_type_id)
	);

	ALTER TABLE master.io_controller_module_types SET TABLESPACE tbl_master;
	
-- 
-- Table: master.io_controller_module_type_params
-- Descripción: Parámetros de tipos de controladores de equipamiento
-- Scope: master
--
	CREATE TABLE master.io_controller_module_type_params (
		module_type_id int8 NOT NULL,
		module_type_param_id int8 NOT NULL,
		data_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_io_controller_module_type_params PRIMARY KEY (module_type_id, module_type_param_id)
	);
	
	CREATE INDEX idx_io_controller_module_type_params_data_type_id ON master.io_controller_module_type_params USING btree (data_type_id);
	CREATE INDEX idx_io_controller_module_type_params_module_type_id ON master.io_controller_module_type_params USING btree (module_type_id);

	ALTER TABLE master.io_controller_module_type_params ADD CONSTRAINT fk_io_controller_module_type_params_io_controller_module_types FOREIGN KEY (module_type_id) REFERENCES master.io_controller_module_types(module_type_id);
	ALTER TABLE master.io_controller_module_type_params ADD CONSTRAINT fk_io_controller_module_type_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);

	ALTER TABLE master.io_controller_module_type_params SET TABLESPACE tbl_master;
	
-- 
-- Table: master.io_controller_module_type_element_types
-- Descripción: Relación de tipos de equipos controlados por un tipo de element_controller
-- Scope: master
--
	CREATE TABLE master.io_controller_module_type_element_types (
		module_type_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_io_controller_module_type_element_types PRIMARY KEY (module_type_id, element_type_id)
	);
	
	CREATE INDEX idx_io_controller_module_type_element_types_module_type_id ON master.io_controller_module_type_element_types USING btree (module_type_id);
	CREATE INDEX idx_io_controller_module_type_element_types_element_type_id ON master.io_controller_module_type_element_types USING btree (element_type_id);
	
	ALTER TABLE master.io_controller_module_type_element_types ADD CONSTRAINT fk_io_controller_module_type_element_types_io_cont_mod_types FOREIGN KEY (module_type_id) REFERENCES master.io_controller_module_types(module_type_id);
	ALTER TABLE master.io_controller_module_type_element_types ADD CONSTRAINT fk_io_controller_module_type_element_types_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);

	ALTER TABLE master.io_controller_module_type_element_types SET TABLESPACE tbl_master;
	

-- 
-- Table: master.command_element_types
-- Descripción: Comandos de equipamientos
-- Scope: master
--
	CREATE TABLE master.command_element_types (
		command_element_type_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		on_change varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		pm_queue_compatibility bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_command_element_types PRIMARY KEY (command_element_type_id, element_type_id)
	);

	CREATE INDEX idx_command_element_types_element_type_id ON master.command_element_types USING btree (element_type_id, element_type_id);
	
	ALTER TABLE master.command_element_types ADD CONSTRAINT fk_command_element_types_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);
	
	ALTER TABLE master.command_element_types SET TABLESPACE tbl_master;
	
-- 
-- Table: master.command_element_type_params
-- Descripción: Parámetros de comandos
-- Scope: master
--
	CREATE TABLE master.command_element_type_params (
		command_element_type_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		command_element_type_param_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		data_type_id int8 NOT NULL,
		threshold_1 varchar NULL,
		threshold_2 varchar NULL,
		command_element_type_param_unit_id int8 NULL,
		element_type_param_id int8 NULL, 
		param_type_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_command_element_type_params PRIMARY KEY (command_element_type_id, element_type_id, command_element_type_param_id)
	);

	
	CREATE INDEX idx_command_element_type_params_command_element_type ON master.command_element_type_params USING btree (command_element_type_id, element_type_id);
	CREATE INDEX idx_command_element_type_params_element_type ON master.command_element_type_params USING btree (element_type_id);
	CREATE INDEX idx_command_element_type_params_data_type_id ON master.command_element_type_params USING btree (data_type_id);
	CREATE INDEX idx_command_element_type_params_element_type_param ON master.command_element_type_params USING btree (element_type_param_id, element_type_id, param_type_id);

	ALTER TABLE master.command_element_type_params ADD CONSTRAINT fk_command_element_type_params_command_element_types FOREIGN KEY (command_element_type_id, element_type_id) REFERENCES master.command_element_types(command_element_type_id, element_type_id);
	ALTER TABLE master.command_element_type_params ADD CONSTRAINT fk_command_element_type_params_element_types FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);
	ALTER TABLE master.command_element_type_params ADD CONSTRAINT fk_command_element_type_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE master.command_element_type_params ADD CONSTRAINT fk_command_element_type_params_param_unit FOREIGN KEY (command_element_type_param_unit_id) REFERENCES master.element_type_param_unit(element_type_param_unit_id);
	ALTER TABLE master.command_element_type_params ADD CONSTRAINT idx_command_element_type_params_element_type_param FOREIGN KEY (element_type_param_id, element_type_id, param_type_id) REFERENCES master.element_type_params(element_type_param_id, element_type_id, param_type_id);

	ALTER TABLE master.command_element_type_params SET TABLESPACE tbl_master;

	-- 
-- Table: conf.command_element_preconditions
-- Descripción: Precondiciones para comandos
-- Scope: conf
--
CREATE TABLE conf.command_element_preconditions (
		-- PK
		command_element_type_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		precondition_id int8 NOT NULL,
		
		-- Equipo a comandar
		element_id int8 NOT NULL,
		
		-- Equipo que tiene que cumplir criterios
		target_element_type_id int8 NOT NULL,
		target_element_id int8 NOT NULL,
		
		-- A, Criterios de medidas
		target_element_type_param_id int8 NULL,
		target_param_type_id int8 NULL,
		
		threshold_operation_id int8 NULL,
  		threshold_val1 varchar NULL,
		threshold_val2 varchar NULL,
		
		-- B, Criterio de estado
		element_type_state_id int8 NULL,
		
		-- C, Criterio de alarma
		active_alarm_config_id int8 NULL,
		
		-- 
		negative bool NOT NULL,
		
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_command_element_preconditions PRIMARY KEY (command_element_type_id, element_type_id, element_id, precondition_id)
	);

	CREATE INDEX idx_command_element_preconditions_command_element_types ON conf.command_element_preconditions USING btree (command_element_type_id, element_type_id, element_id);
	CREATE INDEX idx_command_element_preconditions_element ON conf.command_element_preconditions USING btree (element_type_id, element_id);
	CREATE INDEX idx_command_element_preconditions_target_element ON conf.command_element_preconditions USING btree (target_element_type_id, target_element_id);
	CREATE INDEX idx_command_element_preconditions_element_type_params ON conf.command_element_preconditions USING btree (target_element_type_param_id, target_element_type_id, target_param_type_id);
	CREATE INDEX idx_command_element_preconditions_threshold_operations ON conf.command_element_preconditions USING btree (threshold_operation_id);
	CREATE INDEX idx_command_element_preconditions_element_type_states ON conf.command_element_preconditions USING btree (target_element_type_id, element_type_state_id);
	CREATE INDEX idx_command_element_preconditions_active_alarm_configs ON conf.command_element_preconditions USING btree (active_alarm_config_id);
	
	ALTER TABLE conf.command_element_preconditions ADD CONSTRAINT fk_command_element_preconditions_command_element_types FOREIGN KEY (command_element_type_id, element_type_id) REFERENCES master.command_element_types(command_element_type_id, element_type_id);
	ALTER TABLE conf.command_element_preconditions ADD CONSTRAINT fk_command_element_preconditions_element FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.command_element_preconditions ADD CONSTRAINT fk_command_element_preconditions_target_element FOREIGN KEY (target_element_type_id, target_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.command_element_preconditions ADD CONSTRAINT fk_command_element_preconditions_element_type_params FOREIGN KEY(target_element_type_param_id, target_element_type_id, target_param_type_id) REFERENCES master.element_type_params(element_type_param_id, element_type_id, param_type_id);
	ALTER TABLE conf.command_element_preconditions ADD CONSTRAINT fk_command_element_preconditions_threshold_operations FOREIGN KEY (threshold_operation_id) REFERENCES master.threshold_operations(threshold_operation_id);
	ALTER TABLE conf.command_element_preconditions ADD CONSTRAINT fk_command_element_preconditions_element_type_states FOREIGN KEY (target_element_type_id, element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE conf.command_element_preconditions ADD CONSTRAINT fk_command_element_preconditions_alarm_configs FOREIGN KEY (active_alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	
	ALTER TABLE conf.command_element_preconditions SET TABLESPACE tbl_conf;
	
	
-- 
-- Table: conf.command_element_value_preconditions
-- Descripción: 
-- Scope: conf
--

	CREATE TABLE conf.command_element_value_preconditions (
		command_element_type_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		precondition_id int8 NOT NULL,
		command_element_type_param_id int8 NOT NULL,
		threshold_operation_id int8 NULL,
  		threshold_val1 varchar NULL,
		threshold_val2 varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_command_element_value_preconditions PRIMARY KEY (command_element_type_id, element_type_id, element_id, precondition_id, command_element_type_param_id)
	);
	
	CREATE INDEX idx_command_element_value_preconditions ON conf.command_element_value_preconditions USING btree (command_element_type_id, element_type_id, element_id, precondition_id);
	
	ALTER TABLE conf.command_element_value_preconditions ADD CONSTRAINT fk_command_element_value_preconditions_command_element_pr FOREIGN KEY (command_element_type_id, element_type_id, element_id, precondition_id) REFERENCES conf.command_element_preconditions(command_element_type_id, element_type_id, element_id, precondition_id);
	ALTER TABLE conf.command_element_value_preconditions ADD CONSTRAINT fk_command_element_value_preconditions_command_params FOREIGN KEY (command_element_type_id, element_type_id, command_element_type_param_id) REFERENCES master.command_element_type_params(command_element_type_id, element_type_id, command_element_type_param_id);
	ALTER TABLE conf.command_element_value_preconditions ADD CONSTRAINT fk_command_element_value_preconditions_threshold_operations FOREIGN KEY (threshold_operation_id) REFERENCES master.threshold_operations(threshold_operation_id);
	
	ALTER TABLE conf.command_element_value_preconditions SET TABLESPACE tbl_conf;
	
	
-- Table: master.element_subtype_active_commands
-- Descripción: Librería de comandos para un determinado element_subtype_id del sistema
-- Scope: master
--
	CREATE TABLE master.element_subtype_active_commands (
		element_subtype_id int8 NOT NULL,
		command_element_type_id int8 NOT NULL,
		element_type_id int8 NOT NULL,		
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_subtype_active_commands PRIMARY KEY (element_subtype_id, command_element_type_id, element_type_id)
	);

	ALTER TABLE master.element_subtype_active_commands ADD CONSTRAINT fk_element_subtype_active_commands_element_subtypes 
	FOREIGN KEY (element_subtype_id, element_type_id) REFERENCES master.element_subtypes(element_subtype_id, element_type_id);

	ALTER TABLE master.element_subtype_active_commands ADD CONSTRAINT fk_element_subtype_active_commands_command_element_types 
	FOREIGN KEY (command_element_type_id,element_type_id) REFERENCES master.command_element_types(command_element_type_id,element_type_id);	
	
---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------

-- 
-- Table: conf.io_controller_modules
-- Descripción: Instances de controladores de equipos
-- Scope: conf
--
	CREATE TABLE conf.io_controller_modules (
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_io_controller_modules PRIMARY KEY (module_type_id, module_id)
	);
	
	CREATE INDEX idx_io_controller_modules_module_type_id ON conf.io_controller_modules USING btree (module_type_id);
	
	ALTER TABLE conf.io_controller_modules ADD CONSTRAINT fk_io_controller_modules_io_controller_module_types FOREIGN KEY (module_type_id) REFERENCES master.io_controller_module_types(module_type_id);
	
	ALTER TABLE conf.io_controller_modules SET TABLESPACE tbl_conf;
	
-- 
-- Table: conf.io_controller_module_values
-- Descripción: Valores de parámetros de controladores de equipos
-- Scope: conf
--
	CREATE TABLE conf.io_controller_module_values (
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
		module_type_param_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT null,
		CONSTRAINT pk_io_controller_module_values PRIMARY KEY (module_type_id, module_id, module_type_param_id)
	);

	CREATE INDEX idx_io_controller_module_values_module_id ON conf.io_controller_module_values USING btree (module_id);

	ALTER TABLE conf.io_controller_module_values ADD CONSTRAINT fk_io_controller_module_values_io_controller_module_type_params FOREIGN KEY (module_type_id, module_type_param_id) REFERENCES master.io_controller_module_type_params(module_type_id, module_type_param_id);
	ALTER TABLE conf.io_controller_module_values ADD CONSTRAINT fk_io_controller_module_values_io_controller_modules FOREIGN KEY (module_type_id, module_id) REFERENCES conf.io_controller_modules(module_type_id, module_id);
	
	ALTER TABLE conf.io_controller_module_values SET TABLESPACE tbl_conf;
	
-- 
-- Table: conf.io_controller_module_elements	
-- Descripción: Equipos asignados a un controlador
-- Scope: conf
--
	CREATE TABLE conf.element_io_controller_modules (
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT null,
		CONSTRAINT pk_element_io_controller_modules PRIMARY KEY (element_type_id, element_id, module_type_id, module_id)
	);

	CREATE INDEX idx_element_io_controller_modules_elements ON conf.element_io_controller_modules USING btree (element_type_id, element_id);
	CREATE INDEX idx_element_io_controller_modules_io_controler_module ON conf.element_io_controller_modules USING btree (module_type_id, module_id);

	ALTER TABLE conf.element_io_controller_modules ADD CONSTRAINT fk_element_io_controller_modules_io_controller_modules FOREIGN KEY (module_type_id, module_id) REFERENCES conf.io_controller_modules(module_type_id, module_id);
	ALTER TABLE conf.element_io_controller_modules ADD CONSTRAINT fk_element_io_controller_modules_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE conf.element_io_controller_modules SET TABLESPACE tbl_conf;	
	
-- 
-- Table: conf.command_elements
-- Descripción: Comandos de equipamientos guardados
-- Scope: conf
--
    CREATE TABLE conf.command_elements (
       command_element_type_id int8 NOT NULL,
       element_type_id int8 NOT NULL,
       command_element_id int8 NOT NULL,
       element_id int8 NOT NULL,
       enabled bool NULL,
       visible bool NULL,
       created_at timestamptz NOT NULL,
       updated_at timestamptz NOT NULL,
       CONSTRAINT pk_command_elements PRIMARY KEY (command_element_type_id, element_type_id, command_element_id)
    );
    
    CREATE INDEX idx_command_elements_command_element_type ON conf.command_elements USING btree (command_element_type_id, element_type_id);
    CREATE INDEX idx_command_elements_element ON conf.command_elements USING btree (element_type_id, element_id);
    
    ALTER TABLE conf.command_elements ADD CONSTRAINT fk_command_elements_command_element_types FOREIGN KEY (command_element_type_id, element_type_id) REFERENCES master.command_element_types(command_element_type_id, element_type_id);
    ALTER TABLE conf.command_elements ADD CONSTRAINT fk_command_elements_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);

    ALTER TABLE conf.command_elements SET TABLESPACE tbl_conf;

-- 
-- Table: conf.command_element_values
-- Descripción: Valores de los parámetros de  los comandos
-- Scope: conf
-- 
	CREATE TABLE conf.command_element_values (
       command_element_type_id int8 NOT NULL,
       element_type_id int8 NOT NULL,
       command_element_type_param_id int8 NOT NULL,
       command_element_id int8 NOT NULL,
       value varchar NULL,
       enabled bool NULL,
       visible bool NULL,
       created_at timestamptz NOT NULL,
       updated_at timestamptz NOT NULL,
       CONSTRAINT pk_command_element_values PRIMARY KEY (command_element_type_id, element_type_id, command_element_type_param_id, command_element_id)
	);
	
	CREATE INDEX idx_command_element_values_command_element_type ON conf.command_element_values USING btree (command_element_type_id, element_type_id, command_element_type_param_id);
	CREATE INDEX idx_command_element_values_command_element_id ON conf.command_element_values USING btree (command_element_id);
	
	ALTER TABLE conf.command_element_values ADD CONSTRAINT fk_command_element_values_command_element_type_param FOREIGN KEY (command_element_type_id, element_type_id, command_element_type_param_id) REFERENCES master.command_element_type_params(command_element_type_id, element_type_id, command_element_type_param_id);
	ALTER TABLE conf.command_element_values ADD CONSTRAINT fk_command_element_values_command_element FOREIGN KEY (command_element_type_id, element_type_id, command_element_id) REFERENCES conf.command_elements(command_element_type_id, element_type_id, command_element_id);

	ALTER TABLE conf.command_element_values SET TABLESPACE tbl_conf;

-- 
-- Table: conf.modbus_element_command_element_types
-- Descripción: Direcciones modbus para comandos
-- Scope: conf
--
	CREATE TABLE conf.modbus_element_command_element_types (
		command_element_type_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		xAddress int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_modbus_element_command_element_types PRIMARY KEY (command_element_type_id, element_type_id, element_id)
	);

	CREATE INDEX idx_modbus_element_command_element_types_command_element_types ON conf.modbus_element_command_element_types USING btree (command_element_type_id, element_type_id);
	CREATE INDEX idx_modbus_element_command_element_types_elements ON conf.modbus_element_command_element_types USING btree (element_type_id, element_id);

	ALTER TABLE conf.modbus_element_command_element_types ADD CONSTRAINT fk_modbus_element_command_element_types_command_element_types FOREIGN KEY (command_element_type_id, element_type_id) REFERENCES master.command_element_types(command_element_type_id, element_type_id);
	ALTER TABLE conf.modbus_element_command_element_types ADD CONSTRAINT fk_modbus_element_command_element_types_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE conf.modbus_element_command_element_types SET TABLESPACE tbl_conf;

