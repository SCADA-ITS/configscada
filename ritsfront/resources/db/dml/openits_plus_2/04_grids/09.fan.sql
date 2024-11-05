INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(9, 2, 'LBL_ELEMENT_TYPE_FAN', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(090001, 9, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(090002, 9, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 170, NULL, true, true, NULL, NULL, true, NULL, NULL),
(090003, 9, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(090004, 9, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(090005, 9, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(090006, 9, 1, 6, true, false, false, false, 'LBL_FAN_STATE', 'LBL_FAN_STATE', 'ElementTypeParam:9:2:1', false, false, 'left', true, false, 180, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(090007, 9, 3, 7, true, false, false, false, 'LBL_FAN_VIBRATION', 'LBL_FAN_VIBRATION', 'ElementTypeParam:9:2:2', false, false, 'left', false, false, 160, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(090008, 9, 2, 8, true, false, false, false, 'LBL_FRONT_BEARING_TEMPERATURE', 'LBL_FRONT_BEARING_TEMPERATURE', 'ElementTypeParam:9:2:3', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(090009, 9, 2, 9, true, false, false, false, 'LBL_BACK_BEARING_TEMPERATURE', 'LBL_BACK_BEARING_TEMPERATURE', 'ElementTypeParam:9:2:4', false, false, 'left', false, false, 280, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(9, 1, 'true'),
(9, 2, 'true'),
(9, 7, 'true'),
(9, 8, 'true'),
(9, 9, 'false'),
(9, 10, 'true'),
(9, 11, 'true'),
(9, 12, 'true'),
(9, 13, 'true'),
(9, 14, 'false'),
(9, 15, 'false'),
(9, 16, 'img/grid/element_type_09.png'),
(9, 17, 'true'),
(9, 25, 'true'),
(9, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(9, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(9, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(9, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(090004, './img/state/stateUnknown.png', 'ElementTypeState:9:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(090004, './img/state/stateUp.png', 'ElementTypeState:9:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(090004, './img/state/stateDown.png', 'ElementTypeState:9:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(090004, './img/state/stateAlarm.png', 'ElementTypeState:9:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(090004, './img/state/stateMaintenance.png', 'ElementTypeState:9:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--Ventilador
(090006, 'LBL_GRID_FR_PARADO', '0', true),
(090006, 'LBL_GRID_FR_DIRECTA', '1', true),
(090006, 'LBL_GRID_FR_INVERSA', '2', true);
