---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.data_source_types
-- Description: Data source type
-- Scope: ui
--
	CREATE TABLE ui.data_source_types (
		id int not null,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_data_source_types PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.data_source_types SET TABLESPACE tbs_controltrafico_ui;

-- 
-- Table: ui.data_source_params
-- Description: Data source params
-- Scope: ui
--
	CREATE TABLE ui.data_source_params (
		id int not null,
		data_source_type_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_data_source_params PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.data_source_params SET TABLESPACE tbs_controltrafico_ui;
	
-- 
-- Table: ui.data_sources
-- Description: Data sources
-- Scope: ui
--
	CREATE TABLE ui.data_sources (
		id serial primary key,
		data_source_type_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		entity_vo varchar NOT NULL,
		field_id varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_data_sources_data_source_types ON ui.data_sources USING btree (data_source_type_id);
	
	ALTER TABLE ui.data_sources ADD CONSTRAINT fk_data_sources_data_source_types FOREIGN KEY (data_source_type_id) REFERENCES ui.data_source_types(id);
	
	ALTER TABLE ui.data_sources SET TABLESPACE tbs_controltrafico_ui;

-- 
-- Table: ui.data_source_values
-- Description: Data source values
-- Scope: ui
--
	CREATE TABLE ui.data_source_values (
	    id serial primary key,
		data_source_id int NOT NULL,
		data_source_param_id int NOT NULL,
		value varchar NULL,
		UNIQUE (data_source_id, data_source_param_id)
	);
	
	CREATE INDEX idx_data_source_values_data_sources ON ui.data_source_values USING btree (data_source_id);
	CREATE INDEX idx_data_source_values_data_source_params ON ui.data_source_values USING btree (data_source_param_id);

	ALTER TABLE ui.data_source_values ADD CONSTRAINT fk_data_source_values_data_sources FOREIGN KEY (data_source_id) REFERENCES ui.data_sources(id);
	ALTER TABLE ui.data_source_values ADD CONSTRAINT fk_data_source_values_data_source_params FOREIGN KEY (data_source_param_id) REFERENCES ui.data_source_params(id);
		
	ALTER TABLE ui.data_source_values SET TABLESPACE tbs_controltrafico_ui;
	
