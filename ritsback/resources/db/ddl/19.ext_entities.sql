-- 
-- Table: static.ext_entity_types
-- Descripción: Librería de ext_entity_types genéricos en el sistema
-- Scope: static
--
	CREATE TABLE static.ext_entity_types (
		ext_entity_type_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		persist bool NULL,
		on_process varchar NULL,
		on_change varchar NULL,
		cron_expression varchar NULL,
		config_params varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ext_entity_types PRIMARY KEY (ext_entity_type_id)
	);
	
	ALTER TABLE static.ext_entity_types SET TABLESPACE tbl_static;
		
-- 
-- Table: static.ext_entity_subtypes
-- Descripción: Librería de ext_entity_subtypes genéricos en el sistema
-- Scope: static
--
	CREATE TABLE static.ext_entity_subtypes (
		ext_entity_type_id int8 NOT NULL,
		ext_entity_subtype_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ext_entity_subtypes PRIMARY KEY (ext_entity_subtype_id)
	);
	
	ALTER TABLE static.ext_entity_subtypes ADD CONSTRAINT fk_ext_entity_subtype_ext_entity_types FOREIGN KEY (ext_entity_type_id) REFERENCES static.ext_entity_types(ext_entity_type_id);
    CREATE INDEX idx_ext_entity_subtypes_ext_entities ON static.ext_entity_subtypes USING btree (ext_entity_type_id);

	
	ALTER TABLE static.ext_entity_subtypes SET TABLESPACE tbl_static;
	
-- Table: static.element_type_params
-- Descripción: Librería de parámetros para un determinado element_typees del sistema
-- Scope: static
--
	CREATE TABLE static.ext_entity_type_params (
		ext_entity_type_id int8 NOT NULL,
		ext_entity_type_param_id int8 NOT NULL,
		default_value varchar NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
	    data_type_id int8 NOT NULL,
		unit_id int8 NULL,
	    enabled bool NULL,
		visible bool NULL,
		editable bool NULL DEFAULT false,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ext_entity_type_params PRIMARY KEY (ext_entity_type_id, ext_entity_type_param_id)
	);
	

	ALTER TABLE static.ext_entity_type_params ADD CONSTRAINT fk_ext_entity_type_params_ext_entity_types FOREIGN KEY (ext_entity_type_id) REFERENCES static.ext_entity_types(ext_entity_type_id);
	ALTER TABLE static.ext_entity_type_params ADD CONSTRAINT fk_ext_entity_type_params_data_types FOREIGN KEY (data_type_id) REFERENCES master.data_types(data_type_id);
	ALTER TABLE static.ext_entity_type_params ADD CONSTRAINT fk_ext_entity_type_params_param_unit FOREIGN KEY (unit_id) REFERENCES master.element_type_param_unit(element_type_param_unit_id);

	ALTER TABLE static.ext_entity_type_params SET TABLESPACE tbl_static;
	
-- 
-- Table: conf.management_area_users
-- Descripción: 
-- Scope: static
--
	CREATE TABLE static.management_area_ext_entity_types (
		management_area_id int8 NOT NULL,
		ext_entity_type_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_management_area_ext_entity_types PRIMARY KEY (management_area_id, ext_entity_type_id)
	);


	CREATE INDEX idx_management_area_ext_entity_types ON static.management_area_ext_entity_types USING btree (ext_entity_type_id);

	ALTER TABLE static.management_area_ext_entity_types ADD CONSTRAINT fk_management_ext_entities_management_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);
	ALTER TABLE static.management_area_ext_entity_types ADD CONSTRAINT fk_management_ext_entities_ext_entity_type FOREIGN KEY (ext_entity_type_id) REFERENCES static.ext_entity_types(ext_entity_type_id);
	
	
	ALTER TABLE static.management_area_ext_entity_types SET TABLESPACE tbl_static;
	
	