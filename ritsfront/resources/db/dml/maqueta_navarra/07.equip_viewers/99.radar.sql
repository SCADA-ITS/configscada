INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,  show_historical_chart, chart_interval_min, chart_interval_time_min) VALUES
('ElementType:99', 'RADAR', 'RADAR', 'LBL_ELEMENT_TYPE_RADAR', NULL, true, false, false, 99, 300, 3, null, 2, 1, null, false, null, null);

INSERT INTO ui.equip_viewer_tabs (id, equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
(010099, 'ElementType:99', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(9901, 'ElementType:99', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(9901, 'ElementTypeParam:99:1:1', 1, true),
(9901, 'ElementTypeParam:99:1:2', 1, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(01010099, 'ElementType:99', 'ElementTypeParam:99:1:1' , 1, true),
(02010099, 'ElementType:99', 'ElementTypeParam:99:1:2' , 1, true);





