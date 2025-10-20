INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(3, 1, 1, 0010003, NULL, NULL, '01.8-MR1-ETD-01-SEC1-DET1', '01.8-MR1-ETD-01-SEC1-DET1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 2, 0020003, NULL, NULL, '01.8-MR1-ETD-01-SEC1-DET2', '01.8-MR1-ETD-01-SEC1-DET2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 3, 0050003, NULL, NULL, '01.8-MR1-ETD-01-SEC2-DET1', '01.8-MR1-ETD-01-SEC2-DET1', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 4, 0060003, NULL, NULL, '01.8-MR1-ETD-01-SEC2-DET2', '01.8-MR1-ETD-01-SEC2-DET2', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(3, 1, 1, 1, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 1, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 1, NULL, '01.8-MR1-ETD-01-SEC1-DET1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 2, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 2, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 2, NULL, '01.8-MR1-ETD-01-SEC1-DET2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 3, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 3, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 3, NULL, '01.8-MR1-ETD-01-SEC2-DET1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 4, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 4, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 4, NULL, '01.8-MR1-ETD-01-SEC2-DET2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(3, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 5;
update master.element_type_params set element_type_param_unit_id = 11 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 17;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 100;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 101;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 102;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 103;

delete from master.element_subtype_active_params where element_type_id = 3 and element_subtype_id = 1;

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 3, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 3, 2, 40, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 41, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 42, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 43, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 3, 2, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 103, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 3, 2, 120, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


update master.i18n_labels set translation = '<= 60 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_1' and locale_code = 'es_CL';
update master.i18n_labels set translation = '61-100 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_2' and locale_code = 'es_CL';
update master.i18n_labels set translation = '101-120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_3' and locale_code = 'es_CL';
update master.i18n_labels set translation = '>= 120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_4' and locale_code = 'es_CL';
update master.i18n_labels set translation = '<= 60 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_1_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = '61-100 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_2_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = '101-120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_3_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = '>= 120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_4_DESC' and locale_code = 'es_CL';

update master.i18n_labels set translation = 'Autos y camionetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_1' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Camionetas doble rueda trasera y/o autos y camionetas con remolque' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_2' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Camiones de dos ejes, maquinaria agrícola o de construcción ' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_3' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Motos, cuatrimotos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_4' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Autos y camionetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_1_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Camionetas doble rueda trasera y/o autos y camionetas con remolque' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_2_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Camiones de dos ejes, maquinaria agrícola o de construcción ' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_3_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Motos, cuatrimotos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_4_DESC' and locale_code = 'es_CL';

update master.i18n_labels set translation = 'Detector PAT' where label = 'LBL_ELEMENT_SUBTYPE_DETECTOR_KINEO' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Detector PAT' where label = 'LBL_ELEMENT_SUBTYPE_DETECTOR_KINEO_DESC' and locale_code = 'es_CL';


