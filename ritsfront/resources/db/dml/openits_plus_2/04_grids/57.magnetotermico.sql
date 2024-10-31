INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(57, 2, 'LBL_ELEMENT_TYPE_INTERRUPTOR', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(570001, 57, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(570002, 57, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 220, NULL, true, true, NULL, NULL, true, NULL, NULL),
(570003, 57, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 220, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(570004, 57, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(570005, 57, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(570006, 57, 2, 6, true, false, false, false, 'LBL_STATE', 'LBL_STATE', 'ElementTypeParam:57:2:1', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(57, 1, 'true'),
(57, 2, 'true'),
(57, 7, 'true'),
(57, 8, 'true'),
(57, 9, 'false'),
(57, 10, 'true'),
(57, 11, 'true'),
(57, 12, 'true'),
(57, 13, 'true'),
(57, 14, 'false'),
(57, 15, 'false'),
(57, 16, 'img/grid/element_type_57.png'),
(57, 17, 'true'),
(57, 25, 'true'),
(57, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(57, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(57, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(57, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(570004, './img/state/stateUnknown.png', 'ElementTypeState:57:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(570004, './img/state/stateUp.png', 'ElementTypeState:57:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(570004, './img/state/stateDown.png', 'ElementTypeState:57:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(570004, './img/state/stateAlarm.png', 'ElementTypeState:57:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(570004, './img/state/stateMaintenance.png', 'ElementTypeState:57:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--ERU
(570006, 'LBL_GRID_FR_CERRADO', '0', true),
(570006, 'LBL_GRID_FR_ABIERTO', '1', true);