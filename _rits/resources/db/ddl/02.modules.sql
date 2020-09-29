---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.module_types
-- Description: Module types table
-- Scope: ui
--
	CREATE TABLE ui.module_types (
		id serial primary key,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
		
	ALTER TABLE ui.module_types SET TABLESPACE tbl_ui;

-- 
-- Table: ui.modules
-- Description: Modules table
-- Scope: ui
--
	CREATE TABLE ui.modules (
	    id serial primary key,
		module_type_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		js_file varchar NULL,
		js_method_draw varchar NULL,
		js_method_callbacks varchar NULL,
		data_query varchar NULL,
		wss_topic varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_modules_module_types ON ui.modules USING btree (module_type_id);

	ALTER TABLE ui.modules ADD CONSTRAINT fk_modules_module_types FOREIGN KEY (module_type_id) REFERENCES ui.module_types(id);
		
	ALTER TABLE ui.modules SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.module_params
-- Description: Params for module table
-- Scope: ui
--
	CREATE TABLE ui.module_params (
	    id serial primary key,
		module_type_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_module_params_module_types ON ui.module_params USING btree (module_type_id);

	ALTER TABLE ui.module_params ADD CONSTRAINT fk_module_params_module_types FOREIGN KEY (module_type_id) REFERENCES ui.module_types(id);
		
	ALTER TABLE ui.module_params SET TABLESPACE tbl_ui;

-- 
-- Table: ui.module_values
-- Description: Param values for module table
-- Scope: ui
--
	CREATE TABLE ui.module_values (
	    id serial primary key,
		module_id int8 NOT NULL,
		module_param_id int8 NOT NULL,
		value varchar NULL,
		UNIQUE (module_id, module_param_id)
	);
	
	CREATE INDEX idx_module_values_modules ON ui.module_values USING btree (module_id);
	CREATE INDEX idx_module_values_module_params ON ui.module_values USING btree (module_param_id);

	ALTER TABLE ui.module_values ADD CONSTRAINT fk_module_values_modules FOREIGN KEY (module_id) REFERENCES ui.modules(id);
	ALTER TABLE ui.module_values ADD CONSTRAINT fk_module_values_module_params FOREIGN KEY (module_param_id) REFERENCES ui.module_params(id);
		
	ALTER TABLE ui.module_values SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.window_params
-- Description: Params for window associated to module type
-- Scope: ui
--
	CREATE TABLE ui.window_params (
	    id serial primary key,
		module_type_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_window_params_module_types ON ui.window_params USING btree (module_type_id);

	ALTER TABLE ui.window_params ADD CONSTRAINT fk_window_params_module_types FOREIGN KEY (module_type_id) REFERENCES ui.module_types(id);
		
	ALTER TABLE ui.window_params SET TABLESPACE tbl_ui;

-- 
-- Table: ui.window_values
-- Description: Param values for window associated to module
-- Scope: ui
--
	CREATE TABLE ui.window_values (
	    id serial primary key,
		module_id int8 NOT NULL,
		window_param_id int8 NOT NULL,
		value varchar NULL,
		UNIQUE (module_id, window_param_id)
	);
	
	CREATE INDEX idx_window_values_modules ON ui.window_values USING btree (module_id);
	CREATE INDEX idx_window_values_module_params ON ui.window_values USING btree (window_param_id);

	ALTER TABLE ui.window_values ADD CONSTRAINT fk_window_values_modules FOREIGN KEY (module_id) REFERENCES ui.modules(id);
	ALTER TABLE ui.window_values ADD CONSTRAINT fk_window_values_window_params FOREIGN KEY (window_param_id) REFERENCES ui.window_params(id);
		
	ALTER TABLE ui.window_values SET TABLESPACE tbl_ui;
	
