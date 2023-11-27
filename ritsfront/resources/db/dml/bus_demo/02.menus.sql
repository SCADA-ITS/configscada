DELETE FROM ui.menu_items WHERE menu_group_id = 4000 and module_id NOT IN (1000, 1001);
DELETE FROM ui.menu_items WHERE menu_group_id = 5000 and module_id NOT IN (1036);

INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(3000, 'LBL_MENU_MANAGEMENT', 'img/menu/default.png', 'Management', 'Management', true, true, 3000);

INSERT INTO ui.menu_items(menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MENU MANAGEMENT
(3000, 1, 1920, 'LBL_MENU_ITEM_MASTER_DATA', 'img/menu/default.png', 'Menu master data', 'Menu master data', true, true),
(3000, 2, 1921, 'LBL_MENU_ITEM_PROCESSES', 'img/menu/default.png', 'Menu processes', 'Menu processes', true, true),
(3000, 3, 1922, 'LBL_MENU_ITEM_VALIDATION_AND_SALES', 'img/menu/default.png', 'Menu validation and sales', 'Menu validation and sales', true, true),
(3000, 4, 1923, 'LBL_MENU_ITEM_SHIFTS_AND_EXPEDITIONS', 'img/menu/default.png', 'Menu shifts and expeditions', 'Menu shifts and expeditions', true, true),
(3000, 5, 1924, 'LBL_MENU_ITEM_LIQUIDATION_OF_DRIVING_STAFF', 'img/menu/default.png', 'Menu liquidation of driving staff', 'Menu liquidation of driving staff', true, true),
(3000, 6, 1925, 'LBL_MENU_ITEM_REQUESTS', 'img/menu/default.png', 'Menu requests', 'Menu requests', true, true);
