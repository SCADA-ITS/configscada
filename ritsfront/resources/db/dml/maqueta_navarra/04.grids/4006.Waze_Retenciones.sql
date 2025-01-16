INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4006, 2, 'LBL_EXT_ENTITY_WAZE_JAMS', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014006, 4006, 1, 1, true, false, false, false, 'id', 'id', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(024006, 4006, 1, 2, true, true, false, true, 'circulacion', 'circulacion', 'circulacion', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(034006, 4006, 1, 3, true, true, false, true, 'calle', 'calle', 'calle', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(044006, 4006, 1, 4, true, false, false, true, 'ciudad', 'ciudad', 'ciudad', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(054006, 4006, 1, 5, true, false, false, true, 'pais', 'pais', 'pais', false, false, null, false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL),
(064006, 4006, 1, 6, true, false, false, true, 'fecha publicacion', 'fecha publicacion', 'fecha_publicacion', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(074006, 4006, 1, 7, true, false, false, true, 'velocidad', 'velocidad', 'velocidad_km_h', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(084006, 4006, 1, 8, true, false, false, true, 'distancia', 'distancia', 'distancia_m', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(094006, 4006, 1, 9, true, false, false, true, 'retraso', 'retraso', 'retraso_s', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(104006, 4006, 1, 10, true, true, false, true, 'comienzo', 'comienzo', 'comienzo', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(114006, 4006, 1, 11, true, true, false, true, 'fin', 'fin', 'fin', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(124006, 4006, 1, 12, true, true, false, true, 'estado', 'estado', 'estado', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4006, 1, 'true'),
(4006, 2, 'true'),
(4006, 7, 'false'),
(4006, 8, 'true'),
(4006, 9, 'false'),
(4006, 10, 'true'),
(4006, 11, 'true'),
(4006, 12, 'true'),
(4006, 13, 'true'),
(4006, 14, 'false'),
(4006, 15, 'false'),
(4006, 17, 'true'),
(4006, 21, 'custom');

