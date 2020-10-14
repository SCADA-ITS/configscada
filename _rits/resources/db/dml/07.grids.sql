INSERT INTO ui.grid_field_data_types(id, name, format, description) VALUES
(1, 'string', NULL, 'string'),
(2, 'int', NULL, 'int'),
(3, 'decimal', NULL, 'decimal'),
(4, 'date', NULL, 'date');

INSERT INTO ui.grids(id, adapter_id, "label", multiselect, sort, "search", "filter", pagination, enabled, visible) VALUES
(2, 2, 'LBL_TABLE_EQUIPMENTS', true, true, true, true, false, true, true),
(3, 2, 'LBL_TABLE_EQUIPMENTS', true, true, true, true, false, true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, ispk, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible) VALUES
(1, 2, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, null, false, false, 100, NULL, true, true),
(2, 2, 1, 2, true, true, false, false, '', NULL, 'state', false, null, false, false, 30, NULL, true, true),
(3, 2, 1, 3, true, true, false, false, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, null, true, false, 200, NULL, true, true),
(4, 2, 1, 4, true, false, false, false, 'LBL_NAME', 'LBL_NAME', 'alias', false, null, false, false, 150, NULL, true, true),
(5, 2, 1, 5, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, null, false, false, 200, NULL, true, true),

(6, 3, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, null, false, false, 100, NULL, true, true),
(7, 3, 1, 2, true, true, false, false, '', null, 'state', false, null, false, false, 30, NULL, true, true),
(8, 3, 1, 3, true, true, false, false, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, null, true, false, 200, NULL, true, true),
(9, 3, 1, 4, true, false, false, false, 'LBL_NAME', 'LBL_NAME', 'alias', false, null, false, false, 150, NULL, true, true),
(10, 3, 1, 5, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, null, false, false, 200, NULL, true, true),
(11, 3, 1, 6, false, false, true, false, 'LBL_LENGTH_01', 'LBL_LENGTH_01', 'ElementTypeParam:33:2:101', false, 'center', false, false, 50, NULL, true, true),
(12, 3, 1, 7, false, false, true, false, 'LBL_LENGTH_02', 'LBL_LENGTH_02', 'ElementTypeParam:33:2:102', false, 'center', false, false, 50, NULL, true, true),
(13, 3, 1, 8, false, false, true, false, 'LBL_LENGTH_03', 'LBL_LENGTH_03', 'ElementTypeParam:33:2:103', false, 'center', false, false, 50, NULL, true, true),
(14, 3, 1, 9, false, false, true, false, 'LBL_BOOL_01', 'LBL_BOOL_01', 'ElementTypeParam:33:2:201', false, 'center', false, false, 50, NULL, true, true),
(15, 3, 1, 10, false, false, true, false, 'LBL_GROOVY_TEST_OUTPUT_1', 'LBL_GROOVY_TEST_OUTPUT_1', 'ElementTypeParam:33:2:302', false, 'center', false, false, 50, NULL, true, true),
(16, 3, 1, 11, false, false, true, false, 'LBL_GROOVY_TEST_OUTPUT_2', 'LBL_GROOVY_TEST_OUTPUT_2', 'ElementTypeParam:33:2:303', false, 'center', false, false, 50, NULL, true, true),
(17, 3, 1, 12, false, false, true, false, 'LBL_LEN_01', 'LBL_LEN_01', 'ElementTypeParam:33:2:401', false, 'center', false, false, 50, NULL, true, true),
(18, 3, 1, 13, false, false, true, false, 'LBL_LEN_02', 'LBL_LEN_02', 'ElementTypeParam:33:2:402', false, 'center', false, false, 50, NULL, true, true),
(19, 3, 1, 14, false, false, true, false, 'LBL_LEN_03', 'LBL_LEN_03', 'ElementTypeParam:33:2:403', false, 'center', false, false, 50, NULL, true, true);



INSERT INTO ui.grid_commands(id, grid_id, "position", "label", "default", module_id, multiselect) VALUES
(1, 2, 1, 'LBL_COMMAND_ELEMENT_EDITOR', true, 5, false),
(2, 2, 2, 'LBL_OTHER_COMMAND', false, 5, false),
(3, 3, 1, 'LBL_COMMAND_ELEMENT_EDITOR', true, 5, false),
(4, 3, 2, 'LBL_OTHER_COMMAND', false, 5, false);

INSERT INTO ui.grid_field_icons(id, grid_field_id, icon, value, enabled) VALUES
(1, 2, './img/state/stateUnknown.png', '0', true),
(2, 2, './img/state/stateUp.png', '1', true),
(3, 2, './img/state/stateDown.png', '2', true),
(4, 2, './img/state/stateAlarm.png', '3', true),
(5, 7, './img/state/stateUnknown.png', '0', true),
(6, 7, './img/state/stateUp.png', '1', true),
(7, 7, './img/state/stateDown.png', '2', true),
(8, 7, './img/state/stateAlarm.png', '3', true);
