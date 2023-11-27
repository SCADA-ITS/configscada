-- 
-- Table: conf.elements.TRAVEL_TIME
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(6, NULL, 1, NULL, NULL,  NULL, 'TT_01', 'TT_01', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, NULL, 2, NULL, NULL,  NULL, 'TT_02', 'TT_02', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, NULL, 3, NULL, NULL,  NULL, 'TT_03', 'TT_03', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 
-- Table: conf.element_values.TRAVEL_TIME
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(6,1,1,1,'false',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(6,2,1,1,'8.940',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(6,3,1,1,'22.413',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,4,1,1,'LAS ROZAS',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,5,1,1,'VMS_DGT_1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(6,1,1,2,'false',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(6,2,1,2,'8.940',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(6,3,1,2,'40.000',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,4,1,2,'VILLALBA',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,5,1,2,'VMS_DGT_1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

(6,1,1,3,'false',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(6,2,1,3,'8.940',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(6,3,1,3,'60.000',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,4,1,3,'TUNELES',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(6,5,1,3,'VMS_DGT_1',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
