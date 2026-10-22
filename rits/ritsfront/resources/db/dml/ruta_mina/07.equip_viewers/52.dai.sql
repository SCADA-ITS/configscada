INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES
('ElementType:52', 'DAI', 'DAI', 'LBL_ELEMENT_TYPE_DAI', NULL, true, false, true, 1, 300, 3, null,52, 2, null);

INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, "label", icon, "position", visible) VALUES
('ElementType:52', 100501, 'Alarms module', 'Alarms module', 'LBL_ALARMS', NULL, 2, true);

INSERT INTO ui.equip_viewer_grp_measures (id, equip_viewer_id, name, description, "label", icon, "position", visible) VALUES
(52010, 'ElementType:52', 'Values element type DAI', 'Values element type DAI', 'LBL_ELEMENT_MEASURES', NULL, 1, false);
 
INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, "position", visible) VALUES
-- (5201, 'ElementType:52', 'ElementTypeParam:52:1:1' , 1, true),
-- (5202, 'ElementType:52', 'ElementTypeParam:52:1:2' , 2, true);
(520101, 'ElementType:52', 'ElementTypeParam:52:1:1' , 1, true),
(520102, 'ElementType:52', 'ElementTypeParam:52:1:1004' , 1, true);