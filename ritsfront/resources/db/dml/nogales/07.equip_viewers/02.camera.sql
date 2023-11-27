INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementSubtype:3:2', 'CAMERA_DOMO', 'CAMERA_DOMO', 'LBL_ELEMENT_SUBTYPE_CAMERA', NULL, false, false, false, 2, 300, 3, null,2,2,null);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:3:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementSubtype:3:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(010201, 'ElementSubtype:3:2', 'Values element type camera_domo', 'Values element type camera_domo', 'LBL_ELEMENT_VALUES', NULL, 1, true);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(010201, 'ElementTypeParam:2:2:1', 1, true),
(010201, 'ElementTypeParam:2:2:2', 2, true);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(20103, 'ElementSubtype:3:2', 'ElementTypeParam:2:1:1' , 1, true),
(20203, 'ElementSubtype:3:2', 'ElementTypeParam:2:1:2' , 1, true);

