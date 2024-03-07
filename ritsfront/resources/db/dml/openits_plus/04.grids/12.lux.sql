INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(12, 2, 'LBL_ELEMENT_TYPE_LUX', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(120001, 12, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(120002, 12, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(120003, 12, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(120004, 12, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(120005, 12, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(120006, 12, 3, 6, true, false, false, false, 'LBL_ILLUMINANCE', 'LBL_ILLUMINANCE', 'ElementTypeParam:12:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(12, 1, 'true'),
(12, 2, 'true'),
(12, 7, 'true'),
(12, 8, 'true'),
(12, 9, 'false'),
(12, 10, 'true'),
(12, 11, 'true'),
(12, 12, 'true'),
(12, 13, 'true'),
(12, 14, 'false'),
(12, 15, 'false'),
(12, 16, 'img/grid/element_type_12.png'),
(12, 17, 'true'),
(12, 25, 'true'),
(12, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(12, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(12, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(12, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(120004, './img/state/stateUnknown.png', 'ElementTypeState:12:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(120004, './img/state/stateUp.png', 'ElementTypeState:12:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(120004, './img/state/stateDown.png', 'ElementTypeState:12:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(120004, './img/state/stateAlarm.png', 'ElementTypeState:12:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(120004, './img/state/stateMaintenance.png', 'ElementTypeState:12:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');


