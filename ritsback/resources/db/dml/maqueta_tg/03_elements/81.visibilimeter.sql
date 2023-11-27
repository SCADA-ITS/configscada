-- 
-- Table: conf.elements.VISIBILIMETER
--
INSERT INTO conf.elements 
(element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES 
(81, NULL, 1, 47, NULL, NULL, 'S-TG01-0478-S', 'S-TG01-0478-S',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, NULL, 2, 114, NULL, NULL, 'S-TG01-1146-S', 'S-TG01-1146-S',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, NULL, 3, 454, NULL, NULL, 'S-TG01-1725-N', 'S-TG01-1725-N',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, NULL, 4, 517, NULL, NULL, 'S-TG01-2354-N', 'S-TG01-2354-N',  NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values.VISIBILIMETER
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(81,1,1,1,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,2,1,1,'CS-125',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,3,1,1,'E-0538-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,4,1,1,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,5,1,1,'Section 47',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,6,1,1,'4.780',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,1003,1,1,'S-TG01-0478-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(81,1,1,2,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,2,1,2,'CS-125',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,3,1,2,'E-1065-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,4,1,2,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,5,1,2,'Section 114',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,6,1,2,'11.460',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,1003,1,2,'S-TG01-1146-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(81,1,1,3,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,2,1,3,'CS-125',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,3,1,3,'E-1730-E',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,4,1,3,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,5,1,3,'Section 172',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,6,1,3,'17.250',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,1003,1,3,'S-TG01-1725-N',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 

(81,1,1,4,'CSI',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,2,1,4,'CS-125',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,3,1,4,'E-2459-S',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,4,1,4,'TG',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,5,1,4,'Section 235',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,6,1,4,'23.540',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(81,1003,1,4,'S-TG01-2354-N',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP); 

-- 
-- Table: conf.element_io_controller_modules
--
INSERT INTO conf.element_io_controller_modules
(element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(81, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
