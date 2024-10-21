INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementSubtype:22:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, 1, 300, 3, null, 71, 2, null);

INSERT INTO ui.equip_viewer_tabs (id, equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
(4, 'ElementSubtype:22:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(197101, 'ElementSubtype:22:71', 'Values element type panel', 'Values element type panel', 'LBL_ELEMENT_MEASURES', NULL, 1, true);


INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(197101, 'ElementTypeParam:71:2:1', 1, true),
(197101, 'ElementTypeParam:71:2:10', 2, true);