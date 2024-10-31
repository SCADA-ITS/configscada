INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(15, 2, 'LBL_ELEMENT_TYPE_FIRE_DET', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(150001, 15, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(150002, 15, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(150003, 15, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(150004, 15, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(150005, 15, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(150010, 15, 3, 10, true, false, false, false, 'LBL_AVG_TEMP', 'LBL_AVG_TEMP', 'ElementTypeParam:15:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(150011, 15, 3, 11, true, false, false, false, 'LBL_MAX_TEMP', 'LBL_MAX_TEMP', 'ElementTypeParam:15:2:2', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(150012, 15, 3, 12, true, false, false, false, 'LBL_MIN_TEMP', 'LBL_MIN_TEMP', 'ElementTypeParam:15:2:3', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(15, 1, 'true'),
(15, 2, 'true'),
(15, 7, 'true'),
(15, 8, 'true'),
(15, 9, 'false'),
(15, 10, 'true'),
(15, 11, 'true'),
(15, 12, 'true'),
(15, 13, 'true'),
(15, 14, 'false'),
(15, 15, 'false'),
(15, 16, 'img/grid/element_type_15.png'),
(15, 17, 'true'),
(15, 25, 'true'),
(15, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(15, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(15, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(15, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(150004, './img/state/stateUnknown.png', 'ElementTypeState:15:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(150004, './img/state/stateUp.png', 'ElementTypeState:15:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(150004, './img/state/stateDown.png', 'ElementTypeState:15:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(150004, './img/state/stateAlarm.png', 'ElementTypeState:15:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(150004, './img/state/stateMaintenance.png', 'ElementTypeState:15:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');


