INSERT INTO ui.grid_field_data_types(id, name, format, description) VALUES
(1, 'string', NULL, 'string'),
(2, 'int', NULL, 'int'),
(3, 'decimal', NULL, 'decimal'),
(4, 'date', NULL, 'date');

INSERT INTO ui.grids(id, adapter_id, "label", icon, multiselect, sort, "search", "filter", pagination, enabled, visible) VALUES
(1, 2, 'LBL_DETECTORS', 'img/grid/detector.png', true, true, true, true, false, true, true),
(2, 2, 'LBL_ETDS', 'img/grid/etd.png', true, true, true, true, false, true, true),
(1000, 3, 'LBL_MENU_ITEM_USERS', 'img/grid/user.png', true, true, true, true, false, true, true),
(1001, 1, 'LBL_MENU_ITEM_ROLES', 'img/grid/role.png', true, true, true, true, false, true, true),
(1002, 4, 'LBL_MENU_ITEM_ALARM_CONFIGS', 'img/grid/alarmConfig.png', true, true, true, true, false, true, true);


INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible) VALUES
(1, 1, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 100, NULL, true, true),
(2, 1, 1, 2, true, true, false, false, '', NULL, 'state', false, false, null, false, false, 30, NULL, true, true),
(3, 1, 1, 3, true, true, false, false, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true),
(4, 1, 1, 4, true, false, false, false, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true),
(5, 1, 1, 5, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true),

(6, 2, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 100, NULL, true, true),
(7, 2, 1, 2, true, true, false, false, '', null, 'state', false, false, null, false, false, 30, NULL, true, true),
(8, 2, 1, 3, true, true, false, false, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true),
(9, 2, 1, 4, true, false, false, false, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true),
(10, 2, 1, 5, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, false, null, false, false, 200, NULL, true, true),
(11, 2, 1, 6, false, false, true, false, 'LBL_LENGTH_01', 'LBL_LENGTH_01', 'ElementTypeParam:33:2:101', false, false, 'center', false, false, 50, NULL, true, true),
(12, 2, 1, 7, false, false, true, false, 'LBL_LENGTH_02', 'LBL_LENGTH_02', 'ElementTypeParam:33:2:102', false, false, 'center', false, false, 50, NULL, true, true),
(13, 2, 1, 8, false, false, true, false, 'LBL_LENGTH_03', 'LBL_LENGTH_03', 'ElementTypeParam:33:2:103', false, false, 'center', false, false, 50, NULL, true, true),
(14, 2, 1, 9, false, false, true, false, 'LBL_BOOL_01', 'LBL_BOOL_01', 'ElementTypeParam:33:2:201', false, false, 'center', false, false, 50, NULL, true, true),
(15, 2, 1, 10, false, false, true, false, 'LBL_GROOVY_TEST_OUTPUT_1', 'LBL_GROOVY_TEST_OUTPUT_1', 'ElementTypeParam:33:2:302', false, false, 'center', false, false, 50, NULL, true, true),
(16, 2, 1, 11, false, false, true, false, 'LBL_GROOVY_TEST_OUTPUT_2', 'LBL_GROOVY_TEST_OUTPUT_2', 'ElementTypeParam:33:2:303', false, false, 'center', false, false, 50, NULL, true, true),
(17, 2, 1, 12, false, false, true, false, 'LBL_LEN_01', 'LBL_LEN_01', 'ElementTypeParam:33:2:401', false, false, 'center', false, false, 50, NULL, true, true),
(18, 2, 1, 13, false, false, true, false, 'LBL_LEN_02', 'LBL_LEN_02', 'ElementTypeParam:33:2:402', false, false, 'center', false, false, 50, NULL, true, true),
(19, 2, 1, 14, false, false, true, false, 'LBL_LEN_03', 'LBL_LEN_03', 'ElementTypeParam:33:2:403', false, false, 'center', false, false, 50, NULL, true, true),

(100001, 1000, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false),
(100002, 1000, 1, 2, true, false, false, false, 'LBL_USER', 'LBL_USER', 'name', false, false, null, false, false, 300, NULL, true, true),
(100003, 1000, 1, 3, true, false, false, false, 'LBL_ROLE', 'LBL_ROLE', 'roleAlias', false, false, null, false, false, 300, NULL, true, true),
(100004, 1000, 1, 4, true, true, false, false, 'LBL_FULL_NAME', 'LBL_FULL_NAME', 'fullName', false, false, null, false, false, 300, NULL, true, true),
(100005, 1000, 1, 5, true, true, false, false, 'LBL_EMAIL', 'LBL_EMAIL', 'email', false, false, null, false, false, 300, NULL, true, true),
(100006, 1000, 1, 6, true, true, false, false, 'LBL_LANGUAGE', 'LBL_LANGUAGE', 'language', false, false, null, false, false, 150, NULL, true, true),
(100007, 1000, 1, 7, true, true, true, false, 'LBL_COUNTRY', 'LBL_COUNTRY', 'country', false, false, null, false, false, 150, NULL, true, true),
(100008, 1000, 1, 8, true, true, true, false, 'LBL_TIMEZONE', 'LBL_TIMEZONE', 'timezone', false, false, null, false, false, 150, NULL, true, true),

