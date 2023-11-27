INSERT INTO conf.element_trees(element_tree_id, user_id, role_id, "label", enabled, visible) VALUES
(1, 1, NULL, 'LBL_ELEMENTS_MULTIPLE_LEVELS', true, true),
(2, 2, NULL, 'LBL_ELEMENTS_BY_ROAD_USER_OPER', true, true),
(3, NULL, 1, 'LBL_ELEMENTS_BY_ROAD_ROLE_ADMINISTRATOR', true, true),
(4, 1, NULL, 'LBL_A1', true, true),
(5, 1, NULL, 'LBL_A2', true, true);

INSERT INTO conf.element_tree_nodes(element_tree_node_id, element_tree_id, "label", element_type_id, element_id, "position", enabled, visible) VALUES
(10000, 1, 'LBL_ROAD_1_ADMIN', NULL, NULL, 1, true, true),
(10001, 1, '', 71, 1, 1, true, true),
(10002, 1, '', 71, 2, 2, true, true),
(10003, 1, 'LBL_ROAD_1_1_ADMIN', NULL, NULL, 3, true, true),
(10004, 1, '', 71, 6, 4, true, true),
(10005, 1, '', 71, 7, 5, true, true),
(10008, 1, 'LBL_ROAD_1_2_ADMIN', NULL, NULL, 6, true, true),
(10009, 1, '', 71, 8, 7, true, true),
(10010, 1, '', 71, 9, 8, true, true),
(10006, 1, 'LBL_ROAD_2_ADMIN', NULL, NULL, 9, true, true),
(10007, 1, '', 71, 10, 10, true, true),

(20000, 2, 'LBL_ROAD_1_OPER', NULL, NULL, 1, true, true),
(20001, 2, NULL, 9, 1, 1, true, true),
(20002, 2, NULL, 9, 2, 2, true, true),

(30000, 3, 'LBL_ROAD_2_ROLE_ADMINISTRATOR', NULL, NULL, 1, true, true),
(30001, 3, '', 2, 1, 1, true, true),
(30002, 3, NULL, 2, 2, 2, true, true),

(40000, 4, 'LBL_ROAD_1', NULL, NULL, 1, true, true),
(40001, 4, '', 71, 1, 1, true, true),
(40002, 4, '', 71, 2, 2, true, true),
(40003, 4, 'LBL_ROAD_2', NULL, NULL, 2, true, true),
(40004, 4, '', 71, 3, 1, true, true),
(40005, 4, '', 71, 4, 2, true, true),
--(40006, 4, '', 71, 5, 3, true, true),

(50000, 5, 'LBL_ROAD_1', NULL, NULL, 1, true, true),
(50001, 5, '', 71, 1, 1, true, true),
(50002, 5, '', 71, 2, 2, true, true),
(50003, 5, 'LBL_ROAD_2', NULL, NULL, 2, true, true),
(50004, 5, '', 71, 3, 1, true, true),
(50005, 5, '', 71, 4, 2, true, true),
(50006, 5, '', 71, 5, 3, true, true);


INSERT INTO conf.element_tree_node_hierarchies (element_tree_node_hierarchy_id, element_tree_id, node_parent_id, node_child_id, enabled, visible) VALUES
(1, 1, 10000, 10001, true, true),
(2, 1, 10000, 10002, true, true),
(3, 1, 10000, 10003, true, true),
(8, 1, 10000, 10008, true, true),
(4, 1, 10003, 10004, true, true),
(5, 1, 10003, 10005, true, true),
(6, 1, 10006, 10007, true, true),
(7, 1, 10008, 10009, true, true),
(24, 1, 10008, 10010, true, true),

(22, 2, 20000, 20001, true, true),
(23, 2, 20000, 20002, true, true),

(10, 3, 30000, 30001, true, true),
(11, 3, 30000, 30002, true, true),

(12, 4, 40000, 40001, true, true),
(13, 4, 40000, 40002, true, true),
(14, 4, 40003, 40004, true, true),
(15, 4, 40003, 40005, true, true),
--(16, 4, 40003, 40006, true, true),

(17, 5, 50000, 50001, true, true),
(18, 5, 50000, 50002, true, true),
(19, 5, 50003, 50004, true, true),
(20, 5, 50003, 50005, true, true);
--(21, 5, 50003, 50006, true, true);