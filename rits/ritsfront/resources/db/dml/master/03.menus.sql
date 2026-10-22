INSERT INTO ui.menu_groups(id, "label", icon, "name", description, enabled, visible, position) VALUES
(4000, 'LBL_MENU_CONFIGURATION', 'img/menu/default.png', 'Configuration', 'Configuration', true, true, 4000),
(5000, 'LBL_MENU_HELP', 'img/menu/default.png', 'Menu Help', 'Menu Help', true, true, 5000);

INSERT INTO ui.menu_items(menu_group_id, "position", module_id, "label", icon, "name", description, enabled, visible) VALUES
-- MENU CONFIG
(4000, 1, 1000, 'LBL_USERS', 'img/menu/user-menu.png', 'Menu item Users', 'Menu item Users', true, true),
(4000, 2, 1001, 'LBL_ROLES', 'img/menu/rol-menu.png', 'Menu item Roles', 'Menu item Roles', true, true),
(4000, 3, null, '', null, '', null, true, true),
(4000, 4, 1037, 'LBL_CONFIG_THRESHOLDS', 'img/menu/config.png', 'Thresholds config', 'Thresholds config', true, true),
(4000, 5, 1034, 'LBL_INCIDENT_TYPES_EDITOR', 'img/menu/incident-menu.png', 'Menu item incindet type', 'Menu item incident type', true, true),
(4000, 6, 1002, 'LBL_ALARM_CONFIG', 'img/menu/alarm-config-menu.png', 'Menu item Alarm configs', 'Menu item Alarm configs', true, true),

-- MENU ABOUT 
(5000, 1, 1036, 'LBL_ABOUT', 'img/menu/default.png', 'Menu item about', 'Menu item about', true, true);
