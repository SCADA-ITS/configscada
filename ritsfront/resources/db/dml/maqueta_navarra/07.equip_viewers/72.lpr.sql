INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,  show_historical_chart, chart_interval_min, chart_interval_time_min) VALUES
('ElementType:72', 'LPR', 'LPR', 'LBL_ELEMENT_TYPE_LPR', NULL, false, false, false, 2, 300, 3, null, 2, 1, null, false, null, null);

INSERT INTO ui.equip_viewer_tabs (id, equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
(010072, 'ElementType:72', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
(020072, 'ElementType:72', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 2, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(01010072, 'ElementType:72', 'ElementTypeParam:72:1:1' , 1, true),
(02010072, 'ElementType:72', 'ElementTypeParam:72:1:2' , 1, true);




