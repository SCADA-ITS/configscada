INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,show_historical_chart) VALUES
('ElementType:75','GE','GE','LBL_ELEMENT_TYPE_GE',null,True,False,True,null,225.0,3,null,010075,2,True,true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:75',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(00007501, 'ElementType:75', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--GE
(00007501,'ElementTypeParam:75:2:1', 1, True),
(00007501,'ElementTypeParam:75:2:2', 2, True),
(00007501,'ElementTypeParam:75:2:3', 3, True),
(00007501,'ElementTypeParam:75:2:4', 4, True),
(00007501,'ElementTypeParam:75:2:5', 5, True),
(00007501,'ElementTypeParam:75:2:6', 6, True),
(00007501,'ElementTypeParam:75:2:7', 7, True),
(00007501,'ElementTypeParam:75:2:8', 8, True),
(00007501,'ElementTypeParam:75:2:9', 9, True),
(00007501,'ElementTypeParam:75:2:10', 10, True),
(00007501,'ElementTypeParam:75:2:11', 11, True),
(00007501,'ElementTypeParam:75:2:12', 12, True),
(00007501,'ElementTypeParam:75:2:13', 13, True),
(00007501,'ElementTypeParam:75:2:14', 14, True),
(00007501,'ElementTypeParam:75:2:15', 15, True),
(00007501,'ElementTypeParam:75:2:16', 16, True),
(00007501,'ElementTypeParam:75:2:17', 17, True),
(00007501,'ElementTypeParam:75:2:18', 18, True),
(00007501,'ElementTypeParam:75:2:19', 19, True),
(00007501,'ElementTypeParam:75:2:20', 20, True),
(00007501,'ElementTypeParam:75:2:21', 21, True),
(00007501,'ElementTypeParam:75:2:22', 22, True),
(00007501,'ElementTypeParam:75:2:23', 23, True),
(00007501,'ElementTypeParam:75:2:24', 24, True),
(00007501,'ElementTypeParam:75:2:25', 25, True),
(00007501,'ElementTypeParam:75:2:26', 26, True),
(00007501,'ElementTypeParam:75:2:27', 27, True),
(00007501,'ElementTypeParam:75:2:28', 28, True),
(00007501,'ElementTypeParam:75:2:29', 29, True),
(00007501,'ElementTypeParam:75:2:30', 30, True),
(00007501,'ElementTypeParam:75:2:31', 31, True),
(00007501,'ElementTypeParam:75:2:32', 32, True),
(00007501,'ElementTypeParam:75:2:33', 33, True),
(00007501,'ElementTypeParam:75:2:34', 34, True),
(00007501,'ElementTypeParam:75:2:35', 35, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(007501,'ElementType:75','ElementTypeParam:75:1:1', 1, True),
(007502,'ElementType:75','ElementTypeParam:75:1:2', 2, True),
(007503,'ElementType:75','ElementTypeParam:75:1:3', 3, True),
(007504,'ElementType:75','ElementTypeParam:75:1:4', 4, True);