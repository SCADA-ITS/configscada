INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Equipment', 'Equipment', true, true, 2),
(3, 'LBL_MENU_MANAGEMENT', 'img/menu/default.png', 'Management', 'Management', true, true, 3),
(4, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Tools', 'Tools', true, true, 4);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(0101, 1, 1, 1018, 'LBL_TREE_SELECT_MODULE_AVO', 'img/menu/synoptic-menu.png', 'Tree select module', 'Tree select module', true, true),
(0102, 1, 2, 200, 'LBL_AVO_MAP', 'img/menu/map-menu.png', 'AVO_MAP', 'AVO_MAP', true, true),

-- EQUIPMENT
(0201, 2, 1, 500, 'LBL_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(0202, 2, 2, 1005, 'LBL_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item alarm monitor', 'Menu item alarm monitor', true, true),
(0203, 2, 3, 1026, 'LBL_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true),
(0204, 2, 4, 1030, 'LBL_PAS_SIGNALLING', 'img/menu/pas-menu.png', 'Menu item PAS Signaling', 'Menu item PAS Signaling', true, true),
(0205, 2, 5, 1027, 'LBL_VENTILATION_MANAGER', 'img/menu/ventilation-menu.png', 'Menu item Ventilation Manager', 'Menu item Ventilation Manager', true, true),
(0206, 2, 6, 1028, 'LBL_LIGHTING_MANAGER', 'img/menu/light-menu.png', 'Menu item Lighting Manager', 'Menu item Lighting Manager', true, true),


-- MANAGEMENT
(0301, 3, 1, 1003, 'LBL_INCIDENT_REPORTS', 'img/menu/incident-menu.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(0302, 3, 2, 1024, 'LBL_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),

-- TOOLS
(0401, 4, 1, 403, 'LBL_REPORTS', 'img/menu/default.png', 'Menu item Reports', 'Menu item Reports', true, true),
(0402, 4, 2, 401, 'LBL_VIDEOWALL', 'img/menu/default.png', 'Menu item Videowall', 'Menu item Videowall', true, true);
