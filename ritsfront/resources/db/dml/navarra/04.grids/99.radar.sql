INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(99, 2, 'LBL_ELEMENT_TYPE_RADAR', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(010099, 99, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(020099, 99, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(030099, 99, 1, 3, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),
(040099, 99, 1, 4, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(99, 1, 'true'),
(99, 2, 'true'),
(99, 7, 'true'),
(99, 8, 'true'),
(99, 9, 'false'),
(99, 10, 'true'),
(99, 11, 'true'),
(99, 12, 'true'),
(99, 13, 'true'),
(99, 14, 'false'),
(99, 15, 'false'),
(99, 16, 'img/grid/element_type_99.png'),
(99, 17, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(99, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(99, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(99, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(030099, './img/state/stateUnknown.png', 'ElementTypeState:99:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(030099, './img/state/stateUp.png', 'ElementTypeState:99:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(030099, './img/state/stateDown.png', 'ElementTypeState:99:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(030099, './img/state/stateAlarm.png', 'ElementTypeState:99:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');

