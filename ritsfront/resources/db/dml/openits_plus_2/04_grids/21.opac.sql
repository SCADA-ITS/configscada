INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(21, 2, 'LBL_ELEMENT_TYPE_OPAC', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(210001, 21, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(210002, 21, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(210003, 21, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(210004, 21, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(210005, 21, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(210006, 21, 3, 6, true, false, false, false, 'LBL_OPAC_CONCENTRATION', 'LBL_OPAC_CONCENTRATION', 'ElementTypeParam:21:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 3);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(21, 1, 'true'),
(21, 2, 'true'),
(21, 7, 'true'),
(21, 8, 'true'),
(21, 9, 'false'),
(21, 10, 'true'),
(21, 11, 'true'),
(21, 12, 'true'),
(21, 13, 'true'),
(21, 14, 'false'),
(21, 15, 'false'),
(21, 16, 'img/grid/element_type_21.png'),
(21, 17, 'true'),
(21, 25, 'true'),
(21, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(21, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(21, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(21, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(210004, './img/state/stateUnknown.png', 'ElementTypeState:21:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(210004, './img/state/stateUp.png', 'ElementTypeState:21:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(210004, './img/state/stateDown.png', 'ElementTypeState:21:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(210004, './img/state/stateAlarm.png', 'ElementTypeState:21:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(210004, './img/state/stateMaintenance.png', 'ElementTypeState:21:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');


