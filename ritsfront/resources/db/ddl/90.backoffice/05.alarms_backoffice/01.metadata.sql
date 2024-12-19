SET client_min_messages TO WARNING;

DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_alarms_backoffice') THEN
  
	DROP SCHEMA alarms_backoffice CASCADE;

	CREATE SCHEMA alarms_backoffice;

	-- Table: alarms_backoffice.sg_metadata_tables
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_tables (
		id serial NOT NULL,
		name varchar UNIQUE NOT NULL,
		label varchar NULL,
		label_singular varchar NULL,
		label_description varchar NULL,
		mdi_icon varchar NULL,
		support_images bool NOT NULL,
		support_attachments bool NOT NULL,
		sql_view varchar(4000) null,
		grid_id int NULL,
		metadata varchar null,
		CONSTRAINT pk_sg_metadata_tables PRIMARY KEY (id)
	);
	
	ALTER TABLE alarms_backoffice.sg_metadata_tables SET TABLESPACE tbl_alarms_backoffice;
	
	-- Table: alarms_backoffice.sg_metadata_table_images
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_table_images (
		id serial NOT NULL,
		sg_metadata_table_id int NOT NULL,
		id_value int NOT NULL,
		group_name varchar NOT NULL,
		file_name varchar NOT NULL,
		description varchar NULL,
		position int NOT NULL,
		CONSTRAINT pk_sg_metadata_table_images PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_sg_metadata_table_images_sg_metadata_table_id ON alarms_backoffice.sg_metadata_table_images USING btree (sg_metadata_table_id);

	ALTER TABLE alarms_backoffice.sg_metadata_table_images ADD CONSTRAINT fk_sg_metadata_table_images_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) references alarms_backoffice.sg_metadata_tables(id);
	
	ALTER TABLE alarms_backoffice.sg_metadata_table_images SET TABLESPACE tbl_alarms_backoffice;
	
	-- Table: alarms_backoffice.sg_metadata_table_attachments
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_table_attachments (
		id serial NOT NULL,
		sg_metadata_table_id int NOT NULL,
		id_value int NOT NULL,
		group_name varchar NOT NULL,
		file_name varchar NOT NULL,
		description varchar NULL,
		position int NOT NULL,
		CONSTRAINT pk_sg_metadata_table_attachments PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_sg_metadata_table_attachments_sg_metadata_table_id ON alarms_backoffice.sg_metadata_table_attachments USING btree (sg_metadata_table_id);

	ALTER TABLE alarms_backoffice.sg_metadata_table_attachments ADD CONSTRAINT fk_sg_metadata_table_attachments_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) references alarms_backoffice.sg_metadata_tables(id);
	
	ALTER TABLE alarms_backoffice.sg_metadata_table_attachments SET TABLESPACE tbl_alarms_backoffice;
	
	-- 
	-- Table: alarms_backoffice.sg_metadata_table_triggers
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_table_triggers (
		id serial NOT NULL,
		sg_metadata_table_id int NOT NULL,
		name varchar NULL,
		groovy varchar NOT NULL,
		CONSTRAINT pk_sg_metadata_table_triggers PRIMARY KEY (id)
	);

	CREATE INDEX idx_sg_metadata_table_triggers_sg_metadata_table_id ON alarms_backoffice.sg_metadata_table_triggers USING btree (sg_metadata_table_id);

	ALTER TABLE alarms_backoffice.sg_metadata_table_triggers ADD CONSTRAINT fk_sg_metadata_table_triggers_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) references alarms_backoffice.sg_metadata_tables(id);
	
	ALTER TABLE alarms_backoffice.sg_metadata_table_triggers SET TABLESPACE tbl_alarms_backoffice;

	-- 
	-- Table: alarms_backoffice.sg_metadata_table_commands
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_table_commands (
		id serial NOT NULL,
		sg_metadata_table_id int NOT NULL,
		name varchar NOT NULL,
		label varchar NULL,
		label_description varchar NULL,
		mdi_icon varchar NULL,
		require_confirmation bool NOT NULL,
		available_in_form bool NOT NULL,
		available_in_table bool NOT NULL,
		groovy varchar NOT NULL,
		custom_js varchar(200) NULL,
		CONSTRAINT pk_sg_metadata_table_commands PRIMARY KEY (id)
	);

	CREATE INDEX idx_sg_metadata_table_commands_sg_metadata_table_id ON alarms_backoffice.sg_metadata_table_commands USING btree (sg_metadata_table_id);

	ALTER TABLE alarms_backoffice.sg_metadata_table_commands ADD CONSTRAINT fk_sg_metadata_table_commands_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) references alarms_backoffice.sg_metadata_tables(id);

	ALTER TABLE alarms_backoffice.sg_metadata_table_commands ADD CONSTRAINT unique_sg_metadata_table_commands UNIQUE (sg_metadata_table_id, name);
	
	ALTER TABLE alarms_backoffice.sg_metadata_table_commands SET TABLESPACE tbl_alarms_backoffice;
	
	-- 
	-- Table: alarms_backoffice.sg_metadata_columns
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_columns (
		id serial NOT NULL,
		sg_metadata_table_id int NOT NULL,
		column_name varchar NOT NULL,
		label varchar NULL,
		label_description varchar NULL,
		needs_translation bool NOT NULL,
		metadata varchar null,
		ref_view_column_id int NULL,
		CONSTRAINT pk_sg_metadata_columns PRIMARY KEY (id)
	);

	CREATE INDEX idx_sg_metadata_columns_sg_metadata_table_id ON alarms_backoffice.sg_metadata_columns USING btree (sg_metadata_table_id);

	ALTER TABLE alarms_backoffice.sg_metadata_columns ADD CONSTRAINT fk_sg_metadata_columns_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) references alarms_backoffice.sg_metadata_tables(id);
	
	ALTER TABLE alarms_backoffice.sg_metadata_columns ADD CONSTRAINT fk_sg_metadata_columns_sg_metadata_column_id FOREIGN KEY (ref_view_column_id) references alarms_backoffice.sg_metadata_columns(id);
	
	ALTER TABLE alarms_backoffice.sg_metadata_columns ADD CONSTRAINT unique_sg_metadata_columns UNIQUE (sg_metadata_table_id, column_name);
	
	ALTER TABLE alarms_backoffice.sg_metadata_columns SET TABLESPACE tbl_alarms_backoffice;
	
	-- 
	-- Table: alarms_backoffice.sg_metadata_columnm_fillers
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_column_fillers (
		id serial NOT NULL,
		sg_metadata_column_id int NOT NULL,
		name varchar NULL,
		groovy varchar NOT NULL,
		CONSTRAINT pk_sg_metadata_column_fillers PRIMARY KEY (id)
	);

	CREATE INDEX idx_sg_metadata_column_fillers_sg_metadata_column_id ON alarms_backoffice.sg_metadata_column_fillers USING btree (sg_metadata_column_id);

	ALTER TABLE alarms_backoffice.sg_metadata_column_fillers ADD CONSTRAINT fk_sg_metadata_column_fillers_sg_metadata_column_id FOREIGN KEY (sg_metadata_column_id) references alarms_backoffice.sg_metadata_columns(id);
	
	ALTER TABLE alarms_backoffice.sg_metadata_column_fillers SET TABLESPACE tbl_alarms_backoffice;
	
	-- 
	-- Table: alarms_backoffice.sg_metadata_tasks
	-- Descripción: 
	-- Scope: 
	--
	CREATE TABLE alarms_backoffice.sg_metadata_tasks (
		id serial NOT NULL,
		name varchar NULL,
		cron_expression varchar NOT NULL,
		groovy varchar NOT NULL,
		params varchar NULL,
		CONSTRAINT pk_sg_metadata_tasks PRIMARY KEY (id)
	);
	
	ALTER TABLE alarms_backoffice.sg_metadata_tasks SET TABLESPACE tbl_alarms_backoffice;
	
	-- 
	-- Table: alarms_backoffice.sg_i18n_labels
	-- Descripción: Traducciones
	-- Scope:
	--
	CREATE TABLE alarms_backoffice.sg_i18n_labels (
		id serial NOT NULL,
		locale_code varchar NOT NULL,
		label varchar NOT NULL,
		translation varchar NOT NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_sg_i18n_labels PRIMARY KEY (id)
	);
	
	ALTER TABLE alarms_backoffice.sg_i18n_labels ADD CONSTRAINT unique_sg_i18n_labels UNIQUE (locale_code, label);

	ALTER TABLE alarms_backoffice.sg_i18n_labels SET TABLESPACE tbl_alarms_backoffice;
  END IF;
END $$;