INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(1, 2, 'LBL_ELEMENT_TYPE_WEATHER_STATION', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(010001, 1, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(010002, 1, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(010003, 1, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010004, 1, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 40, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010005, 1, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010006, 1, 3, 6, true, false, false, false, 'LBL_SURFACE_TEMPERATURE', 'LBL_SURFACE_TEMPERATURE', 'ElementTypeParam:1:2:1', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010007, 1, 3, 7, true, false, false, false, 'LBL_AIR_PRESSURE', 'LBL_AIR_PRESSURE', 'ElementTypeParam:1:2:4', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010008, 1, 2, 8, true, false, false, false, 'LBL_RELATIVE_HUMIDITY', 'LBL_RELATIVE_HUMIDITY', 'ElementTypeParam:1:2:10', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010009, 1, 3, 9, true, false, false, false, 'LBL_PRECIPITATION_INTENSITY', 'LBL_PRECIPITATION_INTENSITY', 'ElementTypeParam:1:2:11', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010010, 1, 3, 10, true, false, false, false, 'LBL_GUST_WIND_SPEED', 'LBL_GUST_WIND_SPEED', 'ElementTypeParam:1:2:15', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010011, 1, 2, 11, true, false, false, false, 'LBL_GUST_WIND_DIRECTION', 'LBL_GUST_WIND_DIRECTION', 'ElementTypeParam:1:2:16', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010012, 1, 1, 12, true, false, false, false, 'LBL_PRECIPITATION_TYPE', 'LBL_PRECIPITATION_TYPE', 'ElementTypeParam:1:2:19', false, false, 'left', true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010013, 1, 3, 13, true, false, false, false, 'LBL_PRECIPITATION_QUANTITY', 'LBL_PRECIPITATION_QUANTITY', 'ElementTypeParam:1:2:23', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010014, 1, 1, 14, false, false, true, false, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:1:1:1', false, false, 'left', false, false, 160, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010015, 1, 1, 15, false, false, true, false, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:1:1:2', false, false, 'left', false, false, 160, NULL, true, true, NULL, NULL, NULL, NULL, NULL);


INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(1, 1, 'true'),
(1, 2, 'true'),
(1, 7, 'true'),
(1, 8, 'true'),
(1, 9, 'false'),
(1, 10, 'true'),
(1, 11, 'true'),
(1, 12, 'true'),
(1, 13, 'true'),
(1, 14, 'false'),
(1, 15, 'false'),
(1, 16, 'img/grid/element_type_01.png'),
(1, 17, 'true'),
(1, 25, 'true'),
(1, 26, 'true');


INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(1, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(1, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(1, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(010004, './img/state/stateUnknown.png', 'ElementTypeState:1:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(010004, './img/state/stateUp.png', 'ElementTypeState:1:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(010004, './img/state/stateDown.png', 'ElementTypeState:1:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(010004, './img/state/stateAlarm.png', 'ElementTypeState:1:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(010004, './img/state/stateMaintenance.png', 'ElementTypeState:1:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
--WS
(010012, 'LBL_GRID_FR_WITHOUT_PRECIPITATION', '0', true),
(010012, 'LBL_GRID_FR_LIQUID_PRECIPITATION', '60', true),
(010012, 'LBL_GRID_FR_SOLID_PRECIPITATION', '70', true);
