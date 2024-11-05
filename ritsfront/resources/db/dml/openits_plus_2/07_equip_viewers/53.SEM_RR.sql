INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('Element:53:3','SEM RR','SEM RR','LBL_ELEMENT_TYPE_SEM_2_LUCES',null,True,False,True,null,225.0,3,null,010053,2,true),
('Element:53:4','SEM RR','SEM RR','LBL_ELEMENT_TYPE_SEM_2_LUCES',null,True,False,True,null,225.0,3,null,010053,2,true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('Element:53:3',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True),
('Element:53:4',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True);

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(02005301, 'Element:53:3', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True),
(02005302, 'Element:53:4', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
(02005301,'ElementTypeParam:53:2:1', 1, True),
(02005302,'ElementTypeParam:53:2:1', 1, True);