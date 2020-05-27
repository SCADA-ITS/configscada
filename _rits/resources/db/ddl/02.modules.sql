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
		module_type_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_module_types PRIMARY KEY (module_type_id)
	);
		
	ALTER TABLE ui.module_types SET TABLESPACE tbl_ui;

-- 
-- Table: ui.modules
-- Description: Modules table
-- Scope: ui
--
	CREATE TABLE ui.modules (
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
		menu_hierarchy_order int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		data_query varchar NULL,
		wss_topic varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_modules PRIMARY KEY (module_type_id, module_id)
	);
	
	CREATE INDEX idx_modules_module_types ON ui.modules USING btree (module_type_id);

	ALTER TABLE ui.modules ADD CONSTRAINT fk_modules_module_types FOREIGN KEY (module_type_id) REFERENCES ui.module_types(module_type_id);
		
	ALTER TABLE ui.modules SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.module_params
-- Description: Params for module table
-- Scope: ui
--
	CREATE TABLE ui.module_params (
		module_type_id int8 NOT NULL,
		module_param_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_module_params PRIMARY KEY (module_type_id, module_param_id)
	);
	
	CREATE INDEX idx_module_params_module_types ON ui.module_params USING btree (module_type_id);

	ALTER TABLE ui.module_params ADD CONSTRAINT fk_module_params_module_types FOREIGN KEY (module_type_id) REFERENCES ui.module_types(module_type_id);
		
	ALTER TABLE ui.module_params SET TABLESPACE tbl_ui;

-- 
-- Table: ui.module_values
-- Description: Param values for module table
-- Scope: ui
--
	CREATE TABLE ui.module_values (
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
		module_param_id int8 NOT NULL,
		value varchar NULL,
		CONSTRAINT pk_module_values PRIMARY KEY (module_type_id, module_id, module_param_id)
	);
	
	CREATE INDEX idx_module_values_modules ON ui.module_values USING btree (module_type_id, module_id);
	CREATE INDEX idx_module_values_module_params ON ui.module_values USING btree (module_type_id, module_param_id);

	ALTER TABLE ui.module_values ADD CONSTRAINT fk_module_values_modules FOREIGN KEY (module_type_id, module_id) REFERENCES ui.modules(module_type_id, module_id);
	ALTER TABLE ui.module_values ADD CONSTRAINT fk_module_values_module_params FOREIGN KEY (module_type_id, module_param_id) REFERENCES ui.module_params(module_type_id, module_param_id);
		
	ALTER TABLE ui.module_values SET TABLESPACE tbl_ui;

-- 
-- Table: ui.module_grid_field_group_operations
-- Description: Definition of type of group operations with fields in datagrid
-- Scope: ui
--
	CREATE TABLE ui.module_grid_field_group_operations (
		module_grid_field_group_operation_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		CONSTRAINT pk_module_grid_field_group_operations PRIMARY KEY (module_grid_field_group_operation_id)
	);
	
-- 
-- Table: ui.icons
-- Description: Main category for icons
-- Scope: ui
--
	CREATE TABLE ui.icons (
		icon_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		icon varchar NOT NULL,
		CONSTRAINT pk_icons PRIMARY KEY (icon_id)
	);

	ALTER TABLE ui.icons SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.icon_details
-- Description: Collection of icons for category
-- Scope: ui
--
	CREATE TABLE ui.icon_details (
		icon_id int8 NOT NULL,
		icon_detail_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		icon varchar NOT NULL,
		CONSTRAINT pk_icon_details PRIMARY KEY (icon_id, icon_detail_id)
	);
	
	CREATE INDEX idx_icon_details_icons ON ui.icon_details USING btree (icon_id);
	
	ALTER TABLE ui.icon_details ADD CONSTRAINT fk_icon_details_icons FOREIGN KEY (icon_id) REFERENCES ui.icons(icon_id);

	ALTER TABLE ui.icon_details SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.module_grid_fields
