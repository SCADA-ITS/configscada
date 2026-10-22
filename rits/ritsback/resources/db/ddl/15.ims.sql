--DROP TABLE IF EXISTS conf.ims_incident_task_plans;
--DROP TABLE IF EXISTS conf.ims_incident_alarm_condition_alarms;
--DROP TABLE IF EXISTS conf.ims_incident_alarm_conditions;
--DROP TABLE IF EXISTS conf.ims_incidents;
--DROP TABLE IF EXISTS conf.ims_incident_type_tasks;
--DROP TABLE IF EXISTS conf.ims_incident_type_state_transitions;
--DROP TABLE IF EXISTS conf.ims_incident_types;

--DROP TABLE IF EXISTS static.ims_incident_levels;
--DROP TABLE IF EXISTS static.ims_incident_states;

--DROP TABLE IF EXISTS master.ims_task_types;

---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.ims_incident_groups
-- Descripción: Grupos de incidentes
-- Scope: master
--

CREATE TABLE master.ims_incident_groups (
	incident_group_id int8 NOT NULL,
	alias varchar NULL,
	description varchar NULL,
	label_alias varchar NULL,
	label_description varchar NULL,
	enabled bool NULL,
	visible bool NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT pk_ims_incident_groups PRIMARY KEY (incident_group_id)
);

ALTER TABLE master.ims_incident_groups SET TABLESPACE tbl_master;


-- 
-- Table: master.ims_task_types
-- Descripción: Tipos de tareas disponibles para ejecutar en incidencias
-- Scope: master
--
	CREATE TABLE master.ims_task_types (
		task_type_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_task_types PRIMARY KEY (task_type_id)
	);
	
	ALTER TABLE master.ims_task_types SET TABLESPACE tbl_master;
	
	-- 
-- Table: master.ims_task_params
-- Descripción: Parametros para un tipo de tarea
-- Scope: master
--
	CREATE TABLE master.ims_task_type_params (
		task_type_id int8 NOT NULL,	
		task_type_param_id int8 NOT NULL,
		param_type_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
	    data_type_id int8 NOT NULL,
		threshold_1 varchar NULL,
		threshold_2 varchar NULL,
		task_type_param_unit_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_task_type_params PRIMARY KEY (task_type_id, task_type_param_id)
	);
	
	ALTER TABLE master.ims_task_type_params ADD CONSTRAINT fk_task_type_params_task_types FOREIGN KEY (task_type_id) REFERENCES master.ims_task_types(task_type_id);
	ALTER TABLE master.ims_task_type_params ADD CONSTRAINT fk_task_type_params_param_types FOREIGN KEY (param_type_id) REFERENCES master.param_types(param_type_id);
	ALTER TABLE master.ims_task_type_params ADD CONSTRAINT fk_task_type_params_param_unit FOREIGN KEY (task_type_param_unit_id) REFERENCES master.element_type_param_unit(element_type_param_unit_id);

	ALTER TABLE master.ims_task_type_params SET TABLESPACE tbl_master;
	
	
-- 
-- Table: master.ims_ims_incident_alarm_condition_alarm_types
-- Descripción: Tipos de condiciones en la generacion de incidenicas a partir de alarmas
-- Scope: master
--
	CREATE TABLE master.ims_incident_alarm_condition_alarm_types (
		incident_alarm_condition_alarm_type_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_alarm_condition_alarm_types PRIMARY KEY (incident_alarm_condition_alarm_type_id)
	);
	
	ALTER TABLE master.ims_incident_alarm_condition_alarm_types SET TABLESPACE tbl_master;
	
---------------------------------------------------------
--
-- TABLES FOR STATIC SCHEMA
--
---------------------------------------------------------

-- 
-- Table: static.ims_incident_states
-- Descripción: Estados de incidencias.
-- Scope: static
--
	CREATE TABLE static.ims_incident_states (
		incident_state_id int8 NOT NULL,
		final bool NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_states PRIMARY KEY (incident_state_id)
	);

	ALTER TABLE static.ims_incident_states SET TABLESPACE tbl_static;
	
