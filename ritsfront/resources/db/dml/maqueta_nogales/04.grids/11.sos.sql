INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(11, 2, 'LBL_GRID_SOS', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(110001, 11, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(110002, 11, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(110003, 11, 1, 3, true, false, false, false, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(110004, 11, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),
(110005, 11, 1, 5, true, true, false, false, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(110006, 11, 2, 6, true, false, false, false, 'LBL_VOL_MIC', 'LBL_VOL_MIC', 'ElementTypeParam:11:2:1', false, false, 'left', false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL),
(110007, 11, 2, 7, true, false, false, false, 'LBL_VOL_SOS', 'LBL_VOL_SOS', 'ElementTypeParam:11:2:4', false, false, 'left', false, false, 130, NULL, true, true, NULL, NULL, NULL, NULL),
(110008, 11, 2, 8, true, false, false, false, 'LBL_TEST_AUDIO', 'LBL_TEST_AUDIO', 'ElementTypeParam:11:2:5', false, false, 'left', true, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(110009, 11, 1, 9, true, false, false, false, 'LBL_LAST_DAY_TEST', 'LBL_LAST_DAY_TEST', 'ElementTypeParam:11:2:6', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(110010, 11, 1, 10, true, false, false, false, 'LBL_LAST_MONTH_TEST', 'LBL_LAST_MONTH_TEST', 'ElementTypeParam:11:2:7', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(110011, 11, 1, 11, true, false, false, false, 'LBL_LAST_YEAR_TEST', 'LBL_LAST_YEAR_TEST', 'ElementTypeParam:11:2:8', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(110012, 11, 1, 12, true, false, false, false, 'LBL_LAST_HOUR_TEST', 'LBL_LAST_HOUR_TEST', 'ElementTypeParam:11:2:9', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(110013, 11, 1, 13, true, false, false, false, 'LBL_LAST_MINUTE_TEST', 'LBL_LAST_MINUTE_TEST', 'ElementTypeParam:11:2:10', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(110014, 11, 1, 14, true, false, false, false, 'LBL_LAST_SECOND_TEST', 'LBL_LAST_SECOND_TEST', 'ElementTypeParam:11:2:11', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(11, 1, 'true'),
(11, 2, 'true'),
(11, 7, 'true'),
(11, 8, 'true'),
(11, 9, 'false'),
(11, 10, 'true'),
(11, 11, 'true'),
(11, 12, 'true'),
(11, 13, 'true'),
(11, 14, 'false'),
(11, 15, 'false'),
(11, 16, 'img/grid/sos.png'),
(11, 17, 'true'),
(11, 25, 'true'),
(11, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(11, 1, 'LBL_CMD_VIEW_EQUIPMENT', 'mdi mdi-eye', true, 101401, NULL, 2, false, false, false),
(11, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(11, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(110004, './img/state/stateUnknown.png', 'ElementTypeState:11:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(110004, './img/state/stateUp.png', 'ElementTypeState:11:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(110004, './img/state/stateDown.png', 'ElementTypeState:11:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(110004, './img/state/stateAlarm.png', 'ElementTypeState:11:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
(110008, 'LBL_OK', '1', true), 
(110008, 'LBL_NOK', '0', true);

