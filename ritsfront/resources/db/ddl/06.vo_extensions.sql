---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.vo_enum_types
-- Description: Enumerate types
-- Scope: ui
--
	CREATE TABLE ui.vo_enum_types (
		id int not null,
		alias varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_vo_enum_types PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.vo_enum_types SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.vo_enum_type_values
-- Description: Enumerate type values
-- Scope: ui
--
	CREATE TABLE ui.vo_enum_type_values (
		id int not null,
		vo_enum_type_id int NOT NULL,
		key varchar NOT NULL,
		value varchar NULL,
		translate bool NULL,
		image varchar NULL,
		CONSTRAINT pk_vo_enum_type_values PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_vo_enum_type_values_vo_enum_types ON ui.vo_enum_type_values USING btree (vo_enum_type_id);
	
	ALTER TABLE ui.vo_enum_type_values ADD CONSTRAINT fk_vo_enum_type_values_vo_enum_types FOREIGN KEY (vo_enum_type_id) REFERENCES ui.vo_enum_types(id);

	ALTER TABLE ui.vo_enum_type_values SET TABLESPACE tbl_ui;

-- 
-- Table: ui.vo_entity_types
-- Description: External entity types
-- Scope: ui
--
	CREATE TABLE ui.vo_entity_types (
		id varchar not null,
		alias varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_vo_entity_types PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.vo_entity_types SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.vo_entity_fields
-- Description: External entity type fields
-- Scope: ui
--
	CREATE TABLE ui.vo_entity_fields (
		id int not null,
		vo_entity_type_id varchar NOT NULL,
		field varchar NOT NULL,
		description varchar NULL,
		label_field varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_vo_entity_fields PRIMARY KEY (id)
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
		id varchar not null,
		vo_entity_type_id varchar NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_vo_entities PRIMARY KEY (id)
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
		vo_entity_field_id int NOT NULL,
		value varchar NULL,
		UNIQUE (vo_entity_id, vo_entity_field_id)
	);
	
	CREATE INDEX idx_vo_entity_values_vo_entities ON ui.vo_entity_values USING btree (vo_entity_id);
	CREATE INDEX idx_vo_entity_values_vo_entity_fields ON ui.vo_entity_values USING btree (vo_entity_field_id);

	ALTER TABLE ui.vo_entity_values ADD CONSTRAINT fk_vo_entity_values_vo_entities FOREIGN KEY (vo_entity_id) REFERENCES ui.vo_entities(id);
	ALTER TABLE ui.vo_entity_values ADD CONSTRAINT fk_vo_entity_values_vo_entity_fields FOREIGN KEY (vo_entity_field_id) REFERENCES ui.vo_entity_fields(id);
		
	ALTER TABLE ui.vo_entity_values SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.vo_entity_enum_types
-- Description: Enum types associated to entities
-- Scope: ui
--
	CREATE TABLE ui.vo_entity_enum_types (
	    id serial primary key,
	    vo_entity_id varchar NOT NULL,
	    enum_type_id int NOT NULL,
		UNIQUE (vo_entity_id, enum_type_id)
	);
	
	CREATE INDEX idx_vo_entity_enum_types_vo_entities ON ui.vo_entity_enum_types USING btree (vo_entity_id);
	CREATE INDEX idx_vo_entity_enum_types_vo_enum_types ON ui.vo_entity_enum_types USING btree (enum_type_id);

	ALTER TABLE ui.vo_entity_enum_types ADD CONSTRAINT fk_vo_entity_enum_types_vo_entities FOREIGN KEY (vo_entity_id) REFERENCES ui.vo_entities(id);
	ALTER TABLE ui.vo_entity_enum_types ADD CONSTRAINT fk_vo_entity_enum_types_vo_enum_types FOREIGN KEY (enum_type_id) REFERENCES ui.vo_enum_types(id);
	
		
	ALTER TABLE ui.vo_entity_enum_types SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.lvo_entity_hierarchies
-- Description: Relations between vo elements
-- Scope: ui
--
	CREATE TABLE ui.vo_entity_hierarchies (
		id serial primary key,	
		parent_id varchar NOT NULL,
		child_id varchar NOT NULL,
		position int NOT NULL
	);
	
	CREATE INDEX idx_vo_entity_hierarchies_parent ON ui.vo_entity_hierarchies USING btree (parent_id);
	CREATE INDEX idx_vo_entity_hierarchies_child ON ui.vo_entity_hierarchies USING btree (child_id);
	
	ALTER TABLE ui.vo_entity_hierarchies ADD CONSTRAINT fk_vo_entity_hierarchies_parent FOREIGN KEY (parent_id) REFERENCES ui.vo_entities(id);
	ALTER TABLE ui.vo_entity_hierarchies ADD CONSTRAINT fk_vo_entity_hierarchies_child FOREIGN KEY (child_id) REFERENCES ui.vo_entities(id);
	
	ALTER TABLE ui.vo_entity_hierarchies SET TABLESPACE tbl_ui;
	

