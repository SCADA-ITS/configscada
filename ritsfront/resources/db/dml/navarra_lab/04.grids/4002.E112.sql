INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4002, 2, 'LBL_EXT_ENTITY_E112', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014002, 4002, 1, 1, true, false, false, true, 'id', 'id', 'id', true, false, null, false, false, 110, NULL, true, false, NULL, NULL, NULL, NULL),
(024002, 4002, 4, 2, true, false, false, true, 'fecha', 'fecha', 'fecha', false, true, null, false, false, 175, NULL, true, true, NULL, NULL, NULL, NULL),
(034002, 4002, 1, 3, true, true, false, false, null, null, 'categoria', false, false, null, false, false, 45, NULL, true, true, NULL, NULL, NULL, NULL),
(044002, 4002, 1, 4, true, true, false, true, 'categoria', 'categoria', 'categoria', false, false, null, false, false, 45, NULL, true, true, NULL, NULL, NULL, NULL),
(054002, 4002, 1, 5, true, true, false, true, 'tipo', 'tipo', 'tipo', false, false, null, false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL),
(064002, 4002, 1, 6, true, false, false, true, 'localizacion', 'localizacion', 'localizacion', false, false, null, false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL),
(074002, 4002, 1, 7, true, true, false, true, 'situación', 'situación', 'estado112', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(084002, 4002, 1, 8, true, false, false, true, 'recursos', 'recursos', 'num_recursos', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(094002, 4002, 1, 9, true, false, false, true, 'vehiculos', 'vehiculos', 'num_vehiculos', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(104002, 4002, 1, 10, true, true, false, true, 'estado', 'estado', 'estado', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(114002, 4002, 4, 11, true, false, false, true, 'fecha finalizacion', 'fecha finalizacion', 'fecha_finalizacion', false, true, null, false, false, 175, NULL, true, true, NULL, NULL, NULL, NULL),
(124002, 4002, 1, 12, true, false, true, true, 'latitud', 'latitud', 'latitud', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(134002, 4002, 1, 13, true, false, true, true, 'longitud', 'longitud', 'longitud', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4002, 1, 'true'),
(4002, 2, 'true'),
(4002, 7, 'false'),
(4002, 8, 'true'),
(4002, 9, 'false'),
(4002, 10, 'true'),
(4002, 11, 'true'),
(4002, 12, 'true'),
(4002, 13, 'true'),
(4002, 14, 'false'),
(4002, 15, 'false'),
(4002, 17, 'true'),
(4002, 21, 'custom'),
(4002, 25, 'true'),
(4002, 26, 'true');

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(034002, './img/ext_entity_type/incidencia_112.png', 'ACCIDENTES', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_ACCIDENT'),
(034002, './img/ext_entity_type/meteo_112.png', 'METEOROLÓGICAS', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_WEATHER'),
(034002, './img/ext_entity_type/trafico_112.png', 'TRÁFICO', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_TRAFFIC'),
(034002, './img/ext_entity_type/varios_112.png', 'VARIOS', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_112_OTHERS');

