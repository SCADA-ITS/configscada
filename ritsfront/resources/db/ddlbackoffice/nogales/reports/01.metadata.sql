CREATE TABLE reports.sg_metadata_tables (
    id INT IDENTITY(1,1) NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    label VARCHAR(255) NULL,
    label_singular VARCHAR(255) NULL,
    label_description VARCHAR(255) NULL,
    mdi_icon VARCHAR(255) NULL,
    support_images BIT NOT NULL,
    support_attachments BIT NOT NULL,
    sql_view VARCHAR(4000) NULL,
    grid_id INT NULL,
    metadata VARCHAR(MAX) NULL,
    CONSTRAINT pk_sg_metadata_tables PRIMARY KEY (id)
);

CREATE TABLE reports.sg_metadata_table_images (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	id_value int NOT NULL,
	group_name VARCHAR(255) NOT NULL,
	file_name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NULL,
	position int NOT NULL,
	CONSTRAINT pk_sg_metadata_table_images PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_images_sg_metadata_table_id ON reports.sg_metadata_table_images (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_images ADD CONSTRAINT fk_sg_metadata_table_images_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);
	

CREATE TABLE reports.sg_metadata_table_attachments (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	id_value int NOT NULL,
	group_name VARCHAR(255) NOT NULL,
	file_name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NULL,
	position int NOT NULL,
	CONSTRAINT pk_sg_metadata_table_attachments PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_attachments_sg_metadata_table_id ON reports.sg_metadata_table_attachments (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_attachments ADD CONSTRAINT fk_sg_metadata_table_attachments_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);


CREATE TABLE reports.sg_metadata_table_triggers (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	name VARCHAR(255) NULL,
	groovy VARCHAR(255) NOT NULL,
	CONSTRAINT pk_sg_metadata_table_triggers PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_triggers_sg_metadata_table_id ON reports.sg_metadata_table_triggers (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_triggers ADD CONSTRAINT fk_sg_metadata_table_triggers_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);


CREATE TABLE reports.sg_metadata_table_commands (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	name VARCHAR(255) NOT NULL,
	label VARCHAR(255) NULL,
	label_description varchar NULL,
	mdi_icon VARCHAR(255) NULL,
	require_confirmation BIT NOT NULL,
	available_in_form BIT NOT NULL,
	available_in_table BIT NOT NULL,
	groovy VARCHAR(255) NOT NULL,
	custom_js VARCHAR(255) NULL,
	CONSTRAINT pk_sg_metadata_table_commands PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_commands_sg_metadata_table_id ON reports.sg_metadata_table_commands (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_commands ADD CONSTRAINT fk_sg_metadata_table_commands_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);

ALTER TABLE reports.sg_metadata_table_commands ADD CONSTRAINT unique_sg_metadata_table_commands UNIQUE (sg_metadata_table_id, name);


CREATE TABLE reports.sg_metadata_columns (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	column_name VARCHAR(255) NOT NULL,
	label VARCHAR(255) NULL,
	label_description VARCHAR(255) NULL,
	needs_translation BIT NOT NULL,
	metadata VARCHAR(MAX) null,
	ref_view_column_id int NULL,
	CONSTRAINT pk_sg_metadata_columns PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_columns_sg_metadata_table_id ON reports.sg_metadata_columns (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_columns ADD CONSTRAINT fk_sg_metadata_columns_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);

ALTER TABLE reports.sg_metadata_columns ADD CONSTRAINT fk_sg_metadata_columns_sg_metadata_column_id FOREIGN KEY (ref_view_column_id) REFERENCES reports.sg_metadata_columns(id);

ALTER TABLE reports.sg_metadata_columns ADD CONSTRAINT unique_sg_metadata_columns UNIQUE (sg_metadata_table_id, column_name);



CREATE TABLE reports.sg_metadata_column_fillers (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_column_id int NOT NULL,
	name VARCHAR(255) NULL,
	groovy VARCHAR(255) NOT NULL,
	CONSTRAINT pk_sg_metadata_column_fillers PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_column_fillers_sg_metadata_column_id ON reports.sg_metadata_column_fillers (sg_metadata_column_id);

ALTER TABLE reports.sg_metadata_column_fillers ADD CONSTRAINT fk_sg_metadata_column_fillers_sg_metadata_column_id FOREIGN KEY (sg_metadata_column_id) REFERENCES reports.sg_metadata_columns(id);


CREATE TABLE reports.sg_metadata_tasks (
	id INT IDENTITY(1,1) NOT NULL,
	name VARCHAR(255) NULL,
	cron_expression varchar NOT NULL,
	groovy VARCHAR(255) NOT NULL,
	params VARCHAR(255) NULL,
	CONSTRAINT pk_sg_metadata_tasks PRIMARY KEY (id)
);


CREATE TABLE reports.sg_i18n_labels (
	id INT IDENTITY(1,1) NOT NULL,
	locale_code VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	translation VARCHAR(255) NOT NULL,
	created_at datetimeoffset  NOT NULL,
	updated_at datetimeoffset  NOT NULL,
	CONSTRAINT pk_sg_i18n_labels PRIMARY KEY (id)
);
	
ALTER TABLE reports.sg_i18n_labels ADD CONSTRAINT unique_sg_i18n_labels UNIQUE (locale_code, label);
