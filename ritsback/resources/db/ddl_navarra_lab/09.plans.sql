--DROP TABLE IF EXISTS conf.plan_order_command_elements;
--DROP TABLE IF EXISTS conf.plan_order_preconditions;
--DROP TABLE IF EXISTS conf.plan_order_element_type_states;
--DROP TABLE IF EXISTS conf.plan_order_element_values;
--DROP TABLE IF EXISTS conf.plan_order_values;
--DROP TABLE IF EXISTS conf.plan_orders;
--DROP TABLE IF EXISTS conf.plan_schedules;
--DROP TABLE IF EXISTS conf.plans;
--
--DROP TABLE IF EXISTS master.plan_mode_types;
--DROP TABLE IF EXISTS master.plan_order_precondition_types;
--DROP TABLE IF EXISTS master.plan_order_type_params;
--DROP TABLE IF EXISTS master.plan_order_types;
--DROP TABLE IF EXISTS master.plan_state_types;
--DROP TABLE IF EXISTS master.plan_schedule_state_types;

---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.plan_schedule_state_types
-- Descripción: Tipos de estados de ejecución de planes programados
-- Scope: master
--
	CREATE TABLE master.plan_schedule_state_types (
		plan_schedule_state_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_schedule_state_types PRIMARY KEY (plan_schedule_state_type_id)
	);

	ALTER TABLE master.plan_schedule_state_types SET TABLESPACE tbs_controltrafico_master;


-- 
-- Table: master.plan_state_types
-- Descripción: Tipos de estados de ejecución de planes
-- Scope: master
--
	CREATE TABLE master.plan_state_types (
		plan_state_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_state_types PRIMARY KEY (plan_state_type_id)
	);

	ALTER TABLE master.plan_state_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.plan_order_state_types
-- Descripción: Tipos de estados de ejecución de ordenes en planes
-- Scope: master
--
	CREATE TABLE master.plan_order_state_types (
		plan_order_state_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_order_state_types PRIMARY KEY (plan_order_state_type_id)
	);

	ALTER TABLE master.plan_order_state_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.plan_order_types
-- Descripción: Tipos de ordenes de planes
-- Scope: master
--
	CREATE TABLE master.plan_order_types (
		plan_order_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_order_types PRIMARY KEY (plan_order_type_id)
	);

	ALTER TABLE master.plan_order_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.plan_order_type_params
-- Descripción: Tipos de ordenes de planes
-- Scope: master
--
	CREATE TABLE master.plan_order_type_params (
		plan_order_type_id int8 NOT NULL,
		plan_order_type_param_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		data_type_id int8 NOT NULL,
		threshold_1 varchar NULL,
		threshold_2 varchar NULL,
		plan_order_type_param_unit_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_order_type_params PRIMARY KEY (plan_order_type_id, plan_order_type_param_id)
	);

	ALTER TABLE master.plan_order_type_params SET TABLESPACE tbs_controltrafico_master;
	
	CREATE INDEX idx_plan_order_type_params_plan_order_type_id ON master.plan_order_type_params USING btree (plan_order_type_id);
	CREATE INDEX idx_plan_order_type_params_data_type_id ON master.plan_order_type_params USING btree (data_type_id);
	
	ALTER TABLE master.plan_order_type_params ADD CONSTRAINT fk_plan_order_type_params_plan_order_type FOREIGN KEY (plan_order_type_id) REFERENCES master.plan_order_types(plan_order_type_id);
	ALTER TABLE master.plan_order_type_params ADD CONSTRAINT fk_plan_order_type_params_data_type FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE master.plan_order_type_params ADD CONSTRAINT fk_plan_order_type_params_param_unit FOREIGN KEY (plan_order_type_param_unit_id) REFERENCES master.element_type_param_unit(element_type_param_unit_id);

	ALTER TABLE master.plan_order_type_params SET TABLESPACE tbs_controltrafico_master;
	

-- 
-- Table: master.plan_mode_types
-- Descripción: Tipos de modos de ejecución de un plan
-- Scope: master
--
	CREATE TABLE master.plan_mode_types (
		plan_mode_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_mode_types PRIMARY KEY (plan_mode_type_id)
	);

	ALTER TABLE master.plan_mode_types SET TABLESPACE tbs_controltrafico_master;
	
---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------

