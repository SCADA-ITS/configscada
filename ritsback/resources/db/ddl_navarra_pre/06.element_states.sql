--DROP TABLE IF EXISTS conf.element_state_alarms;
--DROP TABLE IF EXISTS conf.element_spread_states;
--DROP TABLE IF EXISTS master.element_type_state_transitions;

---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.element_type_state_transitions
-- Descripción: Transiciones de estados de tipos de equipos
-- Scope: master
--
	CREATE TABLE master.element_type_state_transitions (
		element_type_id int8 NOT NULL,
		current_element_type_state_id int8 NOT NULL,	
		next_element_type_state_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		editable bool NULL DEFAULT false,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_type_state_transitions PRIMARY KEY (element_type_id, current_element_type_state_id, next_element_type_state_id)
	);

	CREATE INDEX idx_element_type_state_transitions_element_type_id ON master.element_type_state_transitions USING btree (element_type_id);
	CREATE INDEX idx_element_type_state_transitions_current_element_type_state ON master.element_type_state_transitions USING btree (element_type_id, current_element_type_state_id);
	CREATE INDEX idx_element_type_state_transitions_next_element_type_state ON master.element_type_state_transitions USING btree (element_type_id, next_element_type_state_id);

	ALTER TABLE master.element_type_state_transitions ADD CONSTRAINT fk_element_type_state_transitions_current_element_type_states FOREIGN KEY (element_type_id, current_element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE master.element_type_state_transitions ADD CONSTRAINT fk_element_type_state_transitions_next_element_type_states FOREIGN KEY (element_type_id, next_element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	
	ALTER TABLE master.element_type_state_transitions SET TABLESPACE tbs_controltrafico_master;

---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------

-- 
-- Table: conf.element_spread_states
-- Descripción: Reglas para propagación de cambios de estados a otros equipos
-- Scope: conf
--
	CREATE TABLE conf.element_spread_states (
		element_spread_state_id int8 NOT NULL,
		source_element_type_id int8 NOT NULL,
		source_element_id int8 NOT NULL,
		source_element_type_state_id int8 NOT NULL,
		target_element_type_id int8 NOT NULL,
		target_element_id int8 NOT NULL,
		target_element_type_state_id int8 NOT NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_spread_states PRIMARY KEY (element_spread_state_id)
	);
	
	CREATE INDEX idx_element_spread_states_source_element_type ON conf.element_spread_states USING btree (source_element_type_id);
	CREATE INDEX idx_element_spread_states_source_element ON conf.element_spread_states USING btree (source_element_type_id, source_element_id);
	CREATE INDEX idx_element_spread_states_source_element_state ON conf.element_spread_states USING btree (source_element_type_id, source_element_type_state_id);
	CREATE INDEX idx_element_spread_states_target_element_type ON conf.element_spread_states USING btree (target_element_type_id);
	CREATE INDEX idx_element_spread_states_target_element ON conf.element_spread_states USING btree (target_element_type_id, target_element_id);
	CREATE INDEX idx_element_spread_states_target_element_state ON conf.element_spread_states USING btree (target_element_type_id, target_element_type_state_id);
	
	ALTER TABLE conf.element_spread_states ADD CONSTRAINT fk_element_spread_states_source_element_type FOREIGN KEY (source_element_type_id) REFERENCES master.element_types(element_type_id);
	ALTER TABLE conf.element_spread_states ADD CONSTRAINT fk_element_spread_states_source_element FOREIGN KEY (source_element_type_id, source_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.element_spread_states ADD CONSTRAINT fk_element_spread_states_source_element_state FOREIGN KEY (source_element_type_id, source_element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE conf.element_spread_states ADD CONSTRAINT fk_element_spread_states_target_element_type FOREIGN KEY (target_element_type_id) REFERENCES master.element_types(element_type_id);
	ALTER TABLE conf.element_spread_states ADD CONSTRAINT fk_element_spread_states_target_element FOREIGN KEY (target_element_type_id, target_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE conf.element_spread_states ADD CONSTRAINT fk_element_spread_states_target_element_state FOREIGN KEY (target_element_type_id, target_element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	
	ALTER TABLE conf.element_spread_states SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.element_state_alarms
-- Descripción: Alarmas asociadas a cambios de estados
-- Scope: conf
--
	CREATE TABLE conf.element_state_alarms (
		element_type_id int8 NOT NULL,
		element_type_state_id int8 NOT NULL,
		alarm_config_id int8 NOT NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_state_alarms PRIMARY KEY (element_type_id, element_type_state_id)
	);
	
	CREATE INDEX idx_element_state_alarms_element_type_id ON conf.element_state_alarms USING btree (element_type_id);
	CREATE INDEX idx_element_state_alarm_alarm_config_id ON conf.element_state_alarms USING btree (alarm_config_id);
	
	ALTER TABLE conf.element_state_alarms ADD CONSTRAINT fk_element_state_alarms_element_type_state FOREIGN KEY (element_type_id, element_type_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE conf.element_state_alarms ADD CONSTRAINT fk_element_state_alarm_config FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	
	ALTER TABLE conf.element_state_alarms SET TABLESPACE tbs_controltrafico_conf;