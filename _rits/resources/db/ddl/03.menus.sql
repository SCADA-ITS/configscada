---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.menu_element_types
-- Description: Menu element types table
-- Scope: ui
--
	CREATE TABLE ui.menu_element_types (
		menu_element_type_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		CONSTRAINT pk_menu_element_types PRIMARY KEY (menu_element_type_id)
	);
		
	ALTER TABLE ui.menu_element_types SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.menus
-- Description: Menus
-- Scope: ui
--
	CREATE TABLE ui.menus (
		menu_id int8 NOT NULL,
		project_type_id int8 not null,
		project_id int8 not null,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_menus PRIMARY KEY (menu_id)
	);
	
	CREATE INDEX idx_menus_projects ON ui.menus USING btree (project_type_id, project_id);

	ALTER TABLE ui.menus ADD CONSTRAINT fk_menus_projects FOREIGN KEY (project_type_id, project_id) REFERENCES ui.projects(project_type_id, project_id);
		
	ALTER TABLE ui.menus SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.menu_hierarchies
-- Description: Menu hierarchy
-- Scope: ui
--
	CREATE TABLE ui.menu_hierarchies (
		menu_id int8 NOT NULL,
		menu_hierarchy_id int8 NOT NULL,
		parent_menu_hierarchy_id int8 NULL,
		menu_hierarchy_order int8 NOT NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_menu_hierarchies PRIMARY KEY (menu_id, menu_hierarchy_id)
	);
	
	CREATE INDEX idx_menu_hierarchies_menus ON ui.menu_hierarchies USING btree (menu_id);	
	CREATE INDEX idx_menu_hierarchies_parent_menu_hierarchies ON ui.menu_hierarchies USING btree (parent_menu_hierarchy_id);

	ALTER TABLE ui.menu_hierarchies ADD CONSTRAINT fk_menu_hierarchies_menus FOREIGN KEY (menu_id) REFERENCES ui.menus(menu_id);
	ALTER TABLE ui.menu_hierarchies ADD CONSTRAINT fk_menu_hierarchies_parent_menu_hierarchies FOREIGN KEY (menu_id, parent_menu_hierarchy_id) REFERENCES ui.menu_hierarchies(menu_id, menu_hierarchy_id);
		
	ALTER TABLE ui.menu_hierarchies SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.menu_elements
-- Description: Menu elements
-- Scope: ui
--
	CREATE TABLE ui.menu_elements (
		menu_id int8 NOT NULL,
		menu_hierarchy_id int8 NOT NULL,
		menu_element_type_id int8 NULL,
		menu_element_id int8 NOT NULL,
		menu_element_order int8 NOT NULL,
		module_type_id int8 NULL,
		module_id int8 NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_menu_elements PRIMARY KEY (menu_id, menu_hierarchy_id, menu_element_type_id, menu_element_id)
	);
	
	CREATE INDEX idx_menu_elements_menu_hierarchies ON ui.menu_elements USING btree (menu_id, menu_hierarchy_id);
	CREATE INDEX idx_menu_elements_menu_element_types ON ui.menu_elements USING btree (menu_element_type_id);
	CREATE INDEX idx_menu_elements_modules ON ui.menu_elements USING btree (module_type_id, module_id);

	ALTER TABLE ui.menu_elements ADD CONSTRAINT fk_menu_elements_menu_hierarchies FOREIGN KEY (menu_id, menu_hierarchy_id) REFERENCES ui.menu_hierarchies(menu_id, menu_hierarchy_id);
	ALTER TABLE ui.menu_elements ADD CONSTRAINT fk_menu_elements_menu_element_types FOREIGN KEY (menu_element_type_id) REFERENCES ui.menu_element_types(menu_element_type_id);
	ALTER TABLE ui.menu_elements ADD CONSTRAINT fk_menu_elements_modules FOREIGN KEY (module_type_id, module_id) REFERENCES ui.modules(module_type_id, module_id);
		
	ALTER TABLE ui.menu_elements SET TABLESPACE tbl_ui;