-- 
-- Table: conf.plans
-- Descripción: Planes
-- Scope: conf
--
	CREATE TABLE conf.plans (
		plan_id int8 NOT NULL,
		plan_mode_type_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		management_area_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plans PRIMARY KEY (plan_id)
	);
	
	CREATE INDEX idx_plans_plan_mode_type_id ON conf.plans USING btree (plan_mode_type_id);
	
	ALTER TABLE conf.plans ADD CONSTRAINT fk_plans_plan_mode_type FOREIGN KEY (plan_mode_type_id) REFERENCES master.plan_mode_types(plan_mode_type_id);
	ALTER TABLE conf.plans ADD CONSTRAINT fk_plans_management_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);

	ALTER TABLE conf.plans SET TABLESPACE tbs_controltrafico_conf;
	


-- 
-- Table: conf.plan_orders
-- Descripción: Ordenes del plan
-- Scope: conf
--
	CREATE TABLE conf.plan_orders (
		plan_id int8 NOT NULL,
		plan_order_id int8 NOT NULL,
		plan_order_type_id int8 NOT NULL,
		plan_order_order int4 NULL,
		next_plan_order_id int8  NULL,
		next_plan_id int8  NULL,
		alias varchar NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_orders PRIMARY KEY (plan_id, plan_order_id)
	);
	
	CREATE INDEX idx_plan_orders_plan_id ON conf.plan_orders USING btree (plan_id);
	CREATE INDEX idx_plan_orders_plan_order_type_id ON conf.plan_orders USING btree (plan_order_type_id);
	
	ALTER TABLE conf.plan_orders ADD CONSTRAINT fk_plan_orders_plan FOREIGN KEY (plan_id) REFERENCES conf.plans(plan_id);
	ALTER TABLE conf.plan_orders ADD CONSTRAINT fk_plan_orders_plan_order_type FOREIGN KEY (plan_order_type_id) REFERENCES master.plan_order_types(plan_order_type_id);
	ALTER TABLE conf.plan_orders ADD CONSTRAINT fk_plan_orders_next_plan_order_id FOREIGN KEY (plan_id, next_plan_order_id) REFERENCES conf.plan_orders(plan_id, plan_order_id);
    ALTER TABLE conf.plan_orders ADD CONSTRAINT fk_plan_orders_next_plan FOREIGN KEY (next_plan_id) REFERENCES conf.plans(plan_id);

	ALTER TABLE conf.plan_orders SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.plan_order_values
-- Descripción: Valores de parámetros de una orden de un plan 
-- Scope: conf
--
	CREATE TABLE conf.plan_order_values (
		
		plan_id int8 NOT NULL,
		plan_order_id int8 NOT NULL,	
		plan_order_type_id int8 NOT NULL,
		plan_order_type_param_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_order_values PRIMARY KEY (plan_id, plan_order_id, plan_order_type_id, plan_order_type_param_id)
	);
	
	CREATE INDEX idx_plan_order_values_plan_order ON conf.plan_order_values USING btree (plan_id, plan_order_id);
	CREATE INDEX idx_plan_order_values_plan_order_type_param ON conf.plan_order_values USING btree (plan_order_type_id, plan_order_type_param_id);
	
	ALTER TABLE conf.plan_order_values ADD CONSTRAINT fk_plan_order_values_plan_order FOREIGN KEY (plan_id, plan_order_id) REFERENCES conf.plan_orders(plan_id, plan_order_id);
	ALTER TABLE conf.plan_order_values ADD CONSTRAINT fk_plan_order_values_plan_order_type_param FOREIGN KEY (plan_order_type_id, plan_order_type_param_id) REFERENCES master.plan_order_type_params(plan_order_type_id, plan_order_type_param_id);
	
	ALTER TABLE conf.plan_order_values SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.plan_order_element_values