-- 
-- Table: static.ims_incident_levels
-- Descripción: Nivel de severidad de la incidencia
-- Scope: static
--
	CREATE TABLE static.ims_incident_levels (
		incident_level_id int8 NOT NULL,
		priority int4 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_levels PRIMARY KEY (incident_level_id)
	);

	ALTER TABLE static.ims_incident_levels SET TABLESPACE tbl_static;
	
---------------------------------------------------------
--
-- TABLES FOR CONF
--
---------------------------------------------------------

-- 
-- Table: conf.ims_incident_types
-- Descripción: Tipos de incidencias
-- Scope: conf
--
	CREATE TABLE conf.ims_incident_types (
		incident_type_id int8 NOT NULL,
		ini_incident_state_id int8 NOT NULL,
		incident_group_id int8 NOT NULL DEFAULT 0,
		alias varchar NOT NULL,
		description varchar NULL,
		incident_level_id int8 NOT NULL,
		default_user_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_types PRIMARY KEY (incident_type_id)
	);
	
	CREATE INDEX idx_ims_incident_types_ims_incident_states ON conf.ims_incident_types USING btree (ini_incident_state_id);
	CREATE INDEX idx_ims_incident_types_ims_incident_levels ON conf.ims_incident_types USING btree (incident_level_id);
	CREATE INDEX idx_ims_incident_types_users ON conf.ims_incident_types USING btree (default_user_id);
	
	ALTER TABLE conf.ims_incident_types ADD CONSTRAINT fk_ims_incident_types_ims_incident_groups FOREIGN KEY (incident_group_id) REFERENCES master.ims_incident_groups(incident_group_id);
	ALTER TABLE conf.ims_incident_types ADD CONSTRAINT fk_ims_incident_types_ims_incident_levels FOREIGN KEY (incident_level_id) REFERENCES static.ims_incident_levels(incident_level_id);
	ALTER TABLE conf.ims_incident_types ADD CONSTRAINT fk_ims_incident_types_ims_incident_states FOREIGN KEY (ini_incident_state_id) REFERENCES static.ims_incident_states(incident_state_id);
	ALTER TABLE conf.ims_incident_types ADD CONSTRAINT fk_ims_incident_types_users FOREIGN KEY (default_user_id) REFERENCES conf.users(user_id);
	
	ALTER TABLE conf.ims_incident_types SET TABLESPACE tbl_conf;

-- 
-- Table: conf.ims_incident_type_causes
-- Descripción: Tipos de Motivo de cada incidente 
-- Scope: conf
--

	CREATE TABLE conf.ims_incident_type_causes (
		incident_type_id int8 NOT NULL,
		incident_type_cause_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT fk_ims_incident_type_causes PRIMARY KEY (incident_type_id, incident_type_cause_id)
	);
	
	ALTER TABLE conf.ims_incident_type_causes ADD CONSTRAINT fk_ims_incident_types_causes_ims_incident_types FOREIGN KEY (incident_type_id) REFERENCES conf.ims_incident_types(incident_type_id);

	ALTER TABLE conf.ims_incident_type_causes SET TABLESPACE tbl_conf;

