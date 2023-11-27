INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Menu Equipments', 'Menu Equipments', true, true, 2),
(3, 'LBL_MENU_MANAGER', 'img/menu/default.png', 'Menu Manager', 'Menu Mnager', true, true, 3);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(101, 1, 1, 970, 'LBL_MENU_ITEM_MAP', 'img/menu/default.png', 'Menu item Map', 'Menu item Map', true, true),
-- EQUIPMENTS
(200, 2, 1, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/default.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(201, 2, 2, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm_monitor.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
(300, 2, 3, 1003, 'LBL_MENU_ITEM_INCIDENT_REPORTS', 'img/menu/incident.png', 'Menu item incident report', 'Menu item incident report', true, true),

--MANAGER
(301, 3, 1, 1029, 'LBL_PMENU_ITEM_LAN_MANAGER', 'img/menu/default.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(302, 3, 2, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/default.png', 'Menu item Reports', 'Menu item Reports', true, true),
(500, 3, 3, 1000, 'LBL_MENU_ITEM_USERS', 'img/menu/user.png', 'Menu item Users', 'Menu item Users', true, true),
(501, 3, 4, 1001, 'LBL_MENU_ITEM_ROLES', 'img/menu/role.png', 'Menu item Roles', 'Menu item Roles', true, true);