-- Descripción: Valores de parámetros de una orden relacionada con  un equipamiento en un plan 
-- Scope: conf
--
	CREATE TABLE conf.plan_order_element_values (
		
		plan_id int8 NOT NULL,
		plan_order_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		param_type_id int8 NOT NULL,
		element_type_param_id int8 NOT NULL,
		element_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_order_element_values PRIMARY KEY (plan_id, plan_order_id, element_type_id, param_type_id, element_type_param_id, element_id)
	);
	
	CREATE INDEX idx_plan_order_element_values_plan_order ON conf.plan_order_element_values USING btree (plan_id, plan_order_id);
	CREATE INDEX idx_plan_order_element_values_type_param ON conf.plan_order_element_values USING btree (element_type_id, element_type_param_id, param_type_id);
	CREATE INDEX idx_plan_order_element_values_element ON conf.plan_order_element_values USING btree (element_type_id, element_id);
	
	ALTER TABLE conf.plan_order_element_values ADD CONSTRAINT fk_plan_order_element_values_plan_order FOREIGN KEY (plan_id, plan_order_id) REFERENCES conf.plan_orders(plan_id, plan_order_id);
	ALTER TABLE conf.plan_order_element_values ADD CONSTRAINT fk_plan_order_element_values_element_type_params FOREIGN KEY (element_type_id, element_type_param_id, param_type_id) REFERENCES master.element_type_params(element_type_id, element_type_param_id, param_type_id);
	ALTER TABLE conf.plan_order_element_values ADD CONSTRAINT fk_plan_order_element_values_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE conf.plan_order_element_values SET TABLESPACE tbs_controltrafico_conf;

	-- 
