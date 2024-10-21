INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,  show_historical_chart, chart_interval_min, chart_interval_time_min) VALUES
('ElementType:64', 'AIR_QUALITY', 'AIR_QUALITY', 'LBL_ELEMENT_TYPE_AIR_QUALITY', NULL, true, false, false, null, 300, 3, null, 64, 1, null, false, null, null);

INSERT INTO ui.equip_viewer_tabs (id, equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
(5, 'ElementType:64', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(0164, 'ElementType:64', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(0164, 'ElementTypeParam:64:2:1', 1, true),
(0164, 'ElementTypeParam:64:2:2', 2, true),
(0164, 'ElementTypeParam:64:2:3', 3, true),
(0164, 'ElementTypeParam:64:2:4', 3, true);




