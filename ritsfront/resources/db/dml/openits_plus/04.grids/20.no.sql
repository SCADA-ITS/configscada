INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(20, 2, 'LBL_ELEMENT_TYPE_NO', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(200001, 20, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(200002, 20, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(200003, 20, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(200004, 20, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(200005, 20, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(200006, 20, 3, 6, true, false, false, false, 'LBL_NO_CONCENTRATION', 'LBL_NO_CONCENTRATION', 'ElementTypeParam:20:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 1);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(20, 1, 'true'),
(20, 2, 'true'),
(20, 7, 'true'),
(20, 8, 'true'),
(20, 9, 'false'),
(20, 10, 'true'),
(20, 11, 'true'),
(20, 12, 'true'),
(20, 13, 'true'),
(20, 14, 'false'),
(20, 15, 'false'),
(20, 16, 'img/grid/element_type_20.png'),
(20, 17, 'true'),
(20, 25, 'true'),
(20, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(20, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(20, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(20, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(200004, './img/state/stateUnknown.png', 'ElementTypeState:20:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(200004, './img/state/stateUp.png', 'ElementTypeState:20:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(200004, './img/state/stateDown.png', 'ElementTypeState:20:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(200004, './img/state/stateAlarm.png', 'ElementTypeState:20:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(200004, './img/state/stateMaintenance.png', 'ElementTypeState:20:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');


