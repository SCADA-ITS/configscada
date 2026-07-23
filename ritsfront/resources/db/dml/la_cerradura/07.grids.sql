INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(1, 2, 'LBL_ELEMENT_TYPE_WEATHER_STATION', true, true),
(2, 2, 'LBL_ELEMENT_TYPE_CAMERA', true, true),
(11, 2, 'LBL_ELEMENT_TYPE_SOS', true, true),
(13, 2, 'LBL_ELEMENT_TYPE_SEM', true, true),
(18, 2, 'LBL_ELEMENT_TYPE_ANEM_INT', true, true),
(19, 2, 'LBL_ELEMENT_TYPE_CO', true, true),
(20, 2, 'LBL_ELEMENT_TYPE_NO', true, true),
(21, 2, 'LBL_ELEMENT_TYPE_OPAC', true, true),
(53, 2, 'LBL_ELEMENT_TYPE_SEM_2_LUCES', true, true),
(71, 2, 'LBL_ELEMENT_TYPE_PANEL', true, true);



INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>

(010001, 1, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(010002, 1, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(010003, 1, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010004, 1, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 40, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010005, 1, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010006, 1, 1, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_AIR_PRESSURE', 'LBL_ELEMENT_TYPE_PARAM_AIR_PRESSURE_DESC', 'ElementTypeParam:1:2:4', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010007, 1, 3, 7, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_RELATIVE_HUMIDITY', 'LBL_ELEMENT_TYPE_PARAM_RELATIVE_HUMIDITY_DESC', 'ElementTypeParam:1:2:10', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010008, 1, 3, 8, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_WS_WIND_SPEED', 'LBL_ELEMENT_TYPE_PARAM_WS_WIND_SPEED_DESC', 'ElementTypeParam:1:2:26', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(010009, 1, 3, 9, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_WS_WIND_DIRECTION', 'LBL_ELEMENT_TYPE_PARAM_WS_WIND_DIRECTION_DESC', 'ElementTypeParam:1:2:27', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(0100010, 1, 3, 10, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_AIR_TEMPERATURE', 'LBL_ELEMENT_TYPE_PARAM_AIR_TEMPERATURE_DESC', 'ElementTypeParam:1:2:17', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(0100011, 1, 3, 11, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_DEW_POINT_TEMPERATURE', 'LBL_ELEMENT_TYPE_PARAM_DEW_POINT_TEMPERATURE_DESC', 'ElementTypeParam:1:2:18', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(0100012, 1, 1, 12, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY', 'LBL_ELEMENT_TYPE_PARAM_VISIBILITY_DESC', 'ElementTypeParam:1:2:20', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(0100013, 1, 1, 13, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_WIND_TYPE', 'LBL_ELEMENT_TYPE_PARAM_WIND_TYPE_DESC', 'ElementTypeParam:1:2:28', false, false, 'left', true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(0100014, 1, 3, 14, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_PRECIPITATION_INTENSITY', 'LBL_ELEMENT_TYPE_PARAM_PRECIPITATION_INTENSITY_DESC', 'ElementTypeParam:1:2:11', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(0100015, 1, 1, 15, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_PRECIPITATION_TYPE', 'LBL_ELEMENT_TYPE_PARAM_PRECIPITATION_TYPE_DESC', 'ElementTypeParam:1:2:19', false, false, 'left', true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(0100016, 1, 3, 16, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_PRECIPITATION_QUANTITY', 'LBL_ELEMENT_TYPE_PARAM_PRECIPITATION_QUANTITY_DESC', 'ElementTypeParam:1:2:23', false, false, 'left', false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),


(020001, 2, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(020002, 2, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 170, NULL, true, true, NULL, NULL, true, NULL, NULL),
(020003, 2, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(020004, 2, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(020005, 2, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(020006, 2, 1, 6, false, false, true, true, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:2:1:1', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(020007, 2, 1, 7, false, false, true, true, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:2:1:2', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),

(110001, 11, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(110002, 11, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(110003, 11, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(110004, 11, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(110005, 11, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(110006, 11, 1, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_VOL_AUDIO', 'LBL_ELEMENT_TYPE_PARAM_VOL_AUDIO_DESC', 'ElementTypeParam:11:2:4', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),
(110007, 11, 1, 7, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_MEG_STATE', 'LBL_ELEMENT_TYPE_PARAM_MEG_STATE_DESC', 'ElementTypeParam:11:2:15', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),
(110008, 11, 1, 8, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_VOL_MEG', 'LBL_ELEMENT_TYPE_PARAM_VOL_MEG_DESC', 'ElementTypeParam:11:2:16', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),
(110011, 11, 1, 9, false, false, true, true, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:11:1:1', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(110012, 11, 1, 10, false, false, true, true, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:11:1:2', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),

(130001, 13, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(130002, 13, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(130003, 13, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(130004, 13, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(130005, 13, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(130006, 13, 1, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_LIGHT', 'LBL_ELEMENT_TYPE_PARAM_LIGHT_DESC', 'ElementTypeParam:13:2:1', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),

(180001, 18, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(180002, 18, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(180003, 18, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(180004, 18, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(180005, 18, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(180006, 18, 3, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_WIND_SPEED', 'LBL_ELEMENT_TYPE_PARAM_WIND_SPEED_DESC', 'ElementTypeParam:18:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL,NULL,0),
(180007, 18, 1, 7, false, false, true, true, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:18:1:1', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(180008, 18, 1, 8, false, false, true, true, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:18:1:2', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),

(190001, 19, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(190002, 19, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(190003, 19, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(190004, 19, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(190005, 19, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(190006, 19, 3, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_CO_CONCENTRATION', 'LBL_ELEMENT_TYPE_PARAM_CO_CONCENTRATION_DESC', 'ElementTypeParam:19:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 2),
(190007, 19, 1, 7, false, false, true, true, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:19:1:1', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(190008, 19, 1, 8, false, false, true, true, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:19:1:2', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),

(200001, 20, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(200002, 20, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(200003, 20, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(200004, 20, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(200005, 20, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(200006, 20, 3, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_NO_CONCENTRATION', 'LBL_ELEMENT_TYPE_PARAM_NO_CONCENTRATION_DESC', 'ElementTypeParam:20:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 2),
(200007, 20, 1, 7, false, false, true, true, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:20:1:1', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),


(210001, 21, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(210002, 21, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(210003, 21, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(210004, 21, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(210005, 21, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(210006, 21, 3, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_OPAC_CONCENTRATION', 'LBL_ELEMENT_TYPE_PARAM_OPAC_CONCENTRATION_DESC', 'ElementTypeParam:21:2:1', false, false, 'left', false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 2),
(210007, 21, 1, 7, false, false, true, true, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:21:1:1', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),
(210008, 21, 1, 8, false, false, true, true, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:21:1:2', false, false, 'left', false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL,NULL),

(530001, 53, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(530002, 53, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, true, NULL, NULL),
(530003, 53, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(530004, 53, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(530005, 53, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(530006, 53, 1, 6, true, false, false, true, 'LBL_ELEMENT_TYPE_PARAM_LIGHT_AMBER', 'LBL_ELEMENT_TYPE_PARAM_LIGHT_AMBER_DESC', 'ElementTypeParam:53:2:1', false, false, 'left', true, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, 0),

(710001, 71, 1, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(710002, 71, 1, 2, true, false, false, true, 'LBL_GRID_FIELD_NAME', 'LBL_GRID_FIELD_NAME', 'alias', false, false, NULL, false, false, 180, NULL, true, true, NULL, NULL, true, NULL, NULL),
(710003, 71, 1, 3, true, false, false, true, 'LBL_GRID_FIELD_DESCRIPTION', 'LBL_GRID_FIELD_DESCRIPTION', 'description', false, false, NULL, false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(710004, 71, 1, 4, true, true, false, false, NULL, NULL, 'state', false, false, NULL, false, false, 30, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(710005, 71, 1, 5, true, true, false, true, 'LBL_GRID_FIELD_STATE_LABEL', 'LBL_GRID_FIELD_STATE_LABEL', 'stateLabel', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(710009, 71, 1, 9, false, false, true, true, 'LBL_GRID_FIELD_BRAND', 'LBL_GRID_FIELD_BRAND', 'ElementTypeParam:71:1:1', false, false, 'left', false, false, 150, NULL, true, true, NULL, false, NULL, NULL, NULL),
(710010, 71, 1, 10, false, false, true, true, 'LBL_GRID_FIELD_MODEL', 'LBL_GRID_FIELD_MODEL', 'ElementTypeParam:71:1:2', false, false, 'left', true, false, 100, NULL, true, true, NULL, false, NULL, null, NULL),
(710011, 71, 1, 11, false, false, true, true, 'LBL_ELEMENT_TYPE_PARAM_PROTOCOL', 'LBL_ELEMENT_TYPE_PARAM_PROTOCOL', 'ElementTypeParam:71:1:3', false, false, 'left', true, false, 100, NULL, true, true, NULL, false, NULL, null, NULL);


UPDATE ui.grid_fields
SET enabled=false, visible=false
WHERE id in(101205,101209,101210);


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

(13, 1, 'true'),
(13, 2, 'true'),
(13, 7, 'true'),
(13, 8, 'true'),
(13, 9, 'false'),
(13, 10, 'true'),
(13, 11, 'true'),
(13, 12, 'true'),
(13, 13, 'true'),
(13, 14, 'false'),
(13, 15, 'false'),
(13, 16, 'img/grid/element_type_13.png'),
(13, 17, 'true'),
(13, 25, 'true'),
(13, 26, 'true'),

(18, 1, 'true'),
(18, 2, 'true'),
(18, 7, 'true'),
(18, 8, 'true'),
(18, 9, 'false'),
(18, 10, 'true'),
(18, 11, 'true'),
(18, 12, 'true'),
(18, 13, 'true'),
(18, 14, 'false'),
(18, 15, 'false'),
(18, 16, 'img/grid/element_type_18.png'),
(18, 17, 'true'),
(18, 25, 'true'),
(18, 26, 'true'),

(19, 1, 'true'),
(19, 2, 'true'),
(19, 7, 'true'),
(19, 8, 'true'),
(19, 9, 'false'),
(19, 10, 'true'),
(19, 11, 'true'),
(19, 12, 'true'),
(19, 13, 'true'),
(19, 14, 'false'),
(19, 15, 'false'),
(19, 16, 'img/grid/element_type_19.png'),
(19, 17, 'true'),
(19, 25, 'true'),
(19, 26, 'true'),

(20, 1, 'true'),
(20, 2, 'true'),
(20, 7, 'true'),
(20, 8, 'true'),
(20, 9, 'false'),
(20, 10, 'true'),
(20, 11, 'true'),
(20, 12, 'true'),
(20, 13, 'true'),
(20, 14, 'false'),
(20, 15, 'false'),
(20, 16, 'img/grid/element_type_20.png'),
(20, 17, 'true'),
(20, 25, 'true'),
(20, 26, 'true'),

(21, 1, 'true'),
(21, 2, 'true'),
(21, 7, 'true'),
(21, 8, 'true'),
(21, 9, 'false'),
(21, 10, 'true'),
(21, 11, 'true'),
(21, 12, 'true'),
(21, 13, 'true'),
(21, 14, 'false'),
(21, 15, 'false'),
(21, 16, 'img/grid/element_type_21.png'),
(21, 17, 'true'),
(21, 25, 'true'),
(21, 26, 'true'),

(53, 1, 'true'),
(53, 2, 'true'),
(53, 7, 'true'),
(53, 8, 'true'),
(53, 9, 'false'),
(53, 10, 'true'),
(53, 11, 'true'),
(53, 12, 'true'),
(53, 13, 'true'),
(53, 14, 'false'),
(53, 15, 'false'),
(53, 16, 'img/grid/element_type_53.png'),
(53, 17, 'true'),
(53, 25, 'true'),
(53, 26, 'true'),

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

(13, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(13, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(13, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),

(18, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(18, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(18, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, NULL, NULL, false, false, false),

(19, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(19, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(19, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, NULL, NULL, false, false, false),

(20, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(20, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(20, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, NULL, NULL, false, false, false),

(21, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(21, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(21, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),

(53, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(53, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(53, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),

(71, 1, 'LBL_CMD_VIEW', 'mdi mdi-eye', true, 101401, NULL, NULL, false, false, false),
(71, 2, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200004, null, null, false, false, true),
(71, 3, 'LBL_CMD_SHOW_ALARMS', 'mdi mdi-bell', false, 100501, null, 2, true, true, true),


(1003, 6, 'LBL_CMD_SHOW_IN_MAP', 'mdi mdi-map-search', false, 200003, null, 2, false, false, true);

UPDATE ui.grid_commands
SET args='[{"command": "CommandElementType:2:2000", "ids": "CommandElementTypeParam:2:2000:1"}]'
WHERE grid_id = 1012 and label = ('LBL_SEND_FAN_START_DIRECT');

UPDATE ui.grid_commands
SET args='[{"command": "CommandElementType:3:2000", "ids": "CommandElementTypeParam:3:2000:1"}]'
WHERE grid_id = 1012 and label = ('LBL_SEND_FAN_START_REVERSE');

DELETE FROM ui.grid_commands
WHERE grid_id = 1012 AND label in ('LBL_SEND_FAN_RESET_PARTIAL_HOURS', 'LBL_SEND_FAN_RESET_TOTAL_HOURS');

DELETE FROM ui.grid_commands
WHERE grid_id = 1012
  AND position = 6;
  
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

(130004, './img/state/stateUnknown.png', 'ElementTypeState:13:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(130004, './img/state/stateUp.png', 'ElementTypeState:13:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(130004, './img/state/stateDown.png', 'ElementTypeState:13:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(130004, './img/state/stateAlarm.png', 'ElementTypeState:13:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(130004, './img/state/stateMaintenance.png', 'ElementTypeState:13:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),


(180004, './img/state/stateUnknown.png', 'ElementTypeState:18:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(180004, './img/state/stateUp.png', 'ElementTypeState:18:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(180004, './img/state/stateDown.png', 'ElementTypeState:18:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(180004, './img/state/stateAlarm.png', 'ElementTypeState:18:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(180004, './img/state/stateMaintenance.png', 'ElementTypeState:18:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),

(190004, './img/state/stateUnknown.png', 'ElementTypeState:19:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(190004, './img/state/stateUp.png', 'ElementTypeState:19:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(190004, './img/state/stateDown.png', 'ElementTypeState:19:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(190004, './img/state/stateAlarm.png', 'ElementTypeState:19:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(190004, './img/state/stateMaintenance.png', 'ElementTypeState:19:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),

(200004, './img/state/stateUnknown.png', 'ElementTypeState:20:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(200004, './img/state/stateUp.png', 'ElementTypeState:20:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(200004, './img/state/stateDown.png', 'ElementTypeState:20:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(200004, './img/state/stateAlarm.png', 'ElementTypeState:20:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(200004, './img/state/stateMaintenance.png', 'ElementTypeState:20:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),

(210004, './img/state/stateUnknown.png', 'ElementTypeState:21:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(210004, './img/state/stateUp.png', 'ElementTypeState:21:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(210004, './img/state/stateDown.png', 'ElementTypeState:21:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(210004, './img/state/stateAlarm.png', 'ElementTypeState:21:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(210004, './img/state/stateMaintenance.png', 'ElementTypeState:21:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),


(530004, './img/state/stateUnknown.png', 'ElementTypeState:53:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(530004, './img/state/stateUp.png', 'ElementTypeState:53:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(530004, './img/state/stateDown.png', 'ElementTypeState:53:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(530004, './img/state/stateAlarm.png', 'ElementTypeState:53:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(530004, './img/state/stateMaintenance.png', 'ElementTypeState:53:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),


(710004, './img/state/stateUnknown.png', 'ElementTypeState:71:0', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UNKNOWN'),
(710004, './img/state/stateUp.png', 'ElementTypeState:71:1', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_UP'),
(710004, './img/state/stateDown.png', 'ElementTypeState:71:2', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_DOWN'),
(710004, './img/state/stateAlarm.png', 'ElementTypeState:71:3', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_ALARMS'),
(710004, './img/state/stateMaintenance.png', 'ElementTypeState:71:4', false, false, false, true, 'LBL_ELEMENT_TYPE_STATE_MAINTENANCE'),


(100307, './img/incident_level/ausol/incident_level_1.png', 'ImsIncidentLevel:1', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_1'),
(100307, './img/incident_level/ausol/incident_level_2.png', 'ImsIncidentLevel:2', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_LEVE'),
(100307, './img/incident_level/ausol/incident_level_3.png', 'ImsIncidentLevel:3', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_MEDIO'),
(100307, './img/incident_level/ausol/incident_level_4.png', 'ImsIncidentLevel:4', false, false, false, true, 'LBL_ICON_IMS_INCIDENT_LEVEL_GRAVE');




INSERT INTO ui.grid_field_replacements(grid_field_id, "label", value, enabled) VALUES
(0100013, 'LBL_GRID_FIELD_REPLACEMENTS_NORMAL', '1', true),
(0100013, 'LBL_GRID_FIELD_REPLACEMENTS_RACHEADO', '2', true),
(0100015, 'LBL_GRID_FIELD_REPLACEMENTS_NINGUNA', '0', true),
(0100015, 'LBL_GRID_FIELD_REPLACEMENTS_LLUVIA', '1', true),
(0100015, 'LBL_GRID_FIELD_REPLACEMENTS_LLUVIA_NIEVE', '2', true),
(0100015, 'LBL_GRID_FIELD_REPLACEMENTS_NIEVE', '3', true),
(0100015, 'LBL_GRID_FIELD_REPLACEMENTS_GRANIZO', '4', true),

(110007, 'LBL_GRID_FIELD_REPLACEMENTS_ENABLED', '255', true),
(110007, 'LBL_GRID_FIELD_REPLACEMENTS_DISABLED', '0', true),

(130006, 'LBL_GRID_FIELD_REPLACEMENTS_APAGADO', '7', true),
(130006, 'LBL_GRID_FIELD_REPLACEMENTS_ROJO', '1', true),
(130006, 'LBL_GRID_FIELD_REPLACEMENTS_AMBAR', '0', true),
(130006, 'LBL_GRID_FIELD_REPLACEMENTS_VERDE', '4', true),

(530006, 'LBL_GRID_FIELD_REPLACEMENTS_APAGADO', '0', true),
(530006, 'LBL_GRID_FIELD_REPLACEMENTS_ROJO', '1', true);

