INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(93, 2, 'LBL_ELEMENT_TYPE_TRANSFER_GROUP', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(930001, 93, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL,NULL),
(930002, 93, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL,NULL),
(930003, 93, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(930004, 93, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(930005, 93, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, null, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL,NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(93, 1, 'true'),
(93, 2, 'true'),
(93, 7, 'true'),
(93, 8, 'true'),
(93, 9, 'false'),
(93, 10, 'true'),
(93, 11, 'true'),
(93, 12, 'true'),
(93, 13, 'true'),
(93, 14, 'false'),
(93, 15, 'false'),
(93, 16, 'img/grid/element_type_93.png'),
(93, 17, 'true'),
(93, 20, 'true'),
(93, 25, 'true'),
(93, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(93, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(93, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(93, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES(600004, './img/state/stateUnknown.png', 'ElementTypeState:60:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(930004, './img/state/stateUnknown.png', 'ElementTypeState:93:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(930004, './img/state/stateUp.png', 'ElementTypeState:93:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(930004, './img/state/stateDown.png', 'ElementTypeState:93:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(930004, './img/state/stateAlarm.png', 'ElementTypeState:93:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(930004, './img/state/stateMaintenance.png', 'ElementTypeState:93:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

