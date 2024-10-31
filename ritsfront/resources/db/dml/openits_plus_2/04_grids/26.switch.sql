INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(26, 2, 'LBL_ELEMENT_TYPE_SWITCH_COMM', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(260001, 26, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL,NULL),
(260002, 26, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL,NULL),
(260003, 26, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(260004, 26, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(260005, 26, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, null, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL,NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(26, 1, 'true'),
(26, 2, 'true'),
(26, 7, 'true'),
(26, 8, 'true'),
(26, 9, 'false'),
(26, 10, 'true'),
(26, 11, 'true'),
(26, 12, 'true'),
(26, 13, 'true'),
(26, 14, 'false'),
(26, 15, 'false'),
(26, 16, 'img/grid/element_type_26.png'),
(26, 17, 'true'),
(26, 25, 'true'),
(26, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(26, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(26, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(26, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(260004, './img/state/stateUnknown.png', 'ElementTypeState:26:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(260004, './img/state/stateUp.png', 'ElementTypeState:26:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(260004, './img/state/stateDown.png', 'ElementTypeState:26:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(260004, './img/state/stateAlarm.png', 'ElementTypeState:26:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(260004, './img/state/stateMaintenance.png', 'ElementTypeState:26:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');


