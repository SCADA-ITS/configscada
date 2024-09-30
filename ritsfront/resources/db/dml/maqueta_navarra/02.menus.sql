UPDATE ui.menu_items SET enabled = false WHERE module_id = 1037;

INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Equipment', 'Equipment', true, true, 2),
(3, 'LBL_MENU_MANAGER', 'img/menu/default.png', 'Management', 'Management', true, true, 3),
(4, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Tools', 'Tools', true, true, 4);


INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(0101, 1, 1, 200, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'RUTALAMINA_MAP', 'RUTALAMINA_MAP', true, true),
(0102, 1, 2, 201, 'LBL_MENU_ITEM_MAP_GEOSERVER', 'img/menu/map-menu.png', 'RUTALAMINA_MAP', 'RUTALAMINA_MAP', true, true),

-- EQUIPMENT
(0201, 2, 1, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(0202, 2, 2, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm_monitor.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
(0203, 2, 3, null, '', null, '', null, true, true),
(0204, 2, 4, 1040, 'LBL_MENU_ITEM_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true),
(0205, 2, 5, 1039, 'LBL_SIGNALING_QUEUES', 'img/menu/queue-menu.png', 'Menu item queue signaling', 'Menu item queue signaling', true, true),
(0206, 2, 6, 1041, 'LBL_MENU_ITEM_TRAVEL_TIMES', 'img/menu/travel_times.png', 'Menu item travel times', 'Menu item travel times', true, true),

-- MANAGEMENT
(0301, 3, 1, 1051, 'LBL_MENU_ITEM_INCIDENT_REPORTS', 'img/menu/incident.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(0302, 3, 2, 1024, 'LBL_MENU_ITEM_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(0303, 3, 3, 1020, 'LBL_MENU_ITEM_CCTV', 'img/menu/camera-menu.png', 'Menu item CCTV', 'Menu item CCTV', true, true),

-- TOOLS
(0401, 4, 1, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/report.png', 'Menu item Reports', 'Menu item Reports', true, true),
(0402, 4, 2, 1923, 'LBL_MENU_ITEM_BACKOFFICE_DGT', 'img/menu/task-menu.png', 'DGT_GIP', 'Dgt GIP', true, true),
(0403, 4, 3, 1045, 'LBL_HISTORICAL_CHARTS', 'img/menu/chart-menu.png', 'Historical charts', 'Historical Charts', true, true),
(0404, 4, 4, 1006, 'LBL_MENU_ITEM_AUDITS', 'img/menu/logs-menu.png', 'Auditorias', 'Auditorias', true, true),

-- CONFIG
(600, 4000, 7, 1048, 'LBL_MENU_ITEM_EQUIPMENT_EDITOR', 'img/menu/equipment-menu.png', 'Menu item equipment Editor', 'Menu item equipment Editor', true, true),
(601, 4000, 7, 1050, 'LBL_MENU_ITEM_LOCATION_EDITOR', 'img/menu/locations-menu.png', 'Menu item location editor', 'Menu item location editor', true, true),

(500001, 5000, 3, 1047, 'LBL_MENU_HELP_CONTENTS', 'img/menu/help.png', 'Help contents', 'Menu item help contents', true, true);





