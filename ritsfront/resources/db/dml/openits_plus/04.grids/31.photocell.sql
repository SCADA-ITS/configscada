INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(31, 2, 'LBL_ELEMENT_TYPE_PHOTOCELL', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(310001, 31, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(310002, 31, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(310003, 31, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(310004, 31, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(310005, 31, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(310006, 31, 3, 6, true, false, false, false, 'LBL_LUMINANCE', 'LBL_LUMINANCE', 'ElementTypeParam:31:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),
(310007, 31, 1, 7, false, false, true, false, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:31:1:1', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(310008, 31, 1, 8, false, false, true, false, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:31:1:2', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(31, 1, 'true'),
(31, 2, 'true'),
(31, 7, 'true'),
(31, 8, 'true'),
(31, 9, 'false'),
(31, 10, 'true'),
(31, 11, 'true'),
(31, 12, 'true'),
(31, 13, 'true'),
(31, 14, 'false'),
(31, 15, 'false'),
(31, 16, 'img/grid/element_type_31.png'),
(31, 17, 'true'),
(31, 20, 'true'),
(31, 25, 'true'),
(31, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(31, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(31, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(31, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(310004, './img/state/stateUnknown.png', 'ElementTypeState:31:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(310004, './img/state/stateUp.png', 'ElementTypeState:31:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(310004, './img/state/stateDown.png', 'ElementTypeState:31:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(310004, './img/state/stateAlarm.png', 'ElementTypeState:31:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(310004, './img/state/stateMaintenance.png', 'ElementTypeState:31:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');


