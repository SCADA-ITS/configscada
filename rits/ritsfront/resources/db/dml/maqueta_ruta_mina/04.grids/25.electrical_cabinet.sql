INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(25, 2, 'LBL_ELEMENT_TYPE_ELECTRICAL_CABINET', true, true);
INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
-- el id lo definimos como: <tipo><numero de fila 4 numeros>
(25001, 25, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(25002, 25, 1, 2, true, false, false, true, 'LBL_NAME', 'LBL_NAME', 'alias', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(25003, 25, 1, 3, true, false, false, false, 'LBL_LOCATION', 'LBL_LOCATION', 'locationAlias', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(25004, 25, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(25005, 25, 1, 5, true, true, false, true, 'LBL_STATE_LABEL', 'LBL_STATE_LABEL', 'stateLabel', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(25, 1, 'true'),
(25, 2, 'true'),
(25, 7, 'true'),
(25, 8, 'true'),
(25, 9, 'false'),
(25, 10, 'true'),
(25, 11, 'true'),
(25, 12, 'true'),
(25, 13, 'true'),
(25, 14, 'false'),
(25, 15, 'false'),
(25, 16, 'img/grid/element_type_25.png'),
(25, 17, 'true'),
(25, 20, 'true');

-- 
INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(25, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, null, null, false, false, true),
(25, 2, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),
(25, 3, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(25004, './img/state/stateUnknown.png', 'ElementTypeState:25:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(25004, './img/state/stateUp.png', 'ElementTypeState:25:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(25004, './img/state/stateDown.png', 'ElementTypeState:25:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(25004, './img/state/stateAlarm.png', 'ElementTypeState:25:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS');

