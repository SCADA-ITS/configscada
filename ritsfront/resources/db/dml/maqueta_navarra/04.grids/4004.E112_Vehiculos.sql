INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(4004, 2, 'LBL_EXT_ENTITY_E112', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
(014004, 4004, 1, 1, true, true, false, true, 'id', 'id', 'id_incidente', true, false, null, false, false, 110, NULL, true, true, NULL, NULL, NULL, NULL),
(024004, 4004, 1, 2, true, false, false, true, 'matricula', 'matricula', 'matricula', false, true, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(034004, 4004, 1, 3, true, true, false, true, 'marca', 'marca', 'marca', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(044004, 4004, 1, 4, true, true, false, true, 'modelo', 'modelo', 'modelo', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL),
(054004, 4004, 1, 5, true, true, false, true, 'color', 'color', 'color', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL);

INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
(4004, 1, 'true'),
(4004, 2, 'true'),
(4004, 7, 'false'),
(4004, 8, 'true'),
(4004, 9, 'false'),
(4004, 10, 'true'),
(4004, 11, 'true'),
(4004, 12, 'true'),
(4004, 13, 'true'),
(4004, 14, 'false'),
(4004, 15, 'false'),
(4004, 17, 'true'),
(4004, 21, 'custom');

