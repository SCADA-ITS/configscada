INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(11, 'LBL_SELECTOR_TREE_HISTORICAL_DATA_BACKOFFICE', 11000, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(1100000, 11, 'LBL_HISTORICAL_DATA', null, null, true, true),
(1100001, 11, 'LBL_PMV_COMMANDS', null, 11001, true, true),
(1100002, 11, 'LBL_SEM_COMMANDS', null, 11002, true, true),
(1100003, 11, 'LBL_PRES_COMMANDS', null, 11003, true, true),
(1100004, 11, 'LBL_LIGHTNING_CIRCUIT_COMMANDS', null, 11004, true, true),
(1100005, 11, 'LBL_BARRIER_COMMANDS', null, 11005, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(11, 1100000, 1100001, true, true),
(11, 1100000, 1100002, true, true),
(11, 1100000, 1100003, true, true),
(11, 1100000, 1100004, true, true),
(11, 1100000, 1100005, true, true);
