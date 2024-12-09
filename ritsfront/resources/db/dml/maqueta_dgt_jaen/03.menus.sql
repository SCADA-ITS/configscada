INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Equipment', 'Equipment', true, true, 2),
(3, 'LBL_MENU_SUBSYTEM', 'img/menu/default.png', 'Menu Subsystem', 'Menu Subsystem', true, true, 3),
(4, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Menu Tools', 'Menu Tools', true, true, 3),
(5, 'LBL_MENU_MANAGER', 'img/menu/default.png', 'Menu Manager', 'Menu Manager', true, true, 4);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(0101, 1, 1, 1018, 'LBL_MENU_ITEM_SYNOPTIC', 'img/menu/synoptic-menu.png', 'Tree select module', 'Tree select module', true, true),

-- EQUIPMENT
(0201, 2, 1, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(0202, 2, 2, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),

--SUBSYSTEM
(0301, 3, 1, 1027, 'LBL_MENU_ITEM_VENTILATION_MANAGER', 'img/menu/ventilation-menu.png', 'Menu item Ventilation Manager', 'Menu item Ventilation Manager', true, true),
(0302, 3, 2, 1028, 'LBL_MENU_ITEM_LIGHTING_MANAGER', 'img/menu/light-menu.png', 'Menu item Lighting Manager', 'Menu item Lighting Manager', true, true),

-- TOOLS
(0401, 4, 1, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/report.png', 'Menu item Reports', 'Menu item Reports', true, true),
(0402, 4, 2, 1045, 'LBL_HISTORICAL_CHARTS', 'img/menu/chart-menu.png', 'Historical charts', 'Historical Charts', true, true),

--MANAGER
(0501, 5, 1, 1003, 'LBL_MENU_ITEM_INCIDENT_REPORTS', 'img/menu/incident.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(0502, 5, 2, 1024, 'LBL_MENU_ITEM_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(0503, 5, 3, 1040, 'LBL_MENU_ITEM_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true);

update ui.menu_items
set enabled=false
where menu_group_id = 4000 and module_id = 1037;