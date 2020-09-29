---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.vo_entity_types
-- Description: External entity types
-- Scope: ui
--
	CREATE TABLE ui.vo_entity_types (
		id varchar primary key,
		alias varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	ALTER TABLE ui.vo_entity_types SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.vo_entity_fields
-- Description: External entity type fields
-- Scope: ui
--
	CREATE TABLE ui.vo_entity_fields (
		id int8 primary key,
		vo_entity_type_id varchar NOT NULL,
		field varchar NOT NULL,
		description varchar NULL,
		label_field varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_vo_entity_fields_vo_entity_types ON ui.vo_entity_fields USING btree (vo_entity_type_id);
	
	ALTER TABLE ui.vo_entity_fields ADD CONSTRAINT fk_vo_entity_fields_vo_entity_types FOREIGN KEY (vo_entity_type_id) REFERENCES ui.vo_entity_types(id);
	
	ALTER TABLE ui.vo_entity_fields SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.vo_entities
-- Description: External entities
-- Scope: ui
--
	CREATE TABLE ui.vo_entities (
		id varchar primary key,
		vo_entity_type_id varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_vo_entities_vo_entity_types ON ui.vo_entities USING btree (vo_entity_type_id);
	
	ALTER TABLE ui.vo_entities ADD CONSTRAINT fk_vo_entities_entity_types FOREIGN KEY (vo_entity_type_id) REFERENCES ui.vo_entity_types(id);
	
	ALTER TABLE ui.vo_entities SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.vo_entity_values
-- Description: External entity values
-- Scope: ui
--
	CREATE TABLE ui.vo_entity_values (
	    id serial primary key,
		vo_entity_id varchar NOT NULL,
		vo_entity_field_id int8 NOT NULL,
		value varchar NULL,
		UNIQUE (vo_entity_id, vo_entity_field_id)
	);
	
	CREATE INDEX idx_vo_entity_values_vo_entities ON ui.vo_entity_values USING btree (vo_entity_id);
	CREATE INDEX idx_vo_entity_values_vo_entity_fields ON ui.vo_entity_values USING btree (vo_entity_field_id);

	ALTER TABLE ui.vo_entity_values ADD CONSTRAINT fk_vo_entity_values_vo_entities FOREIGN KEY (vo_entity_id) REFERENCES ui.vo_entities(id);
	ALTER TABLE ui.vo_entity_values ADD CONSTRAINT fk_vo_entity_values_vo_entity_fields FOREIGN KEY (vo_entity_field_id) REFERENCES ui.vo_entity_fields(id);
		
	ALTER TABLE ui.vo_entity_values SET TABLESPACE tbl_ui;
	
