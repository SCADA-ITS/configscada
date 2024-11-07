INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties,show_historical_chart) VALUES
('ElementType:1','Meteo Modbus_P1','Meteo Modbus_P1','LBL_ELEMENT_SUBTYPE_METEO_MODBUS_P1',null,True,False,false,null,225.0,3,null,1,2,False,true);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:1',100501,'Alarms module' ,'Alarms module', 'LBL_ALARMS', null, 1, True);

--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador
--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador
INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES
(03000101, 'ElementType:1', 'Measures', 'Measures', 'LBL_GRP_MEASURES', null, 1, True);

INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
(000101,'ElementType:1','ElementTypeParam:1:1:1', 1, True),
(000102,'ElementType:1','ElementTypeParam:1:1:2', 2, True);

INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES
--Meteo Modbus_P1
(03000101,'ElementTypeParam:1:2:1', 1, True),
(03000101,'ElementTypeParam:1:2:4', 4, True),
(03000101,'ElementTypeParam:1:2:10', 10, True),
(03000101,'ElementTypeParam:1:2:11', 11, True),
(03000101,'ElementTypeParam:1:2:15', 15, True),
(03000101,'ElementTypeParam:1:2:16', 16, True),
(03000101,'ElementTypeParam:1:2:19', 19, True),
(03000101,'ElementTypeParam:1:2:23', 23, True);