INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(3, 2, 'LBL_ELEMENT_TYPE_DETECTOR', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(030001, 3, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(030002, 3, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, true, NULL, NULL),
(030003, 3, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030004, 3, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030005, 3, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, null, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030006, 3, 4, 6, true, false, false, false, 'LBL_DATE', 'LBL_DATE', 'ElementTypeParam:3:2:1', false, true, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030007, 3, 2, 7, true, false, false, false, 'LBL_SPEED', 'LBL_SPEED', 'ElementTypeParam:3:2:9', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030008, 3, 2, 8, true, false, false, false, 'LBL_N_VEHICLES', 'LBL_N_VEHICLES', 'ElementTypeParam:3:2:4', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030009, 3, 2, 9, true, false, false, false, 'LBL_LENGTH', 'LBL_LENGTH', 'ElementTypeParam:3:2:17', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030010, 3, 2, 10, true, false, false, false, 'LBL_GAP', 'LBL_GAP', 'ElementTypeParam:3:2:10', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030011, 3, 2, 11, true, false, false, false, 'LBL_OCCUPANCY', 'LBL_OCCUPANCY', 'ElementTypeParam:3:2:6', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030012, 3, 2, 12, true, false, false, false, 'LBL_VEH_LENGTH_1', 'LBL_VEH_LENGTH_1', 'ElementTypeParam:3:2:20', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030013, 3, 2, 13, true, false, false, false, 'LBL_VEH_LENGTH_2', 'LBL_VEH_LENGTH_2', 'ElementTypeParam:3:2:21', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030014, 3, 2, 14, true, false, false, false, 'LBL_VEH_LENGTH_3', 'LBL_VEH_LENGTH_3', 'ElementTypeParam:3:2:22', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030015, 3, 2, 15, true, false, false, false, 'LBL_VEH_SPEED_1', 'LBL_VEH_SPEED_1', 'ElementTypeParam:3:2:40', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030016, 3, 2, 16, true, false, false, false, 'LBL_VEH_SPEED_2', 'LBL_VEH_SPEED_2', 'ElementTypeParam:3:2:41', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030017, 3, 2, 17, true, false, false, false, 'LBL_VEH_SPEED_3', 'LBL_VEH_SPEED_3', 'ElementTypeParam:3:2:42', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030018, 3, 2, 18, true, false, false, false, 'LBL_VEH_SPEED_4', 'LBL_VEH_SPEED_4', 'ElementTypeParam:3:2:43', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030019, 3, 1, 19, false, false, true, false, 'LBL_ORDER', 'LBL_ORDER', 'ElementTypeParam:3:1:2', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030020, 3, 1, 20, false, false, true, false, 'LBL_PK', 'LBL_PK', 'ElementTypeParam:3:1:10', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030021, 3, 1, 21, false, false, true, false, 'LBL_GRID_FIELD_ROAD', 'LBL_GRID_FIELD_ROAD', 'ElementTypeParam:3:1:11', false, false, 'left', false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(030022, 3, 1, 22, false, false, true, false, 'LBL_GRID_FIELD_SECTION', 'LBL_GRID_FIELD_SECTION', 'ElementTypeParam:3:1:12', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(3, 1, 'true'),
(3, 2, 'true'),
(3, 7, 'true'),
(3, 8, 'true'),
(3, 9, 'false'),
(3, 10, 'true'),
(3, 11, 'true'),
(3, 12, 'true'),
(3, 13, 'true'),
(3, 14, 'false'),
(3, 15, 'false'),
(3, 16, 'img/grid/element_type_03.png'),
(3, 17, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(3, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(3, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(030004, './img/state/stateUnknown.png', 'ElementTypeState:3:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(030004, './img/state/stateUp.png', 'ElementTypeState:3:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(030004, './img/state/stateDown.png', 'ElementTypeState:3:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(030004, './img/state/stateAlarm.png', 'ElementTypeState:3:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');
