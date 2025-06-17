SET client_min_messages TO WARNING;
DO $$
BEGIN
    IF EXISTS (
        SELECT schema_name 
        FROM information_schema.schemata 
        WHERE schema_name = 'hist'
    ) THEN
        DROP SCHEMA hist CASCADE;
    END IF;
END $$;

CREATE SCHEMA hist;

-- 
-- Scope: hist
--

-- 
-- Table: hist.ims_incident_reports
-- Descripción: Partes de incidencias
-- Scope: hist
--
	CREATE TABLE hist.ims_incident_reports (
		incident_report_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		incident_type_id int8 NOT NULL,
		incident_type_cause_id int8 NULL,
		affection_stretch_id int8 NOT NULL,
		location varchar NULL,
		incident_state_id int8 NOT NULL,
		incident_level_id int8 NOT NULL,
		current_user_id int8 NULL,
		valid_for_min int4 NULL,
		confirm bool NOT NULL,
		road_impact_id int8 NULL,
		last_Update timestamptz NOT NULL,
		generated_at timestamptz NOT NULL,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_reports PRIMARY KEY (incident_report_id, created_at)
	);
	
	CREATE INDEX idx_ims_incident_reports_ims_incidents ON hist.ims_incident_reports USING btree (incident_type_id, affection_stretch_id);
	CREATE INDEX idx_ims_incident_reports_ims_incident_states ON hist.ims_incident_reports USING btree (incident_state_id);
	CREATE INDEX idx_ims_incident_reports_ims_incident_levels ON hist.ims_incident_reports USING btree (incident_level_id);
	CREATE INDEX idx_ims_incident_reports_users ON hist.ims_incident_reports USING btree (current_user_id);

	ALTER TABLE hist.ims_incident_reports SET TABLESPACE tbl_hist;
	
	
	-- 
-- Table: hist.ims_incident_param_values
-- Descripción: incident para values
-- Scope: hist
--
	CREATE TABLE hist.ims_incident_param_values (
	incident_param_id int8 NOT NULL,
	incident_report_id int8 NOT NULL,
	value varchar NULL,
	created_at timestamptz NOT NULL,
	CONSTRAINT pk_ims_incident_param_values PRIMARY KEY (incident_report_id, incident_param_id, created_at)
	);
	
	ALTER TABLE hist.ims_incident_param_values SET TABLESPACE tbl_hist;

	
-- 
-- Table: hist.ims_incident_report_task_states
-- Descripción: Estado de las tareas en un parte de incidencia
-- Scope: hist
--
	CREATE TABLE hist.ims_incident_report_task_states (
	    incident_report_id int8 NOT NULL,
    	incident_type_task_id int8 NOT NULL,
	    completed_at timestamptz NULL,
	    error bool NULL,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_states PRIMARY KEY (incident_report_id, incident_type_task_id, created_at)
	);
	
	CREATE INDEX idx_ims_incident_report_task_states_ims_incident_reports ON hist.ims_incident_report_task_states USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_task_states_ims_incident_type_tasks ON hist.ims_incident_report_task_states USING btree (incident_type_task_id);

	ALTER TABLE hist.ims_incident_report_task_states SET TABLESPACE tbl_hist;

	
-- Table: hist.ims_incident_report_task_values
-- Descripción: Valores dianamicos de los parametros de la tarea
-- Scope: hist
--
	CREATE TABLE hist.ims_incident_report_task_values (
	    incident_report_id int8 NOT NULL,
    	task_type_id int8 NOT NULL,	
		task_type_param_id int8 NOT NULL,
		incident_type_task_id int8 NOT NULL,
		value varchar NULL,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_values PRIMARY KEY (incident_report_id, task_type_id, task_type_param_id, incident_type_task_id, created_at)
	);

	CREATE INDEX idx_ims_incident_report_task_values_ims_incident_reports ON hist.ims_incident_report_task_values USING btree (incident_report_id);

	ALTER TABLE hist.ims_incident_report_task_values SET TABLESPACE tbl_hist;
	
