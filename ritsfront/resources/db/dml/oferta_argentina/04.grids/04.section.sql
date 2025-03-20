INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4, 2, 'LBL_ELEMENT_TYPE_SECTION', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(040001, 4, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(040002, 4, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, true, NULL),
(040003, 4, 1, 3, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(040004, 4, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL),
(040005, 4, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(040006, 4, 4, 6, true, false, false, false, 'LBL_DATE', 'LBL_DATE', 'ElementTypeParam:4:2:1', false, true, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(040007, 4, 1, 7, true, false, false, false, 'LBL_SPEED', 'LBL_SPEED', 'ElementTypeParam:4:2:9', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040008, 4, 1, 8, true, false, false, false, 'LBL_N_VEHICLES', 'LBL_N_VEHICLES', 'ElementTypeParam:4:2:4', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040009, 4, 1, 9, true, false, false, false, 'LBL_GAP', 'LBL_GAP', 'ElementTypeParam:4:2:10', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040010, 4, 1, 10, true, false, false, false, 'LBL_OCCUPANCY', 'LBL_OCCUPANCY', 'ElementTypeParam:4:2:6', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040011, 4, 1, 11, true, false, false, false, 'LBL_VEH_SPEED_1', 'LBL_VEH_SPEED_1', 'ElementTypeParam:4:2:80', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040012, 4, 1, 12, true, false, false, false, 'LBL_VEH_SPEED_2', 'LBL_VEH_SPEED_2', 'ElementTypeParam:4:2:81', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040013, 4, 1, 13, true, false, false, false, 'LBL_VEH_SPEED_3', 'LBL_VEH_SPEED_3', 'ElementTypeParam:4:2:82', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040014, 4, 1, 13, true, false, false, false, 'LBL_VEH_LENGTH_1', 'LBL_VEH_LENGTH_1', 'ElementTypeParam:4:2:60', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(040015, 4, 1, 13, true, false, false, false, 'LBL_VEH_LENGTH_2', 'LBL_VEH_LENGTH_2', 'ElementTypeParam:4:2:61', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL);


INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4, 1, 'true'),
(4, 2, 'true'),
(4, 7, 'true'),
(4, 8, 'true'),
(4, 9, 'false'),
(4, 10, 'true'),
(4, 11, 'true'),
(4, 12, 'true'),
(4, 13, 'true'),
(4, 14, 'false'),
(4, 15, 'false'),
(4, 16, 'img/grid/element_type_04.png'),
(4, 17, 'true');

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(040004, './img/state/stateUnknown.png', 'ElementTypeState:4:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(040004, './img/state/stateUp.png', 'ElementTypeState:4:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_VALID'),
(040004, './img/state/stateDown.png', 'ElementTypeState:4:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_INVALID'),
(040004, './img/state/stateAlarm.png', 'ElementTypeState:4:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');
