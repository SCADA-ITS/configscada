INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(74, 2, 'LBL_ELEMENT_TYPE_ERU', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(740001, 74, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(740002, 74, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 170, NULL, true, true, NULL, NULL, true, NULL, NULL),
(740003, 74, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(740004, 74, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(740005, 74, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(740006, 74, 2, 6, true, false, false, false, 'LBL_PERIPHERAL_CONTROL', 'LBL_PERIPHERAL_CONTROL', 'ElementTypeParam:74:2:1', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(740011, 74, 1, 11, false, false, true, false, 'LBL_ERU_TYPE', 'LBL_ERU_TYPE', 'ElementTypeParam:74:1:5', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(740012, 74, 1, 12, false, false, true, false, 'LBL_REDUNDANT_ERU_TYPE', 'LBL_REDUNDANT_ERU_TYPE', 'ElementTypeParam:74:1:6', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(740013, 74, 1, 13, false, false, true, false, 'LBL_REDUNDANT_ERU_ALIAS', 'LBL_REDUNDANT_ERU_ALIAS', 'ElementTypeParam:74:1:7', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, true, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(74, 1, 'true'),
(74, 2, 'true'),
(74, 7, 'true'),
(74, 8, 'true'),
(74, 9, 'false'),
(74, 10, 'true'),
(74, 11, 'true'),
(74, 12, 'true'),
(74, 13, 'true'),
(74, 14, 'false'),
(74, 15, 'false'),
(74, 16, 'img/grid/element_type_74.png'),
(74, 17, 'true'),
(74, 25, 'true'),
(74, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(74, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(74, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(74, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES(600004, './img/state/stateUnknown.png', 'ElementTypeState:60:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(740004, './img/state/stateUnknown.png', 'ElementTypeState:74:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(740004, './img/state/stateUp.png', 'ElementTypeState:74:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(740004, './img/state/stateDown.png', 'ElementTypeState:74:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(740004, './img/state/stateAlarm.png', 'ElementTypeState:74:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(740004, './img/state/stateMaintenance.png', 'ElementTypeState:74:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

