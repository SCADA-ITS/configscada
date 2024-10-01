INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,show_historical_chart) VALUES
('ElementSubtype:11:71','VMS_CLV','VMS_CLV','LBL_ELEMENT_SUBTYPE_PANEL_VMS_CLV', NULL, true, false, true, null, 300, 1, null, 110071, 1, false, true),
('ElementSubtype:12:71','VMS_AF','VMS_AF','LBL_ELEMENT_SUBTYPE_PANEL_VMS_AF', NULL, true, false, true, null, 300, 1, null, 120071, 1, false, true),
('ElementSubtype:16:71','VMS_TXT_1LINE','VMS_TXT_1LINE','LBL_ELEMENT_SUBTYPE_PANEL_VMS_TXT_1LINE', NULL, true, false, true, null, 300, 1, null, 160071, 1, false, true),
('ElementSubtype:18:71','VMS_GR_TXT_DGT','VMS_GR_TXT_DGT','LBL_ELEMENT_SUBTYPE_PANEL_VMS_GR_TXT_DGT', NULL, true, false, true, null, 300, 1, null, 180071, 1, false, true),
('ElementSubtype:19:71', 'PANEL', 'PANEL', 'LBL_ELEMENT_TYPE_PANEL', NULL, true, false, true, null, 300, 1, null, 100071, 1, false, true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:11:71',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 2, True),
('ElementSubtype:12:71',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 2, True),
('ElementSubtype:16:71',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 2, True),
('ElementSubtype:18:71',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 2, True),
('ElementSubtype:19:71', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(11007101, 'ElementSubtype:11:71', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True),
(12007101, 'ElementSubtype:12:71', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True),
(16007101, 'ElementSubtype:16:71', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True),
(18007101, 'ElementSubtype:18:71', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True),
(00007101, 'ElementSubtype:19:71', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--VMS_CLV
(11007101,'ElementTypeParam:71:2:1', 1, True),
(11007101,'ElementTypeParam:71:2:3', 3, True),
(11007101,'ElementTypeParam:71:2:4', 4, True),
--VMS_AF
(12007101,'ElementTypeParam:71:2:1', 1, True),
(12007101,'ElementTypeParam:71:2:3', 3, True),
(12007101,'ElementTypeParam:71:2:4', 4, True),
--VMS_TXT_1LINE
(16007101,'ElementTypeParam:71:2:3', 3, True),
(16007101,'ElementTypeParam:71:2:5', 5, True),
(16007101,'ElementTypeParam:71:2:6', 6, True),
--VMS_GR_TXT_DGT
(18007101,'ElementTypeParam:71:2:1', 1, True),
(18007101,'ElementTypeParam:71:2:2', 2, True),
(18007101,'ElementTypeParam:71:2:3', 3, True),
(18007101,'ElementTypeParam:71:2:10', 10, True),
--PMV
(00007101,'ElementTypeParam:71:2:2', 1, True),
(00007101,'ElementTypeParam:71:2:10', 2, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(711101,'ElementSubtype:11:71','ElementTypeParam:71:1:1', 1, True),
(711102,'ElementSubtype:11:71','ElementTypeParam:71:1:2', 2, True),
(711103,'ElementSubtype:11:71','ElementTypeParam:71:1:9', 9, True),
(711201,'ElementSubtype:12:71','ElementTypeParam:71:1:1', 1, True),
(711202,'ElementSubtype:12:71','ElementTypeParam:71:1:2', 2, True),
(711203,'ElementSubtype:12:71','ElementTypeParam:71:1:9', 9, True),
(711601,'ElementSubtype:16:71','ElementTypeParam:71:1:1', 1, True),
(711602,'ElementSubtype:16:71','ElementTypeParam:71:1:2', 2, True),
(711603,'ElementSubtype:16:71','ElementTypeParam:71:1:9', 9, True),
(711801,'ElementSubtype:18:71','ElementTypeParam:71:1:1', 1, True),
(711802,'ElementSubtype:18:71','ElementTypeParam:71:1:2', 2, True),
(711803,'ElementSubtype:18:71','ElementTypeParam:71:1:9', 9, True),
(711901, 'ElementSubtype:19:71', 'ElementTypeParam:71:1:1' , 1, true),
(711902, 'ElementSubtype:19:71', 'ElementTypeParam:71:1:2' , 1, true);
