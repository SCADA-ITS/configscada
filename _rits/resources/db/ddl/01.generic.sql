---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.custom_graphic_equipments
-- Description: Representation of customized graphics for equipments in synoptics, maps, etc
-- Scope: ui
--
	CREATE TABLE ui.custom_graphic_equipments (
		id serial primary key,
		name varchar NOT NULL,
		description varchar NULL
	);

	ALTER TABLE ui.custom_graphic_equipments SET TABLESPACE tbl_ui;
