UPDATE ui.menu_items SET enabled = false WHERE module_id = 1037;

INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Menu Equipment', 'Menu Equipment', true, true, 2);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(100, 1, 1, 1018, 'LBL_MENU_ITEM_SYNOPTIC', 'img/menu/synoptic-menu.png', 'Menu item Synoptic Tunnel', 'Menu item Synoptic Tunnel', true, true),
--(103, 1, 3, 971, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'Menu item Map', 'Menu item Map', true, true),

--EQUIPMENT
(200, 2, 2, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(201, 2, 4, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),

(500001, 5000, 3, 1047, 'LBL_MENU_HELP_CONTENTS', 'img/menu/help.png', 'Help contents', 'Menu item help contents', true, true);

