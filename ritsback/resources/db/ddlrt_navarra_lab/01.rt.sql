SET client_min_messages TO WARNING;
DO $$
BEGIN
    IF EXISTS (
        SELECT schema_name 
        FROM information_schema.schemata 
        WHERE schema_name = 'rt'
    ) THEN
        DROP SCHEMA rt CASCADE;
    END IF;
END $$;

CREATE SCHEMA rt;

-- 
-- Table: rt.audit_log_id
-- Descripción: Auditorias que tienen que ser reconocidas
-- Scope: rt
--
	CREATE TABLE rt.audit_logs (
		audit_log_id int8 NOT NULL,	
		log_type_id int8 NOT NULL,
		log_subtype_id int8  NULL,
		user_id int8 NOT NULL,
		log_level_id int8 NOT NULL,
		time_stamp timestamptz NOT NULL,
		management_area_ids varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_audit_logs PRIMARY KEY (audit_log_id, log_type_id)
	);

	CREATE INDEX idx_audit_logs_log_type_id ON rt.audit_logs USING btree (log_type_id);
	CREATE INDEX idx_audit_logs_log_subtype_id ON rt.audit_logs USING btree (log_subtype_id);
	CREATE INDEX idx_audit_logs_log_level_id ON rt.audit_logs USING btree (log_level_id);
	CREATE INDEX idx_audit_logs_user_id ON rt.audit_logs USING btree (user_id);

	ALTER TABLE rt.audit_logs ADD CONSTRAINT fk_audit_logs_log_type FOREIGN KEY (log_type_id) REFERENCES master.log_types(log_type_id);
	ALTER TABLE rt.audit_logs ADD CONSTRAINT fk_audit_logs_log_subtype FOREIGN KEY (log_subtype_id) REFERENCES master.log_subtypes(log_subtype_id);
	ALTER TABLE rt.audit_logs ADD CONSTRAINT fk_audit_logs_levels FOREIGN KEY (log_level_id) REFERENCES master.log_levels(log_level_id);
	ALTER TABLE rt.audit_logs ADD CONSTRAINT fk_audit_logs_users FOREIGN KEY (user_id) REFERENCES conf.users(user_id);

	ALTER TABLE rt.audit_logs SET TABLESPACE tbs_controltrafico_rt;	
	
-- 
-- Table: rt.audit_log_values
-- Descripción:
-- Scope: rt
--
	CREATE TABLE rt.audit_log_values (
		audit_log_id int8 NOT NULL,
		log_type_id int8 NOT NULL,
		log_type_param_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_audit_log_values PRIMARY KEY (audit_log_id, log_type_id, log_type_param_id)
	);

	CREATE INDEX idx_audit_log_values_audit_log_id ON rt.audit_log_values USING btree (audit_log_id);
	
	CREATE INDEX idx_audit_log_values_log_type_params ON rt.audit_log_values USING btree (log_type_id, log_type_param_id);
	CREATE INDEX idx_audit_log_values_audit_logs ON rt.audit_log_values USING btree (audit_log_id, log_type_id);

	ALTER TABLE rt.audit_log_values ADD CONSTRAINT fk_audit_log_values_log_type_params FOREIGN KEY (log_type_id, log_type_param_id) REFERENCES master.log_type_params(log_type_id, log_type_param_id);
	ALTER TABLE rt.audit_log_values ADD CONSTRAINT fk_audit_log_values_audit_logs FOREIGN KEY (audit_log_id, log_type_id) REFERENCES rt.audit_logs(audit_log_id, log_type_id);
	
	ALTER TABLE rt.audit_log_values SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.element_states
-- Descripción: Tabla de estados de equipos
-- Scope: rt
--
	CREATE TABLE rt.element_states (
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		previous_state_id int8 NOT NULL,
		current_state_id int8 NOT NULL,
		maintenance_comment varchar NULL,
	  	created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_element_states PRIMARY KEY (element_type_id, element_id)
	);
	
	CREATE INDEX idx_element_states_elements_element_type_states_previous ON rt.element_states USING btree (element_type_id, previous_state_id);
	CREATE INDEX idx_element_states_elements_element_type_states_current ON rt.element_states USING btree (element_type_id, current_state_id);
	
	ALTER TABLE rt.element_states ADD CONSTRAINT fk_element_states_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE rt.element_states ADD CONSTRAINT fk_element_states_elements_element_type_states_previous FOREIGN KEY (element_type_id, previous_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);
	ALTER TABLE rt.element_states ADD CONSTRAINT fk_element_states_elements_element_type_states_current FOREIGN KEY (element_type_id, current_state_id) REFERENCES master.element_type_states(element_type_id, element_type_state_id);

	ALTER TABLE rt.element_states SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.alarms
