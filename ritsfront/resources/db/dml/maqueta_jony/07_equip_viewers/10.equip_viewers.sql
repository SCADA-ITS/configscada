/*INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_properties, show_historical_chart) VALUES
('ElementType:1', 'EM', 'EM', 'LBL_ELEMENT_TYPE_WEATHER_STATION', NULL, true, false, false, null, 225, 3, null, 0301, null, true),
('ElementType:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 450, 3, null, 2271, null, false);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:1', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(0101, 'ElementType:1', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(7106, 'ElementType:71', 'Values element type pmv', 'Values element type pmv', 'LBL_ELEMENT_MEASURES', NULL, 1, true);


INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(0101, 'ElementTypeParam:1:2:1', 1, true),
(0101, 'ElementTypeParam:1:2:4', 1, true),
(0101, 'ElementTypeParam:1:2:10', 1, true),
(0101, 'ElementTypeParam:1:2:11', 1, true),
(0101, 'ElementTypeParam:1:2:15', 1, true),
(0101, 'ElementTypeParam:1:2:16', 1, true),
(0101, 'ElementTypeParam:1:2:19', 1, true),
(0101, 'ElementTypeParam:1:2:23', 1, true),

(7106, 'ElementTypeParam:71:2:10', 1, true);*/


