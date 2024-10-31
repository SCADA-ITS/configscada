INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(58, 2, 'LBL_ELEMENT_TYPE_PULSADOR', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(580001, 58, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(580002, 58, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, true, NULL, NULL),
(580003, 58, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(580004, 58, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(580005, 58, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(58, 1, 'true'),
(58, 2, 'true'),
(58, 7, 'true'),
(58, 8, 'true'),
(58, 9, 'false'),
(58, 10, 'true'),
(58, 11, 'true'),
(58, 12, 'true'),
(58, 13, 'true'),
(58, 14, 'false'),
(58, 15, 'false'),
(58, 16, 'img/grid/element_type_58.png'),
(58, 17, 'true'),
(58, 25, 'true'),
(58, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(58, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(58, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(58, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(580004, './img/state/stateUnknown.png', 'ElementTypeState:58:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(580004, './img/state/stateUp.png', 'ElementTypeState:58:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(580004, './img/state/stateDown.png', 'ElementTypeState:58:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(580004, './img/state/stateAlarm.png', 'ElementTypeState:58:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(580004, './img/state/stateMaintenance.png', 'ElementTypeState:58:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