-- 
-- Table: conf.ims_incident_type_state_transitions
-- Descripción: Transiciones permitidas de tipos de incidencia 
-- Scope: conf
--
	CREATE TABLE conf.ims_incident_type_state_transitions (
	    incident_type_id int8 NOT NULL,
		ini_incident_state_id int8 NOT NULL,
		end_incident_state_id int8 NOT NULL,
		auto bool NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_type_state_transitions PRIMARY KEY (incident_type_id, ini_incident_state_id)
	);
	
	CREATE INDEX idx_ims_incident_type_state_transitions_ims_incident_types ON conf.ims_incident_type_state_transitions USING btree (incident_type_id);
	CREATE INDEX idx_ims_incident_type_state_transitions_ims_incid_stat_i ON conf.ims_incident_type_state_transitions USING btree (ini_incident_state_id);
	CREATE INDEX idx_ims_incident_type_state_transitions_ims_incid_stat_e ON conf.ims_incident_type_state_transitions USING btree (end_incident_state_id);
	
	ALTER TABLE conf.ims_incident_type_state_transitions ADD CONSTRAINT fk_ims_incident_type_state_transitions_ims_incident_types FOREIGN KEY (incident_type_id) REFERENCES conf.ims_incident_types(incident_type_id);
	ALTER TABLE conf.ims_incident_type_state_transitions ADD CONSTRAINT fk_ims_incident_type_state_transitions_ims_incid_stat_i FOREIGN KEY (ini_incident_state_id) REFERENCES static.ims_incident_states(incident_state_id);
	ALTER TABLE conf.ims_incident_type_state_transitions ADD CONSTRAINT fk_ims_incident_type_state_transitions_ims_incid_stat_e FOREIGN KEY (end_incident_state_id) REFERENCES static.ims_incident_states(incident_state_id);

	ALTER TABLE conf.ims_incident_type_state_transitions SET TABLESPACE tbl_conf;

-- 
-- Table: conf.ims_incident_type_tasks
-- Descripción: Tarea asociada al estado de un tipo de incidencia
-- Scope: conf
--
	CREATE TABLE conf.ims_incident_type_tasks (
		incident_type_task_id int8 NOT NULL,
	    incident_type_id int8 NOT NULL,
	    incident_state_id int8 NULL,
	    task_type_id int8 NOT NULL,
	    alias varchar NOT NULL,
		description varchar NULL,
		mandatory bool NOT NULL,
		position int4 NOT NULL,
		auto bool NOT NULL,	
		on_change varchar NULL,
		delay int4 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_type_tasks PRIMARY KEY (incident_type_task_id)
	);
	
	CREATE INDEX idx_ims_incident_type_tasks_ims_incident_types ON conf.ims_incident_type_tasks USING btree (incident_type_id);
	CREATE INDEX idx_ims_incident_type_tasks_ims_incident_states ON conf.ims_incident_type_tasks USING btree (incident_state_id);
	CREATE INDEX idx_ims_incident_type_tasks_ims_task_types ON conf.ims_incident_type_tasks USING btree (task_type_id);
	
	ALTER TABLE conf.ims_incident_type_tasks ADD CONSTRAINT fk_ims_incident_type_tasks_ims_incident_types FOREIGN KEY (incident_type_id) REFERENCES conf.ims_incident_types(incident_type_id);
	ALTER TABLE conf.ims_incident_type_tasks ADD CONSTRAINT fk_ims_incident_type_tasks_ims_incident_states FOREIGN KEY (incident_state_id) REFERENCES static.ims_incident_states(incident_state_id);
	ALTER TABLE conf.ims_incident_type_tasks ADD CONSTRAINT fk_ims_incident_type_tasks_ims_task_types FOREIGN KEY (task_type_id) REFERENCES master.ims_task_types(task_type_id);
	
	ALTER TABLE conf.ims_incident_type_tasks SET TABLESPACE tbl_conf;
	
	
-- 
-- Table: conf.ims_incident_type_task_values
-- Descripción: Parámetros de las tareas
-- Scope: conf
--
	CREATE TABLE conf.ims_incident_type_task_values (
		task_type_id int8 NOT NULL,	
		task_type_param_id int8 NOT NULL,
		incident_type_task_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_type_task_values PRIMARY KEY (task_type_id, task_type_param_id, incident_type_task_id)
	);

	ALTER TABLE conf.ims_incident_type_task_values ADD CONSTRAINT fk_incident_type_task_values_type_params FOREIGN KEY (task_type_id, task_type_param_id) REFERENCES master.ims_task_type_params(task_type_id, task_type_param_id);
	ALTER TABLE conf.ims_incident_type_task_values ADD CONSTRAINT fk_incident_type_task_values_incident_type_tasks FOREIGN KEY (incident_type_task_id) REFERENCES conf.ims_incident_type_tasks(incident_type_task_id);
	
	ALTER TABLE conf.ims_incident_type_task_values SET TABLESPACE tbl_conf;
