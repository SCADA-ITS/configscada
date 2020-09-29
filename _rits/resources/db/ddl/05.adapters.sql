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
		id serial primary key,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	ALTER TABLE ui.adapter_types SET TABLESPACE tbl_ui;

-- 
-- Table: ui.adapters
-- Description: Adapters
-- Scope: ui
--
	CREATE TABLE ui.adapters (
		id serial primary key,
		adapter_type_id int8 NOT NULL,
		data_source_id int8 NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_adapters_adapter_types ON ui.adapters USING btree (adapter_type_id);
	CREATE INDEX idx_adapters_data_sources ON ui.adapters USING btree (data_source_id);
	
	ALTER TABLE ui.adapters ADD CONSTRAINT fk_adapters_adapter_types FOREIGN KEY (adapter_type_id) REFERENCES ui.adapter_types(id);
	ALTER TABLE ui.adapters ADD CONSTRAINT fk_adapters_data_sources FOREIGN KEY (data_source_id) REFERENCES ui.data_sources(id);
	
	ALTER TABLE ui.adapters SET TABLESPACE tbl_ui;

	
-- 
-- Table: ui.adapter_params
-- Description: Adapter params
-- Scope: ui
--
	CREATE TABLE ui.adapter_params (
		id serial primary key,
		adapter_id int8 NOT NULL,
		pos int8 NOT NULL,
		param varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		UNIQUE (adapter_id, pos)
	);
	
	CREATE INDEX idx_adapter_params_adapters ON ui.adapter_params USING btree (adapter_id);
	
	ALTER TABLE ui.adapter_params ADD CONSTRAINT fk_adapter_params_adapters FOREIGN KEY (adapter_id) REFERENCES ui.adapters(id);
	
	ALTER TABLE ui.adapter_params SET TABLESPACE tbl_ui;
