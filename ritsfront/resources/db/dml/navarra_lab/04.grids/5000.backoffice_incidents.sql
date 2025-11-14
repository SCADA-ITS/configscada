INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(5000, 2, 'LBL_INCIDENTS', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
--(500001, 5000, 2, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, NULL, false, false, 50, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(500002, 5000, 2, 2, true, false, false, false, '', '', 'incident_report_id', true, false, NULL, false, false, 50, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500003, 5000, 1, 3, true, false, false, false, 'tipo incidencia', 'tipo incidencia', 'incident_type', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500004, 5000, 1, 4, true, false, false, false, 'descripción', 'descripción', 'incident_sub_type', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500005, 5000, 1, 5, true, false, false, false, 'nivel', 'nivel', 'level', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500006, 5000, 1, 6, true, false, false, false, 'tramo', 'tramo', 'stretch', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500007, 5000, 1, 7, true, false, false, false, 'localización', 'localización', 'location', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500008, 5000, 1, 8, true, false, false, false, 'latitud', 'latitud', 'lat', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500009, 5000, 1, 9, true, false, false, false, 'longitud', 'longitud', 'lon', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500010, 5000, 1, 10, true, false, false, false, 'comentarios', 'comentarios', 'comment', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500011, 5000, 4, 11, true, false, false, false, 'inicio', 'inicio', 'created_at', false, true, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500012, 5000, 4, 12, true, false, false, false, 'final', 'final', 'finish_at', false, true, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500013, 5000, 1, 13, true, false, false, false, 'afección máxima', 'afección máxima', 'end_value', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500014, 5000, 1, 14, true, false, false, false, 'detalle afección', 'detalle afección', 'affection_detail_id', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500015, 5000, 1, 15, true, false, false, false, 'victimas', 'victimas', 'deceassed', false, false, NULL, false, false, 50, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500016, 5000, 1, 16, true, false, false, false, 'nº víctimas mortales', 'nº víctimas mortales', 'num_decesseased', false, false, NULL, false, false, 50, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(500017, 5000, 1, 17, true, false, false, false, 'afección máxima', 'afección máxima', 'max_affection', false, false, NULL, false, false, 50, NULL, true, true, NULL, NULL, NULL, NULL, NULL);



