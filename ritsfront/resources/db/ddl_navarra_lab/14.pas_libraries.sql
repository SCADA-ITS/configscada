---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.pas_libraries
-- Description: Definition of pas libraries
-- Scope: ui

CREATE TABLE ui.pas_libraries (
	id int not null,
	name varchar NOT NULL,
	description varchar NULL,
	label varchar NOT NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_pas_libraries PRIMARY KEY (id)
);

ALTER TABLE ui.pas_libraries SET TABLESPACE tbs_controltrafico_ui;


-- 
-- Table: ui.pas_messages
-- Description: Definition of pas libraries
-- Scope: ui

CREATE TABLE ui.pas_messages (
	id int not null,
	alias varchar NOT NULL,
	description varchar NULL,
	message varchar NULL,
	audio varchar NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_pas_messages PRIMARY KEY (id)
);

ALTER TABLE ui.pas_messages SET TABLESPACE tbs_controltrafico_ui;

-- 
-- Table: ui.pas_library_nodes
-- Description: 
-- Scope: ui		
CREATE TABLE ui.pas_library_nodes (
		id int not NULL,
		pas_library_id int not NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		pas_message_id int NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_pas_library_nodes PRIMARY KEY (id)
	);
	
ALTER TABLE ui.pas_library_nodes SET TABLESPACE tbs_controltrafico_ui;

ALTER TABLE ui.pas_library_nodes ADD CONSTRAINT fk_pas_library_nodes_pas_library FOREIGN KEY (pas_library_id) REFERENCES ui.pas_libraries(id);
ALTER TABLE ui.pas_library_nodes ADD CONSTRAINT fk_pas_library_nodes_pas_messages FOREIGN KEY (pas_message_id) REFERENCES ui.pas_messages(id);

-- 
-- Table: ui.pas_library_hierarchies
-- Description: 
-- Scope: ui		
CREATE TABLE ui.pas_library_hierarchies (
		id serial PRIMARY key,
		pas_library_id int not NULL,
		node_parent_id int not NULL,
		node_child_id int not NULL,
		enabled bool NULL,
		visible bool NULL,
		UNIQUE (node_parent_id, node_child_id)
	);
	
ALTER TABLE ui.pas_library_hierarchies SET TABLESPACE tbs_controltrafico_ui;

ALTER TABLE ui.pas_library_hierarchies ADD CONSTRAINT fk_pas_library_hierarchies_pas_libraries FOREIGN KEY (pas_library_id) REFERENCES ui.pas_libraries(id);
ALTER TABLE ui.pas_library_hierarchies ADD CONSTRAINT fk_pas_library_hierarchies_node_parent FOREIGN KEY (node_parent_id) REFERENCES ui.pas_library_nodes(id);
ALTER TABLE ui.pas_library_hierarchies ADD CONSTRAINT fk_pas_library_hierarchies_node_child FOREIGN KEY (node_child_id) REFERENCES ui.pas_library_nodes(id);


-- 
-- Table: ui.pas_library_elements
-- Description: Match elements to libraries
-- Scope: ui

CREATE TABLE ui.pas_library_elements (
	element_id varchar NOT NULL,
	pas_library_id int not NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_pas_library_elements PRIMARY KEY (element_id)
);

ALTER TABLE ui.pas_library_elements SET TABLESPACE tbs_controltrafico_ui;

ALTER TABLE ui.pas_library_elements ADD CONSTRAINT fk_pas_library_elements_pas_libaries FOREIGN KEY (pas_library_id) REFERENCES ui.pas_libraries(id);
ALTER TABLE ui.pas_library_elements ADD CONSTRAINT fk_pas_library_elements_element_id FOREIGN KEY (element_id) REFERENCES ui.vo_entities(id);