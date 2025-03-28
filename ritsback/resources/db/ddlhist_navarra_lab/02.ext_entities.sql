-- Scope: hist
--

DO $$
BEGIN
    -- Verificar si el tipo enumerado ya existe
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'ext_entity_status') THEN
        -- Crear el tipo enumerado
        	CREATE TYPE ext_entity_status AS ENUM ('CREATED', 'DELETED', 'UPDATED');
    END IF;
END $$;

-- 
-- Table: hist.ext_entities
-- Descripción: Partes de incidencias
-- Scope: hist
--

	CREATE TABLE hist.ext_entities (
		ext_entity_id int8 NOT NULL,
		ext_entity_type_id int8 NOT NULL,
		ext_entity_subtype_id int8  NULL,
		uid varchar(100) NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		coordinates varchar NULL,
		auto_route boolean NULL,
		last_update timestamptz NOT NULL,
		generated_at timestamptz NOT NULL,
		created_at timestamptz NOT NULL,
		status ext_entity_status NOT NULL, 
		CONSTRAINT pk_ext_entities PRIMARY KEY (ext_entity_id, created_at)
	);

	CREATE INDEX idx_ext_entities_ext_entities_type ON hist.ext_entities USING btree (ext_entity_type_id);
	CREATE INDEX idx_ext_entities_ext_entities_subtype ON hist.ext_entities USING btree (ext_entity_subtype_id);
	
	ALTER TABLE hist.ext_entities SET TABLESPACE tbs_controltrafico_hist;
		
-- 
-- Table: hist.ext_entity_values
-- Descripción: Medidas de las entidades externas
-- Scope: hist
--
	CREATE TABLE hist.ext_entity_values (
		ext_entity_type_id int8 NOT NULL,
		ext_entity_id int8 NOT NULL,
		ext_entity_type_param_id int8 NOT NULL,
		value varchar NULL,
		created_at timestamptz NOT NULL,
		CONSTRAINT pk_ext_entity_values PRIMARY KEY (ext_entity_id, ext_entity_type_param_id, created_at)
	);

	CREATE INDEX idx_ext_entity_values_ext_entity ON hist.ext_entity_values USING btree (ext_entity_id);
	CREATE INDEX idx_ext_entity_values_ext_entity_type ON hist.ext_entity_values USING btree (ext_entity_type_id);


	ALTER TABLE hist.ext_entity_values SET TABLESPACE tbs_controltrafico_hist;
	


	