INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(65, 2, 'LBL_ELEMENT_TYPE_SAI', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(650001, 65, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(650002, 65, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(650003, 65, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650004, 65, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650005, 65, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650010, 65, 2, 10, true, false, false, false, 'LBL_SAI_BYPASS', 'LBL_SAI_BYPASS', 'ElementTypeParam:65:2:1', false, false, 'left', true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650011, 65, 2, 11, true, false, false, false, 'LBL_SAI_POWER_FAIL', 'LBL_SAI_POWER_FAIL', 'ElementTypeParam:65:2:2', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650012, 65, 2, 12, true, false, false, false, 'LBL_SAI_TEMPERATURE', 'LBL_SAI_TEMPERATURE', 'ElementTypeParam:65:2:3', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650013, 65, 2, 13, true, false, false, false, 'LBL_SAI_L1_PERCENT', 'LBL_SAI_L1_PERCENT', 'ElementTypeParam:65:2:4', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650014, 65, 2, 14, true, false, false, false, 'LBL_SAI_L2_PERCENT', 'LBL_SAI_L2_PERCENT', 'ElementTypeParam:65:2:5', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650015, 65, 2, 15, true, false, false, false, 'LBL_SAI_L3_PERCENT', 'LBL_SAI_L3_PERCENT', 'ElementTypeParam:65:2:6', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(650016, 65, 2, 16, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_AUTONOMY', 'LBL_ELEMENT_TYPE_PARAM_AUTONOMY', 'ElementTypeParam:65:2:7', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(65, 1, 'true'),
(65, 2, 'true'),
(65, 7, 'true'),
(65, 8, 'true'),
(65, 9, 'false'),
(65, 10, 'true'),
(65, 11, 'true'),
(65, 12, 'true'),
(65, 13, 'true'),
(65, 14, 'false'),
(65, 15, 'false'),
(65, 16, 'img/grid/element_type_65.png'),
(65, 17, 'true'),
(65, 25, 'true'),
(65, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(65, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(65, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(65, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(650004, './img/state/stateUnknown.png', 'ElementTypeState:65:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(650004, './img/state/stateUp.png', 'ElementTypeState:65:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(650004, './img/state/stateDown.png', 'ElementTypeState:65:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(650004, './img/state/stateAlarm.png', 'ElementTypeState:65:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(650004, './img/state/stateMaintenance.png', 'ElementTypeState:65:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--SAI
(650010, 'LBL_GRID_FR_NO', '0', true),
(650010, 'LBL_GRID_FR_SI', '1', true),

(650011, 'LBL_GRID_FR_NO', '0', true),
(650011, 'LBL_GRID_FR_SI', '1', true);