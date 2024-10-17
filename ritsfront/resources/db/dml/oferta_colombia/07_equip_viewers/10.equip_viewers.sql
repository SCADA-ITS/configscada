INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_properties, show_historical_chart) VALUES
('ElementType:1', 'EM', 'EM', 'LBL_ELEMENT_TYPE_WEATHER_STATION', NULL, true, false, false, null, 225, 3, null, 00010001, null, true),
('ElementType:2', 'Camara', 'Camara', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 3, 450, 3, null, 00010002, null, false),
('ElementType:11', 'SOS', 'SOS', 'LBL_ELEMENT_TYPE_SOS', NULL, true, false, false, null, 450, 3, null, 00010011, true, false),
('ElementType:17', 'GÁLIBO', 'GÁLIBO', 'LBL_ELEMENT_TYPE_GALIBO', NULL, true, false, false, null, 450, 3, null, 00010017, true, false),
('ElementSubtype:11:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 450, 3, null, 11010071, null, false),
('ElementSubtype:22:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 450, 3, null, 22010071, null, false);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:1', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementType:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementType:11', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:17', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:11:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:22:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);


INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(0101, 'ElementType:1', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1101, 'ElementType:11', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1701, 'ElementType:17', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(7102, 'ElementSubtype:11:71', 'Values element type pmv', 'Values element type pmv', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(7106, 'ElementSubtype:22:71', 'Values element type pmv', 'Values element type pmv', 'LBL_ELEMENT_MEASURES', NULL, 1, true);



INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(0101, 'ElementTypeParam:1:2:1', 1, true),
(0101, 'ElementTypeParam:1:2:4', 1, true),
(0101, 'ElementTypeParam:1:2:10', 1, true),
(0101, 'ElementTypeParam:1:2:11', 1, true),
(0101, 'ElementTypeParam:1:2:15', 1, true),
(0101, 'ElementTypeParam:1:2:16', 1, true),
(0101, 'ElementTypeParam:1:2:19', 1, true),
(0101, 'ElementTypeParam:1:2:23', 1, true),

(7102, 'ElementTypeParam:71:2:10', 1, true),
(7106, 'ElementTypeParam:71:2:10', 1, true);


INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(10101, 'ElementType:1', 'ElementTypeParam:1:1:1' , 1, true),
(10102, 'ElementType:1', 'ElementTypeParam:1:1:2' , 1, true),

(20101, 'ElementType:2', 'ElementTypeParam:2:1:1' , 1, true),
(20102, 'ElementType:2', 'ElementTypeParam:2:1:2' , 1, true),

(711101, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:1' , 1, true),
(711102, 'ElementSubtype:11:71', 'ElementTypeParam:71:1:2' , 1, true),

(712201, 'ElementSubtype:22:71', 'ElementTypeParam:71:1:1' , 1, true),
(712202, 'ElementSubtype:22:71', 'ElementTypeParam:71:1:2' , 1, true);


