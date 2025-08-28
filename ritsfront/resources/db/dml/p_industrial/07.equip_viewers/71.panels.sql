INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:71', 'PANEL', 'PANEL', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 300, 3, null,000071, 2, null);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(197101, 'ElementType:71', 'Values element type panel', 'Values element type panel', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

/*INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(197101, 'ElementTypeParam:71:2:1', 1, true);*/

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(711901, 'ElementType:71', 'ElementTypeParam:71:1:1' , 1, true),
(711902, 'ElementType:71', 'ElementTypeParam:71:1:2' , 1, true);