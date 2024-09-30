INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('Element:86:3','PN-BB-MAR','PN-BB-MAR','LBL_ELEMENT_TYPE_PUMP',null,True,False,false,null,225.0,3,null,000086,2,null),
('Element:86:4','PN-BB-CON','PN-BB-CON','LBL_ELEMENT_TYPE_PUMP',null,True,False,false,null,225.0,3,null,000086,2,null),
('Element:86:5','PN-BB-RET','PN-BB-RET','LBL_ELEMENT_TYPE_PUMP',null,True,False,false,null,225.0,3,null,000086,2,null),
('Element:86:6','PN-BB-REF','PN-BB-REF','LBL_ELEMENT_TYPE_PUMP',null,True,False,false,null,225.0,3,null,000086,2,null),
('Element:86:7','PN-BB-VAP','PN-BB-VAP','LBL_ELEMENT_TYPE_PUMP',null,True,False,false,null,225.0,3,null,000086,2,null);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('Element:86:3',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True ),
('Element:86:4',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True ),
('Element:86:5',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True ),
('Element:86:6',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True ),
('Element:86:7',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(008601, 'Element:86:3', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True),
(008602, 'Element:86:4', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True),
(008603, 'Element:86:5', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True),
(008604, 'Element:86:6', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True),
(008605, 'Element:86:7', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
(008602,'ElementTypeParam:86:2:4', 1, True),

(008603,'ElementTypeParam:86:2:3', 1, True),

(008604,'ElementTypeParam:86:2:2', 1, True),
(008604,'ElementTypeParam:86:2:3', 1, True),

(008605,'ElementTypeParam:86:2:2', 1, True),
(008605,'ElementTypeParam:86:2:3', 1, True);

