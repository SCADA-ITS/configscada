INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(63, 2, 'LBL_GENERADOR', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(630001, 63, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(630002, 63, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(630003, 63, 1, 3, true, false, false, false, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(630004, 63, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),
(630005, 63, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(630006, 63, 1, 6, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_PRESSURE', 'LBL_ELEMENT_TYPE_PARAM_PRESSURE', 'ElementTypeParam:63:2:1', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL),
(630007, 63, 1, 7, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_TEMPERATURE', 'LBL_ELEMENT_TYPE_PARAM_TEMPERATURE', 'ElementTypeParam:63:2:2', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(63, 1, 'true'), 
(63, 2, 'true'), 
(63, 7, 'true'), 
(63, 8, 'true'), 
(63, 9, 'false'), 
(63, 10, 'true'), 
(63, 11, 'true'), 
(63, 12, 'true'), 
(63, 13, 'true'), 
(63, 14, 'false'), 
(63, 15, 'false'), 
(63, 16, 'img/grid/element_type_63.png'), 
(63, 17, 'true'), 
(63, 25, 'true'),
(63, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(63, 1, 'LBL_CMD_VIEW_EQUIPMENT', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(63, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(63, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(630004, './img/state/stateUnknown.png', 'ElementTypeState:63:0', false, false, false, true, 'LBL_ICON_STATE_UNKNOWN'),
(630004, './img/state/stateUp.png', 'ElementTypeState:63:1', false, false, false, true, 'LBL_ICON_STATE_UP'),
(630004, './img/state/stateDown.png', 'ElementTypeState:63:2', false, false, false, true, 'LBL_ICON_STATE_DOWN'),
(630004, './img/state/stateAlarm.png', 'ElementTypeState:63:3', false, false, false, true, 'LBL_ICON_STATE_ALARM');


