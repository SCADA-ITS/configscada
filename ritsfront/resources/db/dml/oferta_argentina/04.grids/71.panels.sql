INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(71, 2, 'LBL_ELEMENT_TYPE_PANEL', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(710001, 71, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(710002, 71, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(710003, 71, 1, 3, true, false, false, false, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, NULL, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(710004, 71, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL),
(710005, 71, 1, 5, true, true, false, false, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 120, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(71, 1, 'true'),
(71, 2, 'true'),
(71, 7, 'true'),
(71, 8, 'true'),
(71, 9, 'false'),
(71, 10, 'true'),
(71, 11, 'true'),
(71, 12, 'true'),
(71, 13, 'true'),
(71, 14, 'false'),
(71, 15, 'false'),
(71, 16, 'img/grid/element_type_71.png'),
(71, 17, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(71, 1, 'LBL_CMD_VIEW_EQUIPMENT', 'mdi mdi-eye', true, 101401, NULL, 1, false, false, false),
(71, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 1, true, true, true),
(71, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, 1, true, true, true);


INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(710004, './img/state/stateUnknown.png', 'ElementTypeState:71:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(710004, './img/state/stateUp.png', 'ElementTypeState:71:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(710004, './img/state/stateDown.png', 'ElementTypeState:71:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(710004, './img/state/stateAlarm.png', 'ElementTypeState:71:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');
