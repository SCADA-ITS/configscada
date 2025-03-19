INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_properties) VALUES
('ElementType:3', 'Detector', 'Detector', 'LBL_ELEMENT_TYPE_DETECTOR', NULL, true, false, false, null, 450, 3, null, 3, true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:3', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(0301, 'ElementType:3', 'General', 'General', 'LBL_ELEMENT_GENERAL', NULL, 1, true),
(0302, 'ElementType:3', 'Clasificación velocidad', 'Clasificación velocidad', 'LBL_ELEMENT_SPEED_CLASSIFICATION', NULL, 2, true),
(0303, 'ElementType:3', 'Clasificación longitud', 'Clasificación longitud', 'LBL_ELEMENT_LONG_CLASSIFICATION', NULL, 3, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(0301, 'ElementTypeParam:3:2:1', 1, true),
(0301, 'ElementTypeParam:3:2:2', 8, true),
(0301, 'ElementTypeParam:3:2:4', 3, true),
(0301, 'ElementTypeParam:3:2:6', 5, true),
(0301, 'ElementTypeParam:3:2:7', 6, true),
(0301, 'ElementTypeParam:3:2:8', 7, true),
(0301, 'ElementTypeParam:3:2:9', 2, true),
(0302, 'ElementTypeParam:3:2:40', 1, true),
(0302, 'ElementTypeParam:3:2:41', 2, true),
(0303, 'ElementTypeParam:3:2:20', 1, true),
(0303, 'ElementTypeParam:3:2:21', 2, true);

