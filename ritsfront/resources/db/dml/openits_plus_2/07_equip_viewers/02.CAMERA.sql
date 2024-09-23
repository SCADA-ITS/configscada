INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:2','CAMERA','CAMERA','LBL_ELEMENT_TYPE_CAMERA',null,false,False,false,1,null,null,null,040002,2,false);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementType:2',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(000201,'ElementType:2','ElementTypeParam:2:1:1', 1, True),
(000202,'ElementType:2','ElementTypeParam:2:1:2', 2, True);