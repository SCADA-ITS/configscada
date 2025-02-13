---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.adapter_types
-- Description: Adapter type
-- Scope: ui
--
	CREATE TABLE ui.adapter_types (
		id int not null,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_adapter_types PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.adapter_types SET TABLESPACE tbs_controltrafico_ui;

-- 
-- Table: ui.adapters
-- Description: Adapters
-- Scope: ui
--
	CREATE TABLE ui.adapters (
		id int not null,
		adapter_type_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		file varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_adapters PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_adapters_adapter_types ON ui.adapters USING btree (adapter_type_id);
	
	ALTER TABLE ui.adapters ADD CONSTRAINT fk_adapters_adapter_types FOREIGN KEY (adapter_type_id) REFERENCES ui.adapter_types(id);
	
	ALTER TABLE ui.adapters SET TABLESPACE tbs_controltrafico_ui;

	
-- 
-- Table: ui.adapter_params
-- Description: Adapter params
-- Scope: ui
--
	CREATE TABLE ui.adapter_params (
		id int not null,
		adapter_id int NOT NULL,
		pos int NOT NULL,
		param varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		UNIQUE (adapter_id, pos),
		CONSTRAINT pk_adapter_params PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_adapter_params_adapters ON ui.adapter_params USING btree (adapter_id);
	
	ALTER TABLE ui.adapter_params ADD CONSTRAINT fk_adapter_params_adapters FOREIGN KEY (adapter_id) REFERENCES ui.adapters(id);
	
	ALTER TABLE ui.adapter_params SET TABLESPACE tbs_controltrafico_ui;

-- 
-- Table: ui.adapter_data_source_type
-- Description: Adapter data source types
-- Scope: ui
--
	CREATE TABLE ui.adapter_data_source_types (
		id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_adapter_data_source_types PRIMARY KEY (id)
	);

	ALTER TABLE ui.adapter_data_source_types SET TABLESPACE tbs_controltrafico_ui;

-- 
-- Table: ui.adapter_data_sources
-- Description: Adapter data sources
-- Scope: ui
--
	CREATE TABLE ui.adapter_data_sources (
	    id serial primary key,
		adapter_id int NOT NULL,
		data_source_id int NOT NULL,
		adapter_data_source_type_id int NOT NULL,
		value varchar NOT NULL,
		UNIQUE (adapter_id, data_source_id)
	);
	
	CREATE INDEX idx_adapter_data_sources_adapters ON ui.adapter_data_sources USING btree (adapter_id);
	CREATE INDEX idx_adapter_data_sources_data_sources ON ui.adapter_data_sources USING btree (data_source_id);
	CREATE INDEX idx_adapter_data_sources_adapter_data_source_types ON ui.adapter_data_sources USING btree (adapter_data_source_type_id);
	
	ALTER TABLE ui.adapter_data_sources ADD CONSTRAINT fk_adapter_data_sources_adapters FOREIGN KEY (adapter_id) REFERENCES ui.adapters(id);
	ALTER TABLE ui.adapter_data_sources ADD CONSTRAINT fk_adapter_data_sources_data_sources FOREIGN KEY (data_source_id) REFERENCES ui.data_sources(id);
	ALTER TABLE ui.adapter_data_sources ADD CONSTRAINT fk_adapter_data_sources_adapter_data_source_types FOREIGN KEY (adapter_data_source_type_id) REFERENCES ui.adapter_data_source_types(id);
	
	ALTER TABLE ui.adapter_data_sources SET TABLESPACE tbs_controltrafico_ui;