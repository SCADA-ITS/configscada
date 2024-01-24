INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_properties, show_historical_chart) VALUES
('ElementSubtype:11:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 450, 3, null, 110071, null, false),
('ElementSubtype:12:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 450, 3, null, 120071, null, false),
('ElementSubtype:18:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 450, 3, null, 180071, null, false);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:11:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:12:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:18:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(7111, 'ElementSubtype:11:71', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(7112, 'ElementSubtype:12:71', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(7118, 'ElementSubtype:18:71', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(7111, 'ElementTypeParam:71:1:1', 1, true),
(7111, 'ElementTypeParam:71:1:3', 2, true),

(7112, 'ElementTypeParam:71:1:1', 1, true),
(7112, 'ElementTypeParam:71:1:3', 2, true),

(7118, 'ElementTypeParam:71:1:1', 1, true),
(7118, 'ElementTypeParam:71:1:3', 2, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(711101, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:1' , 1, true),
(711102, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:3' , 2, true),

(711201, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:1' , 1, true),
(711202, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:3' , 2, true),

(711801, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:1' , 1, true),
(711802, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:3' , 2, true);
