INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(48, 2, 'LBL_ELEMENT_TYPE_TOXIC_CONTROL', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(480001, 48, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(480002, 48, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, NULL, false, false, 220, NULL, true, true, NULL, NULL, true, NULL, NULL),
(480003, 48, 1, 3, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, false, NULL, false, false, 220, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(480004, 48, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(480005, 48, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(480006, 48, 1, 6, false, false, true, false, 'LBL_BRAND', 'LBL_BRAND', 'ElementTypeParam:48:1:1', false, false, 'left', false, false, 130, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(480007, 48, 1, 7, false, false, true, false, 'LBL_MODEL', 'LBL_MODEL', 'ElementTypeParam:48:1:2', false, false, 'left', false, false, 130, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(480008, 48, 2, 8, true, false, false, false, 'LBL_OPENING_GATE', 'LBL_OPENING_GATE', 'ElementTypeParam:48:2:1', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(480009, 48, 2, 9, true, false, false, false, 'LBL_GATE_OPENED', 'LBL_GATE_OPENED', 'ElementTypeParam:48:2:2', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(48, 1, 'true'),
(48, 2, 'true'),
(48, 7, 'true'),
(48, 8, 'true'),
(48, 9, 'false'),
(48, 10, 'true'),
(48, 11, 'true'),
(48, 12, 'true'),
(48, 13, 'true'),
(48, 14, 'false'),
(48, 15, 'false'),
(48, 16, 'img/grid/element_type_57.png'),
(48, 17, 'true'),
(48, 20, 'true');


INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(48, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(48, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(48, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(480004, './img/state/stateUnknown.png', 'ElementTypeState:48:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(480004, './img/state/stateUp.png', 'ElementTypeState:48:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(480004, './img/state/stateDown.png', 'ElementTypeState:48:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(480004, './img/state/stateAlarm.png', 'ElementTypeState:48:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');

