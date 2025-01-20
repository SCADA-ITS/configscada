INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4003, 2, 'LBL_EXT_ENTITY_E112', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014003, 4003, 1, 1, true, false, false, true, 'id', 'id', 'id_incidente', true, false, null, false, false, 110, NULL, true, false, NULL, NULL, NULL, NULL),
(024003, 4003, 4, 2, true, false, false, true, 'fecha salida', 'fecha salida', 'fecha_salida', false, false, null, false, false, 175, NULL, true, true, NULL, NULL, NULL, NULL),
(034003, 4003, 1, 3, true, false, false, true, 'nombre', 'nombre', 'nombre', false, true, null, false, false, 100, NULL, true, true, NULL, NULL, NULL, NULL),
(044003, 4003, 1, 4, true, false, false, true, 'agencia', 'agencia', 'agencia', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(054003, 4003, 1, 5, true, true, false, true, 'estacion', 'estacion', 'estacion', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(064003, 4003, 4, 6, true, false, false, true, 'fecha ultimo estado', 'fecha ultimo estado', 'fecha_ultimo_estado', false, false, null, false, false, 175, NULL, true, true, NULL, NULL, NULL, NULL),
(074003, 4003, 1, 7, true, true, false, true, 'estado', 'estado', 'estado', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4003, 1, 'true'),
(4003, 2, 'true'),
(4003, 7, 'false'),
(4003, 8, 'true'),
(4003, 9, 'false'),
(4003, 10, 'true'),
(4003, 11, 'true'),
(4003, 12, 'false'),
(4003, 13, 'true'),
(4003, 14, 'false'),
(4003, 15, 'false'),
(4003, 17, 'false'),
(4003, 21, 'custom');

