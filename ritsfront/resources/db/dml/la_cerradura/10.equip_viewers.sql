INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, no_show_measures, measure_width, measure_max_rows, measure_only, graphic_icon_id, no_show_properties, show_historical_chart) VALUES
('ElementSubtype:1:2', 'CAMERA DOMO', 'CAMERA DOMO', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 2, true, null, null, null, 010002, null, false),
('ElementSubtype:2:2', 'CAMERA DAI', 'CAMERA DAI', 'LBL_ELEMENT_TYPE_CAMERA', NULL, false, false, false, 4, false, null, null, null, 020002, null, false),
('ElementSubtype:4:11', 'SOS', 'SOS', 'LBL_ELEMENT_TYPE_SOS', NULL, true, false, false, null, false, 300, 1, null, 000011, false, false),
('ElementType:13', 'SEM', 'SEM', 'LBL_ELEMENT_TYPE_TRAFFIC_LIGHT', NULL, true, false, true, null, false, 300, 1, null, 000013, true, false),
('ElementType:18','ANEM_INT','ANEM_INT','LBL_ELEMENT_TYPE_ANEM_INT',null,true,False,true,null,false, 300,3,null,18, false,false),
('ElementType:19','CO','CO','LBL_ELEMENT_TYPE_CO',null, true, False, true, null, false, 300, 3, null,19,false, false),
('ElementType:20','NO','NO','LBL_ELEMENT_TYPE_NO',null, true, False, false, null, false, 300, 3, null,20,false, false),
('ElementType:21','OPAC','OPAC','LBL_ELEMENT_TYPE_OPAC',null, true, False, true, null, false, 300, 3, null,21,false, false),
('ElementSubtype:2:53', 'SEM_2_GRUPOS', 'SEM_2_GRUPOS', 'LBL_ELEMENT_TYPE_SEM_2_LUCES', NULL, true, false, false, null, false, null, null, null, 020053, true, false),
('ElementSubtype:12:71', 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, true, null, null, null, 120071, null, false);



INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:1:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementSubtype:1:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementSubtype:2:2', 102001, 'SingleCam', 'CCTV Module - Action SingleCam', 'LBL_ELEMENT_TYPE_PARAM_VIDEO', NULL, 1, true),
('ElementSubtype:2:2', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true),
('ElementSubtype:4:11', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:13', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementType:18',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:19',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:20',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementType:21',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, true ),
('ElementSubtype:2:53', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true),
('ElementSubtype:12:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 1, true);




INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(1101, 'ElementSubtype:4:11', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1301, 'ElementType:13', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true),
(1801, 'ElementType:18', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(1901, 'ElementType:19', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(2001, 'ElementType:20', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(2101, 'ElementType:21', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, true),
(025301, 'ElementSubtype:2:53', 'Medidas', 'Medidas', 'LBL_ELEMENT_MEASURES', NULL, 1, true);




INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, "position", visible) VALUES
(1101, 'ElementTypeParam:11:2:4', 1, true),
(1101, 'ElementTypeParam:11:2:15', 2, true),
(1101, 'ElementTypeParam:11:2:16', 3, true),

(1301, 'ElementTypeParam:13:2:1', 1, true),

(1801,'ElementTypeParam:18:2:1', 1, true),

(1901,'ElementTypeParam:19:2:1', 1, true),

(2001,'ElementTypeParam:20:2:1', 1, true),

(2101,'ElementTypeParam:21:2:1', 1, true);





INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(020101, 'ElementSubtype:1:2', 'ElementTypeParam:2:1:1' , 1, true),
(020102, 'ElementSubtype:1:2', 'ElementTypeParam:2:1:2' , 2, true),
(020201, 'ElementSubtype:2:2', 'ElementTypeParam:2:1:1' , 1, true),
(020202, 'ElementSubtype:2:2', 'ElementTypeParam:2:1:2' , 2, true),



(1101, 'ElementSubtype:4:11', 'ElementTypeParam:11:1:1', 1, true),
(1102, 'ElementSubtype:4:11', 'ElementTypeParam:11:1:2', 2, true),

(1801,'ElementType:18','ElementTypeParam:18:1:1', 1, true),
(1802,'ElementType:18','ElementTypeParam:18:1:2', 2, true),

(1901,'ElementType:19','ElementTypeParam:19:1:1', 1, true),
(1902,'ElementType:19','ElementTypeParam:19:1:2', 2, true),

(2001,'ElementType:20','ElementTypeParam:20:1:1', 1, true),

(2101,'ElementType:21','ElementTypeParam:21:1:1', 1, true),
(2102,'ElementType:21','ElementTypeParam:21:1:2', 2, true),

(711201, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:1' , 1, true),
(711202, 'ElementSubtype:12:71', 'ElementTypeParam:71:1:3' , 2, true);

