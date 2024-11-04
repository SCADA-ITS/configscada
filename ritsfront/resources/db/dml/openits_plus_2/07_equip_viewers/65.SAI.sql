INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties, show_historical_chart) VALUES
('ElementType:65','SAI','SAI','LBL_ELEMENT_TYPE_SAI',null,True,False,false,null,225.0,3,null,65,2,True,true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:65',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(00006501, 'ElementType:65', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--SAI
(00006501,'ElementTypeParam:65:2:1', 1, True),
(00006501,'ElementTypeParam:65:2:2', 2, True),
(00006501,'ElementTypeParam:65:2:3', 3, True),
(00006501,'ElementTypeParam:65:2:4', 4, True),
(00006501,'ElementTypeParam:65:2:5', 5, True),
(00006501,'ElementTypeParam:65:2:6', 6, True),
(00006501,'ElementTypeParam:65:2:7', 7, True);