-- Table: conf.plan_order_element_type_states
-- Descripción: Valores de parámetros de una orden relacionada con  un equipamiento en un plan 
-- Scope: conf
--
	CREATE TABLE conf.plan_order_element_type_states(
		
		plan_id int8 NOT NULL,
		plan_order_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_type_state_id int8 NOT NULL,
		element_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_order_element_type_states PRIMARY KEY (plan_id, plan_order_id, element_type_id, element_type_state_id, element_id)
	);
	
	CREATE INDEX idx_plan_order_element_states_type_plan_order ON conf.plan_order_element_type_states USING btree (plan_id, plan_order_id);
	CREATE INDEX idx_plan_order_element_states_type_element ON conf.plan_order_element_type_states USING btree (element_type_id, element_id);
	
	ALTER TABLE conf.plan_order_element_type_states ADD CONSTRAINT fk_plan_order_element_type_states_plan_order FOREIGN KEY (plan_id, plan_order_id) REFERENCES conf.plan_orders(plan_id, plan_order_id);
	ALTER TABLE conf.plan_order_element_type_states ADD CONSTRAINT fk_plan_order_element_type_states_element_element_type_states FOREIGN KEY (element_type_id, element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE conf.plan_order_element_type_states ADD CONSTRAINT fk_plan_order_element_type_states_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE conf.plan_order_element_type_states SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.plan_order_conditions
-- Descripción: condiciones asociadas a una orden de un plan 
-- Scope: conf
--
	CREATE TABLE conf.plan_order_conditions (
		
		plan_order_condition_id int8 NOT NULL,
		plan_id int8 NOT NULL,
		plan_order_id int8 NOT NULL,	
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		param_type_id int8 NULL,
		element_type_param_id int8 NULL,
		threshold_operation_id int8 NULL,
		threshold_val1 varchar NULL,
		threshold_val2 varchar NULL,
		element_type_state_id int8 NULL,
		plan_order_condition_order int4 NOT NULL,
		nexus_and bool NOT NULL,	
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_order_conditions PRIMARY KEY (plan_order_condition_id, plan_id, plan_order_id)
	);
	
	CREATE INDEX idx_plan_order_conditions_plan_order_precondition_id ON conf.plan_order_conditions USING btree (plan_order_condition_id);
	CREATE INDEX idx_plan_order_conditions_plan_order ON conf.plan_order_conditions USING btree (plan_id, plan_order_id);
	CREATE INDEX idx_plan_order_conditions_type_param ON conf.plan_order_conditions USING btree (element_type_id, element_type_param_id, param_type_id);
	CREATE INDEX idx_plan_order_conditions_element ON conf.plan_order_conditions USING btree (element_type_id, element_id);
	
	ALTER TABLE conf.plan_order_conditions ADD CONSTRAINT fk_plan_order_conditions_plan_order FOREIGN KEY (plan_id, plan_order_id) REFERENCES conf.plan_orders(plan_id, plan_order_id);
	ALTER TABLE conf.plan_order_conditions ADD CONSTRAINT fk_plan_order_conditions_element_type_params FOREIGN KEY (element_type_id, element_type_param_id, param_type_id) REFERENCES master.element_type_params(element_type_id, element_type_param_id, param_type_id);
	ALTER TABLE conf.plan_order_conditions ADD CONSTRAINT fk_plan_order_conditions_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.plan_order_conditions ADD CONSTRAINT fk_plan_order_conditions_element_type_states FOREIGN KEY (element_type_id, element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE conf.plan_order_conditions ADD CONSTRAINT fk_plan_order_conditions_threshold_operations FOREIGN KEY (threshold_operation_id) REFERENCES master.threshold_operations(threshold_operation_id);

	ALTER TABLE conf.plan_order_conditions SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.plan_schedules
-- Descripción: Planificaciones de planes
-- Scope: conf
--
	CREATE TABLE conf.plan_schedules (
		plan_schedule_id int8 NOT NULL,
		plan_id int8 NOT NULL,
		cron_expression varchar NULL,
		alias varchar NULL,
		description varchar NULL,
		current_plan_schedule_state_type_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_plan_schedules PRIMARY KEY (plan_schedule_id)
	);
	
	CREATE INDEX idx_plan_schedules_plan ON conf.plan_schedules USING btree (plan_id);
	
	ALTER TABLE conf.plan_schedules ADD CONSTRAINT fk_plans_schedules_plan FOREIGN KEY (plan_id) REFERENCES conf.plans(plan_id);

	ALTER TABLE conf.plan_schedules SET TABLESPACE tbs_controltrafico_conf;

	
-- 
-- Table: conf.plan_order_comand_elements
-- Descripción: Relacion uno a uno entre las ordenes y los comandos
-- Scope: conf
--
    CREATE TABLE conf.plan_order_command_elements (
       command_element_type_id int8 NOT NULL,
       element_type_id int8 NOT NULL,
       command_element_id int8 NOT NULL,
       plan_id int8 NOT NULL,
       plan_order_id int8 NOT NULL,
       enabled bool NULL,
       visible bool NULL,
       created_at timestamptz NOT NULL,
       updated_at timestamptz NOT NULL,
       CONSTRAINT pk_plan_order_command_elements PRIMARY KEY (plan_id, plan_order_id)
    );
    
    CREATE INDEX idx_plan_order_command_elements_command_element ON conf.plan_order_command_elements USING btree (command_element_type_id, element_type_id, command_element_id);
    CREATE INDEX idx_plan_order_command_elements_plan_order ON conf.plan_order_command_elements USING btree (plan_id, plan_order_id);
    
	ALTER TABLE conf.plan_order_command_elements ADD CONSTRAINT fk_plan_order_command_elements_command_element FOREIGN KEY (command_element_type_id, element_type_id, command_element_id) REFERENCES conf.command_elements(command_element_type_id, element_type_id, command_element_id);
	ALTER TABLE conf.plan_order_command_elements ADD CONSTRAINT fk_plan_order_command_elements_plan_order FOREIGN KEY (plan_id, plan_order_id) REFERENCES conf.plan_orders(plan_id, plan_order_id);

	ALTER TABLE conf.plan_order_command_elements SET TABLESPACE tbs_controltrafico_conf;


-- 
-- Table: conf.plan_nodes
-- Description: 
-- Scope: conf		
CREATE TABLE conf.plan_nodes (
		node_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		plan_id int8  NULL,
		management_area_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_plan_nodes PRIMARY KEY (node_id)
	);
	
ALTER TABLE conf.plan_nodes SET TABLESPACE tbs_controltrafico_conf;

ALTER TABLE conf.plan_nodes ADD CONSTRAINT fk_plan_nodes_plans FOREIGN KEY (plan_id) REFERENCES conf.plans(plan_id);
ALTER TABLE conf.plan_nodes ADD CONSTRAINT fk_plan_nodes_management_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);


-- 
-- Table: conf.plan_node_hierarchies
-- Description: 
-- Scope: conf		
CREATE TABLE conf.plan_node_hierarchies (
		node_parent_id int8 NOT NULL,
		node_child_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_plan_node_hierarchies PRIMARY KEY (node_parent_id, node_child_id)
	);
	
ALTER TABLE conf.plan_node_hierarchies SET TABLESPACE tbs_controltrafico_conf;

ALTER TABLE conf.plan_node_hierarchies ADD CONSTRAINT fk_plan_node_hierarchies_parent FOREIGN KEY (node_parent_id) REFERENCES conf.plan_nodes(node_id);
ALTER TABLE conf.plan_node_hierarchies ADD CONSTRAINT fk_plan_node_hierarchies_child FOREIGN KEY (node_child_id) REFERENCES conf.plan_nodes(node_id);



	
	
