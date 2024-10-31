INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:70','Network analyzer','Network analyzer','LBL_ELEMENT_SUBTYPE_NETWORK_ANALYZER',null,True,False,False,null,225.0,3,null,70,2,null);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:70',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True);

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(01007001, 'ElementType:70', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--Network analyzer
(01007001,'ElementTypeParam:70:2:1', 1, True),
(01007001,'ElementTypeParam:70:2:2', 2, True),
(01007001,'ElementTypeParam:70:2:3', 3, True),
(01007001,'ElementTypeParam:70:2:4', 4, True),
(01007001,'ElementTypeParam:70:2:5', 5, True),
(01007001,'ElementTypeParam:70:2:6', 6, True),
(01007001,'ElementTypeParam:70:2:7', 7, True),
(01007001,'ElementTypeParam:70:2:8', 8, True),
(01007001,'ElementTypeParam:70:2:9', 9, True),
(01007001,'ElementTypeParam:70:2:10', 10, True),
(01007001,'ElementTypeParam:70:2:11', 11, True),
(01007001,'ElementTypeParam:70:2:12', 12, True),
(01007001,'ElementTypeParam:70:2:13', 13, True),
(01007001,'ElementTypeParam:70:2:14', 14, True),
(01007001,'ElementTypeParam:70:2:15', 15, True),
(01007001,'ElementTypeParam:70:2:16', 16, True),
(01007001,'ElementTypeParam:70:2:17', 17, True),
(01007001,'ElementTypeParam:70:2:18', 18, True),
(01007001,'ElementTypeParam:70:2:19', 19, True),
(01007001,'ElementTypeParam:70:2:20', 20, True),
(01007001,'ElementTypeParam:70:2:21', 21, True),
(01007001,'ElementTypeParam:70:2:22', 22, True),
(01007001,'ElementTypeParam:70:2:23', 23, True),
(01007001,'ElementTypeParam:70:2:24', 24, True),
(01007001,'ElementTypeParam:70:2:25', 25, True),
(01007001,'ElementTypeParam:70:2:26', 26, True),
(01007001,'ElementTypeParam:70:2:27', 27, True),
(01007001,'ElementTypeParam:70:2:28', 28, True),
(01007001,'ElementTypeParam:70:2:29', 29, True),
(01007001,'ElementTypeParam:70:2:30', 30, True),
(01007001,'ElementTypeParam:70:2:31', 31, True),
(01007001,'ElementTypeParam:70:2:32', 32, True),
(01007001,'ElementTypeParam:70:2:33', 33, True),
(01007001,'ElementTypeParam:70:2:34', 34, True),
(01007001,'ElementTypeParam:70:2:35', 35, True),
(01007001,'ElementTypeParam:70:2:36', 36, True),
(01007001,'ElementTypeParam:70:2:37', 37, True),
(01007001,'ElementTypeParam:70:2:38', 38, True),
(01007001,'ElementTypeParam:70:2:38', 39, True),
(01007001,'ElementTypeParam:70:2:40', 40, True),
(01007001,'ElementTypeParam:70:2:40', 41, True),
(01007001,'ElementTypeParam:70:2:42', 42, True),
(01007001,'ElementTypeParam:70:2:43', 43, True),
(01007001,'ElementTypeParam:70:2:44', 44, True),
(01007001,'ElementTypeParam:70:2:45', 45, True),
(01007001,'ElementTypeParam:70:2:46', 46, True),
(01007001,'ElementTypeParam:70:2:47', 47, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(700101,'ElementType:70','ElementTypeParam:70:1:1', 1, True),
(700102,'ElementType:70','ElementTypeParam:70:1:2', 2, True);