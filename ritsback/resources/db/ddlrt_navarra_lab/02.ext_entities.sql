-- 
-- Table: rt.ext_entities
-- Descripción: Entidades externas
-- Scope: rt
--

	CREATE SEQUENCE rt.last_ext_entities_id START 1;
		
	CREATE TABLE rt.ext_entities (
		ext_entity_id int8 default nextval('rt.last_ext_entities_id'),
		ext_entity_type_id int8 NOT NULL,
		ext_entity_subtype_id int8  NULL,
		uid varchar(100) NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		coordinates varchar NULL,
		auto_route boolean NULL,
		enabled bool NULL,
		visible bool NULL,
		last_update timestamptz NOT NULL,
		generated_at timestamptz NOT NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ext_entities PRIMARY KEY (ext_entity_id),
		CONSTRAINT unique_uid_per_type UNIQUE (ext_entity_type_id, uid) 
	);
	
	CREATE INDEX idx_ext_entities_ext_entity_types ON rt.ext_entities USING btree (ext_entity_type_id);
	CREATE INDEX idx_ext_entities_ext_entity_subtypes ON rt.ext_entities USING btree (ext_entity_subtype_id);

	ALTER TABLE rt.ext_entities ADD CONSTRAINT fk_ext_entities_ext_entity_types FOREIGN KEY (ext_entity_type_id) REFERENCES static.ext_entity_types(ext_entity_type_id);
	ALTER TABLE rt.ext_entities ADD CONSTRAINT fk_ext_entities_ext_entity_subtypes FOREIGN KEY (ext_entity_subtype_id) REFERENCES static.ext_entity_subtypes(ext_entity_subtype_id);
	
	ALTER TABLE rt.ext_entities SET TABLESPACE tbs_controltrafico_rt;
	
-- 
-- Table: rt.ext_entity_values
-- Descripción: Medidas de las entidades externas
-- Scope: rt
--
	CREATE TABLE rt.ext_entity_values (
		ext_entity_type_id int8 NOT NULL,
		ext_entity_id int8 NOT NULL,
		ext_entity_type_param_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_ext_entity_values PRIMARY KEY (ext_entity_id, ext_entity_type_param_id)
	);

	CREATE INDEX idx_ext_entity_values_ext_entity ON rt.ext_entity_values USING btree (ext_entity_id);

	ALTER TABLE rt.ext_entity_values ADD CONSTRAINT fk_ext_entity_values_ext_entity_type_params FOREIGN KEY (ext_entity_type_id, ext_entity_type_param_id) REFERENCES static.ext_entity_type_params(ext_entity_type_id, ext_entity_type_param_id);
	ALTER TABLE rt.ext_entity_values ADD CONSTRAINT fk_ext_entity_values_ext_entity FOREIGN KEY (ext_entity_id) REFERENCES rt.ext_entities(ext_entity_id);

	ALTER TABLE rt.ext_entity_values SET TABLESPACE tbs_controltrafico_rt;

