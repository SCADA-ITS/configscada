---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.backoffice
-- Description: Definition of pas libraries
-- Scope: ui

	CREATE TABLE ui.backoffices (
		id int not null,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NULL,
		jdbc varchar NULL,
		"user" varchar NULL,
		password varchar NULL,
		"schema" varchar NULL,
		default_metadata_backoffice varchar null,
		default_metadata_table varchar null,
		default_metadata_column varchar null,
		enabled bool NULL,
		CONSTRAINT pk_backoffices PRIMARY KEY (id)
	);
	
	ALTER TABLE ui.backoffices SET TABLESPACE tbs_controltrafico_ui;
