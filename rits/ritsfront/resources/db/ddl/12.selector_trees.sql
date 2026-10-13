---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.selector_trees
-- Description: Definition of tree component
-- Scope: ui

CREATE TABLE ui.selector_trees (
	id int not null,
	default_module_id int not NULL,
	label varchar NOT NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_selector_trees PRIMARY KEY (id)
);

ALTER TABLE ui.selector_trees SET TABLESPACE tbl_ui;
ALTER TABLE ui.selector_trees ADD CONSTRAINT fk_selector_tree_module FOREIGN KEY (default_module_id) REFERENCES ui.modules(id);


-- 
-- Table: ui.selector_tree_nodes
-- Description: 
-- Scope: ui		
CREATE TABLE ui.selector_tree_nodes (
		id int not NULL,
		selector_tree_id int not NULL,
		label varchar NOT NULL,
		module_id int NULL,
		image varchar NULL,
		position int NULL,
		management_areas varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_selector_tree_nodes PRIMARY KEY (id)
	);
	
ALTER TABLE ui.selector_tree_nodes SET TABLESPACE tbl_ui;

ALTER TABLE ui.selector_tree_nodes ADD CONSTRAINT fk_selector_tree_node_selector_tree FOREIGN KEY (selector_tree_id) REFERENCES ui.selector_trees(id);
ALTER TABLE ui.selector_tree_nodes ADD CONSTRAINT fk_selector_tree_node_module FOREIGN KEY (module_id) REFERENCES ui.modules(id);

-- 
-- Table: ui.selector_tree_nodes_hierarchies
-- Description: 
-- Scope: ui		
CREATE TABLE ui.selector_tree_hierarchies (
		id serial PRIMARY key,
		selector_tree_id int not NULL,
		node_parent_id int not NULL,
		node_child_id int not NULL,
		enabled bool NULL,
		visible bool NULL,
		UNIQUE (node_parent_id, node_child_id)
	);
	
ALTER TABLE ui.selector_tree_hierarchies SET TABLESPACE tbl_ui;

ALTER TABLE ui.selector_tree_hierarchies ADD CONSTRAINT fk_selector_tree_hierarchies_selector_tree FOREIGN KEY (selector_tree_id) REFERENCES ui.selector_trees(id);
ALTER TABLE ui.selector_tree_hierarchies ADD CONSTRAINT fk_selector_tree_hierarchies_node_parent FOREIGN KEY (node_parent_id) REFERENCES ui.selector_tree_nodes(id);
ALTER TABLE ui.selector_tree_hierarchies ADD CONSTRAINT fk_selector_tree_hierarchies_node_child FOREIGN KEY (node_child_id) REFERENCES ui.selector_tree_nodes(id);