-- 
-- Table: conf.ims_incidents
-- Descripción: Incidencia en un tramo determinado
-- Scope: conf
--
	CREATE TABLE conf.ims_incident_type_stretchs (
		incident_type_id int8 NOT NULL,
		stretch_id int8 NOT NULL,
		default_location_id int8 NULL,
		operational_plan_url varchar NULL,
		valid_for_min int4 NULL,
		alias varchar NULL,
		description varchar NULL,
		multi_instance bool NOT NULL,
		cctv_scenario_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_type_stretchs PRIMARY KEY (incident_type_id, stretch_id)
	);
	
	CREATE INDEX idx_ims_incident_type_stretchs_ims_incident_types ON conf.ims_incident_type_stretchs USING btree (incident_type_id);
	CREATE INDEX idx_ims_incident_type_stretchs_stretchs ON conf.ims_incident_type_stretchs USING btree (stretch_id);
	CREATE INDEX idx_ims_incident_type_stretchs_locations ON conf.ims_incident_type_stretchs USING btree (default_location_id);
	
	ALTER TABLE conf.ims_incident_type_stretchs ADD CONSTRAINT fk_ims_incident_type_stretchs_ims_incident_types FOREIGN KEY (incident_type_id) REFERENCES conf.ims_incident_types(incident_type_id);
	ALTER TABLE conf.ims_incident_type_stretchs ADD CONSTRAINT fk_ims_incident_type_stretchs_stretchs FOREIGN KEY (stretch_id) REFERENCES conf.stretchs(stretch_id);
	ALTER TABLE conf.ims_incident_type_stretchs ADD CONSTRAINT fk_ims_incident_type_stretchs_locations FOREIGN KEY (default_location_id) REFERENCES conf.locations(location_id);
	ALTER TABLE conf.ims_incident_type_stretchs ADD CONSTRAINT fk_ims_incident_type_stretchs_cctv_scenarios FOREIGN KEY (cctv_scenario_id) REFERENCES conf.cctv_scenarios(scenario_id);

	
	ALTER TABLE conf.ims_incident_type_stretchs SET TABLESPACE tbl_conf;
	
-- 
-- Table: conf.ims_incident_alarm_conditions
-- Descripción: Condiciones a cumplir para que se genere una determinada incidencia
-- Scope: conf
-- 
	CREATE TABLE conf.ims_incident_alarm_conditions (
		incident_alarm_condition_id int8 NOT NULL,
		incident_type_id int8 NOT NULL,
		stretch_id int8 NOT NULL,
		auto bool NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_alarms PRIMARY KEY (incident_alarm_condition_id)
	);
	
	CREATE INDEX idx_ims_incident_alarm_conditions_ims_incident_type_stretchs ON conf.ims_incident_alarm_conditions USING btree (incident_type_id, stretch_id);
	
	ALTER TABLE conf.ims_incident_alarm_conditions ADD CONSTRAINT fk_ims_incident_alarm_conditions_ims_incident_type_stretchs FOREIGN KEY (incident_type_id, stretch_id) REFERENCES conf.ims_incident_type_stretchs(incident_type_id, stretch_id);

	ALTER TABLE conf.ims_incident_alarm_conditions SET TABLESPACE tbl_conf;

	
