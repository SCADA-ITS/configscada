INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1000, 'LBL_MENU_ITEM_PLAN_MANAGER', 1029, true, true);


INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, position, enabled, visible) VALUES
(1001, 1000, 'LBL_PLAN_MONITOR', 'menu/planMonitor.png', 1022, 3, true, true),
(1002, 1000, 'LBL_PLAN_SCHEDULER', 'menu/planScheduler.png', 1009, 2, true, true),
(1003, 1000, 'LBL_PLAN_LIBRARY', 'menu/planLibrary.png', 1029, 1, true, true);