-- Table: hist.ims_incident_report_task_elements
-- Descripción:
-- Scope: rt
--
	CREATE TABLE hist.ims_incident_report_task_elements (
	    incident_report_id int8 NOT NULL,
		incident_type_task_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_elements PRIMARY KEY (incident_report_id, incident_type_task_id, element_type_id, element_id, created_at)
	);
	
	CREATE INDEX idx_ims_incident_report_task_elements_ims_incident_reports ON hist.ims_incident_report_task_elements USING btree (incident_report_id);

	
	ALTER TABLE hist.ims_incident_report_task_elements SET TABLESPACE tbl_hist;
	
-- 
-- Table: hist.ims_incident_report_task_plan_states
-- Descripción: Estado de un plan en un parte de incidencia
-- Scope: hist
--
	CREATE TABLE hist.ims_incident_report_task_plan_states (
	    incident_report_id int8 NOT NULL,
	    incident_task_plan_id int8 NOT NULL,
	    completed_at timestamptz NULL,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_task_plan_states PRIMARY KEY (incident_report_id, incident_task_plan_id, created_at)
	);
	
	CREATE INDEX idx_ims_incident_report_task_plan_states_reports ON hist.ims_incident_report_task_plan_states USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_task_plan_states_task_plans ON hist.ims_incident_report_task_plan_states USING btree (incident_task_plan_id);
	
	ALTER TABLE hist.ims_incident_report_task_plan_states SET TABLESPACE tbl_hist;

-- 
-- Table: hist.ims_incident_report_logs
-- Descripción: Traza de un parte de incidencia
-- Scope: hist
--
	CREATE TABLE hist.ims_incident_report_logs (
		incident_report_log_id int8 NOT NULL,
		incident_report_id int8 NOT NULL,
		log_subtype_id int8 NULL,
		user_id int8 NOT NULL,
		level int4 NOT NULL,
		log_timestamp timestamptz NOT null,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_logs PRIMARY KEY (incident_report_log_id, created_at)
	);
	
	CREATE INDEX idx_ims_incident_report_logs_ims_incident_reports ON hist.ims_incident_report_logs USING btree (incident_report_id);
	CREATE INDEX idx_ims_incident_report_logs_users ON hist.ims_incident_report_logs USING btree (user_id);

	ALTER TABLE hist.ims_incident_report_logs SET TABLESPACE tbl_hist;
	
-- 
-- Table: hist.ims_incident_report_log_values
-- Descripción: Datos de la traza de un parte de incidencia
-- Scope: rt
--
	CREATE TABLE hist.ims_incident_report_log_values (
		incident_report_log_id int8 NOT NULL,
		log_type_id int8 NOT NULL,
		log_type_param_id int8 NOT NULL,
		value varchar NULL,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_log_values PRIMARY KEY (incident_report_log_id, log_type_id, log_type_param_id, created_at)
	);
	
	CREATE INDEX idx_ims_incident_report_log_values_ims_incident_report_logs ON hist.ims_incident_report_log_values USING btree (incident_report_log_id);
	
	ALTER TABLE hist.ims_incident_report_log_values SET TABLESPACE tbl_hist;
	
	-- 
-- Table: conf.ims_incident_report_alarms
-- Descripción: Alarmas que han producido reportes
-- Scope: hist
--
	CREATE TABLE hist.ims_incident_report_alarms (
	    incident_report_id int8 NOT NULL,
		alarm_config_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
  		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ims_incident_report_alarms  PRIMARY KEY (incident_report_id, alarm_config_id, element_type_id, element_id, created_at)
	);
	
	CREATE INDEX idx_ims_incident_report_alarms_incident_reports ON hist.ims_incident_report_alarms USING btree (incident_report_id);

	ALTER TABLE hist.ims_incident_report_alarms SET TABLESPACE tbl_hist;
