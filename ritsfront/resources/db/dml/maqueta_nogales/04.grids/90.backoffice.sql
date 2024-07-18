INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(9005, 2, 'LBL_GRID_ASSIGNED_RESOURCES', true, true);

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES
--el id lo definimos como: <tipo><numero de fila 4 numeros>
(90050001, 9005, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
(90050002, 9005, 1, 2, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$incident_report_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(90050003, 9005, 1, 3, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$action_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(90050004, 9005, 1, 4, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$typology_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(90050005, 9005, 1, 5, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$resource_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(90050006, 9005, 1, 6, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$notice_date', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(90050007, 9005, 1, 7, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$arrival_date', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(90050008, 9005, 1, 8, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$release_date', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
(90050009, 9005, 1, 9, true, false, false, true, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'assigned_resources$comment', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL);