(100101, 1001, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false),
(100102, 1001, 1, 2, true, false, false, false, 'LBL_ALIAS', 'LBL_ALIAS', 'alias', false, false, null, false, false, 300, NULL, true, true),
(100103, 1001, 1, 3, true, false, false, false, 'LBL_DESCRIPTION', 'LBL_DESCRIPTION', 'description', false, false, null, false, false, 400, NULL, true, true),

(100201, 1002, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false),
(100202, 1002, 1, 2, true, false, false, false, 'LBL_ALIAS', 'LBL_ALIAS', 'alias', false, false, null, false, false, 300, NULL, true, true),
(100203, 1002, 1, 3, true, false, false, false, 'LBL_ALARM_TYPE_LABEL_ALIAS', 'LBL_ALARM_TYPE_LABEL_ALIAS', 'alarmTypeLabelAlias', false, false, null, false, false, 400, NULL, true, true),
(100204, 1002, 1, 4, true, false, false, false, 'LBL_ALARM_LEVEL_LABEL_ALIAS', 'LBL_ALARM_LEVEL_LABEL_ALIAS', 'alarmLevelLabelAlias', false, false, null, false, false, 400, NULL, true, true),
(100205, 1002, 1, 5, true, false, false, false, 'LBL_ELEMENT_TYPE_LABEL_ALIAS', 'LBL_ELEMENT_TYPE_LABEL_ALIAS', 'elementTypeLabelAlias', false, false, null, false, false, 400, NULL, true, true),
(100206, 1002, 1, 6, true, false, false, false, 'LBL_ELEMENT_ALIAS', 'LBL_ELEMENT_ALIAS', 'elementAlias', false, false, null, false, false, 400, NULL, true, true);

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect) VALUES
(1, 1, 'LBL_CMD_VIEW', 'mdi mdi-pencil', true, 50001, null, null, false),
(1, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 50001, null, null, false),
(2, 1, 'LBL_CMD_VIEW', 'mdi mdi-pencil', true, 50001, null, null, false),
(2, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 50001, null, null, false),

(1000, 1, 'LBL_CMD_ADD', 'mdi mdi-plus', false, 100001, null, 2, false),
(1000, 2, 'LBL_CMD_EDIT', 'mdi mdi-pencil', true, 100002, null, 2, false),
(1000, 3, 'LBL_CMD_DELETE', 'mdi mdi-delete', false, 100003, null, 2, true),

(1001, 1, 'LBL_CMD_ADD', 'mdi mdi-plus', false, 100101, null, 2, false),
(1001, 2, 'LBL_CMD_EDIT', 'mdi mdi-pencil', true, 100102, null, 2, false),
(1001, 3, 'LBL_CMD_DELETE', 'mdi mdi-delete', false, 100103, null, 2, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled) VALUES
(2, './img/state/stateUnknown.png', 'ElementTypeState:3:0', false, false, false, true),
(2, './img/state/stateUp.png', 'ElementTypeState:3:1', false, false, false, true),
(2, './img/state/stateDown.png', 'ElementTypeState:3:2', false, false, false, true),
(2, './img/state/stateAlarm.png', 'ElementTypeState:3:3', false, false, false, true),
(7, './img/state/stateUnknown.png', 'ElementTypeState:33:0', false, false, false, true),
(7, './img/state/stateUp.png', 'ElementTypeState:33:1', false, false, false, true),
(7, './img/state/stateDown.png', 'ElementTypeState:33:2', false, false, false, true),
(7, './img/state/stateAlarm.png', 'ElementTypeState:33:3', false, false, false, true),

(100006, './img/flags/128-spain.png', 'es', false, false, false, true),
(100006, './img/flags/260-united-kingdom.png', 'en', false, false, false, true),
(100006, './img/flags/091-slovakia.png', 'sk', false, false, false, true),

(100003, NULL, NULL, false, true, false, true),
(100205, NULL, NULL, false, true, false, true);


INSERT INTO ui.grid_field_references(grid_field_id, ref_field_id, em_id, em_field, em_field_returned, enabled) VALUES
(3, 2, 'ELEMENT_TYPE_STATE', 'id', 'labelAlias', true),
(8, 7, 'ELEMENT_TYPE_STATE', 'id', 'labelAlias', true);
