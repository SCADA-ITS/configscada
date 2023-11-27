INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Equipment', 'Equipment', true, true, 2);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(0101, 1, 1, 901, 'LBL_TREE_SELECT_MODULE_PTA', 'img/menu/synoptic-menu.png', 'Tree select module', 'Tree select module', true, true),

-- EQUIPMENT
(0201, 2, 1, 500, 'LBL_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(0202, 2, 2, 1005, 'LBL_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
(0203, 2, 3, 1026, 'LBL_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true);

