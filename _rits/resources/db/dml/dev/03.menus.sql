INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true),
(2, 'LBL_MENU_TABLES', 'img/menu/default.png', 'Menu Tables', 'Menu Tables', true, true),
(3, 'LBL_MENU_PLANS', 'img/menu/default.png', 'Menu Plans', 'Menu Plans', true, true),
(4, 'LBL_MENU_OTHERS', 'img/menu/default.png', 'Menu Others', 'Menu Others', true, true),
(5, 'LBL_MENU_NEW', 'img/menu/default.png', 'Menu New', 'Menu New', true, true);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(100, 1, 1, 100, 'LBL_MAIN_MAP', 'img/menu/default.png', 'Menu item Main map', 'Menu item Main map', true, true),
(101, 1, 2, 101, 'LBL_GENERIC_SYNOPTIC', 'img/menu/default.png', 'Menu item Generic Synoptic', 'Menu item Generic Synoptic', true, true),
-- TABLES
(200, 2, 1, 200, 'LBL_TABLE_EQUIPMENTS', 'img/menu/default.png', 'Menu item Table Equipments', 'Menu item Table Equipments', true, true),
(201, 2, 2, 201, 'LBL_TABLE_USERS', 'img/menu/default.png', 'Menu item Table Users', 'Menu item Table Users', true, true),
(202, 2, 3, 202, 'LBL_TABLE_ROLES', 'img/menu/default.png', 'Menu item Table Roles', 'Menu item Table Roles', true, true),
(203, 2, 4, 203, 'LBL_TABLE_ALARM_CONFIG', 'img/menu/default.png', 'Menu item Table Alarm Config', 'Menu item Table Alarm Config', true, true),
-- PLANS
(300, 3, 1, 300, 'LBL_PLAN_MANAGER', 'img/menu/default.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(301, 3, 2, 301, 'LBL_PLAN_SCHEDULER', 'img/menu/default.png', 'Menu item Plan Scheduler', 'Menu item Plan Scheduler', true, true),
-- OTHERS
(400, 4, 1, 400, 'LBL_ALARM_MANAGER', 'img/menu/default.png', 'Menu item Alarm manager', 'Menu item Alarm Manager', true, true),
(401, 4, 2, 401, 'LBL_CCTV', 'img/menu/default.png', 'Menu item CCTV', 'Menu item CCTV', true, true),
(402, 4, 3, 402, 'LBL_CONTROL_PANEL', 'img/menu/default.png', 'Menu item Control Panel', 'Menu item  CCTV', true, true),
(403, 4, 4, 403, 'LBL_REPORTS', 'img/menu/default.png', 'Menu item Reports', 'Menu item Reports', true, true),
(404, 4, 5, 404, 'LBL_USER_MANAGER', 'img/menu/default.png', 'Menu item User Manager', 'Menu item User Manager', true, true),

(500, 5, 1, 500, 'LBL_EQUIPMENTS', 'img/menu/default.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(501, 5, 2, 1000, 'LBL_USERS', 'img/menu/user.png', 'Menu item Users', 'Menu item Users', true, true),
(502, 5, 3, 1001, 'LBL_ROLES', 'img/menu/role.png', 'Menu item Roles', 'Menu item Roles', true, true),
(503, 5, 4, 1002, 'LBL_ALARM_CONFIG', 'img/menu/alarmConfig.png', 'Menu item Alarm configs', 'Menu item Alarm configs', true, true);