INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(54, NULL, 1, 0010054, NULL, NULL, 'NO-S1S1-MCFV-01', 'NO-S1S1-MCFV-01', NULL, NULL, 'EtdParamsAdapterTFA_NOG.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, NULL, 2, 0020054, NULL, NULL, 'NO-S1S2-MCFV-02', 'NO-S1S2-MCFV-02', NULL, NULL, 'EtdParamsAdapterTFA_NOG.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, NULL, 3, 0030054, NULL, NULL, 'NO-S1S3-MCFV-03', 'NO-S1S3-MCFV-03', NULL, NULL, 'EtdParamsAdapterTFA_NOG.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, NULL, 4, 0040054, NULL, NULL, 'NO-S2-MCFV-04', 'NO-S2-MCFV-04', NULL, NULL, 'EtdParamsAdapterTFA_NOG.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(54, 1, 1, 1, NULL, 'PAT-TRAFFIC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 2, 1, 1, NULL, 'AVC-1000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 1003, 1, 1, NULL, 'NO-S1S1-MCFV-01', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(54, 1, 1, 2, NULL, 'PAT-TRAFFIC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 2, 1, 2, NULL, 'AVC-1000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 1003, 1, 2, NULL, 'NO-S1S2-MCFV-02', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(54, 1, 1, 3, NULL, 'PAT-TRAFFIC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 2, 1, 3, NULL, 'AVC-1000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 1003, 1, 3, NULL, 'NO-S1S3-MCFV-03', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(54, 1, 1, 4, NULL, 'PAT-TRAFFIC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 2, 1, 4, NULL, 'AVC-1000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 1003, 1, 4, NULL, 'NO-S2-MCFV-04', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(54, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(54, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);