INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_MODULES', 'img/menu/default.png', 'Menu Modules', 'Menu Modules', true, true, 2),
(3, 'LBL_MENU_OTHERS', 'img/menu/default.png', 'Menu Others', 'Menu Others', true, true, 3),
(4, 'LBL_MENU_TEST', 'img/menu/default.png', 'Menu Test', 'Menu Test', true, true, 4),
(5, 'LBL_MENU_BACKOFFICE', 'img/menu/default.png', 'Menu Backoffice', 'Menu Backoffice', true, true, 5),
(6, 'LBL_MENU_GIP', 'img/menu/default.png', 'Menu GIP', 'Menu GIP', true, true, 6);

INSERT INTO ui.menu_items(menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(1, 1, 920, 'LBL_MENU_ITEM_SYNOPTIC', 'img/menu/synoptic-menu.png', 'Menu item Synoptic Test', 'Menu item Synoptic Test', true, true),
(1, 2, 970, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'Menu item Mapa Test', 'Menu item Mapa Test', true, true),
(1, 3, 971, 'Mapa Navarra', 'img/menu/map-menu.png', 'Menu item Mapa Navarra', 'Menu item Mapa Navarra', true, true),
(1, 4, 972, 'Mapa Paracuellos', 'img/menu/map-menu.png', 'Menu item Mapa Paracuellos', 'Menu item Mapa Paracuellos', true, true),
(1, 5, null, '', null, '', null, true, true),
(1, 6, 1057, 'LBL_MENU_ITEM_MAP_EDITOR', 'img/menu/map-menu.png', 'Editor de mapas', 'Editor de mapas', true, true),

-- MODULES
(2, 1, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(2, 2, null, '', null, '', null, true, true),
(2, 9, 1003, 'LBL_INCIDENT_REPORTS', 'img/menu/incident-menu.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(2, 10, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
(2, 11, 1040, 'LBL_SIGNALING_MONITOR', 'img/menu/signaling-monitor-menu.png', 'Menu item signaling monitor', 'Menu item signaling monitor', true, true),
(2, 12, 1039, 'LBL_SIGNALING_QUEUES', 'img/menu/queue-menu.png', 'Menu item queue signaling', 'Menu item queue signaling', true, true),
(2, 13, 1041, 'LBL_TRAVEL_TIMES', 'img/menu/travel_times.png', 'Menu item travel times', 'Menu item travel times', true, true),
(2, 14, 1006, 'LBL_AUDIT_LOGS', 'img/menu/logs-menu.png', 'Menu item Audit Logs', 'Menu item Audit Logs', true, true),
(2, 15, null, '', null, '', null, true, true),
(2, 16, 1024, 'LBL_MENU_ITEM_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(2, 17, null, '', null, '', null, true, true),
(2, 18, 1026, 'LBL_MENU_ITEM_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true),
(2, 19, 1027, 'LBL_MENU_ITEM_VENTILATION_MANAGER', 'img/menu/ventilation-menu.png', 'Menu item Ventilation Manager', 'Menu item Ventilation Manager', true, true),
(2, 20, 1028, 'LBL_MENU_ITEM_LIGHTING_MANAGER', 'img/menu/light-menu.png', 'Menu item Lighting Manager', 'Menu item Lighting Manager', true, true),
(2, 21, 1030, 'LBL_MENU_ITEM_PAS_SIGNALLING', 'img/menu/pas-menu.png', 'Menu item PAS Signaling', 'Menu item PAS Signaling', true, true),
(2, 22, null, '', null, '', null, true, true),
(2, 23, 1020, 'LBL_CCTV', 'img/menu/camera-menu.png', 'Menu item CCTV', 'Menu item CCTV', true, true),
(2, 24, 1051, 'LBL_INCIDENT_MONITOR_MODULE', 'img/menu/incident-menu.png', 'Menu incident Monitor Module', 'Menu incident Monitor Module', true, true),
(2, 25, 1055, 'LBL_TRANSIT_LIST_MODULE', 'img/menu/transit-menu.png', 'Transit list Module', 'Transit list Module', true, true),


-- OTHERS
--(3, 1, 402, 'LBL_CONTROL_PANEL', 'img/menu/control-panel-menu.png', 'Menu item Control Panel', 'Menu item  CCTV', true, true),
--(3, 2, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/default.png', 'Menu item Reports', 'Menu item Reports', true, true),
(3, 1, 600, 'WAZE', 'img/menu/default.png', 'WAZE', 'WAZE', true, true),


-- TEST
(4, 1, 2001, 'LBL_TEST_1', 'img/menu/default.png', 'Menu item test 1', 'Menu item test 1', true, true),
(4, 2, 1018, 'LBL_TREE_SELECT_MODULE', 'img/menu/default.png', 'Tree select module', 'Tree select module', true, true),
(4, 3, 2002, 'LBL_TEST_MAP', 'img/menu/default.png', 'Test map', 'Test map', true, true),
(4, 4, 1045, 'LBL_HISTORICAL_CHARTS', 'img/menu/chart-menu.png', 'Historical charts', 'Historical Charts', true, true),
(4, 5, 2003, 'LBL_INFRACTIONS_EDITOR', 'img/menu/default.png', 'Infraction editor', 'Infraction editor', true, true),
(4, 6, 9000, 'LBL_TRANSIT_LIST_MODULE', 'img/menu/transit-menu.png', 'Transit list Module', 'Transit list Module', true, true),
(4, 6, 9002, 'LBL_MENU_ITEM_TRANSIT_DISCARDED_REASONS', 'img/menu/transit-menu.png', 'Transit discarded reasons Module', 'Transit discarded reasons Module', true, true),

-- BACKOFFICE

(5, 1, 1900, 'LBL_ORDERS', 'img/menu/default.png', 'Orders', 'Orders', true, true),
(5, 2, 1901, 'LBL_CUSTOMERS', 'img/menu/default.png', 'Customers', 'Customers', true, true),
(5, 3, 1902, 'LBL_PRODUCTS', 'img/menu/default.png', 'Products', 'Products', true, true),
(5, 4, null, '', null, '', null, true, true),
(5, 5, 1903, 'LBL_ORDERS_1', 'img/menu/default.png', 'Order 1', 'Order 1', true, true),
(5, 6, 1904, 'LBL_SUBCATEGORIES', 'img/menu/default.png', 'Subcategories', 'Subcategories', true, true),
--(5, 7, null, '', null, '', null, true, true),
--(5, 8, 1905, 'LBL_INFRACTIONS', 'img/menu/default.png', 'Infractions', 'Infractions', true, true),
--(5, 9, 1906, 'LBL_EXT_ENTITIES', 'img/menu/default.png', 'External entities', 'External entities', true, true),
(5, 7, 1927, 'LBL_C4', 'img/menu/default.png', 'incidenciasc4', 'incidenciasc4', true, true),
(5, 8, 2020, 'LBL_MENU_ITEM_C4', 'img/menu/backofficeincident.png', 'incidenciasc4', 'incidenciasc4', true, true),
(5, 9, 1905, 'LBL_DRIVERS', 'img/menu/default.png', 'Drivers', 'Drivers', true, true),
(5, 10, 1906, 'LBL_EXT_ENTITIES', 'img/menu/default.png', 'ExtEntities', 'ExtEntities', true, true),


-- GIP
(6, 1, 1921, 'LBL_INCIDENTS', 'img/menu/default.png', 'Incident', 'Incident', true, true),
(6, 2, null, '', null, '', null, true, true),
(6, 3, 1920, 'LBL_LOCATIONS', 'img/menu/default.png', 'Locations', 'Locations', true, true),
(6, 4, 1922, 'LBL_RESOURCES', 'img/menu/default.png', 'Resources', 'Resources', true, true),
(6, 5, 1923, 'LBL_AGENTS', 'img/menu/default.png', 'Agents', 'Agents', true, true),
(6, 6, 1924, 'LBL_VEHICLES', 'img/menu/default.png', 'vehicles', 'vehicles', true, true),
(6, 7, 1925, 'LBL_LANES', 'img/menu/default.png', 'Lanes', 'Lanes', true, true),
(6, 8, 1926, 'LBL_PARTICIPANTS', 'img/menu/default.png', 'Participants', 'Participants', true, true),

-- CONFIG
(4000, 7, 1048, 'LBL_MENU_ITEM_EQUIPMENT_EDITOR', 'img/menu/equipment-menu.png', 'Menu item equipment Editor', 'Menu item equipment Editor', true, true),
(4000, 7, 1050, 'LBL_MENU_ITEM_LOCATION_EDITOR', 'img/menu/locations-menu.png', 'Menu item location editor', 'Menu item location editor', true, true),
(4000, 7, 1053, 'LBL_MENU_ITEM_ROUTE_EDITOR', 'img/menu/route-menu.png', 'Menu item route editor', 'Menu item route editor', true, true),

-- HELP
(5000, 3, 1047, 'LBL_MENU_HELP_CONTENTS', 'img/menu/default.png', 'Help contents', 'Menu item help contents', true, true);