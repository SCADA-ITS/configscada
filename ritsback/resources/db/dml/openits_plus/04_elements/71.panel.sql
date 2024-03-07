INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(71, 18, 1, 0010071, NULL, NULL, 'TUN-PMV-4CA', 'TUN-PMV-4CA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 2, 0020071, NULL, NULL, 'TUN-PMV-3MA', 'TUN-PMV-3MA', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 3, 0030071, NULL, NULL, 'MAP-DGT-1', 'MAP-DGT-1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 4, 0040071, NULL, NULL, 'MAP-DGT-2', 'MAP-DGT-2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 5, 0050071, NULL, NULL, 'MAP-DGT-3', 'MAP-DGT-3', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 6, 0060071, NULL, NULL, 'MAP-DGT-4', 'MAP-DGT-4', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 7, 0070071, NULL, NULL, 'MAP-DGT-5', 'MAP-DGT-5', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 8, 0080071, NULL, NULL, 'MAP-DGT-6', 'MAP-DGT-6', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 9, 0090071, NULL, NULL, 'MAP-DGT-7', 'MAP-DGT-7', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 10, 0100071, NULL, NULL, 'MAP-DGT-8', 'MAP-DGT-8', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 11, 0110071, NULL, NULL, 'MAP-DGT-9', 'MAP-DGT-9', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 12, 0120071, NULL, NULL, 'MAP-DGT-10', 'MAP-DGT-10', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 13, 0130071, NULL, NULL, 'MAP-DGT-11', 'MAP-DGT-11', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 18, 14, 0140071, NULL, NULL, 'MAP-DGT-12', 'MAP-DGT-12', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(71, 1, 1, 1, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 1, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 1, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 1, NULL, 'TUN-PMV-4CA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 2, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 2, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 2, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 2, NULL, 'TUN-PMV-3MA', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 3, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 3, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 3, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 3, NULL, 'MAP-DGT-1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 4, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 4, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 4, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 4, NULL, 'MAP-DGT-2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 5, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 5, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 5, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 5, NULL, 'MAP-DGT-3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 6, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 6, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 6, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 6, NULL, 'MAP-DGT-4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 7, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 7, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 7, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 7, NULL, 'MAP-DGT-5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 8, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 8, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 8, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 8, NULL, 'MAP-DGT-6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 9, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 9, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 9, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 9, NULL, 'MAP-DGT-7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 10, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 10, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 10, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 10, NULL, 'MAP-DGT-8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 11, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 11, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 11, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 11, NULL, 'MAP-DGT-9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 12, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 12, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 12, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 12, NULL, 'MAP-DGT-10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 13, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 13, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 13, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 13, NULL, 'MAP-DGT-11', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(71, 1, 1, 14, NULL, 'Fixalia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 2, 1, 14, NULL, 'DGT', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 4, 1, 14, NULL, '[{"zone":1,"type":"graphic","vms_group_id":18},{"zone":2,"type":"text","maxCharsPerLine":12}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 1003, 1, 14, NULL, 'MAP-DGT-12', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(71, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(71, 14, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);