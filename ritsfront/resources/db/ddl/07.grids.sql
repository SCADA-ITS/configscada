---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.grid_field_group_operations
-- Description: Definition of type of group operations with fields in datagrid
-- Scope: ui
--
	CREATE TABLE ui.grid_field_group_operations (
		id int not null,
		name varchar NOT NULL,
		description varchar NULL,
		CONSTRAINT pk_grid_field_group_operations PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.grid_field_group_operations SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.grid_field_data_types
-- Description: Definition of data type in grid fields
-- Scope: ui
--
	CREATE TABLE ui.grid_field_data_types (
		id int not null,
		name varchar NOT NULL,
		format varchar NULL,
		description varchar NULL,
		CONSTRAINT pk_grid_field_data_types PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.grid_field_data_types SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.grids
-- Description: Definition of data table
-- Scope: ui
--
	CREATE TABLE ui.grids (
		id int not null,
		adapter_id int NULL,
		label varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_grids PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_grids_adapters ON ui.grids USING btree (adapter_id);
	
	ALTER TABLE ui.grids ADD CONSTRAINT fk_grids_adapters FOREIGN KEY (adapter_id) REFERENCES ui.adapters(id);
	
	ALTER TABLE ui.grids SET TABLESPACE tbl_ui;

-- 
-- Table: ui.grid_options
-- Description: Grid options
-- Scope: ui
--
	CREATE TABLE ui.grid_options (
		id int not null,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_grid_options PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.grid_options SET TABLESPACE tbl_ui;

-- 
-- Table: ui.grid_option_values
-- Description: Grid options values
-- Scope: ui
--
	CREATE TABLE ui.grid_option_values (
		id serial PRIMARY key,
		grid_id int NOT NULL,
		grid_option_id int NOT NULL,
		value varchar NULL,
		UNIQUE (grid_id, grid_option_id)
	);
	
	CREATE INDEX idx_grid_option_values_grids ON ui.grid_option_values USING btree (grid_id);
	CREATE INDEX idx_grid_option_values_grid_options ON ui.grid_option_values USING btree (grid_option_id);

	ALTER TABLE ui.grid_option_values ADD CONSTRAINT fk_grid_option_values_grids FOREIGN KEY (grid_id) REFERENCES ui.grids(id);
	ALTER TABLE ui.grid_option_values ADD CONSTRAINT fk_grid_option_values_grid_options FOREIGN KEY (grid_option_id) REFERENCES ui.grid_options(id);
		
	ALTER TABLE ui.grid_option_values SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.grid_commands
-- Description: Commands available on grid
-- Scope: ui
--
	CREATE TABLE ui.grid_commands (
		id serial primary key,
		grid_id int NOT NULL,
		position int NOT NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		"default" bool NOT NULL,
		module_action_id int NULL,
		args varchar NULL,
		view_type_id int NULL,
		multiselect bool NOT NULL,
		show_text bool NULL,
		item_required bool NULL,
		grouped bool NULL,
		unselect_all_after bool NULL
	);
	
	CREATE INDEX idx_grid_commands_grids ON ui.grid_commands USING btree (grid_id);
	CREATE INDEX idx_grid_commands_module_actions ON ui.grid_commands USING btree (module_action_id);
	CREATE INDEX idx_grid_commands_view_types ON ui.grid_commands USING btree (view_type_id);
	
	ALTER TABLE ui.grid_commands ADD CONSTRAINT fk_grid_commands_grids FOREIGN KEY (grid_id) REFERENCES ui.grids(id);
	ALTER TABLE ui.grid_commands ADD CONSTRAINT fk_grid_commands_module_actions FOREIGN KEY (module_action_id) REFERENCES ui.module_actions(id);
	ALTER TABLE ui.grid_commands ADD CONSTRAINT fk_grid_commands_view_types FOREIGN KEY (view_type_id) REFERENCES ui.view_types(id);
	
	ALTER TABLE ui.grid_commands SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.grid_fields
-- Description: Definition of fields for module of type grid
-- Scope: ui
--
	CREATE TABLE ui.grid_fields (
		id int not null,	
		grid_id int NOT NULL,
		grid_field_data_type_id int NOT NULL,
		position int NOT NULL,
		required bool NOT NULL,
		parameterized bool NOT NULL,
		extended bool NOT NULL,
		filtered bool NOT NULL,
		label varchar NULL,
		label_tooltip varchar NULL,
		source_field varchar NOT NULL,
		is_pk bool NOT NULL,
		is_epoch_milli bool NOT NULL,
		align varchar NULL,
		needs_translation bool NOT NULL,
		grouping_summary bool NOT NULL,
		width_px int NOT NULL,
		grid_field_group_operation_id int NULL,
		enabled bool NULL,
		visible bool NULL,
		adjust bool NULL,
		header_vertical bool NULL,
		fixed_column bool NULL,
		replacement_function varchar NULL,
		precision_filter int NULL,
		convert_cron bool NULL,
		CONSTRAINT pk_grid_fields PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_grid_fields_grids ON ui.grid_fields USING btree (grid_id);
	CREATE INDEX idx_grid_fields_grid_field_data_types ON ui.grid_fields USING btree (grid_field_data_type_id);
	CREATE INDEX idx_grid_fields_grid_field_group_operations ON ui.grid_fields USING btree (grid_field_group_operation_id);

	ALTER TABLE ui.grid_fields ADD CONSTRAINT fk_grid_fields_grids FOREIGN KEY (grid_id) REFERENCES ui.grids(id);
	ALTER TABLE ui.grid_fields ADD CONSTRAINT fk_grid_fields_grid_field_data_types FOREIGN KEY (grid_field_data_type_id) REFERENCES ui.grid_field_data_types(id);
	ALTER TABLE ui.grid_fields ADD CONSTRAINT fk_grid_fields_grid_field_group_operations FOREIGN KEY (grid_field_group_operation_id) REFERENCES ui.grid_field_group_operations(id);
		
	ALTER TABLE ui.grid_fields SET TABLESPACE tbl_ui;

-- 
-- Table: ui.grid_field_icons
-- Description: Relationship between field values in grid and icons associated
-- Scope: ui
--
	CREATE TABLE ui.grid_field_icons (
		id serial primary key,
		grid_field_id int NOT NULL,
		icon varchar NULL,
		value varchar NULL,
		show_value bool NOT NULL,
		badge_color bool NOT NULL,
		avatar bool NOT NULL,
		enabled bool,
		label_value varchar NULL
	);
	
	CREATE INDEX idx_grid_field_icons_grid_fields ON ui.grid_field_icons USING btree (grid_field_id);
	
	ALTER TABLE ui.grid_field_icons ADD CONSTRAINT fk_grid_field_icons_grid_fields FOREIGN KEY (grid_field_id) REFERENCES ui.grid_fields(id);
	
	ALTER TABLE ui.grid_field_icons SET TABLESPACE tbl_ui;

	
-- 
-- Table: ui.grid_field_balloons
-- Description: Relationship between field values in grid and ballon color
-- Scope: ui
--
	CREATE TABLE ui.grid_field_balloons (
		id serial primary key,
		grid_field_id int NOT NULL,
		color varchar NULL,
		value varchar NULL,
		enabled bool NULL
	);
	
	CREATE INDEX idx_grid_field_balloons_grid_fields ON ui.grid_field_balloons USING btree (grid_field_id);
	
	ALTER TABLE ui.grid_field_balloons ADD CONSTRAINT fk_grid_field_balloons_grid_fields FOREIGN KEY (grid_field_id) REFERENCES ui.grid_fields(id);
	
	ALTER TABLE ui.grid_field_balloons SET TABLESPACE tbl_ui;

	
-- 
-- Table: ui.grid_field_filters
-- Description: Relationship between field values in grid and quick filters
-- Scope: ui
--
	CREATE TABLE ui.grid_field_filters (
		id serial primary key,
		grid_field_id int NOT NULL,
		label varchar NULL,
		label_description varchar NULL,
		value varchar NULL,
		eval_js bool NULL,
		operators bool NULL,
		enabled bool NULL
	);
	
	CREATE INDEX idx_grid_field_filters_grid_fields ON ui.grid_field_filters USING btree (grid_field_id);
	
	ALTER TABLE ui.grid_field_filters ADD CONSTRAINT fk_grid_field_filters_grid_fields FOREIGN KEY (grid_field_id) REFERENCES ui.grid_fields(id);
	
	ALTER TABLE ui.grid_field_filters SET TABLESPACE tbl_ui;
	


-- 
-- Table: ui.grid_field_replacements
-- Description: Relationship between field values in grid and replacements associated
-- Scope: ui
--
	CREATE TABLE ui.grid_field_replacements (
		id serial primary key,
		grid_field_id int NOT NULL,
		label varchar NOT NULL,
		value varchar NOT NULL,
		enabled bool
	);
	
	CREATE INDEX idx_grid_field_replacements_grid_fields ON ui.grid_field_replacements USING btree (grid_field_id);
	
	ALTER TABLE ui.grid_field_replacements ADD CONSTRAINT fk_grid_field_replacements_grid_fields FOREIGN KEY (grid_field_id) REFERENCES ui.grid_fields(id);
	
	ALTER TABLE ui.grid_field_replacements SET TABLESPACE tbl_ui;

-- 
-- Table: ui.grid_field_references
-- Description: Grid field value calculated from another field and function
-- Scope: ui
--
	CREATE TABLE ui.grid_field_references (
		id serial primary key,
		grid_field_id int NOT NULL,
		ref_field_id int NOT NULL,
		em_id varchar NOT NULL,
		em_field varchar NOT NULL,
		em_field_returned varchar NOT NULL,
		enabled bool
	);
	
	CREATE INDEX idx_grid_field_references_grid_fields ON ui.grid_field_references USING btree (grid_field_id);
	
	ALTER TABLE ui.grid_field_references ADD CONSTRAINT fk_grid_field_references_grid_fields FOREIGN KEY (grid_field_id) REFERENCES ui.grid_fields(id);
	
	ALTER TABLE ui.grid_field_references SET TABLESPACE tbl_ui;
