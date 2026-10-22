UPDATE ui.menu_items SET enabled = false WHERE module_id = 1037;
UPDATE ui.menu_items SET enabled = false WHERE module_id = 1034;

INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true, 1),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Menu Equipment', 'Menu Equipment', true, true, 2),
(3, 'LBL_MENU_MANAGEMENT', 'img/menu/default.png', 'Management', 'Management', true, true, 3),
(4, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Menu Tools', 'Menu Tools', true, true, 4);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(0100, 1, 1, 1018, 'LBL_MENU_ITEM_SYNOPTIC', 'img/menu/synoptic-menu.png', 'Menu item Synoptic Tree', 'Menu item Synoptic Tree', true, true),
(0101, 1, 2, 401, 'LBL_MENU_ITEM_MAP', 'img/menu/map-menu.png', 'External map', 'External map', true, true),

-- EQUIPMENT
(0200, 2, 1, 500, 'LBL_MENU_ITEM_EQUIPMENTS', 'img/menu/equipment-menu.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(0201, 2, 2, 1005, 'LBL_MENU_ITEM_ALARM_MONITOR', 'img/menu/alarm-monitor-menu-2.png', 'Menu item Alarm Monitor', 'Menu item Alarm Monitor', true, true),
(0202, 2, 3, 1026, 'LBL_MENU_ITEM_VMS_SIGNALING', 'img/menu/signaling-menu.png', 'Menu item VMS Signaling', 'Menu item VMS Signaling', true, true),

-- PLANS
(0301, 3, 1, 1003, 'LBL_MENU_ITEM_INCIDENT_REPORTS', 'img/menu/incident-menu.png', 'Menu item incindet report', 'Menu item incident report', true, true),
(0302, 3, 1, 1024, 'LBL_MENU_ITEM_PLAN_MANAGER', 'img/menu/plan-menu.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),

--TOOLS
(0401, 4, 1, 403, 'LBL_MENU_ITEM_REPORTS', 'img/menu/default.png', 'Menu item Reports', 'Menu item Reports', true, true);



