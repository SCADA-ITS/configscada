INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(87, 2, 'LBL_ELEMENT_TYPE_TANK', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(870001, 87, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL,NULL),
(870002, 87, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, true, NULL,NULL),
(870003, 87, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(870004, 87, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(870005, 87, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, null, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(870006, 87, 2, 6, true, false, false, false, 'LBL_TANK_CAPACITY_WATER', 'LBL_TANK_CAPACITY_WATER', 'ElementTypeParam:87:2:1', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(870007, 87, 2, 7, true, false, false, false, 'LBL_TANK_CAPACITY_DIESEL', 'LBL_TANK_CAPACITY_DIESEL', 'ElementTypeParam:87:2:2', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(870008, 87, 2, 8, true, false, false, false, 'LBL_TANK_CAPACITY_TOXIC', 'LBL_TANK_CAPACITY_TOXIC', 'ElementTypeParam:87:2:3', false, false, 'left', false, false, 240, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(87, 1, 'true'),
(87, 2, 'true'),
(87, 7, 'true'),
(87, 8, 'true'),
(87, 9, 'false'),
(87, 10, 'true'),
(87, 11, 'true'),
(87, 12, 'true'),
(87, 13, 'true'),
(87, 14, 'false'),
(87, 15, 'false'),
(87, 16, 'img/grid/element_type_87.png'),
(87, 17, 'true'),
(87, 20, 'true'),
(87, 25, 'true'),
(87, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(87, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(87, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(87, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES(600004, './img/state/stateUnknown.png', 'ElementTypeState:60:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(870004, './img/state/stateUnknown.png', 'ElementTypeState:87:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(870004, './img/state/stateUp.png', 'ElementTypeState:87:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(870004, './img/state/stateDown.png', 'ElementTypeState:87:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(870004, './img/state/stateAlarm.png', 'ElementTypeState:87:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(870004, './img/state/stateMaintenance.png', 'ElementTypeState:87:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

