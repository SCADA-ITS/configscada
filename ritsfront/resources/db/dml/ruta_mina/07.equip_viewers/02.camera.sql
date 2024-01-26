INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementSubtype:1:2', 'CAMERA_FIX', 'CAMERA_FIX', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 2, 300, 3, null, 211,1,null),
('ElementSubtype:2:2', 'CAMERA_DOMO', 'CAMERA_DOMO', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 2, 300, 3, null, 211,1,null),
('ElementSubtype:3:2', 'CAMERA_DOMO', 'CAMERA_DOMO', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 2, 300, 3, null, 2,1,null),
('ElementSubtype:4:2', 'CAMERA_SANCTIONING', 'CAMERA_SANCTIONING', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true, false, false, 2, 300, 3, null, 22, 1, null);




INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:1:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementSubtype:3:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementSubtype:2:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementSubtype:3:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementSubtype:2:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementSubtype:4:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);



INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
-- (010201, 'ElementSubtype:3:2', 'Values element type camera_domo', 'Values element type camera_domo', 'LBL_ELEMENT_VALUES', NULL, 1, true),
(010203, 'ElementSubtype:4:2', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);


INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
-- (010201, 'ElementTypeParam:2:2:1', 1, true),
-- (010201, 'ElementTypeParam:2:2:2', 2, true),
(010203, 'ElementTypeParam:2:2:6', 1, true),
(010203, 'ElementTypeParam:2:2:7', 2, true),
(010203, 'ElementTypeParam:2:2:8', 3, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(20101, 'ElementSubtype:1:2', 'ElementTypeParam:2:1:1' , 1, true),
(20102, 'ElementSubtype:1:2', 'ElementTypeParam:2:1:2' , 1, true),
(20201, 'ElementSubtype:2:2', 'ElementTypeParam:2:1:1' , 1, true),
(20202, 'ElementSubtype:2:2', 'ElementTypeParam:2:1:2' , 1, true),
(20301, 'ElementSubtype:3:2', 'ElementTypeParam:2:1:1' , 1, true),
(20302, 'ElementSubtype:3:2', 'ElementTypeParam:2:1:2' , 1, true),
(20401, 'ElementSubtype:4:2', 'ElementTypeParam:2:1:1' , 1, true),
(20402, 'ElementSubtype:4:2', 'ElementTypeParam:2:1:2' , 1, true);