-- Description: Definition of fields for module of type grid
-- Scope: ui
--
	CREATE TABLE ui.module_grid_fields (
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
		module_grid_field_id int8 NOT NULL,
		position int8 NOT NULL,
		element_type_id int8 NOT NULL,
		param_type_id int8 NOT NULL,
		element_type_param_id int8 NOT NULL,
		label varchar NOT NULL,
		isPk bool NOT NULL,
		width_px int8 NOT NULL,
		module_grid_field_group_operation_id int8 NULL,
		module_grid_field_icon_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_module_grid_fields PRIMARY KEY (module_type_id, module_id, module_grid_field_id)
	);
	
	CREATE INDEX idx_module_grid_fields_modules ON ui.module_grid_fields USING btree (module_type_id, module_id);
	--CREATE INDEX idx_module_grid_fields_data_types ON ui.module_grid_fields USING btree (field_data_type_id);
	CREATE INDEX idx_module_grid_fields_modules_element_type_params ON ui.module_grid_fields USING btree (element_type_id, param_type_id, element_type_param_id);
	CREATE INDEX idx_module_grid_fields_module_grid_field_group_operations ON ui.module_grid_fields USING btree (module_grid_field_group_operation_id);

	ALTER TABLE ui.module_grid_fields ADD CONSTRAINT fk_module_grid_fields_modules FOREIGN KEY (module_type_id, module_id) REFERENCES ui.modules(module_type_id, module_id);
	--ALTER TABLE ui.module_grid_fields ADD CONSTRAINT fk_module_grid_fields_data_types FOREIGN KEY (field_data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE ui.module_grid_fields ADD CONSTRAINT fk_module_grid_fields_modules_element_type_params FOREIGN KEY (element_type_id, param_type_id, element_type_param_id) REFERENCES master.element_type_params(element_type_id, param_type_id, element_type_param_id);
	ALTER TABLE ui.module_grid_fields ADD CONSTRAINT fk_module_grid_fields_module_grid_field_group_operations FOREIGN KEY (module_grid_field_group_operation_id) REFERENCES ui.module_grid_field_group_operations(module_grid_field_group_operation_id);
		
	ALTER TABLE ui.module_grid_fields SET TABLESPACE tbl_ui;

-- 
-- Table: ui.module_grid_field_icons
-- Description: Relationship between field values in grid and icons associated
-- Scope: ui
--
	CREATE TABLE ui.module_grid_field_icons (
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
		module_grid_field_id int8 NOT NULL,
		module_grid_field_icon_id int8 NOT NULL,
		
		icon_id int8 NOT NULL,
		icon_detail_id int8 NOT NULL,
		value varchar NOT NULL,
		enabled bool,
		
		
		CONSTRAINT pk_module_grid_field_icons PRIMARY KEY (module_type_id, module_id, module_grid_field_id, module_grid_field_icon_id)
	);
	
	CREATE INDEX idx_module_grid_field_icons_module_grid_fields ON ui.module_grid_field_icons USING btree (module_type_id, module_id, module_grid_field_id);
	CREATE INDEX idx_module_grid_field_icons_icon_details ON ui.module_grid_field_icons USING btree (icon_id, icon_detail_id);
	
	ALTER TABLE ui.module_grid_field_icons ADD CONSTRAINT fk_module_grid_field_icons_module_grid_fields FOREIGN KEY (module_type_id, module_id, module_grid_field_id) REFERENCES ui.module_grid_fields(module_type_id, module_id, module_grid_field_id);
	ALTER TABLE ui.module_grid_field_icons ADD CONSTRAINT fk_module_grid_field_icons_icon_details FOREIGN KEY (icon_id, icon_detail_id) REFERENCES ui.icon_details(icon_id, icon_detail_id);
	
	ALTER TABLE ui.module_grid_field_icons SET TABLESPACE tbl_ui;
