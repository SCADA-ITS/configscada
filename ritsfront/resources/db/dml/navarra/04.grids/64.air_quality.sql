INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(64, 2, 'LBL_ELEMENT_TYPE_AIR_QUALITY', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(640001, 64, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(640002, 64, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
--(020003, 2, 1, 3, true, false, false, false, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(640004, 64, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(640005, 64, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(640006, 64, 3, 6, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_CO2', 'LBL_ELEMENT_TYPE_PARAM_CO2', 'ElementTypeParam:64:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(640007, 64, 3, 7, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_NOX', 'LBL_ELEMENT_TYPE_PARAM_NOX', 'ElementTypeParam:64:2:2', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(640008, 64, 3, 8, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_PM2_5', 'LBL_ELEMENT_TYPE_PARAM_PM2_5', 'ElementTypeParam:64:2:3', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(640009, 64, 3, 9, true, false, false, false, 'LBL_ELEMENT_TYPE_PARAM_PM10', 'LBL_ELEMENT_TYPE_PARAM_PM10', 'ElementTypeParam:64:2:4', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(64, 1, 'true'),
(64, 2, 'true'),
(64, 7, 'true'),
(64, 8, 'true'),
(64, 9, 'false'),
(64, 10, 'true'),
(64, 11, 'true'),
(64, 12, 'true'),
(64, 13, 'true'),
(64, 14, 'false'),
(64, 15, 'false'),
(64, 16, 'img/grid/element_type_64.png'),
(64, 17, 'true');


INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(64, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(64, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(64, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(640004, './img/state/stateUnknown.png', 'ElementTypeState:64:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(640004, './img/state/stateUp.png', 'ElementTypeState:64:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(640004, './img/state/stateDown.png', 'ElementTypeState:64:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(640004, './img/state/stateAlarm.png', 'ElementTypeState:64:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');

