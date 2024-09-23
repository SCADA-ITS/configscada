INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties, no_show_measures) VALUES
('ElementSubtype:18:71', 'PANEL', 'PANEL', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, null, null, null, 100071, 1, false, true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:18:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(711901, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:1' , 1, true),
(711902, 'ElementSubtype:18:71', 'ElementTypeParam:71:1:2' , 1, true);