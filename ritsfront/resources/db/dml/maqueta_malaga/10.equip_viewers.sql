INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementSubtype:22:71', 'PANEL', 'PANEL', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 300, 3, null, 220071,null, null),
('ElementSubtype:12:71', 'PANEL', 'PANEL', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 300, 3, null, 120071,null, null),
('Element:36:1', 'BALIZA AMARILLA', 'BALIZA AMARILLA', 'LBL_ELEMENT_TYPE_BALIZA', NULL, true, false, false, null, 300, 3, null, 000036,null, true),
('Element:36:2', 'BALIZA VERDE', 'BALIZA VERDE', 'LBL_ELEMENT_TYPE_BALIZA', NULL, true, false, false, null, 300, 3, null, 100036,null, true),
('ElementType:37', 'IPL', 'IPL', 'LBL_ELEMENT_TYPE_IPL', NULL, true, false, false, null, 300, 3, null, 000037,null, true),
('ElementType:38', 'IPL', 'IPL', 'LBL_ELEMENT_TYPE_BUS_VAO', NULL, true, false, true, null, 300, 3, null, 000038,null, true);


INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:22:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementSubtype:12:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('Element:36:1', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('Element:36:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementType:37', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementType:38', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(227101, 'ElementSubtype:22:71', 'Values element type panel', 'Values element type panel', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(127101, 'ElementSubtype:12:71', 'Values element type panel', 'Values element type panel', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(003601, 'Element:36:1', 'Values element type baliza', 'Values element type baliza', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(003602, 'Element:36:2', 'Values element type baliza', 'Values element type baliza', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(003701, 'ElementType:37', 'Values element type ipl', 'Values element type ipl', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(003801, 'ElementType:38', 'Values element type bus vao', 'Values element type bus vao', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(227101, 'ElementTypeParam:71:1:3', 1, true),
(127101, 'ElementTypeParam:71:1:3', 1, true),

(003601, 'ElementTypeParam:36:2:1', 1, true),
(003602, 'ElementTypeParam:36:2:1', 1, true),
(003701, 'ElementTypeParam:37:2:1', 1, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(10101, 'ElementSubtype:22:71', 'ElementTypeParam:71:1:3' , 1, true),
(10102, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:3' , 1, true);