INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:4', 'SECTION', 'SECTION', 'LBL_ELEMENT_SUBTYPE_SECTION', NULL, true, true, false, null, 300, 3, null,000004,2,true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:4', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(0401, 'ElementType:4', 'General', 'General', 'LBL_ELEMENT_GENERAL', NULL, 1, true),
(0402, 'ElementType:4', 'Clas.Velocidad', 'Clas.Velocidad', 'LBL_ELEMENT_SPEED_CLASSIFICATION', NULL, 2, true),
(0403, 'ElementType:4', 'Clas.Tipo', 'Clas.Tipo', 'LBL_ELEMENT_TYPE_CLASSIFICATION', NULL, 3, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(0401, 'ElementTypeParam:4:2:1', 1, true),
(0401, 'ElementTypeParam:4:2:2', 2, true),
(0401, 'ElementTypeParam:4:2:4', 3, true),
(0401, 'ElementTypeParam:4:2:6', 4, true),
(0401, 'ElementTypeParam:4:2:7', 5, true),
(0401, 'ElementTypeParam:4:2:8', 6, true),
(0401, 'ElementTypeParam:4:2:9', 7, true),
(0401, 'ElementTypeParam:4:2:18', 8, true),

(0402, 'ElementTypeParam:4:2:80', 1, true),
(0402, 'ElementTypeParam:4:2:81', 2, true),
(0402, 'ElementTypeParam:4:2:82', 3, true),
(0402, 'ElementTypeParam:4:2:83', 3, true),

(0403, 'ElementTypeParam:4:2:140', 1, true),
(0403, 'ElementTypeParam:4:2:141', 2, true),
(0403, 'ElementTypeParam:4:2:142', 3, true),
(0403, 'ElementTypeParam:4:2:143', 4, true);
