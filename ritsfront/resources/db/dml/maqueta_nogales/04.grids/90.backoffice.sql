INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES
(9005, 2, 'LBL_GRID_ASSIGNED_RESOURCES', true, true);
--(9006, NULL, 'LBL_GRID_PARTICIPANTS', true, true);

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

--(90060001, 9006, 1, 1, true, false, false, false, 'LBL_ID', 'LBL_ID', 'id', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),
--(90060002, 9006, 2, 2, true, false, false, false, 'LBL_INCIDENT_REPORT_ID', 'LBL_INCIDENT_REPORT_ID', 'incident_report_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(90060003, 9006, 1, 3, true, false, false, false, 'LBL_NAME', 'LBL_NAME', 'name', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(90060004, 9006, 1, 4, true, false, false, false, 'LBL_RUT', 'LBL_RUT', 'rut', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(90060005, 9006, 1, 5, true, false, false, false, 'LBL_ADDRESS', 'LBL_ADDRESS', 'address', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(90060006, 9006, 1, 6, true, false, false, false, 'LBL_PHONE_INFO', 'LBL_PHONE_INFO', 'phone', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(90060007, 9006, 2, 7, true, false, false, false, 'LBL_AGE', 'LBL_AGE', 'age', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(90060008, 9006, 2, 8, true, false, false, false, 'LBL_GENDER_ID', 'LBL_GENDER_ID', 'gender_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(90060009, 9006, 2, 9, true, false, false, false, 'LBL_STAGE_ID', 'LBL_STAGE_ID', 'stage_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(900600010, 9006, 2, 10, true, false, false, false, 'LBL_USER_TYPE_ID', 'LBL_USER_TYPE_ID', 'user_type_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(900600011, 9006, 2, 11, true, false, false, false, 'LBL_INJURY_TYPE_ID', 'LBL_INJURY_TYPE_ID', 'injury_type_id', false, false, null, false, false, 200, NULL, true, true, NULL, NULL, true, NULL),
--(900600012, 9006, 2, 12, true, false, false, false, 'LBL_INJURY_LEVEL_ID', 'LBL_INJURY_LEVEL_ID', 'injury_level_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(900600013, 9006, 2, 13, true, false, false, false, 'LBL_PARTICIPANT_ASSISTANCE_TYPE_ID', 'LBL_PARTICIPANT_ASSISTANCE_TYPE_ID', 'participant_assistance_type_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),
--(900600014, 9006, 2, 14, true, false, false, false, 'LBL_PARTICIPANT_TRANSFER_PLACE_ID', 'LBL_PARTICIPANT_TRANSFER_PLACE_ID', 'participant_transfer_place_id', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL);
--
--
--INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES
--
--(9006, 1, 'true'),
--(9006, 2, 'true'),
--(9006, 7, 'true'),
--(9006, 8, 'true'),
--(9006, 9, 'false'),
--(9006, 10, 'true'),
--(9006, 11, 'true'),
--(9006, 12, 'true'),
--(9006, 13, 'true'),
--(9006, 14, 'false'),
--(9006, 15, 'false'),
--(9006, 17, 'true'),
--(9006, 20, 'true'),
--(9006, 21, 'Custom');



