INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementSubtype:2:11', 'SOS', 'SOS', 'LBL_ELEMENT_TYPE_SOS', NULL, true, false, true, null, 300, 3, null,11, 2, true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:2:11', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(021101, 'ElementSubtype:2:11', 'Values element type sos', 'Values element type sos', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(021101, 'ElementTypeParam:11:2:1', 1, true),
(021101, 'ElementTypeParam:11:2:4', 4, true),
(021101, 'ElementTypeParam:11:2:5', 5, true),
(021101, 'ElementTypeParam:11:2:6', 6, true),
(021101, 'ElementTypeParam:11:2:7', 7, true),
(021101, 'ElementTypeParam:11:2:8', 8, true),
(021101, 'ElementTypeParam:11:2:9', 9, true),
(021101, 'ElementTypeParam:11:2:10', 10, true),
(021101, 'ElementTypeParam:11:2:11', 11, true);
