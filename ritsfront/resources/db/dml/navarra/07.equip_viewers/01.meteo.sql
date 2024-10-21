INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,  show_historical_chart, chart_interval_min, chart_interval_time_min) VALUES
('ElementType:1', 'WEATHER_STATION', 'WEATHER_STATION', 'LBL_ELEMENT_TYPE_WEATHER_STATION', NULL, true, false, false, null, 250, 4, null, 1, 1, null, false, null, null);

INSERT INTO ui.equip_viewer_tabs (id, equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
(6, 'ElementType:1', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(0101, 'ElementType:1', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(0101, 'ElementTypeParam:1:2:1', 1, true),
(0101, 'ElementTypeParam:1:2:10', 2, true),
(0101, 'ElementTypeParam:1:2:11', 3, true),
(0101, 'ElementTypeParam:1:2:15', 4, true),
(0101, 'ElementTypeParam:1:2:16', 5, true),
(0101, 'ElementTypeParam:1:2:19', 6, true),
(0101, 'ElementTypeParam:1:2:20', 7, true);




