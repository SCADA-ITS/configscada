INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties, show_historical_chart) VALUES
('ElementSubtype:2:4','Sec REVENGA','Sec REVENGA','LBL_ELEMENT_SUBTYPE_SECTION_REVENGA',null,True,False,false,null,225.0,3,null,4,2,null, true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementSubtype:2:4',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True);

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(02000401, 'ElementSubtype:2:4', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--Sec REVENGA
(02000401,'ElementTypeParam:4:2:1', 1, True),
(02000401,'ElementTypeParam:4:2:2', 2, True),
(02000401,'ElementTypeParam:4:2:3', 3, True),
(02000401,'ElementTypeParam:4:2:4', 4, True),
(02000401,'ElementTypeParam:4:2:5', 5, True),
(02000401,'ElementTypeParam:4:2:6', 6, True),
(02000401,'ElementTypeParam:4:2:7', 7, True),
(02000401,'ElementTypeParam:4:2:8', 8, True),
(02000401,'ElementTypeParam:4:2:9', 9, True),
(02000401,'ElementTypeParam:4:2:10', 11, True),
(02000401,'ElementTypeParam:4:2:12', 12, True),
(02000401,'ElementTypeParam:4:2:13', 13, True),
(02000401,'ElementTypeParam:4:2:18', 18, True),
(02000401,'ElementTypeParam:4:2:60', 60, True),
(02000401,'ElementTypeParam:4:2:61', 61, True),
(02000401,'ElementTypeParam:4:2:80', 80, True),
(02000401,'ElementTypeParam:4:2:81', 81, True),
(02000401,'ElementTypeParam:4:2:82', 82, True),
(02000401,'ElementTypeParam:4:2:100', 100, True),
(02000401,'ElementTypeParam:4:2:101', 101, True),
(02000401,'ElementTypeParam:4:2:120', 120, True),
(02000401,'ElementTypeParam:4:2:121', 121, True),
(02000401,'ElementTypeParam:4:2:122', 122, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(040201,'ElementSubtype:2:4','ElementTypeParam:4:1:10', 10, True);