-- 
-- Table: conf.ims_incident_alarm_condition_alarms
-- Descripción: Alarmas necesarias para que se cumpla una condición para poder generar una incidencia
-- Scope: conf
-- 
 
	CREATE TABLE conf.ims_incident_alarm_condition_alarms (
		incident_alarm_condition_alarm_id int8 NOT NULL,
		incident_alarm_condition_id int8 NOT NULL,
		incident_alarm_condition_alarm_type_id int8 NOT NULL,
		alarm_config_id int8 NOT NULL,
		element_type_id int8 NULL,
		element_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_alarm_condition_alarms PRIMARY KEY (incident_alarm_condition_alarm_id)
	);
	
	CREATE INDEX idx_ims_incident_alarm_cond_alarms_ims_incident_alarm_conds ON conf.ims_incident_alarm_condition_alarms USING btree (incident_alarm_condition_id);
	CREATE INDEX idx_ims_incident_alarm_cond_alarms_alarm_configs ON conf.ims_incident_alarm_condition_alarms USING btree (alarm_config_id);
	CREATE INDEX idx_ims_incident_alarm_cond_ac_elements ON conf.ims_incident_alarm_condition_alarms USING btree (alarm_config_id, element_type_id, element_id);
	CREATE INDEX idx_ims_incident_alarm_cond_elements ON conf.ims_incident_alarm_condition_alarms USING btree (element_type_id, element_id);
	
	ALTER TABLE conf.ims_incident_alarm_condition_alarms ADD CONSTRAINT fk_ims_incident_alarm_cond_alarms_ims_incident_alarm_condi FOREIGN KEY (incident_alarm_condition_id) REFERENCES conf.ims_incident_alarm_conditions(incident_alarm_condition_id);
	ALTER TABLE conf.ims_incident_alarm_condition_alarms ADD CONSTRAINT fk_ims_incident_alarm_cond_alarms_alarm_configs FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	ALTER TABLE conf.ims_incident_alarm_condition_alarms ADD CONSTRAINT fk_ims_incident_alarm_cond_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.ims_incident_alarm_condition_alarms ADD CONSTRAINT fk_ims_incident_alarm_cond_types FOREIGN KEY (incident_alarm_condition_alarm_type_id) REFERENCES master.ims_incident_alarm_condition_alarm_types(incident_alarm_condition_alarm_type_id);

	ALTER TABLE conf.ims_incident_alarm_condition_alarms SET TABLESPACE tbl_conf;
	
-- 
-- Table: conf.ims_incident_task_plans
-- Descripción: Planes asociados a un tipo de tarea de 'ejecición de planes' para una determinada incidencia
-- Scope: conf
--
	CREATE TABLE conf.ims_incident_task_plans (
	    incident_task_plan_id int8 NOT NULL,
	    incident_type_task_id int8 NOT NULL,
	    incident_type_id int8 NOT NULL,
		stretch_id int8 NOT NULL,
	    plan_id int8 NOT NULL,
	    position int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_task_plans PRIMARY KEY (incident_task_plan_id)
	);
	
	CREATE INDEX idx_ims_incident_task_plans_ims_incident_type_tasks ON conf.ims_incident_task_plans USING btree (incident_type_task_id);
	CREATE INDEX idx_ims_incident_task_plans_ims_incident_type_stretchs ON conf.ims_incident_task_plans USING btree (incident_type_id, stretch_id);
	CREATE INDEX idx_ims_incident_task_plans_plans ON conf.ims_incident_task_plans USING btree (plan_id);
	
	
	ALTER TABLE conf.ims_incident_task_plans ADD CONSTRAINT fk_ims_incident_task_plans_ims_incident_type_tasks FOREIGN KEY (incident_type_task_id) REFERENCES conf.ims_incident_type_tasks(incident_type_task_id);
	ALTER TABLE conf.ims_incident_task_plans ADD CONSTRAINT fk_ims_incident_task_plans_ims_incident_type_stretchs FOREIGN KEY (incident_type_id, stretch_id) REFERENCES conf.ims_incident_type_stretchs(incident_type_id, stretch_id);
	ALTER TABLE conf.ims_incident_task_plans ADD CONSTRAINT fk_ims_incident_task_plans_plans FOREIGN KEY (plan_id) REFERENCES conf.plans(plan_id);
	
	ALTER TABLE conf.ims_incident_task_plans SET TABLESPACE tbl_conf;