-- Descripción: Tabla de alarmas activas
-- Scope: rt
--
	CREATE TABLE rt.alarms (
		alarm_id int8 NOT NULL,
		alarm_config_id int8 NOT NULL,
		creation_user_id int8 NOT NULL DEFAULT 1,		
		recognized_user_id int8 NULL,		
		recognized timestamptz NULL,
		recognized_comment varchar NULL,
		activation timestamptz NULL,
		activation_comment varchar NULL,		
		deactivation_user_id int8 NULL, 
		deactivation timestamptz NULL,
		deactivation_comment varchar NULL,
		disagree bool NULL,
		source_element_type_id int8 NOT NULL,
		source_element_id int8 NOT NULL,		
		target_element_type_id int8 NOT NULL,
		target_element_id int8 NOT NULL,		
		filtered_until timestamptz NULL,
  		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_alarms PRIMARY KEY (alarm_id)
	);
	
	CREATE INDEX idx_alarm_alarm_configs ON rt.alarms USING btree (alarm_config_id);
	CREATE INDEX idx_alarm_source_elements ON rt.alarms USING btree (source_element_type_id, source_element_id);
	CREATE INDEX idx_alarm_target_elements ON rt.alarms USING btree (target_element_type_id, target_element_id);
	CREATE INDEX idx_ims_alarm_users_creation ON rt.alarms USING btree (creation_user_id);
	CREATE INDEX idx_ims_alarm_users_recognition ON rt.alarms USING btree (recognized_user_id);
	CREATE INDEX idx_ims_alarm_users_deactivation ON rt.alarms USING btree (deactivation_user_id);
	
	ALTER TABLE rt.alarms ADD CONSTRAINT fk_alarm_alarm_configs FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	ALTER TABLE rt.alarms ADD CONSTRAINT fk_alarm_source_elements FOREIGN KEY (source_element_type_id, source_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE rt.alarms ADD CONSTRAINT fk_alarm_target_elements FOREIGN KEY (target_element_type_id, target_element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE rt.alarms ADD CONSTRAINT fk_ims_alarm_users_creation FOREIGN KEY (creation_user_id) REFERENCES conf.users(user_id);
	ALTER TABLE rt.alarms ADD CONSTRAINT fk_ims_alarm_users_recognition FOREIGN KEY (recognized_user_id) REFERENCES conf.users(user_id);
	ALTER TABLE rt.alarms ADD CONSTRAINT fk_ims_alarm_users_deactivation FOREIGN KEY (deactivation_user_id) REFERENCES conf.users(user_id);
	
	ALTER TABLE rt.alarms SET TABLESPACE tbs_controltrafico_rt;

-- 
-- Table: rt.ims_incident_reports
-- Descripción: Partes de incidencias
-- Scope: rt
--

	CREATE SEQUENCE rt.last_incident_report_id START 1;
		
	CREATE TABLE rt.ims_incident_reports (
		incident_report_id int8 default nextval('rt.last_incident_report_id'),
		incident_type_id int8 NOT NULL,
		incident_type_cause_id int8 NULL,
		affection_stretch_id int8 NOT NULL,
		location_id int8 NULL,
		incident_state_id int8 NOT NULL,
		incident_level_id int8 NOT NULL,
		current_user_id int8 NULL,
		valid_for_min int4 NULL,
		auto_close_in_min int4 NULL,
		confirm bool NOT NULL,
		road_impact_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		last_Update timestamptz NOT NULL,
		generated_at timestamptz NOT NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_reports PRIMARY KEY (incident_report_id)
	);
	
	CREATE INDEX idx_ims_incident_reports_ims_incident_levels ON rt.ims_incident_reports USING btree (incident_level_id);
	CREATE INDEX idx_ims_incident_reports_ims_incident_states ON rt.ims_incident_reports USING btree (incident_state_id);
	CREATE INDEX idx_ims_incident_reports_ims_incidents ON rt.ims_incident_reports USING btree (incident_type_id, affection_stretch_id);
	CREATE INDEX idx_ims_incident_reports_ims_incident_types_causes ON rt.ims_incident_reports USING btree (incident_type_id, incident_type_cause_id);
	CREATE INDEX idx_ims_incident_reports_locations ON rt.ims_incident_reports USING btree (location_id);
	CREATE INDEX idx_ims_incident_reports_users ON rt.ims_incident_reports USING btree (current_user_id);
	CREATE INDEX idx_ims_incident_reports_impacts ON rt.ims_incident_reports USING btree (road_impact_id);

	ALTER TABLE rt.ims_incident_reports ADD CONSTRAINT fk_ims_incident_reports_ims_incident_levels FOREIGN KEY (incident_level_id) REFERENCES static.ims_incident_levels(incident_level_id);
	ALTER TABLE rt.ims_incident_reports ADD CONSTRAINT fk_ims_incident_reports_ims_incident_states FOREIGN KEY (incident_state_id) REFERENCES static.ims_incident_states(incident_state_id);
	ALTER TABLE rt.ims_incident_reports ADD CONSTRAINT fk_ims_incident_reports_ims_incident_type_stretchs FOREIGN KEY (incident_type_id, affection_stretch_id) REFERENCES conf.ims_incident_type_stretchs(incident_type_id, stretch_id);
	ALTER TABLE rt.ims_incident_reports ADD CONSTRAINT fk_ims_incident_reports_ims_incident_types_causes FOREIGN KEY (incident_type_id, incident_type_cause_id) REFERENCES conf.ims_incident_type_causes(incident_type_id, incident_type_cause_id);
	ALTER TABLE rt.ims_incident_reports ADD CONSTRAINT fk_ims_incident_reports_locations FOREIGN KEY (location_id) REFERENCES conf.locations(location_id);
	ALTER TABLE rt.ims_incident_reports ADD CONSTRAINT fk_ims_incident_reports_users FOREIGN KEY (current_user_id) REFERENCES conf.users(user_id);
	ALTER TABLE rt.ims_incident_reports ADD CONSTRAINT fk_ims_incident_reports_impacts FOREIGN KEY (road_impact_id) REFERENCES master.road_impacts(road_impact_id);
		
	ALTER TABLE rt.ims_incident_reports SET TABLESPACE tbs_controltrafico_rt;

-- 
-- Table: rt.ims_incident_report_task_states
-- Descripción: Estado de las tareas en un parte de incidencia
-- Scope: rt
--
	CREATE TABLE rt.ims_incident_report_task_states (
	    incident_report_id int8 NOT NULL,
    	incident_type_task_id int8 NOT NULL,
	    completed_at timestamptz NULL,
	    error bool NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_states PRIMARY KEY (incident_report_id, incident_type_task_id)
	);
	
	CREATE INDEX idx_ims_incident_report_task_states_ims_incident_reports ON rt.ims_incident_report_task_states USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_task_states_ims_incident_type_tasks ON rt.ims_incident_report_task_states USING btree (incident_type_task_id);
	
	ALTER TABLE rt.ims_incident_report_task_states ADD CONSTRAINT fk_ims_incident_report_task_states_ims_incident_reports FOREIGN KEY (incident_report_id) REFERENCES rt.ims_incident_reports(incident_report_id);
	ALTER TABLE rt.ims_incident_report_task_states ADD CONSTRAINT fk_ims_incident_report_task_states_ims_incident_type_tasks FOREIGN KEY (incident_type_task_id) REFERENCES conf.ims_incident_type_tasks(incident_type_task_id);
	
	ALTER TABLE rt.ims_incident_report_task_states SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.ims_incident_report_task_values
-- Descripción: Valores dianamicos de los parametros de la tarea
-- Scope: rt
--
	CREATE TABLE rt.ims_incident_report_task_values (
	    incident_report_id int8 NOT NULL,
    	task_type_id int8 NOT NULL,	
		task_type_param_id int8 NOT NULL,
		incident_type_task_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_values PRIMARY KEY (incident_report_id, task_type_id, task_type_param_id, incident_type_task_id)
	);
	
	CREATE INDEX idx_ims_incident_report_task_values_ims_incident_reports ON rt.ims_incident_report_task_values USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_task_values_type_params ON rt.ims_incident_report_task_values USING btree (task_type_id, task_type_param_id);
	CREATE INDEX idx_ims_incident_report_task_values_incident_type_tasks ON rt.ims_incident_report_task_values USING btree (incident_type_task_id);
	
	ALTER TABLE rt.ims_incident_report_task_values ADD CONSTRAINT fk_ims_incident_report_task_values_ims_incident_reports FOREIGN KEY (incident_report_id) REFERENCES rt.ims_incident_reports(incident_report_id);
	ALTER TABLE rt.ims_incident_report_task_values ADD CONSTRAINT fk_ims_incident_report_task_values_type_params FOREIGN KEY (task_type_id, task_type_param_id) REFERENCES master.ims_task_type_params(task_type_id, task_type_param_id);
	ALTER TABLE rt.ims_incident_report_task_values ADD CONSTRAINT fk_ims_incident_report_task_values_incident_type_tasks FOREIGN KEY (incident_type_task_id) REFERENCES conf.ims_incident_type_tasks(incident_type_task_id);
		
	ALTER TABLE rt.ims_incident_report_task_values SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.ims_incident_report_task_elements
-- Descripción:
-- Scope: rt
--
	CREATE TABLE rt.ims_incident_report_task_elements (
	    incident_report_id int8 NOT NULL,
		incident_type_task_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_elements PRIMARY KEY (incident_report_id, incident_type_task_id, element_type_id, element_id)
	);
	
	CREATE INDEX idx_ims_incident_report_task_element_task_states ON rt.ims_incident_report_task_elements USING btree (incident_report_id, incident_type_task_id);
	CREATE INDEX idx_ims_incident_report_task_element_element ON rt.ims_incident_report_task_elements USING btree (element_type_id, element_id);
	
	ALTER TABLE rt.ims_incident_report_task_elements ADD CONSTRAINT fk_ims_incident_report_task_element_task_states FOREIGN KEY (incident_report_id, incident_type_task_id) REFERENCES rt.ims_incident_report_task_states(incident_report_id, incident_type_task_id);
	ALTER TABLE rt.ims_incident_report_task_elements ADD CONSTRAINT fk_ims_incident_report_task_element_element FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
		
	ALTER TABLE rt.ims_incident_report_task_elements SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.ims_incident_report_task_plan_states
-- Descripción: Estado de un plan en un parte de incidencia
-- Scope: rt
--
	CREATE TABLE rt.ims_incident_report_task_plan_states (
	    incident_report_id int8 NOT NULL,
	    incident_task_plan_id int8 NOT NULL,
	    completed_at timestamptz NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_plan_states PRIMARY KEY (incident_report_id, incident_task_plan_id)
	);
	
	CREATE INDEX idx_ims_incident_report_task_plan_states_reports ON rt.ims_incident_report_task_plan_states USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_task_plan_states_task_plans ON rt.ims_incident_report_task_plan_states USING btree (incident_task_plan_id);
	
	ALTER TABLE rt.ims_incident_report_task_plan_states ADD CONSTRAINT fk_ims_incident_report_task_plan_states_ims_incident_reports FOREIGN KEY (incident_report_id) REFERENCES rt.ims_incident_reports(incident_report_id);
	ALTER TABLE rt.ims_incident_report_task_plan_states ADD CONSTRAINT fk_ims_incident_report_task_plan_states_ims_incident_task_plans FOREIGN KEY (incident_task_plan_id) REFERENCES conf.ims_incident_task_plans(incident_task_plan_id);
	
	ALTER TABLE rt.ims_incident_report_task_plan_states SET TABLESPACE tbs_controltrafico_rt;

-- 
-- Table: rt.ims_incident_report_logs
-- Descripción: Traza de un parte de incidencia
-- Scope: rt
--

	CREATE SEQUENCE rt.last_incident_report_log_id START 1;
		
	CREATE TABLE rt.ims_incident_report_logs (
		incident_report_log_id int8 default nextval('rt.last_incident_report_log_id'),
		incident_report_id int8 NOT NULL,
		log_subtype_id int8 NULL,
		user_id int8 NOT NULL,
		level int4 NOT NULL,
		log_timestamp timestamptz NOT null,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_logs PRIMARY KEY (incident_report_log_id)
	);
	
	CREATE INDEX idx_ims_incident_report_logs_ims_incident_log_levels ON rt.ims_incident_report_logs USING btree (level);
	
	CREATE INDEX idx_ims_incident_report_logs_ims_incident_reports ON rt.ims_incident_report_logs USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_logs_users ON rt.ims_incident_report_logs USING btree (user_id);
	CREATE INDEX idx_ims_incident_report_logs_log_subtype ON rt.ims_incident_report_logs USING btree (log_subtype_id);
	
	ALTER TABLE rt.ims_incident_report_logs ADD CONSTRAINT fk_ims_incident_report_logs_ims_incident_reports FOREIGN KEY (incident_report_id) REFERENCES rt.ims_incident_reports(incident_report_id);
	ALTER TABLE rt.ims_incident_report_logs ADD CONSTRAINT fk_ims_incident_report_logs_users FOREIGN KEY (user_id) REFERENCES conf.users(user_id);
	ALTER TABLE rt.ims_incident_report_logs ADD CONSTRAINT fk_ims_incident_report_logs_log_subtype FOREIGN KEY (log_subtype_id) REFERENCES master.log_subtypes(log_subtype_id);
	
	ALTER TABLE rt.ims_incident_report_logs SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.ims_incident_report_log_values
-- Descripción: Datos de la traza de un parte de incidencia
-- Scope: rt
--
	CREATE TABLE rt.ims_incident_report_log_values (
		incident_report_log_id int8 NOT NULL,
		log_type_id int8 NOT NULL,
		log_type_param_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_log_values PRIMARY KEY (incident_report_log_id, log_type_id, log_type_param_id)
	);
	
	CREATE INDEX idx_ims_incident_report_log_values_ims_incident_report_logs ON rt.ims_incident_report_log_values USING btree (incident_report_log_id);
	CREATE INDEX idx_ims_incident_report_log_values_log_type_params ON rt.ims_incident_report_log_values USING btree (log_type_id, log_type_param_id);
	
	ALTER TABLE rt.ims_incident_report_log_values ADD CONSTRAINT fk_ims_incident_report_log_values_ims_incident_report_logs FOREIGN KEY (incident_report_log_id) REFERENCES rt.ims_incident_report_logs(incident_report_log_id);
	ALTER TABLE rt.ims_incident_report_log_values ADD CONSTRAINT fk_ims_incident_report_log_values_log_type_params FOREIGN KEY (log_type_id, log_type_param_id) REFERENCES master.log_type_params(log_type_id, log_type_param_id);
	
	ALTER TABLE rt.ims_incident_report_log_values SET TABLESPACE tbs_controltrafico_rt;
	
	-- 
-- Table: conf.ims_incident_report_alarms
-- Descripción: Alarmas que han producido reportes
-- Scope: rt
--
	CREATE TABLE rt.ims_incident_report_alarms (
	    incident_report_id int8 NOT NULL,
		alarm_config_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		activation timestamptz NULL,
		enabled bool NULL,
		visible bool NULL,
  		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_alarms  PRIMARY KEY (incident_report_id, alarm_config_id, element_type_id, element_id)
	);
	
	CREATE INDEX idx_ims_incident_report_alarms_incident_reports ON rt.ims_incident_report_alarms USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_alarms_alarm_configs ON rt.ims_incident_report_alarms USING btree (alarm_config_id);
	CREATE INDEX idx_ims_incident_report_alarms_elements ON rt.ims_incident_report_alarms USING btree (element_type_id, element_id);

	ALTER TABLE rt.ims_incident_report_alarms ADD CONSTRAINT fk_ims_incident_report_alarms_incident_reports FOREIGN KEY (incident_report_id) REFERENCES rt.ims_incident_reports(incident_report_id);
	ALTER TABLE rt.ims_incident_report_alarms ADD CONSTRAINT fk_ims_incident_report_alarms_alarm_configs FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	ALTER TABLE rt.ims_incident_report_alarms ADD CONSTRAINT fk_ims_incident_report_alarms_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE rt.ims_incident_report_alarms SET TABLESPACE tbs_controltrafico_rt;
	
	-- 
-- Table: static.ims_incident_param_values
-- Descripción: The value for each incident param
-- Scope: rt
--
	CREATE TABLE rt.ims_incident_param_values (
		incident_param_id int8 NOT NULL,
		incident_report_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
	    
		CONSTRAINT pk_ims_incident_param_values PRIMARY KEY (incident_report_id, incident_param_id)
	);
	
	CREATE INDEX idx_ims_incident_reports ON rt.ims_incident_param_values USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_params ON rt.ims_incident_param_values USING btree (incident_param_id);
	
	ALTER TABLE rt.ims_incident_param_values ADD CONSTRAINT fk_ims_incident_reports FOREIGN KEY (incident_report_id) REFERENCES rt.ims_incident_reports(incident_report_id);
	ALTER TABLE rt.ims_incident_param_values ADD CONSTRAINT fk_ims_incident_params FOREIGN KEY (incident_param_id) REFERENCES static.ims_incident_params(incident_param_id);
	
	ALTER TABLE rt.ims_incident_param_values SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.ims_delayed_incident_reports
-- Descripción: Delayed Incident Reports
-- Scope: rt
--
	
	CREATE SEQUENCE rt.last_delayed_incident_report_id START 1;
	
	CREATE TABLE rt.ims_delayed_incident_reports (
	    id int8 DEFAULT nextval('rt.last_delayed_incident_report_id'),
	    incident_type_id int8 NOT NULL,
	    location_id int8 NULL,
	    affection_stretch_id int8 NOT NULL,
	    user_id int8 NULL,
	    road_impact_id int8 NULL,
	    delayed_incident_state_type_id int8 NOT NULL DEFAULT 0,
	    cron_expression varchar(255) NULL,
	    auto_close_in_min int4 NULL,
	    enabled bool NULL,
		visible bool NULL,
	    last_Update timestamptz NOT NULL DEFAULT now(),
	    created_at timestamptz NOT NULL DEFAULT now(),
	    updated_at timestamptz NOT NULL DEFAULT now(),
	    PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_ims_delayed_incident_reports_incident_type ON rt.ims_delayed_incident_reports USING btree (incident_type_id);
	CREATE INDEX idx_ims_delayed_incident_reports_location ON rt.ims_delayed_incident_reports USING btree (location_id);
	CREATE INDEX idx_ims_delayed_incident_reports_user ON rt.ims_delayed_incident_reports USING btree (user_id);
	CREATE INDEX idx_ims_delayed_incident_reports_road_impact ON rt.ims_delayed_incident_reports USING btree (road_impact_id);
	CREATE INDEX idx_ims_delayed_incident_state_type ON rt.ims_delayed_incident_reports USING btree (delayed_incident_state_type_id);
	CREATE INDEX idx_ims_delayed_incident_affection_stretch ON rt.ims_delayed_incident_reports USING btree (affection_stretch_id);

	ALTER TABLE rt.ims_delayed_incident_reports ADD CONSTRAINT fk_ims_delayed_incident_reports_incident_type FOREIGN KEY (incident_type_id) REFERENCES conf.ims_incident_types(incident_type_id);
	ALTER TABLE rt.ims_delayed_incident_reports ADD CONSTRAINT fk_ims_delayed_incident_reports_location FOREIGN KEY (location_id) REFERENCES conf.locations(location_id);
	ALTER TABLE rt.ims_delayed_incident_reports ADD CONSTRAINT fk_ims_delayed_incident_reports_user FOREIGN KEY (user_id) REFERENCES conf.users(user_id);
	ALTER TABLE rt.ims_delayed_incident_reports ADD CONSTRAINT fk_ims_delayed_incident_reports_road_impact FOREIGN KEY (road_impact_id) REFERENCES master.road_impacts(road_impact_id);
	ALTER TABLE rt.ims_delayed_incident_reports ADD CONSTRAINT fk_ims_delayed_incident_state_type FOREIGN KEY (delayed_incident_state_type_id) REFERENCES master.ims_delayed_incident_state_types(delayed_incident_state_type_id);
	ALTER TABLE rt.ims_delayed_incident_reports ADD CONSTRAINT fk_ims_delayed_incident_affection_stretch FOREIGN KEY (affection_stretch_id) REFERENCES conf.stretchs(stretch_id);
	
	ALTER TABLE rt.ims_delayed_incident_reports SET TABLESPACE tbs_controltrafico_rt;
-- 
-- Table: rt.ims_delayed_incident_report_alarms
-- Descripción: Alarmas que han producido reportes
-- Scope: rt
--
	
	CREATE TABLE rt.ims_delayed_incident_report_alarms (
	    delayed_incident_report_id int8 NOT NULL,
		alarm_config_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		activation timestamptz NULL,
		enabled bool NULL,
		visible bool NULL,
  		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_delayed_incident_report_alarms  PRIMARY KEY (delayed_incident_report_id, alarm_config_id, element_type_id, element_id)
	);
	
	CREATE INDEX idx_ims_delayed_incident_report_alarms_delayed_incident_reports ON rt.ims_delayed_incident_report_alarms USING btree (delayed_incident_report_id);
	CREATE INDEX idx_ims_delayed_incident_report_alarms_alarm_configs ON rt.ims_delayed_incident_report_alarms USING btree (alarm_config_id);
	CREATE INDEX idx_ims_delayed_incident_report_alarms_elements ON rt.ims_delayed_incident_report_alarms USING btree (element_type_id, element_id);

	ALTER TABLE rt.ims_delayed_incident_report_alarms ADD CONSTRAINT fk_ims_delayed_incident_report_alarms_ims_delayed_incident_reports FOREIGN KEY (delayed_incident_report_id) REFERENCES rt.ims_delayed_incident_reports(id);
	ALTER TABLE rt.ims_delayed_incident_report_alarms ADD CONSTRAINT fk_ims_delayed_incident_report_alarms_alarm_configs FOREIGN KEY (alarm_config_id) REFERENCES conf.alarm_configs(alarm_config_id);
	ALTER TABLE rt.ims_delayed_incident_report_alarms ADD CONSTRAINT fk_ims_delayed_incident_report_alarms_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	ALTER TABLE rt.ims_delayed_incident_report_alarms SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.element_values
-- Descripción: Parámetros de los equipos
-- Scope: rt
--
	CREATE TABLE rt.element_values (
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
		CONSTRAINT pk_element_values_rt PRIMARY KEY (element_type_id, element_type_param_id, param_type_id, element_id)
	);

	CREATE INDEX idx_element_values_element_id_rt ON rt.element_values USING btree (element_id);
	
	CREATE INDEX idx_element_values_element_type_params_rt ON rt.element_values USING btree (element_type_id, element_type_param_id, param_type_id);
	CREATE INDEX idx_element_values_elements_rt ON rt.element_values USING btree (element_type_id, element_id);
	CREATE INDEX idx_element_values_element_value_states_rt ON rt.element_values USING btree (element_value_state_id);

	ALTER TABLE rt.element_values ADD CONSTRAINT fk_element_values_element_type_params_rt FOREIGN KEY (element_type_id, element_type_param_id, param_type_id) REFERENCES master.element_type_params(element_type_id, element_type_param_id, param_type_id);
	ALTER TABLE rt.element_values ADD CONSTRAINT fk_element_values_elements_rt FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	ALTER TABLE rt.element_values ADD CONSTRAINT fk_element_values_element_value_states_rt FOREIGN KEY (element_value_state_id) REFERENCES master.element_value_states(element_value_state_id);
	
	ALTER TABLE rt.element_values SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.pm_command_elements 
-- Descripción: Comandos almacenados en cada cola 
-- Scope: conf
--
    CREATE TABLE rt.pm_command_elements (
       pm_command_element_id int8 NOT NULL,
       command_element_type_id int8 NOT NULL,
       element_type_id int8 NOT NULL,
       element_id int8 NOT NULL,
       queue_id int8 NOT NULL,
       priority_id int4 NOT NULL,
       start_time timestamptz NOT NULL,
       end_time timestamptz NOT NULL,
       source varchar NULL,
       enabled bool NULL,
       visible bool NULL,
       created_at timestamptz NOT NULL,
       updated_at timestamptz NOT NULL,
       CONSTRAINT pk_pm_command_elements PRIMARY KEY (queue_id, command_element_type_id, pm_command_element_id, element_type_id, element_id)
    );
    
    CREATE INDEX idx_pm_command_elements_command_element_types ON rt.pm_command_elements USING btree (command_element_type_id, element_type_id);
    CREATE INDEX idx_pm_command_elements_elements ON rt.pm_command_elements USING btree (element_type_id, element_id);
    CREATE INDEX idx_pm_command_elements_pm_element_type_queues ON rt.pm_command_elements USING btree (element_type_id, queue_id);
    CREATE INDEX idx_pm_command_elements_pm_queue_priorities ON rt.pm_command_elements USING btree (queue_id, priority_id);
    
    ALTER TABLE rt.pm_command_elements ADD CONSTRAINT fk_pm_command_elements_command_element_types FOREIGN KEY (command_element_type_id, element_type_id) REFERENCES master.command_element_types(command_element_type_id, element_type_id);
    ALTER TABLE rt.pm_command_elements ADD CONSTRAINT fk_pm_command_elements_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
    ALTER TABLE rt.pm_command_elements ADD CONSTRAINT fk_pm_command_elements_pm_element_type_queues FOREIGN KEY (element_type_id, queue_id) REFERENCES master.pm_element_type_queues(element_type_id, queue_id);
    ALTER TABLE rt.pm_command_elements ADD CONSTRAINT fk_pm_command_elements_pm_queue_priorities FOREIGN KEY (queue_id, priority_id) REFERENCES master.pm_queue_priorities(queue_id, priority_id);

    ALTER TABLE rt.pm_command_elements SET TABLESPACE tbs_controltrafico_rt;

-- 
-- Table: rt.command_element_values

-- Scope: rt
-- 
	CREATE TABLE rt.pm_command_element_values (
       pm_command_element_id int8 NOT NULL,
       command_element_type_id int8 NOT NULL,
       element_type_id int8 NOT NULL,
       element_id int8 NOT NULL,
       queue_id int8 NOT NULL,
       command_element_type_param_id int8 NOT NULL,
       value varchar NULL,
       enabled bool NULL,
       visible bool NULL,
       created_at timestamptz NOT NULL,
       updated_at timestamptz NOT NULL,
       CONSTRAINT pk_command_element_values PRIMARY KEY (queue_id, command_element_type_id, pm_command_element_id, element_type_id, element_id, command_element_type_param_id)
	);
	
	CREATE INDEX idx_pm_command_element_values_command_element_type_param ON rt.pm_command_element_values USING btree (command_element_type_id, element_type_id, command_element_type_param_id);
	CREATE INDEX idx_pm_command_element_values_pm_command_element ON rt.pm_command_element_values USING btree (queue_id, command_element_type_id, pm_command_element_id, element_type_id, element_id);
	
	ALTER TABLE rt.pm_command_element_values ADD CONSTRAINT fk_pm_command_element_values_command_element_type_param FOREIGN KEY (command_element_type_id, element_type_id, command_element_type_param_id) REFERENCES master.command_element_type_params(command_element_type_id, element_type_id, command_element_type_param_id);
	ALTER TABLE rt.pm_command_element_values ADD CONSTRAINT fk_pm_command_element_values_pm_command_element FOREIGN KEY (queue_id, command_element_type_id, pm_command_element_id, element_type_id, element_id) REFERENCES rt.pm_command_elements(queue_id, command_element_type_id, pm_command_element_id, element_type_id, element_id);

	ALTER TABLE rt.pm_command_element_values SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.route_avoid_polygons
-- Scope: rt
-- 
		CREATE SEQUENCE rt.last_route_avoid_polygon_id START 1;
		
		CREATE TABLE rt.route_avoid_polygons (
		route_avoid_polygon_id int8 default nextval('rt.last_route_avoid_polygon_id'),
		alias varchar NOT NULL,
		description varchar NULL,
		lat float8 NULL,
		lon float8 NULL,
		polygon varchar NULL,
		incident_report_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_route_avoid_polygon  PRIMARY KEY (route_avoid_polygon_id)
	);
	
	CREATE INDEX fk_route_avoid_polygon_incident_report ON rt.route_avoid_polygons USING btree (incident_report_id);

	ALTER TABLE rt.route_avoid_polygons ADD CONSTRAINT fk_route_avoid_polygon_incident_report FOREIGN KEY (incident_report_id) REFERENCES rt.ims_incident_reports(incident_report_id);
	
	ALTER TABLE rt.route_avoid_polygons SET TABLESPACE tbs_controltrafico_rt;
