INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(86, 2, 'LBL_ELEMENT_TYPE_PUMP', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(860001, 86, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL,NULL),
(860002, 86, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(860003, 86, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(860004, 86, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(860005, 86, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, null, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(860006, 86, 1, 6, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_PUMP_ON', 'LBL_ELEMENT_TYPE_PARAM_PUMP_ON', 'ElementTypeParam:86:2:1', false, false, 'left', true, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(860007, 86, 1, 7, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_PRESSURE', 'LBL_ELEMENT_TYPE_PARAM_PRESSURE', 'ElementTypeParam:86:2:2', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(860008, 86, 1, 8, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_TEMPERATURE', 'LBL_ELEMENT_TYPE_PARAM_TEMPERATURE', 'ElementTypeParam:86:2:3', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(860009, 86, 1, 9, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_ANGULAR_SPEED', 'LBL_ELEMENT_TYPE_PARAM_ANGULAR_SPEED', 'ElementTypeParam:86:2:4', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(86, 1, 'true'),
(86, 2, 'true'),
(86, 7, 'true'),
(86, 8, 'true'),
(86, 9, 'false'),
(86, 10, 'true'),
(86, 11, 'true'),
(86, 12, 'true'),
(86, 13, 'true'),
(86, 14, 'false'),
(86, 15, 'false'),
(86, 16, 'img/grid/element_type_86.png'),
(86, 17, 'true'),
(86, 20, 'true'),
(86, 25, 'true'),
(86, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(86, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(86, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(86, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(860004, './img/state/stateUnknown.png', 'ElementTypeState:86:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(860004, './img/state/stateUp.png', 'ElementTypeState:86:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(860004, './img/state/stateDown.png', 'ElementTypeState:86:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(860004, './img/state/stateAlarm.png', 'ElementTypeState:86:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(860004, './img/state/stateMaintenance.png', 'ElementTypeState:86:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--Bomba
(860006, 'LBL_GRID_FR_NO', '0', true),
(860006, 'LBL_GRID_FR_SI', '1', true);
