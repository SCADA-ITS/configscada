UPDATE ui.menu_items SET enabled = false WHERE module_id = 1037;
UPDATE ui.menu_items SET enabled = false WHERE module_id = 1034;

INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Equipment', 'Equipment', true, true, 2),
(3, 'LBL_MENU_MANAGER', 'img/menu/default.png', 'Management', 'Management', true, true, 3),
(4, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Tools', 'Tools', true, true, 4);


INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(0101, 1, 1, 200, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'Mapa Navarra', 'Mapa Navarra', true, true),
(0102, 1, 2, 401, 'LBL_MENU_ITEM_MAP_GOOGLE_SIMPLE', 'img/menu/map-menu.png', 'Mapa Google simplificado', 'Mapa Google simplificado', true, true),
(0103, 1, 2, 402, 'LBL_MENU_ITEM_MAP_GOOGLE_COMPLEJO', 'img/menu/map-menu.png', 'Mapa Google simplificado', 'Mapa Google simplificado', true, true),

-- EQUIPMENT
(0201, 2, 1, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(0202, 2, 2, 1020, 'LBL_MENU_ITEM_CCTV', 'img/menu/camera-menu.png', 'Menu item CCTV', 'Menu item CCTV', true, true),
(0203, 2, 3, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm_monitor.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
(0204, 2, 4, 1055, 'LBL_TRANSIT_LIST_MODULE', 'img/menu/transit-menu.png', 'Transit list Module', 'Transit list Module', true, true),

-- MANAGEMENT
(0301, 3, 1, 1051, 'LBL_MENU_ITEM_INCIDENT_REPORTS', 'img/menu/incident.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(0302, 3, 2, 2020, 'LBL_MENU_ITEM_EXTERNAL_INCIDENTS', 'img/menu/backofficeincident.png', 'incidencias externas', 'incidencias externas', true, true),
(0303, 3, 3, 1024, 'LBL_MENU_ITEM_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),

-- TOOLS
(0401, 4, 1, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/report.png', 'Menu item Reports', 'Menu item Reports', true, true),

-- CONFIG
(600, 4000, 7, null, '', null, '', null, true, true),
(601, 4000, 8, 1050, 'LBL_MENU_ITEM_LOCATION_EDITOR', 'img/menu/locations-menu.png', 'Menu item location editor', 'Menu item location editor', true, true),
(602, 4000, 9, 1053, 'LBL_MENU_ITEM_ROUTE_EDITOR', 'img/menu/route-menu.png', 'Menu item route editor', 'Menu item route editor', true, true),
(603, 4000, 10, 1048, 'LBL_MENU_ITEM_EQUIPMENT_EDITOR', 'img/menu/equipment-menu.png', 'Menu item equipment Editor', 'Menu item equipment Editor', true, true),

-- HELP
(501, 5000, 1, 1047, 'LBL_MENU_HELP_CONTENTS', 'img/menu/help.png', 'Help contents', 'Menu item help contents', true, true);





