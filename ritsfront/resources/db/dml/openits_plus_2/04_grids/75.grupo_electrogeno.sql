INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(75, 2, 'LBL_ELEMENT_TYPE_GE', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(750001, 75, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(750002, 75, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(750003, 75, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750004, 75, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750005, 75, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750006, 75, 2, 6, true, false, false, false, 'LBL_GRP_CORRIENTE_FASE_1', 'LBL_GRP_CORRIENTE_FASE_1', 'ElementTypeParam:75:2:1', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750007, 75, 2, 7, true, false, false, false, 'LBL_GRP_CORRIENTE_FASE_2', 'LBL_GRP_CORRIENTE_FASE_2', 'ElementTypeParam:75:2:2', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750008, 75, 2, 8, true, false, false, false, 'LBL_GRP_CORRIENTE_FASE_3', 'LBL_GRP_CORRIENTE_FASE_3', 'ElementTypeParam:75:2:3', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750009, 75, 2, 9, true, false, false, false, 'LBL_GRP_FRECUENCIA_SALIDA', 'LBL_GRP_FRECUENCIA_SALIDA', 'ElementTypeParam:75:2:4', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750010, 75, 2, 10, true, false, false, false, 'LBL_GRP_HORAS_TOT_MARCHA', 'LBL_GRP_HORAS_TOT_MARCHA', 'ElementTypeParam:75:2:5', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750011, 75, 2, 11, true, false, false, false, 'LBL_GRP_RPM_GENERADOR', 'LBL_GRP_RPM_GENERADOR', 'ElementTypeParam:75:2:6', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750012, 75, 2, 12, true, false, false, false, 'LBL_GRP_TENSION_BATERIA', 'LBL_GRP_TENSION_BATERIA', 'ElementTypeParam:75:2:7', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750013, 75, 2, 13, true, false, false, false, 'LBL_GRP_TENSION_SALIDA_GEN', 'LBL_GRP_TENSION_SALIDA_GEN', 'ElementTypeParam:75:2:8', false, false, 'left', false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750014, 75, 1, 14, true, false, false, false, 'LBL_GRP_MODO_OPERACION_OFF', 'LBL_GRP_MODO_OPERACION_OFF', 'ElementTypeParam:75:2:9', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750015, 75, 1, 15, true, false, false, false, 'LBL_GRP_MODO_OPERACION_MAN', 'LBL_GRP_MODO_OPERACION_MAN', 'ElementTypeParam:75:2:10', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(750016, 75, 1, 16, true, false, false, false, 'LBL_GRP_MODO_OPERACION_AUT', 'LBL_GRP_MODO_OPERACION_AUT', 'ElementTypeParam:75:2:11', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(75, 1, 'true'),
(75, 2, 'true'),
(75, 7, 'true'),
(75, 8, 'true'),
(75, 9, 'false'),
(75, 10, 'true'),
(75, 11, 'true'),
(75, 12, 'true'),
(75, 13, 'true'),
(75, 14, 'false'),
(75, 15, 'false'),
(75, 16, 'img/grid/element_type_75.png'),
(75, 17, 'true'),
(75, 25, 'true'),
(75, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(75, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(75, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(75, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(750004, './img/state/stateUnknown.png', 'ElementTypeState:75:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(750004, './img/state/stateUp.png', 'ElementTypeState:75:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(750004, './img/state/stateDown.png', 'ElementTypeState:75:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(750004, './img/state/stateAlarm.png', 'ElementTypeState:75:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(750004, './img/state/stateMaintenance.png', 'ElementTypeState:75:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');

