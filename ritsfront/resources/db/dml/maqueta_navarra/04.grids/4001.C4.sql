INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4001, 2, 'LBL_EXT_ENTITY_C4', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014001, 4001, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(024001, 4001, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'categoria', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL);
--(034001, 2, 1, 3, true, true, false, false, 'Cosa', 'Cosa', 'c4_my_ext_entities_with_values$categoria', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL);

/*INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4001, 1, 'true'),
(4001, 2, 'true'),
(4001, 7, 'true'),
(4001, 8, 'true'),
(4001, 9, 'false'),
(4001, 10, 'true'),
(4001, 11, 'true'),
(4001, 12, 'true'),
(4001, 13, 'true'),
(4001, 14, 'false'),
(4001, 15, 'false'),
(4001, 16, 'img/grid/element_type_02.png'),
(4001, 17, 'true');*/

/*INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(4001, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(4001, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(4001, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);*/

/*INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(020003, './img/state/stateUnknown.png', 'ElementTypeState:2:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(020003, './img/state/stateUp.png', 'ElementTypeState:2:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(020003, './img/state/stateDown.png', 'ElementTypeState:2:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(020003, './img/state/stateAlarm.png', 'ElementTypeState:2:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');*/

