INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,show_historical_chart) VALUES
('ElementType:18','ANEM_INT','ANEM_INT','LBL_ELEMENT_TYPE_ANEM_INT',null,True,False,false,null,225.0,3,null,18,2,True,true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:18',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(00001801, 'ElementType:18', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--ANEM_INT
(00001801,'ElementTypeParam:18:2:1', 1, True),
(00001801,'ElementTypeParam:18:2:2', 2, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(001801,'ElementType:18','ElementTypeParam:18:1:1', 1, True),
(001802,'ElementType:18','ElementTypeParam:18:1:2', 2, True),
(001803,'ElementType:18','ElementTypeParam:18:1:3', 3, True);