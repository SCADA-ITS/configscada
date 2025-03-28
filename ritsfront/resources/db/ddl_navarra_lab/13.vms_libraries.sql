---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.vms_libraries
-- Description: Definition of vms libraries
-- Scope: ui

CREATE TABLE ui.vms_libraries (
	id int not null,
	name varchar NOT NULL,
	description varchar NULL,
	label varchar NOT NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_vms_libraries PRIMARY KEY (id)
);

ALTER TABLE ui.vms_libraries SET TABLESPACE tbs_controltrafico_ui;


-- 
-- Table: ui.vms_library_nodes
-- Description: 
-- Scope: ui		
CREATE TABLE ui.vms_library_nodes (
		id int not NULL,
		vms_library_id int not NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		signaling varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_vms_library_nodes PRIMARY KEY (id)
	);
	
ALTER TABLE ui.vms_library_nodes SET TABLESPACE tbs_controltrafico_ui;

ALTER TABLE ui.vms_library_nodes ADD CONSTRAINT fk_vms_library_nodes_vms_libaries FOREIGN KEY (vms_library_id) REFERENCES ui.vms_libraries(id);

-- 
-- Table: ui.vms_library_hierarchies
-- Description: 
-- Scope: ui		
CREATE TABLE ui.vms_library_hierarchies (
		id serial PRIMARY key,
		vms_library_id int not NULL,
		node_parent_id int not NULL,
		node_child_id int not NULL,
		enabled bool NULL,
		visible bool NULL,
		UNIQUE (node_parent_id, node_child_id)
	);
	
ALTER TABLE ui.vms_library_hierarchies SET TABLESPACE tbs_controltrafico_ui;

ALTER TABLE ui.vms_library_hierarchies ADD CONSTRAINT fk_vms_library_hierarchies_vms_libaries FOREIGN KEY (vms_library_id) REFERENCES ui.vms_libraries(id);
ALTER TABLE ui.vms_library_hierarchies ADD CONSTRAINT fk_vms_library_hierarchies_node_parent FOREIGN KEY (node_parent_id) REFERENCES ui.vms_library_nodes(id);
ALTER TABLE ui.vms_library_hierarchies ADD CONSTRAINT fk_vms_library_hierarchies_node_child FOREIGN KEY (node_child_id) REFERENCES ui.vms_library_nodes(id);


-- 
-- Table: ui.vms_library_element_subtype
-- Description: Match subtypes to libraries
-- Scope: ui

CREATE TABLE ui.vms_library_element_subtypes (
	element_subtype_id varchar NOT NULL,
	vms_library_id int not NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_vms_library_element_subtypes PRIMARY KEY (element_subtype_id)
);

ALTER TABLE ui.vms_library_element_subtypes SET TABLESPACE tbs_controltrafico_ui;

ALTER TABLE ui.vms_library_element_subtypes ADD CONSTRAINT fk_vms_library_element_subtypes_vms_libaries FOREIGN KEY (vms_library_id) REFERENCES ui.vms_libraries(id);
ALTER TABLE ui.vms_library_element_subtypes ADD CONSTRAINT fk_vms_library_element_subtypes_element_subtypes FOREIGN KEY (element_subtype_id) REFERENCES ui.vo_entities(id);

