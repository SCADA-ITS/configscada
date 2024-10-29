INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('Element:87:1','TUN-DEP-DIESEL-PCI','TUN-DEP-DIESEL-PCI','LBL_ELEMENT_TYPE_TANK',null,True,False,True,null,225.0,3,null,010087,2,null),
('Element:87:2','TUN-DALJ-1','TUN-DALJ-1','LBL_ELEMENT_TYPE_TANK',null,True,False,True,null,225.0,3,null,010087,2,null),
('Element:87:3','TUN-DEP-TOX-MA','TUN-DEP-TOX-MA','LBL_ELEMENT_TYPE_TANK',null,True,False,True,null,225.0,3,null,050087,2,null);


INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('Element:87:1',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True ),
('Element:87:2',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True ),
('Element:87:3',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(00008701, 'Element:87:1', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True),
(00008702, 'Element:87:2', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True),
(00008703, 'Element:87:3', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);


INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--TANK
(00008701,'ElementTypeParam:87:2:1', 1, True),

(00008702,'ElementTypeParam:87:2:1', 1, True),

(00008703,'ElementTypeParam:87:2:3', 1, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(008701,'Element:87:1','ElementTypeParam:87:1:1', 1, false),
(008702,'Element:87:1','ElementTypeParam:87:1:2', 2, False),
(008703,'Element:87:2','ElementTypeParam:87:1:1', 1, false),
(008704,'Element:87:2','ElementTypeParam:87:1:2', 2, False),
(008705,'Element:87:3','ElementTypeParam:87:1:1', 1, false),
(008706,'Element:87:3','ElementTypeParam:87:1:2', 2, False);