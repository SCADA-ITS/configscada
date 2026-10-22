-- 
-- Table: conf.elements.ACCELEROMETER
--
INSERT INTO conf.elements 
(element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES 
(82, NULL, 1, 46, NULL, NULL, 'A-TG01-0465-S', 'A-TG01-0465-S',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, NULL, 2, 114, NULL, NULL, 'A-TG01-1146-S', 'A-TG01-1146-S',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, NULL, 3, 454, NULL, NULL, 'A-TG01-1729-N', 'A-TG01-1729-N',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, NULL, 4, 519, NULL, NULL, 'A-TG01-2371-N', 'A-TG01-2371-N',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values.ACCELEROMETER
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(82,1,1,1,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,2,1,1,'CUSP-3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,3,1,1,'E-0538-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,4,1,1,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,5,1,1,'Section 46',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,6,1,1,'CH4650',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,1003,1,1,'A-TG01-0465-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(82,1,1,2,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,2,1,2,'CUSP-3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,3,1,2,'E-1065-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,4,1,2,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,5,1,2,'Section 114',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,6,1,2,'CH11460',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,1003,1,2,'A-TG01-1146-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(82,1,1,3,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,2,1,3,'CUSP-3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,3,1,3,'E-1730-E',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,4,1,3,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,5,1,3,'Section 172',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,6,1,3,'CH17290',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,1003,1,3,'A-TG01-1729-N',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(82,1,1,4,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,2,1,4,'CUSP-3',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,3,1,4,'E-2459-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,4,1,4,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,5,1,4,'Section 237',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,6,1,4,'CH23710',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(82,1003,1,4,'A-TG01-2371-N',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP); 

-- 
-- Table: conf.element_io_controller_modules
--
INSERT INTO conf.element_io_controller_modules
(element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(82, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
