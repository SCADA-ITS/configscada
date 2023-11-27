UPDATE ui.menu_items SET enabled = false WHERE module_id = 1037;

INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Menu Equipment', 'Menu Equipment', true, true, 2),
(3, 'LBL_MENU_MANAGER', 'img/menu/default.png', 'Menu Manager', 'Menu Manager', true, true, 3),
(4, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Menu Tools', 'Menu Tools', true, true, 4);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(100, 1, 1, 1018, 'LBL_MENU_ITEM_SYNOPTIC', 'img/menu/synoptic-menu.png', 'Menu item Synoptic Tunnel', 'Menu item Synoptic Tunnel', true, true),
(101, 1, 2, 970, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'Menu item Map', 'Menu item Map', true, true),
--EQUIPMENT
(200, 2, 2, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(201, 2, 4, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
(202, 2, 1, 1020, 'LBL_MENU_ITEM_CCTV', 'img/menu/camera-menu.png', 'Menu item CCTV', 'Menu item CCTV', true, true),
(203, 2, 3, 1026, 'LBL_MENU_ITEM_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true),
(204, 2, 5, 1028, 'LBL_MENU_ITEM_LIGHTING_MANAGER', 'img/menu/light-menu.png', 'Menu item Lighting Manager', 'Menu item Lighting Manager', true, true),
(205, 2, 6, 1030, 'LBL_MENU_ITEM_PAS_SIGNALLING', 'img/menu/pas-menu.png', 'Menu item PAS Signaling', 'Menu item PAS Signaling', true, true),
(206, 2, 7, 1027, 'LBL_MENU_ITEM_VENTILATION_MANAGER', 'img/menu/ventilation-menu.png', 'Menu item Ventilation Manager', 'Menu item Ventilation Manager', true, true),
--MANAGER
(300, 3, 1, 1003, 'LBL_MENU_ITEM_INCIDENT_REPORTS', 'img/menu/incident-menu.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(301, 3, 2, 1024, 'LBL_MENU_ITEM_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),

--TOOLS
(400, 4, 4, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/default.png', 'Menu item Reports', 'Menu item Reports', true, true),
(403, 4, 7, 406, 'LBL_MENU_ITEM_VIDEOWALL', 'img/menu/default.png', 'Menu item Videowall', 'Menu item Videowall', true, true),
(404, 4, 2, 407, 'LBL_MENU_ITEM_RECORDS', 'img/menu/default.png', 'Menu item Grabaciones', 'Menu item Grabaciones', true, true),
(405, 4, 3, 408, 'LBL_MENU_ITEM_INCIDENCIAS_DAI', 'img/menu/default.png', 'Menu Incidencias DAI', 'Menu Incidencias DAI', true, true);

