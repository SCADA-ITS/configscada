INSERT INTO conf.element_trees(element_tree_id, user_id, role_id, "label", enabled, visible) VALUES
(01, NULL, NULL, 'CARRETERA', true, true);

INSERT INTO conf.element_tree_nodes(element_tree_node_id, element_tree_id, "label", element_type_id, element_id, "position", management_area_id, enabled, visible) VALUES
(0101, 01, 'SENTIDO CRECIENTE', NULL, NULL, 1, NULL, true, true),
	(010101, 01, 'PMV-1', 71, 1, 2, NULL, true, true),	
	(010102, 01, 'PMV-2', 71, 2, 3, NULL, true, true),	
(0102, 01, 'SENTIDO DECRECIENTE', NULL, NULL, 4, NULL, true, true),
	(010201, 01, 'PMV-3', 71, 3, 5, NULL, true, true),	
	(010202, 01, 'PMV-4', 71, 4, 6, NULL, true, true);
                               
	                           
INSERT INTO conf.element_tree_node_hierarchies (element_tree_node_hierarchy_id, element_tree_id, node_parent_id, node_child_id, enabled, visible) VALUES  
(01, 01, 0101, 010101, true, true), 
(02, 01, 0101, 010102, true, true), 

(03, 01, 0102, 010201, true, true), 
(04, 01, 0102, 010202, true, true);




