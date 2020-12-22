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
		base_map_url varchar NOT NULL,
		exclusive_layers boolean NOT NULL
	);
	
	ALTER TABLE ui.maps SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.map_layers
-- Description: Definition map layers
-- Scope: ui
--
	CREATE TABLE ui.map_layers (
		id serial primary key,	
		map_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		visible boolean NOT NULL
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
		parent_layer_id int not null,
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
		custom_graphic_equipment_id int null,
		label varchar null,
		width int null,
		height int null,
		horizontal_flip boolean not null
	);
	
	CREATE INDEX idx_map_layer_elements_parent ON ui.map_layer_elements USING btree (layer_id);
	CREATE INDEX idx_map_layer_elements_custom_graphic_equipment ON ui.map_layer_elements USING btree (custom_graphic_equipment_id);
	
	ALTER TABLE ui.map_layer_elements ADD CONSTRAINT fk_map_layer_elements_parent FOREIGN KEY (layer_id) REFERENCES ui.map_layers(id);
	ALTER TABLE ui.map_layer_elements ADD CONSTRAINT fk_map_layer_elements_custom_graphic_equipment FOREIGN KEY (custom_graphic_equipment_id) REFERENCES ui.custom_graphic_equipments(id);
	
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
		custom_graphic_equipment_id int null,
		width int null,
		height int null,
		horizontal_flip boolean not null
	);
	
	CREATE INDEX idx_map_layer_element_zooms_map_layer_element ON ui.map_layer_element_zooms USING btree (map_layer_element_id);
	CREATE INDEX idx_map_layer_element_zooms_graphic_equipment ON ui.map_layer_element_zooms USING btree (custom_graphic_equipment_id);
	
	ALTER TABLE ui.map_layer_element_zooms ADD CONSTRAINT fk_map_layer_element_zooms_map_layer_element FOREIGN KEY (map_layer_element_id) REFERENCES ui.map_layer_elements(id);
	ALTER TABLE ui.map_layer_element_zooms ADD CONSTRAINT fk_map_layer_element_zooms_custom_graphic_equipment FOREIGN KEY (custom_graphic_equipment_id) REFERENCES ui.custom_graphic_equipments(id);
	
	ALTER TABLE ui.map_layer_element_zooms SET TABLESPACE tbl_ui;
	
