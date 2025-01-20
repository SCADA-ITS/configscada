INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4001, 2, 'LBL_EXT_ENTITY_C4', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014001, 4001, 1, 1, true, false, false, false, 'id', 'id', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(024001, 4001, 4, 2, true, false, false, true, 'fecha', 'fecha', 'fecha', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(034001, 4001, 4, 3, true, false, false, true, 'fecha actualizacion', 'fecha actualizacion', 'fecha_actualizacion', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(044001, 4001, 1, 4, true, true, false, false, NULL, NULL, 'categoria', false, false, null, false, false, 50, NULL, true, true, NULL, NULL, NULL, NULL),
(054001, 4001, 1, 5, true, true, false, true, 'categoria', 'categoria', 'categoria', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(064001, 4001, 1, 6, true, true, false, true, 'tipo', 'tipo', 'tipo', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(074001, 4001, 1, 7, true, false, false, true, 'titulo', 'titulo', 'titulo', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(084001, 4001, 1, 8, true, false, false, true, 'afeccion', 'afeccion', 'afeccion', false, false, null, false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL),
(094001, 4001, 1, 9, true, false, false, true, 'carretera', 'carretera', 'carretera', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(104001, 4001, 1, 10, true, false, false, true, 'localizacion', 'localizacion', 'localizacion', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(114001, 4001, 1, 11, true, true, false, true, 'estado', 'estado', 'estado', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4001, 1, 'true'),
(4001, 2, 'true'),
(4001, 7, 'false'),
(4001, 8, 'true'),
(4001, 9, 'false'),
(4001, 10, 'true'),
(4001, 11, 'true'),
(4001, 12, 'true'),
(4001, 13, 'true'),
(4001, 14, 'false'),
(4001, 15, 'false'),
(4001, 17, 'true'),
(4001, 21, 'custom');

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(044001, './img/ext_entity_type/incidencia_blanco.png', 'Red Viaria', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_C4_ROAD'),
(044001, './img/ext_entity_type/obras_blanco.png', 'Obras', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_C4_WORKS'),
(044001, './img/ext_entity_type/meteo_blanco.png', 'Meteorológicas', false, false, false, true, 'LBL_EXT_ENTITY_TYPE_C4_WEATHER');

