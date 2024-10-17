INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(1, 2, 'LBL_ELEMENT_TYPE_WEATHER_STATION', true, true),
(2, 2, 'LBL_ELEMENT_TYPE_CAMERA', true, true),
(11, 2, 'LBL_ELEMENT_TYPE_SOS', true, true),
(71, 2, 'LBL_ELEMENT_TYPE_PANEL', true, true);



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
(010012, 1, 2, 12, true, false, false, false, 'LBL_PRECIPITATION_TYPE', 'LBL_PRECIPITATION_TYPE', 'ElementTypeParam:1:2:19', false, false, 'center', true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010013, 1, 3, 13, true, false, false, false, 'LBL_PRECIPITATION_QUANTITY', 'LBL_PRECIPITATION_QUANTITY', 'ElementTypeParam:1:2:23', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010014, 1, 1, 14, false, false, true, false, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:1:1:1', false, false, 'left', false, false, 160, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010015, 1, 1, 15, false, false, true, false, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:1:1:2', false, false, 'left', false, false, 160, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010016, 1, 1, 16, false, false, true, false, 'LBL_GRID_FIELD_ROAD', 'LBL_GRID_FIELD_ROAD', 'ElementTypeParam:1:1:3', false, false, 'left', false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010017, 1, 1, 17, false, false, true, false, 'LBL_GRID_FIELD_SECTION', 'LBL_GRID_FIELD_SECTION', 'ElementTypeParam:1:1:4', false, false, 'left', false, false, 60, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010018, 1, 1, 18, false, false, true, false, 'LBL_PK', 'LBL_PK', 'ElementTypeParam:1:1:5', false, false, 'left', false, false, 60, NULL, true, true, NULL, NULL, NULL, NULL, NULL),

(020001, 2, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(020002, 2, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(020003, 2, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(020004, 2, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(020005, 2, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(020006, 2, 1, 6, false, false, true, false, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:2:1:1', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(020007, 2, 1, 7, false, false, true, false, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:2:1:2', false, false, 'left', false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL, NULL),

(110001, 11, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(110002, 11, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(110003, 11, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(110004, 11, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(110005, 11, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),

(710001, 71, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(710002, 71, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 180, NULL, true, true, NULL, NULL, true, NULL, NULL),
(710003, 71, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(710004, 71, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(710005, 71, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(710009, 71, 1, 9, true, false, true, false, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:71:1:1', false, false, 'left', false, false, 100, NULL, true, true, NULL, false, NULL, NULL, NULL),
(710010, 71, 1, 10, true, false, true, false, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:71:1:2', false, false, 'left', false, false, 150, NULL, true, true, NULL, false, NULL, NULL, NULL),
(710011, 71, 1, 11, true, false, true, false, 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_ENABLED', 'LBL_ELEMENT_TYPE_PARAM_TRAVEL_TIME_ENABLED', 'ElementTypeParam:71:2:10', false, false, 'left', true, false, 150, NULL, true, true, NULL, false, NULL, null, NULL);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(100307, './img/incident_level/incident_level_1.png', 'ImsIncidentLevel:1', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_1'),
(100307, './img/incident_level/incident_level_2.png', 'ImsIncidentLevel:2', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_2'),
(100307, './img/incident_level/incident_level_3.png', 'ImsIncidentLevel:3', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_3'),
(100307, './img/incident_level/incident_level_4.png', 'ImsIncidentLevel:4', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_4');

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
(1, 26, 'true'),

(2, 1, 'true'),
(2, 2, 'true'),
(2, 7, 'true'),
(2, 8, 'true'),
(2, 9, 'false'),
(2, 10, 'true'),
(2, 11, 'true'),
(2, 12, 'true'),
(2, 13, 'true'),
(2, 14, 'false'),
(2, 15, 'false'),
(2, 16, 'img/grid/element_type_02.png'),
(2, 17, 'true'),
(2, 25, 'true'),
(2, 26, 'true'),

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
(11, 16, 'img/grid/element_type_11.png'),
(11, 17, 'true'),
(11, 25, 'true'),
(11, 26, 'true'),

(71, 1, 'true'),
(71, 2, 'true'),
(71, 7, 'true'),
(71, 8, 'true'),
(71, 9, 'false'),
(71, 10, 'true'),
(71, 11, 'true'),
(71, 12, 'true'),
(71, 13, 'true'),
(71, 14, 'false'),
(71, 15, 'false'),
(71, 16, 'img/grid/element_type_71.png'),
(71, 17, 'true'),
(71, 25, 'true'),
(71, 26, 'true');

INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES
(1, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(1, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(1, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),

(2, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(2, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(2, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),

(11, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(11, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(11, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),

(71, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(71, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(71, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(010004, './img/state/stateUnknown.png', 'ElementTypeState:1:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(010004, './img/state/stateUp.png', 'ElementTypeState:1:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(010004, './img/state/stateDown.png', 'ElementTypeState:1:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(010004, './img/state/stateAlarm.png', 'ElementTypeState:1:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(010004, './img/state/stateMaintenance.png', 'ElementTypeState:1:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),

(020004, './img/state/stateUnknown.png', 'ElementTypeState:2:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(020004, './img/state/stateUp.png', 'ElementTypeState:2:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(020004, './img/state/stateDown.png', 'ElementTypeState:2:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(020004, './img/state/stateAlarm.png', 'ElementTypeState:2:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(020004, './img/state/stateMaintenance.png', 'ElementTypeState:2:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),

(110004, './img/state/stateUnknown.png', 'ElementTypeState:11:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(110004, './img/state/stateUp.png', 'ElementTypeState:11:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(110004, './img/state/stateDown.png', 'ElementTypeState:11:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(110004, './img/state/stateAlarm.png', 'ElementTypeState:11:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(110004, './img/state/stateMaintenance.png', 'ElementTypeState:11:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),

(710004, './img/state/stateUnknown.png', 'ElementTypeState:71:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(710004, './img/state/stateUp.png', 'ElementTypeState:71:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(710004, './img/state/stateDown.png', 'ElementTypeState:71:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(710004, './img/state/stateAlarm.png', 'ElementTypeState:71:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(710004, './img/state/stateMaintenance.png', 'ElementTypeState:71:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE');
