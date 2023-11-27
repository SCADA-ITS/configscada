---------------------------------------------------------
--
-- TABLES FOR STATIC SCHEMA
--
---------------------------------------------------------

-- 
-- Table: static.sm_thresholds
-- Descripción: 
-- Scope: static
--
	CREATE TABLE static.sm_thresholds (
		threshold_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		data_type_id int8 NOT NULL,
		value varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_thresholds PRIMARY KEY (threshold_id)
	);

	ALTER TABLE static.sm_thresholds ADD CONSTRAINT fk_sm_thresholds_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE static.sm_thresholds SET TABLESPACE tbl_static;
	
-- 
-- Table: static.sm_timers
-- Descripción: 
-- Scope: static
--
	CREATE TABLE static.sm_timers (
		timer_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		threshold_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_type_param_id int8 NOT NULL,
		param_type_id int8 NOT NULL,	
		min_elements_match_threshold int4 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_timers PRIMARY KEY (timer_id)
	);
	
	ALTER TABLE static.sm_timers ADD CONSTRAINT fk_sm_timer_sm_thresholds FOREIGN KEY (threshold_id) REFERENCES static.sm_thresholds(threshold_id);
	ALTER TABLE static.sm_timers ADD CONSTRAINT fk_sm_timer_params_element_types FOREIGN KEY (element_type_param_id, element_type_id, param_type_id) REFERENCES master.element_type_params(element_type_param_id, element_type_id, param_type_id);
	ALTER TABLE static.sm_timers SET TABLESPACE tbl_static;

-- 
-- Table: Table: static.sm_conditions
-- Descripción:  
-- Scope: 
--
	CREATE TABLE static.sm_conditions (
		
		condition_id int8 NOT NULL,
		threshold_operation_id int8 NOT NULL,
		threshold_val1_id int8 NOT NULL,
		threshold_val2_id int8 NULL,
		element_type_id int8 NULL,
		element_type_param_id int8 NULL,
		param_type_id int8 NULL,
		min_elements_match_threshold int4 NULL,
		timer_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_conditions PRIMARY KEY (condition_id)
	);
	
	ALTER TABLE static.sm_conditions ADD CONSTRAINT fk_sm_conditions_threshold_operations FOREIGN KEY (threshold_operation_id) REFERENCES master.threshold_operations(threshold_operation_id);
	ALTER TABLE static.sm_conditions ADD CONSTRAINT fk_sm_conditions_thresholdval1 FOREIGN KEY (threshold_val1_id) REFERENCES static.sm_thresholds(threshold_id);
	ALTER TABLE static.sm_conditions ADD CONSTRAINT fk_sm_conditions_thresholdval2 FOREIGN KEY (threshold_val2_id) REFERENCES static.sm_thresholds(threshold_id);
	ALTER TABLE static.sm_conditions ADD CONSTRAINT fk_sm_conditions_element_type_params FOREIGN KEY (element_type_param_id, element_type_id, param_type_id) REFERENCES master.element_type_params(element_type_param_id, element_type_id, param_type_id);
	ALTER TABLE static.sm_conditions ADD CONSTRAINT fk_sm_conditions_timers FOREIGN KEY (timer_id) REFERENCES static.sm_timers(timer_id);
	ALTER TABLE static.sm_conditions SET TABLESPACE tbl_static;
	
-- 
-- Table: static.sm_state_machines
-- Descripción: 
-- Scope: static
--
	CREATE TABLE static.sm_state_machines (
		state_machine_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_state_machines PRIMARY KEY (state_machine_id)
	);
	
	ALTER TABLE static.sm_state_machines SET TABLESPACE tbl_static;

-- 
-- Table: static.sm_states
-- Descripción: 
-- Scope: static
--

	CREATE TABLE static.sm_states (
		state_machine_id int8 NOT NULL,
		state_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		init_state bool NULL,
		t_min int8  NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_states PRIMARY KEY (state_machine_id, state_id)
	);
	
	ALTER TABLE static.sm_states ADD CONSTRAINT fk_sm_state_sm_state_machines FOREIGN KEY (state_machine_id) REFERENCES static.sm_state_machines(state_machine_id);
	ALTER TABLE static.sm_states SET TABLESPACE tbl_static;

-- 
-- Table: static.sm_state_timers
-- Descripción: 
-- Scope: static
--

	CREATE TABLE static.sm_state_timers (
		state_machine_id int8 NOT NULL,
		state_id int8 NOT NULL,
		timer_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_state_timers PRIMARY KEY (state_machine_id, state_id, timer_id)
	);
	
	ALTER TABLE static.sm_state_timers SET TABLESPACE tbl_static;
	ALTER TABLE static.sm_state_timers ADD CONSTRAINT fk_sm_state_timers_sm_state FOREIGN KEY (state_machine_id, state_id) REFERENCES static.sm_states(state_machine_id, state_id);
	ALTER TABLE static.sm_state_timers ADD CONSTRAINT fk_sm_state_timers_sm_timer FOREIGN KEY (timer_id) REFERENCES static.sm_timers(timer_id);

	
-- 
-- Table: static.sm_transitions
-- Descripción: 
-- Scope: static
--

	CREATE TABLE static.sm_transitions (
		state_machine_id int8 NOT NULL,
		state_id int8 NOT NULL,
		transition_id int8 NOT NULL,
		nexus_and bool NOT NULL,
		next_state_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_transitions PRIMARY KEY (state_machine_id, state_id, transition_id)
	);
	
	ALTER TABLE static.sm_transitions ADD CONSTRAINT fk_sm_trasition_sm_states FOREIGN KEY (state_machine_id, state_id) REFERENCES static.sm_states(state_machine_id, state_id);
	ALTER TABLE static.sm_transitions ADD CONSTRAINT fk_sm_trasition_sm_next_states FOREIGN KEY (state_machine_id, next_state_id) REFERENCES static.sm_states(state_machine_id, state_id);
	ALTER TABLE static.sm_transitions SET TABLESPACE tbl_static;


-- 
-- Table: static.sm_transitions_conditions
-- Descripción: 
-- Scope: static
--

	CREATE TABLE static.sm_transition_conditions (
		state_machine_id int8 NOT NULL,
		state_id int8 NOT NULL,
		transition_id int8 NOT NULL,
		condition_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sm_transition_conditions PRIMARY KEY (state_machine_id, state_id, transition_id, condition_id)
	);
	
	ALTER TABLE static.sm_transition_conditions ADD CONSTRAINT fk_sm_transition_conditions_transitions FOREIGN KEY (state_machine_id, state_id, transition_id) REFERENCES static.sm_transitions(state_machine_id, state_id, transition_id);
	ALTER TABLE static.sm_transition_conditions ADD CONSTRAINT fk_sm_transition_conditions_conditions FOREIGN KEY (condition_id) REFERENCES static.sm_conditions(condition_id);
	ALTER TABLE static.sm_transition_conditions SET TABLESPACE tbl_static;


	
	