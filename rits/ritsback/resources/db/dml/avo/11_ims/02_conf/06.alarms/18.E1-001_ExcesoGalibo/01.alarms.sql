--
-- Table: conf.ims_incident_alarm_conditions
--

INSERT INTO conf.ims_incident_alarm_conditions (incident_alarm_condition_id, incident_type_id, stretch_id, auto, enabled, visible, created_at, updated_at)VALUES
(0001049,49,123,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0002049,49,199,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0003049,49,200,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0004049,49,202,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0005049,49,203,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0006049,49,204,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0007049,49,205,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0008049,49,211,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0009049,49,212,true,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 
-- Table: conf.ims_incident_alarm_condition_alarms
--
INSERT INTO conf.ims_incident_alarm_condition_alarms (incident_alarm_condition_alarm_id, incident_alarm_condition_id, incident_alarm_condition_alarm_type_id, alarm_config_id, element_type_id, element_id, enabled, visible, created_at, updated_at)VALUES
(0001049,0001049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0002049,0002049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0003049,0003049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0004049,0004049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0005049,0005049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0006049,0006049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0007049,0007049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0008049,0008049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(0009049,0009049,2,348,null,null,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

