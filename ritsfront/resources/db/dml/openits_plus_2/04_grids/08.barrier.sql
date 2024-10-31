INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(8, 2, 'LBL_ELEMENT_TYPE_BARRIER', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(080001, 8, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(080002, 8, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(080003, 8, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(080004, 8, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(080005, 8, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(080006, 8, 1, 6, true, false, false, false, 'LBL_BAR_CMD', 'LBL_BAR_CMD', 'ElementTypeParam:8:2:1', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(8, 1, 'true'),
(8, 2, 'true'),
(8, 7, 'true'),
(8, 8, 'true'),
(8, 9, 'false'),
(8, 10, 'true'),
(8, 11, 'true'),
(8, 12, 'true'),
(8, 13, 'true'),
(8, 14, 'false'),
(8, 15, 'false'),
(8, 16, 'img/grid/element_type_08.png'),
(8, 17, 'true'),
(8, 25, 'true'),
(8, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(8, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(8, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(8, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(080004, './img/state/stateUnknown.png', 'ElementTypeState:8:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(080004, './img/state/stateUp.png', 'ElementTypeState:8:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(080004, './img/state/stateDown.png', 'ElementTypeState:8:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(080004, './img/state/stateAlarm.png', 'ElementTypeState:8:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(080004, './img/state/stateMaintenance.png', 'ElementTypeState:8:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--Barrera
(080006, 'LBL_GRID_FR_SUBIDA', '1', true),
(080006, 'LBL_GRID_FR_BAJADA', '2', true);
