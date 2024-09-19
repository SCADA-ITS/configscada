INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:89','GATE','GATE','LBL_ELEMENT_TYPE_GATE',null,True,False,false,null,225.0,3,null,89,2,true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:89',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(00008901, 'ElementType:89', 'Measures', 'Measures', 'LBL_ELEMENT_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(008901,'ElementType:89','ElementTypeParam:89:1:1', 1, True),
(008902,'ElementType:89','ElementTypeParam:89:1:2', 2, True);