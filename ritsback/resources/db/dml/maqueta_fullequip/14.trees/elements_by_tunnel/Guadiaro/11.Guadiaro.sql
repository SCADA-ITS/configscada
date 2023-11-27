INSERT INTO conf.element_tree_nodes(element_tree_node_id, element_tree_id, "label", element_type_id, element_id, "position", management_area_id, enabled, visible) VALUES
-- Ausol I
(0411, 04, 'Guadiaro', NULL, NULL, 1, null, true, true),
    (041171, 04, 'Panel', NULL, NULL, 40, null, true, true),
        (041171001, 04, '', 71, 86, 1, null, true, true),
        (041171002, 04, '', 71, 87, 2, null, true, true);

INSERT INTO conf.element_tree_node_hierarchies (element_tree_node_hierarchy_id, element_tree_id, node_parent_id, node_child_id, enabled, visible) VALUES  
--paneles
(041171, 04, 0411, 041171, true, true),
    (041171001, 04, 041171, 041171001, true, true),
    (041171002, 04, 041171, 041171002, true, true);