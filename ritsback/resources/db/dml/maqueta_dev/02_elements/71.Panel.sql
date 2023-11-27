-- 
-- Table: conf.elements.panel
--
INSERT INTO conf.elements
(element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES 
(71,22,1,1000,NULL, NULL,'VMS_DGT_1','VMS_DGT_1',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,2,1,NULL, NULL,'VMS_DGT_2','VMS_DGT_2',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,3,1,NULL, NULL,'VMS_DGT_3','VMS_DGT_3',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,4,1,NULL, NULL,'VMS_DGT_4','VMS_DGT_4',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,5,1,NULL, NULL,'VMS_DGT_5','VMS_DGT_5',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,6,1,NULL, NULL,'VMS_DGT_6','VMS_DGT_6',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,7,1,NULL, NULL,'VMS_DGT_7','VMS_DGT_7',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,8,1,NULL, NULL,'VMS_DGT_8','VMS_DGT_8',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,9,1,NULL, NULL,'VMS_DGT_9','VMS_DGT_9',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,10,1,NULL, NULL,'VMS_DGT_10','VMS_DGT_10',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,11,1,NULL, NULL,'VMS_DGT_11','VMS_DGT_11',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,12,1,NULL, NULL,'VMS_DGT_12','VMS_DGT_12',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,13,1,NULL, NULL,'VMS_DGT_13','VMS_DGT_13',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71,22,14,1,NULL, NULL,'VMS_DGT_14','VMS_DGT_14',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
--(71,18,3,1,NULL, NULL,'VMS_128x64_1','VMS_128x64_1',NULL,NULL,NULL,NULL,true,true,false,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.GT
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(71,4,1,1,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text","maxCharsPerLine":5},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,1,'VMS_DGT_1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 1, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,2,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,2,'VMS_DGT_2',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 2, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,3,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,3,'VMS_DGT_3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 3, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,4,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,4,'VMS_DGT_4',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 4, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,5,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,5,'VMS_DGT_5',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 5, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,6,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,6,'VMS_DGT_6',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 6, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,7,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,7,'VMS_DGT_7',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 7, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,8,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,8,'VMS_DGT_8',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 8, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,9,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,9,'VMS_DGT_9',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 9, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,10,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,10,'VMS_DGT_10',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 10, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,11,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,11,'VMS_DGT_11',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 11, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,12,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,12,'VMS_DGT_12',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 12, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,13,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,13,'VMS_DGT_13',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 13, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(71,4,1,14,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"text"},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(71,1003,1,14,'VMS_DGT_14',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(71, 9, 1, 14, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--(71,4,1,3,'[{"zone":1,"type":"graphic","vms_group_id":1},{"zone":2,"type":"dual","vms_group_id":1},{"zone":3,"type":"graphic","vms_group_id":2}]',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
--(71,1003,1,3,'VMS_128x64_1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

DELETE FROM rt.pm_command_element_values;
DELETE FROM rt.pm_command_elements;

---- 
---- Table: rt.pm_command_elements
----
--INSERT INTO rt.pm_command_elements
--(pm_command_element_id, command_element_type_id, element_type_id, element_id, queue_id, priority_id, start_time, end_time, "source", enabled, visible, created_at, updated_at) VALUES
--(1, 2, 71, 1, 1, 1, '2022-03-28 12:48:02.712', '2022-03-30 12:48:02.712', '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(2, 2, 71, 1, 1, 1, '2022-03-28 12:48:02.712', '2022-03-30 12:48:02.712', '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(3, 2, 71, 1, 1, 1, '2022-03-28 12:48:02.712', '2022-03-30 12:48:02.712', '', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
---- 
---- Table: rt.pm_command_element_values
----
--INSERT INTO rt.pm_command_element_values
--(pm_command_element_id, command_element_type_id, element_type_id, element_id, queue_id, command_element_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
--(1, 2, 71, 1, 1, 1, '[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":81,"alternance":744}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"ACCIDENTE","alternance":"REDUZCA"},{"id":2,"value":"A 3 KM EN","alternance":"LA VELOCIDAD"},{"id":3,"value":"CARRIL DER.","alternance":"USE CAR. IZD"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":85}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(2, 2, 71, 1, 1, 1, '[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":22,"alternance":29}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"PRECAUCION","alternance":"ATENCION","color":"#ff0000"},{"id":2,"value":"LA SENDA","alternance":"AMIGO","color":"#00ff00"},{"id":3,"value":"ES PELIGROSA","alternance":"CONDUCTOR","color":"#0000ff"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":28}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(3, 2, 71, 1, 1, 1, '[{"zone":1,"graphics":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
