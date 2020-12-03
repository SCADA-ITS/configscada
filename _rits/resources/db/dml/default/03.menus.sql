INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible) VALUES
(1, 'LBL_MENU_MAPS_AND_SYNOPTICS', 'img/menu/menu_default.png', 'Menu Maps & Synoptics', 'Menu Maps & Synoptics', true, true),
(2, 'LBL_MENU_TABLES', 'img/menu/menu_default.png', 'Menu Tables', 'Menu Tables', true, true),
(3, 'LBL_MENU_PLANS', 'img/menu/menu_default.png', 'Menu Plans', 'Menu Plans', true, true),
(4, 'LBL_MENU_OTHERS', 'img/menu/menu_default.png', 'Menu Others', 'Menu Others', true, true);

INSERT INTO ui.menu_items(id, menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MAPS & SYNOPTICS
(1, 1, 1, 1, 'LBL_MAIN_MAP', 'img/menu/equipments_icon.png', 'Menu item Main map', 'Menu item Main map', true, true),
(2, 1, 2, 3, 'LBL_SYNOPTIC_LINEAL', 'img/menu/equipments_icon.png', 'Menu item Synoptic Lineal', 'Menu item Synoptic Lineal', true, true),
(3, 1, 3, 4, 'LBL_SYNOPTIC_GLOBAL', 'img/menu/equipments_icon.png', 'Menu item Synoptic Global', 'Menu item Synoptic Global', true, true),
(4, 1, 4, 5, 'LBL_SYNOPTIC_CT_ITS', 'img/menu/equipments_icon.png', 'Menu item Synoptic CT_ITS', 'Menu item Synoptic CT_ITS', true, true),
(5, 1, 5, 1, 'LBL_SYNOPTIC_CSA', 'img/menu/equipments_icon.png', 'Menu item Synoptic Unifilar CSA', 'Menu item Synoptic Unifilar CSA', true, true),
-- TABLES
(6, 2, 1, 2, 'LBL_TABLE_EQUIPMENTS', 'img/menu/equipments_icon.png', 'Menu item Table Equipments', 'Menu item Table Equipments', true, true),
(7, 2, 2, 1, 'LBL_TABLE_USERS', 'img/menu/equipments_icon.png', 'Menu item Table Users', 'Menu item Table Users', true, true),
(8, 2, 3, 1, 'LBL_TABLE_ROLES', 'img/menu/equipments_icon.png', 'Menu item Table Roles', 'Menu item Table Roles', true, true),
(9, 2, 4, 1, 'LBL_TABLE_ALARM_CONFIG', 'img/menu/equipments_icon.png', 'Menu item Table Alarm Config', 'Menu item Table Alarm Config', true, true),
-- PLANS
(10, 3, 1, 6, 'LBL_PLAN_MANAGER', 'img/menu/equipments_icon.png', 'Menu item Plan Manager', 'Menu item Plan Manager', true, true),
(11, 3, 2, 7, 'LBL_PLAN_SCHEDULER', 'img/menu/equipments_icon.png', 'Menu item Plan Scheduler', 'Menu item Plan Scheduler', true, true),
-- OTHERS
(12, 4, 1, 1, 'LBL_ALARM_MANAGER', 'img/menu/equipments_icon.png', 'Menu item Alarm manager', 'Menu item Alarm Manager', true, true),
(13, 4, 2, 1, 'LBL_CCTV', 'img/menu/equipments_icon.png', 'Menu item CCTV', 'Menu item CCTV', true, true),
(14, 4, 3, 1, 'LBL_CONTROL_PANEL', 'img/menu/equipments_icon.png', 'Menu item Control Panel', 'Menu item  CCTV', true, true),
(15, 4, 4, 1, 'LBL_REPORTS', 'img/menu/equipments_icon.png', 'Menu item Reports', 'Menu item Reports', true, true),
(16, 4, 5, 1, 'LBL_USER_MANAGER', 'img/menu/equipments_icon.png', 'Menu item User Manager', 'Menu item User Manager', true, true),
(17, 4, 6, 8, 'LBL_EQUIPMENTS', 'img/menu/equipments_icon.png', 'Menu item Equipments', 'Menu item Equipments', true, true);