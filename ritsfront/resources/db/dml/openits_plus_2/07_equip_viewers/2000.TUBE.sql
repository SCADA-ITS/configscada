INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:2002', 'Lighting manager', 'Lighting manager', 'LBL_ELEMENT_TYPE_ILUMINATION', NULL, false, false, false, null, 300, 3, null, 2002, 1, false);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
--('ElementType:2000',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null,  1, True );
('ElementType:2002', 200002, 'Synoptic', 'Synoptic', 'LBL_SYNOPTIC', NULL, 1, true);

-- --equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
-- --if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
-- INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
-- (00200001, 'ElementType:2000', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

-- INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
-- --TUBE
-- (00200001,'ElementTypeParam:2000:2:1', 1, True),
-- (00200001,'ElementTypeParam:2000:2:2', 2, True),
-- (00200001,'ElementTypeParam:2000:2:3', 3, True),
-- (00200001,'ElementTypeParam:2000:2:4', 4, True),
-- (00200001,'ElementTypeParam:2000:2:5', 5, True),
-- (00200001,'ElementTypeParam:2000:2:6', 6, True),
-- (00200001,'ElementTypeParam:2000:2:7', 7, True),
-- (00200001,'ElementTypeParam:2000:2:8', 8, True);

-- INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
-- (00200001,'ElementType:2000','ElementTypeParam:2000:1:1', 1, True),
-- (00200002,'ElementType:2000','ElementTypeParam:2000:1:2', 2, True);