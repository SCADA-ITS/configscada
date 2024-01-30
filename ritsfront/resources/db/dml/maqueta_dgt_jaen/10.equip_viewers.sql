INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, no_show_measures, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_properties, show_historical_chart) VALUES
('ElementSubtype:4:9', 'JETFAN', 'JETFAN', 'LBL_ELEMENT_TYPE_JETFAN', NULL, true, false, true, null, true, null, null, null, 040009, null, false),
('ElementType:13', 'SEM', 'SEM', 'LBL_ELEMENT_TYPE_TRAFFIC_LIGHT', NULL, true, false, true, null, false, 300, 1, null, 000013, true, false),
('ElementSubtype:11:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 110071, null, false),
('ElementSubtype:12:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 120071, null, false),
('ElementSubtype:18:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 180071, null, false);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:4:9', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:13', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:11:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:12:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:18:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(040901, 'ElementSubtype:4:9', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1301, 'ElementType:13', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(040901, 'ElementTypeParam:9:2:1', 1, true),
(040901, 'ElementTypeParam:9:2:2', 1, true),
(040901, 'ElementTypeParam:9:2:28', 1, true),
(040901, 'ElementTypeParam:9:2:29', 1, true),
(1301, 'ElementTypeParam:13:2:1', 1, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(090401, 'ElementSubtype:4:9', 'ElementTypeParam:9:1:1' , 1, true),
(090402, 'ElementSubtype:4:9', 'ElementTypeParam:9:1:2' , 2, true),

(711101, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:1' , 1, true),
(711102, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:3' , 2, true),

(711201, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:1' , 1, true),
(711202, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:3' , 2, true),

(711801, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:1' , 1, true),
(711802, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:3' , 2, true);
