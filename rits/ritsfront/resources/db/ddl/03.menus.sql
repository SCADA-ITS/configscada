---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

--
-- Table: ui.menu_groups
-- Description: Menu groups
-- Scope: ui
--
	CREATE TABLE ui.menu_groups (
		id serial primary key,
		label varchar NOT NULL,
		icon varchar NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		position int NULL
	);
	
	ALTER TABLE ui.menu_groups SET TABLESPACE tbl_ui;

-- 
-- Table: ui.menu_items
-- Description: Menu items
-- Scope: ui
--
	CREATE TABLE ui.menu_items (
		id serial primary key,
		menu_group_id int NOT NULL,
		position int NOT NULL,
		module_id int NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL
	);
	
	CREATE INDEX idx_menu_items_menu_groups ON ui.menu_items USING btree (menu_group_id);
	CREATE INDEX idx_menu_items_modules ON ui.menu_items USING btree (module_id);

	ALTER TABLE ui.menu_items ADD CONSTRAINT fk_menu_items_menu_groups FOREIGN KEY (menu_group_id) REFERENCES ui.menu_groups(id);
	ALTER TABLE ui.menu_items ADD CONSTRAINT fk_menu_items_modules FOREIGN KEY (module_id) REFERENCES ui.modules(id);
		
	ALTER TABLE ui.menu_items SET TABLESPACE tbl_ui;
