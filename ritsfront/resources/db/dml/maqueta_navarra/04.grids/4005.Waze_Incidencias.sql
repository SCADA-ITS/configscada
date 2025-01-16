INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4005, 2, 'LBL_EXT_ENTITY_WAZE_INCIDENTS', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014005, 4005, 1, 1, true, false, false, false, 'id', 'id', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(024005, 4005, 1, 2, true, true, false, true, 'subtipo', 'subtipo', 'subtipo', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(034005, 4005, 1, 3, true, true, false, true, 'description', 'description', 'description', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(044005, 4005, 1, 4, true, false, false, true, 'fecha publicacion', 'fecha publicacion', 'fecha_publicacion', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(054005, 4005, 1, 5, true, false, false, true, 'direccion', 'direccion', 'direccion', false, false, null, false, false, 180, NULL, true, true, NULL, NULL, NULL, NULL),
(064005, 4005, 1, 6, true, false, false, true, 'calle', 'calle', 'calle', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(074005, 4005, 1, 7, true, false, false, true, 'pais', 'pais', 'pais', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(084005, 4005, 1, 8, true, false, false, true, 'localidad', 'localidad', 'localidad', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(094005, 4005, 1, 9, true, false, false, true, 'confianza', 'confianza', 'confianza', false, true, null, false, false, 170, NULL, true, true, NULL, NULL, NULL, NULL),
(104005, 4005, 1, 10, true, true, false, true, 'municipal', 'municipal', 'usuario_municipio', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(114005, 4005, 1, 11, true, true, false, true, 'estado', 'estado', 'estado', false, false, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4005, 1, 'true'),
(4005, 2, 'true'),
(4005, 7, 'false'),
(4005, 8, 'true'),
(4005, 9, 'false'),
(4005, 10, 'true'),
(4005, 11, 'true'),
(4005, 12, 'true'),
(4005, 13, 'true'),
(4005, 14, 'false'),
(4005, 15, 'false'),
(4005, 17, 'true'),
(4005, 21, 'custom');

