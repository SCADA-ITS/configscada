INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4002, 2, 'LBL_INCIDENTS_SUMMARY', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(014002, 4002, 1, 1, true, false, false, true, 'id', 'id', 'id_incidente', true, false, null, false, false, 110, NULL, true, false, NULL, NULL, NULL, NULL),
(024002, 4002, 1, 2, true, true, false, true, 'anio', 'anio', 'anio', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(034002, 4002, 1, 3, true, true, false, true, 'mes', 'mes', 'mes', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(044002, 4002, 1, 4, true, false, false, true, 'total', 'total', 'total_accidentes', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(054002, 4002, 1, 5, true, false, false, true, 'con lesionados', 'con lesionados', 'con_lesionados', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL),
(064002, 4002, 1, 6, true, false, false, true, 'muertos', 'muertos', 'muertos', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(074002, 4002, 1, 7, true, false, false, true, 'graves', 'graves', 'graves', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(084002, 4002, 1, 8, true, false, false, true, 'menos graves', 'menos graves', 'menos_graves', false, false, null, false, false, 120, NULL, true, true, NULL, NULL, NULL, NULL),
(094002, 4002, 1, 9, true, false, false, true, 'leves', 'leves', 'leves', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(104002, 4002, 1, 10, true, false, false, true, 'lesionados', 'lesionados', 'lesionados', false, false, null, false, false, 90, NULL, true, true, NULL, NULL, NULL, NULL),
(114002, 4002, 1, 11, true, false, false, true, 'atropello', 'atropello', 'atropello', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(124002, 4002, 1, 12, true, false, false, true, 'caida', 'caida', 'caida', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(134002, 4002, 1, 13, true, false, false, true, 'volcadura', 'volcadura', 'volcadura', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(144002, 4002, 1, 14, true, false, false, true, 'colision', 'colision', 'colision', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(154002, 4002, 1, 15, true, false, false, true, 'choque', 'choque', 'choque', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL),
(164002, 4002, 1, 16, true, false, false, true, 'otros', 'otros', 'otros', false, false, null, false, false, 80, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4002, 1, 'true'),
(4002, 2, 'true'),
(4002, 7, 'false'),
(4002, 8, 'true'),
(4002, 9, 'false'),
(4002, 10, 'true'),
(4002, 11, 'true'),
(4002, 12, 'false'),
(4002, 13, 'true'),
(4002, 14, 'false'),
(4002, 15, 'false'),
(4002, 17, 'false'),
(4002, 21, 'custom'),
(4002, 25, 'true'),
(4002, 26, 'true');
