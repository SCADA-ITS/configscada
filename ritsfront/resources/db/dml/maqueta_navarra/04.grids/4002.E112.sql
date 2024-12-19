INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4002, 2, 'LBL_EXT_ENTITY_E112', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014002, 4002, 1, 1, true, false, false, false, 'LBL_EXT_ENTITY_E112_COLUMN_ID', 'LBL_EXT_ENTITY_E112_COLUMN_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(024002, 4002, 1, 2, true, true, false, false, NULL, NULL, 'categoria', false, false, null, false, false, 50, NULL, true, true, NULL, NULL, NULL, NULL),
(034002, 4002, 4, 3, true, true, false, true, 'LBL_EXT_ENTITY_E112_COLUMN_DATE', 'LBL_EXT_ENTITY_E112_COLUMN_DATE', 'fecha', false, true, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(044002, 4002, 1, 4, true, true, false, true, 'LBL_EXT_ENTITY_E112_COLUMN_TYPE', 'LBL_EXT_ENTITY_E112_COLUMN_TYPE', 'tipo', false, false, null, false, false, 400, NULL, true, true, NULL, NULL, NULL, NULL),
(054002, 4002, 1, 5, true, false, false, true, 'LBL_EXT_ENTITY_E112_COLUMN_LOCATION', 'LBL_EXT_ENTITY_E112_COLUMN_LOCATION', 'localizacion', false, false, null, false, false, 500, NULL, true, true, NULL, NULL, NULL, NULL),
(064002, 4002, 1, 6, true, true, false, true, 'LBL_EXT_ENTITY_E112_COLUMN_STATE', 'LBL_EXT_ENTITY_E112_COLUMN_STATE', 'estado', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4002, 1, 'true'),
(4002, 2, 'true'),
(4002, 7, 'true'),
(4002, 8, 'true'),
(4002, 9, 'false'),
(4002, 10, 'true'),
(4002, 11, 'true'),
(4002, 12, 'true'),
(4002, 13, 'true'),
(4002, 14, 'false'),
(4002, 15, 'false'),
(4002, 16, 'img/ext_entity_type/ext_entity_112.png'),
(4002, 17, 'true'),
(4002, 21, 'custom');

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(024002, './img/ext_entity_type/incidencia_112.png', 'Accidentes', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_ACCIDENT'),
(024002, './img/ext_entity_type/meteo_112.png', 'Meteorológicas', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_WEATHER'),
(024002, './img/ext_entity_type/trafico_112.png', 'Tráfico', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_TRAFFIC'),
(024002, './img/ext_entity_type/varios_112.png', 'Varios', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_OTHERS');

