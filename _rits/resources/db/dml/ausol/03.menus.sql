INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true),
(2, 'LBL_MENU_EQUIPMENT', 'img/menu/default.png', 'Menu Equipment', 'Menu Equipment', true, true),
(3, 'LBL_MENU_MANAGER', 'img/menu/default.png', 'Menu Manager', 'Menu Manager', true, true),
(4, 'LBL_MENU_TOOLS', 'img/menu/default.png', 'Menu Tools', 'Menu Tools', true, true),
(5, 'LBL_MENU_CONFIGURATION', 'img/menu/default.png', 'Menu Configuration', 'Menu Configuration', true, true);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(100, 1, 1, 100, 'LBL_LINEAL', 'img/menu/default.png', 'Menu item Main map', 'Menu item Main map', true, true),
(101, 1, 2, 101, 'LBL_GLOBAL', 'img/menu/default.png', 'Menu item Generic Synoptic', 'Menu item Generic Synoptic', true, true),
(102, 1, 3, 102, 'LBL_MAPA', 'img/menu/default.png', 'Menu item Mapa', 'Menu item Mapa', true, true),
(103, 1, 4, 103, 'LBL_UNIFILAR_CSA', 'img/menu/default.png', 'Menu item Unifilar CSA', 'Menu item  Unifilar CSA', true, true),
(104, 1, 5, 104, 'LBL_UNIFILAR_CSB', 'img/menu/default.png', 'Menu item Unifilar CSB', 'Menu item Unifilar CSB', true, true),
(105, 1, 6, 105, 'LBL_UNIFILAR_CSV', 'img/menu/default.png', 'Menu item Unifilar CSV', 'Menu item Unifilar CSV', true, true),
(106, 1, 7, 106, 'LBL_UNIFILAR_CGBT', 'img/menu/default.png', 'Menu item Unifilar CGBT', 'Menu item Unifilar CGBT', true, true),

(500, 2, 1, 500, 'LBL_EQUIPMENTS', 'img/menu/default.png', 'Menu item Equipments', 'Menu item Equipments', true, true),
(400, 2, 2, 400, 'LBL_ALARM_MANAGER', 'img/menu/alarmConfig.png', 'Menu item Alarm manager', 'Menu item Alarm Manager', true, true),

(300, 3, 1, 300, 'LBL_PLAN_MANAGER', 'img/menu/default.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(301, 3, 2, 301, 'LBL_PLAN_SCHEDULER', 'img/menu/default.png', 'Menu item Plan Scheduler', 'Menu item Plan Scheduler', true, true),
(401, 3, 3, 401, 'LBL_CCTV', 'img/menu/default.png', 'Menu item CCTV', 'Menu item CCTV', true, true),
(501, 3, 4, 1000, 'LBL_USERS', 'img/menu/user.png', 'Menu item Users', 'Menu item Users', true, true),
(502, 3, 5, 1001, 'LBL_ROLES', 'img/menu/role.png', 'Menu item Roles', 'Menu item Roles', true, true),

(403, 4, 1, 403, 'LBL_REPORTS', 'img/menu/default.png', 'Menu item Reports', 'Menu item Reports', true, true),
(402, 4, 2, 402, 'LBL_IMD_VEH_H', 'img/menu/default.png', 'Menu item IMD', 'Menu item IMD', true, true),
(405, 4, 3, 405, 'LBL_VEL_MED_KM_H', 'img/menu/default.png', 'Menu item Vel Media', 'Menu item Vel Media', true, true),

(404, 4, 5, 404, 'LBL_USER_MANAGER', 'img/menu/default.png', 'Menu item User Manager', 'Menu item User Manager', true, true),

(503, 5, 1, 1002, 'LBL_ALARM_CONFIG', 'img/menu/alarmConfig.png', 'Menu item Alarm configs', 'Menu item Alarm configs', true, true);