UPDATE ui.menu_items SET enabled = false WHERE module_id = 1037;

INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Menu Equipment', 'Menu Equipment', true, true, 2),
(3, 'LBL_MENU_SUBSYTEM', 'img/menu/default.png', 'Menu Subsystem', 'Menu Subsystem', true, true, 3),
(4, 'LBL_MENU_MANAGER', 'img/menu/default.png', 'Menu Manager', 'Menu Manager', true, true, 4),
(5, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Menu Tools', 'Menu Tools', true, true, 5);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(100, 1, 1, 1018, 'LBL_MENU_ITEM_SYNOPTIC', 'img/menu/synoptic-menu.png', 'Menu item Synoptic Tunnel', 'Menu item Synoptic Tunnel', true, true),
(102, 1, 1, 2018, 'LBL_MENU_ITEM_SYNOPTIC', 'img/menu/synoptic-menu.png', 'Menu item Synoptic Tunnel', 'Menu item Synoptic Tunnel', true, true),
(101, 1, 2, 970, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'Menu item Map', 'Menu item Map', true, true),
(103, 1, 3, 971, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'Menu item Map', 'Menu item Map', true, true),

--EQUIPMENT
(200, 2, 2, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(201, 2, 4, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
--SUBSYSTEM
(300, 3, 1, 1020, 'LBL_MENU_ITEM_CCTV', 'img/menu/camera-menu.png', 'Menu item CCTV', 'Menu item CCTV', true, true),
(301, 3, 2, 1028, 'LBL_MENU_ITEM_LIGHTING_MANAGER', 'img/menu/light-menu.png', 'Menu item Lighting Manager', 'Menu item Lighting Manager', true, true),
(302, 3, 3, 1030, 'LBL_MENU_ITEM_PAS_SIGNALLING', 'img/menu/pas-menu.png', 'Menu item PAS Signaling', 'Menu item PAS Signaling', true, true),
(303, 3, 4, 1027, 'LBL_MENU_ITEM_VENTILATION_MANAGER', 'img/menu/ventilation-menu.png', 'Menu item Ventilation Manager', 'Menu item Ventilation Manager', true, true),
--MANAGER
(400, 4, 1, 1003, 'LBL_MENU_ITEM_INCIDENT_REPORTS', 'img/menu/incident-menu.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(401, 4, 2, 1024, 'LBL_MENU_ITEM_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(402, 4, 3, null, '', null, '', null, true, true),
(403, 4, 4, 1040, 'LBL_MENU_ITEM_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true),
(404, 4, 5, 1039, 'LBL_SIGNALING_QUEUES', 'img/menu/queue-menu.png', 'Menu item queue signaling', 'Menu item queue signaling', true, true),
(405, 4, 6, 1041, 'LBL_MENU_ITEM_TRAVEL_TIMES', 'img/menu/travel_times.png', 'Menu item travel times', 'Menu item travel times', true, true),

--TOOLS
(500, 5, 1, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/report.png', 'Menu item Reports', 'Menu item Reports', true, true),
(501, 5, 2, 1045, 'LBL_HISTORICAL_CHARTS', 'img/menu/chart-menu.png', 'Historical charts', 'Historical Charts', true, true),
--(502, 5, 3, 407, 'LBL_MENU_ITEM_RECORDS', 'img/menu/default.png', 'Menu item Grabaciones', 'Menu item Grabaciones', true, true),
--(503, 5, 4, 408, 'LBL_MENU_ITEM_INCIDENCIAS_DAI', 'img/menu/default.png', 'Menu Incidencias DAI', 'Menu Incidencias DAI', true, true),
(504, 5, 5, 1920, 'LBL_GIP', 'img/menu/default.png', 'NOGALES_GIP', 'Nogales GIP', true, true),
(506, 5, 6, 1006, 'LBL_MENU_ITEM_AUDITS', 'img/menu/default.png', 'Auditorias', 'Auditorias', true, true),
(505, 5, 7, 6011, 'LBL_MENU_ITEM_BACKOFFICE_INCIDENT', 'img/menu/backofficeincident.png', 'NOGALES_GIP_INCIDENT', 'Nogales GIP', true, true),
(507, 5, 8, 1921, 'LBL_MENU_ITEM_BACKOFFICE_ALARM', 'img/menu/backofficealarm.png', 'ALARM_BACKOFFICE', 'Alarms backoffice', true, true),
(508, 5, 9, 1922, 'LBL_MENU_ITEM_BACKOFFICE_ELEMENT', 'img/menu/backofficeelement.png', 'ELEMENTS_GIP', 'Elements GIP', true, true),


(500001, 5000, 3, 1047, 'LBL_MENU_HELP_CONTENTS', 'img/menu/default.png', 'Help contents', 'Menu item help contents', true, true);

