INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:16','PA_ZONE','PA_ZONE','LBL_ELEMENT_TYPE_PA_ZONE',null,True,False,False,null,225.0,3,null,16,2,True);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:16',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(00001601, 'ElementType:16', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--PA_ZONE
-- (00001601,'ElementTypeParam:16:2:1', 1, True),
(00001601,'ElementTypeParam:16:2:1001', 1, True),
(00001601,'ElementTypeParam:16:2:1002', 1, True),
(00001601,'ElementTypeParam:16:2:1003', 1, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(001601,'ElementType:16','ElementTypeParam:16:1:5', 5, True),
(001602,'ElementType:16','ElementTypeParam:16:1:6', 6, True);