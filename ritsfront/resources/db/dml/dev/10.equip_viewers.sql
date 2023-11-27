INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_measures, info_tab_mode, no_show_properties, show_chart, chart_interval_min, chart_interval_time_min) VALUES
('ElementType:1', 'Weather station', 'Weather station', 'LBL_WEATHER_STATION', NULL, true, false, false, null, 300, 3, null, NULL, null, NULL, NULL, NULL, NULL, NULL),
('ElementType:2', 'Camera', 'Camera', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true, false, false, null, 450, 3, null, 8, null, NULL, NULL, NULL, NULL, NULL),
--('ElementType:5', 'PA', 'PA', 'PA', NULL, false, false, true, null, 300, 3, null, null, null),
('ElementType:9', 'FAN', 'FAN', 'LBL_FAN', NULL, true, true, true, null, null, 2, true, 9, null, NULL, NULL, NULL, NULL, NULL),
('Element:9:1', 'RV-VENT-VF1-CA', 'RV-VENT-VF1-CA', 'LBL_FAN', NULL, true, true, true, null, null, 2, true, 9, null, NULL, NULL, true, 1, 10),
('ElementSubtype:2:9', 'AXIALFAN', 'AXIALFAN', 'LBL_AXIALFAN', NULL, true, true, true, null, null, 2, true, 10, null, NULL, NULL, NULL, NULL, NULL),
--('ElementType:16', 'PA_ZONE', 'PA_ZONE', 'PA_ZONE', NULL, false, false, true, null, 300, 3, null, null, null),
('ElementType:34', 'Electric Cabinet SCN', 'Electric Cabinet SCN', 'LBL_EC_SCN', NULL, true, true, true, 3, null, null, null, 12, null, NULL, NULL, NULL, NULL, NULL),
('ElementType:38', 'VMS_TXT', 'VMS_TXT', 'LBL_VMS_TXT', NULL, true, true, true, 3, null, null, null, null, null, NULL, NULL, NULL, NULL, NULL),
('ElementSubtype:22:71', 'VMS_DGT', 'VMS_DGT', 'LBL_VMS_DGT', NULL, true, true, true, 3, null, null, null, 14, null, NULL, NULL, NULL, NULL, NULL),
('Element:2:1', 'Camera 1', 'Camera 1', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true, false, false, 4, 450, 3, null, 8, null, NULL, NULL, NULL, NULL, NULL),
('ElementType:13', 'Traffic light', 'Traffic light', 'LBL_TRAFFIC_LIGHT', NULL, true, false, false, null, 300, 3, null, 20, null, NULL, NULL, NULL, NULL, NULL),
('ElementType:2001', 'Ventilation manager', 'Ventilation manager', 'LBL_VENTILATION_MANAGER', NULL, false, false, false, null, 300, 3, null, null, null, NULL, NULL, NULL, NULL, NULL),
('ElementType:2002', 'Lighting manager', 'Lighting manager', 'LBL_MENU_ITEM_LIGHTING_MANAGER', NULL, false, false, false, null, 300, 3, null, null, null, NULL, NULL, NULL, NULL, NULL);


INSERT INTO ui.equip_viewer_tabs (id, equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
(1, 'ElementType:1', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
(2, 'ElementType:9', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
(3, 'ElementType:34', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
(4, 'ElementType:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_CAMERA', NULL, 1, true),

(7, 'Element:2:1', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_CAMERA', NULL, 1, true),
(8, 'Element:2:1', 200001, 'Map', 'Map', 'LBL_MAP', NULL, 2, true),
(9, 'Element:2:1', 200002, 'Synoptic', 'Synoptic', 'LBL_SYNOPTIC', NULL, 3, true),

(10, 'ElementType:2001', 200002, 'Synoptic', 'Synoptic', 'LBL_SYNOPTIC', NULL, 1, true),

(11, 'ElementType:2002', 200002, 'Synoptic', 'Synoptic', 'LBL_SYNOPTIC', NULL, 1, true),

(12, 'Element:2:1', 103101, 'Charts', 'Charts', 'LBL_CHARTS', NULL, 4, true);



INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(1, 'ElementType:1', 'Values element type ws', 'Values element type ws', 'LBL_ELEMENT_VALUES', NULL, 1, true),
(2, 'ElementType:2', 'Values element type camera', 'Values element type camera', 'LBL_ELEMENT_VALUES', NULL, 1, true);


INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES

(1, 'ElementTypeParam:1:1:4', 1, true),
(1, 'ElementTypeParam:1:2:14', 2, true),
(1, 'ElementTypeParam:1:2:20', 3, true),
(1, 'ElementTypeParam:1:2:13', 4, true),
(1, 'ElementTypeParam:1:2:10', 5, true),
(1, 'ElementTypeParam:1:2:17', 6, true),

(2, 'ElementTypeParam:2:1:1', 1, true),
(2, 'ElementTypeParam:2:1:2', 2, true),
(2, 'ElementTypeParam:2:1:4', 3, true),
(2, 'ElementTypeParam:2:1:5', 4, true),
(2, 'ElementTypeParam:2:1:8', 5, true),
(2, 'ElementTypeParam:2:1:9', 6, true);

