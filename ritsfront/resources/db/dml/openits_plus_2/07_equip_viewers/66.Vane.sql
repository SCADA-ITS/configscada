INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:66','VANE','VANE','LBL_ELEMENT_SUBTYPE_VANE',null,True,False,True,null,225.0,3,null,66,2,null);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:66',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True);

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(01006601, 'ElementType:66', 'Measures', 'Measures', 'LBL_GRP_MEASURES', 66, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--VANE
(01006601,'ElementTypeParam:66:2:3', 1, True),
(01006601,'ElementTypeParam:66:2:2', 2, True),
(01006601,'ElementTypeParam:66:2:4', 4, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(660101,'ElementType:66','ElementTypeParam:66:1:1', 1, True),
(660102,'ElementType:66','ElementTypeParam:66:1:2', 2, True),
(660103,'ElementType:66','ElementTypeParam:66:1:3', 3, True),
(660104,'ElementType:66','ElementTypeParam:66:1:4', 4, True);