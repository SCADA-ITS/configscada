INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(62, 2, 'LBL_REACTOR', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(620001, 62, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(620002, 62, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(620003, 62, 1, 3, true, false, false, false, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(620004, 62, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),
(620005, 62, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(62, 1, 'true'), 
(62, 2, 'true'), 
(62, 7, 'true'), 
(62, 8, 'true'), 
(62, 9, 'false'), 
(62, 10, 'True'), 
(62, 11, 'True'), 
(62, 12, 'True'), 
(62, 13, 'True'), 
(62, 14, 'false'), 
(62, 15, 'false'), 
(62, 16, 'img/grid/element_type_62.png'), 
(62, 17, 'true'), 
(62, 25, 'true'),
(62, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(62, 1, 'LBL_CMD_VIEW_EQUIPMENT', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(62, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(62, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(620004, './img/state/stateUnknown.png', 'ElementTypeState:62:0', false, false, false, true, 'LBL_ICON_STATE_UNKNOWN'),
(620004, './img/state/stateUp.png', 'ElementTypeState:62:1', false, false, false, true, 'LBL_ICON_STATE_UP'),
(620004, './img/state/stateDown.png', 'ElementTypeState:62:2', false, false, false, true, 'LBL_ICON_STATE_DOWN'),
(620004, './img/state/stateAlarm.png', 'ElementTypeState:62:3', false, false, false, true, 'LBL_ICON_STATE_ALARM'),
(620004, './img/state/stateMaintenance.png', 'ElementTypeState:62:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');



