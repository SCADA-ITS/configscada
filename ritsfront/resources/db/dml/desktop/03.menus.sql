INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible) VALUES
(1, 'LBL_MENU_1', 'img/menu/menu_default.png', 'Menu 1', 'Menu 1', true, true),
(2, 'LBL_MENU_2', 'img/menu/menu_default.png', 'Menu 2', 'Menu 2', true, true),
(3, 'LBL_MENU_3', 'img/menu/menu_default.png', 'Menu 3', 'Menu 3', true, true);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
(5, 1, 1, 5, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipments_icon.png', 'Equipment Manager', 'Equipment Manager', true, true),
(6, 1, 2, 6, 'LBL_PLANS', 'img/menu/plan_icon.png', 'Plan manager', 'Plan manager', true, true),
(7, 1, 3, 7, 'LBL_MODULE_3', 'img/module/module_default.png', 'Module 3', 'Module 3', true, true),
(8, 2, 1, 8, 'LBL_MODULE_4', 'img/module/module_default.png', 'Module 4', 'Module 4', true, true),
(9, 2, 2, 9, 'LBL_MODULE_5', 'img/module/module_default.png', 'Module 5', 'Module 5', true, true),
(10, 3, 1, 10, 'LBL_MODULE_6', 'img/module/module_default.png', 'Module 6', 'Module 6', true, true);
