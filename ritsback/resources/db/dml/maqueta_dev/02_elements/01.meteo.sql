-- 
-- Table: conf.elements.WS
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES
(1, NULL, 1, 1, NULL,  NULL, 'MDKD01L', 'MDKD01L', NULL, NULL, 'VmsTemp.groovy', NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values.METEO
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(1,1,1,1,'Dilus',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1,2,1,1,'P2',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), 
(1,1003,1,1,'MDKD01L',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);