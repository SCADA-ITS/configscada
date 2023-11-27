INSERT INTO conf.element_tree_nodes(element_tree_node_id, element_tree_id, "label", element_type_id, element_id, "position", management_area_id, enabled, visible) VALUES
-- Ausol I
(0412, 04, 'Manilva', NULL, NULL, 1, null, true, true),
    (041271, 04, 'Panel', NULL, NULL, 40, null, true, true),
        (041271001, 04, '', 71, 88, 1, null, true, true),
        (041271002, 04, '', 71, 89, 2, null, true, true);

INSERT INTO conf.element_tree_node_hierarchies (element_tree_node_hierarchy_id, element_tree_id, node_parent_id, node_child_id, enabled, visible) VALUES  
--paneles
(041271, 04, 0412, 041271, true, true),
    (041271001, 04, 041271, 041271001, true, true),
    (041271002, 04, 041271, 041271002, true, true);