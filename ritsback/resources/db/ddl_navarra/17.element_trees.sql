---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: conf.element_trees
-- Description: Definition of tree component
-- Scope: conf

CREATE TABLE conf.element_trees (
	element_tree_id int8 not null,
	user_id int8 null,
	role_id int8 null,
	label varchar NOT NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_element_trees PRIMARY KEY (element_tree_id)
);

ALTER TABLE conf.element_trees ADD CONSTRAINT fk_element_tree_user FOREIGN KEY (user_id) REFERENCES conf.users(user_id);
ALTER TABLE conf.element_trees ADD CONSTRAINT fk_element_tree_role FOREIGN KEY (role_id) REFERENCES conf.roles(role_id);

ALTER TABLE conf.element_trees SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.element_tree_nodes
-- Description: 
-- Scope: conf		
CREATE TABLE conf.element_tree_nodes (
		element_tree_node_id int8 not NULL,
		element_tree_id int8 not NULL,
		label varchar NULL,
		element_type_id int8 NULL,	
		element_id int8 NULL,
		position int8 NULL,
		management_area_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_element_tree_nodes PRIMARY KEY (element_tree_node_id)
	);
	
ALTER TABLE conf.element_tree_nodes ADD CONSTRAINT fk_element_tree_node_element_tree FOREIGN KEY (element_tree_id) REFERENCES conf.element_trees(element_tree_id);
ALTER TABLE conf.element_tree_nodes ADD CONSTRAINT fk_element_tree_node_element FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
ALTER TABLE conf.element_tree_nodes ADD CONSTRAINT fk_element_tree_nodes_management_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);

ALTER TABLE conf.element_tree_nodes SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.element_tree_node_hierarchies
-- Description: 
-- Scope: conf		
CREATE TABLE conf.element_tree_node_hierarchies (
		element_tree_node_hierarchy_id int8 not NULL,
		element_tree_id int8 not NULL,
		node_parent_id int8 not NULL,
		node_child_id int8 not NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_element_tree_node_hierarchies PRIMARY KEY (element_tree_node_hierarchy_id)
	);
	
ALTER TABLE conf.element_tree_node_hierarchies ADD CONSTRAINT fk_element_tree_node_hierarchies_element_tree FOREIGN KEY (element_tree_id) REFERENCES conf.element_trees(element_tree_id);
ALTER TABLE conf.element_tree_node_hierarchies ADD CONSTRAINT fk_element_tree_node_hierarchies_node_parent FOREIGN KEY (node_parent_id) REFERENCES conf.element_tree_nodes(element_tree_node_id);
ALTER TABLE conf.element_tree_node_hierarchies ADD CONSTRAINT fk_element_tree_node_hierarchies_node_child FOREIGN KEY (node_child_id) REFERENCES conf.element_tree_nodes(element_tree_node_id);

ALTER TABLE conf.element_tree_node_hierarchies SET TABLESPACE tbs_controltrafico_conf;