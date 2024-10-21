INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,  show_historical_chart, chart_interval_min, chart_interval_time_min) VALUES
('ElementSubtype:4:2', 'CAMERA_SANCTIONING', 'CAMERA_SANCTIONING', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true, false, false, null, 300, 3, null, 2, 1, null, false, null, null);

INSERT INTO ui.equip_viewer_tabs (id, equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
(1, 'ElementSubtype:4:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
(2, 'ElementSubtype:4:2', 103101, 'Charts', 'Charts', 'LBL_CHARTS', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(010203, 'ElementSubtype:4:2', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(010203, 'ElementTypeParam:2:2:6', 1, true),
(010203, 'ElementTypeParam:2:2:7', 2, true),
(010203, 'ElementTypeParam:2:2:8', 3, true);




