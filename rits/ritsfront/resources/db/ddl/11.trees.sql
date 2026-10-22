---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.trees
-- Description: Definition of tree component
-- Scope: ui
--
	CREATE TABLE ui.trees (
		id int not null,
		adapter_id int NULL,
		label varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_trees PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.trees SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.tree_options
-- Description: Tree options
-- Scope: ui
--
	CREATE TABLE ui.tree_options (
		id int not null,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_tree_options PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.tree_options SET TABLESPACE tbl_ui;

-- 
-- Table: ui.tree_option_values
-- Description: Tree options values
-- Scope: ui
--
	CREATE TABLE ui.tree_option_values (
		id serial PRIMARY key,
		tree_id int NOT NULL,
		tree_option_id int NOT NULL,
		value varchar NULL,
		UNIQUE (tree_id, tree_option_id)
	);
	
	CREATE INDEX idx_tree_option_values_trees ON ui.tree_option_values USING btree (tree_id);
	CREATE INDEX idx_tree_option_values_tree_options ON ui.tree_option_values USING btree (tree_option_id);

	ALTER TABLE ui.tree_option_values ADD CONSTRAINT fk_tree_option_values_trees FOREIGN KEY (tree_id) REFERENCES ui.trees(id);
	ALTER TABLE ui.tree_option_values ADD CONSTRAINT fk_tree_option_values_tree_options FOREIGN KEY (tree_option_id) REFERENCES ui.tree_options(id);
		
	ALTER TABLE ui.tree_option_values SET TABLESPACE tbl_ui;
-- 
-- Table: ui.tree_commands
-- Description: Commands available on tree
-- Scope: ui
--
	CREATE TABLE ui.tree_commands (
		id serial primary key,
		tree_id int NOT NULL,
		position int NOT NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		"default" bool NOT NULL,
		module_action_id int NOT NULL,
		args varchar NULL,
		view_type_id int NULL,
		multiselect bool NOT NULL,
		item_required bool NULL,
		grouped bool NULL
	);
	
	CREATE INDEX idx_tree_commands_trees ON ui.tree_commands USING btree (tree_id);
	CREATE INDEX idx_tree_commands_module_actions ON ui.tree_commands USING btree (module_action_id);
	CREATE INDEX idx_tree_commands_view_types ON ui.tree_commands USING btree (view_type_id);
	
	ALTER TABLE ui.tree_commands ADD CONSTRAINT fk_tree_commands_trees FOREIGN KEY (tree_id) REFERENCES ui.trees(id);
	ALTER TABLE ui.tree_commands ADD CONSTRAINT fk_tree_commands_module_actions FOREIGN KEY (module_action_id) REFERENCES ui.module_actions(id);
	ALTER TABLE ui.tree_commands ADD CONSTRAINT fk_tree_commands_view_types FOREIGN KEY (view_type_id) REFERENCES ui.view_types(id);
	
	ALTER TABLE ui.tree_commands SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.tree_params
-- Description: params available of tree
-- Scope: ui
--
	CREATE TABLE ui.tree_params (
		id int not null,
		tree_id int NOT NULL,
		name varchar NOT NULL,
		CONSTRAINT pk_tree_item_params PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_tree_params_trees ON ui.tree_params USING btree (tree_id);
	
	ALTER TABLE ui.tree_params ADD CONSTRAINT fk_tree_params_trees FOREIGN KEY (tree_id) REFERENCES ui.trees(id);
	
	ALTER TABLE ui.tree_params SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.tree_styles
-- Description: Styles types
-- Scope: ui
--
	CREATE TABLE ui.tree_styles (
		id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_tree_styles PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.tree_styles SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.tree_param_styles
-- Description: Styles depending on the parameter value
-- Scope: ui
--
	CREATE TABLE ui.tree_param_styles (
		id int primary key,
		tree_style_id int NOT NULL,
		tree_param_id int NOT NULL,
		operator varchar NULL,
		value1 varchar NULL,
		value2 varchar NULL,
		func varchar NULL,
		resource varchar NULL
	);
	
	CREATE INDEX idx_tree_param_styles_tree_styles ON ui.tree_param_styles USING btree (tree_style_id);
	CREATE INDEX idx_tree_param_styles_tree_params ON ui.tree_param_styles USING btree (tree_param_id);
	
	ALTER TABLE ui.tree_param_styles ADD CONSTRAINT fk_tree_param_styles_tree_styles FOREIGN KEY (tree_style_id) REFERENCES ui.tree_styles(id);
	ALTER TABLE ui.tree_param_styles ADD CONSTRAINT fk_tree_param_styles_tree_params FOREIGN KEY (tree_param_id) REFERENCES ui.tree_params(id);
	
	ALTER TABLE ui.tree_param_styles SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.tree_filters
-- Description: available predefined filters for tree
-- Scope: ui
--
	CREATE TABLE ui.tree_filters (
		id int not null,
		tree_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_tree_filters PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_tree_filters_trees ON ui.tree_filters USING btree (tree_id);
	
	ALTER TABLE ui.tree_filters ADD CONSTRAINT fk_tree_filters_trees FOREIGN KEY (tree_id) REFERENCES ui.trees(id);
	
	ALTER TABLE ui.tree_filters SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.tree_filters
-- Description: available predefined filters for tree
-- Scope: ui
--
	CREATE TABLE ui.tree_filter_params (
		id int primary key,
		tree_id int NOT NULL,
		tree_param_id int NOT NULL,
		operator varchar NULL,
		value1 varchar NULL,
		value2 varchar NULL,
		func varchar NULL
	);
	
	CREATE INDEX idx_tree_filter_params_trees ON ui.tree_filter_params USING btree (tree_id);
	CREATE INDEX idx_tree_filter_params_tree_params ON ui.tree_filter_params USING btree (tree_param_id);
	
	ALTER TABLE ui.tree_filter_params ADD CONSTRAINT fk_tree_filter_params_trees FOREIGN KEY (tree_id) REFERENCES ui.trees(id);
	ALTER TABLE ui.tree_filter_params ADD CONSTRAINT fk_tree_filter_params_tree_params FOREIGN KEY (tree_param_id) REFERENCES ui.tree_params(id);
	
	ALTER TABLE ui.tree_filter_params SET TABLESPACE tbl_ui;
