---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.view_type
-- Description: View types table
-- Scope: ui
--
CREATE TABLE ui.view_types (
	id int NOT NULL,
	name varchar NOT NULL,
	description varchar NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_view_types PRIMARY KEY (id)
);
	
ALTER TABLE ui.view_types SET TABLESPACE tbl_ui;

-- 
-- Table: ui.module_types
-- Description: Module types table
-- Scope: ui
--
	CREATE TABLE ui.module_types (
		id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_module_types PRIMARY KEY (id)
	);
		
	ALTER TABLE ui.module_types SET TABLESPACE tbl_ui;

-- 
-- Table: ui.modules
-- Description: Modules table
-- Scope: ui
--
	CREATE TABLE ui.modules (
	    id int NOT NULL,
		module_type_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		default_view_type_id int NULL,
		default_system_permission_id varchar NULL,
		js_file varchar NULL,
		js_method_draw varchar NULL,
		js_method_callbacks varchar NULL,
		data_query varchar NULL,
		wss_topic varchar NULL,		
		enabled bool NULL,
		visible bool NULL,
		autoload bool NULL,
		management_areas varchar NULL,
		CONSTRAINT pk_modules PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_modules_module_types ON ui.modules USING btree (module_type_id);
	CREATE INDEX idx_modules_view_types ON ui.modules USING btree (default_view_type_id);
	

	ALTER TABLE ui.modules ADD CONSTRAINT fk_modules_module_types FOREIGN KEY (module_type_id) REFERENCES ui.module_types(id);
	ALTER TABLE ui.modules ADD CONSTRAINT fk_modules_view_types FOREIGN KEY (default_view_type_id) REFERENCES ui.view_types(id);
		
	ALTER TABLE ui.modules SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.module_actions
-- Description: Actions for modules
-- Scope: ui
--
	CREATE TABLE ui.module_actions (
	    id int not null,
		module_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		system_permission_id varchar NULL,
		no_externalizable bool NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_module_action PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_module_action_modules ON ui.module_actions USING btree (module_id);

	ALTER TABLE ui.module_actions ADD CONSTRAINT fk_module_action_modules FOREIGN KEY (module_id) REFERENCES ui.modules(id);
		
	ALTER TABLE ui.module_actions SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.module_params
-- Description: Params for module table
-- Scope: ui
--
	CREATE TABLE ui.module_params (
	    id int not null,
		module_type_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_module_params PRIMARY KEY (id)
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
		module_id int NOT NULL,
		module_param_id int NOT NULL,
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
	    id int not null,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_window_params PRIMARY KEY (id)
	);
		
	ALTER TABLE ui.window_params SET TABLESPACE tbl_ui;

-- 
-- Table: ui.window_values
-- Description: Param values for window associated to module
-- Scope: ui
--
	CREATE TABLE ui.window_values (
	    id serial primary key,
		module_id int NOT NULL,
		window_param_id int NOT NULL,
		value varchar NULL,
		UNIQUE (module_id, window_param_id)
	);
	
	CREATE INDEX idx_window_values_modules ON ui.window_values USING btree (module_id);
	CREATE INDEX idx_window_values_module_params ON ui.window_values USING btree (window_param_id);

	ALTER TABLE ui.window_values ADD CONSTRAINT fk_window_values_modules FOREIGN KEY (module_id) REFERENCES ui.modules(id);
	ALTER TABLE ui.window_values ADD CONSTRAINT fk_window_values_window_params FOREIGN KEY (window_param_id) REFERENCES ui.window_params(id);
		
	ALTER TABLE ui.window_values SET TABLESPACE tbl_ui;
	
