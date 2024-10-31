INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(51, 2, 'LBL_ELEMENT_TYPE_ETD', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(510001, 51, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(510002, 51, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(510003, 51, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(510004, 51, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(510005, 51, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(510006, 51, 1, 6, false, false, true, false, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:51:1:1', false, false, 'left', false, false, 130, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(510007, 51, 1, 7, false, false, true, false, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:51:1:2', false, false, 'left', false, false, 130, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(51, 1, 'true'),
(51, 2, 'true'),
(51, 7, 'true'),
(51, 8, 'true'),
(51, 9, 'false'),
(51, 10, 'true'),
(51, 11, 'true'),
(51, 12, 'true'),
(51, 13, 'true'),
(51, 14, 'false'),
(51, 15, 'false'),
(51, 16, 'img/grid/element_type_51.png'),
(51, 17, 'true'),
(51, 25, 'true'),
(51, 26, 'true');


INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(51, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(51, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(51, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(510004, './img/state/stateUnknown.png', 'ElementTypeState:51:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(510004, './img/state/stateUp.png', 'ElementTypeState:51:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(510004, './img/state/stateDown.png', 'ElementTypeState:51:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(510004, './img/state/stateAlarm.png', 'ElementTypeState:51:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(510004, './img/state/stateMaintenance.png', 'ElementTypeState:51:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

