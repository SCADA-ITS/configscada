INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4, 2, 'LBL_ELEMENT_TYPE_SECTION', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(040001, 4, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(040002, 4, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, true, NULL, NULL),
(040003, 4, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040004, 4, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040005, 4, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, null, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040006, 4, 4, 6, true, false, false, false, 'LBL_DATE', 'LBL_DATE', 'ElementTypeParam:4:2:1', false, true, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040008, 4, 2, 8, true, false, false, false, 'LBL_CURRENT_DIRECTION', 'LBL_CURRENT_DIRECTION', 'ElementTypeParam:4:2:3', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040009, 4, 2, 9, true, false, false, false, 'LBL_N_VEHICLES', 'LBL_N_VEHICLES', 'ElementTypeParam:4:2:4', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040010, 4, 2, 10, true, false, false, false, 'LBL_INTENSITY', 'LBL_INTENSITY', 'ElementTypeParam:4:2:5', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040011, 4, 2, 11, true, false, false, false, 'LBL_OCCUPANCY', 'LBL_OCCUPANCY', 'ElementTypeParam:4:2:6', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040012, 4, 1, 12, true, false, false, false, 'LBL_CONGESTION', 'LBL_CONGESTION', 'ElementTypeParam:4:2:7', false, false, 'left', true, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040013, 4, 1, 13, true, false, false, false, 'LBL_KAMIKAZE', 'LBL_KAMIKAZE', 'ElementTypeParam:4:2:8', false, false, 'left', true, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040014, 4, 2, 14, true, false, false, false, 'LBL_SPEED', 'LBL_SPEED', 'ElementTypeParam:4:2:9', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040015, 4, 2, 15, true, false, false, false, 'LBL_GAP', 'LBL_GAP', 'ElementTypeParam:4:2:10', false, false, 'left', false, false, 140, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040016, 4, 2, 16, true, false, false, false, 'LBL_LOS', 'LBL_LOS', 'ElementTypeParam:4:2:12', false, false, 'left', false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040017, 4, 2, 17, true, false, false, false, 'LBL_LOS_PERCENT', 'LBL_LOS_PERCENT', 'ElementTypeParam:4:2:13', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040018, 4, 2, 18, true, false, false, false, 'LBL_LENGTH', 'LBL_LENGTH', 'ElementTypeParam:4:2:18', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040019, 4, 2, 19, true, false, false, false, 'LBL_VEH_LENGTH_1', 'LBL_VEH_LENGTH_1', 'ElementTypeParam:4:2:60', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040020, 4, 2, 20, true, false, false, false, 'LBL_VEH_LENGTH_2', 'LBL_VEH_LENGTH_2', 'ElementTypeParam:4:2:61', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040021, 4, 2, 21, true, false, false, false, 'LBL_VEH_SPEED_1', 'LBL_VEH_SPEED_1', 'ElementTypeParam:4:2:80', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040022, 4, 2, 22, true, false, false, false, 'LBL_VEH_SPEED_2', 'LBL_VEH_SPEED_2', 'ElementTypeParam:4:2:81', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040023, 4, 2, 23, true, false, false, false, 'LBL_VEH_SPEED_3', 'LBL_VEH_SPEED_3', 'ElementTypeParam:4:2:82', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040024, 4, 2, 24, true, false, false, false, 'LBL_VEH_LENGTH_1_PERCENT', 'LBL_VEH_LENGTH_1_PERCENT', 'ElementTypeParam:4:2:100', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040025, 4, 2, 25, true, false, false, false, 'LBL_VEH_LENGTH_2_PERCENT', 'LBL_VEH_LENGTH_2_PERCENT', 'ElementTypeParam:4:2:101', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040026, 4, 2, 26, true, false, false, false, 'LBL_VEH_SPEED_1_PERCENT', 'LBL_VEH_SPEED_1_PERCENT', 'ElementTypeParam:4:2:120', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040027, 4, 2, 27, true, false, false, false, 'LBL_VEH_SPEED_2_PERCENT', 'LBL_VEH_SPEED_2_PERCENT', 'ElementTypeParam:4:2:121', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(040028, 4, 2, 28, true, false, false, false, 'LBL_VEH_SPEED_3_PERCENT', 'LBL_VEH_SPEED_3_PERCENT', 'ElementTypeParam:4:2:122', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL);



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
(4, 17, 'true'),
(4, 25, 'true'),
(4, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(4, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(4, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(4, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(040004, './img/state/stateUnknown.png', 'ElementTypeState:4:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(040004, './img/state/stateUp.png', 'ElementTypeState:4:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_VALID'),
(040004, './img/state/stateDown.png', 'ElementTypeState:4:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_INVALID'),
(040004, './img/state/stateAlarm.png', 'ElementTypeState:4:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(040004, './img/state/stateMaintenance.png', 'ElementTypeState:4:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');


INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--Seccion
(040008, 'LBL_GRID_FR_DIRECTA', '0', true),
(040008, 'LBL_GRID_FR_INVERSA', '1', true),

(040012, 'LBL_GRID_FR_NO', '0', true),
(040012, 'LBL_GRID_FR_SI', '1', true),

(040013, 'LBL_GRID_FR_NO', '0', true),
(040013, 'LBL_GRID_FR_SI', '1', true);