INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(5, 2, 'LBL_ELEMENT_TYPE_PA_SYSTEM', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(050001, 5, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(050002, 5, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(050003, 5, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(050004, 5, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(050005, 5, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(050006, 5, 1, 6, true, false, false, false, 'LBL_MESSAGES_ON_AIR', 'LBL_MESSAGES_ON_AIR', 'ElementTypeParam:5:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(5, 1, 'true'),
(5, 2, 'true'),
(5, 7, 'true'),
(5, 8, 'true'),
(5, 9, 'false'),
(5, 10, 'true'),
(5, 11, 'true'),
(5, 12, 'true'),
(5, 13, 'true'),
(5, 14, 'false'),
(5, 15, 'false'),
(5, 16, 'img/grid/element_type_05.png'),
(5, 17, 'true'),
(5, 25, 'true'),
(5, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(5, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(5, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(5, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(050004, './img/state/stateUnknown.png', 'ElementTypeState:5:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(050004, './img/state/stateUp.png', 'ElementTypeState:5:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(050004, './img/state/stateDown.png', 'ElementTypeState:5:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(050004, './img/state/stateAlarm.png', 'ElementTypeState:5:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(050004, './img/state/stateMaintenance.png', 'ElementTypeState:5:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

