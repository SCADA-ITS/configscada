---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.maps
-- Description: Definition of map element
-- Scope: ui
--
	CREATE TABLE ui.maps (
		id serial primary key,	
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		longitude float4 not null,
		latitude float4 not null,
		zoom int not null,
		zoom_max int not null,
		zoom_min int not null,
		limit_init_lon float4 null,
		limit_init_lat float4 null,
		limit_end_lon float4 null,
		limit_end_lat float4 null,
		custom_provider_url varchar null, 
		default_zoom_search int null,
		dark_mode boolean NOT NULL
	);
	
	ALTER TABLE ui.maps SET TABLESPACE tbl_ui;

-- 
-- Table: ui.map_incidents
-- Description: Definition of map incidents
-- Scope: ui
--
	CREATE TABLE ui.map_incidents (
		id int NOT NULL,	
		incident_graphic_icon_id int NOT NULL,
		incident_report_module_action_id int NOT NULL,
		incident_wizard_module_action_id int NOT NULL,
		CONSTRAINT pk_map_incidents PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_map_incidents_graphic_icon ON ui.map_incidents USING btree (incident_graphic_icon_id);
	CREATE INDEX idx_map_incidents_incident_report_module_action_id ON ui.map_incidents USING btree (incident_report_module_action_id);
	
	ALTER TABLE ui.map_incidents ADD CONSTRAINT fk_map_incidents_graphic_icon FOREIGN KEY (incident_graphic_icon_id) REFERENCES ui.graphic_icons(id);
	ALTER TABLE ui.map_incidents ADD CONSTRAINT fk_map_incidents_incident_report_module_action_id FOREIGN KEY (incident_report_module_action_id) REFERENCES ui.module_actions(id);
	
	ALTER TABLE ui.map_incidents SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.map_layers
-- Description: Definition map layers
-- Scope: ui
--
	CREATE TABLE ui.map_layers (
		id serial primary key,	
		base_layer varchar NULL,
		map_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		visible boolean NOT NULL,
		active boolean NULL
	);
	
	CREATE INDEX idx_map_layers_map ON ui.map_layers USING btree (map_id);
	
	ALTER TABLE ui.map_layers ADD CONSTRAINT fk_map_layers_map FOREIGN KEY (map_id) REFERENCES ui.maps(id);
	
	ALTER TABLE ui.map_layers SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.map_layer_relationships
-- Description: Definition layers that contains other layers
-- Scope: ui
--
	CREATE TABLE ui.map_layer_relationships (
		id serial primary key,
		map_id int not null,
		parent_layer_id int null,
		child_layer_id int not null
	);
	
	CREATE INDEX idx_map_layer_relationships_maps ON ui.map_layer_relationships USING btree (map_id);
	CREATE INDEX idx_map_layer_relationships_parent ON ui.map_layer_relationships USING btree (parent_layer_id);
	CREATE INDEX idx_map_layer_relationships_child ON ui.map_layer_relationships USING btree (child_layer_id);
	
	ALTER TABLE ui.map_layer_relationships ADD CONSTRAINT fk_map_layer_relationships_maps FOREIGN KEY (map_id) REFERENCES ui.maps(id);
	ALTER TABLE ui.map_layer_relationships ADD CONSTRAINT fk_map_layer_relationships_parent FOREIGN KEY (parent_layer_id) REFERENCES ui.map_layers(id);
	ALTER TABLE ui.map_layer_relationships ADD CONSTRAINT fk_map_layer_relationships_child FOREIGN KEY (child_layer_id) REFERENCES ui.map_layers(id);
	
	ALTER TABLE ui.map_layer_relationships SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.map_layer_elements
-- Description: Definition layers that contains elements
-- Scope: ui
--
	CREATE TABLE ui.map_layer_elements (
		id serial primary key,	
		layer_id int not null,
		element_id varchar not null,
		graphic_icon_id int null,
		label varchar null,
		longitude float4 null,
		latitude float4 null,
		horizontal_flip boolean not null,
		rotate int null,
		tooltip boolean not null,
		show_text boolean not null,
		text_color varchar null,
		z_index_front int null ,
		text_size int null,
		text_pos int null,
		selectable boolean not null
	);
	
	CREATE INDEX idx_map_layer_elements_parent ON ui.map_layer_elements USING btree (layer_id);
	
	ALTER TABLE ui.map_layer_elements ADD CONSTRAINT fk_map_layer_elements_parent FOREIGN KEY (layer_id) REFERENCES ui.map_layers(id);
	ALTER TABLE ui.map_layer_elements ADD CONSTRAINT fk_map_layer_elements_graphic_icon FOREIGN KEY (graphic_icon_id) REFERENCES ui.graphic_icons(id);

	ALTER TABLE ui.map_layer_elements SET TABLESPACE tbl_ui;
	

-- 
-- Table: ui.map_layer_element
-- Description: Zoom specialization for custom elements
-- Scope: ui
--
	CREATE TABLE ui.map_layer_element_zooms (
		id serial primary key,
		map_layer_element_id int not null,
		zoom_lower int not null,
		zoom_upper int not null,
		graphic_icon_id int null,
		longitude float4 null,
		latitude float4 null,
		horizontal_flip boolean not null,
		rotate int null,
		tooltip boolean not null,
		show_text boolean not null,
		text_color varchar null,
		z_index_front int null
	);
	
	CREATE INDEX idx_map_layer_element_zooms_map_layer_element ON ui.map_layer_element_zooms USING btree (map_layer_element_id);
	
	ALTER TABLE ui.map_layer_element_zooms ADD CONSTRAINT fk_map_layer_element_zooms_map_layer_element FOREIGN KEY (map_layer_element_id) REFERENCES ui.map_layer_elements(id);
	ALTER TABLE ui.map_layer_element_zooms ADD CONSTRAINT fk_map_layer_element_zooms_graphic_icon FOREIGN KEY (graphic_icon_id) REFERENCES ui.graphic_icons(id);
	
	ALTER TABLE ui.map_layer_element_zooms SET TABLESPACE tbl_ui;

-- 
-- Table: ui.map_commands
-- Description: Definition map that contains commands
-- Scope: ui
--

	CREATE TABLE ui.map_commands (
		id serial NOT NULL,
		context bool NOT NULL DEFAULT false,
		map_id int4 NOT NULL,
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
		CONSTRAINT map_commands_pkey PRIMARY KEY (id),
		CONSTRAINT fk_map_commands_maps FOREIGN KEY (map_id) REFERENCES ui.maps(id),
		CONSTRAINT fk_map_commands_module_actions FOREIGN KEY (module_action_id) REFERENCES ui.module_actions(id),
		CONSTRAINT fk_map_commands_view_types FOREIGN KEY (view_type_id) REFERENCES ui.view_types(id)
	)
	TABLESPACE tbl_ui
	;
	CREATE INDEX idx_map_commands_maps ON ui.map_commands USING btree (map_id);
	CREATE INDEX idx_map_commands_module_actions ON ui.map_commands USING btree (module_action_id);
	CREATE INDEX idx_map_commands_view_types ON ui.map_commands USING btree (view_type_id);

-- 
-- Table: ui.map_element_type_commands
-- Description: Definition map_element_type_commands that contains the bind between element_type and map_command_id
-- Scope: ui
--

	CREATE TABLE ui.map_element_type_commands (
		id serial NOT NULL,
		element_type varchar NOT NULL,
		map_command_id int4 NOT NULL,
		module_action_id int4 NULL,
		view_type_id int4 NULL,
		CONSTRAINT map_element_type_commands_pkey PRIMARY KEY (id),
		CONSTRAINT fk_map_element_type_commands_map_commands FOREIGN KEY (map_command_id) REFERENCES ui.map_commands(id),
		CONSTRAINT fk_map_element_type_commands_module_actions FOREIGN KEY (module_action_id) REFERENCES ui.module_actions(id),
		CONSTRAINT fk_map_element_type_commands_view_types FOREIGN KEY (view_type_id) REFERENCES ui.view_types(id)
	)
	TABLESPACE tbl_ui
	;
	

-- 
-- Table: ui.map_zones
-- Description: Definition of map element
-- Scope: ui
--
	CREATE TABLE ui.map_zones (
		id serial primary key,
		map_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		longitude float4 not null,
		latitude float4 not null,
		zoom int not null
	);
	
	CREATE INDEX idx_map_zones_map ON ui.map_zones USING btree (map_id);
	
	ALTER TABLE ui.map_zones ADD CONSTRAINT fk_map_zones_map FOREIGN KEY (map_id) REFERENCES ui.maps(id);
	
	ALTER TABLE ui.map_zones SET TABLESPACE tbl_ui;
	
	