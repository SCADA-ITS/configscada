---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.graphic_icons
-- Description: Representation of customized graphics for equipments in synoptics, maps, etc
-- Scope: ui
--
	CREATE TABLE ui.graphic_icons (
		id int NOT NULL,
		"name" varchar NOT NULL,
		description varchar NULL,
		alarms bool NULL DEFAULT false,
		measures bool NULL DEFAULT false,
		js_draw_file varchar NULL,
		width float4 NULL,
		height float4 NULL,
		svg_pattern_var varchar NULL,
		svg_file varchar NOT NULL,
		js_custom_type_render varchar NULL,
		keep_aspect_ratio boolean NULL,
		CONSTRAINT pk_graphic_icons PRIMARY KEY (id)
	)
	TABLESPACE tbs_controltrafico_ui
	;

-- 
-- Table: ui.synoptics
-- Description: Definition of synoptic element
-- Scope: ui
--
	CREATE TABLE ui.synoptics (
		id serial primary key,	
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		base_layer varchar NOT NULL,
		exclusive_layers boolean NOT NULL
	);
	
	ALTER TABLE ui.synoptics SET TABLESPACE tbs_controltrafico_ui;
	
-- 
-- Table: ui.synoptic_layers
-- Description: Definition synoptic layers
-- Scope: ui
--
	CREATE TABLE ui.synoptic_layers (
		id serial primary key,
		base_layer varchar NULL,
		synoptic_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		visible boolean NOT NULL,
		active boolean NULL
	);
	
	CREATE INDEX idx_synoptic_layers_synoptic ON ui.synoptic_layers USING btree (synoptic_id);
	
	ALTER TABLE ui.synoptic_layers ADD CONSTRAINT fk_synoptic_layers_synoptic FOREIGN KEY (synoptic_id) REFERENCES ui.synoptics(id);
	
	ALTER TABLE ui.synoptic_layers SET TABLESPACE tbs_controltrafico_ui;
	
-- 
-- Table: ui.synoptic_layer_relationships
-- Description: Definition layers that contains other layers
-- Scope: ui
--
	CREATE TABLE ui.synoptic_layer_relationships (
		id serial primary key,
		synoptic_id int not null,
		parent_layer_id int null,
		child_layer_id int not null
	);
	
	CREATE INDEX idx_synoptic_layer_relationships_synoptic ON ui.synoptic_layer_relationships USING btree (synoptic_id);
	CREATE INDEX idx_synoptic_layer_relationships_parent ON ui.synoptic_layer_relationships USING btree (parent_layer_id);
	CREATE INDEX idx_synoptic_layer_relationships_child ON ui.synoptic_layer_relationships USING btree (child_layer_id);
	
	ALTER TABLE ui.synoptic_layer_relationships ADD CONSTRAINT fk_synoptic_layer_relationships_synoptic FOREIGN KEY (synoptic_id) REFERENCES ui.synoptics(id);
	ALTER TABLE ui.synoptic_layer_relationships ADD CONSTRAINT fk_synoptic_layer_relationships_parent FOREIGN KEY (parent_layer_id) REFERENCES ui.synoptic_layers(id);
	ALTER TABLE ui.synoptic_layer_relationships ADD CONSTRAINT fk_synoptic_layer_relationships_child FOREIGN KEY (child_layer_id) REFERENCES ui.synoptic_layers(id);
	
	ALTER TABLE ui.synoptic_layer_relationships SET TABLESPACE tbs_controltrafico_ui;
	
-- 
-- Table: ui.synoptic_layer_elements
-- Description: Definition layers that contains elements
-- Scope: ui
--
	CREATE TABLE ui.synoptic_layer_elements (
		id serial primary key,	
		layer_id int not null,
		element_id varchar not null,
		graphic_icon_id int null,
		"label" varchar null,
		x int not null,
		y int not null,
		horizontal_flip boolean not null,
		rotate int null,
		tooltip boolean not null,
		show_text boolean not null,
		text_color varchar null,
		text_size int null,
		text_pos int null,
		selectable boolean not null
	);
	
	CREATE INDEX idx_synoptic_layer_elements_parent ON ui.synoptic_layer_elements USING btree (layer_id);
	CREATE INDEX idx_synoptic_layer_elements_graphic_icon ON ui.synoptic_layer_elements USING btree (graphic_icon_id);
	
	ALTER TABLE ui.synoptic_layer_elements ADD CONSTRAINT fk_synoptic_layer_elements_parent FOREIGN KEY (layer_id) REFERENCES ui.synoptic_layers(id);
	ALTER TABLE ui.synoptic_layer_elements ADD CONSTRAINT fk_synoptic_layer_elements_custom_graphic_equipment FOREIGN KEY (graphic_icon_id) REFERENCES ui.graphic_icons(id);
	
	ALTER TABLE ui.synoptic_layer_elements SET TABLESPACE tbs_controltrafico_ui;

-- 
-- Table: ui.synoptic_commands
-- Description: Definition synoptic that contains commands
-- Scope: ui
--

	CREATE TABLE ui.synoptic_commands (
		id serial NOT NULL,
		context bool NOT NULL DEFAULT false,
		synoptic_id int4 NOT NULL,
		"position" int4 NOT NULL,
		"label" varchar NOT NULL,
		icon varchar NULL,
		"default" bool NOT NULL,
		module_action_id int4 NOT NULL,
		args varchar NULL,
		view_type_id int4 NULL,
		multiselect bool NOT NULL,
		show_text bool NULL,
		item_required bool NULL,
		CONSTRAINT synoptic_commands_pkey PRIMARY KEY (id),
		CONSTRAINT fk_synoptic_commands_synoptics FOREIGN KEY (synoptic_id) REFERENCES ui.synoptics(id),
		CONSTRAINT fk_synoptic_commands_module_actions FOREIGN KEY (module_action_id) REFERENCES ui.module_actions(id),
		CONSTRAINT fk_synoptic_commands_view_types FOREIGN KEY (view_type_id) REFERENCES ui.view_types(id)
	)
	TABLESPACE tbs_controltrafico_ui
	;
	CREATE INDEX idx_synoptic_commands_synoptics ON ui.synoptic_commands USING btree (synoptic_id);
	CREATE INDEX idx_synoptic_commands_module_actions ON ui.synoptic_commands USING btree (module_action_id);
	CREATE INDEX idx_synoptic_commands_view_types ON ui.synoptic_commands USING btree (view_type_id);

-- 
-- Table: ui.synoptic_element_type_commands
-- Description: Definition synoptic_element_type_commands that contains the bind between element_type and synoptic_command_id
-- Scope: ui
--

	CREATE TABLE ui.synoptic_element_type_commands (
		id serial NOT NULL,
		element_type varchar NOT NULL,
		synoptic_command_id int4 NOT NULL,
		module_action_id int4 NULL,
		view_type_id int4 NULL,
		CONSTRAINT synoptic_element_type_commands_pkey PRIMARY KEY (id),
		CONSTRAINT fk_synoptic_element_type_commands_synoptic_commands FOREIGN KEY (synoptic_command_id) REFERENCES ui.synoptic_commands(id),
		CONSTRAINT fk_synoptic_element_type_commands_module_actions FOREIGN KEY (module_action_id) REFERENCES ui.module_actions(id),
		CONSTRAINT fk_synoptic_element_type_commands_view_types FOREIGN KEY (view_type_id) REFERENCES ui.view_types(id)
	)
	TABLESPACE tbs_controltrafico_ui
	;
