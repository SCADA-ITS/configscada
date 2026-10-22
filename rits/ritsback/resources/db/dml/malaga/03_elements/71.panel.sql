INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(71, 22, 1, 0010071, NULL, NULL, 'PMV-A7056-PK-1-050-C', 'PMV-A7056-PK-1-050-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 22, 2, 0020071, NULL, NULL, 'PMV-A7056-PK-0-550-D', 'PMV-A7056-PK-0-550-D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 22, 3, 0030071, NULL, NULL, 'PMV-A7056-PK-0-050-C', 'PMV-A7056-PK-0-050-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 22, 4, 0040071, NULL, NULL, 'PMV-A357-PK-59-950-C', 'PMV-A357-PK-59-950-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 22, 5, 0050071, NULL, NULL, 'PMV-A357-PK-61-645-D', 'PMV-A357-PK-61-645-D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 6, 0060071, NULL, NULL, 'PGC1-A7056-PK-0-550-C', 'PGC1-A7056-PK-0-550-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 7, 0070071, NULL, NULL, 'PGC2-A7056-PK-0-550-C', 'PGC2-A7056-PK-0-550-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 8, 0080071, NULL, NULL, 'PGC3-A7056-PK-0-550-C', 'PGC3-A7056-PK-0-550-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 9, 0090071, NULL, NULL, 'PGC1-A7056-PK-0-050-C', 'PGC1-A7056-PK-0-050-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 10, 0100071, NULL, NULL, 'PGC2-A7056-PK-0-050-C', 'PGC2-A7056-PK-0-050-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 11, 0110071, NULL, NULL, 'PGC3-A7056-PK-0-050-C', 'PGC3-A7056-PK-0-050-C', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 12, 0120071, NULL, NULL, 'PGC1-A357-PK-61-000-D', 'PGC1-A357-PK-61-000-D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 13, 0130071, NULL, NULL, 'PGC2-A357-PK-61-000-D', 'PGC2-A357-PK-61-000-D', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(71, 2, 1, 1, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 1, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 1, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":12},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 1, NULL, 'PMV-A7056-PK-1-050-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 2, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 2, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 2, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":12},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 2, NULL, 'PMV-A7056-PK-0-550-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 3, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 3, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 3, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":12},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 3, NULL, 'PMV-A7056-PK-0-050-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 4, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 4, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 4, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":12},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 4, NULL, 'PMV-A357-PK-59-950-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 5, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 5, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 5, NULL, '[{"zone":1,"type":"graphic","vms_group_id":22},{"zone":2,"type":"text","maxCharsPerLine":12},{"zone":3,"type":"graphic","vms_group_id":22}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 5, NULL, 'PMV-A357-PK-61-645-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 6, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 6, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 6, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 6, NULL, 'PGC1-A7056-PK-0-550-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 7, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 7, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 7, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 7, NULL, 'PGC2-A7056-PK-0-550-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 8, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 8, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 8, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 8, NULL, 'PGC3-A7056-PK-0-550-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 9, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 9, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 9, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 9, NULL, 'PGC1-A7056-PK-0-050-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 10, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 10, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 10, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 10, NULL, 'PGC2-A7056-PK-0-050-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 11, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 11, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 11, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 11, NULL, 'PGC3-A7056-PK-0-050-C', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 12, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 12, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 12, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 12, NULL, 'PGC1-A357-PK-61-000-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 2, 1, 13, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 13, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 13, NULL, '[{"zone":1,"type":"graphic","vms_group_id":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 13, NULL, 'PGC2-A357-PK-61-000-D', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(71, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
