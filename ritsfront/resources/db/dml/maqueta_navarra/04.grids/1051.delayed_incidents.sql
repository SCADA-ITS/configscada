delete from ui.grid_field_icons where grid_field_id = 105108;
delete from ui.grid_fields where grid_id = 1051;

INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function, precision_filter) VALUES
(105101, 1051, 2, 1, true, false, false, false, 'LBL_GRID_FIELD_ID', 'LBL_GRID_FIELD_ID', 'id', true, false, NULL, false, false, 50, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(105102, 1051, 2, 2, true, false, false, false, '', '', 'incidentId', true, false, NULL, false, false, 80, NULL, true, false, NULL, NULL, NULL, NULL, NULL),
(105103, 1051, 1, 3, true, false, false, false, 'LBL_GRID_FIELD_INCIDENT_TYPE_ALIAS', 'LBL_GRID_FIELD_INCIDENT_TYPE_ALIAS', 'incidentTypeAlias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105104, 1051, 1, 4, true, false, true, false, 'LBL_GRID_FIELD_INCIDENT_LEVEL_LABEL_ALIAS', 'LBL_GRID_FIELD_INCIDENT_LEVEL_LABEL_ALIAS', 'incidentLevelLabelAlias', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105105, 1051, 1, 5, true, false, true, false, 'alias', 'alias', 'alias', false, false, NULL, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105106, 1051, 1, 6, true, false, true, false, 'LBL_GRID_FIELD_INCIDENT_TYPE_DESCRIPTION', 'LBL_GRID_FIELD_INCIDENT_TYPE_DESCRIPTION', 'incidentTypeDescription', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105107, 1051, 1, 7, true, false, false, false, 'LBL_GRID_FIELD_AFFECTION_STRETCH', 'LBL_GRID_FIELD_AFFECTION_STRETCH', 'affectionStretchAlias', false, false, NULL, false, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105108, 1051, 1, 8, true, false, true, false, 'LBL_GRID_FIELD_LOCATION', 'LBL_GRID_FIELD_LOCATION', 'locationAlias', false, false, NULL, false, false, 200, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105109, 1051, 1, 9, true, false, false, false, 'LBL_GRID_FIELD_INCIDENT_STATE_LABEL_ALIAS', 'LBL_GRID_FIELD_INCIDENT_STATE_LABEL_ALIAS', 'delayedIncidentStateLabelAlias', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105110, 1051, 1, 10, true, false, false, false, 'LBL_GRID_FIELD_CRONEXPRESION', 'LBL_GRID_FIELD_CRONEXPRESION', 'cronExpression', false, false, NULL, true, false, 150, NULL, true, true, NULL, NULL, NULL, NULL, NULL),
(105111, 1051, 1, 11, true, false, false, false, 'LBL_GRID_FIELD_NEXT_EXEC', 'LBL_GRID_FIELD_NEXT_EXEC', 'cronExpression', false, false, NULL, false, false, 175, NULL, true, true, NULL, NULL, NULL, 'getNextExec', NULL);

INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES
(105109, NULL, NULL, false, true, false, true, NULL);