--DROP TABLE IF EXISTS conf.vms_graphic_protocol_values;
--DROP TABLE IF EXISTS conf.vms_graphic_element_type_params;

--DROP TABLE IF EXISTS master.vms_graphic_protocol_type_params;
--DROP TABLE IF EXISTS master.vms_graphic_protocol_types;
--DROP TABLE IF EXISTS master.vms_graphics;
--DROP TABLE IF EXISTS master.vms_graphic_types;

---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.vms_graphic_types
-- Descripción: Tipos de gráficos para paneles en el sistema
-- Scope: master
--
	CREATE TABLE master.vms_graphic_types (
		graphic_type_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vms_graphic_types PRIMARY KEY (graphic_type_id)
	);

	ALTER TABLE master.vms_graphic_types SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.vms_graphic_categories
-- Descripción: Grupos de clasificación de gráficos
-- Scope: master
--
	CREATE TABLE master.vms_graphic_categories (
	    graphic_category_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vms_graphic_categories PRIMARY KEY (graphic_category_id)
	);
	
	ALTER TABLE master.vms_graphic_categories SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.vms_graphics
-- Descripción: Gráficos dados de alta en el sistema
-- Scope: master
--
	CREATE TABLE master.vms_graphics (
	    graphic_type_id int8 NOT NULL,
		graphic_id int8 NOT NULL,
		graphic_category_id int8 NULL,
  		alias varchar NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vms_graphics PRIMARY KEY (graphic_type_id, graphic_id)
	);
	
	CREATE INDEX idx_vms_graphics_vms_graphic_types ON master.vms_graphics USING btree (graphic_type_id);
	CREATE INDEX idx_vms_graphics_vms_graphic_categories ON master.vms_graphics USING btree (graphic_category_id);
	
	ALTER TABLE master.vms_graphics ADD CONSTRAINT fk_vms_graphics_vms_graphic_types FOREIGN KEY (graphic_type_id) REFERENCES master.vms_graphic_types(graphic_type_id);
	ALTER TABLE master.vms_graphics ADD CONSTRAINT fk_vms_graphics_vms_graphic_categories FOREIGN KEY (graphic_category_id) REFERENCES master.vms_graphic_categories(graphic_category_id);

	ALTER TABLE master.vms_graphics SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: conf.vms_graphic_groups
-- Descripción: Valores para la relacion grupos de gráficos
-- Scope: conf
--
	CREATE TABLE conf.vms_graphic_groups (
		graphic_group_id int8 NOT NULL,
		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vms_graphic_groups PRIMARY KEY (graphic_group_id)
	    );

	ALTER TABLE conf.vms_graphic_groups SET TABLESPACE tbs_controltrafico_conf;


	-- 
	-- Table: conf.vms_graphic_groups
	-- Descripción: Valores para la relacion entre elementos gráficos y grupos.
	-- Scope: conf
	--

	CREATE TABLE conf.vms_graphic_groups_elements (
		graphic_group_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vms_graphic_groups_elements PRIMARY KEY (graphic_group_id, element_type_id, element_id)
	    );

	ALTER TABLE conf.vms_graphic_groups SET TABLESPACE tbs_controltrafico_conf;

	CREATE INDEX idx_vms_graphic_groups_elements_elements ON conf.vms_graphic_groups_elements USING btree (element_type_id, element_id);

	ALTER TABLE conf.vms_graphic_groups_elements ADD CONSTRAINT fk_vms_graphic_groups_graphic_groups FOREIGN KEY (graphic_group_id) REFERENCES conf.vms_graphic_groups(graphic_group_id);
	ALTER TABLE conf.vms_graphic_groups_elements ADD CONSTRAINT fk_vms_graphic_groups_elements_elements FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);

-- Table: conf.vms_graphic_graphic_group_values
-- Descripción: Valores de grupos y grupos de gráficos
-- Scope: conf
--

	CREATE TABLE conf.vms_graphic_graphic_group_values (
		graphic_type_id int8 NOT NULL,
		graphic_id int8 NOT NULL,	
		graphic_group_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_vms_graphic_graphic_groups PRIMARY KEY (graphic_type_id, graphic_id, graphic_group_id)
		
	);

	ALTER TABLE conf.vms_graphic_graphic_group_values SET TABLESPACE tbs_controltrafico_conf;

	CREATE INDEX idx_vms_grap_graph_group_values_groups ON conf.vms_graphic_graphic_group_values USING btree (graphic_group_id);
	CREATE INDEX idx_vms_grap_graph_group_values_vms_grap ON conf.vms_graphic_graphic_group_values USING btree (graphic_type_id, graphic_id);


	ALTER TABLE conf.vms_graphic_graphic_group_values ADD CONSTRAINT fk_vms_graphic_grap_groups FOREIGN KEY (graphic_group_id) REFERENCES conf.vms_graphic_groups(graphic_group_id);
	ALTER TABLE conf.vms_graphic_graphic_group_values ADD CONSTRAINT fk_vms_graphic_grap_groups_vms_graph FOREIGN KEY (graphic_type_id, graphic_id) REFERENCES master.vms_graphics(graphic_type_id, graphic_id);
	


