---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------
-- 
-- Table: master.log_level
-- Descripción: Tipos de nivel para lso log
-- Scope: master
--
	CREATE TABLE master.log_levels (
		log_level_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_log_levels PRIMARY KEY (log_level_id)
	);	
-- 
-- Table: master.log_types
-- Descripción: Tipos de registros
-- Scope: master
--
	CREATE TABLE master.log_types (
		log_type_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		level_output_db int8 NULL,
		level_output_stomp int8 NULL,
		level_treatment int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_log_types PRIMARY KEY (log_type_id)
	);

	ALTER TABLE master.log_types ADD CONSTRAINT fk_log_types_level_output_db_levels FOREIGN KEY (level_output_db) REFERENCES master.log_levels(log_level_id);
	ALTER TABLE master.log_types ADD CONSTRAINT fk_log_types_level_output_stomp_levels FOREIGN KEY (level_output_stomp) REFERENCES master.log_levels(log_level_id);
	ALTER TABLE master.log_types ADD CONSTRAINT fk_log_types_level_treatment_levels FOREIGN KEY (level_treatment) REFERENCES master.log_levels(log_level_id);

	ALTER TABLE master.log_types SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.log_type_params
-- Descripción: Parámetros de tipos de registros
-- Scope: master
--
	CREATE TABLE master.log_type_params (
		log_type_id int8 NOT NULL,
		log_type_param_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_log_type_params PRIMARY KEY (log_type_id, log_type_param_id)
	);
	
	CREATE INDEX idx_log_type_params_log_types ON master.log_type_params USING btree (log_type_id);
	
	ALTER TABLE master.log_type_params ADD CONSTRAINT fk_log_type_params_log_types FOREIGN KEY (log_type_id) REFERENCES master.log_types(log_type_id);
	
	ALTER TABLE master.log_type_params SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.log_subtypes
-- Descripción: Tipos de registros
-- Scope: master
--
	CREATE TABLE master.log_subtypes (
		log_subtype_id int8 NOT NULL,
		log_type_id int8 NOT NULL,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		level_output_db int8 NULL,
		level_output_stomp int8 NULL,
		level_treatment int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_log_subtypes PRIMARY KEY (log_subtype_id)
	);

	CREATE INDEX idx_log_subtypes_log_types ON master.log_subtypes USING btree (log_type_id);
	ALTER TABLE master.log_subtypes ADD CONSTRAINT fk_log_subtype_log_types FOREIGN KEY (log_type_id) REFERENCES master.log_types(log_type_id);
	ALTER TABLE master.log_subtypes ADD CONSTRAINT fk_log_subtypes_level_output_db_levels FOREIGN KEY (level_output_db) REFERENCES master.log_levels(log_level_id);
	ALTER TABLE master.log_subtypes ADD CONSTRAINT fk_log_subtypes_level_output_stomp_levels FOREIGN KEY (level_output_stomp) REFERENCES master.log_levels(log_level_id);
	ALTER TABLE master.log_subtypes ADD CONSTRAINT fk_log_subtypes_level_treatment_levels FOREIGN KEY (level_treatment) REFERENCES master.log_levels(log_level_id);

	ALTER TABLE master.log_subtypes SET TABLESPACE tbs_controltrafico_master;	
	