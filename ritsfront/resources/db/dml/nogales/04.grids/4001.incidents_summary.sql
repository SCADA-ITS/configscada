INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4001, 2, 'LBL_INCIDENTS_SUMMARY', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(014001, 4001, 1, 1, true, false, false, true, 'id', 'id', 'id_incidente', true, false, null, false, false, 110, NULL, true, false, NULL, NULL, NULL, NULL),
(024001, 4001, 1, 2, true, true, false, true, 'anio', 'anio', 'anio', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(034001, 4001, 1, 3, true, true, false, true, 'mes', 'mes', 'mes', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(044001, 4001, 1, 4, true, false, false, true, 'dia', 'dia', 'dia', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(054001, 4001, 1, 5, true, false, false, true, 'hora', 'hora', 'hora', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(064001, 4001, 1, 6, true, false, false, true, 'km', 'km', 'km', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(074001, 4001, 1, 7, true, true, false, true, 'tipo accidente', 'tipo accidente', 'tipo_accidente', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(084001, 4001, 1, 8, true, false, false, true, 'ilesos', 'ilesos', 'ilesos', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(094001, 4001, 1, 9, true, false, false, true, 'muertos', 'muertos', 'muertos', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(104001, 4001, 1, 10, true, false, false, true, 'graves', 'graves', 'graves', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(114001, 4001, 1, 11, true, false, false, true, 'menos graves', 'menos graves', 'menos_graves', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(124001, 4001, 1, 12, true, false, false, true, 'leves', 'leves', 'leves', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(134001, 4001, 1, 13, true, false, false, true, 'lesionados', 'lesionados', 'lesionados', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(144001, 4001, 1, 14, true, true, false, true, 'causa', 'causa', 'causa', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(154001, 4001, 1, 15, true, true, false, true, 'ubicacion relativa', 'ubicacion relativa', 'ubicacion_relativa', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),
(164001, 4001, 1, 16, true, false, false, true, 'internas', 'internas', 'internas', false, false, null, false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL),
(174001, 4001, 1, 17, true, false, false, true, 'externas', 'externas', 'externas', false, false, null, false, false, 300, NULL, true, true, NULL, NULL, NULL, NULL);


INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4001, 1, 'true'),
(4001, 2, 'true'),
(4001, 7, 'false'),
(4001, 8, 'true'),
(4001, 9, 'false'),
(4001, 10, 'true'),
(4001, 11, 'true'),
(4001, 12, 'false'),
(4001, 13, 'true'),
(4001, 14, 'false'),
(4001, 15, 'false'),
(4001, 17, 'false'),
(4001, 21, 'custom'),
(4001, 25, 'true'),
(4001, 26, 'true');
