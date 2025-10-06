INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(2, 2, 'LBL_ELEMENT_TYPE_CAMERA', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(020001, 2, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(020002, 2, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(020003, 2, 1, 3, true, false, false, true, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(020004, 2, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),
(020005, 2, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(2, 1, 'true'),
(2, 2, 'true'),
(2, 7, 'true'),
(2, 8, 'true'),
(2, 9, 'false'),
(2, 10, 'true'),
(2, 11, 'true'),
(2, 12, 'true'),
(2, 13, 'true'),
(2, 14, 'false'),
(2, 15, 'false'),
(2, 16, 'img/grid/camera.png'),
(2, 17, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(2, 1, 'LBL_CMD_VIEW_EQUIPMENT', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(2, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 1, true, true, true),
(2, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, 1, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(020004, './img/state/stateUnknown.png', 'ElementTypeState:2:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(020004, './img/state/stateUp.png', 'ElementTypeState:2:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(020004, './img/state/stateDown.png', 'ElementTypeState:2:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(020004, './img/state/stateAlarm.png', 'ElementTypeState:2:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');
