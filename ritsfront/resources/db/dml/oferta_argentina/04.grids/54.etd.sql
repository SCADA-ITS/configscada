INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(54, 2, 'LBL_ETD', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(540001, 54, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(540002, 54, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(540003, 54, 1, 3, true, false, false, false, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(540004, 54, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),
(540005, 54, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(54, 1, 'true'),
(54, 2, 'true'),
(54, 3, NULL),
(54, 4, NULL),
(54, 5, NULL),
(54, 6, NULL),
(54, 7, 'true'),
(54, 8, 'true'),
(54, 9, 'false'),
(54, 10, 'true'),
(54, 11, 'true'),
(54, 12, 'true'),
(54, 13, 'true'),
(54, 14, 'false'),
(54, 15, 'false'),
(54, 16, 'img/grid/element_type_51.png'),
(54, 17, 'true'),
(54, 18, NULL),
(54, 19, NULL),
(54, 20, 'true'),
(54, 21, NULL),
(54, 22, NULL);

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(54, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(54, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(540004, './img/state/stateUnknown.png', 'ElementTypeState:54:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(540004, './img/state/stateUp.png', 'ElementTypeState:54:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(540004, './img/state/stateDown.png', 'ElementTypeState:54:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(540004, './img/state/stateAlarm.png', 'ElementTypeState:54:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');


