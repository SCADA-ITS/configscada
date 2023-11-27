--DROP TABLE IF EXISTS master.i18n_labels;
--DROP TABLE IF EXISTS master.i18n_modules;
--DROP TABLE IF EXISTS master.i18n_locales;

---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------
	
-- 
-- Table: master.i18n_locales
-- Descripción: Lenguajes
-- Scope: master
--
	CREATE TABLE master.i18n_locales (
		locale_code varchar NOT NULL,
		name varchar NOT NULL,
		enabled bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_i18n_locales PRIMARY KEY (locale_code)
	);
	
	ALTER TABLE master.i18n_locales SET TABLESPACE tbl_master;
	
-- 
-- Table: master.i18n_modules
-- Descripción: Modulos
-- Scope: master
--
	CREATE TABLE master.i18n_modules (
		module_id int8 NOT NULL,
  		module varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_i18n_modules PRIMARY KEY (module_id)
	);

	ALTER TABLE master.i18n_modules SET TABLESPACE tbl_master;

-- 
-- Table: master.i18n_labels
-- Descripción: Traducciones
-- Scope: master
--
	CREATE TABLE master.i18n_labels (
		locale_code varchar NOT NULL,
		module_id int8 NOT NULL,
		label varchar NOT NULL,
		translation varchar NOT NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_i18n_labels PRIMARY KEY (locale_code, module_id, label)
	);

	CREATE INDEX idx_i18n_labels_i18n_locales ON master.i18n_labels USING btree (locale_code);
	CREATE INDEX idx_i18n_labels_i18n_modules ON master.i18n_labels USING btree (module_id);
	
	ALTER TABLE master.i18n_labels ADD CONSTRAINT fk_i18n_labels_i18n_locales FOREIGN KEY (locale_code) REFERENCES master.i18n_locales(locale_code);
	ALTER TABLE master.i18n_labels ADD CONSTRAINT fk_i18n_labels_i18n_modules FOREIGN KEY (module_id) REFERENCES master.i18n_modules(module_id);
	
	ALTER TABLE master.i18n_labels SET TABLESPACE tbl_master;

---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------
