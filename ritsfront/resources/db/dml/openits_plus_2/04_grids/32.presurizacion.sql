INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(32, 2, 'LBL_ELEMENT_TYPE_PRESURIZACION', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(320001, 32, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(320002, 32, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, true, NULL, NULL),
(320003, 32, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(320004, 32, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(320005, 32, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(320006, 32, 1, 6, true, false, false, false, 'LBL_REMOTE_CONTROL', 'LBL_REMOTE_CONTROL', 'ElementTypeParam:32:2:1', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),
(320007, 32, 1, 7, true, false, false, false, 'LBL_PRESS_FAN_1', 'LBL_PRESS_FAN_1', 'ElementTypeParam:32:2:2', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),
(320008, 32, 1, 8, true, false, false, false, 'LBL_PRESS_FAN_2', 'LBL_PRESS_FAN_2', 'ElementTypeParam:32:2:3', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(32, 1, 'true'),
(32, 2, 'true'),
(32, 7, 'true'),
(32, 8, 'true'),
(32, 9, 'false'),
(32, 10, 'true'),
(32, 11, 'true'),
(32, 12, 'true'),
(32, 13, 'true'),
(32, 14, 'false'),
(32, 15, 'false'),
(32, 16, 'img/grid/element_type_32.png'),
(32, 17, 'true'),
(32, 20, 'true'),
(32, 25, 'true'),
(32, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(32, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(32, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(32, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(320004, './img/state/stateUnknown.png', 'ElementTypeState:32:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(320004, './img/state/stateUp.png', 'ElementTypeState:32:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(320004, './img/state/stateDown.png', 'ElementTypeState:32:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(320004, './img/state/stateAlarm.png', 'ElementTypeState:32:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(320004, './img/state/stateMaintenance.png', 'ElementTypeState:32:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--Presurizacion
(320006, 'LBL_GRID_FR_NO', '0', true),
(320006, 'LBL_GRID_FR_SI', '1', true),

(320007, 'LBL_GRID_FR_NO', '0', true),
(320007, 'LBL_GRID_FR_SI', '1', true),

(320008, 'LBL_GRID_FR_NO', '0', true),
(320008, 'LBL_GRID_FR_SI', '1', true);
