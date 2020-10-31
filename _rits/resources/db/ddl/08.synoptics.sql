---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

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
	
	ALTER TABLE ui.synoptics SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.synoptic_layers
-- Description: Definition synoptic layers
-- Scope: ui
--
	CREATE TABLE ui.synoptic_layers (
		id serial primary key,
		synoptic_id int NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		visible boolean NOT NULL
	);
	
	CREATE INDEX idx_synoptic_layers_synoptic ON ui.synoptic_layers USING btree (synoptic_id);
	
	ALTER TABLE ui.synoptic_layers ADD CONSTRAINT fk_synoptic_layers_synoptic FOREIGN KEY (synoptic_id) REFERENCES ui.synoptics(id);
	
	ALTER TABLE ui.synoptic_layers SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.synoptic_layer_relationships
-- Description: Definition layers that contains other layers
-- Scope: ui
--
	CREATE TABLE ui.synoptic_layer_relationships (
		id serial primary key,
		synoptic_id int not null,
		parent_layer_id int not null,
		child_layer_id int not null
	);
	
	CREATE INDEX idx_synoptic_layer_relationships_synoptic ON ui.synoptic_layer_relationships USING btree (synoptic_id);
	CREATE INDEX idx_synoptic_layer_relationships_parent ON ui.synoptic_layer_relationships USING btree (parent_layer_id);
	CREATE INDEX idx_synoptic_layer_relationships_child ON ui.synoptic_layer_relationships USING btree (child_layer_id);
	
	ALTER TABLE ui.synoptic_layer_relationships ADD CONSTRAINT fk_synoptic_layer_relationships_synoptic FOREIGN KEY (synoptic_id) REFERENCES ui.synoptics(id);
	ALTER TABLE ui.synoptic_layer_relationships ADD CONSTRAINT fk_synoptic_layer_relationships_parent FOREIGN KEY (parent_layer_id) REFERENCES ui.synoptic_layers(id);
	ALTER TABLE ui.synoptic_layer_relationships ADD CONSTRAINT fk_synoptic_layer_relationships_child FOREIGN KEY (child_layer_id) REFERENCES ui.synoptic_layers(id);
	
	ALTER TABLE ui.synoptic_layer_relationships SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.synoptic_layer_elements
-- Description: Definition layers that contains elements
-- Scope: ui
--
	CREATE TABLE ui.synoptic_layer_elements (
		id serial primary key,	
		layer_id int not null,
		element_id varchar not null,
		custom_graphic_equipment_id int null,
		label varchar null,
		x int not null,
		y int not null,
		width int null,
		height int null,
		horizontal_flip boolean not null
	);
	
	CREATE INDEX idx_synoptic_layer_elements_parent ON ui.synoptic_layer_elements USING btree (layer_id);
	CREATE INDEX idx_synoptic_layer_elements_custom_graphic_equipment ON ui.synoptic_layer_elements USING btree (custom_graphic_equipment_id);
	
	ALTER TABLE ui.synoptic_layer_elements ADD CONSTRAINT fk_synoptic_layer_elements_parent FOREIGN KEY (layer_id) REFERENCES ui.synoptic_layers(id);
	ALTER TABLE ui.synoptic_layer_elements ADD CONSTRAINT fk_synoptic_layer_elements_custom_graphic_equipment FOREIGN KEY (custom_graphic_equipment_id) REFERENCES ui.custom_graphic_equipments(id);
	
	ALTER TABLE ui.synoptic_layer_elements SET TABLESPACE tbl_ui;
	
