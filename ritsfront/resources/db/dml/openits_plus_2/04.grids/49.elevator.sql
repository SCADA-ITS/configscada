INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(49, 2, 'LBL_ELEMENT_TYPE_ELEVATOR', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(490001, 49, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(490002, 49, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, NULL, false, false, 220, NULL, true, true, NULL, NULL, true, NULL, NULL),
(490003, 49, 1, 3, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, false, NULL, false, false, 220, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(490004, 49, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(490005, 49, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(490008, 49, 2, 6, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_FLOOR', 'LBL_ELEMENT_TYPE_PARAM_FLOOR', 'ElementTypeParam:49:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(49, 1, 'true'),
(49, 2, 'true'),
(49, 7, 'true'),
(49, 8, 'true'),
(49, 9, 'false'),
(49, 10, 'true'),
(49, 11, 'true'),
(49, 12, 'true'),
(49, 13, 'true'),
(49, 14, 'false'),
(49, 15, 'false'),
(49, 16, 'img/grid/element_type_49.png'),
(49, 17, 'true'),
(49, 20, 'true');


INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(49, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(49, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(49, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(490004, './img/state/stateUnknown.png', 'ElementTypeState:49:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(490004, './img/state/stateUp.png', 'ElementTypeState:49:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(490004, './img/state/stateDown.png', 'ElementTypeState:49:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(490004, './img/state/stateAlarm.png', 'ElementTypeState:49:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');


